package com.myl.plugin_core.proxy

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
import android.view.*
import android.view.accessibility.AccessibilityEvent
import android.widget.Toolbar
import java.io.*
import java.util.concurrent.Executor
import java.util.function.Consumer

interface HostActivitySuperMethodProxy {

    fun getParent(): Activity
    //---------系统activity已经实现的方法begin---------------
    fun isDestroyed(): Boolean

    fun getTaskId(): Int

    fun dump(arg0: String, arg1: FileDescriptor, arg2: PrintWriter, arg3: Array<String>)

    fun getActionBar(): ActionBar?

    fun <T : View> requireViewById(arg0: Int): T

    fun setFinishOnTouchOutside(arg0: Boolean)

    fun addContentView(arg0: View, arg1: ViewGroup.LayoutParams)

    fun setResult(arg0: Int, arg1: Intent)

    fun setResult(arg0: Int)

    open fun setContentView(arg0: View?, arg1: ViewGroup.LayoutParams?)

    open fun setContentView(arg0: Int)

    open fun setContentView(arg0: View?)

    fun <T : View> findViewById(arg0: Int): T

    fun managedQuery(
        arg0: Uri,
        arg1: Array<String>,
        arg2: String,
        arg3: Array<String>,
        arg4: String
    ): Cursor

    fun setPictureInPictureParams(arg0: PictureInPictureParams)

    fun getApplication(): Application

    fun getApplicationContext(): Context

    fun getWindowManager(): WindowManager

    fun getWindow(): Window

    fun getLoaderManager(): LoaderManager

    fun getCurrentFocus(): View?

    fun getIntent(): Intent

    fun setIntent(arg0: Intent)

    fun isChild(): Boolean

    fun registerActivityLifecycleCallbacks(arg0: Application.ActivityLifecycleCallbacks)

    fun unregisterActivityLifecycleCallbacks(arg0: Application.ActivityLifecycleCallbacks)

    fun isVoiceInteraction(): Boolean

    fun isVoiceInteractionRoot(): Boolean

    fun getVoiceInteractor(): VoiceInteractor

    fun isLocalVoiceInteractionSupported(): Boolean

    fun startLocalVoiceInteraction(arg0: Bundle)

    fun stopLocalVoiceInteraction()

    fun getComponentName(): ComponentName

    fun dismissKeyboardShortcutsHelper()

    fun startActivityFromChild(arg0: Activity, arg1: Intent, arg2: Int, arg3: Bundle?)

    fun startActionMode(arg0: ActionMode.Callback, arg1: Int): ActionMode?

    fun startActionMode(arg0: ActionMode.Callback): ActionMode?

    fun shouldUpRecreateTask(arg0: Intent): Boolean

    fun navigateUpTo(arg0: Intent): Boolean

    fun navigateUpToFromChild(arg0: Activity, arg1: Intent): Boolean

    fun getParentActivityIntent(): Intent?

    fun setEnterSharedElementCallback(arg0: SharedElementCallback)

    fun setExitSharedElementCallback(arg0: SharedElementCallback)

    fun postponeEnterTransition()

    fun startPostponedEnterTransition()

    fun requestDragAndDropPermissions(arg0: DragEvent): DragAndDropPermissions

    fun startLockTask()

    fun stopLockTask()

    fun showLockTaskEscapeMessage()

    fun setShowWhenLocked(arg0: Boolean)

    fun setInheritShowWhenLocked(arg0: Boolean)

    fun setTurnScreenOn(arg0: Boolean)

    fun setVisible(arg0: Boolean)

    fun startManagingCursor(arg0: Cursor)

    fun getLastNonConfigurationInstance(): Any?

    fun getChangingConfigurations(): Int

    fun showAssist(arg0: Bundle): Boolean

    fun reportFullyDrawn()

    fun requestShowKeyboardShortcuts()

    fun setTitle(arg0: CharSequence)

    fun setTitle(arg0: Int)

    fun getTitle(): CharSequence

    fun getContentTransitionManager(): TransitionManager

    fun setDefaultKeyMode(arg0: Int)

    fun setActionBar(arg0: Toolbar?)

    fun stopManagingCursor(arg0: Cursor)

    fun isInPictureInPictureMode(): Boolean

    fun getFragmentManager(): FragmentManager

    fun enterPictureInPictureMode()

    fun enterPictureInPictureMode(arg0: PictureInPictureParams): Boolean

    fun getMaxNumPictureInPictureActions(): Int

    fun isInMultiWindowMode(): Boolean

    fun setContentTransitionManager(arg0: TransitionManager)

    fun getContentScene(): Scene

    fun hasWindowFocus(): Boolean

    fun invalidateOptionsMenu()

    fun openOptionsMenu()

    fun closeOptionsMenu()

    fun registerForContextMenu(arg0: View)

    fun unregisterForContextMenu(arg0: View)

    fun openContextMenu(arg0: View)

    fun closeContextMenu()

    fun showDialog(arg0: Int, arg1: Bundle): Boolean

    fun showDialog(arg0: Int)

    fun dismissDialog(arg0: Int)

    fun removeDialog(arg0: Int)

    fun getSearchEvent(): SearchEvent

    fun startSearch(arg0: String?, arg1: Boolean, arg2: Bundle?, arg3: Boolean)

    fun triggerSearch(arg0: String, arg1: Bundle?)

    fun takeKeyEvents(arg0: Boolean)

    fun requestWindowFeature(arg0: Int): Boolean

    fun setFeatureDrawableResource(arg0: Int, arg1: Int)

    fun setFeatureDrawableUri(arg0: Int, arg1: Uri)

    fun setFeatureDrawable(arg0: Int, arg1: Drawable)

    fun setFeatureDrawableAlpha(arg0: Int, arg1: Int)

    fun getMenuInflater(): MenuInflater

//    fun setTheme(arg0: Int)

    fun requestPermissions(arg0: Array<String>, arg1: Int)

    fun shouldShowRequestPermissionRationale(arg0: String): Boolean

    fun startActivityForResult(arg0: Intent, arg1: Int)

    fun startActivityForResult(arg0: Intent, arg1: Int, arg2: Bundle?)

