package com.swkj.common.listener;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingDataAdapter;

import com.swkj.common.base.paging.BasePageListAdapter;
import com.swkj.common.base.paging.listener.OnItemChildClickListener;
import com.swkj.common.base.paging.listener.OnItemClickListener;

import org.jetbrains.annotations.NotNull;

public abstract class OnItemClickPageListener implements OnItemChildClickListener,
        OnItemClickListener {
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
    public OnItemClickPageListener() {

    }

    /**
     * 指定延迟
     *
     * @param delay 延迟毫秒
     */
    public OnItemClickPageListener(long delay) {
        mDelay = delay;
    }

    /**
     * 安全点击 避免连点
     *
     * @param
     */
    public abstract void onSafeClick(@Nullable final PagingDataAdapter<?, ?> adapter, @Nullable final View view,
            final int position);

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

//    @Override
//    public void onItemChildClick(@Nullable final BasePageListAdapter<?, ?> adapter, @NotNull final View view,
//            final int position) {
//        if (isFastDoubleClick(mDelay)) {
//            return;
//        }
//        this.onSafeClick(adapter, view, position);
//    }
//
//    @Override
//    public void onItemClick(@Nullable final BasePageListAdapter<?, ?> adapter, @Nullable final View view,
//            final int position) {
//        if (isFastDoubleClick(mDelay)) {
//            return;
//        }
//        this.onSafeClick(adapter, view, position);
//    }

    @Override
    public void onItemChildClick(@NonNull PagingDataAdapter<?, ?> adapter, @NonNull View view, int position) {
        if (isFastDoubleClick(mDelay)) {
            return;
        }
        this.onSafeClick(adapter, view, position);
    }

    @Override
    public void onItemClick(@NonNull PagingDataAdapter<?, ?> adapter, @NonNull View view, int position) {
        if (isFastDoubleClick(mDelay)) {
            return;
        }
        this.onSafeClick(adapter, view, position);
    }
}
