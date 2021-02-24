package com.myl.androidapkplugin.pluginlib.service

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.myl.androidapkplugin.pluginlib.core.PluginLibManager
import com.myl.androidapkplugin.pluginlib.plugin.PluginContext

object PluginServiceManager {
    private const val CONTAINER_SERVICE_NAME =
        "com.myl.plugin_core.service.ContainerService"

    // 所有已启动的service集合
    private val mAliveServicesMap = HashMap<ComponentName, PluginService>()
    // 保存service的binder
    private val mServiceBinderMap = HashMap<ComponentName, IBinder?>()
    // service对应ServiceConnection集合
    private val mServiceConnectionMap = HashMap<ComponentName, HashSet<ServiceConnection>>()
    // ServiceConnection与对应的Intent的集合
    private val mConnectionIntentMap = HashMap<ServiceConnection, Intent>()
    // 存在mAliveServicesMap中，且stopService已经调用的service集合
    private val mServiceStopCalledMap = HashSet<ComponentName>()
    // 通过startService启动起来的service集合
    private val mStartServiceSet = HashSet<ComponentName>()

    private var isPluginConnection = false

    private var startId: Int = 0
    private fun getNewStartId(): Int {
        startId++

        return startId
    }

    fun unbindPluginService(connection: ServiceConnection, pluginContext: PluginContext) {

        for ((componentName, connSet) in mServiceConnectionMap) {
            if (connSet.contains(connection)) {
                connSet.remove(connection)
                val intent = mConnectionIntentMap.remove(connection)
                connection.onServiceDisconnected(componentName)

                if (connSet.size == 0) {
                    // 已经没有任何connection了，mServiceConnectionMap移除该service数据
                    mServiceConnectionMap.remove(componentName)

                    // 所有connection都unbind了
                    mAliveServicesMap[componentName]?.onUnbind(intent!!)
                }
                isPluginConnection = true
                // 结束该service
                destroyPluginService(componentName)
            }
        }
        if (!isPluginConnection) {
            pluginContext.baseContext.unbindService(connection)
        }
    }


    fun bindPluginService(
        serviceIntent: Intent?,
        conn: ServiceConnection,
        flags: Int,
        pluginContext: PluginContext
    ): Boolean {
        val serviceInfo = PluginLibManager.findPluginServiceInfo(serviceIntent)
        serviceInfo?.apply {
            val serviceComponent = serviceIntent?.component
            val pluginService: PluginService? =
                if (!mAliveServicesMap.containsKey(serviceComponent)) {
                    // 如果还没创建，则创建,并保持
                    createPluginService(serviceComponent, pluginContext)
                } else {
                    mAliveServicesMap[serviceComponent]
                }
            var serviceIBinder: IBinder? = null
            if (!mServiceBinderMap.containsKey(serviceComponent)) {
                // 还没调用过onBinder,在这里调用
                serviceIBinder = pluginService?.onBind(serviceIntent)
                mServiceBinderMap[serviceComponent!!] = serviceIBinder
            }
            if (!mServiceConnectionMap.containsKey(serviceComponent)) {
                val connectionSet = HashSet<ServiceConnection>()
                connectionSet.add(conn)
                mServiceConnectionMap[serviceComponent!!] = connectionSet
            }
            // 如果service的bind connection集合中不包含该connection,则加入
            mServiceConnectionMap[serviceComponent]!!.add(conn)
            mConnectionIntentMap[conn] = serviceIntent!!

            conn.onServiceConnected(serviceComponent, serviceIBinder)
            return true
        }
        return pluginContext.baseContext.bindService(serviceIntent, conn, flags)
    }


    fun startPluginService(serviceIntent: Intent?, pluginContext: PluginContext): ComponentName? {
        val serviceInfo = PluginLibManager.findPluginServiceInfo(serviceIntent)
        val serviceComponent = serviceIntent?.component
        serviceInfo?.apply {
            val cachePluginService = mAliveServicesMap[serviceComponent]
            if (cachePluginService != null) {
                cachePluginService.onStartCommand(serviceIntent, 0, getNewStartId())
                return serviceComponent
            }
            val pluginService = createPluginService(serviceComponent, pluginContext)
            pluginService.onStartCommand(serviceIntent, 0, getNewStartId())
        }
        return pluginContext.baseContext.startService(serviceIntent)
    }

    private fun createPluginService(
        serviceComponent: ComponentName?,
        pluginContext: PluginContext
    ): PluginService {
        val pluginService =
            newPluginInstance(serviceComponent!!, pluginContext.mPluginClassLoader)
        fillPluginService(pluginService, pluginContext)
        pluginService.onCreate()
        mAliveServicesMap[serviceComponent] = pluginService
        return pluginService
    }

    fun stopService(serviceIntent: Intent?, pluginContext: PluginContext): Boolean {
        val serviceComponent = serviceIntent?.component
        val isPluginServiceDestroy = destroyPluginService(serviceComponent)
        return if (isPluginServiceDestroy) {
            isPluginServiceDestroy
        } else {
            pluginContext.baseContext.stopService(serviceIntent)
        }
    }

    private fun destroyPluginService(serviceComponentName: ComponentName?): Boolean {
        val destroy = {
            // 移除该service，及相关数据
            val pluginService = mAliveServicesMap.remove(serviceComponentName)
            mServiceStopCalledMap.remove(serviceComponentName)
            mServiceBinderMap.remove(serviceComponentName)
            mStartServiceSet.remove(serviceComponentName)

            // 调用service的onDestroy
            pluginService!!.onDestroy()
        }

        // 如果不是通过startService启动的，则所有connection unbind后就可以结束了
        if (!mStartServiceSet.contains(serviceComponentName)) {
            if (mServiceConnectionMap[serviceComponentName] == null) {
                // 结束该service
                destroy()
                return true
            }
        } else {
            // 如果该service，有通过startService,则必须调用过stopService且没有bind了，才能销毁
            if (mServiceStopCalledMap.contains(serviceComponentName) &&
                !mServiceConnectionMap.containsKey(serviceComponentName)
            ) {
                // 结束该service
                destroy()
                return true
            }

        }

        return false
//        if (mAliveServicesMap.containsKey(serviceComponentName)) {
//            val pluginService = mAliveServicesMap[serviceComponentName]
//            pluginService?.apply {
//                mAliveServicesMap.remove(serviceComponentName)
//                pluginService?.onDestroy()
//            }
//            return true
//        }
//        return false
    }

    private fun newPluginInstance(
        serviceComponentName: ComponentName,
        pluginClassLoader: ClassLoader?
    ): PluginService {
        val pluginServiceName = serviceComponentName?.className
        return pluginClassLoader?.loadClass(pluginServiceName)?.newInstance() as PluginService
    }

    private fun fillPluginService(
        pluginService: PluginService,
        pluginContext: PluginContext
    ) {
        pluginService.mPluginResources = pluginContext.mPluginResources
        pluginService.mPluginClassLoader = pluginContext.mPluginClassLoader
        pluginService.pluginApplication = pluginContext.pluginApplication
        pluginService.setPluginApplicationInfo(pluginContext.mApplicationInfo)
        pluginService.setHostContextAsBase(pluginContext.baseContext)
    }


}