    fun isActivityTransitionRunning(): Boolean

    @Throws(IntentSender.SendIntentException::class)
    fun startIntentSenderForResult(
        arg0: IntentSender, arg1: Int, arg2: Intent?, arg3: Int, arg4: Int,
        arg5: Int, arg6: Bundle
    )

    @Throws(IntentSender.SendIntentException::class)
    fun startIntentSenderForResult(
        arg0: IntentSender, arg1: Int, arg2: Intent?, arg3: Int, arg4: Int,
        arg5: Int
    )

//    fun startActivity(arg0: Intent, arg1: Bundle)
//
//    fun startActivity(arg0: Intent)
//
//    fun startActivities(arg0: Array<Intent>, arg1: Bundle)
//
//    fun startActivities(arg0: Array<Intent>)
//
//    @Throws(IntentSender.SendIntentException::class)
//    fun startIntentSender(
//        arg0: IntentSender,
//        arg1: Intent,
//        arg2: Int,
//        arg3: Int,
//        arg4: Int
//    )
//
//    @Throws(IntentSender.SendIntentException::class)
//    fun startIntentSender(
//        arg0: IntentSender,
//        arg1: Intent,
//        arg2: Int,
//        arg3: Int,
//        arg4: Int,
//        arg5: Bundle
//    )

    fun startActivityIfNeeded(arg0: Intent, arg1: Int): Boolean

    fun startActivityIfNeeded(arg0: Intent, arg1: Int, arg2: Bundle?): Boolean

    fun startNextMatchingActivity(arg0: Intent, arg1: Bundle?): Boolean

    fun startNextMatchingActivity(arg0: Intent): Boolean

    fun startActivityFromFragment(arg0: Fragment, arg1: Intent, arg2: Int)

    fun startActivityFromFragment(arg0: Fragment, arg1: Intent, arg2: Int, arg3: Bundle?)

//    @Throws(IntentSender.SendIntentException::class)
//    fun startIntentSenderFromChild(
//        arg0: Activity, arg1: IntentSender, arg2: Int, arg3: Intent, arg4: Int,
//        arg5: Int, arg6: Int, arg7: Bundle
//    )
//
//    @Throws(IntentSender.SendIntentException::class)
//    fun startIntentSenderFromChild(
//        arg0: Activity, arg1: IntentSender, arg2: Int, arg3: Intent, arg4: Int,
//        arg5: Int, arg6: Int
//    )

    fun overridePendingTransition(arg0: Int, arg1: Int)

    fun getReferrer(): Uri?

    fun getCallingPackage(): String?

    fun isFinishing(): Boolean

    fun finishAffinity()

//    fun finishFromChild(arg0: Activity)

    fun finishAfterTransition()

    fun finishActivity(arg0: Int)

//    fun finishActivityFromChild(arg0: Activity, arg1: Int)

    fun finishAndRemoveTask()

    fun releaseInstance(): Boolean

    fun createPendingResult(arg0: Int, arg1: Intent, arg2: Int): PendingIntent

    fun setRequestedOrientation(arg0: Int)

    fun getRequestedOrientation(): Int

    fun isTaskRoot(): Boolean

    fun moveTaskToBack(arg0: Boolean): Boolean

    fun getLocalClassName(): String

    fun getPreferences(arg0: Int): SharedPreferences

//    fun getSystemService(arg0: String): Any

    fun setTitleColor(arg0: Int)

    fun getTitleColor(): Int

    fun setTaskDescription(arg0: ActivityManager.TaskDescription)

    fun setProgressBarVisibility(arg0: Boolean)

    fun setProgressBarIndeterminateVisibility(arg0: Boolean)

    fun setProgressBarIndeterminate(arg0: Boolean)

    fun setProgress(arg0: Int)

    fun setSecondaryProgress(arg0: Int)

    fun setVolumeControlStream(arg0: Int)

    fun getVolumeControlStream(): Int

    fun setMediaController(arg0: MediaController)

    fun getMediaController(): MediaController

    fun runOnUiThread(arg0: Runnable)

    fun isImmersive(): Boolean

    fun requestVisibleBehind(arg0: Boolean): Boolean

    fun setImmersive(arg0: Boolean)

