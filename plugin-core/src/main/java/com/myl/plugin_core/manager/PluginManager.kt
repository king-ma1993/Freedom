package com.myl.plugin_core.manager

import android.content.ComponentName
import android.content.Context
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.content.pm.PackageInfo
import android.content.pm.ServiceInfo
import android.content.res.AssetManager
import android.text.TextUtils
import androidx.collection.ArrayMap
import com.myl.plugin_core.Constant
import com.myl.plugin_core.helper.PluginNativeLibsHelper
import com.myl.plugin_core.manager.server.PluginServerInfo
import com.myl.plugin_core.manifest.AndroidManifestParser
import com.myl.plugin_core.util.FileUtils
import com.myl.plugin_core.util.LogUtils
import com.myl.plugin_core.util.SystemUtils
import java.io.File
import java.io.IOException

class PluginManager(
    private val packageInfo: PackageInfo,
    private val pluginPath: String,
    private val context: Context
) {

    private var mPluginAssetManager: AssetManager? = null

    companion object {
        const val TAG = "PluginManager"
    }

    /**
     * 存放插件activity的信息
     */
    private val mActivitiesInfo: MutableMap<ComponentName, ActivityInfo> = ArrayMap()
    /**
     * 存放插件service的信息
     */
    private val mServiceInfo: MutableMap<ComponentName, ServiceInfo> = ArrayMap()

    /**
     * receiver -> intent filters对应关系
     */
    private val mReceivers: MutableMap<String, List<IntentFilter>> = HashMap()

    /**
     * activity -> intent filters对应关系
     */
    private val mActivities: MutableMap<String, List<IntentFilter>> = ArrayMap()
    /**
     * service -> intent filters对应关系
     */
    private val mServices: MutableMap<String, List<IntentFilter>> = ArrayMap()
//
//    /**
//     *  存放插件receivers的name信息
//     */
//    private val mReceiverInfo: HashMap<ComponentName, ActivityInfo> = hashMapOf()

    fun parsePlugin(): PluginServerInfo? {
        // 2.对apk进行拷贝
        val localPluginPath = copyOrMoveApk(pluginPath, packageInfo)
        LogUtils.d(TAG, "copyOrMoveApk: path?:$localPluginPath")
        // 3.从插件中释放 So 文件
        PluginNativeLibsHelper.install(
            localPluginPath!!,
            getNativeLibsDir()
        )
        //4.解析清单文件里面的内容
        parseManifest()
        //5.创建pluginServerInfo
        val pluginServerInfo = buildPluginInfo(packageInfo, localPluginPath)
        pluginServerInfo?.apply {
            odexDirPath = getOdexDirPath()
            libSoPath = getLibSoPath()
        }
        //6.保证文件夹创建
        FileUtils.ensureDirectoryCreated(File(getOdexDirPath()))
        FileUtils.ensureDirectoryCreated(File(getLibSoPath()))
        return pluginServerInfo
    }

    private fun getLibSoPath(): String {
        return getApkDir(context) + "/" + Constant.LIB_DIR
    }


    @Throws(Throwable::class)
    private fun createAssetManager(ctx: Context?): AssetManager? {
        ctx?.apply {
            val pm = ctx.packageManager
            val res = pm.getResourcesForApplication(packageInfo.applicationInfo)
            return res.assets
        }
        return null
    }

    private fun initPluginAssetManager() {
        mPluginAssetManager =
            createAssetManager(context)
    }


    private fun parseManifest() {
        insureCreatePluginAssetManager()
        mPluginAssetManager?.apply {
            val componentIntentFilters = AndroidManifestParser.parse(this)
            mReceivers.putAll(componentIntentFilters.mReceivers)
            mActivities.putAll(componentIntentFilters.mActivities)
            mServices.putAll(componentIntentFilters.mServices)
        }
        initActivityInfo()
        initServiceInfo()
//        initReceiver()
    }

    private fun insureCreatePluginAssetManager() {
        if (mPluginAssetManager == null) {
            initPluginAssetManager()
        }
    }

    /**
     * 根据类型来获取SO释放的路径
     *
     *
     * 注意：仅供框架内部使用
     *
     * @return SO释放路径所在的File对象
     */
    private fun getNativeLibsDir(): File {
        // 必须使用宿主的Context对象，防止出现“目录定位到插件内”的问题
        val dir: File = context!!.getDir(Constant.LOCAL_PLUGIN_APK_DIR, 0)
        return File(
            dir,
            getInstallFileName(
                packageInfo.packageName
            )
        )
    }


    private fun buildPluginInfo(
        packageInfo: PackageInfo,
        pluginPath: String?
    ): PluginServerInfo? {
        pluginPath?.apply {
            return PluginServerInfo(packageInfo, pluginPath)
        }
        return null
    }


    /**
     * 拷贝插件apk到
     */
    private fun copyOrMoveApk(path: String?, packageInfo: PackageInfo?): String? {
        if (TextUtils.isEmpty(path) || packageInfo == null) {
            return null
        }
        path?.apply {
            val srcFile = File(path)
            val newFile =
                getApkFile(packageInfo?.packageName, context)

            // 插件已被释放过一次？通常“同版本覆盖安装”时，覆盖次数超过2次的会出现此问题
            // 此时，直接删除安装路径下的文件即可，这样就可以直接Move/Copy了
            if (newFile.exists()) {
                FileUtils.deleteQuietly(newFile)
            }

            // 将源APK文件移动/复制到安装路径下
            try {
                FileUtils.copyFile(srcFile, newFile)
            } catch (e: IOException) {
                LogUtils.e(
                    TAG,
                    "copyOrMoveApk: Copy/Move Failed! src=$srcFile; dest=$newFile",
                    e
                )
            }
            //替换路径已经修改为解压后的路径
            return newFile.absolutePath
        }
        return null
    }

    private fun getApkFile(packageName: String?, context: Context): File {
        return File(
            getApkDir(context), getInstallFileName(
                packageName
            ) + ".apk"
        )
    }

    /**
     * 获取APK存放的文件信息
     *
     *
     *
     *
     * @return Apk所在的File对象
     */

    /**
     * 获取APK存放目录
     *
     * @return
     */
    private fun getApkDir(context: Context): String {
        // 必须使用宿主的Context对象，防止出现“目录定位到插件内”的问题
        var dir: File = context!!.getDir(Constant.LOCAL_PLUGIN_APK_DIR, 0)
        return dir.absolutePath
    }


    /**
     * 获取优化后dex存放目录
     */
    private fun getOdexDirPath(): String {
        val odexPath = if (SystemUtils.isVmArt()) {
            Constant.ODEX_DIR
        } else {
            Constant.OAT_DIR
        }
        return getApkDir(context) + "/" + odexPath
    }

    private fun getInstallFileName(packageName: String?): String {
        val name =
            packageName?.toLowerCase()
        val hashcode = name.hashCode()
        return (hashcode + 26).toString()
    }



//    private fun initReceiver() {
//        LogUtils.d(TAG, "packageInfo.receiver = :${packageInfo?.receivers}")
//        val receivers = packageInfo?.receivers ?: return
//
//        for (activityInfo in receivers) {
//            mReceiverInfo[ComponentName(activityInfo.packageName, activityInfo.name)] =
//                activityInfo
//        }
//    }

    private fun initServiceInfo() {
        LogUtils.d(TAG, "packageInfo.services = :${packageInfo?.services}")
        val services = packageInfo?.services ?: return

        for (serviceInfo in services) {
            mServiceInfo[ComponentName(serviceInfo.packageName, serviceInfo.name)] =
                serviceInfo
        }
    }

    private fun initActivityInfo() {
        LogUtils.d(TAG, "packageInfo.activities = :${packageInfo?.activities}")
        val activities = packageInfo?.activities ?: return

        for (activityInfo in activities) {
            mActivitiesInfo[ComponentName(activityInfo.packageName, activityInfo.name)] =
                activityInfo
        }
    }


    /**
     * 获取插件的 ActivityInfo
     *
     * @param componentName the Activity ComponentName
     * @return activity 对应的 ActivityInfo 对象，如果 activity 不存在则返回 null
     */
    fun findPluginActivityInfo(componentName: ComponentName?): ActivityInfo? {
        return mActivitiesInfo[componentName]
    }

    /**
     * 获取插件的 ServiceInfo
     *
     * @param componentName the ServiceInfo ComponentName
     * @return Service 对应的 ServiceInfo 对象，如果 Service 不存在则返回 null
     */
    fun findPluginServiceInfo(componentName: ComponentName?): ServiceInfo? {
        return mServiceInfo[componentName]
    }

    fun findReceiverIntentFilter(receiverName: String): List<IntentFilter>? {
        return mReceivers[receiverName]
    }
}