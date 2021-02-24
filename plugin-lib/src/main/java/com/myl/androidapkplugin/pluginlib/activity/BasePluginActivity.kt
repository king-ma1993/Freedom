package com.myl.androidapkplugin.pluginlib.activity

import android.annotation.SuppressLint
import android.app.*
import android.app.assist.AssistContent
import android.content.*
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.media.session.MediaController
import android.net.Uri
import android.os.Bundle
import android.os.CancellationSignal
import android.os.PersistableBundle
import android.transition.Scene
import android.transition.TransitionManager
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.accessibility.AccessibilityEvent
import android.widget.Toolbar
import com.myl.androidapkplugin.pluginlib.fragment.PluginFragmentManager
import com.myl.androidapkplugin.pluginlib.plugin.PluginApplication
import com.myl.androidapkplugin.pluginlib.plugin.PluginContext
import com.myl.plugin_core.proxy.HostActivityInterface
import com.myl.plugin_core.util.LogUtils
import java.io.FileDescriptor
import java.io.PrintWriter
import java.util.function.Consumer

/**
 * BasePluginActivity GeneratedPluginActivity
 */
@SuppressLint("NullableProblems", "deprecation", "NewApi")
open abstract class BasePluginActivity : PluginContext(), Window.Callback, KeyEvent.Callback {

    companion object {
        const val TAG = "BasePluginActivity"
    }

    //设置为了PluginContainerActivity
    var hostActivityDelegator: HostActivityInterface? = null


    open fun isChangingConfigurations(): Boolean {
        return hostActivityDelegator?.superIsChangingConfigurations() ?: false
    }

    open fun finish() {
        hostActivityDelegator?.superFinish()
    }

    open fun startActivityFromChild(arg0: Activity, arg1: Intent, arg2: Int) {
        hostActivityDelegator?.superStartActivityFromChild(arg0, arg1, arg2)
    }

    open fun getLayoutInflater(): LayoutInflater? {
        return hostActivityDelegator?.superGetLayoutInflater()
    }

    open fun recreate() {
        hostActivityDelegator?.superRecreate()
    }

    open fun getCallingActivity(): ComponentName? {
        return hostActivityDelegator?.superGetCallingActivity()
    }

    override fun onKeyMultiple(arg0: Int, arg1: Int, arg2: KeyEvent): Boolean {
        return hostActivityDelegator!!.superOnKeyMultiple(arg0, arg1, arg2)
    }

    override fun onKeyDown(arg0: Int, arg1: KeyEvent): Boolean {
        return hostActivityDelegator!!.superOnKeyDown(arg0, arg1)
    }

    override fun onKeyUp(arg0: Int, arg1: KeyEvent): Boolean {
        return hostActivityDelegator!!.superOnKeyUp(arg0, arg1)
    }

    override fun onKeyLongPress(arg0: Int, arg1: KeyEvent): Boolean {
        return hostActivityDelegator!!.superOnKeyLongPress(arg0, arg1)
    }

    open fun onRestoreInstanceState(arg0: Bundle?, arg1: PersistableBundle?) {
        hostActivityDelegator!!.superOnRestoreInstanceState(arg0, arg1)
    }

    open fun onPostCreate(arg0: Bundle?, arg1: PersistableBundle?) {
        hostActivityDelegator?.superOnPostCreate(arg0, arg1)
    }

    open fun onStateNotSaved() {
        hostActivityDelegator?.superOnStateNotSaved()
    }

    open fun onTopResumedActivityChanged(arg0: Boolean) {
        hostActivityDelegator?.superOnTopResumedActivityChanged(arg0)
    }

    open fun onLocalVoiceInteractionStarted() {
        hostActivityDelegator?.superOnLocalVoiceInteractionStarted()
    }

    open fun onLocalVoiceInteractionStopped() {
        hostActivityDelegator?.superOnLocalVoiceInteractionStopped()
    }

    open fun onSaveInstanceState(arg0: Bundle, arg1: PersistableBundle) {
        hostActivityDelegator?.superOnSaveInstanceState(arg0, arg1)
    }

    open fun onCreateThumbnail(arg0: Bitmap, arg1: Canvas): Boolean {
        return hostActivityDelegator!!.superOnCreateThumbnail(arg0, arg1)
    }

    open fun onCreateDescription(): CharSequence? {
        return hostActivityDelegator?.superOnCreateDescription()
    }

    open fun onProvideAssistData(arg0: Bundle) {
        hostActivityDelegator?.superOnProvideAssistData(arg0)
    }

    open fun onProvideAssistContent(arg0: AssistContent) {
        hostActivityDelegator?.superOnProvideAssistContent(arg0)
    }