    @Throws(PackageManager.NameNotFoundException::class)
    fun setVrModeEnabled(arg0: Boolean, arg1: ComponentName)
//
//    fun applyOverrideConfiguration(arg0: Configuration)
//
//    fun getAssets(): AssetManager
//
//    fun getTheme(): Resources.Theme
//
//    fun setTheme(arg0: Resources.Theme)
//
//    fun checkPermission(arg0: String, arg1: Int, arg2: Int): Int
//
//    fun getPackageName(): String
//
//    fun getCacheDir(): File
//
//    fun getSystemServiceName(arg0: Class<*>): String
//
//    fun getPackageCodePath(): String
//
//    fun getDir(arg0: String, arg1: Int): File
//
//    fun getSharedPreferences(arg0: String, arg1: Int): SharedPreferences
//
//    fun deleteSharedPreferences(arg0: String): Boolean
//
//    fun moveSharedPreferencesFrom(arg0: Context, arg1: String): Boolean
//
//    @Throws(FileNotFoundException::class)
//    fun openFileInput(arg0: String): FileInputStream
//
//    @Throws(FileNotFoundException::class)
//    fun openFileOutput(arg0: String, arg1: Int): FileOutputStream
//
//    fun getFileStreamPath(arg0: String): File
//
//    fun getDataDir(): File
//
//    fun getFilesDir(): File
//
//    fun getNoBackupFilesDir(): File
//
//    fun getExternalFilesDir(arg0: String): File
//
//    fun getExternalFilesDirs(arg0: String): Array<File>
//
//    fun getObbDir(): File
//
//    fun getObbDirs(): Array<File>
//
//    fun getCodeCacheDir(): File
//
//    fun getExternalCacheDir(): File
//
//    fun getExternalCacheDirs(): Array<File>
//
//    fun getExternalMediaDirs(): Array<File>
//
//    fun openOrCreateDatabase(
//        arg0: String,
//        arg1: Int,
//        arg2: SQLiteDatabase.CursorFactory
//    ): SQLiteDatabase
//
//    fun openOrCreateDatabase(
//        arg0: String, arg1: Int, arg2: SQLiteDatabase.CursorFactory,
//        arg3: DatabaseErrorHandler
//    ): SQLiteDatabase
//
//    fun moveDatabaseFrom(arg0: Context, arg1: String): Boolean
//
//    fun deleteDatabase(arg0: String): Boolean
//
//    fun getDatabasePath(arg0: String): File
//
//    fun databaseList(): Array<String>
//
//    fun getWallpaper(): Drawable
//
//    fun peekWallpaper(): Drawable
//
//    fun getWallpaperDesiredMinimumWidth(): Int
//
//    fun getWallpaperDesiredMinimumHeight(): Int
//
//    @Throws(IOException::class)
//    fun setWallpaper(arg0: InputStream)
//
//    @Throws(IOException::class)
//    fun setWallpaper(arg0: Bitmap)
//
//    @Throws(IOException::class)
//    fun clearWallpaper()
//
//    fun sendBroadcast(arg0: Intent)
//
//    fun sendBroadcast(arg0: Intent, arg1: String)
//
//    fun sendOrderedBroadcast(
//        arg0: Intent, arg1: String, arg2: BroadcastReceiver, arg3: Handler,
//        arg4: Int, arg5: String, arg6: Bundle
//    )
//
//    fun sendOrderedBroadcast(arg0: Intent, arg1: String)
//
//    fun sendBroadcastAsUser(arg0: Intent, arg1: UserHandle, arg2: String)
//
//    fun sendBroadcastAsUser(arg0: Intent, arg1: UserHandle)
//
//    fun sendOrderedBroadcastAsUser(
//        arg0: Intent, arg1: UserHandle, arg2: String, arg3: BroadcastReceiver,
//        arg4: Handler, arg5: Int, arg6: String, arg7: Bundle
//    )
//
//    fun sendStickyBroadcast(arg0: Intent)
//
//    fun sendStickyOrderedBroadcast(
//        arg0: Intent, arg1: BroadcastReceiver, arg2: Handler, arg3: Int,
//        arg4: String, arg5: Bundle
//    )
//
//    fun removeStickyBroadcast(arg0: Intent)
//
//    fun sendStickyBroadcastAsUser(arg0: Intent, arg1: UserHandle)
//
//    fun sendStickyOrderedBroadcastAsUser(
//        arg0: Intent, arg1: UserHandle, arg2: BroadcastReceiver,
//        arg3: Handler, arg4: Int, arg5: String, arg6: Bundle
//    )
//
//    fun removeStickyBroadcastAsUser(arg0: Intent, arg1: UserHandle)
//
//    fun registerReceiver(
//        arg0: BroadcastReceiver,
//        arg1: IntentFilter,
//        arg2: String,
//        arg3: Handler
//    ): Intent
//
//    fun registerReceiver(arg0: BroadcastReceiver, arg1: IntentFilter, arg2: Int): Intent
//
//    fun registerReceiver(arg0: BroadcastReceiver, arg1: IntentFilter): Intent
//
//    fun registerReceiver(
//        arg0: BroadcastReceiver, arg1: IntentFilter, arg2: String, arg3: Handler,
//        arg4: Int
//    ): Intent
//
//    fun unregisterReceiver(arg0: BroadcastReceiver)
//
//    fun startService(arg0: Intent): ComponentName
//
//    fun startForegroundService(arg0: Intent): ComponentName
//
//    fun stopService(arg0: Intent): Boolean
//
//    fun bindService(
//        arg0: Intent,
//        arg1: Int,
//        arg2: Executor,
//        arg3: ServiceConnection
//    ): Boolean
//
//    fun bindService(arg0: Intent, arg1: ServiceConnection, arg2: Int): Boolean
//
//    fun bindIsolatedService(
//        arg0: Intent, arg1: Int, arg2: String, arg3: Executor,
//        arg4: ServiceConnection
//    ): Boolean
//
//    fun updateServiceGroup(arg0: ServiceConnection, arg1: Int, arg2: Int)
//
//    fun unbindService(arg0: ServiceConnection)
//
//    fun getMainExecutor(): Executor
//
//    fun getContentResolver(): ContentResolver
//
//    fun getPackageManager(): PackageManager
//
//    fun getBaseContext(): Context
//
//    fun getMainLooper(): Looper
//
//    fun deleteFile(arg0: String): Boolean
//
//    fun getApplicationContext(): Context
//
//    fun getApplicationInfo(): ApplicationInfo
//
//    fun startInstrumentation(arg0: ComponentName, arg1: String, arg2: Bundle): Boolean
//
//    fun getOpPackageName(): String
//
//    fun getPackageResourcePath(): String
//
//    fun fileList(): Array<String>
//
//    fun checkCallingPermission(arg0: String): Int
//
//    fun checkCallingOrSelfPermission(arg0: String): Int
//
//    fun checkSelfPermission(arg0: String): Int
//
//    fun enforcePermission(arg0: String, arg1: Int, arg2: Int, arg3: String)
//
//    fun enforceCallingPermission(arg0: String, arg1: String)
//
//    fun enforceCallingOrSelfPermission(arg0: String, arg1: String)
//
//    fun grantUriPermission(arg0: String, arg1: Uri, arg2: Int)
//
//    fun revokeUriPermission(arg0: String, arg1: Uri, arg2: Int)
//
//    fun revokeUriPermission(arg0: Uri, arg1: Int)
//
//    fun checkUriPermission(arg0: Uri, arg1: Int, arg2: Int, arg3: Int): Int
//
//    fun checkUriPermission(
//        arg0: Uri,
//        arg1: String,
//        arg2: String,
//        arg3: Int,
//        arg4: Int,
//        arg5: Int
//    ): Int
//
//    fun checkCallingUriPermission(arg0: Uri, arg1: Int): Int
//
//    fun checkCallingOrSelfUriPermission(arg0: Uri, arg1: Int): Int
//
//    fun enforceUriPermission(
//        arg0: Uri, arg1: String, arg2: String, arg3: Int, arg4: Int, arg5: Int,
//        arg6: String
//    )
//
//    fun enforceUriPermission(arg0: Uri, arg1: Int, arg2: Int, arg3: Int, arg4: String)
//
//    fun enforceCallingUriPermission(arg0: Uri, arg1: Int, arg2: String)
//
//    fun enforceCallingOrSelfUriPermission(arg0: Uri, arg1: Int, arg2: String)
//
//    @Throws(PackageManager.NameNotFoundException::class)
//    fun createPackageContext(arg0: String, arg1: Int): Context
//
//    @Throws(PackageManager.NameNotFoundException::class)
//    fun createContextForSplit(arg0: String): Context
//
//    fun createConfigurationContext(arg0: Configuration): Context
//
//    fun createDisplayContext(arg0: Display): Context
//
//    fun isRestricted(): Boolean
//
//    fun createDeviceProtectedStorageContext(): Context
//
//    fun isDeviceProtectedStorage(): Boolean
//
//    fun registerComponentCallbacks(arg0: ComponentCallbacks)
//
//    fun unregisterComponentCallbacks(arg0: ComponentCallbacks)
//
//    fun attachBaseContext(arg0: Context)
//
//    fun isChangingConfigurations(): Boolean
//
//    fun finish()
//
//    fun startActivityFromChild(arg0: Activity, arg1: Intent, arg2: Int)
//
//    fun getClassLoader(): ClassLoader
//
//    fun getLayoutInflater(): LayoutInflater
//
//    fun getResources(): Resources
//
//    fun recreate()
//
//    fun getCallingActivity(): ComponentName?
//
//    fun onActionModeStarted(arg0: ActionMode)
//
//    fun onWindowStartingActionMode(arg0: ActionMode.Callback, arg1: Int): ActionMode?
//
//    fun onWindowStartingActionMode(arg0: ActionMode.Callback): ActionMode?
//
//    fun onActionModeFinished(arg0: ActionMode)
//
//    fun onProvideKeyboardShortcuts(
//        arg0: List<KeyboardShortcutGroup>,
//        arg1: Menu?,
//        arg2: Int
//    )
//
//    fun onWindowAttributesChanged(arg0: WindowManager.LayoutParams)
//
//    fun onContentChanged()
//
//    fun onWindowFocusChanged(arg0: Boolean)
//
//    fun onAttachedToWindow()
//
//    fun onDetachedFromWindow()
//
//    fun onCreatePanelView(arg0: Int): View?
//
//    fun onCreatePanelMenu(arg0: Int, arg1: Menu): Boolean
//
//    fun onPreparePanel(arg0: Int, arg1: View, arg2: Menu): Boolean?
//
//    fun onMenuOpened(arg0: Int, arg1: Menu): Boolean
//
//    fun onMenuItemSelected(arg0: Int, arg1: MenuItem): Boolean
//
//    fun onPanelClosed(arg0: Int, arg1: Menu)
//
//    fun onSearchRequested(): Boolean?
//
//    fun onSearchRequested(arg0: SearchEvent): Boolean?
//
//    fun onPointerCaptureChanged(arg0: Boolean)
//
//    fun dispatchKeyEvent(arg0: KeyEvent): Boolean
//
//    fun dispatchKeyShortcutEvent(arg0: KeyEvent): Boolean
//
//    fun dispatchTouchEvent(arg0: MotionEvent): Boolean
//
//    fun dispatchTrackballEvent(arg0: MotionEvent): Boolean
//
//    fun dispatchGenericMotionEvent(arg0: MotionEvent): Boolean
//
//    fun dispatchPopulateAccessibilityEvent(arg0: AccessibilityEvent): Boolean

