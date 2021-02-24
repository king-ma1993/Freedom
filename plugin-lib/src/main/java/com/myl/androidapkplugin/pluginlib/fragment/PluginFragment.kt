package com.myl.androidapkplugin.pluginlib.fragment

import android.animation.Animator
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.Fragment
import android.app.LoaderManager
import android.app.SharedElementCallback
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.transition.Transition
import android.util.AttributeSet
import android.view.*
import com.myl.androidapkplugin.pluginlib.activity.PluginActivity
import com.myl.plugin_core.activity.PluginContainerActivity
import java.io.FileDescriptor
import java.io.PrintWriter
import java.lang.reflect.Constructor
import java.util.HashMap

open class PluginFragment {

    protected var mContainerFragment: IContainerFragment? = null
    private val constructorMap = HashMap<String, Constructor<*>>()
    private var mAttachedContext: Context? = null

    /**
     * 标志当前Fragment是否由app自己的代码创建的
     */
    protected var mIsAppCreateFragment: Boolean = false

    init {
        mContainerFragment = instantiateContainerFragment(this)
        mContainerFragment?.bindPluginFragment(this)
        mIsAppCreateFragment = true

    }

    open fun setArguments(args: Bundle) {
        if (mIsAppCreateFragment) {
            mContainerFragment?.arguments = args
        }
    }

    open fun getArguments(): Bundle? {
        return mContainerFragment?.arguments
    }


    open fun getActivity(): PluginActivity? {
        if (mAttachedContext == null) {
            return null
        } else if (mAttachedContext is PluginActivity) {
            return mAttachedContext as PluginActivity
        } else {
            val activity = mContainerFragment?.activity as PluginContainerActivity
            return activity?.getPluginActivity() as PluginActivity
        }
    }


    open fun getContainerFragment(): IContainerFragment {
        if (mContainerFragment == null) {
            throw NullPointerException(this.javaClass.name + " mContainerFragment == null")
        }
        return mContainerFragment!!
    }

    private fun instantiateContainerFragment(pluginFragment: PluginFragment): IContainerFragment {
        val pluginFragmentClassname = pluginFragment.javaClass.name
        val containerFragmentClassName =
            pluginFragmentClassname.substring(0, pluginFragmentClassname.length - 1)
        var constructor: Constructor<*>? = constructorMap[containerFragmentClassName]
        if (constructor == null) {
            val pluginClassLoader = pluginFragment.javaClass.classLoader
            try {
                val aClass = pluginClassLoader?.loadClass(containerFragmentClassName)
                constructor = aClass?.getConstructor()
                constructorMap[containerFragmentClassName] = constructor!!
            } catch (e: Exception) {
                throw Fragment.InstantiationException("无法构造$containerFragmentClassName", e)
            }

        }
        try {
            return IContainerFragment::class.java.cast(constructor.newInstance())!!
        } catch (e: Exception) {
            throw Fragment.InstantiationException("无法构造$containerFragmentClassName", e)
        }

    }

    open fun getResources(): Resources? {
        checkNotNull(mAttachedContext) { "Fragment $this not attached to Activity" }
        return mAttachedContext?.resources
    }


    open fun onAttach(context: Context?) {
        context?.apply {
            mAttachedContext = context
        }
    }

    open fun setInitialSavedState(state: Fragment.SavedState) {

    }


    open fun setTargetFragment(fragment: Fragment, requestCode: Int) {

    }


    open fun getContext(): Context? {
        return mAttachedContext
    }

    open fun setContainerFragment(containerFragment: IContainerFragment?) {
        mIsAppCreateFragment = false
        mContainerFragment?.unbindPluginFragment()
        mContainerFragment = containerFragment
    }

    open fun onHiddenChanged(hidden: Boolean) {
        mContainerFragment?.superOnHiddenChanged(hidden)
    }


    open fun setRetainInstance(retain: Boolean) {
        mContainerFragment?.superSetRetainInstance(retain)
    }


    open fun setHasOptionsMenu(hasMenu: Boolean) {
        mContainerFragment?.superSetHasOptionsMenu(hasMenu)
    }


    open fun setMenuVisibility(menuVisible: Boolean) {
        mContainerFragment?.superSetMenuVisibility(menuVisible)
    }


    open fun setUserVisibleHint(isVisibleToUser: Boolean) {
        mContainerFragment?.superSetUserVisibleHint(isVisibleToUser)
    }


    open fun getUserVisibleHint(): Boolean {
        return mContainerFragment?.userVisibleHint ?: false
    }


    open fun getLoaderManager(): LoaderManager? {
        return null
    }


    open fun startActivity(intent: Intent) {
        mAttachedContext?.startActivity(intent)
    }


    @TargetApi(16)
    open fun startActivity(intent: Intent, options: Bundle) {
        mAttachedContext?.startActivity(intent, options)
    }


    open fun startActivityForResult(intent: Intent, requestCode: Int) {

    }


