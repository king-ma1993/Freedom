package com.myl.androidapkplugin.pluginlib.fragment

import android.animation.Animator
import android.app.Activity
import android.app.Fragment
import android.app.LoaderManager
import android.app.SharedElementCallback
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.transition.Transition
import android.util.AttributeSet
import android.view.*
import com.myl.androidapkplugin.pluginlib.activity.PluginActivity
import com.myl.plugin_core.activity.PluginContainerActivity
import java.io.FileDescriptor
import java.io.PrintWriter
import java.lang.reflect.Constructor
import java.util.*

open class ContainerFragment : Fragment(),
    IContainerFragment {
    private var init = false

    private var mPluginFragment: PluginFragment? = null

    private val constructorMap = HashMap<String, Constructor<*>>()

    private var mDestroyed: Boolean = false

    override fun asFragment(): Fragment {
        return this
    }

    override fun getPluginFragment(): PluginFragment? {
        return mPluginFragment
    }

    override fun bindPluginFragment(pluginFragment: PluginFragment?) {
        init = true
        mPluginFragment = pluginFragment
    }

    override fun unbindPluginFragment() {
        init = false
        mPluginFragment = null
    }

    override fun onAttach(context: Context?) {
        initPluginFragment(context)
        super.onAttach(context)
        if (context is PluginContainerActivity) {
            val pluginActivity = context.getPluginActivity() as PluginActivity
            mPluginFragment?.onAttach(pluginActivity)
        }

    }

    private fun initPluginFragment(context: Context?) {
        if (init) {
            return
        }
        init = true

        onBindPluginFragment(context)
    }

    private fun onBindPluginFragment(context: Context?) {
        mPluginFragment = instantiatePluginFragment(this, context)
        mPluginFragment?.setContainerFragment(this)
    }

    private fun instantiatePluginFragment(
        containerFragment: ContainerFragment,
        context: Context?
    ): PluginFragment? {
        val pluginFragmentClassName = containerFragment.javaClass.name + "_"
        var constructor: Constructor<*>? = constructorMap.get(pluginFragmentClassName)
        if (constructor == null) {
            val containerActivity = context as PluginContainerActivity
            val pluginActivity = containerActivity.getPluginActivity() as PluginActivity
            pluginActivity?.apply {
                val pluginClassLoader = pluginActivity.classLoader
                try {
                    val aClass = pluginClassLoader.loadClass(pluginFragmentClassName)
                    constructor = aClass.getConstructor()
                    constructorMap[pluginFragmentClassName] = constructor!!
                } catch (e: Exception) {
                    throw InstantiationException("无法构造$pluginFragmentClassName", e)
                }

            }
        }
        try {
            return PluginFragment::class.java.cast(constructor!!.newInstance())
        } catch (e: Exception) {
            throw InstantiationException("无法构造$pluginFragmentClassName", e)
        }

    }

    override fun setInitialSavedState(state: Fragment.SavedState) {
        mPluginFragment?.setInitialSavedState(state)
    }

    override fun setTargetFragment(fragment: Fragment, requestCode: Int) {
        mPluginFragment?.setTargetFragment(fragment, requestCode)
    }

    override fun getContext(): Context? {
        return mPluginFragment?.getContext()
    }


    override fun onHiddenChanged(hidden: Boolean) {
        mPluginFragment?.onHiddenChanged(hidden)
    }

    override fun setRetainInstance(retain: Boolean) {
        mPluginFragment?.setRetainInstance(retain)
    }

    override fun setHasOptionsMenu(hasMenu: Boolean) {
        mPluginFragment?.setHasOptionsMenu(hasMenu)
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        mPluginFragment?.setMenuVisibility(menuVisible)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        mPluginFragment?.setUserVisibleHint(isVisibleToUser)
    }

    override fun getLoaderManager(): LoaderManager? {
        return mPluginFragment?.getLoaderManager()
    }

    override fun startActivity(intent: Intent) {
        mPluginFragment?.startActivity(intent)
    }

    override fun startActivity(intent: Intent, options: Bundle) {
        mPluginFragment?.startActivity(intent, options)
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        mPluginFragment?.startActivityForResult(intent, requestCode)
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle) {
        mPluginFragment?.startActivityForResult(intent, requestCode, options)
    }

    @Throws(IntentSender.SendIntentException::class)
    override fun startIntentSenderForResult(
        intent: IntentSender,
        requestCode: Int,
        fillInIntent: Intent?,
        flagsMask: Int,
        flagsValues: Int,
        extraFlags: Int,
        options: Bundle
    ) {
        mPluginFragment?.startIntentSenderForResult(
            intent,
            requestCode,
            fillInIntent,
            flagsMask,
            flagsValues,
            extraFlags,
            options
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        mPluginFragment?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        mPluginFragment?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun shouldShowRequestPermissionRationale(permission: String): Boolean {
        return mPluginFragment?.shouldShowRequestPermissionRationale(permission) ?: false
    }

    @Deprecated("")
    override fun onInflate(attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(attrs, savedInstanceState)
        mPluginFragment?.onInflate(attrs, savedInstanceState)
    }

    override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        initPluginFragment(context)
        super.onInflate(context, attrs, savedInstanceState)
        mPluginFragment?.onInflate(context, attrs, savedInstanceState)
    }

    @Deprecated("")
    override fun onInflate(activity: Activity, attrs: AttributeSet, savedInstanceState: Bundle?) {
        initPluginFragment(activity)
        super.onInflate(activity, attrs, savedInstanceState)
        mPluginFragment?.onInflate(activity, attrs, savedInstanceState)
    }

    override fun onAttachFragment(childFragment: Fragment) {
        mPluginFragment?.onAttachFragment(childFragment)
    }

    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
        return mPluginFragment?.onCreateAnimator(transit, enter, nextAnim)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPluginFragment?.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mPluginFragment?.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mPluginFragment?.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPluginFragment?.onActivityCreated(savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        mPluginFragment?.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mPluginFragment?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mPluginFragment?.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mPluginFragment?.onSaveInstanceState(outState)
    }

    override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean, newConfig: Configuration) {
        mPluginFragment?.onMultiWindowModeChanged(isInMultiWindowMode, newConfig)
    }

    @Deprecated("")
    override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean) {
        mPluginFragment?.onMultiWindowModeChanged(isInMultiWindowMode)
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        mPluginFragment?.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
    }

    @Deprecated("")
    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean) {
        mPluginFragment?.onPictureInPictureModeChanged(isInPictureInPictureMode)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mPluginFragment?.onConfigurationChanged(newConfig)
    }

    override fun onPause() {
        super.onPause()
        mPluginFragment?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mPluginFragment?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mPluginFragment?.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        mPluginFragment?.onTrimMemory(level)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPluginFragment?.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPluginFragment?.onDestroy()
        mDestroyed = true
    }

    override fun onDetach() {
        super.onDetach()
        mPluginFragment?.onDetach()
        if (mDestroyed) {
            onUnbindPluginFragment()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        mPluginFragment?.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        mPluginFragment?.onPrepareOptionsMenu(menu)
    }

    override fun onDestroyOptionsMenu() {
        mPluginFragment?.onDestroyOptionsMenu()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return mPluginFragment?.onOptionsItemSelected(item) ?: false
    }

    override fun onOptionsMenuClosed(menu: Menu) {
        mPluginFragment?.onOptionsMenuClosed(menu)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo
    ) {
        mPluginFragment?.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun registerForContextMenu(view: View) {
        mPluginFragment?.registerForContextMenu(view)
    }

    override fun unregisterForContextMenu(view: View) {
        mPluginFragment?.unregisterForContextMenu(view)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return mPluginFragment?.onContextItemSelected(item) ?: false
    }

    override fun setEnterSharedElementCallback(callback: SharedElementCallback) {
        mPluginFragment?.setEnterSharedElementCallback(callback)
    }

    override fun setExitSharedElementCallback(callback: SharedElementCallback) {
        mPluginFragment?.setExitSharedElementCallback(callback)
    }

    override fun setEnterTransition(transition: Transition?) {
        mPluginFragment?.setEnterTransition(transition)
    }

    override fun getEnterTransition(): Transition? {
        return mPluginFragment?.getEnterTransition()
    }

    override fun setReturnTransition(transition: Transition?) {
        mPluginFragment?.setReturnTransition(transition)
    }

    override fun getReturnTransition(): Transition? {
        return mPluginFragment?.getReturnTransition()
    }

    override fun setExitTransition(transition: Transition?) {
        mPluginFragment?.setExitTransition(transition)
    }

    override fun getExitTransition(): Transition? {
        return mPluginFragment?.getExitTransition()
    }

    override fun setReenterTransition(transition: Transition?) {
        mPluginFragment?.setReenterTransition(transition)
    }

    override fun getReenterTransition(): Transition? {
        return mPluginFragment?.getReenterTransition()
    }

    override fun setSharedElementEnterTransition(transition: Transition?) {
        mPluginFragment?.setSharedElementEnterTransition(transition)
    }

    override fun getSharedElementEnterTransition(): Transition? {
        return mPluginFragment?.getSharedElementEnterTransition()
    }

    override fun setSharedElementReturnTransition(transition: Transition?) {
        mPluginFragment?.setSharedElementReturnTransition(transition)
    }

    override fun getSharedElementReturnTransition(): Transition? {
        return mPluginFragment?.getSharedElementReturnTransition()
    }

    override fun setAllowEnterTransitionOverlap(allow: Boolean) {
        mPluginFragment?.setAllowEnterTransitionOverlap(allow) ?: false
    }

    override fun getAllowEnterTransitionOverlap(): Boolean {
        return mPluginFragment?.getAllowEnterTransitionOverlap() ?: false
    }

    override fun setAllowReturnTransitionOverlap(allow: Boolean) {
        mPluginFragment?.setAllowReturnTransitionOverlap(allow) ?: false
    }

    override fun getAllowReturnTransitionOverlap(): Boolean {
        return mPluginFragment?.getAllowReturnTransitionOverlap() ?: false
    }

    override fun postponeEnterTransition() {
        mPluginFragment?.postponeEnterTransition()
    }

    override fun startPostponedEnterTransition() {
        mPluginFragment?.startPostponedEnterTransition()
    }

    override fun dump(
        prefix: String,
        fd: FileDescriptor,
        writer: PrintWriter,
        args: Array<String>
    ) {
        mPluginFragment?.dump(prefix, fd, writer, args)
    }

    override fun superSetUserVisibleHint(isVisibleToUser: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            super.setUserVisibleHint(isVisibleToUser)
        }
    }

    override fun superSetRetainInstance(retain: Boolean) {
        super.setRetainInstance(retain)
    }

    override fun superSetHasOptionsMenu(hasMenu: Boolean) {
        super.setHasOptionsMenu(hasMenu)
    }

    override fun superSetMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
    }

    override fun superOnHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
    }

    private fun onUnbindPluginFragment() {
        mPluginFragment?.setContainerFragment(null)
        mPluginFragment = null
    }

}