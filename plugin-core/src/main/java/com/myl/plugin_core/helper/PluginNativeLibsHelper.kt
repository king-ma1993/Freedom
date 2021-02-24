package com.myl.plugin_core.helper

import android.text.TextUtils
import com.myl.plugin_core.util.CloseableUtils
import com.myl.plugin_core.util.FileUtils
import com.myl.plugin_core.util.LogUtils
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

object PluginNativeLibsHelper {
    private const val TAG = "PluginNativeLibsHelper"


    private fun injectEntriesAndLibsMap(
        zipFile: ZipFile,
        libZipEntries: MutableMap<String, ZipEntry>,
        soList: MutableMap<String, MutableSet<String>>
    ) {
        val entries = zipFile.entries()
        while (entries.hasMoreElements()) {
            val entry = entries.nextElement()
            val name = entry.name
            if (name.contains("../")) {
                continue
            }
            if (name.startsWith("lib/") && !entry.isDirectory) {
                libZipEntries[name] = entry
                val soName = File(name).name
                var fs: MutableSet<String>? = soList[soName]
                if (fs == null) {
                    fs = TreeSet()
                    soList[soName] = fs
                }
                fs.add(name)
            }
        }
    }

    /**
     * 安装Native SO库
     *
     *
     * 模拟系统安装流程，最终只释放一个最合身的SO库进入Libs目录中
     *
     * @param apkPath   APK文件路径
     * @param nativeDir 要释放的Libs目录，通常从getLibDir中获取
     * @return 安装是否成功
     */
    fun install(apkPath: String, nativeDir: File): Boolean {
        LogUtils.d(TAG, "install(): Start. apkp=" + apkPath + "; nd=" + nativeDir.absolutePath)

        // 为防止加载旧SO，先清空目录
        clear(nativeDir)

        var zipFile: ZipFile? = null
        try {
            zipFile = ZipFile(apkPath)
            val libZipEntries = HashMap<String, ZipEntry>()
            val soList = HashMap<String, MutableSet<String>>()

            // 找到所有的SO库，包括各种版本的，方便findSoPathForAbis中过滤
            injectEntriesAndLibsMap(
                zipFile,
                libZipEntries,
                soList
            )

            for (soName in soList.keys) {
                val soPaths = soList[soName]
                val soPath =
                    findSoPathForAbis(
                        soPaths,
                        soName
                    )
                LogUtils.d(TAG, "install(): Ready to extract. so=$soName; sop=$soPath")
                if (soPath == null) {
                    continue
                }
                val file = File(nativeDir, soName)
                extractFile(
                    zipFile,
                    libZipEntries[soPath],
                    file
                )
            }
            return true
        } catch (e: Throwable) {
            e.printStackTrace()
            // 清除所有释放的文件，防止释放了一半
            clear(nativeDir)
            return false
        } finally {
            CloseableUtils.closeQuietly(zipFile)
        }
    }

    @Throws(IOException::class)
    private fun extractFile(zipFile: ZipFile, ze: ZipEntry?, outFile: File) {
        var inputStream: InputStream? = null
        try {
            inputStream = zipFile.getInputStream(ze)
            FileUtils.copyInputStreamToFile(inputStream, outFile)
            LogUtils.i(TAG, "extractFile(): Success! fn=" + outFile.name)
        } finally {
            CloseableUtils.closeQuietly(inputStream)
        }
    }

    // 根据Abi来获取需要释放的SO在压缩包中的位置
    private fun findSoPathForAbis(soPaths: Set<String>?, soName: String): String? {
        if (soPaths == null || soPaths.isEmpty()) {
            return null
        }
        // 若主程序用的是64位进程，则所属的SO必须只拷贝64位的，否则会出异常。32位也是如此
        // 问：如果用户用的是64位处理器，宿主没有放任何SO，那么插件会如何？
        // 答：宿主在被安装时，系统会标记此为64位App，则之后的SO加载则只认64位的
        // 问：如何让插件支持32位？
        // 答：宿主需被标记为32位才可以。可在宿主App中放入任意32位的SO（如放到libs/armeabi目录下）即可。

        // 获取指令集列表
        val is64 = VMRuntimeCompat.is64Bit()
        val abis: Array<String>
        if (is64) {
            abis = BuildCompat.SUPPORTED_64_BIT_ABIS
        } else {
            abis = BuildCompat.SUPPORTED_32_BIT_ABIS
        }

        // 开始寻找合适指定指令集的SO路径
        val soPath =
            findSoPathWithAbiList(
                soPaths,
                soName,
                abis
            )
        LogUtils.d(
            TAG, "findSoPathForAbis: Find so path. name=" + soName + "; list=" + soPath +
                    "; Host-is-64bit?=" + is64 + "; abis=" + Arrays.toString(abis)
        )
        return soPath
    }

    /**
     * 删除插件的SO库，通常在插件SO释放失败后，或者已有新插件，需要清除老插件时才会生效
     */
    private fun clear(nativeDir: File) {
        if (!nativeDir.exists()) {
            return
        }
        try {
            FileUtils.forceDelete(nativeDir)
        } catch (e: IOException) {
            // IOException：有可能是IO，如权限出现问题等，打出日志
            e.printStackTrace()
        } catch (e2: IllegalArgumentException) {
            e2.printStackTrace()
        }

    }

    private fun findSoPathWithAbiList(
        soPaths: Set<String>,
        soName: String,
        supportAbis: Array<String>
    ): String? {
        Arrays.sort(supportAbis)
        for (soPath in soPaths) {
            var abi = soPath.replaceFirst("lib/".toRegex(), "")
            abi = abi.replace("/$soName", "")

            if (!TextUtils.isEmpty(abi) && Arrays.binarySearch(supportAbis, abi) >= 0) {
                return soPath
            }
        }
        return null
    }
}