package com.myl.androidapkplugin.pluginlib.core;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.VersionedPackage;
import android.os.Build;

import com.myl.androidapkplugin.pluginlib.proxy.PluginPackageManager;

/**
 * 这个类不能使用kotlin来写，因为kotlin的单例和java的有区别，通过javaassist调用此类的方法时，
 * 需要调用PackageManagerRedirect.INSTANCE,
 * 而在kotlin类里调用时而是直接调用
 */
public class PackageManagerRedirect {

    private static PluginPackageManager getPluginPackageManager(ClassLoader classLoaderOfInvokeCode) {
        return PluginLibManager.INSTANCE.getPluginPackageManager(classLoaderOfInvokeCode);
    }

    public static ApplicationInfo getApplicationInfo(ClassLoader classLoaderOfInvokeCode,
                                                     String packageName, int flags) {
        return getPluginPackageManager(classLoaderOfInvokeCode).getApplicationInfo(
                packageName,
                flags
        );
    }

    public static ActivityInfo getActivityInfo(ClassLoader classLoaderOfInvokeCode,
                                               ComponentName component, int flags) {
        return getPluginPackageManager(classLoaderOfInvokeCode).getActivityInfo(component, flags);
    }

    public static PackageInfo getPackageInfo(ClassLoader classLoaderOfInvokeCode,
                                             String packageName, int flags) {
        return getPluginPackageManager(classLoaderOfInvokeCode).getPackageInfo(packageName, flags);
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static PackageInfo getPackageInfo(ClassLoader classLoaderOfInvokeCode,
                                             VersionedPackage versionedPackage,
                                             int flags)  {
        return getPluginPackageManager(classLoaderOfInvokeCode).
                getPackageInfo(versionedPackage.getPackageName(), flags);
    }

    public static ProviderInfo resolveContentProvider(ClassLoader classLoaderOfInvokeCode,
                                                      String name, int flags) {
        return getPluginPackageManager(classLoaderOfInvokeCode).resolveContentProvider(name, flags);
    }
}
