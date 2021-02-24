package com.myl.androidapkplugin.pluginlib.delegate

import android.annotation.SuppressLint
import android.app.*
import android.app.assist.AssistContent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.os.CancellationSignal
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.*
import android.view.accessibility.AccessibilityEvent
import com.myl.androidapkplugin.pluginlib.activity.BasePluginActivity
import java.util.function.Consumer

/**
 *  BaseActivityDelegate  GeneratedShadowActivityDelegate
 */
@SuppressLint("NewApi")
abstract class BaseActivityDelegate :
    com.myl.plugin_core.proxy.method.PluginActivityMethodInterface {

    var pluginActivity: BasePluginActivity? = null
    protected var mPluginActivityInit = false

    override fun isChangingConfigurations(): Boolean {
        //回调到插件PluginActivity
        return pluginActivity?.isChangingConfigurations() ?: false
    }

    override fun finish() {
        pluginActivity?.finish()
    }

    override fun startActivityFromChild(arg0: Activity, arg1: Intent, arg2: Int) {
        pluginActivity?.startActivityFromChild(arg0, arg1, arg2)
    }

    override fun getClassLoader(): ClassLoader? {
        return pluginActivity?.classLoader
    }

    override fun getLayoutInflater(): LayoutInflater {
        return pluginActivity?.getLayoutInflater()!!
    }

    override fun getResources(): Resources {
        if (mPluginActivityInit) {
            return pluginActivity!!.resources
        } else {
            //预期只有android.view.Window.getDefaultFeatures会调用到这个分支，此时我们还无法确定插件资源
            //而getDefaultFeatures只需要访问系统资源
            return Resources.getSystem()
        }
    }

    override fun recreate() {
        pluginActivity?.recreate()
    }

    override fun getCallingActivity(): ComponentName {
        return pluginActivity?.getCallingActivity()!!
    }

    override fun onCreate(arg0: Bundle?, arg1: PersistableBundle?) {
        pluginActivity?.onCreate(arg0, arg1)
    }

    override fun onRestoreInstanceState(arg0: Bundle?, arg1: PersistableBundle?) {
        pluginActivity?.onRestoreInstanceState(arg0, arg1)
    }

    override fun onPostCreate(arg0: Bundle?, arg1: PersistableBundle?) {
        pluginActivity?.onPostCreate(arg0, arg1)
    }

    override fun onStateNotSaved() {
        pluginActivity?.onStateNotSaved()
    }

    override fun onTopResumedActivityChanged(arg0: Boolean) {
        pluginActivity?.onTopResumedActivityChanged(arg0)
    }

    override fun onLocalVoiceInteractionStarted() {
        pluginActivity?.onLocalVoiceInteractionStarted()
    }

    override fun onLocalVoiceInteractionStopped() {
        pluginActivity?.onLocalVoiceInteractionStopped()
    }

    override fun onSaveInstanceState(arg0: Bundle, arg1: PersistableBundle) {
        pluginActivity?.onSaveInstanceState(arg0, arg1)
    }

    override fun onCreateThumbnail(arg0: Bitmap, arg1: Canvas): Boolean {
        return pluginActivity?.onCreateThumbnail(arg0, arg1) ?: false
    }

    override fun onCreateDescription(): CharSequence? {
        return pluginActivity?.onCreateDescription()
    }

    override fun onProvideAssistData(arg0: Bundle) {
        pluginActivity?.onProvideAssistData(arg0)
    }

    override fun onProvideAssistContent(arg0: AssistContent) {
        pluginActivity?.onProvideAssistContent(arg0)
    }


    override fun onGetDirectActions(arg0: CancellationSignal, arg1: Consumer<List<DirectAction>>) {
        pluginActivity?.onGetDirectActions(arg0, arg1)
    }

    override fun onPerformDirectAction(
        arg0: String, arg1: Bundle, arg2: CancellationSignal,
        arg3: Consumer<Bundle>
    ) {
        pluginActivity?.onPerformDirectAction(arg0, arg1, arg2, arg3)
    }

    override fun onProvideKeyboardShortcuts(
        arg0: List<KeyboardShortcutGroup>,
        arg1: Menu?,
        arg2: Int
    ) {
        pluginActivity?.onProvideKeyboardShortcuts(arg0, arg1, arg2)
    }

    override fun onMultiWindowModeChanged(arg0: Boolean, arg1: Configuration) {
        pluginActivity?.onMultiWindowModeChanged(arg0, arg1)
    }

    override fun onMultiWindowModeChanged(arg0: Boolean) {
        pluginActivity?.onMultiWindowModeChanged(arg0)
    }

    override fun onPictureInPictureModeChanged(arg0: Boolean) {
        pluginActivity?.onPictureInPictureModeChanged(arg0)
    }

    override fun onPictureInPictureModeChanged(arg0: Boolean, arg1: Configuration) {
        pluginActivity?.onPictureInPictureModeChanged(arg0, arg1)
    }

    override fun onConfigurationChanged(arg0: Configuration) {
        pluginActivity?.onConfigurationChanged(arg0)
    }

    override fun onRetainNonConfigurationInstance(): Any? {
        return pluginActivity?.onRetainNonConfigurationInstance()
    }

    override fun onLowMemory() {
        pluginActivity?.onLowMemory()
    }

    override fun onTrimMemory(arg0: Int) {
        pluginActivity?.onTrimMemory(arg0)
    }

    override fun onAttachFragment(arg0: Fragment) {
        pluginActivity?.onAttachFragment(arg0)
    }

    override fun onKeyDown(arg0: Int, arg1: KeyEvent): Boolean {
        return pluginActivity?.onKeyDown(arg0, arg1) ?: false
    }

    override fun onKeyLongPress(arg0: Int, arg1: KeyEvent): Boolean {
        return pluginActivity?.onKeyLongPress(arg0, arg1) ?: false
    }

    override fun onKeyUp(arg0: Int, arg1: KeyEvent): Boolean {
        return pluginActivity?.onKeyUp(arg0, arg1) ?: false
    }

    override fun onKeyMultiple(arg0: Int, arg1: Int, arg2: KeyEvent): Boolean {
        return pluginActivity?.onKeyMultiple(arg0, arg1, arg2) ?: false
    }

    override fun onBackPressed() {
        pluginActivity?.onBackPressed()
    }

    override fun onKeyShortcut(arg0: Int, arg1: KeyEvent): Boolean {
        return pluginActivity?.onKeyShortcut(arg0, arg1) ?: false
    }

    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        return pluginActivity?.onTouchEvent(arg0) ?: false
    }

    override fun onTrackballEvent(arg0: MotionEvent): Boolean {
        return pluginActivity?.onTrackballEvent(arg0) ?: false
    }

    override fun onGenericMotionEvent(arg0: MotionEvent): Boolean {
        return pluginActivity?.onGenericMotionEvent(arg0) ?: false
    }

    override fun onUserInteraction() {
        pluginActivity?.onUserInteraction()
    }

    override fun onWindowAttributesChanged(arg0: WindowManager.LayoutParams) {
        pluginActivity?.onWindowAttributesChanged(arg0)
    }

    override fun onContentChanged() {
        pluginActivity?.onContentChanged()
    }

    override fun onWindowFocusChanged(arg0: Boolean) {
        pluginActivity?.onWindowFocusChanged(arg0)
    }

    override fun onAttachedToWindow() {
        pluginActivity?.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        pluginActivity?.onDetachedFromWindow()
    }

    override fun onCreatePanelView(arg0: Int): View? {
        return pluginActivity?.onCreatePanelView(arg0)
    }

    override fun onCreatePanelMenu(arg0: Int, arg1: Menu): Boolean {
        return pluginActivity?.onCreatePanelMenu(arg0, arg1) ?: false
    }

    override fun onPreparePanel(arg0: Int, arg1: View?, arg2: Menu): Boolean {
        return pluginActivity?.onPreparePanel(arg0, arg1, arg2) ?: false
    }

    override fun onMenuOpened(arg0: Int, arg1: Menu): Boolean {
        return pluginActivity?.onMenuOpened(arg0, arg1) ?: false
    }

    override fun onMenuItemSelected(arg0: Int, arg1: MenuItem): Boolean {
        return pluginActivity?.onMenuItemSelected(arg0, arg1) ?: false
    }

    override fun onPanelClosed(arg0: Int, arg1: Menu) {
        pluginActivity?.onPanelClosed(arg0, arg1)
    }

    override fun onCreateOptionsMenu(arg0: Menu): Boolean {
        return pluginActivity?.onCreateOptionsMenu(arg0) ?: false
    }

    override fun onPrepareOptionsMenu(arg0: Menu): Boolean {
        return pluginActivity?.onPrepareOptionsMenu(arg0) ?: false
    }

    override fun onOptionsItemSelected(arg0: MenuItem): Boolean {
        return pluginActivity?.onOptionsItemSelected(arg0) ?: false
    }

    override fun onNavigateUp(): Boolean {
        return pluginActivity?.onNavigateUp() ?: false
    }

    override fun onCreateNavigateUpTaskStack(arg0: TaskStackBuilder) {
        pluginActivity?.onCreateNavigateUpTaskStack(arg0)
    }

    override fun onPrepareNavigateUpTaskStack(arg0: TaskStackBuilder) {
        pluginActivity?.onPrepareNavigateUpTaskStack(arg0)
    }

    override fun onOptionsMenuClosed(arg0: Menu) {
        pluginActivity?.onOptionsMenuClosed(arg0)
    }

    override fun onCreateContextMenu(
        arg0: ContextMenu,
        arg1: View,
        arg2: ContextMenu.ContextMenuInfo
    ) {
        pluginActivity?.onCreateContextMenu(arg0, arg1, arg2)
    }

    override fun onContextItemSelected(arg0: MenuItem): Boolean {
        return pluginActivity?.onContextItemSelected(arg0) ?: false
    }

    override fun onContextMenuClosed(arg0: Menu) {
        pluginActivity?.onContextMenuClosed(arg0)
    }

    override fun onSearchRequested(): Boolean {
        return pluginActivity?.onSearchRequested() ?: false
    }

    override fun onSearchRequested(arg0: SearchEvent?): Boolean {
        return pluginActivity?.onSearchRequested(arg0) ?: false
    }

    override fun onRequestPermissionsResult(arg0: Int, arg1: Array<String>, arg2: IntArray) {
        pluginActivity?.onRequestPermissionsResult(arg0, arg1, arg2)
    }

    override fun onProvideReferrer(): Uri? {
        return pluginActivity?.onProvideReferrer()
    }

    override fun onActivityReenter(arg0: Int, arg1: Intent) {
        pluginActivity?.onActivityReenter(arg0, arg1)
    }

    override fun onCreateView(arg0: View?, arg1: String, arg2: Context, arg3: AttributeSet): View? {
        return pluginActivity?.onCreateView(arg0, arg1, arg2, arg3)
    }

    override fun onCreateView(arg0: String, arg1: Context, arg2: AttributeSet): View? {
        return pluginActivity?.onCreateView(arg0, arg1, arg2)
    }

    override fun onVisibleBehindCanceled() {
        pluginActivity?.onVisibleBehindCanceled()
    }

    override fun onEnterAnimationComplete() {
        pluginActivity?.onEnterAnimationComplete()
    }

    override fun onWindowStartingActionMode(arg0: ActionMode.Callback): ActionMode? {
        return pluginActivity?.onWindowStartingActionMode(arg0)
    }

    override fun onWindowStartingActionMode(arg0: ActionMode.Callback, arg1: Int): ActionMode? {
        return pluginActivity?.onWindowStartingActionMode(arg0, arg1)
    }

    override fun onActionModeStarted(arg0: ActionMode) {
        pluginActivity?.onActionModeStarted(arg0)
    }

    override fun onActionModeFinished(arg0: ActionMode) {
        pluginActivity?.onActionModeFinished(arg0)
    }

    override fun onPointerCaptureChanged(arg0: Boolean) {
        pluginActivity?.onPointerCaptureChanged(arg0)
    }

    override fun onStart() {
        pluginActivity?.onStart()
    }

    override fun onStop() {
        pluginActivity?.onStop()
    }

    override fun onCreate(arg0: Bundle?) {
        pluginActivity?.onCreate(arg0)
    }

    override fun onRestoreInstanceState(arg0: Bundle) {
        pluginActivity?.onRestoreInstanceState(arg0)
    }

    override fun onPostCreate(arg0: Bundle?) {
        pluginActivity?.onPostCreate(arg0)
    }

    override fun onRestart() {
        pluginActivity?.onRestart()
    }

    override fun onResume() {
        pluginActivity?.onResume()
    }

    override fun onPostResume() {
        pluginActivity?.onPostResume()
    }

    override fun onNewIntent(arg0: Intent) {
        pluginActivity?.onNewIntent(arg0)
    }

    override fun onSaveInstanceState(arg0: Bundle) {
        pluginActivity?.onSaveInstanceState(arg0)
    }

    override fun onPause() {
        pluginActivity?.onPause()
    }

    override fun onUserLeaveHint() {
        pluginActivity?.onUserLeaveHint()
    }

    override fun onDestroy() {
        pluginActivity?.onDestroy()
    }

    override fun onCreateDialog(arg0: Int, arg1: Bundle): Dialog? {
        return pluginActivity?.onCreateDialog(arg0, arg1)
    }

    override fun onCreateDialog(arg0: Int): Dialog? {
        return pluginActivity?.onCreateDialog(arg0)
    }

    override fun onPrepareDialog(arg0: Int, arg1: Dialog, arg2: Bundle) {
        pluginActivity?.onPrepareDialog(arg0, arg1, arg2)
    }

    override fun onPrepareDialog(arg0: Int, arg1: Dialog) {
        pluginActivity?.onPrepareDialog(arg0, arg1)
    }

    override fun onApplyThemeResource(arg0: Resources.Theme, arg1: Int, arg2: Boolean) {
        pluginActivity?.onApplyPluginThemeResource(arg0, arg1, arg2)
    }

    override fun onActivityResult(arg0: Int, arg1: Int, arg2: Intent) {
        pluginActivity?.onActivityResult(arg0, arg1, arg2)
    }

    override fun onTitleChanged(arg0: CharSequence, arg1: Int) {
        pluginActivity?.onTitleChanged(arg0, arg1)
    }

    override fun dispatchKeyEvent(arg0: KeyEvent): Boolean? {
        return pluginActivity?.dispatchKeyEvent(arg0)
    }

    override fun dispatchKeyShortcutEvent(arg0: KeyEvent): Boolean {
        return pluginActivity?.dispatchKeyShortcutEvent(arg0) ?: false
    }

    override fun dispatchTouchEvent(arg0: MotionEvent): Boolean {
        return pluginActivity?.dispatchTouchEvent(arg0) ?: false
    }

    override fun dispatchTrackballEvent(arg0: MotionEvent): Boolean {
        return pluginActivity?.dispatchTrackballEvent(arg0) ?: false
    }

    override fun dispatchGenericMotionEvent(arg0: MotionEvent): Boolean {
        return pluginActivity?.dispatchGenericMotionEvent(arg0)  ?: false
    }

    override fun dispatchPopulateAccessibilityEvent(arg0: AccessibilityEvent): Boolean {
        return pluginActivity?.dispatchPopulateAccessibilityEvent(arg0)  ?: false
    }
}