package com.swkj.common.base.paging.listener;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.listener.GridSpanSizeLookup;

public interface BaseListenerImp {
    /**
     * Register a callback to be invoked when an item in this RecyclerView has
     * been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    void setOnItemClickListener(@Nullable OnItemClickListener listener);

    void setOnItemLongClickListener(@Nullable OnItemLongClickListener listener);

    void setOnItemChildClickListener(@Nullable OnItemChildClickListener listener);

    void setOnItemChildLongClickListener(@Nullable OnItemChildLongClickListener listener);

    void setGridSpanSizeLookup(@Nullable GridSpanSizeLookup spanSizeLookup);
}