    //---------系统activity已经实现的方法end---------------
    fun superIsChangingConfigurations(): Boolean

    fun superFinish()

    fun superStartActivityFromChild(arg0: Activity, arg1: Intent, arg2: Int)

    fun superGetClassLoader(): ClassLoader

    fun superGetLayoutInflater(): LayoutInflater

    fun superGetResources(): Resources

    fun superRecreate()

    fun superGetCallingActivity(): ComponentName?

    fun superOnKeyMultiple(arg0: Int, arg1: Int, arg2: KeyEvent): Boolean

    fun superOnKeyDown(arg0: Int, arg1: KeyEvent): Boolean

    fun superOnKeyUp(arg0: Int, arg1: KeyEvent): Boolean

    fun superOnKeyLongPress(arg0: Int, arg1: KeyEvent): Boolean

    fun superOnRestoreInstanceState(arg0: Bundle?, arg1: PersistableBundle?)

    fun superOnPostCreate(arg0: Bundle?, arg1: PersistableBundle?)

    fun superOnStateNotSaved()

    fun superOnTopResumedActivityChanged(arg0: Boolean)

    fun superOnLocalVoiceInteractionStarted()

    fun superOnLocalVoiceInteractionStopped()

    fun superOnSaveInstanceState(arg0: Bundle, arg1: PersistableBundle)

    fun superOnCreateThumbnail(arg0: Bitmap, arg1: Canvas): Boolean

    fun superOnCreateDescription(): CharSequence?

    fun superOnProvideAssistData(arg0: Bundle)

    fun superOnProvideAssistContent(arg0: AssistContent)

    fun superOnGetDirectActions(arg0: CancellationSignal, arg1: Consumer<List<DirectAction>>)

    fun superOnPerformDirectAction(
        arg0: String, arg1: Bundle, arg2: CancellationSignal,
        arg3: Consumer<Bundle>
    )

    fun superOnCreate(arg0: Bundle?, arg1: PersistableBundle?)

    fun superOnActionModeStarted(arg0: ActionMode)

    fun superOnWindowStartingActionMode(arg0: ActionMode.Callback, arg1: Int): ActionMode?

    fun superOnWindowStartingActionMode(arg0: ActionMode.Callback): ActionMode?

    fun superOnActionModeFinished(arg0: ActionMode)

    fun superOnAttachFragment(arg0: Fragment)

    fun superOnRetainNonConfigurationInstance(): Any?