    open fun onGetDirectActions(arg0: CancellationSignal, arg1: Consumer<List<DirectAction>>) {
        hostActivityDelegator?.superOnGetDirectActions(arg0, arg1)
    }

    open fun onPerformDirectAction(
        arg0: String, arg1: Bundle, arg2: CancellationSignal,
        arg3: Consumer<Bundle>
    ) {
        hostActivityDelegator?.superOnPerformDirectAction(arg0, arg1, arg2, arg3)
    }

    open fun onCreate(arg0: Bundle?, arg1: PersistableBundle?) {
        hostActivityDelegator?.superOnCreate(arg0, arg1)
    }

    override fun onActionModeStarted(arg0: ActionMode) {
        hostActivityDelegator?.superOnActionModeStarted(arg0)
    }

    override fun onWindowStartingActionMode(arg0: ActionMode.Callback): ActionMode? {
        return hostActivityDelegator?.superOnWindowStartingActionMode(arg0)
    }

    override fun onWindowStartingActionMode(arg0: ActionMode.Callback, arg1: Int): ActionMode? {
        return hostActivityDelegator?.superOnWindowStartingActionMode(arg0, arg1)
    }

    override fun onActionModeFinished(arg0: ActionMode) {
        hostActivityDelegator?.superOnActionModeFinished(arg0)
    }

    open fun onAttachFragment(arg0: Fragment) {
        hostActivityDelegator?.superOnAttachFragment(arg0)
    }

    open fun onRetainNonConfigurationInstance(): Any? {
        return hostActivityDelegator?.superOnRetainNonConfigurationInstance()
    }

    open fun onTrimMemory(arg0: Int) {
        hostActivityDelegator?.superOnTrimMemory(arg0)
    }

    open fun onLowMemory() {
        hostActivityDelegator?.superOnLowMemory()
    }

    override fun onProvideKeyboardShortcuts(
        arg0: List<KeyboardShortcutGroup>,
        arg1: Menu?,
        arg2: Int
    ) {
        hostActivityDelegator?.superOnProvideKeyboardShortcuts(arg0, arg1, arg2)
    }

    open fun onMultiWindowModeChanged(arg0: Boolean) {
        hostActivityDelegator?.superOnMultiWindowModeChanged(arg0)
    }

    open fun onMultiWindowModeChanged(arg0: Boolean, arg1: Configuration) {
        hostActivityDelegator?.superOnMultiWindowModeChanged(arg0, arg1)
    }

    open fun onBackPressed() {
        hostActivityDelegator?.superOnBackPressed()
    }

    open fun onConfigurationChanged(arg0: Configuration) {
        hostActivityDelegator?.superOnConfigurationChanged(arg0)
    }

    open fun onPictureInPictureModeChanged(arg0: Boolean) {
        hostActivityDelegator?.superOnPictureInPictureModeChanged(arg0)
    }

    open fun onPictureInPictureModeChanged(arg0: Boolean, arg1: Configuration) {
        hostActivityDelegator?.superOnPictureInPictureModeChanged(arg0, arg1)
    }

    open fun onKeyShortcut(arg0: Int, arg1: KeyEvent): Boolean? {
        return hostActivityDelegator?.superOnKeyShortcut(arg0, arg1)
    }

    open fun onTouchEvent(arg0: MotionEvent): Boolean? {
        return hostActivityDelegator?.superOnTouchEvent(arg0)
    }

    open fun onTrackballEvent(arg0: MotionEvent): Boolean? {
        return hostActivityDelegator?.superOnTrackballEvent(arg0)
    }

    open fun onGenericMotionEvent(arg0: MotionEvent): Boolean? {
        return hostActivityDelegator?.superOnGenericMotionEvent(arg0)
    }

    open fun onUserInteraction() {
        hostActivityDelegator?.superOnUserInteraction()
    }

    override fun onWindowAttributesChanged(arg0: WindowManager.LayoutParams) {
        hostActivityDelegator?.superOnWindowAttributesChanged(arg0)
    }

    override fun onContentChanged() {
        hostActivityDelegator?.superOnContentChanged()
    }

    override fun onWindowFocusChanged(arg0: Boolean) {
        hostActivityDelegator?.superOnWindowFocusChanged(arg0)
    }

    override fun onAttachedToWindow() {
        hostActivityDelegator?.superOnAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        hostActivityDelegator?.superOnDetachedFromWindow()
    }

    override fun onCreatePanelView(arg0: Int): View? {
        return hostActivityDelegator?.superOnCreatePanelView(arg0)
    }

    override fun onCreatePanelMenu(arg0: Int, arg1: Menu): Boolean {
        return hostActivityDelegator!!.superOnCreatePanelMenu(arg0, arg1)
    }

