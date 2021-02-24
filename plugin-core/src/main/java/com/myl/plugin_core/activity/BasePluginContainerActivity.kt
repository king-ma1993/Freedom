package com.myl.plugin_core.activity

import android.annotation.SuppressLint
import android.app.*
import android.app.assist.AssistContent
import android.content.*
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Configuration
import android.content.res.Resources
import android.database.Cursor
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.media.session.MediaController
import android.net.Uri
import android.os.*
import android.transition.Scene
import android.transition.TransitionManager
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.accessibility.AccessibilityEvent
import android.widget.Toolbar
import com.myl.plugin_core.proxy.HostActivitySuperMethodProxy
import com.myl.plugin_core.proxy.method.PluginActivityMethodInterface
import com.myl.plugin_core.util.LogUtils
import java.io.*
import java.util.concurrent.Executor
import java.util.function.Consumer

@SuppressLint("NewApi", "MissingPermission")
abstract class BasePluginContainerActivity : Activity(),
    HostActivitySuperMethodProxy {
    companion object {
        const val TAG = "BasePluginContainerActivity"
    }

    //插件activity实现这个接口
    protected var pluginActivityMethodInterface: PluginActivityMethodInterface? = null

    //生命周期回调 PluginActivityDelegateProxy
    override fun isChangingConfigurations(): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.isChangingConfigurations()
        } else {
            super.isChangingConfigurations()
        }
    }

    override fun finish() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.finish()
        } else {
            super.finish()
        }
    }

    override fun startActivityFromChild(arg0: Activity, arg1: Intent, arg2: Int) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.startActivityFromChild(arg0, arg1, arg2)
        } else {
            super.startActivityFromChild(arg0, arg1, arg2)
        }
    }

    override fun getClassLoader(): ClassLoader? {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.getClassLoader()
        } else {
            super.getClassLoader()
        }
    }

    override fun getLayoutInflater(): LayoutInflater {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.getLayoutInflater()
        } else {
            super.getLayoutInflater()
        }
    }

    override fun getResources(): Resources {
        return if (/*isSetContentView && */pluginActivityMethodInterface != null) {
//            LogUtils.d(
//                TAG,
//                "getResources pluginActivityMethodInterface:${Log.getStackTraceString(Throwable())}"
//            )
            pluginActivityMethodInterface!!.getResources()
        } else {
            super.getResources()
        }
    }

    override fun recreate() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.recreate()
        } else {
            super.recreate()
        }
    }

    override fun getCallingActivity(): ComponentName? {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.getCallingActivity()
        } else {
            super.getCallingActivity()
        }
    }

    override fun onKeyMultiple(arg0: Int, arg1: Int, arg2: KeyEvent): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onKeyMultiple(arg0, arg1, arg2)
        } else {
            super.onKeyMultiple(arg0, arg1, arg2)
        }
    }

    override fun onKeyDown(arg0: Int, arg1: KeyEvent): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onKeyDown(arg0, arg1)
        } else {
            super.onKeyDown(arg0, arg1)
        }
    }

    override fun onKeyUp(arg0: Int, arg1: KeyEvent): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onKeyUp(arg0, arg1)
        } else {
            super.onKeyUp(arg0, arg1)
        }
    }

    override fun onKeyLongPress(arg0: Int, arg1: KeyEvent): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onKeyLongPress(arg0, arg1)
        } else {
            super.onKeyLongPress(arg0, arg1)
        }
    }


    override fun onRestoreInstanceState(arg0: Bundle?, arg1: PersistableBundle?) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onRestoreInstanceState(arg0, arg1)
        } else {
            super.onRestoreInstanceState(arg0, arg1)
        }
    }

    override fun onPostCreate(arg0: Bundle?, arg1: PersistableBundle?) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onPostCreate(arg0, arg1)
        } else {
            super.onPostCreate(arg0, arg1)
        }
    }

    override fun onStateNotSaved() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onStateNotSaved()
        } else {
            super.onStateNotSaved()
        }
    }

    override fun onTopResumedActivityChanged(arg0: Boolean) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onTopResumedActivityChanged(arg0)
        } else {
            super.onTopResumedActivityChanged(arg0)
        }
    }

    override fun onLocalVoiceInteractionStarted() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onLocalVoiceInteractionStarted()
        } else {
            super.onLocalVoiceInteractionStarted()
        }
    }

    override fun onLocalVoiceInteractionStopped() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onLocalVoiceInteractionStopped()
        } else {
            super.onLocalVoiceInteractionStopped()
        }
    }

    override fun onSaveInstanceState(arg0: Bundle, arg1: PersistableBundle) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onSaveInstanceState(arg0, arg1)
        } else {
            super.onSaveInstanceState(arg0, arg1)
        }
    }

    override fun onCreateThumbnail(arg0: Bitmap, arg1: Canvas): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onCreateThumbnail(arg0, arg1)
        } else {
            super.onCreateThumbnail(arg0, arg1)
        }
    }

    override fun onCreateDescription(): CharSequence? {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onCreateDescription()
        } else {
            super.onCreateDescription()
        }
    }

    override fun onProvideAssistData(arg0: Bundle) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onProvideAssistData(arg0)
        } else {
            super.onProvideAssistData(arg0)
        }
    }

    override fun onProvideAssistContent(arg0: AssistContent) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onProvideAssistContent(arg0)
        } else {
            super.onProvideAssistContent(arg0)
        }
    }

    override fun onGetDirectActions(arg0: CancellationSignal, arg1: Consumer<List<DirectAction>>) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onGetDirectActions(arg0, arg1)
        } else {
            super.onGetDirectActions(arg0, arg1)
        }
    }

    override fun onPerformDirectAction(
        arg0: String, arg1: Bundle, arg2: CancellationSignal,
        arg3: Consumer<Bundle>
    ) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onPerformDirectAction(arg0, arg1, arg2, arg3)
        } else {
            super.onPerformDirectAction(arg0, arg1, arg2, arg3)
        }
    }

    override fun onCreate(arg0: Bundle?, arg1: PersistableBundle?) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onCreate(arg0, arg1)
        } else {
            super.onCreate(arg0, arg1)
        }
    }

    override fun onActionModeStarted(arg0: ActionMode) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onActionModeStarted(arg0)
        } else {
            super.onActionModeStarted(arg0)
        }
    }

    override fun onWindowStartingActionMode(arg0: ActionMode.Callback, arg1: Int): ActionMode? {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onWindowStartingActionMode(arg0, arg1)
        } else {
            super.onWindowStartingActionMode(arg0, arg1)
        }
    }

    override fun onWindowStartingActionMode(arg0: ActionMode.Callback): ActionMode? {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onWindowStartingActionMode(arg0)
        } else {
            super.onWindowStartingActionMode(arg0)
        }
    }

    override fun onActionModeFinished(arg0: ActionMode) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onActionModeFinished(arg0)
        } else {
            super.onActionModeFinished(arg0)
        }
    }

    override fun onAttachFragment(arg0: Fragment) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onAttachFragment(arg0)
        } else {
            super.onAttachFragment(arg0)
        }
    }

    override fun onRetainNonConfigurationInstance(): Any? {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onRetainNonConfigurationInstance()
        } else {
            super.onRetainNonConfigurationInstance()
        }
    }

    override fun onTrimMemory(arg0: Int) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onTrimMemory(arg0)
        } else {
            super.onTrimMemory(arg0)
        }
    }

    override fun onLowMemory() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onLowMemory()
        } else {
            super.onLowMemory()
        }
    }

    override fun onProvideKeyboardShortcuts(
        arg0: List<KeyboardShortcutGroup>,
        arg1: Menu?,
        arg2: Int
    ) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onProvideKeyboardShortcuts(arg0, arg1, arg2)
        } else {
            super.onProvideKeyboardShortcuts(arg0, arg1, arg2)
        }
    }

    override fun onMultiWindowModeChanged(arg0: Boolean) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onMultiWindowModeChanged(arg0)
        } else {
            super.onMultiWindowModeChanged(arg0)
        }
    }

    override fun onMultiWindowModeChanged(arg0: Boolean, arg1: Configuration) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onMultiWindowModeChanged(arg0, arg1)
        } else {
            super.onMultiWindowModeChanged(arg0, arg1)
        }
    }

    override fun onBackPressed() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    override fun onConfigurationChanged(arg0: Configuration) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onConfigurationChanged(arg0)
        } else {
            super.onConfigurationChanged(arg0)
        }
    }

    override fun onPictureInPictureModeChanged(arg0: Boolean) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onPictureInPictureModeChanged(arg0)
        } else {
            super.onPictureInPictureModeChanged(arg0)
        }
    }

    override fun onPictureInPictureModeChanged(arg0: Boolean, arg1: Configuration) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onPictureInPictureModeChanged(arg0, arg1)
        } else {
            super.onPictureInPictureModeChanged(arg0, arg1)
        }
    }

    override fun onKeyShortcut(arg0: Int, arg1: KeyEvent): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onKeyShortcut(arg0, arg1)
        } else {
            super.onKeyShortcut(arg0, arg1)
        }
    }

    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onTouchEvent(arg0)
        } else {
            super.onTouchEvent(arg0)
        }
    }

    override fun onTrackballEvent(arg0: MotionEvent): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onTrackballEvent(arg0)
        } else {
            super.onTrackballEvent(arg0)
        }
    }

    override fun onGenericMotionEvent(arg0: MotionEvent): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onGenericMotionEvent(arg0)
        } else {
            super.onGenericMotionEvent(arg0)
        }
    }

    override fun onUserInteraction() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onUserInteraction()
        } else {
            super.onUserInteraction()
        }
    }

    override fun onWindowAttributesChanged(arg0: WindowManager.LayoutParams) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onWindowAttributesChanged(arg0)
        } else {
            super.onWindowAttributesChanged(arg0)
        }
    }

    override fun onContentChanged() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onContentChanged()
        } else {
            super.onContentChanged()
        }
    }

    override fun onWindowFocusChanged(arg0: Boolean) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onWindowFocusChanged(arg0)
        } else {
            super.onWindowFocusChanged(arg0)
        }
    }

    override fun onAttachedToWindow() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onAttachedToWindow()
        } else {
            super.onAttachedToWindow()
        }
    }

    override fun onDetachedFromWindow() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onDetachedFromWindow()
        } else {
            super.onDetachedFromWindow()
        }
    }

    override fun onCreatePanelView(arg0: Int): View? {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onCreatePanelView(arg0)
        } else {
            super.onCreatePanelView(arg0)
        }
    }

    override fun onCreatePanelMenu(arg0: Int, arg1: Menu): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onCreatePanelMenu(arg0, arg1)
        } else {
            super.onCreatePanelMenu(arg0, arg1)
        }
    }

    override fun onPreparePanel(arg0: Int, arg1: View?, arg2: Menu): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onPreparePanel(arg0, arg1, arg2)
        } else {
            super.onPreparePanel(arg0, arg1, arg2)
        }
    }

    override fun onMenuOpened(arg0: Int, arg1: Menu): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onMenuOpened(arg0, arg1)
        } else {
            super.onMenuOpened(arg0, arg1)
        }
    }

    override fun onMenuItemSelected(arg0: Int, arg1: MenuItem): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onMenuItemSelected(arg0, arg1)
        } else {
            super.onMenuItemSelected(arg0, arg1)
        }
    }

    override fun onPanelClosed(arg0: Int, arg1: Menu) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onPanelClosed(arg0, arg1)
        } else {
            super.onPanelClosed(arg0, arg1)
        }
    }

    override fun onCreateOptionsMenu(arg0: Menu): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onCreateOptionsMenu(arg0)
        } else {
            super.onCreateOptionsMenu(arg0)
        }
    }

    override fun onPrepareOptionsMenu(arg0: Menu): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onPrepareOptionsMenu(arg0)
        } else {
            super.onPrepareOptionsMenu(arg0)
        }
    }

    override fun onOptionsItemSelected(arg0: MenuItem): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onOptionsItemSelected(arg0)
        } else {
            super.onOptionsItemSelected(arg0)
        }
    }

    override fun onNavigateUp(): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onNavigateUp()
        } else {
            super.onNavigateUp()
        }
    }

    override fun onNavigateUpFromChild(arg0: Activity): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onNavigateUpFromChild(arg0)
        } else {
            super.onNavigateUpFromChild(arg0)
        }
    }

    override fun onCreateNavigateUpTaskStack(arg0: TaskStackBuilder) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onCreateNavigateUpTaskStack(arg0)
        } else {
            super.onCreateNavigateUpTaskStack(arg0)
        }
    }

    override fun onPrepareNavigateUpTaskStack(arg0: TaskStackBuilder) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onPrepareNavigateUpTaskStack(arg0)
        } else {
            super.onPrepareNavigateUpTaskStack(arg0)
        }
    }

    override fun onOptionsMenuClosed(arg0: Menu) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onOptionsMenuClosed(arg0)
        } else {
            super.onOptionsMenuClosed(arg0)
        }
    }

    override fun onCreateContextMenu(
        arg0: ContextMenu,
        arg1: View,
        arg2: ContextMenu.ContextMenuInfo
    ) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onCreateContextMenu(arg0, arg1, arg2)
        } else {
            super.onCreateContextMenu(arg0, arg1, arg2)
        }
    }

    override fun onContextItemSelected(arg0: MenuItem): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onContextItemSelected(arg0)
        } else {
            super.onContextItemSelected(arg0)
        }
    }

    override fun onContextMenuClosed(arg0: Menu) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onContextMenuClosed(arg0)
        } else {
            super.onContextMenuClosed(arg0)
        }
    }

    override fun onSearchRequested(): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onSearchRequested()
        } else {
            super.onSearchRequested()
        }
    }

    override fun onSearchRequested(arg0: SearchEvent?): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onSearchRequested(arg0)
        } else {
            super.onSearchRequested(arg0)
        }
    }

    override fun onRequestPermissionsResult(arg0: Int, arg1: Array<String>, arg2: IntArray) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onRequestPermissionsResult(arg0, arg1, arg2)
        } else {
            super.onRequestPermissionsResult(arg0, arg1, arg2)
        }
    }

    override fun onProvideReferrer(): Uri? {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onProvideReferrer()
        } else {
            super.onProvideReferrer()
        }
    }

    override fun onActivityReenter(arg0: Int, arg1: Intent) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onActivityReenter(arg0, arg1)
        } else {
            super.onActivityReenter(arg0, arg1)
        }
    }

    override fun onCreateView(arg0: View?, name: String, context: Context, attrs: AttributeSet): View? {
        LogUtils.d(
            TAG,
            "onCreateView view:$arg0 name:$name context:$context " + Log.getStackTraceString(Throwable()))
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onCreateView(arg0, name, context, attrs)
        } else {
            super.onCreateView(arg0, name, context, attrs)
        }
    }

    override fun onCreateView(name: String, context: Context, arg2: AttributeSet): View? {
        LogUtils.d(
            TAG,
            "onCreateView view:$name name:$name context:$context " + Log.getStackTraceString(Throwable()))
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onCreateView(name, context, arg2)
        } else {
            super.onCreateView(name, context, arg2)
        }
    }

    override fun onVisibleBehindCanceled() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onVisibleBehindCanceled()
        } else {
            super.onVisibleBehindCanceled()
        }
    }

    override fun onEnterAnimationComplete() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onEnterAnimationComplete()
        } else {
            super.onEnterAnimationComplete()
        }
    }

    override fun onPointerCaptureChanged(arg0: Boolean) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onPointerCaptureChanged(arg0)
        } else {
            super.onPointerCaptureChanged(arg0)
        }
    }

    override fun onStop() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onStop()
        } else {
            super.onStop()
        }
    }

    override fun onStart() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onStart()
        } else {
            super.onStart()
        }
    }

    override fun onPause() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onPause()
        } else {
            super.onPause()
        }
    }

    override fun onResume() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onResume()
        } else {
            super.onResume()
        }
    }

    override fun onRestoreInstanceState(arg0: Bundle) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onRestoreInstanceState(arg0)
        } else {
            super.onRestoreInstanceState(arg0)
        }
    }

    override fun onPostCreate(arg0: Bundle?) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onPostCreate(arg0)
        } else {
            super.onPostCreate(arg0)
        }
    }

    override fun onRestart() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onRestart()
        } else {
            super.onRestart()
        }
    }

    override fun onPostResume() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onPostResume()
        } else {
            super.onPostResume()
        }
    }

    override fun onNewIntent(arg0: Intent) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onNewIntent(arg0)
        } else {
            super.onNewIntent(arg0)
        }
    }

    override fun onSaveInstanceState(arg0: Bundle) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onSaveInstanceState(arg0)
        } else {
            super.onSaveInstanceState(arg0)
        }
    }

    override fun onUserLeaveHint() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onUserLeaveHint()
        } else {
            super.onUserLeaveHint()
        }
    }

    override fun onDestroy() {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onDestroy()
        } else {
            super.onDestroy()
        }
    }

    override fun onCreate(arg0: Bundle?) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onCreate(arg0)
        } else {
            super.onCreate(arg0)
        }
    }

    override fun onCreateDialog(arg0: Int, arg1: Bundle): Dialog? {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onCreateDialog(arg0, arg1)
        } else {
            super.onCreateDialog(arg0, arg1)
        }
    }

    override fun onCreateDialog(arg0: Int): Dialog? {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onCreateDialog(arg0)
        } else {
            super.onCreateDialog(arg0)
        }
    }

    override fun onPrepareDialog(arg0: Int, arg1: Dialog, arg2: Bundle) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onPrepareDialog(arg0, arg1, arg2)
        } else {
            super.onPrepareDialog(arg0, arg1, arg2)
        }
    }

    override fun onPrepareDialog(arg0: Int, arg1: Dialog) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onPrepareDialog(arg0, arg1)
        } else {
            super.onPrepareDialog(arg0, arg1)
        }
    }

    override fun onApplyThemeResource(arg0: Resources.Theme, arg1: Int, arg2: Boolean) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onApplyThemeResource(arg0, arg1, arg2)
        } else {
            super.onApplyThemeResource(arg0, arg1, arg2)
        }
    }

    override fun onActivityResult(arg0: Int, arg1: Int, arg2: Intent) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onActivityResult(arg0, arg1, arg2)
        } else {
            super.onActivityResult(arg0, arg1, arg2)
        }
    }

    override fun onTitleChanged(arg0: CharSequence, arg1: Int) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onTitleChanged(arg0, arg1)
        } else {
            super.onTitleChanged(arg0, arg1)
        }
    }

    override fun onChildTitleChanged(arg0: Activity, arg1: CharSequence) {
        if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.onChildTitleChanged(arg0, arg1)
        } else {
            super.onChildTitleChanged(arg0, arg1)
        }
    }

    override fun dispatchKeyEvent(arg0: KeyEvent): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.dispatchKeyEvent(arg0) ?: false
        } else {
            super.dispatchKeyEvent(arg0)
        }
    }

    override fun dispatchKeyShortcutEvent(arg0: KeyEvent): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.dispatchKeyShortcutEvent(arg0)
        } else {
            super.dispatchKeyShortcutEvent(arg0)
        }
    }

    override fun dispatchTouchEvent(arg0: MotionEvent): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.dispatchTouchEvent(arg0)
        } else {
            super.dispatchTouchEvent(arg0)
        }
    }

    override fun dispatchTrackballEvent(arg0: MotionEvent): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.dispatchTrackballEvent(arg0)
        } else {
            super.dispatchTrackballEvent(arg0)
        }
    }

    override fun dispatchGenericMotionEvent(arg0: MotionEvent): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.dispatchGenericMotionEvent(arg0)
        } else {
            super.dispatchGenericMotionEvent(arg0)
        }
    }

    override fun dispatchPopulateAccessibilityEvent(arg0: AccessibilityEvent): Boolean {
        return if (pluginActivityMethodInterface != null) {
            pluginActivityMethodInterface!!.dispatchPopulateAccessibilityEvent(arg0)
        } else {
            super.dispatchPopulateAccessibilityEvent(arg0)
        }
    }

    override fun superIsChangingConfigurations(): Boolean {
        return super.isChangingConfigurations()
    }

    override fun superFinish() {
        super.finish()
    }

    override fun superStartActivityFromChild(arg0: Activity, arg1: Intent, arg2: Int) {
        super.startActivityFromChild(arg0, arg1, arg2)
    }

    override fun superGetClassLoader(): ClassLoader {
        return super.getClassLoader()
    }

    override fun superGetLayoutInflater(): LayoutInflater {
        return super.getLayoutInflater()
    }

    override fun superGetResources(): Resources {
        return super.getResources()
    }

    override fun superRecreate() {
        super.recreate()
    }

    override fun superGetCallingActivity(): ComponentName? {
        return super.getCallingActivity()
    }

    override fun superOnKeyMultiple(arg0: Int, arg1: Int, arg2: KeyEvent): Boolean {
        return super.onKeyMultiple(arg0, arg1, arg2)
    }

    override fun superOnKeyDown(arg0: Int, arg1: KeyEvent): Boolean {
        return super.onKeyDown(arg0, arg1)
    }

    override fun superOnKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        return super.onKeyUp(keyCode, event)
    }

    override fun superOnKeyLongPress(arg0: Int, arg1: KeyEvent): Boolean {
        return super.onKeyLongPress(arg0, arg1)
    }

    override fun superOnRestoreInstanceState(arg0: Bundle?, arg1: PersistableBundle?) {
        super.onRestoreInstanceState(arg0, arg1)
    }

    override fun superOnPostCreate(arg0: Bundle?, arg1: PersistableBundle?) {
        super.onPostCreate(arg0, arg1)
    }

    override fun superOnStateNotSaved() {
        super.onStateNotSaved()
    }

    override fun superOnTopResumedActivityChanged(arg0: Boolean) {
        super.onTopResumedActivityChanged(arg0)
    }

    override fun superOnLocalVoiceInteractionStarted() {
        super.onLocalVoiceInteractionStarted()
    }

    override fun superOnLocalVoiceInteractionStopped() {
        super.onLocalVoiceInteractionStopped()
    }

    override fun superOnSaveInstanceState(arg0: Bundle, arg1: PersistableBundle) {
        super.onSaveInstanceState(arg0, arg1)
    }

    override fun superOnCreateThumbnail(arg0: Bitmap, arg1: Canvas): Boolean {
        return super.onCreateThumbnail(arg0, arg1)
    }

    override fun superOnCreateDescription(): CharSequence? {
        return super.onCreateDescription()
    }

    override fun superOnProvideAssistData(arg0: Bundle) {
        super.onProvideAssistData(arg0)
    }

    override fun superOnProvideAssistContent(arg0: AssistContent) {
        super.onProvideAssistContent(arg0)
    }

    override fun superOnGetDirectActions(
        arg0: CancellationSignal,
        arg1: Consumer<List<DirectAction>>
    ) {
        super.onGetDirectActions(arg0, arg1)
    }

    override fun superOnPerformDirectAction(
        arg0: String, arg1: Bundle, arg2: CancellationSignal,
        arg3: Consumer<Bundle>
    ) {
        super.onPerformDirectAction(arg0, arg1, arg2, arg3)
    }

    override fun superOnCreate(arg0: Bundle?, arg1: PersistableBundle?) {
        super.onCreate(arg0, arg1)
    }

    override fun superOnActionModeStarted(arg0: ActionMode) {
        super.onActionModeStarted(arg0)
    }

    override fun superOnWindowStartingActionMode(
        arg0: ActionMode.Callback,
        arg1: Int
    ): ActionMode? {
        return super.onWindowStartingActionMode(arg0, arg1)
    }

    override fun superOnWindowStartingActionMode(arg0: ActionMode.Callback): ActionMode? {
        return super.onWindowStartingActionMode(arg0)
    }

    override fun superOnActionModeFinished(arg0: ActionMode) {
        super.onActionModeFinished(arg0)
    }

    override fun superOnAttachFragment(arg0: Fragment) {
        super.onAttachFragment(arg0)
    }

    override fun superOnRetainNonConfigurationInstance(): Any? {
        return super.onRetainNonConfigurationInstance()
    }

    override fun superOnTrimMemory(arg0: Int) {
        super.onTrimMemory(arg0)
    }

    override fun superOnLowMemory() {
        super.onLowMemory()
    }

    override fun superOnProvideKeyboardShortcuts(
        arg0: List<KeyboardShortcutGroup>, arg1: Menu?,
        arg2: Int
    ) {
        super.onProvideKeyboardShortcuts(arg0, arg1, arg2)
    }

    override fun superOnMultiWindowModeChanged(arg0: Boolean) {
        super.onMultiWindowModeChanged(arg0)
    }

    override fun superOnMultiWindowModeChanged(arg0: Boolean, arg1: Configuration) {
        super.onMultiWindowModeChanged(arg0, arg1)
    }

    override fun superOnBackPressed() {
        super.onBackPressed()
    }

    override fun superOnConfigurationChanged(arg0: Configuration) {
        super.onConfigurationChanged(arg0)
    }

    override fun superOnPictureInPictureModeChanged(arg0: Boolean) {
        super.onPictureInPictureModeChanged(arg0)
    }

    override fun superOnPictureInPictureModeChanged(arg0: Boolean, arg1: Configuration) {
        super.onPictureInPictureModeChanged(arg0, arg1)
    }

    override fun superOnKeyShortcut(arg0: Int, arg1: KeyEvent): Boolean {
        return super.onKeyShortcut(arg0, arg1)
    }

    override fun superOnTouchEvent(arg0: MotionEvent): Boolean {
        return super.onTouchEvent(arg0)
    }

    override fun superOnTrackballEvent(arg0: MotionEvent): Boolean {
        return super.onTrackballEvent(arg0)
    }

    override fun superOnGenericMotionEvent(arg0: MotionEvent): Boolean {
        return super.onGenericMotionEvent(arg0)
    }

    override fun superOnUserInteraction() {
        super.onUserInteraction()
    }

    override fun superOnWindowAttributesChanged(arg0: WindowManager.LayoutParams) {
        super.onWindowAttributesChanged(arg0)
    }

    override fun superOnContentChanged() {
        super.onContentChanged()
    }

    override fun superOnWindowFocusChanged(arg0: Boolean) {
        super.onWindowFocusChanged(arg0)
    }

    override fun superOnAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun superOnDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    override fun superOnCreatePanelView(arg0: Int): View? {
        return super.onCreatePanelView(arg0)
    }

    override fun superOnCreatePanelMenu(arg0: Int, arg1: Menu): Boolean {
        return super.onCreatePanelMenu(arg0, arg1)
    }

    override fun superOnPreparePanel(arg0: Int, arg1: View?, arg2: Menu): Boolean {
        return super.onPreparePanel(arg0, arg1, arg2)
    }

    override fun superOnMenuOpened(arg0: Int, arg1: Menu): Boolean {
        return super.onMenuOpened(arg0, arg1)
    }

    override fun superOnMenuItemSelected(arg0: Int, arg1: MenuItem): Boolean {
        return super.onMenuItemSelected(arg0, arg1)
    }

    override fun superOnPanelClosed(arg0: Int, arg1: Menu) {
        super.onPanelClosed(arg0, arg1)
    }

    override fun superOnCreateOptionsMenu(arg0: Menu): Boolean {
        return super.onCreateOptionsMenu(arg0)
    }

    override fun superOnPrepareOptionsMenu(arg0: Menu): Boolean {
        return super.onPrepareOptionsMenu(arg0)
    }

    override fun superOnOptionsItemSelected(arg0: MenuItem): Boolean {
        return super.onOptionsItemSelected(arg0)
    }

    override fun superOnNavigateUp(): Boolean {
        return super.onNavigateUp()
    }

    override fun superOnNavigateUpFromChild(arg0: Activity): Boolean {
        return super.onNavigateUpFromChild(arg0)
    }

    override fun superOnCreateNavigateUpTaskStack(arg0: TaskStackBuilder) {
        super.onCreateNavigateUpTaskStack(arg0)
    }

    override fun superOnPrepareNavigateUpTaskStack(arg0: TaskStackBuilder) {
        super.onPrepareNavigateUpTaskStack(arg0)
    }

    override fun superOnOptionsMenuClosed(arg0: Menu) {
        super.onOptionsMenuClosed(arg0)
    }

    override fun superOnCreateContextMenu(
        arg0: ContextMenu, arg1: View,
        arg2: ContextMenu.ContextMenuInfo
    ) {
        super.onCreateContextMenu(arg0, arg1, arg2)
    }

    override fun superOnContextItemSelected(arg0: MenuItem): Boolean {
        return super.onContextItemSelected(arg0)
    }

    override fun superOnContextMenuClosed(arg0: Menu) {
        super.onContextMenuClosed(arg0)
    }

    override fun superOnSearchRequested(): Boolean {
        return super.onSearchRequested()
    }

    override fun superOnSearchRequested(arg0: SearchEvent?): Boolean {
        return super.onSearchRequested(arg0)
    }

    override fun superOnRequestPermissionsResult(arg0: Int, arg1: Array<String>, arg2: IntArray) {
        super.onRequestPermissionsResult(arg0, arg1, arg2)
    }

    override fun superOnProvideReferrer(): Uri? {
        return super.onProvideReferrer()
    }

    override fun superOnActivityReenter(arg0: Int, arg1: Intent) {
        super.onActivityReenter(arg0, arg1)
    }

    override fun superOnCreateView(
        arg0: View?,
        arg1: String,
        arg2: Context,
        arg3: AttributeSet
    ): View? {
        return super.onCreateView(arg0, arg1, arg2, arg3)
    }

    override fun superOnCreateView(arg0: String, arg1: Context, arg2: AttributeSet): View? {
        return super.onCreateView(arg0, arg1, arg2)
    }

    override fun superOnVisibleBehindCanceled() {
        super.onVisibleBehindCanceled()
    }

    override fun superOnEnterAnimationComplete() {
        super.onEnterAnimationComplete()
    }

    override fun superOnPointerCaptureChanged(arg0: Boolean) {
        super.onPointerCaptureChanged(arg0)
    }

    override fun superOnStop() {
        super.onStop()
    }

    override fun superOnStart() {
        super.onStart()
    }

    override fun superOnPause() {
        super.onPause()
    }

    override fun superOnResume() {
        super.onResume()
    }

    override fun superOnRestoreInstanceState(arg0: Bundle) {
        super.onRestoreInstanceState(arg0)
    }

    override fun superOnPostCreate(arg0: Bundle?) {
        super.onPostCreate(arg0)
    }

    override fun superOnRestart() {
        super.onRestart()
    }

    override fun superOnPostResume() {
        super.onPostResume()
    }

    override fun superOnNewIntent(arg0: Intent) {
        super.onNewIntent(arg0)
    }

    override fun superOnSaveInstanceState(arg0: Bundle) {
        super.onSaveInstanceState(arg0)
    }

    override fun superOnUserLeaveHint() {
        super.onUserLeaveHint()
    }

    override fun superOnDestroy() {
        super.onDestroy()
    }

    override fun superOnCreate(arg0: Bundle?) {
        super.onCreate(arg0)
    }

    override fun superOnCreateDialog(arg0: Int, arg1: Bundle): Dialog? {
        return super.onCreateDialog(arg0, arg1)
    }

    override fun superOnCreateDialog(arg0: Int): Dialog {
        return super.onCreateDialog(arg0)
    }

    override fun superOnPrepareDialog(arg0: Int, arg1: Dialog, arg2: Bundle) {
        super.onPrepareDialog(arg0, arg1, arg2)
    }

    override fun superOnPrepareDialog(arg0: Int, arg1: Dialog) {
        super.onPrepareDialog(arg0, arg1)
    }

    override fun superOnApplyThemeResource(arg0: Resources.Theme, arg1: Int, arg2: Boolean) {
        super.onApplyThemeResource(arg0, arg1, arg2)
    }

    override fun superOnActivityResult(arg0: Int, arg1: Int, arg2: Intent) {
        super.onActivityResult(arg0, arg1, arg2)
    }

    override fun superOnTitleChanged(arg0: CharSequence, arg1: Int) {
        super.onTitleChanged(arg0, arg1)
    }

    override fun superOnChildTitleChanged(arg0: Activity, arg1: CharSequence) {
        super.onChildTitleChanged(arg0, arg1)
    }

    override fun superDispatchKeyEvent(arg0: KeyEvent): Boolean {
        return super.dispatchKeyEvent(arg0)
    }

    override fun superDispatchKeyShortcutEvent(arg0: KeyEvent): Boolean {
        return super.dispatchKeyShortcutEvent(arg0)
    }

    override fun superDispatchTouchEvent(arg0: MotionEvent): Boolean {
        return super.dispatchTouchEvent(arg0)
    }

    override fun superDispatchTrackballEvent(arg0: MotionEvent): Boolean {
        return super.dispatchTrackballEvent(arg0)
    }

    override fun superDispatchGenericMotionEvent(arg0: MotionEvent): Boolean {
        return super.dispatchGenericMotionEvent(arg0)
    }

    override fun superDispatchPopulateAccessibilityEvent(arg0: AccessibilityEvent): Boolean {
        return super.dispatchPopulateAccessibilityEvent(arg0)
    }

    override fun superGetParent(): Activity {
        return super.getParent()
    }

    override fun superIsDestroyed(): Boolean {
        return super.isDestroyed()
    }

    override fun superGetTaskId(): Int {
        return super.getTaskId()
    }

    override fun superDump(
        arg0: String,
        arg1: FileDescriptor,
        arg2: PrintWriter,
        arg3: Array<String>
    ) {
        super.dump(arg0, arg1, arg2, arg3)
    }

    override fun superGetActionBar(): ActionBar? {
        return super.getActionBar()
    }

    override fun <T : View> superRequireViewById(arg0: Int): T {
        return super.requireViewById(arg0)
    }

    override fun superSetFinishOnTouchOutside(arg0: Boolean) {
        super.setFinishOnTouchOutside(arg0)
    }

    override fun superAddContentView(arg0: View, arg1: ViewGroup.LayoutParams) {
        super.addContentView(arg0, arg1)
    }

    override fun superSetResult(arg0: Int, arg1: Intent) {
        super.setResult(arg0, arg1)
    }

    override fun superSetResult(arg0: Int) {
        super.setResult(arg0)
    }

    override fun superSetContentView(arg0: View, arg1: ViewGroup.LayoutParams) {
        super.setContentView(arg0, arg1)
    }

    override fun superSetContentView(arg0: Int) {
        super.setContentView(arg0)
    }

    override fun superSetContentView(arg0: View) {
        super.setContentView(arg0)
    }

    override fun <T : View> superFindViewById(arg0: Int): T {
        return super.findViewById(arg0)
    }

    override fun superManagedQuery(
        arg0: Uri, arg1: Array<String>, arg2: String, arg3: Array<String>,
        arg4: String
    ): Cursor {
        return super.managedQuery(arg0, arg1, arg2, arg3, arg4)
    }

    override fun superSetPictureInPictureParams(arg0: PictureInPictureParams) {
        super.setPictureInPictureParams(arg0)
    }

    override fun superGetApplication(): Application {
        return super.getApplication()
    }

    override fun superGetWindowManager(): WindowManager {
        return super.getWindowManager()
    }

    override fun superGetWindow(): Window {
        return super.getWindow()
    }

    override fun superGetLoaderManager(): LoaderManager {
        return super.getLoaderManager()
    }

    override fun superGetCurrentFocus(): View? {
        return super.getCurrentFocus()
    }

    override fun superGetIntent(): Intent {
        return super.getIntent()
    }

    override fun superSetIntent(arg0: Intent) {
        super.setIntent(arg0)
    }

    override fun superIsChild(): Boolean {
        return super.isChild()
    }

    override fun superRegisterActivityLifecycleCallbacks(arg0: Application.ActivityLifecycleCallbacks) {
        super.registerActivityLifecycleCallbacks(arg0)
    }

    override fun superUnregisterActivityLifecycleCallbacks(arg0: Application.ActivityLifecycleCallbacks) {
        super.unregisterActivityLifecycleCallbacks(arg0)
    }

    override fun superIsVoiceInteraction(): Boolean {
        return super.isVoiceInteraction()
    }

    override fun superIsVoiceInteractionRoot(): Boolean {
        return super.isVoiceInteractionRoot()
    }

    override fun superGetVoiceInteractor(): VoiceInteractor {
        return super.getVoiceInteractor()
    }

    override fun superIsLocalVoiceInteractionSupported(): Boolean {
        return super.isLocalVoiceInteractionSupported()
    }

    override fun superStartLocalVoiceInteraction(arg0: Bundle) {
        super.startLocalVoiceInteraction(arg0)
    }

    override fun superStopLocalVoiceInteraction() {
        super.stopLocalVoiceInteraction()
    }

    override fun superGetComponentName(): ComponentName {
        return super.getComponentName()
    }

    override fun superDismissKeyboardShortcutsHelper() {
        super.dismissKeyboardShortcutsHelper()
    }

    override fun superStartActivityFromChild(
        arg0: Activity,
        arg1: Intent,
        arg2: Int,
        arg3: Bundle
    ) {
        super.startActivityFromChild(arg0, arg1, arg2, arg3)
    }

    override fun superStartActionMode(arg0: ActionMode.Callback, arg1: Int): ActionMode? {
        return super.startActionMode(arg0, arg1)
    }

    override fun superStartActionMode(arg0: ActionMode.Callback): ActionMode? {
        return super.startActionMode(arg0)
    }

    override fun superShouldUpRecreateTask(arg0: Intent): Boolean {
        return super.shouldUpRecreateTask(arg0)
    }

    override fun superNavigateUpTo(arg0: Intent): Boolean {
        return super.navigateUpTo(arg0)
    }

    override fun superNavigateUpToFromChild(arg0: Activity, arg1: Intent): Boolean {
        return super.navigateUpToFromChild(arg0, arg1)
    }

    override fun superGetParentActivityIntent(): Intent? {
        return super.getParentActivityIntent()
    }

    override fun superSetEnterSharedElementCallback(arg0: SharedElementCallback) {
        super.setEnterSharedElementCallback(arg0)
    }

    override fun superSetExitSharedElementCallback(arg0: SharedElementCallback) {
        super.setExitSharedElementCallback(arg0)
    }

    override fun superPostponeEnterTransition() {
        super.postponeEnterTransition()
    }

    override fun superStartPostponedEnterTransition() {
        super.startPostponedEnterTransition()
    }

    override fun superRequestDragAndDropPermissions(arg0: DragEvent): DragAndDropPermissions {
        return super.requestDragAndDropPermissions(arg0)
    }

    override fun superStartLockTask() {
        super.startLockTask()
    }

    override fun superStopLockTask() {
        super.stopLockTask()
    }

    override fun superShowLockTaskEscapeMessage() {
        super.showLockTaskEscapeMessage()
    }

    override fun superSetShowWhenLocked(arg0: Boolean) {
        super.setShowWhenLocked(arg0)
    }

    override fun superSetInheritShowWhenLocked(arg0: Boolean) {
        super.setInheritShowWhenLocked(arg0)
    }

    override fun superSetTurnScreenOn(arg0: Boolean) {
        super.setTurnScreenOn(arg0)
    }

    override fun superSetVisible(arg0: Boolean) {
        super.setVisible(arg0)
    }

    override fun superStartManagingCursor(arg0: Cursor) {
        super.startManagingCursor(arg0)
    }

    override fun superGetLastNonConfigurationInstance(): Any? {
        return super.getLastNonConfigurationInstance()
    }

    override fun superGetChangingConfigurations(): Int {
        return super.getChangingConfigurations()
    }

    override fun superShowAssist(arg0: Bundle): Boolean {
        return super.showAssist(arg0)
    }

    override fun superReportFullyDrawn() {
        super.reportFullyDrawn()
    }

    override fun superRequestShowKeyboardShortcuts() {
        super.requestShowKeyboardShortcuts()
    }

    override fun superSetTitle(arg0: CharSequence) {
        super.setTitle(arg0)
    }

    override fun superSetTitle(arg0: Int) {
        super.setTitle(arg0)
    }

    override fun superGetTitle(): CharSequence {
        return super.getTitle()
    }

    override fun superGetContentTransitionManager(): TransitionManager {
        return super.getContentTransitionManager()
    }

    override fun superSetDefaultKeyMode(arg0: Int) {
        super.setDefaultKeyMode(arg0)
    }

    override fun superSetActionBar(arg0: Toolbar) {
        super.setActionBar(arg0)
    }

    override fun superStopManagingCursor(arg0: Cursor) {
        super.stopManagingCursor(arg0)
    }

    override fun superIsInPictureInPictureMode(): Boolean {
        return super.isInPictureInPictureMode()
    }

    override fun superGetFragmentManager(): FragmentManager {
        return super.getFragmentManager()
    }

    override fun superEnterPictureInPictureMode() {
        super.enterPictureInPictureMode()
    }

    override fun superEnterPictureInPictureMode(arg0: PictureInPictureParams): Boolean {
        return super.enterPictureInPictureMode(arg0)
    }

    override fun superGetMaxNumPictureInPictureActions(): Int {
        return super.getMaxNumPictureInPictureActions()
    }

    override fun superIsInMultiWindowMode(): Boolean {
        return super.isInMultiWindowMode()
    }

    override fun superSetContentTransitionManager(arg0: TransitionManager) {
        super.setContentTransitionManager(arg0)
    }

    override fun superGetContentScene(): Scene {
        return super.getContentScene()
    }

    override fun superHasWindowFocus(): Boolean {
        return super.hasWindowFocus()
    }

    override fun superInvalidateOptionsMenu() {
        super.invalidateOptionsMenu()
    }

    override fun superOpenOptionsMenu() {
        super.openOptionsMenu()
    }

    override fun superCloseOptionsMenu() {
        super.closeOptionsMenu()
    }

    override fun superRegisterForContextMenu(arg0: View) {
        super.registerForContextMenu(arg0)
    }

    override fun superUnregisterForContextMenu(arg0: View) {
        super.unregisterForContextMenu(arg0)
    }

    override fun superOpenContextMenu(arg0: View) {
        super.openContextMenu(arg0)
    }

    override fun superCloseContextMenu() {
        super.closeContextMenu()
    }

    override fun superShowDialog(arg0: Int, arg1: Bundle): Boolean {
        return super.showDialog(arg0, arg1)
    }

    override fun superShowDialog(arg0: Int) {
        super.showDialog(arg0)
    }

    override fun superDismissDialog(arg0: Int) {
        super.dismissDialog(arg0)
    }

    override fun superRemoveDialog(arg0: Int) {
        super.removeDialog(arg0)
    }

    override fun superGetSearchEvent(): SearchEvent {
        return super.getSearchEvent()
    }

    override fun superStartSearch(arg0: String, arg1: Boolean, arg2: Bundle, arg3: Boolean) {
        super.startSearch(arg0, arg1, arg2, arg3)
    }

    override fun superTriggerSearch(arg0: String, arg1: Bundle) {
        super.triggerSearch(arg0, arg1)
    }

    override fun superTakeKeyEvents(arg0: Boolean) {
        super.takeKeyEvents(arg0)
    }

    override fun superRequestWindowFeature(arg0: Int): Boolean {
        return super.requestWindowFeature(arg0)
    }

    override fun superSetFeatureDrawableResource(arg0: Int, arg1: Int) {
        super.setFeatureDrawableResource(arg0, arg1)
    }

    override fun superSetFeatureDrawableUri(arg0: Int, arg1: Uri) {
        super.setFeatureDrawableUri(arg0, arg1)
    }

    override fun superSetFeatureDrawable(arg0: Int, arg1: Drawable) {
        super.setFeatureDrawable(arg0, arg1)
    }

    override fun superSetFeatureDrawableAlpha(arg0: Int, arg1: Int) {
        super.setFeatureDrawableAlpha(arg0, arg1)
    }

    override fun superGetMenuInflater(): MenuInflater {
        return super.getMenuInflater()
    }

    override fun superSetTheme(arg0: Int) {
        super.setTheme(arg0)
    }

    override fun superRequestPermissions(arg0: Array<String>, arg1: Int) {
        super.requestPermissions(arg0, arg1)
    }

    override fun superShouldShowRequestPermissionRationale(arg0: String): Boolean {
        return super.shouldShowRequestPermissionRationale(arg0)
    }

    override fun superStartActivityForResult(arg0: Intent, arg1: Int) {
        super.startActivityForResult(arg0, arg1)
    }

    override fun superStartActivityForResult(arg0: Intent, arg1: Int, arg2: Bundle) {
        super.startActivityForResult(arg0, arg1, arg2)
    }

    override fun superIsActivityTransitionRunning(): Boolean {
        return super.isActivityTransitionRunning()
    }

    @Throws(IntentSender.SendIntentException::class)
    override fun superStartIntentSenderForResult(
        arg0: IntentSender, arg1: Int, arg2: Intent, arg3: Int,
        arg4: Int, arg5: Int, arg6: Bundle
    ) {
        super.startIntentSenderForResult(arg0, arg1, arg2, arg3, arg4, arg5, arg6)
    }

    @Throws(IntentSender.SendIntentException::class)
    override fun superStartIntentSenderForResult(
        arg0: IntentSender, arg1: Int, arg2: Intent, arg3: Int,
        arg4: Int, arg5: Int
    ) {
        super.startIntentSenderForResult(arg0, arg1, arg2, arg3, arg4, arg5)
    }

    override fun superStartActivity(arg0: Intent, arg1: Bundle) {
        super.startActivity(arg0, arg1)
    }

    override fun superStartActivity(arg0: Intent) {
        super.startActivity(arg0)
    }

    override fun superStartActivities(arg0: Array<Intent>, arg1: Bundle) {
        super.startActivities(arg0, arg1)
    }

    override fun superStartActivities(arg0: Array<Intent>) {
        super.startActivities(arg0)
    }

    @Throws(IntentSender.SendIntentException::class)
    override fun superStartIntentSender(
        arg0: IntentSender,
        arg1: Intent,
        arg2: Int,
        arg3: Int,
        arg4: Int
    ) {
        super.startIntentSender(arg0, arg1, arg2, arg3, arg4)
    }

    @Throws(IntentSender.SendIntentException::class)
    override fun superStartIntentSender(
        arg0: IntentSender, arg1: Intent, arg2: Int, arg3: Int, arg4: Int,
        arg5: Bundle
    ) {
        super.startIntentSender(arg0, arg1, arg2, arg3, arg4, arg5)
    }

    override fun superStartActivityIfNeeded(arg0: Intent, arg1: Int): Boolean {
        return super.startActivityIfNeeded(arg0, arg1)
    }

    override fun superStartActivityIfNeeded(arg0: Intent, arg1: Int, arg2: Bundle): Boolean {
        return super.startActivityIfNeeded(arg0, arg1, arg2)
    }

    override fun superStartNextMatchingActivity(arg0: Intent, arg1: Bundle): Boolean {
        return super.startNextMatchingActivity(arg0, arg1)
    }

    override fun superStartNextMatchingActivity(arg0: Intent): Boolean {
        return super.startNextMatchingActivity(arg0)
    }

    override fun superStartActivityFromFragment(arg0: Fragment, arg1: Intent, arg2: Int) {
        super.startActivityFromFragment(arg0, arg1, arg2)
    }

    override fun superStartActivityFromFragment(
        arg0: Fragment,
        arg1: Intent,
        arg2: Int,
        arg3: Bundle
    ) {
        super.startActivityFromFragment(arg0, arg1, arg2, arg3)
    }

    @Throws(IntentSender.SendIntentException::class)
    override fun superStartIntentSenderFromChild(
        arg0: Activity, arg1: IntentSender, arg2: Int,
        arg3: Intent, arg4: Int, arg5: Int, arg6: Int, arg7: Bundle
    ) {
        super.startIntentSenderFromChild(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7)
    }

    @Throws(IntentSender.SendIntentException::class)
    override fun superStartIntentSenderFromChild(
        arg0: Activity, arg1: IntentSender, arg2: Int,
        arg3: Intent, arg4: Int, arg5: Int, arg6: Int
    ) {
        super.startIntentSenderFromChild(arg0, arg1, arg2, arg3, arg4, arg5, arg6)
    }

    override fun superOverridePendingTransition(arg0: Int, arg1: Int) {
        super.overridePendingTransition(arg0, arg1)
    }

    override fun superGetReferrer(): Uri? {
        return super.getReferrer()
    }

    override fun superGetCallingPackage(): String? {
        return super.getCallingPackage()
    }

    override fun superIsFinishing(): Boolean {
        return super.isFinishing()
    }

    override fun superFinishAffinity() {
        super.finishAffinity()
    }

    override fun superFinishFromChild(arg0: Activity) {
        super.finishFromChild(arg0)
    }

    override fun superFinishAfterTransition() {
        super.finishAfterTransition()
    }

    override fun superFinishActivity(arg0: Int) {
        super.finishActivity(arg0)
    }

    override fun superFinishActivityFromChild(arg0: Activity, arg1: Int) {
        super.finishActivityFromChild(arg0, arg1)
    }

    override fun superFinishAndRemoveTask() {
        super.finishAndRemoveTask()
    }

    override fun superReleaseInstance(): Boolean {
        return super.releaseInstance()
    }

    override fun superCreatePendingResult(arg0: Int, arg1: Intent, arg2: Int): PendingIntent {
        return super.createPendingResult(arg0, arg1, arg2)
    }

    override fun superSetRequestedOrientation(arg0: Int) {
        super.setRequestedOrientation(arg0)
    }

    override fun superGetRequestedOrientation(): Int {
        return super.getRequestedOrientation()
    }

    override fun superIsTaskRoot(): Boolean {
        return super.isTaskRoot()
    }

    override fun superMoveTaskToBack(arg0: Boolean): Boolean {
        return super.moveTaskToBack(arg0)
    }

    override fun superGetLocalClassName(): String {
        return super.getLocalClassName()
    }

    override fun superGetPreferences(arg0: Int): SharedPreferences {
        return super.getPreferences(arg0)
    }

    override fun superGetSystemService(arg0: String): Any? {
        return super.getSystemService(arg0)
    }

    override fun superSetTitleColor(arg0: Int) {
        super.setTitleColor(arg0)
    }

    override fun superGetTitleColor(): Int {
        return super.getTitleColor()
    }

    override fun superSetTaskDescription(arg0: ActivityManager.TaskDescription) {
        super.setTaskDescription(arg0)
    }

    override fun superSetProgressBarVisibility(arg0: Boolean) {
        super.setProgressBarVisibility(arg0)
    }

    override fun superSetProgressBarIndeterminateVisibility(arg0: Boolean) {
        super.setProgressBarIndeterminateVisibility(arg0)
    }

    override fun superSetProgressBarIndeterminate(arg0: Boolean) {
        super.setProgressBarIndeterminate(arg0)
    }

    override fun superSetProgress(arg0: Int) {
        super.setProgress(arg0)
    }

    override fun superSetSecondaryProgress(arg0: Int) {
        super.setSecondaryProgress(arg0)
    }

    override fun superSetVolumeControlStream(arg0: Int) {
        super.setVolumeControlStream(arg0)
    }

    override fun superGetVolumeControlStream(): Int {
        return super.getVolumeControlStream()
    }

    override fun superSetMediaController(arg0: MediaController) {
        super.setMediaController(arg0)
    }

    override fun superGetMediaController(): MediaController {
        return super.getMediaController()
    }

    override fun superRunOnUiThread(arg0: Runnable) {
        super.runOnUiThread(arg0)
    }

    override fun superIsImmersive(): Boolean {
        return super.isImmersive()
    }

    override fun superRequestVisibleBehind(arg0: Boolean): Boolean {
        return super.requestVisibleBehind(arg0)
    }

    override fun superSetImmersive(arg0: Boolean) {
        super.setImmersive(arg0)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun superSetVrModeEnabled(arg0: Boolean, arg1: ComponentName) {
        super.setVrModeEnabled(arg0, arg1)
    }

    override fun superApplyOverrideConfiguration(arg0: Configuration) {
        super.applyOverrideConfiguration(arg0)
    }

    override fun superGetAssets(): AssetManager {
        return super.getAssets()
    }

    override fun superGetTheme(): Resources.Theme {
        return super.getTheme()
    }

    override fun superSetTheme(arg0: Resources.Theme) {
        super.setTheme(arg0)
    }

    override fun superCheckPermission(arg0: String, arg1: Int, arg2: Int): Int {
        return super.checkPermission(arg0, arg1, arg2)
    }

    override fun superGetPackageName(): String {
        return super.getPackageName()
    }

    override fun superGetCacheDir(): File {
        return super.getCacheDir()
    }

    override fun superGetSystemServiceName(arg0: Class<*>): String? {
        return super.getSystemServiceName(arg0)
    }

    override fun superGetPackageCodePath(): String {
        return super.getPackageCodePath()
    }

    override fun superGetDir(arg0: String, arg1: Int): File {
        return super.getDir(arg0, arg1)
    }

    override fun superGetSharedPreferences(arg0: String, arg1: Int): SharedPreferences {
        return super.getSharedPreferences(arg0, arg1)
    }

    override fun superDeleteSharedPreferences(arg0: String): Boolean {
        return super.deleteSharedPreferences(arg0)
    }

    override fun superMoveSharedPreferencesFrom(arg0: Context, arg1: String): Boolean {
        return super.moveSharedPreferencesFrom(arg0, arg1)
    }

    @Throws(FileNotFoundException::class)
    override fun superOpenFileInput(arg0: String): FileInputStream {
        return super.openFileInput(arg0)
    }

    @Throws(FileNotFoundException::class)
    override fun superOpenFileOutput(arg0: String, arg1: Int): FileOutputStream {
        return super.openFileOutput(arg0, arg1)
    }

    override fun superGetFileStreamPath(arg0: String): File {
        return super.getFileStreamPath(arg0)
    }

    override fun superGetDataDir(): File {
        return super.getDataDir()
    }

    override fun superGetFilesDir(): File {
        return super.getFilesDir()
    }

    override fun superGetNoBackupFilesDir(): File {
        return super.getNoBackupFilesDir()
    }

    override fun superGetExternalFilesDir(arg0: String): File? {
        return super.getExternalFilesDir(arg0)
    }

    override fun superGetExternalFilesDirs(arg0: String): Array<File> {
        return super.getExternalFilesDirs(arg0)
    }

    override fun superGetObbDir(): File {
        return super.getObbDir()
    }

    override fun superGetObbDirs(): Array<File> {
        return super.getObbDirs()
    }

    override fun superGetCodeCacheDir(): File {
        return super.getCodeCacheDir()
    }

    override fun superGetExternalCacheDir(): File? {
        return super.getExternalCacheDir()
    }

    override fun superGetExternalCacheDirs(): Array<File> {
        return super.getExternalCacheDirs()
    }

    override fun superGetExternalMediaDirs(): Array<File> {
        return super.getExternalMediaDirs()
    }

    override fun superOpenOrCreateDatabase(
        arg0: String, arg1: Int,
        arg2: SQLiteDatabase.CursorFactory
    ): SQLiteDatabase {
        return super.openOrCreateDatabase(arg0, arg1, arg2)
    }

    override fun superOpenOrCreateDatabase(
        arg0: String, arg1: Int,
        arg2: SQLiteDatabase.CursorFactory, arg3: DatabaseErrorHandler
    ): SQLiteDatabase {
        return super.openOrCreateDatabase(arg0, arg1, arg2, arg3)
    }

    override fun superMoveDatabaseFrom(arg0: Context, arg1: String): Boolean {
        return super.moveDatabaseFrom(arg0, arg1)
    }

    override fun superDeleteDatabase(arg0: String): Boolean {
        return super.deleteDatabase(arg0)
    }

    override fun superGetDatabasePath(arg0: String): File {
        return super.getDatabasePath(arg0)
    }

    override fun superDatabaseList(): Array<String> {
        return super.databaseList()
    }

    override fun superGetWallpaper(): Drawable {
        return super.getWallpaper()
    }

    override fun superPeekWallpaper(): Drawable {
        return super.peekWallpaper()
    }

    override fun superGetWallpaperDesiredMinimumWidth(): Int {
        return super.getWallpaperDesiredMinimumWidth()
    }

    override fun superGetWallpaperDesiredMinimumHeight(): Int {
        return super.getWallpaperDesiredMinimumHeight()
    }

    @Throws(IOException::class)
    override fun superSetWallpaper(arg0: InputStream) {
        super.setWallpaper(arg0)
    }

    @Throws(IOException::class)
    override fun superSetWallpaper(arg0: Bitmap) {
        super.setWallpaper(arg0)
    }

    @Throws(IOException::class)
    override fun superClearWallpaper() {
        super.clearWallpaper()
    }

    override fun superSendBroadcast(arg0: Intent) {
        super.sendBroadcast(arg0)
    }

    override fun superSendBroadcast(arg0: Intent, arg1: String) {
        super.sendBroadcast(arg0, arg1)
    }

    override fun superSendOrderedBroadcast(
        arg0: Intent, arg1: String, arg2: BroadcastReceiver,
        arg3: Handler, arg4: Int, arg5: String, arg6: Bundle
    ) {
        super.sendOrderedBroadcast(arg0, arg1, arg2, arg3, arg4, arg5, arg6)
    }

    override fun superSendOrderedBroadcast(arg0: Intent, arg1: String) {
        super.sendOrderedBroadcast(arg0, arg1)
    }

    override fun superSendBroadcastAsUser(arg0: Intent, arg1: UserHandle, arg2: String) {
        super.sendBroadcastAsUser(arg0, arg1, arg2)
    }

    override fun superSendBroadcastAsUser(arg0: Intent, arg1: UserHandle) {
        super.sendBroadcastAsUser(arg0, arg1)
    }

    override fun superSendOrderedBroadcastAsUser(
        arg0: Intent, arg1: UserHandle, arg2: String,
        arg3: BroadcastReceiver, arg4: Handler, arg5: Int, arg6: String, arg7: Bundle
    ) {
        super.sendOrderedBroadcastAsUser(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7)
    }

    override fun superSendStickyBroadcast(arg0: Intent) {
        super.sendStickyBroadcast(arg0)
    }

    override fun superSendStickyOrderedBroadcast(
        arg0: Intent, arg1: BroadcastReceiver, arg2: Handler,
        arg3: Int, arg4: String, arg5: Bundle
    ) {
        super.sendStickyOrderedBroadcast(arg0, arg1, arg2, arg3, arg4, arg5)
    }

    override fun superRemoveStickyBroadcast(arg0: Intent) {
        super.removeStickyBroadcast(arg0)
    }

    override fun superSendStickyBroadcastAsUser(arg0: Intent, arg1: UserHandle) {
        super.sendStickyBroadcastAsUser(arg0, arg1)
    }

    override fun superSendStickyOrderedBroadcastAsUser(
        arg0: Intent, arg1: UserHandle,
        arg2: BroadcastReceiver, arg3: Handler, arg4: Int, arg5: String, arg6: Bundle
    ) {
        super.sendStickyOrderedBroadcastAsUser(arg0, arg1, arg2, arg3, arg4, arg5, arg6)
    }

    override fun superRemoveStickyBroadcastAsUser(arg0: Intent, arg1: UserHandle) {
        super.removeStickyBroadcastAsUser(arg0, arg1)
    }

    override fun superRegisterReceiver(
        arg0: BroadcastReceiver, arg1: IntentFilter, arg2: String,
        arg3: Handler
    ): Intent? {
        return super.registerReceiver(arg0, arg1, arg2, arg3)
    }

    override fun superRegisterReceiver(
        arg0: BroadcastReceiver,
        arg1: IntentFilter,
        arg2: Int
    ): Intent? {
        return super.registerReceiver(arg0, arg1, arg2)
    }

    override fun superRegisterReceiver(arg0: BroadcastReceiver, arg1: IntentFilter): Intent? {
        return super.registerReceiver(arg0, arg1)
    }

    override fun superRegisterReceiver(
        arg0: BroadcastReceiver, arg1: IntentFilter, arg2: String,
        arg3: Handler, arg4: Int
    ): Intent? {
        return super.registerReceiver(arg0, arg1, arg2, arg3, arg4)
    }

    override fun superUnregisterReceiver(arg0: BroadcastReceiver) {
        super.unregisterReceiver(arg0)
    }

    override fun superStartService(arg0: Intent): ComponentName? {
        return super.startService(arg0)
    }

    override fun superStartForegroundService(arg0: Intent): ComponentName? {
        return super.startForegroundService(arg0)
    }

    override fun superStopService(arg0: Intent): Boolean {
        return super.stopService(arg0)
    }

    override fun superBindService(
        arg0: Intent,
        arg1: Int,
        arg2: Executor,
        arg3: ServiceConnection
    ): Boolean {
        return super.bindService(arg0, arg1, arg2, arg3)
    }

    override fun superBindService(arg0: Intent, arg1: ServiceConnection, arg2: Int): Boolean {
        return super.bindService(arg0, arg1, arg2)
    }

    override fun superBindIsolatedService(
        arg0: Intent, arg1: Int, arg2: String, arg3: Executor,
        arg4: ServiceConnection
    ): Boolean {
        return super.bindIsolatedService(arg0, arg1, arg2, arg3, arg4)
    }

    override fun superUpdateServiceGroup(arg0: ServiceConnection, arg1: Int, arg2: Int) {
        super.updateServiceGroup(arg0, arg1, arg2)
    }

    override fun superUnbindService(arg0: ServiceConnection) {
        super.unbindService(arg0)
    }

    override fun superGetMainExecutor(): Executor {
        return super.getMainExecutor()
    }

    override fun superGetContentResolver(): ContentResolver {
        return super.getContentResolver()
    }

    override fun superGetPackageManager(): PackageManager {
        return super.getPackageManager()
    }

    override fun superGetBaseContext(): Context {
        return super.getBaseContext()
    }

    override fun superGetMainLooper(): Looper {
        return super.getMainLooper()
    }

    override fun superDeleteFile(arg0: String): Boolean {
        return super.deleteFile(arg0)
    }

    override fun superGetApplicationContext(): Context {
        return super.getApplicationContext()
    }

    override fun superGetApplicationInfo(): ApplicationInfo {
        return super.getApplicationInfo()
    }

    override fun superStartInstrumentation(
        arg0: ComponentName,
        arg1: String,
        arg2: Bundle
    ): Boolean {
        return super.startInstrumentation(arg0, arg1, arg2)
    }

    override fun superGetOpPackageName(): String {
        return super.getOpPackageName()
    }

    override fun superGetPackageResourcePath(): String {
        return super.getPackageResourcePath()
    }

    override fun superFileList(): Array<String> {
        return super.fileList()
    }

    override fun superCheckCallingPermission(arg0: String): Int {
        return super.checkCallingPermission(arg0)
    }

    override fun superCheckCallingOrSelfPermission(arg0: String): Int {
        return super.checkCallingOrSelfPermission(arg0)
    }

    override fun superCheckSelfPermission(arg0: String): Int {
        return super.checkSelfPermission(arg0)
    }

    override fun superEnforcePermission(arg0: String, arg1: Int, arg2: Int, arg3: String) {
        super.enforcePermission(arg0, arg1, arg2, arg3)
    }

    override fun superEnforceCallingPermission(arg0: String, arg1: String) {
        super.enforceCallingPermission(arg0, arg1)
    }

    override fun superEnforceCallingOrSelfPermission(arg0: String, arg1: String) {
        super.enforceCallingOrSelfPermission(arg0, arg1)
    }

    override fun superGrantUriPermission(arg0: String, arg1: Uri, arg2: Int) {
        super.grantUriPermission(arg0, arg1, arg2)
    }

    override fun superRevokeUriPermission(arg0: String, arg1: Uri, arg2: Int) {
        super.revokeUriPermission(arg0, arg1, arg2)
    }

    override fun superRevokeUriPermission(arg0: Uri, arg1: Int) {
        super.revokeUriPermission(arg0, arg1)
    }

    override fun superCheckUriPermission(arg0: Uri, arg1: Int, arg2: Int, arg3: Int): Int {
        return super.checkUriPermission(arg0, arg1, arg2, arg3)
    }

    override fun superCheckUriPermission(
        arg0: Uri, arg1: String, arg2: String, arg3: Int, arg4: Int,
        arg5: Int
    ): Int {
        return super.checkUriPermission(arg0, arg1, arg2, arg3, arg4, arg5)
    }

    override fun superCheckCallingUriPermission(arg0: Uri, arg1: Int): Int {
        return super.checkCallingUriPermission(arg0, arg1)
    }

    override fun superCheckCallingOrSelfUriPermission(arg0: Uri, arg1: Int): Int {
        return super.checkCallingOrSelfUriPermission(arg0, arg1)
    }

    override fun superEnforceUriPermission(
        arg0: Uri, arg1: String, arg2: String, arg3: Int, arg4: Int,
        arg5: Int, arg6: String
    ) {
        super.enforceUriPermission(arg0, arg1, arg2, arg3, arg4, arg5, arg6)
    }

    override fun superEnforceUriPermission(
        arg0: Uri,
        arg1: Int,
        arg2: Int,
        arg3: Int,
        arg4: String
    ) {
        super.enforceUriPermission(arg0, arg1, arg2, arg3, arg4)
    }

    override fun superEnforceCallingUriPermission(arg0: Uri, arg1: Int, arg2: String) {
        super.enforceCallingUriPermission(arg0, arg1, arg2)
    }

    override fun superEnforceCallingOrSelfUriPermission(arg0: Uri, arg1: Int, arg2: String) {
        super.enforceCallingOrSelfUriPermission(arg0, arg1, arg2)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun superCreatePackageContext(arg0: String, arg1: Int): Context {
        return super.createPackageContext(arg0, arg1)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun superCreateContextForSplit(arg0: String): Context {
        return super.createContextForSplit(arg0)
    }

    override fun superCreateConfigurationContext(arg0: Configuration): Context {
        return super.createConfigurationContext(arg0)
    }

    override fun superCreateDisplayContext(arg0: Display): Context {
        return super.createDisplayContext(arg0)
    }

    override fun superIsRestricted(): Boolean {
        return super.isRestricted()
    }

    override fun superCreateDeviceProtectedStorageContext(): Context {
        return super.createDeviceProtectedStorageContext()
    }

    override fun superIsDeviceProtectedStorage(): Boolean {
        return super.isDeviceProtectedStorage()
    }

    override fun superRegisterComponentCallbacks(arg0: ComponentCallbacks) {
        super.registerComponentCallbacks(arg0)
    }

    override fun superUnregisterComponentCallbacks(arg0: ComponentCallbacks) {
        super.unregisterComponentCallbacks(arg0)
    }

    override fun superAttachBaseContext(arg0: Context) {
        super.attachBaseContext(arg0)
    }

    public override fun attachBaseContext(arg0: Context) {
        super.attachBaseContext(arg0)
    }


}