    fun superOnTrimMemory(arg0: Int)

    fun superOnLowMemory()

    fun superOnProvideKeyboardShortcuts(arg0: List<KeyboardShortcutGroup>, arg1: Menu?, arg2: Int)

    fun superOnMultiWindowModeChanged(arg0: Boolean)

    fun superOnMultiWindowModeChanged(arg0: Boolean, arg1: Configuration)

    fun superOnBackPressed()

    fun superOnConfigurationChanged(arg0: Configuration)

    fun superOnPictureInPictureModeChanged(arg0: Boolean)

    fun superOnPictureInPictureModeChanged(arg0: Boolean, arg1: Configuration)

    fun superOnKeyShortcut(arg0: Int, arg1: KeyEvent): Boolean

    fun superOnTouchEvent(arg0: MotionEvent): Boolean

    fun superOnTrackballEvent(arg0: MotionEvent): Boolean

    fun superOnGenericMotionEvent(arg0: MotionEvent): Boolean

    fun superOnUserInteraction()

    fun superOnWindowAttributesChanged(arg0: WindowManager.LayoutParams)

    fun superOnContentChanged()

    fun superOnWindowFocusChanged(arg0: Boolean)

    fun superOnAttachedToWindow()

    fun superOnDetachedFromWindow()

    fun superOnCreatePanelView(arg0: Int): View?

    fun superOnCreatePanelMenu(arg0: Int, arg1: Menu): Boolean

    fun superOnPreparePanel(arg0: Int, arg1: View?, arg2: Menu): Boolean

    fun superOnMenuOpened(arg0: Int, arg1: Menu): Boolean

    fun superOnMenuItemSelected(arg0: Int, arg1: MenuItem): Boolean

    fun superOnPanelClosed(arg0: Int, arg1: Menu)

    fun superOnCreateOptionsMenu(arg0: Menu): Boolean

    fun superOnPrepareOptionsMenu(arg0: Menu): Boolean

    fun superOnOptionsItemSelected(arg0: MenuItem): Boolean

    fun superOnNavigateUp(): Boolean

    fun superOnNavigateUpFromChild(arg0: Activity): Boolean

    fun superOnCreateNavigateUpTaskStack(arg0: TaskStackBuilder)

    fun superOnPrepareNavigateUpTaskStack(arg0: TaskStackBuilder)

    fun superOnOptionsMenuClosed(arg0: Menu)

    fun superOnCreateContextMenu(arg0: ContextMenu, arg1: View, arg2: ContextMenu.ContextMenuInfo)

    fun superOnContextItemSelected(arg0: MenuItem): Boolean

    fun superOnContextMenuClosed(arg0: Menu)

    fun superOnSearchRequested(): Boolean

    fun superOnSearchRequested(arg0: SearchEvent?): Boolean

    fun superOnRequestPermissionsResult(arg0: Int, arg1: Array<String>, arg2: IntArray)

    fun superOnProvideReferrer(): Uri?

    fun superOnActivityReenter(arg0: Int, arg1: Intent)

    fun superOnCreateView(arg0: View?, arg1: String, arg2: Context, arg3: AttributeSet): View?

    fun superOnCreateView(arg0: String, arg1: Context, arg2: AttributeSet): View?

    fun superOnVisibleBehindCanceled()

    fun superOnEnterAnimationComplete()

    fun superOnPointerCaptureChanged(arg0: Boolean)

    fun superOnStop()

    fun superOnStart()

    fun superOnPause()

    fun superOnResume()

    fun superOnRestoreInstanceState(arg0: Bundle)

    fun superOnPostCreate(arg0: Bundle?)

    fun superOnRestart()

    fun superOnPostResume()

    fun superOnNewIntent(arg0: Intent)

    fun superOnSaveInstanceState(arg0: Bundle)

    fun superOnUserLeaveHint()

    fun superOnDestroy()

    fun superOnCreate(arg0: Bundle?)

    fun superOnCreateDialog(arg0: Int, arg1: Bundle): Dialog?

    fun superOnCreateDialog(arg0: Int): Dialog?

    fun superOnPrepareDialog(arg0: Int, arg1: Dialog, arg2: Bundle)

    fun superOnPrepareDialog(arg0: Int, arg1: Dialog)

    fun superOnApplyThemeResource(arg0: Resources.Theme, arg1: Int, arg2: Boolean)

    fun superOnActivityResult(arg0: Int, arg1: Int, arg2: Intent)

    fun superOnTitleChanged(arg0: CharSequence, arg1: Int)

    fun superOnChildTitleChanged(arg0: Activity, arg1: CharSequence)

    fun superDispatchKeyEvent(arg0: KeyEvent): Boolean

    fun superDispatchKeyShortcutEvent(arg0: KeyEvent): Boolean

    fun superDispatchTouchEvent(arg0: MotionEvent): Boolean

    fun superDispatchTrackballEvent(arg0: MotionEvent): Boolean

    fun superDispatchGenericMotionEvent(arg0: MotionEvent): Boolean

    fun superDispatchPopulateAccessibilityEvent(arg0: AccessibilityEvent): Boolean

    fun superGetParent(): Activity

    fun superIsDestroyed(): Boolean

    fun superGetTaskId(): Int

    fun superDump(arg0: String, arg1: FileDescriptor, arg2: PrintWriter, arg3: Array<String>)

    fun superGetActionBar(): ActionBar?

    fun <T : View> superRequireViewById(arg0: Int): T

    fun superSetFinishOnTouchOutside(arg0: Boolean)

    fun superAddContentView(arg0: View, arg1: ViewGroup.LayoutParams)

    fun superSetResult(arg0: Int, arg1: Intent)

    fun superSetResult(arg0: Int)

    fun superSetContentView(arg0: View, arg1: ViewGroup.LayoutParams)

    fun superSetContentView(arg0: Int)

    fun superSetContentView(arg0: View)

    fun <T : View> superFindViewById(arg0: Int): T

    fun superManagedQuery(
        arg0: Uri,
        arg1: Array<String>,
        arg2: String,
        arg3: Array<String>,
        arg4: String
    ): Cursor

