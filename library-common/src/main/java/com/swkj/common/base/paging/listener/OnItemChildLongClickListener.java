package com.swkj.common.base.paging.listener;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;


public interface OnItemChildLongClickListener {
    /**
     * callback method to be invoked when an item in this view has been
     * click and held
     *
     * @param adapter  this BaseQuickAdapter adapter
     * @param view     The childView whihin the itemView that was clicked and held.
     * @param position The position of the view int the adapter
     * @return true if the callback consumed the long click ,false otherwise
     */
    boolean onItemChildLongClick(@NonNull PagingDataAdapter<?,?> adapter, @NonNull View view, int position);
}