    override fun onPreparePanel(arg0: Int, arg1: View?, arg2: Menu): Boolean {
        return hostActivityDelegator!!.superOnPreparePanel(arg0, arg1, arg2)
    }

    override fun onMenuOpened(arg0: Int, menu: Menu?): Boolean {
        LogUtils.d(
            TAG,
            "onMenuOpened menu:$menu trace:" + Log.d(TAG, Log.getStackTraceString(Throwable()))
        )
        if (menu == null) {
            return false
        }
        return hostActivityDelegator!!.superOnMenuOpened(arg0, menu!!)
    }

    override fun onMenuItemSelected(arg0: Int, arg1: MenuItem): Boolean {
        return hostActivityDelegator!!.superOnMenuItemSelected(arg0, arg1)
    }

    override fun onPanelClosed(arg0: Int, arg1: Menu) {
        hostActivityDelegator?.superOnPanelClosed(arg0, arg1)
    }

    open fun onCreateOptionsMenu(arg0: Menu): Boolean {
        return hostActivityDelegator!!.superOnCreateOptionsMenu(arg0)
    }

    open fun onPrepareOptionsMenu(arg0: Menu): Boolean {
        return hostActivityDelegator!!.superOnPrepareOptionsMenu(arg0)
    }

    open fun onOptionsItemSelected(arg0: MenuItem): Boolean {
        return hostActivityDelegator!!.superOnOptionsItemSelected(arg0)
    }

    open fun onNavigateUp(): Boolean {
        return hostActivityDelegator!!.superOnNavigateUp()
    }

    abstract fun onNavigateUpFromChild(arg0: PluginActivity): Boolean

    open fun onCreateNavigateUpTaskStack(arg0: TaskStackBuilder) {
        hostActivityDelegator?.superOnCreateNavigateUpTaskStack(arg0)
    }

    open fun onPrepareNavigateUpTaskStack(arg0: TaskStackBuilder) {
        hostActivityDelegator?.superOnPrepareNavigateUpTaskStack(arg0)
    }

    open fun onOptionsMenuClosed(arg0: Menu) {
        hostActivityDelegator?.superOnOptionsMenuClosed(arg0)
    }

    open fun onCreateContextMenu(arg0: ContextMenu, arg1: View, arg2: ContextMenu.ContextMenuInfo) {
        hostActivityDelegator?.superOnCreateContextMenu(arg0, arg1, arg2)
    }

    fun onContextItemSelected(arg0: MenuItem): Boolean {
        return hostActivityDelegator!!.superOnContextItemSelected(arg0)
    }

    open fun onContextMenuClosed(arg0: Menu) {
        hostActivityDelegator?.superOnContextMenuClosed(arg0)
    }

    override fun onSearchRequested(arg0: SearchEvent?): Boolean {
        return hostActivityDelegator!!.superOnSearchRequested(arg0)
    }

    override fun onSearchRequested(): Boolean {
        return hostActivityDelegator!!.superOnSearchRequested()
    }

    open fun onRequestPermissionsResult(arg0: Int, arg1: Array<String>, arg2: IntArray) {
        hostActivityDelegator?.superOnRequestPermissionsResult(arg0, arg1, arg2)
    }

    open fun onProvideReferrer(): Uri? {
        return hostActivityDelegator?.superOnProvideReferrer()
    }

    open fun onActivityReenter(arg0: Int, arg1: Intent) {
        hostActivityDelegator?.superOnActivityReenter(arg0, arg1)
    }

    open fun onCreateView(arg0: String, arg1: Context, arg2: AttributeSet): View? {
        return hostActivityDelegator?.superOnCreateView(arg0, arg1, arg2)
    }

    open fun onCreateView(arg0: View?, arg1: String, arg2: Context, arg3: AttributeSet): View? {
        return hostActivityDelegator?.superOnCreateView(arg0, arg1, arg2, arg3)
    }

    open fun onVisibleBehindCanceled() {
        hostActivityDelegator?.superOnVisibleBehindCanceled()
    }

    fun onEnterAnimationComplete() {
        hostActivityDelegator?.superOnEnterAnimationComplete()
    }

    override fun onPointerCaptureChanged(arg0: Boolean) {
        hostActivityDelegator?.superOnPointerCaptureChanged(arg0)
    }

    open fun onStop() {
        hostActivityDelegator?.superOnStop()
    }

    open fun onStart() {
        hostActivityDelegator?.superOnStart()
    }

    open fun onPause() {
        hostActivityDelegator?.superOnPause()
    }

    open fun onResume() {
        hostActivityDelegator?.superOnResume()
    }

    open fun onRestoreInstanceState(arg0: Bundle) {
        hostActivityDelegator?.superOnRestoreInstanceState(arg0)
    }

    open fun onPostCreate(arg0: Bundle?) {
        hostActivityDelegator?.superOnPostCreate(arg0)
    }