    fun superSetPictureInPictureParams(arg0: PictureInPictureParams)

    fun superGetApplication(): Application

    fun superGetWindowManager(): WindowManager

    fun superGetWindow(): Window

    fun superGetLoaderManager(): LoaderManager

    fun superGetCurrentFocus(): View?

    fun superGetIntent(): Intent

    fun superSetIntent(arg0: Intent)

    fun superIsChild(): Boolean

    fun superRegisterActivityLifecycleCallbacks(arg0: Application.ActivityLifecycleCallbacks)

    fun superUnregisterActivityLifecycleCallbacks(arg0: Application.ActivityLifecycleCallbacks)

    fun superIsVoiceInteraction(): Boolean

    fun superIsVoiceInteractionRoot(): Boolean

    fun superGetVoiceInteractor(): VoiceInteractor

    fun superIsLocalVoiceInteractionSupported(): Boolean

    fun superStartLocalVoiceInteraction(arg0: Bundle)

    fun superStopLocalVoiceInteraction()

    fun superGetComponentName(): ComponentName

    fun superDismissKeyboardShortcutsHelper()

    fun superStartActivityFromChild(arg0: Activity, arg1: Intent, arg2: Int, arg3: Bundle)

    fun superStartActionMode(arg0: ActionMode.Callback, arg1: Int): ActionMode?

    fun superStartActionMode(arg0: ActionMode.Callback): ActionMode?

    fun superShouldUpRecreateTask(arg0: Intent): Boolean

    fun superNavigateUpTo(arg0: Intent): Boolean

    fun superNavigateUpToFromChild(arg0: Activity, arg1: Intent): Boolean

    fun superGetParentActivityIntent(): Intent?

    fun superSetEnterSharedElementCallback(arg0: SharedElementCallback)

    fun superSetExitSharedElementCallback(arg0: SharedElementCallback)

    fun superPostponeEnterTransition()

    fun superStartPostponedEnterTransition()

    fun superRequestDragAndDropPermissions(arg0: DragEvent): DragAndDropPermissions

    fun superStartLockTask()

    fun superStopLockTask()

    fun superShowLockTaskEscapeMessage()

    fun superSetShowWhenLocked(arg0: Boolean)

    fun superSetInheritShowWhenLocked(arg0: Boolean)

    fun superSetTurnScreenOn(arg0: Boolean)

    fun superSetVisible(arg0: Boolean)

    fun superStartManagingCursor(arg0: Cursor)

    fun superGetLastNonConfigurationInstance(): Any?

    fun superGetChangingConfigurations(): Int

    fun superShowAssist(arg0: Bundle): Boolean

    fun superReportFullyDrawn()

    fun superRequestShowKeyboardShortcuts()

    fun superSetTitle(arg0: CharSequence)

    fun superSetTitle(arg0: Int)

    fun superGetTitle(): CharSequence

    fun superGetContentTransitionManager(): TransitionManager

    fun superSetDefaultKeyMode(arg0: Int)

    fun superSetActionBar(arg0: Toolbar)

    fun superStopManagingCursor(arg0: Cursor)

    fun superIsInPictureInPictureMode(): Boolean

    fun superGetFragmentManager(): FragmentManager

    fun superEnterPictureInPictureMode()

    fun superEnterPictureInPictureMode(arg0: PictureInPictureParams): Boolean

    fun superGetMaxNumPictureInPictureActions(): Int

    fun superIsInMultiWindowMode(): Boolean

    fun superSetContentTransitionManager(arg0: TransitionManager)

    fun superGetContentScene(): Scene

    fun superHasWindowFocus(): Boolean

    fun superInvalidateOptionsMenu()

    fun superOpenOptionsMenu()

    fun superCloseOptionsMenu()

    fun superRegisterForContextMenu(arg0: View)

    fun superUnregisterForContextMenu(arg0: View)

    fun superOpenContextMenu(arg0: View)

    fun superCloseContextMenu()

    fun superShowDialog(arg0: Int, arg1: Bundle): Boolean

    fun superShowDialog(arg0: Int)

    fun superDismissDialog(arg0: Int)

    fun superRemoveDialog(arg0: Int)

    fun superGetSearchEvent(): SearchEvent

    fun superStartSearch(arg0: String, arg1: Boolean, arg2: Bundle, arg3: Boolean)

    fun superTriggerSearch(arg0: String, arg1: Bundle)

    fun superTakeKeyEvents(arg0: Boolean)

    fun superRequestWindowFeature(arg0: Int): Boolean

    fun superSetFeatureDrawableResource(arg0: Int, arg1: Int)

    fun superSetFeatureDrawableUri(arg0: Int, arg1: Uri)

    fun superSetFeatureDrawable(arg0: Int, arg1: Drawable)

    fun superSetFeatureDrawableAlpha(arg0: Int, arg1: Int)

    fun superGetMenuInflater(): MenuInflater

    fun superSetTheme(arg0: Int)

    fun superRequestPermissions(arg0: Array<String>, arg1: Int)

    fun superShouldShowRequestPermissionRationale(arg0: String): Boolean

    fun superStartActivityForResult(arg0: Intent, arg1: Int)

    fun superStartActivityForResult(arg0: Intent, arg1: Int, arg2: Bundle)

    fun superIsActivityTransitionRunning(): Boolean

    @Throws(IntentSender.SendIntentException::class)
    fun superStartIntentSenderForResult(
        arg0: IntentSender, arg1: Int, arg2: Intent, arg3: Int, arg4: Int,
        arg5: Int, arg6: Bundle
    )

    @Throws(IntentSender.SendIntentException::class)
    fun superStartIntentSenderForResult(
        arg0: IntentSender, arg1: Int, arg2: Intent, arg3: Int, arg4: Int,
        arg5: Int
    )

    fun superStartActivity(arg0: Intent, arg1: Bundle)

    fun superStartActivity(arg0: Intent)

    fun superStartActivities(arg0: Array<Intent>, arg1: Bundle)

    fun superStartActivities(arg0: Array<Intent>)

    @Throws(IntentSender.SendIntentException::class)
    fun superStartIntentSender(arg0: IntentSender, arg1: Intent, arg2: Int, arg3: Int, arg4: Int)

