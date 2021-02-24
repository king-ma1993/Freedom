package com.myl.gradle_plugin.transform.replace

object Constants {
    const val fragmentClassname = "android.app.Fragment"
    const val fragmentManagerClassname = "android.app.FragmentManager"
    const val fragmentTransactionClassname = "android.app.FragmentTransaction"
    const val dialogFragmentClassname = "android.app.DialogFragment"




    const val replace_applicationName = "com.myl.androidapkplugin.pluginlib.plugin.PluginApplication"
    const val replace_lifecycleCallbackName = "com.myl.androidapkplugin.pluginlib.activity.PluginActivityLifecycleCallbacks"
    const val replace_activityName = "com.myl.androidapkplugin.pluginlib.activity.PluginActivity"
    const val replace_fragmentManagerName = "com.myl.androidapkplugin.pluginlib.fragment.PluginFragmentManager"
    const val replace_fragmentName = "com.myl.androidapkplugin.pluginlib.fragment.PluginFragment"
    const val replace_FragmentTransactionName = "com.myl.androidapkplugin.pluginlib.fragment.PluginFragmentTransaction"
    const val notReplace_wrapper = "com.myl.androidapkplugin.pluginlib.activity.PluginActivityLifecycleCallbacks\$Wrapper"

    const val pluginDialogFragment = "com.myl.androidapkplugin.pluginlib.fragment.PluginDialogFragment"
    const val containerFragment = "com.myl.androidapkplugin.pluginlib.fragment.ContainerFragment"
    const val containerdialogfragment = "com.myl.androidapkplugin.pluginlib.fragment.ContainerDialogFragment"
    const val iContainerdialogfragment = "com.myl.androidapkplugin.pluginlib.fragment.IContainerFragment"

    const val baseActivityDelegate = "com.myl.androidapkplugin.pluginlib.delegate.BaseActivityDelegate"
    const val basePluginActivity = "com.myl.androidapkplugin.pluginlib.activity.BasePluginActivity"

    const val pluginActivityDelegateProxy = "com.myl.androidapkplugin.pluginlib.delegate.PluginActivityDelegateProxy"

}