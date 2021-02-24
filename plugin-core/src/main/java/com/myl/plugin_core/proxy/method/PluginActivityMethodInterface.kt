package com.myl.plugin_core.proxy.method

import android.app.Activity
import android.app.Dialog
import android.app.DirectAction
import android.app.Fragment
import android.app.TaskStackBuilder
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
import android.view.ActionMode
import android.view.ContextMenu
import android.view.KeyEvent
import android.view.KeyboardShortcutGroup
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.SearchEvent
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import java.util.function.Consumer

/**
 * 插件activity会调用的方法，在宿主中的代理接口，插件activity实现这个接口
 */
interface PluginActivityMethodInterface {
    fun isChangingConfigurations(): Boolean

    fun getClassLoader(): ClassLoader?

    fun getLayoutInflater(): LayoutInflater

    fun getResources(): Resources

    fun getCallingActivity(): ComponentName

    fun finish()

    fun startActivityFromChild(arg0: Activity, arg1: Intent, arg2: Int)

    fun recreate()

    fun onKeyMultiple(arg0: Int, arg1: Int, arg2: KeyEvent): Boolean

    fun onKeyDown(arg0: Int, arg1: KeyEvent): Boolean

    fun onKeyUp(arg0: Int, arg1: KeyEvent): Boolean

    fun onKeyLongPress(arg0: Int, arg1: KeyEvent): Boolean

    fun onRestoreInstanceState(arg0: Bundle?, arg1: PersistableBundle?)

    fun onPostCreate(arg0: Bundle?, arg1: PersistableBundle?)

    fun onStateNotSaved()

    fun onTopResumedActivityChanged(arg0: Boolean)

    fun onLocalVoiceInteractionStarted()

    fun onLocalVoiceInteractionStopped()

    fun onSaveInstanceState(arg0: Bundle, arg1: PersistableBundle)

    fun onCreateThumbnail(arg0: Bitmap, arg1: Canvas): Boolean

    fun onCreateDescription(): CharSequence?

    fun onProvideAssistData(arg0: Bundle)

    fun onProvideAssistContent(arg0: AssistContent)

    fun onGetDirectActions(arg0: CancellationSignal, arg1: Consumer<List<DirectAction>>)

    fun onPerformDirectAction(
        arg0: String, arg1: Bundle, arg2: CancellationSignal,
        arg3: Consumer<Bundle>
    )

    fun onCreate(arg0: Bundle?, arg1: PersistableBundle?)

    fun onActionModeStarted(arg0: ActionMode)

    fun onWindowStartingActionMode(arg0: ActionMode.Callback, arg1: Int): ActionMode?

    fun onWindowStartingActionMode(arg0: ActionMode.Callback): ActionMode?

    fun onActionModeFinished(arg0: ActionMode)

    fun onAttachFragment(arg0: Fragment)

    fun onRetainNonConfigurationInstance(): Any?

    fun onTrimMemory(arg0: Int)

    fun onLowMemory()

    fun onProvideKeyboardShortcuts(arg0: List<KeyboardShortcutGroup>, arg1: Menu?, arg2: Int)

    fun onMultiWindowModeChanged(arg0: Boolean)

    fun onMultiWindowModeChanged(arg0: Boolean, arg1: Configuration)

    fun onBackPressed()

    fun onConfigurationChanged(arg0: Configuration)

    fun onPictureInPictureModeChanged(arg0: Boolean)

    fun onPictureInPictureModeChanged(arg0: Boolean, arg1: Configuration)

    fun onKeyShortcut(arg0: Int, arg1: KeyEvent): Boolean

    fun onTouchEvent(arg0: MotionEvent): Boolean

    fun onTrackballEvent(arg0: MotionEvent): Boolean

    fun onGenericMotionEvent(arg0: MotionEvent): Boolean

    fun onUserInteraction()

    fun onWindowAttributesChanged(arg0: WindowManager.LayoutParams)

    fun onContentChanged()

    fun onWindowFocusChanged(arg0: Boolean)

    fun onAttachedToWindow()

    fun onDetachedFromWindow()

    fun onCreatePanelView(arg0: Int): View?

    fun onCreatePanelMenu(arg0: Int, arg1: Menu): Boolean

    fun onPreparePanel(arg0: Int, arg1: View?, arg2: Menu): Boolean

    fun onMenuOpened(arg0: Int, arg1: Menu): Boolean

    fun onMenuItemSelected(arg0: Int, arg1: MenuItem): Boolean

    fun onPanelClosed(arg0: Int, arg1: Menu)

    fun onCreateOptionsMenu(arg0: Menu): Boolean

    fun onPrepareOptionsMenu(arg0: Menu): Boolean

    fun onOptionsItemSelected(arg0: MenuItem): Boolean

    fun onNavigateUp(): Boolean

    fun onNavigateUpFromChild(arg0: Activity): Boolean

    fun onCreateNavigateUpTaskStack(arg0: TaskStackBuilder)

    fun onPrepareNavigateUpTaskStack(arg0: TaskStackBuilder)

    fun onOptionsMenuClosed(arg0: Menu)

    fun onCreateContextMenu(arg0: ContextMenu, arg1: View, arg2: ContextMenu.ContextMenuInfo)

    fun onContextItemSelected(arg0: MenuItem): Boolean

    fun onContextMenuClosed(arg0: Menu)

    fun onSearchRequested(): Boolean

    fun onSearchRequested(arg0: SearchEvent?): Boolean

    fun onRequestPermissionsResult(arg0: Int, arg1: Array<String>, arg2: IntArray)

    fun onProvideReferrer(): Uri?

    fun onActivityReenter(arg0: Int, arg1: Intent)

    fun onCreateView(arg0: View?, arg1: String, arg2: Context, arg3: AttributeSet): View?

    fun onCreateView(arg0: String, arg1: Context, arg2: AttributeSet): View?

    fun onVisibleBehindCanceled()

    fun onEnterAnimationComplete()

    fun onPointerCaptureChanged(arg0: Boolean)

    fun onStop()

    fun onStart()

    fun onPause()

    fun onResume()

    fun onRestoreInstanceState(arg0: Bundle)

    fun onPostCreate(arg0: Bundle?)

    fun onRestart()

    fun onPostResume()

    fun onNewIntent(arg0: Intent)

    fun onSaveInstanceState(arg0: Bundle)

    fun onUserLeaveHint()

    fun onDestroy()

    fun onCreate(arg0: Bundle?)

    fun onCreateDialog(arg0: Int, arg1: Bundle): Dialog?

    fun onCreateDialog(arg0: Int): Dialog?

    fun onPrepareDialog(arg0: Int, arg1: Dialog, arg2: Bundle)

    fun onPrepareDialog(arg0: Int, arg1: Dialog)

    fun onApplyThemeResource(arg0: Resources.Theme, arg1: Int, arg2: Boolean)

    fun onActivityResult(arg0: Int, arg1: Int, arg2: Intent)

    fun onTitleChanged(arg0: CharSequence, arg1: Int)

    fun onChildTitleChanged(arg0: Activity, arg1: CharSequence)

    fun dispatchKeyEvent(arg0: KeyEvent): Boolean?

    fun dispatchKeyShortcutEvent(arg0: KeyEvent): Boolean

    fun dispatchTouchEvent(arg0: MotionEvent): Boolean

    fun dispatchTrackballEvent(arg0: MotionEvent): Boolean

    fun dispatchGenericMotionEvent(arg0: MotionEvent): Boolean

    fun dispatchPopulateAccessibilityEvent(arg0: AccessibilityEvent): Boolean
}
