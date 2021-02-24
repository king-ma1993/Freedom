package com.myl.plugin_core.loader

import android.util.Log
import com.myl.plugin_core.info.PluginInfo
import dalvik.system.DexClassLoader

class PluginClassLoader(pluginInfo: PluginInfo, parent: ClassLoader) :
    DexClassLoader(
        pluginInfo.localPluginPath,
        pluginInfo.getOdexDirPath(),
        pluginInfo.getLibSoPath(),
        parent
    ) {

    companion object {
        const val TAG = "PluginClassLoader"
    }


    // copied from https://android.googlesource.com/platform/libcore/+/master/dalvik/src/main/java/dalvik/system/DelegateLastClassLoader.java
    @Throws(ClassNotFoundException::class)
    public override fun loadClass(name: String, resolve: Boolean): Class<*>? {
        // First, check whether the class has already been loaded. Return it if that's the
        // case.
        val cl = findLoadedClass(name)
        if (cl != null) {
            Log.d(TAG, "findLoadedClass name:$name")
            return cl
        }
        // Next, check whether the class in question is present in the boot classpath.
//        try {
//            return Object::class.java.classLoader!!.loadClass(name)
//        } catch (ignored: ClassNotFoundException) {
//        }

        // Next, check whether the class in question is present in the dexPath that this classloader
        // operates on, or its shared libraries.
        val fromSuper: ClassNotFoundException
        try {
            Log.d(TAG, "loadClass name:$name")
            return findClass(name)
        } catch (ex: ClassNotFoundException) {
            fromSuper = ex
            ex.printStackTrace()
        }

        // Finally, check whether the class in question is present in the parent classloader.
        try {
            return parent.loadClass(name)
        } catch (th: Throwable) {
//            th.printStackTrace()
        } catch (cnfe: ClassNotFoundException) {
            // The exception we're catching here is the CNFE thrown by the parent of this
            // classloader. However, we would like to throw a CNFE that provides details about
            // the class path / list of dex files associated with *this* classloader, so we choose
            // to throw the exception thrown from that lookup.
            throw fromSuper
        }

//        try {
//            val systemClassLoader = parent.parent
//            Log.d(TAG, "loadClass systemClassLoader:$systemClassLoader")
//            if (systemClassLoader != null) {
//                return systemClassLoader.loadClass(name)
//            }
//        } catch (th: Throwable) {
//            th.printStackTrace()
//        }
        return null
    }

    override fun findClass(name: String?): Class<*> {
        return super.findClass(name)
    }

}