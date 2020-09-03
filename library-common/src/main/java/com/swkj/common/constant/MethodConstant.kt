package com.swkj.common.constant

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.Color
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.view.View
import android.view.WindowManager.LayoutParams
import androidx.annotation.IntDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.SOURCE
import java.util.ArrayList
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * package ：com.bs.blaufuchslive.constant
 * author : kele
 * date : 2020/2/20 14:04
 * description :公共的静态方法
 */
object MethodConstant {

    @JvmStatic
    fun setTransparentForWindow(activity: Activity) {
        val window = activity.window
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        } else if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            window.setFlags(
                LayoutParams.FLAG_TRANSLUCENT_STATUS,
                LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
        }
    }

    /**
     * The constant TYPE_MIUI.
     */
    const val TYPE_MIUI = 0
    /**
     * The constant TYPE_FLYME.
     */
    const val TYPE_FLYME = 1
    /**
     * The constant TYPE_M.
     */
    const val TYPE_M = 3 //6.0

    /**
     * 设置状态栏透明
     *
     * @param activity the activity
     */
    @TargetApi(19)
    fun setTranslucentStatus(activity: Activity) { // 5.0以上系统状态栏透明
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            //清除透明状态栏
            window.clearFlags(LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            //设置状态栏颜色必须添加
            window.addFlags(LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT //设置透明
        } else if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) { //19
            activity.window.addFlags(LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    /**
     * 设置沉浸式状态栏
     *
     * @param activity     the activity
     * @param fontIconDark 状态栏字体和图标颜色是否为深色
     */
    /*fun setImmersiveStatusBar(activity: Activity, fontIconDark: Boolean) {
        setTranslucentStatus(activity)
        if (fontIconDark) {
            if (VERSION.SDK_INT >= VERSION_CODES.M) {
                setStatusBarFontIconDark(activity, TYPE_M)
            } else if (OSUtils.isMiui()) {
                setStatusBarFontIconDark(activity, TYPE_MIUI)
            } else if (OSUtils.isFlyme()) {
                setStatusBarFontIconDark(activity, TYPE_FLYME)
            } else { //其他情况下我们将状态栏设置为灰色，就不会看不见字体
                setStatusBarColor(activity, Color.LTGRAY) //灰色
            }
        }
    }*/

    /**
     * 设置文字颜色
     *
     * @param activity the activity
     * @param type     the type
     */
    fun setStatusBarFontIconDark(activity: Activity, @ViewType type: Int) {
        when (type) {
            TYPE_MIUI -> setMiuiUI(activity, true)
            TYPE_M -> setCommonUI(activity)
            TYPE_FLYME -> setFlymeUI(activity, true)
        }
    }

    /**
     * Sets common ui.
     *
     * @param activity the activity
     */
//设置6.0的字体
    fun setCommonUI(activity: Activity) {
        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            activity.window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
    }

    /**
     * Sets flyme ui.
     *
     * @param activity the activity
     * @param dark     the dark
     */
//设置Flyme的字体
    fun setFlymeUI(activity: Activity, dark: Boolean) {
        try {
            val window = activity.window
            val lp = window.attributes
            val darkFlag =
                LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags =
                LayoutParams::class.java.getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(lp)
            value = if (dark) {
                value or bit
            } else {
                value and bit.inv()
            }
            meizuFlags.setInt(lp, value)
            window.attributes = lp
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Sets miui ui.
     *
     * @param activity the activity
     * @param dark     the dark
     */
//设置MIUI字体
    fun setMiuiUI(activity: Activity, dark: Boolean) {
        try {
            val window = activity.window
            val clazz: Class<*> = activity.window.javaClass
            val layoutParams =
                Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            val darkModeFlag = field.getInt(layoutParams)
            val extraFlagField =
                clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            if (dark) { //状态栏亮色且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag)
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*suspend fun selectImage(count: Int, activity: Activity?): List<String> = suspendCoroutine  {
        val list: MutableList<String> = ArrayList()
        PictureSelector.create(activity)
            .openGallery(PictureMimeType.ofImage())
            .isWeChatStyle(true)
            .isCameraAroundState(true)
            .maxSelectNum(count)
            .minSelectNum(1)
            .imageSpanCount(3)
            .selectionMode(PictureConfig.MULTIPLE)
            .previewImage(true)
            .isCamera(true) //图片加载引擎，必传项
            .loadImageEngine(GlideEngine.createGlideEngine())
            .imageFormat(PictureMimeType.PNG)
            .compress(true)
            .forResult(object : OnResultCallbackListener {
                override fun onResult(result: List<LocalMedia>) {
                    for (bean in result) {
                        list.add(bean.compressPath)
                    }
                    it.resume(list)
                }

                override fun onCancel() {
                    it.resumeWithException(Throwable("null"))
                }
            })
    }*/

    /**
     * The interface View type.
     */
    @IntDef(TYPE_MIUI, TYPE_FLYME, TYPE_M)
    @Retention(SOURCE)
    internal annotation class ViewType
}