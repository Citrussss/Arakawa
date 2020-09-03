package com.swkj.common.base;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pisa
 * @version 1.0
 * @date 2020/3/3
 * @effect : 抽象选择
 */
public abstract class BaseSelectAdapter<T extends BaseSelectAdapter.Item, K extends BaseViewHolder> extends BaseMultiItemQuickAdapter<T, K> {
    protected List<T> selectList = new ArrayList<>();

    /**
     * 默认-1为无限制。
     */
    private int maxSelectCount = -1;
    private boolean isReverse = true;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public BaseSelectAdapter(List<T> data) {
        super(data);
    }

    //    public BaseSelectAdapter(int layoutResId, @Nullable List<T> data) {
//        super(layoutResId, data);
//    }
//
//    public BaseSelectAdapter(@Nullable List<T> data) {
//        super(data);
//    }
//
//    public BaseSelectAdapter(int layoutResId) {
//        super(layoutResId);
//    }

    /**
     * @return 添加失败时返回false
     */
    public boolean select(T item) {
        T lastSelect = null;
        if (selectList.contains(item) ) {
            lastSelect = item;
            if (isReverse){
                selectList.remove(item);
            }
        } else if ((maxSelectCount != -1 && selectList.size() >= maxSelectCount)) {
            if (!selectList.isEmpty()) {
                lastSelect = selectList.get(0);
                selectList.remove(lastSelect);
            }
        }
        if (item != lastSelect) {
            selectList.add(item);
        }
        notifyItemChanged(getData().indexOf(lastSelect));
        notifyItemChanged(getData().indexOf(item));
        return true;
    }

    public interface Item extends MultiItemEntity {
        /**
         * @return 返回显示的标题
         */
        String getTitle();
    }

    public void setMaxSelectCount(int maxSelectCount) {
        this.maxSelectCount = maxSelectCount;
    }

    public List<T> getSelectList() {
        return selectList;
    }

    /**
     * 设置是否可以反选
     *
     * @param reverse
     */
    public void setReverse(boolean reverse) {
        isReverse = reverse;
    }
}