    open fun onRestart() {
        hostActivityDelegator?.superOnRestart()
    }

    open fun onPostResume() {
        hostActivityDelegator?.superOnPostResume()
    }

    open fun onNewIntent(arg0: Intent) {
        hostActivityDelegator?.superOnNewIntent(arg0)
    }

    open fun onSaveInstanceState(arg0: Bundle) {
        hostActivityDelegator?.superOnSaveInstanceState(arg0)
    }

    open fun onUserLeaveHint() {
        hostActivityDelegator?.superOnUserLeaveHint()
    }

    open fun onDestroy() {
        hostActivityDelegator?.superOnDestroy()
    }

    open fun onCreate(arg0: Bundle?) {
        hostActivityDelegator?.superOnCreate(arg0)
    }

    open fun onCreateDialog(arg0: Int, arg1: Bundle): Dialog? {
        return hostActivityDelegator?.superOnCreateDialog(arg0, arg1)
    }

    open fun onCreateDialog(arg0: Int): Dialog? {
        return hostActivityDelegator?.superOnCreateDialog(arg0)
    }

    open fun onPrepareDialog(arg0: Int, arg1: Dialog, arg2: Bundle) {
        hostActivityDelegator?.superOnPrepareDialog(arg0, arg1, arg2)
    }

    open fun onPrepareDialog(arg0: Int, arg1: Dialog) {
        hostActivityDelegator?.superOnPrepareDialog(arg0, arg1)
    }

    open fun onApplyPluginThemeResource(arg0: Resources.Theme, arg1: Int, arg2: Boolean) {
        hostActivityDelegator?.superOnApplyThemeResource(arg0, arg1, arg2)
    }

    open fun onActivityResult(arg0: Int, arg1: Int, arg2: Intent) {
        hostActivityDelegator?.superOnActivityResult(arg0, arg1, arg2)
    }

    open fun onTitleChanged(arg0: CharSequence, arg1: Int) {
        hostActivityDelegator?.superOnTitleChanged(arg0, arg1)
    }

