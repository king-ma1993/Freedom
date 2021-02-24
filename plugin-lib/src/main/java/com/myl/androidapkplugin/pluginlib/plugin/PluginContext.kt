package com.myl.androidapkplugin.pluginlib.plugin

import android.content.*
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import com.myl.androidapkplugin.pluginlib.broadcast.BroadcastReceiverWapper
import com.myl.androidapkplugin.pluginlib.core.PluginLibManager
import com.myl.androidapkplugin.pluginlib.service.PluginServiceManager
import com.myl.plugin_core.util.LogUtils
import java.util.*

open class PluginContext : ContextThemeWrapper {

    companion object {
        const val TAG = "PluginContext"
    }

    var mPluginResources: Resources? = null
    var mPluginClassLoader: ClassLoader? = null

    private var mPluginLayoutInflater: LayoutInflater? = null

    var mApplicationInfo: ApplicationInfo? = null

    var pluginApplication: PluginApplication? = null

    private var mPluginTheme: Resources.Theme? = null

    private val mBroadcastReceivers = HashMap<BroadcastReceiver, BroadcastReceiverWapper>()

    constructor() {}

    override fun getPackageManager(): PackageManager {
        return super.getPackageManager()
    }


    constructor(base: Context, themeResId: Int) : super(base, themeResId) {}

    fun setPluginApplicationInfo(applicationInfo: ApplicationInfo?) {
        val copy = ApplicationInfo(applicationInfo)
        copy.metaData = null//正常通过Context获得的ApplicationInfo就没有metaData
        mApplicationInfo = copy
    }

    override fun setTheme(theme: Resources.Theme?) {
        mPluginTheme = theme
    }

    override fun getTheme(): Resources.Theme? {
        mPluginTheme?.apply {
            return mPluginTheme
        }
//        LogUtils.d(
//            TAG,
//            "getTheme:" + Log.getStackTraceString(Throwable())
//        )
        return super.getTheme()
    }

    override fun getPackageName(): String? {
        return mApplicationInfo?.packageName
    }

    override fun getResources(): Resources {
        mPluginResources?.apply {
            return this
        }
        return super.getResources()
    }


    override fun getClassLoader(): ClassLoader {
        LogUtils.d(
            TAG,
            "getClassLoader:" + Log.getStackTraceString(Throwable())
        )
        mPluginClassLoader?.apply {
            return this
        }
        return super.getClassLoader()
    }


    private fun wrapBroadcastReceiver(receiver: BroadcastReceiver?): BroadcastReceiverWapper? {
        receiver?.apply {
            synchronized(mBroadcastReceivers) {
                var broadcastReceiverWapper: BroadcastReceiverWapper? =
                    mBroadcastReceivers[receiver]
                if (broadcastReceiverWapper == null) {
                    broadcastReceiverWapper = BroadcastReceiverWapper(receiver, this@PluginContext)
                }
                mBroadcastReceivers[receiver] = broadcastReceiverWapper
                return broadcastReceiverWapper
            }
        }
        return null
    }

    override fun registerReceiver(receiver: BroadcastReceiver?, filter: IntentFilter?): Intent? {
        return super.registerReceiver(wrapBroadcastReceiver(receiver), filter)
    }

    override fun registerReceiver(
        receiver: BroadcastReceiver?,
        filter: IntentFilter?,
        flags: Int
    ): Intent? {
        return super.registerReceiver(wrapBroadcastReceiver(receiver), filter, flags)
    }

    override fun registerReceiver(
        receiver: BroadcastReceiver?,
        filter: IntentFilter?,
        broadcastPermission: String?,
        scheduler: Handler?
    ): Intent? {
        return super.registerReceiver(
            wrapBroadcastReceiver(receiver),
            filter,
            broadcastPermission,
            scheduler
        )
    }

    override fun registerReceiver(
        receiver: BroadcastReceiver?,
        filter: IntentFilter?,
        broadcastPermission: String?,
        scheduler: Handler?,
        flags: Int
    ): Intent? {
        return super.registerReceiver(
            wrapBroadcastReceiver(receiver),
            filter,
            broadcastPermission,
            scheduler,
            flags
        )
    }

    override fun getSystemService(name: String): Any? {
        if (Context.LAYOUT_INFLATER_SERVICE == name) {
            LogUtils.d(
                TAG,
                "getSystemService:" + Log.getStackTraceString(Throwable())
            )
        }
//        return super.getSystemService(name)
        if (LAYOUT_INFLATER_SERVICE == name) {
            if (mPluginLayoutInflater == null) {
                val inflater = super.getSystemService(name) as LayoutInflater
                mPluginLayoutInflater = PluginLayoutInflater.build(inflater, this)
            }
            return mPluginLayoutInflater
        }
        return super.getSystemService(name)
    }

    override fun getBaseContext(): Context {
        LogUtils.d(
            TAG,
            "getBaseContext:" + super.getBaseContext()
        )
        return super.getBaseContext()
    }


    override fun unregisterReceiver(receiver: BroadcastReceiver?) {
        synchronized(mBroadcastReceivers) {
            val broadcastReceiverWrapper = mBroadcastReceivers[receiver]
            if (broadcastReceiverWrapper != null) {
                super.unregisterReceiver(broadcastReceiverWrapper)
            } else {
                super.unregisterReceiver(receiver)
            }
        }
    }

    override fun startActivity(intent: Intent) {
        startActivity(intent, null)
    }

    override fun startActivity(intent: Intent, options: Bundle?) {
        val pluginIntent = PluginLibManager.convertPluginActivityIntent(intent)
        //todo 这里可以注释掉，到时测一下
        pluginIntent?.setExtrasClassLoader(mPluginClassLoader)
        super.startActivity(pluginIntent, options)
    }

    override fun startService(service: Intent?): ComponentName? {
        return PluginServiceManager.startPluginService(service, this)
    }

    override fun stopService(service: Intent?): Boolean {
        return PluginServiceManager.stopService(service, this)
    }

    override fun bindService(service: Intent?, conn: ServiceConnection, flags: Int): Boolean {
        return PluginServiceManager.bindPluginService(service, conn, flags, this)
    }

    override fun unbindService(conn: ServiceConnection) {
        return PluginServiceManager.unbindPluginService(conn, this)
    }

}