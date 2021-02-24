package com.myl.androidplugin

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.myl.androidapkplugin.HostPluginApi
import com.myl.plugin_core.info.PluginInfo
import com.myl.plugin_core.util.FileUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import androidx.core.content.ContextCompat.getSystemService


class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val classLoader = Button::class.java.classLoader
        Log.d(TAG,"classLoader:$classLoader")
        btn_install_apk_from_assets.setOnClickListener {
            val pd = ProgressDialog.show(
                this@MainActivity,
                "Installing...",
                "Please wait...",
                true,
                true
            )
            Handler(Looper.getMainLooper()).postDelayed({
                simulateInstallExternalPlugin()
                pd.dismiss()
            }, 1000)
        }
        add_fragment.setOnClickListener {
            val intent = Intent(this, TestService::class.java)
            startService(intent)
            val msg = "这是一个动态添加的fragment"
            val bundle = Bundle()
            bundle.putString("msg", msg)
            val testFragment = TestFragment.newInstance(bundle)
            fragmentManager.beginTransaction().add(R.id.fragment_container, testFragment).commit()
        }
    }


    /**
     * 模拟安装外置插件
     * 注意：为方便演示，外置插件临时放置到Host的assets/external目录下，具体说明见README
     */
    private fun simulateInstallExternalPlugin() {
        val demoApk = "demo-debug.apk"
//        val demoApk = "wanandroid-debug.apk"
        val demoApkPath = "external" + File.separator + demoApk

        // 文件是否已经存在？直接删除重来
        val pluginFilePath = filesDir.absolutePath + File.separator + demoApk
        val pluginFile = File(pluginFilePath)
        if (pluginFile.exists()) {
            FileUtils.deleteQuietly(pluginFile)
        }

        // 开始复制
        copyAssetsFileToAppFiles(demoApkPath, demoApk)
        var info: PluginInfo? = null
        if (pluginFile.exists()) {
            info = HostPluginApi.install(pluginFilePath)
        }

        val mainEntranceClass = "com.myl.demo.PluginDemoActivity"
//        val mainEntranceClass = "com.myl.wanandroid.mvp.ui.activity.start.SplashActivity"

        if (info != null) {
            HostPluginApi.startActivity(
                this@MainActivity,
                HostPluginApi.createIntent(
                    info!!.packageName,
                    mainEntranceClass
                )
            )
        } else {
            Toast.makeText(this@MainActivity, "install external plugin failed", Toast.LENGTH_SHORT)
                .show()
        }
    }

    /**
     * 从assets目录中复制某文件内容
     * @param  assetFileName assets目录下的Apk源文件路径
     * @param  newFileName 复制到/data/data/package_name/files/目录下文件名
     */
    private fun copyAssetsFileToAppFiles(assetFileName: String, newFileName: String) {
        var inputStream: InputStream? = null
        var fos: FileOutputStream? = null
        val buffsize = 1024

        try {
            inputStream = this.assets.open(assetFileName)
            fos = this.openFileOutput(newFileName, Context.MODE_PRIVATE)
            val buffer = ByteArray(buffsize)
            var byteCount = inputStream.read(buffer)
            while (byteCount != -1) {
                fos!!.write(buffer, 0, byteCount)
                byteCount = inputStream.read(buffer)
            }
            fos!!.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream!!.close()
                fos!!.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        return super.onCreateView(parent, name, context, attrs)
    }

//    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
//        Log.d(TAG, "dispatchKeyEvent:" + Log.getStackTraceString(Throwable()))
//        return super.dispatchKeyEvent(event)
//    }
//
//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        Log.d(TAG, "onBackPressed:" + Log.getStackTraceString(Throwable()))
//        return super.onKeyDown(keyCode, event)
//    }
//
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//        Log.d(TAG, "onBackPressed:" + Log.getStackTraceString(Throwable()))
//    }

    override fun onPause() {
        super.onPause()
//        try {
//
//        } catch (th: Throwable) {
//            Log.d(TAG, "onPause:$th")
//        }

    }
}