    abstract fun onChildTitleChanged(arg0: PluginActivity, arg1: CharSequence)

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        val keyCode = event.keyCode
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            LogUtils.d(
                TAG,
                "dispatchKeyEvent  trace:" + Log.d(TAG, Log.getStackTraceString(Throwable()))
            )
        }
        return hostActivityDelegator!!.superDispatchKeyEvent(event)
    }

    override fun dispatchKeyShortcutEvent(arg0: KeyEvent): Boolean {
        return hostActivityDelegator!!.superDispatchKeyShortcutEvent(arg0)
    }

    override fun dispatchTouchEvent(arg0: MotionEvent): Boolean {
        return hostActivityDelegator!!.superDispatchTouchEvent(arg0)
    }

    override fun dispatchTrackballEvent(arg0: MotionEvent): Boolean {
        return hostActivityDelegator!!.superDispatchTrackballEvent(arg0)
    }

    override fun dispatchGenericMotionEvent(arg0: MotionEvent): Boolean {
        return hostActivityDelegator!!.superDispatchGenericMotionEvent(arg0)
    }

    override fun dispatchPopulateAccessibilityEvent(arg0: AccessibilityEvent): Boolean {
        return hostActivityDelegator!!.superDispatchPopulateAccessibilityEvent(arg0)
    }

    abstract fun getParent(): PluginActivity?

    open fun isDestroyed(): Boolean? {
        return hostActivityDelegator?.isDestroyed()
    }

    open fun getTaskId(): Int? {
        return hostActivityDelegator?.getTaskId()
    }

    open fun dump(arg0: String, arg1: FileDescriptor, arg2: PrintWriter, arg3: Array<String>) {
        hostActivityDelegator?.dump(arg0, arg1, arg2, arg3)
    }

    open fun getActionBar(): ActionBar? {
        return hostActivityDelegator?.getActionBar()
    }

    open fun <T : View> requireViewById(arg0: Int): T? {
        return hostActivityDelegator?.requireViewById(arg0)
    }

    open fun setFinishOnTouchOutside(arg0: Boolean) {
        hostActivityDelegator?.setFinishOnTouchOutside(arg0)
    }

    open fun addContentView(arg0: View, arg1: ViewGroup.LayoutParams) {
        hostActivityDelegator?.addContentView(arg0, arg1)
    }

    open fun setResult(arg0: Int) {
        hostActivityDelegator?.setResult(arg0)
    }

    open fun setResult(arg0: Int, arg1: Intent) {
        hostActivityDelegator?.setResult(arg0, arg1)
    }

    open fun setContentView(arg0: View) {
        hostActivityDelegator?.setContentView(arg0)
    }

    open fun setContentView(arg0: Int) {
        hostActivityDelegator?.setContentView(arg0)
    }

    open fun setContentView(arg0: View, arg1: ViewGroup.LayoutParams) {
        hostActivityDelegator?.setContentView(arg0, arg1)
    }

    open fun <T : View> findViewById(arg0: Int): T? {
        return hostActivityDelegator?.findViewById(arg0)
    }

    open fun managedQuery(
        arg0: Uri,
        arg1: Array<String>,
        arg2: String,
        arg3: Array<String>,
        arg4: String
    ): Cursor? {
        return hostActivityDelegator?.managedQuery(arg0, arg1, arg2, arg3, arg4)
    }

    open fun setPictureInPictureParams(arg0: PictureInPictureParams) {
        hostActivityDelegator?.setPictureInPictureParams(arg0)
    }

    abstract fun getApplication(): PluginApplication

    fun getWindowManager(): WindowManager? {
        return hostActivityDelegator?.getWindowManager()
    }

    open fun getWindow(): Window? {
        return hostActivityDelegator?.getWindow()
    }

    open fun getLoaderManager(): LoaderManager? {
        return hostActivityDelegator?.getLoaderManager()
    }

    open fun getCurrentFocus(): View? {
        return hostActivityDelegator?.getCurrentFocus()
    }

    open fun getIntent(): Intent? {
        return hostActivityDelegator?.getIntent()
    }

    open fun setIntent(arg0: Intent) {
        hostActivityDelegator?.setIntent(arg0)
    }

    open fun isChild(): Boolean {
        return hostActivityDelegator?.isChild() ?: false
    }

    open fun registerActivityLifecycleCallbacks(arg0: Application.ActivityLifecycleCallbacks) {
        hostActivityDelegator?.registerActivityLifecycleCallbacks(arg0)
    }

    open fun unregisterActivityLifecycleCallbacks(arg0: Application.ActivityLifecycleCallbacks) {
        hostActivityDelegator?.unregisterActivityLifecycleCallbacks(arg0)
    }

    open fun isVoiceInteraction(): Boolean? {
        return hostActivityDelegator?.isVoiceInteraction()
    }

    open fun isVoiceInteractionRoot(): Boolean? {
        return hostActivityDelegator?.isVoiceInteractionRoot()
    }

    open fun getVoiceInteractor(): VoiceInteractor? {
        return hostActivityDelegator?.getVoiceInteractor()
    }

    open fun isLocalVoiceInteractionSupported(): Boolean? {
        return hostActivityDelegator?.isLocalVoiceInteractionSupported()
    }

    open fun startLocalVoiceInteraction(arg0: Bundle) {
        hostActivityDelegator?.startLocalVoiceInteraction(arg0)
    }

    open fun stopLocalVoiceInteraction() {
        hostActivityDelegator?.stopLocalVoiceInteraction()
    }

    open fun getComponentName(): ComponentName? {
        return hostActivityDelegator?.getComponentName()
    }

    open fun dismissKeyboardShortcutsHelper() {
        hostActivityDelegator?.dismissKeyboardShortcutsHelper()
    }

    open fun startActivityFromChild(arg0: Activity, arg1: Intent, arg2: Int, arg3: Bundle) {
        hostActivityDelegator?.startActivityFromChild(arg0, arg1, arg2, arg3)
    }

    open fun startActionMode(arg0: ActionMode.Callback): ActionMode? {
        return hostActivityDelegator?.startActionMode(arg0)
    }

    open fun startActionMode(arg0: ActionMode.Callback, arg1: Int): ActionMode? {
        return hostActivityDelegator?.startActionMode(arg0, arg1)
    }

    open fun shouldUpRecreateTask(arg0: Intent): Boolean {
        return hostActivityDelegator?.shouldUpRecreateTask(arg0) ?: false
    }

    open fun navigateUpTo(arg0: Intent): Boolean {
        return hostActivityDelegator?.navigateUpTo(arg0) ?: false
    }

    abstract fun navigateUpToFromChild(arg0: PluginActivity, arg1: Intent): Boolean

    open fun getParentActivityIntent(): Intent? {
        return hostActivityDelegator?.getParentActivityIntent()
    }

    open fun setEnterSharedElementCallback(arg0: SharedElementCallback) {
        hostActivityDelegator?.setEnterSharedElementCallback(arg0)
    }

    open fun setExitSharedElementCallback(arg0: SharedElementCallback) {
        hostActivityDelegator?.setExitSharedElementCallback(arg0)
    }

    open fun postponeEnterTransition() {
        hostActivityDelegator?.postponeEnterTransition()
    }

    open fun startPostponedEnterTransition() {
        hostActivityDelegator?.startPostponedEnterTransition()
    }

    open fun requestDragAndDropPermissions(arg0: DragEvent): DragAndDropPermissions? {
        return hostActivityDelegator?.requestDragAndDropPermissions(arg0)
    }

    open fun startLockTask() {
        hostActivityDelegator?.startLockTask()
    }

    open fun stopLockTask() {
        hostActivityDelegator?.stopLockTask()
    }

    open fun showLockTaskEscapeMessage() {
        hostActivityDelegator?.showLockTaskEscapeMessage()
    }

    open fun setShowWhenLocked(arg0: Boolean) {
        hostActivityDelegator?.setShowWhenLocked(arg0)
    }

    open fun setInheritShowWhenLocked(arg0: Boolean) {
        hostActivityDelegator?.setInheritShowWhenLocked(arg0)
    }

    open fun setTurnScreenOn(arg0: Boolean) {
        hostActivityDelegator?.setTurnScreenOn(arg0)
    }

    open fun setVisible(arg0: Boolean) {
        hostActivityDelegator?.setVisible(arg0)
    }

    open fun startManagingCursor(arg0: Cursor) {
        hostActivityDelegator?.startManagingCursor(arg0)
    }

    open fun getLastNonConfigurationInstance(): Any? {
        return hostActivityDelegator?.getLastNonConfigurationInstance()
    }

    open fun getChangingConfigurations(): Int? {
        return hostActivityDelegator?.getChangingConfigurations()
    }

    open fun showAssist(arg0: Bundle): Boolean? {
        return hostActivityDelegator?.showAssist(arg0)
    }

    open fun reportFullyDrawn() {
        hostActivityDelegator?.reportFullyDrawn()
    }

    open fun requestShowKeyboardShortcuts() {
        hostActivityDelegator?.requestShowKeyboardShortcuts()
    }

    open fun setTitle(arg0: Int) {
        hostActivityDelegator?.setTitle(arg0)
    }

    open fun setTitle(arg0: CharSequence) {
        hostActivityDelegator?.setTitle(arg0)
    }

    open fun getTitle(): CharSequence? {
        return hostActivityDelegator?.getTitle()
    }

    open fun getContentTransitionManager(): TransitionManager? {
        return hostActivityDelegator?.getContentTransitionManager()
    }

    open fun setDefaultKeyMode(arg0: Int) {
        hostActivityDelegator?.setDefaultKeyMode(arg0)
    }

    open fun setActionBar(arg0: Toolbar) {
        hostActivityDelegator?.setActionBar(arg0)
    }

    open fun stopManagingCursor(arg0: Cursor) {
        hostActivityDelegator?.stopManagingCursor(arg0)
    }

    open fun isInPictureInPictureMode(): Boolean? {
        return hostActivityDelegator?.isInPictureInPictureMode()
    }

    abstract fun getFragmentManager(): PluginFragmentManager

    open fun enterPictureInPictureMode() {
        hostActivityDelegator?.enterPictureInPictureMode()
    }

    open fun enterPictureInPictureMode(arg0: PictureInPictureParams): Boolean? {
        return hostActivityDelegator?.enterPictureInPictureMode(arg0)
    }

    open fun getMaxNumPictureInPictureActions(): Int? {
        return hostActivityDelegator?.getMaxNumPictureInPictureActions()
    }

    open fun isInMultiWindowMode(): Boolean? {
        return hostActivityDelegator?.isInMultiWindowMode()
    }

    open fun setContentTransitionManager(arg0: TransitionManager) {
        hostActivityDelegator?.setContentTransitionManager(arg0)
    }

    open fun getContentScene(): Scene? {
        return hostActivityDelegator?.getContentScene()
    }

    open fun hasWindowFocus(): Boolean? {
        return hostActivityDelegator?.hasWindowFocus()
    }

    open fun invalidateOptionsMenu() {
        hostActivityDelegator?.invalidateOptionsMenu()
    }

    open fun openOptionsMenu() {
        hostActivityDelegator?.openOptionsMenu()
    }

    open fun closeOptionsMenu() {
        hostActivityDelegator?.closeOptionsMenu()
    }

    open fun registerForContextMenu(arg0: View) {
        hostActivityDelegator?.registerForContextMenu(arg0)
    }

    open fun unregisterForContextMenu(arg0: View) {
        hostActivityDelegator?.unregisterForContextMenu(arg0)
    }

    open fun openContextMenu(arg0: View) {
        hostActivityDelegator?.openContextMenu(arg0)
    }

    open fun closeContextMenu() {
        hostActivityDelegator?.closeContextMenu()
    }

    open fun showDialog(arg0: Int, arg1: Bundle): Boolean? {
        return hostActivityDelegator?.showDialog(arg0, arg1)
    }

    open fun showDialog(arg0: Int) {
        hostActivityDelegator?.showDialog(arg0)
    }

    open fun dismissDialog(arg0: Int) {
        hostActivityDelegator?.dismissDialog(arg0)
    }

    open fun removeDialog(arg0: Int) {
        hostActivityDelegator?.removeDialog(arg0)
    }

    open fun getSearchEvent(): SearchEvent? {
        return hostActivityDelegator?.getSearchEvent()
    }

    open fun startSearch(arg0: String, arg1: Boolean, arg2: Bundle, arg3: Boolean) {
        hostActivityDelegator?.startSearch(arg0, arg1, arg2, arg3)
    }

    open fun triggerSearch(arg0: String, arg1: Bundle) {
        hostActivityDelegator?.triggerSearch(arg0, arg1)
    }

    open fun takeKeyEvents(arg0: Boolean) {
        hostActivityDelegator?.takeKeyEvents(arg0)
    }

    open fun requestWindowFeature(arg0: Int): Boolean? {
        return hostActivityDelegator?.requestWindowFeature(arg0)
    }

    open fun setFeatureDrawableResource(arg0: Int, arg1: Int) {
        hostActivityDelegator?.setFeatureDrawableResource(arg0, arg1)
    }

    open fun setFeatureDrawableUri(arg0: Int, arg1: Uri) {
        hostActivityDelegator?.setFeatureDrawableUri(arg0, arg1)
    }

    open fun setFeatureDrawable(arg0: Int, arg1: Drawable) {
        hostActivityDelegator?.setFeatureDrawable(arg0, arg1)
    }

    open fun setFeatureDrawableAlpha(arg0: Int, arg1: Int) {
        hostActivityDelegator?.setFeatureDrawableAlpha(arg0, arg1)
    }

    open fun getMenuInflater(): MenuInflater? {
        return hostActivityDelegator?.getMenuInflater()
    }

    open fun requestPermissions(arg0: Array<String>, arg1: Int) {
        hostActivityDelegator?.requestPermissions(arg0, arg1)
    }

    open fun shouldShowRequestPermissionRationale(arg0: String): Boolean {
        return hostActivityDelegator?.shouldShowRequestPermissionRationale(arg0) ?: false
    }

    open fun startActivityForResult(arg0: Intent, arg1: Int) {
        hostActivityDelegator?.startActivityForResult(arg0, arg1)
    }

    open fun startActivityForResult(arg0: Intent, arg1: Int, arg2: Bundle) {
        hostActivityDelegator?.startActivityForResult(arg0, arg1, arg2)
    }

    open fun isActivityTransitionRunning(): Boolean? {
        return hostActivityDelegator?.isActivityTransitionRunning()
    }

    @Throws(IntentSender.SendIntentException::class)
    open fun startIntentSenderForResult(
        arg0: IntentSender, arg1: Int, arg2: Intent, arg3: Int,
        arg4: Int, arg5: Int
    ) {
        hostActivityDelegator?.startIntentSenderForResult(arg0, arg1, arg2, arg3, arg4, arg5)
    }

    @Throws(IntentSender.SendIntentException::class)
    open fun startIntentSenderForResult(
        arg0: IntentSender, arg1: Int, arg2: Intent, arg3: Int,
        arg4: Int, arg5: Int, arg6: Bundle
    ) {
        hostActivityDelegator?.startIntentSenderForResult(arg0, arg1, arg2, arg3, arg4, arg5, arg6)
    }

    open fun startActivityIfNeeded(arg0: Intent, arg1: Int): Boolean? {
        return hostActivityDelegator?.startActivityIfNeeded(arg0, arg1)
    }

    open fun startActivityIfNeeded(arg0: Intent, arg1: Int, arg2: Bundle): Boolean? {
        return hostActivityDelegator?.startActivityIfNeeded(arg0, arg1, arg2)
    }

    open fun startNextMatchingActivity(arg0: Intent, arg1: Bundle): Boolean? {
        return hostActivityDelegator?.startNextMatchingActivity(arg0, arg1)
    }

    open fun startNextMatchingActivity(arg0: Intent): Boolean? {
        return hostActivityDelegator?.startNextMatchingActivity(arg0)
    }

    open fun startActivityFromFragment(arg0: Fragment, arg1: Intent, arg2: Int) {
        hostActivityDelegator?.startActivityFromFragment(arg0, arg1, arg2)
    }

    open fun startActivityFromFragment(arg0: Fragment, arg1: Intent, arg2: Int, arg3: Bundle) {
        hostActivityDelegator?.startActivityFromFragment(arg0, arg1, arg2, arg3)
    }

    @Throws(IntentSender.SendIntentException::class)
    abstract fun startIntentSenderFromChild(
        arg0: PluginActivity, arg1: IntentSender, arg2: Int,
        arg3: Intent, arg4: Int, arg5: Int, arg6: Int, arg7: Bundle
    )

    @Throws(IntentSender.SendIntentException::class)
    abstract fun startIntentSenderFromChild(
        arg0: PluginActivity, arg1: IntentSender, arg2: Int,
        arg3: Intent, arg4: Int, arg5: Int, arg6: Int
    )

    open fun overridePendingTransition(arg0: Int, arg1: Int) {
        hostActivityDelegator?.overridePendingTransition(arg0, arg1)
    }

    open fun getReferrer(): Uri? {
        return hostActivityDelegator?.getReferrer()
    }

    open fun getCallingPackage(): String? {
        return hostActivityDelegator?.getCallingPackage()
    }

    open fun isFinishing(): Boolean? {
        return hostActivityDelegator?.isFinishing()
    }

    open fun finishAffinity() {
        hostActivityDelegator?.finishAffinity()
    }

    abstract fun finishFromChild(arg0: PluginActivity)

    open fun finishAfterTransition() {
        hostActivityDelegator?.finishAfterTransition()
    }

    open fun finishActivity(arg0: Int) {
        hostActivityDelegator?.finishActivity(arg0)
    }

    abstract fun finishActivityFromChild(arg0: PluginActivity, arg1: Int)

    open fun finishAndRemoveTask() {
        hostActivityDelegator?.finishAndRemoveTask()
    }

    open fun releaseInstance(): Boolean? {
        return hostActivityDelegator?.releaseInstance()
    }

    open fun createPendingResult(arg0: Int, arg1: Intent, arg2: Int): PendingIntent? {
        return hostActivityDelegator?.createPendingResult(arg0, arg1, arg2)
    }

    open fun setRequestedOrientation(arg0: Int) {
        hostActivityDelegator?.setRequestedOrientation(arg0)
    }

    open fun getRequestedOrientation(): Int {
        return hostActivityDelegator?.getRequestedOrientation()?: ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    open fun isTaskRoot(): Boolean? {
        return hostActivityDelegator?.isTaskRoot()
    }

    open fun moveTaskToBack(arg0: Boolean): Boolean? {
        return hostActivityDelegator?.moveTaskToBack(arg0)
    }

    open fun getLocalClassName(): String? {
        return hostActivityDelegator?.getLocalClassName()
    }

    open fun getPreferences(arg0: Int): SharedPreferences? {
        return hostActivityDelegator?.getPreferences(arg0)
    }

    open fun setTitleColor(arg0: Int) {
        hostActivityDelegator?.setTitleColor(arg0)
    }

    open fun getTitleColor(): Int? {
        return hostActivityDelegator?.getTitleColor()
    }

    open fun setTaskDescription(arg0: ActivityManager.TaskDescription) {
        hostActivityDelegator?.setTaskDescription(arg0)
    }

    open fun setProgressBarVisibility(arg0: Boolean) {
        hostActivityDelegator?.setProgressBarVisibility(arg0)
    }

    open fun setProgressBarIndeterminateVisibility(arg0: Boolean) {
        hostActivityDelegator?.setProgressBarIndeterminateVisibility(arg0)
    }

    open fun setProgressBarIndeterminate(arg0: Boolean) {
        hostActivityDelegator?.setProgressBarIndeterminate(arg0)
    }

    open fun setProgress(arg0: Int) {
        hostActivityDelegator?.setProgress(arg0)
    }

    open fun setSecondaryProgress(arg0: Int) {
        hostActivityDelegator?.setSecondaryProgress(arg0)
    }

    open fun setVolumeControlStream(arg0: Int) {
        hostActivityDelegator?.setVolumeControlStream(arg0)
    }

    open fun getVolumeControlStream(): Int? {
        return hostActivityDelegator?.getVolumeControlStream()
    }

    open fun setMediaController(arg0: MediaController) {
        hostActivityDelegator?.setMediaController(arg0)
    }

    open fun getMediaController(): MediaController? {
        return hostActivityDelegator?.getMediaController()
    }

    open fun runOnUiThread(arg0: Runnable) {
        hostActivityDelegator?.runOnUiThread(arg0)
    }

    open fun isImmersive(): Boolean? {
        return hostActivityDelegator?.isImmersive()
    }

    open fun requestVisibleBehind(arg0: Boolean): Boolean? {
        return hostActivityDelegator?.requestVisibleBehind(arg0)
    }

    open fun setImmersive(arg0: Boolean) {
        hostActivityDelegator?.setImmersive(arg0)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    open fun setVrModeEnabled(arg0: Boolean, arg1: ComponentName) {
        hostActivityDelegator?.setVrModeEnabled(arg0, arg1)
    }

}