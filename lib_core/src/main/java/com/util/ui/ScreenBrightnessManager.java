/*
 * Copyright (C) 2016 - 2017 贵阳货车帮科技有限公司
 */

package com.util.ui;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

/**
 * author : taowang
 * date :2018/7/27
 * description:屏幕亮度控制类
 **/
public final class ScreenBrightnessManager {

    //手动调节屏幕亮度
    public static final int SCREEN_BRIGHTNESS_MODE_MANUAL = 0;
    //自动调节屏幕亮度
    public static final int SCREEN_BRIGHTNESS_MODE_AUTOMATIC = 1;

    public static final int MAX_SCREEN_BRIGHTNESS = 255;
    public static final int MIN_SCREEN_BRIGHTNESS = 0;
    private static final float MAX_SCREEN_BRIGHTNESS_FLOAT = 255.0f;

    private ScreenBrightnessManager() {
        throw new AssertionError("Don't instance! ");
    }

    public static int getScreenMode(Context context) {
        if (context == null) {
            return -1;
        }
        int screenMode = SCREEN_BRIGHTNESS_MODE_MANUAL;
        try {
            screenMode = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return screenMode;
    }

    /**
     * 获得当前屏幕亮度值 0--255
     *
     * @return
     */
    public static int getScreenBrightness(Context context) {
        if (context == null) {
            return -1;
        }
        int screenBrightness = 255;
        try {
            screenBrightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return screenBrightness;
    }


    public static void setScreenMode(Context context, int paramInt) {
        if (context == null) {
            return;
        }
        try {
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, paramInt);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public static void saveScreenBrightness(Context context, int paramInt) {
        if (context == null) {
            return;
        }
        try {
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, paramInt);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    /**
     * 保存当前的屏幕亮度值，并使之生效
     */
    public static void setScreenBrightness(Activity activity, int paramInt) {
        if (activity == null) {
            return;
        }
        Window localWindow = activity.getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
        localLayoutParams.screenBrightness = paramInt / MAX_SCREEN_BRIGHTNESS_FLOAT;
        localWindow.setAttributes(localLayoutParams);
    }
}
