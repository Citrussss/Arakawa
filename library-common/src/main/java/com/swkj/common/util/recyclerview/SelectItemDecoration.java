package com.swkj.common.util.recyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author pisa
 * date  2020/3/20
 * version 1.0
 * effect :选中View的装饰
 */
public abstract class SelectItemDecoration extends RecyclerView.ItemDecoration {
    /**
     * 获取View是否被选中
     *
     * @param position
     * @return
     */
    protected abstract boolean isSelect(int position);

    private int padding;
    private Drawable drawable;
    private Drawable bgDrawable;

    public SelectItemDecoration(int padding, Drawable drawable, Drawable bgDrawable) {
        this.padding = padding;
        this.drawable = drawable;
        this.bgDrawable = bgDrawable;
    }

    public SelectItemDecoration(int padding, Drawable drawable) {
        this.padding = padding;
        this.drawable = drawable;
    }

    public SelectItemDecoration(int padding) {
        this.padding = padding;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        int childAdapterPosition = parent.getChildAdapterPosition(view);
//        boolean select = isSelect(childAdapterPosition);
//        if (select) {
//            outRect.set(padding, padding, padding, padding);
//        } else {
//            outRect.set(0, padding, 0, padding);
//        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (bgDrawable != null) {
            for (int i = 0; i < parent.getChildCount(); i++) {
                View childAt = parent.getChildAt(i);
                int childAdapterPosition = parent.getChildAdapterPosition(childAt);
                boolean select = isSelect(childAdapterPosition);
                if (select) {
                    int left = childAt.getLeft() - padding;
                    int top = childAt.getTop() - padding;
                    int right = childAt.getRight() + padding;
                    int bottom = childAt.getBottom() + padding;
                    bgDrawable.setBounds(left, top, right, bottom);
                    bgDrawable.draw(c);
                }
            }
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (drawable != null) {
            for (int i = 0; i < parent.getChildCount(); i++) {
                View childAt = parent.getChildAt(i);
                int childAdapterPosition = parent.getChildAdapterPosition(childAt);
                boolean select = isSelect(childAdapterPosition);
                if (select) {
                    int left = childAt.getLeft() - padding;
                    int top = childAt.getTop() - padding;
                    int right = childAt.getRight() + padding;
                    int bottom = childAt.getBottom() + padding;
                    drawable.setBounds(left, top, right, bottom);
                    drawable.draw(c);
                }
            }
        }
    }
}
