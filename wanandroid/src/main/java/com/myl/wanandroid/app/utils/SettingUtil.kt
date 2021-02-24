package com.myl.wanandroid.app.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.preference.PreferenceManager
import android.view.View
import android.widget.ProgressBar
import com.jess.arms.utils.ArmsUtils
import com.kingja.loadsir.core.LoadService
import com.myl.wanandroid.app.utils.DatetimeUtil
import com.myl.wanandroid.R
import com.myl.wanandroid.app.weight.loadCallBack.LoadingCallback
import java.lang.reflect.InvocationTargetException
import kotlin.math.roundToInt


object SettingUtil {

    /**
     * 获取主题颜色
     */
    fun getColor(context: Context): Int {
        val setting = PreferenceManager.getDefaultSharedPreferences(context)
        val defaultColor = ArmsUtils.getColor(context, R.color.colorPrimary)
        val color = setting.getInt("color", defaultColor)
        return if (color != 0 && Color.alpha(color) != 255) {
            defaultColor
        } else {
            color
        }

    }

    /**
     * 设置主题颜色
     */
    fun setColor(context: Context, color: Int) {
        val setting = PreferenceManager.getDefaultSharedPreferences(context)
        setting.edit().putInt("color", color).apply()
    }

    /**
     * 设置列表动画模式
     */
    fun setListMode(context: Context, mode: Int) {
        val setting = PreferenceManager.getDefaultSharedPreferences(context)
        setting.edit().putInt("mode", mode).apply()
    }

    /**
     * 获取列表动画模式
     */
    fun getListMode(context: Context): Int {
        val setting = PreferenceManager.getDefaultSharedPreferences(context)
        return setting.getInt("mode", 0)
    }

    /**
     * 获取是支持侧滑返回
     */
    fun getSlidable(context: Context): Boolean {
        val setting = PreferenceManager.getDefaultSharedPreferences(context)
        return setting.getBoolean("slidable", true)
    }

    /**
     * 获取是否请求置顶文章
     */
    fun getRequestTop(context: Context): Boolean {
        val setting = PreferenceManager.getDefaultSharedPreferences(context)
        return setting.getBoolean("top", true)
    }

    fun getColorStateList(context: Context): ColorStateList {
        val colors = intArrayOf(getColor(context), ArmsUtils.getColor(context, R.color.colorGray))
        val states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(android.R.attr.state_checked, android.R.attr.state_checked)
        states[1] = intArrayOf()
        return ColorStateList(states, colors)
    }

    fun getOneColorStateList(context: Context): ColorStateList {
        val colors = intArrayOf(getColor(context))
        val states = arrayOfNulls<IntArray>(1)
        states[0] = intArrayOf()
        return ColorStateList(states, colors)
    }

    fun getOneColorStateList(color: Int): ColorStateList {
        val colors = intArrayOf(color)
        val states = arrayOfNulls<IntArray>(1)
        states[0] = intArrayOf()
        return ColorStateList(states, colors)
    }

    /**
     * 设置shap文件的颜色
     *
     * @param view
     * @param color
     */
    fun setShapColor(view: View, color: Int) {
        val drawable = view.background as GradientDrawable
        drawable.setColor(color)
    }

    /**
     * 设置shap的渐变颜色
     */
    fun setShapColor(view: View, color: IntArray, orientation: GradientDrawable.Orientation) {
        val drawable = view.background as GradientDrawable
        drawable.orientation = orientation//渐变方向
        drawable.colors = color//渐变颜色数组
    }

    /**
     * 设置selector文件的颜色
     *
     * @param view
     * @param yesColor
     * @param noColor
     */
    fun setSelectorColor(view: View, yesColor: Int, noColor: Int) {
        val mySelectorGrad = view.background as StateListDrawable
        try {
            val slDraClass = StateListDrawable::class.java
            val getStateCountMethod = slDraClass.getDeclaredMethod("getStateCount", *arrayOfNulls(0))
            val getStateSetMethod = slDraClass.getDeclaredMethod("getStateSet", Int::class.javaPrimitiveType)
            val getDrawableMethod = slDraClass.getDeclaredMethod("getStateDrawable", Int::class.javaPrimitiveType)
            val count = getStateCountMethod.invoke(mySelectorGrad) as Int//对应item标签
            for (i in 0 until count) {
                val stateSet = getStateSetMethod.invoke(mySelectorGrad, i) as IntArray//对应item标签中的 android:state_xxxx
                if (stateSet.isEmpty()) {
                    val drawable = getDrawableMethod.invoke(mySelectorGrad, i) as GradientDrawable//这就是你要获得的Enabled为false时候的drawable
                    drawable.setColor(yesColor)
                } else {
                    for (j in stateSet.indices) {
                        val drawable = getDrawableMethod.invoke(mySelectorGrad, i) as GradientDrawable//这就是你要获得的Enabled为false时候的drawable
                        drawable.setColor(noColor)
                    }
                }
            }
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }

    /**
     * 设置颜色透明一半
     * @param color
     * @return
     */
    fun translucentColor(color: Int): Int {
        val factor = 0.5f
        val alpha = (Color.alpha(color) * factor).roundToInt()
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }

    /**
     * 设置loading的颜色 加载布局
     */
    fun setLoadingColor(context: Context, loadsir: LoadService<Any>) {
        loadsir.setCallBack(LoadingCallback::class.java) { _, view ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.findViewById<ProgressBar>(R.id.loading_progress).indeterminateTintMode = PorterDuff.Mode.SRC_ATOP
                view.findViewById<ProgressBar>(R.id.loading_progress).indeterminateTintList = getOneColorStateList(context)
            }
        }
    }

    /**
     * 判断是否过了国庆节了
     */
    fun isHoliDay(): Boolean {
        return DatetimeUtil.nows <= DatetimeUtil.getCustomTime("2019-10-7")
    }


}
