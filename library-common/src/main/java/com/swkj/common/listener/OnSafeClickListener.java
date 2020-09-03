package com.swkj.common.listener;

import android.view.View;

public abstract class OnSafeClickListener implements View.OnClickListener {
    /**
     * 最后点击时间
     */
    private static long lastClickTime;

    /**
     * 延迟毫秒
     */
    private long mDelay = 600;

    /**
     * 安全点击事件 避免连点
     */
    public OnSafeClickListener() {

    }

    /**
     * 指定延迟
     *
     * @param delay 延迟毫秒
     */
    public OnSafeClickListener(long delay) {
        mDelay = delay;
    }

    @Override
    public void onClick(View v) {
        if (isFastDoubleClick(mDelay)) {
            return;
        }

        this.onSafeClick(v);
    }

    /**
     * 安全点击 避免连点
     *
     * @param v
     */
    public abstract void onSafeClick(View v);

    /**
     * 检查是否快速连续点击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(500);
    }

    /**
     * 检查是否快速连续点击
     *
     * @param delay
     * @return
     */
    public static boolean isFastDoubleClick(long delay) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < delay && time - lastClickTime >= 0) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