    @Throws(IntentSender.SendIntentException::class)
    fun superStartIntentSender(
        arg0: IntentSender, arg1: Intent, arg2: Int, arg3: Int, arg4: Int,
        arg5: Bundle
    )

    fun superStartActivityIfNeeded(arg0: Intent, arg1: Int): Boolean

    fun superStartActivityIfNeeded(arg0: Intent, arg1: Int, arg2: Bundle): Boolean

    fun superStartNextMatchingActivity(arg0: Intent, arg1: Bundle): Boolean

    fun superStartNextMatchingActivity(arg0: Intent): Boolean

    fun superStartActivityFromFragment(arg0: Fragment, arg1: Intent, arg2: Int)

    fun superStartActivityFromFragment(arg0: Fragment, arg1: Intent, arg2: Int, arg3: Bundle)

    @Throws(IntentSender.SendIntentException::class)
    fun superStartIntentSenderFromChild(
        arg0: Activity, arg1: IntentSender, arg2: Int, arg3: Intent,
        arg4: Int, arg5: Int, arg6: Int, arg7: Bundle
    )

    @Throws(IntentSender.SendIntentException::class)
    fun superStartIntentSenderFromChild(
        arg0: Activity, arg1: IntentSender, arg2: Int, arg3: Intent,
        arg4: Int, arg5: Int, arg6: Int
    )

    fun superOverridePendingTransition(arg0: Int, arg1: Int)

    fun superGetReferrer(): Uri?

    fun superGetCallingPackage(): String?

    fun superIsFinishing(): Boolean

    fun superFinishAffinity()

    fun superFinishFromChild(arg0: Activity)

    fun superFinishAfterTransition()

    fun superFinishActivity(arg0: Int)

    fun superFinishActivityFromChild(arg0: Activity, arg1: Int)

    fun superFinishAndRemoveTask()

    fun superReleaseInstance(): Boolean

    fun superCreatePendingResult(arg0: Int, arg1: Intent, arg2: Int): PendingIntent

    fun superSetRequestedOrientation(arg0: Int)

    fun superGetRequestedOrientation(): Int

    fun superIsTaskRoot(): Boolean

    fun superMoveTaskToBack(arg0: Boolean): Boolean

    fun superGetLocalClassName(): String

    fun superGetPreferences(arg0: Int): SharedPreferences

    fun superGetSystemService(arg0: String): Any?

    fun superSetTitleColor(arg0: Int)

    fun superGetTitleColor(): Int

    fun superSetTaskDescription(arg0: ActivityManager.TaskDescription)

    fun superSetProgressBarVisibility(arg0: Boolean)

    fun superSetProgressBarIndeterminateVisibility(arg0: Boolean)

    fun superSetProgressBarIndeterminate(arg0: Boolean)

    fun superSetProgress(arg0: Int)

    fun superSetSecondaryProgress(arg0: Int)

    fun superSetVolumeControlStream(arg0: Int)

    fun superGetVolumeControlStream(): Int

    fun superSetMediaController(arg0: MediaController)

    fun superGetMediaController(): MediaController

    fun superRunOnUiThread(arg0: Runnable)

    fun superIsImmersive(): Boolean

    fun superRequestVisibleBehind(arg0: Boolean): Boolean

    fun superSetImmersive(arg0: Boolean)

    @Throws(PackageManager.NameNotFoundException::class)
    fun superSetVrModeEnabled(arg0: Boolean, arg1: ComponentName)

    fun superApplyOverrideConfiguration(arg0: Configuration)

    fun superGetAssets(): AssetManager

    fun superGetTheme(): Resources.Theme

    fun superSetTheme(arg0: Resources.Theme)

    fun superCheckPermission(arg0: String, arg1: Int, arg2: Int): Int

    fun superGetPackageName(): String

    fun superGetCacheDir(): File

    fun superGetSystemServiceName(arg0: Class<*>): String?

    fun superGetPackageCodePath(): String

    fun superGetDir(arg0: String, arg1: Int): File

    fun superGetSharedPreferences(arg0: String, arg1: Int): SharedPreferences

    fun superDeleteSharedPreferences(arg0: String): Boolean

    fun superMoveSharedPreferencesFrom(arg0: Context, arg1: String): Boolean

    @Throws(FileNotFoundException::class)
    fun superOpenFileInput(arg0: String): FileInputStream

    @Throws(FileNotFoundException::class)
    fun superOpenFileOutput(arg0: String, arg1: Int): FileOutputStream

    fun superGetFileStreamPath(arg0: String): File

    fun superGetDataDir(): File

    fun superGetFilesDir(): File

    fun superGetNoBackupFilesDir(): File

    fun superGetExternalFilesDir(arg0: String): File?

    fun superGetExternalFilesDirs(arg0: String): Array<File>

    fun superGetObbDir(): File

    fun superGetObbDirs(): Array<File>

    fun superGetCodeCacheDir(): File

    fun superGetExternalCacheDir(): File?

    fun superGetExternalCacheDirs(): Array<File>

    fun superGetExternalMediaDirs(): Array<File>

    fun superOpenOrCreateDatabase(
        arg0: String, arg1: Int,
        arg2: SQLiteDatabase.CursorFactory
    ): SQLiteDatabase

    fun superOpenOrCreateDatabase(
        arg0: String, arg1: Int, arg2: SQLiteDatabase.CursorFactory,
        arg3: DatabaseErrorHandler
    ): SQLiteDatabase

    fun superMoveDatabaseFrom(arg0: Context, arg1: String): Boolean

    fun superDeleteDatabase(arg0: String): Boolean

    fun superGetDatabasePath(arg0: String): File

    fun superDatabaseList(): Array<String>

    fun superGetWallpaper(): Drawable

    fun superPeekWallpaper(): Drawable

    fun superGetWallpaperDesiredMinimumWidth(): Int

    fun superGetWallpaperDesiredMinimumHeight(): Int

    @Throws(IOException::class)
    fun superSetWallpaper(arg0: InputStream)

