package com.myl.plugin_core.res

import android.content.res.AssetManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import androidx.annotation.RequiresApi

class PluginResources(
    assets: AssetManager,
    var hostResource: Resources
) : Resources(assets, hostResource.displayMetrics, hostResource.configuration) {

    override fun getDrawable(id: Int): Drawable {
        return try {
            super.getDrawable(id)
        } catch (e: Throwable) {
            hostResource.getDrawable(id)
        }
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getDrawable(id: Int, theme: Theme?): Drawable {
        return try {
            super.getDrawable(id, theme)
        } catch (e: Throwable) {
            hostResource.getDrawable(id, theme)
        }
    }


    override fun getText(id: Int): CharSequence {
        return try {
            super.getText(id)
        } catch (e: Throwable) {
            hostResource.getText(id)
        }
    }

    override fun getText(id: Int, def: CharSequence?): CharSequence {
        return try {
            return super.getText(id, def)
        } catch (e: Throwable) {
            hostResource.getText(id, def)
        }
    }

}