    open fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle) {

    }


    @Throws(IntentSender.SendIntentException::class)
    open fun startIntentSenderForResult(
        intent: IntentSender,
        requestCode: Int,
        fillInIntent: Intent?,
        flagsMask: Int,
        flagsValues: Int,
        extraFlags: Int,
        options: Bundle
    ) {

    }

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

    }


    open fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

    }


    open fun shouldShowRequestPermissionRationale(permission: String): Boolean {
        return false
    }


    open fun onGetLayoutInflater(savedInstanceState: Bundle): LayoutInflater? {
        return null
    }


    open fun onInflate(attrs: AttributeSet, savedInstanceState: Bundle?) {

    }


    open fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {

    }

    open fun onInflate(activity: Activity, attrs: AttributeSet, savedInstanceState: Bundle?) {

    }


    open fun onAttachFragment(childFragment: Fragment) {

    }


    open fun onAttach(activity: PluginActivity) {
        mAttachedContext = activity
    }


    open fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
        return null
    }


    open fun onCreate(savedInstanceState: Bundle?) {

    }


    open fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return null
    }


    open fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }


    open fun getView(): View? {
        return mContainerFragment?.view
    }


    open fun onActivityCreated(savedInstanceState: Bundle?) {

    }


    open fun onViewStateRestored(savedInstanceState: Bundle?) {

    }


    open fun onStart() {

    }


    open fun onResume() {

    }


    open fun onSaveInstanceState(outState: Bundle) {

    }


    open fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean, newConfig: Configuration) {

    }


    open fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean) {

    }


    open fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {

    }


    open fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean) {

    }


    open fun onConfigurationChanged(newConfig: Configuration) {

    }


    open fun onPause() {

    }


    open fun onStop() {

    }


    open fun onLowMemory() {

    }


    open fun onTrimMemory(level: Int) {

    }


    open fun onDestroyView() {

    }


    open fun onDestroy() {

    }


    open fun onDetach() {

    }


    open fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

    }


    open fun onPrepareOptionsMenu(menu: Menu) {

    }


    open fun onDestroyOptionsMenu() {

    }


    open fun onOptionsItemSelected(item: MenuItem): Boolean {
        return false
    }


    open fun onOptionsMenuClosed(menu: Menu) {

    }


    open fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo
    ) {

    }


    open fun registerForContextMenu(view: View) {

    }


    open fun unregisterForContextMenu(view: View) {

    }


    open fun onContextItemSelected(item: MenuItem): Boolean {
        return false
    }


    open fun setEnterSharedElementCallback(callback: SharedElementCallback) {

    }


    open fun setExitSharedElementCallback(callback: SharedElementCallback) {

    }


    open fun setEnterTransition(transition: Transition?) {

    }


    open fun getEnterTransition(): Transition? {
        return null
    }


    open fun setReturnTransition(transition: Transition?) {

    }


    open fun getReturnTransition(): Transition? {
        return null
    }


    open fun setExitTransition(transition: Transition?) {

    }


    open fun getExitTransition(): Transition? {
        return null
    }


    open fun setReenterTransition(transition: Transition?) {

    }


    open fun getReenterTransition(): Transition? {
        return null
    }


    open fun setSharedElementEnterTransition(transition: Transition?) {

    }


    open fun getSharedElementEnterTransition(): Transition? {
        return null
    }


    open fun setSharedElementReturnTransition(transition: Transition?) {

    }


    open fun getSharedElementReturnTransition(): Transition? {
        return null
    }


    open fun setAllowEnterTransitionOverlap(allow: Boolean) {

    }


    open fun getAllowEnterTransitionOverlap(): Boolean {
        return false
    }


    open fun setAllowReturnTransitionOverlap(allow: Boolean) {

    }


    open fun getAllowReturnTransitionOverlap(): Boolean {
        return false
    }


    open fun postponeEnterTransition() {

    }


    open fun startPostponedEnterTransition() {

    }


    open fun dump(prefix: String, fd: FileDescriptor, writer: PrintWriter, args: Array<String>) {

    }

    open fun isAdded(): Boolean {
        return mContainerFragment?.isAdded ?: false
    }

    open fun isDetached(): Boolean {
        return mContainerFragment?.isDetached ?: false
    }

    open fun isRemoving(): Boolean {
        return mContainerFragment?.isRemoving ?: false
    }

    open fun isInLayout(): Boolean {
        return mContainerFragment?.isInLayout ?: false
    }

    open fun isResumed(): Boolean {
        return mContainerFragment?.isResumed ?: false
    }

    open fun isVisible(): Boolean {
        return mContainerFragment?.isVisible ?: false
    }

    open fun isHidden(): Boolean {
        return mContainerFragment?.isHidden ?: false
    }

    open fun getId(): Int? {
        return mContainerFragment?.getId()
    }

    open fun getTag(): String? {
        return mContainerFragment?.getTag()
    }

    open fun getText(resId: Int): CharSequence? {
        return getResources()?.getText(resId)
    }

    open fun getString(resId: Int): String? {
        return getResources()?.getString(resId)
    }

    open fun getString(resId: Int, vararg formatArgs: Any): String? {
        return getResources()?.getString(resId, *formatArgs)
    }

    open fun requestPermissions(permissions: Array<String>, requestCode: Int) {
        mContainerFragment?.requestPermissions(permissions, requestCode)
    }

    @SuppressLint("NewApi")
    open fun getParentFragment(): PluginFragment {
        val parentFragment = mContainerFragment?.asFragment()?.parentFragment
        return (parentFragment as IContainerFragment).pluginFragment
    }


}