    @Throws(IOException::class)
    fun superSetWallpaper(arg0: Bitmap)

    @Throws(IOException::class)
    fun superClearWallpaper()

    fun superSendBroadcast(arg0: Intent)

    fun superSendBroadcast(arg0: Intent, arg1: String)

    fun superSendOrderedBroadcast(
        arg0: Intent, arg1: String, arg2: BroadcastReceiver, arg3: Handler,
        arg4: Int, arg5: String, arg6: Bundle
    )

    fun superSendOrderedBroadcast(arg0: Intent, arg1: String)

    fun superSendBroadcastAsUser(arg0: Intent, arg1: UserHandle, arg2: String)

    fun superSendBroadcastAsUser(arg0: Intent, arg1: UserHandle)

    fun superSendOrderedBroadcastAsUser(
        arg0: Intent, arg1: UserHandle, arg2: String,
        arg3: BroadcastReceiver, arg4: Handler, arg5: Int, arg6: String, arg7: Bundle
    )

    fun superSendStickyBroadcast(arg0: Intent)

    fun superSendStickyOrderedBroadcast(
        arg0: Intent, arg1: BroadcastReceiver, arg2: Handler, arg3: Int,
        arg4: String, arg5: Bundle
    )

    fun superRemoveStickyBroadcast(arg0: Intent)

    fun superSendStickyBroadcastAsUser(arg0: Intent, arg1: UserHandle)

    fun superSendStickyOrderedBroadcastAsUser(
        arg0: Intent, arg1: UserHandle, arg2: BroadcastReceiver,
        arg3: Handler, arg4: Int, arg5: String, arg6: Bundle
    )

    fun superRemoveStickyBroadcastAsUser(arg0: Intent, arg1: UserHandle)

    fun superRegisterReceiver(
        arg0: BroadcastReceiver, arg1: IntentFilter, arg2: String,
        arg3: Handler
    ): Intent?

    fun superRegisterReceiver(arg0: BroadcastReceiver, arg1: IntentFilter, arg2: Int): Intent?

    fun superRegisterReceiver(arg0: BroadcastReceiver, arg1: IntentFilter): Intent?

    fun superRegisterReceiver(
        arg0: BroadcastReceiver, arg1: IntentFilter, arg2: String, arg3: Handler,
        arg4: Int
    ): Intent?

    fun superUnregisterReceiver(arg0: BroadcastReceiver)

    fun superStartService(arg0: Intent): ComponentName?

    fun superStartForegroundService(arg0: Intent): ComponentName?

    fun superStopService(arg0: Intent): Boolean

    fun superBindService(arg0: Intent, arg1: Int, arg2: Executor, arg3: ServiceConnection): Boolean

    fun superBindService(arg0: Intent, arg1: ServiceConnection, arg2: Int): Boolean

    fun superBindIsolatedService(
        arg0: Intent, arg1: Int, arg2: String, arg3: Executor,
        arg4: ServiceConnection
    ): Boolean

    fun superUpdateServiceGroup(arg0: ServiceConnection, arg1: Int, arg2: Int)

    fun superUnbindService(arg0: ServiceConnection)

    fun superGetMainExecutor(): Executor

    fun superGetContentResolver(): ContentResolver

    fun superGetPackageManager(): PackageManager

    fun superGetBaseContext(): Context

    fun superGetMainLooper(): Looper

    fun superDeleteFile(arg0: String): Boolean

    fun superGetApplicationContext(): Context

    fun superGetApplicationInfo(): ApplicationInfo

    fun superStartInstrumentation(arg0: ComponentName, arg1: String, arg2: Bundle): Boolean

    fun superGetOpPackageName(): String

    fun superGetPackageResourcePath(): String

    fun superFileList(): Array<String>

    fun superCheckCallingPermission(arg0: String): Int

    fun superCheckCallingOrSelfPermission(arg0: String): Int

    fun superCheckSelfPermission(arg0: String): Int

    fun superEnforcePermission(arg0: String, arg1: Int, arg2: Int, arg3: String)

    fun superEnforceCallingPermission(arg0: String, arg1: String)

    fun superEnforceCallingOrSelfPermission(arg0: String, arg1: String)

    fun superGrantUriPermission(arg0: String, arg1: Uri, arg2: Int)

    fun superRevokeUriPermission(arg0: String, arg1: Uri, arg2: Int)

    fun superRevokeUriPermission(arg0: Uri, arg1: Int)

    fun superCheckUriPermission(arg0: Uri, arg1: Int, arg2: Int, arg3: Int): Int

    fun superCheckUriPermission(
        arg0: Uri,
        arg1: String,
        arg2: String,
        arg3: Int,
        arg4: Int,
        arg5: Int
    ): Int

    fun superCheckCallingUriPermission(arg0: Uri, arg1: Int): Int

    fun superCheckCallingOrSelfUriPermission(arg0: Uri, arg1: Int): Int

    fun superEnforceUriPermission(
        arg0: Uri, arg1: String, arg2: String, arg3: Int, arg4: Int, arg5: Int,
        arg6: String
    )

    fun superEnforceUriPermission(arg0: Uri, arg1: Int, arg2: Int, arg3: Int, arg4: String)

    fun superEnforceCallingUriPermission(arg0: Uri, arg1: Int, arg2: String)

    fun superEnforceCallingOrSelfUriPermission(arg0: Uri, arg1: Int, arg2: String)

    @Throws(PackageManager.NameNotFoundException::class)
    fun superCreatePackageContext(arg0: String, arg1: Int): Context

    @Throws(PackageManager.NameNotFoundException::class)
    fun superCreateContextForSplit(arg0: String): Context

    fun superCreateConfigurationContext(arg0: Configuration): Context

    fun superCreateDisplayContext(arg0: Display): Context

    fun superIsRestricted(): Boolean

    fun superCreateDeviceProtectedStorageContext(): Context

    fun superIsDeviceProtectedStorage(): Boolean

    fun superRegisterComponentCallbacks(arg0: ComponentCallbacks)

    fun superUnregisterComponentCallbacks(arg0: ComponentCallbacks)

    fun superAttachBaseContext(arg0: Context)

}