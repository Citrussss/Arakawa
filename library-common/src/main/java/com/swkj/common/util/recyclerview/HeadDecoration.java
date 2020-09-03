//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.swkj.common.util.recyclerview;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import java.util.HashMap;
import java.util.Map;


/**
 * recyclerview 的 分割头，带悬浮效果
 */
public abstract class HeadDecoration extends RecyclerView.ItemDecoration {
    private String TAG = "QDX";
    private Paint mHeaderTxtPaint = new Paint(1);
    private Paint mHeaderContentPaint;
    private int headerHeight = 136;
    private int textPaddingLeft = 50;
    private int textSize = 50;
    private int textColor = -16777216;
    private int headerContentColor = -1118482;
    private final float txtYAxis;
    private RecyclerView mRecyclerView;
    private boolean isInitHeight = false;
    private SparseIntArray stickyHeaderPosArray = new SparseIntArray();
    private GestureDetector gestureDetector;
    private SparseArray<View> headViewMap = new SparseArray<>();
    private HeadDecoration.OnHeaderClickListener headerClickEvent;
    private OnGestureListener gestureListener = new OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            for (int i = 0; i < HeadDecoration.this.stickyHeaderPosArray.size(); ++i) {
                int value = HeadDecoration.this.stickyHeaderPosArray.valueAt(i);
                float y = e.getY();
                if ((float) (value - HeadDecoration.this.headerHeight) <= y && y <= (float) value) {
                    if (HeadDecoration.this.headerClickEvent != null) {
                        HeadDecoration.this.headerClickEvent.headerClick(HeadDecoration.this.stickyHeaderPosArray.keyAt(i));
                    }

                    return true;
                }
            }

            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    };
    private HeadDecoration.OnDecorationHeadDraw headerDrawEvent;
    private Map<String, Drawable> imgDrawableMap = new HashMap<>();

    protected HeadDecoration() {
        this.mHeaderTxtPaint.setColor(this.textColor);
        this.mHeaderTxtPaint.setTextSize((float) this.textSize);
        this.mHeaderTxtPaint.setTextAlign(Align.LEFT);
        this.mHeaderContentPaint = new Paint(1);
        this.mHeaderContentPaint.setColor(this.headerContentColor);
        FontMetrics fontMetrics = this.mHeaderTxtPaint.getFontMetrics();
        float total = -fontMetrics.ascent + fontMetrics.descent;
        this.txtYAxis = total / 2.0F - fontMetrics.descent;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View itemView, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, itemView, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            setLinearLayoutSpaceItemDecoration(outRect, itemView, parent);
        } else if (layoutManager instanceof GridLayoutManager) {
            setLinearLayoutSpaceItemDecoration(outRect, itemView, parent);
            //列数
//            spanCount = gridLayoutManager.getSpanCount();
//            setNGridLayoutSpaceItemDecoration(outRect, view, parent, state);

        }
    }

    private void setLinearLayoutSpaceItemDecoration(@NonNull Rect outRect, @NonNull View itemView, @NonNull RecyclerView parent) {
        if (this.mRecyclerView == null) {
            this.mRecyclerView = parent;
        }

        if (this.headerDrawEvent != null && !this.isInitHeight) {
            View headerView = this.headerDrawEvent.getHeaderView(0);
            headerView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            this.headerHeight = headerView.getMeasuredHeight();
            this.isInitHeight = true;
        }

        int pos = parent.getChildAdapterPosition(itemView);
        String curHeaderName = this.getHeaderName(pos);
        if (curHeaderName != null) {
            if (pos == 0 || !curHeaderName.equals(this.getHeaderName(pos - 1))) {
                outRect.top = this.headerHeight;
//                Timber.w("top==>" + outRect.top + ",,this.headerHeight==>" + this.headerHeight +
//                        ",,this.itemView==>" + itemView.getHeight());
            }

        }
    }

    public abstract String getHeaderName(int var1);

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        super.onDrawOver(canvas, recyclerView, state);
        if (this.mRecyclerView == null) {
            this.mRecyclerView = recyclerView;
        }

        if (this.gestureDetector == null) {
            this.gestureDetector = new GestureDetector(recyclerView.getContext(), this.gestureListener);
            recyclerView.setOnTouchListener((v, event) -> HeadDecoration.this.gestureDetector.onTouchEvent(event));
        }

        this.stickyHeaderPosArray.clear();
        int childCount = recyclerView.getChildCount();
        int left = recyclerView.getLeft() + recyclerView.getPaddingLeft();
        int right = recyclerView.getRight() - recyclerView.getPaddingRight();
        String firstHeaderName = null;
        int firstPos = 0;
        int translateTop = 0;

        for (int i = 0; i < childCount; ++i) {
            View childView = recyclerView.getChildAt(i);
            int pos = recyclerView.getChildAdapterPosition(childView);
            String curHeaderName = this.getHeaderName(pos);
            if (i == 0) {
                firstHeaderName = curHeaderName;
                firstPos = pos;
            }

            if (curHeaderName != null) {
                int viewTop = childView.getTop() + recyclerView.getPaddingTop();
                if (pos == 0 || !curHeaderName.equals(this.getHeaderName(pos - 1))) {
                    if (this.headerDrawEvent != null) {
                        View headerView;
                        if (this.headViewMap.get(pos) == null) {
                            headerView = this.headerDrawEvent.getHeaderView(pos);
                            headerView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                            headerView.setDrawingCacheEnabled(true);
                            headerView.layout(0, 0, right, this.headerHeight);
                            this.headViewMap.put(pos, headerView);
                            canvas.drawBitmap(headerView.getDrawingCache(), (float) left, (float) (viewTop - this.headerHeight), null);
                        } else {
                            headerView = this.headViewMap.get(pos);
                            canvas.drawBitmap(headerView.getDrawingCache(), (float) left, (float) (viewTop - this.headerHeight), null);
                        }
                    } else {
                        canvas.drawRect((float) left, (float) (viewTop - this.headerHeight), (float) right, (float) viewTop, this.mHeaderContentPaint);
                        FontMetrics fontMetrics = mHeaderTxtPaint.getFontMetrics();
                        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
                        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
                        float baseLineY = viewTop - (headerHeight + top + bottom) / 2;
                        canvas.drawText(curHeaderName, (float) (left + this.textPaddingLeft), baseLineY, this.mHeaderTxtPaint);
//                        canvas.drawText(curHeaderName, (float) (left + this.textPaddingLeft), (float) (viewTop - this.headerHeight / 2) + this.txtYAxis, this.mHeaderTxtPaint);
                    }

                    if (this.headerHeight < viewTop && viewTop <= 2 * this.headerHeight) {
                        translateTop = viewTop - 2 * this.headerHeight;
                    }

                    this.stickyHeaderPosArray.put(pos, viewTop);
//                    Log.i(this.TAG, "绘制各个头部" + pos);
                }
            }
        }
        //此处开始处理悬浮头部
        if (firstHeaderName != null) {
            Log.e(firstHeaderName, String.valueOf(recyclerView.getTop()));
            canvas.save();
            canvas.translate(0.0F, (float) translateTop);
            if (this.headerDrawEvent != null) {
                View headerView;
                if (this.headViewMap.get(firstPos) == null) {
                    headerView = this.headerDrawEvent.getHeaderView(firstPos);
                    headerView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                    headerView.setDrawingCacheEnabled(true);
                    headerView.layout(0, 0, right, this.headerHeight);
                    this.headViewMap.put(firstPos, headerView);
                    canvas.drawBitmap(headerView.getDrawingCache(), (float) left, 0.0F, null);
                } else {
                    headerView = this.headViewMap.get(firstPos);
                    canvas.drawBitmap(headerView.getDrawingCache(), (float) left, 0.0F, null);
                }
            } else {
                canvas.drawRect((float) left, 0.0F, (float) right, (float) this.headerHeight, this.mHeaderContentPaint);
                FontMetrics fontMetrics = mHeaderTxtPaint.getFontMetrics();
                float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
                float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
                int baseLineY = (int) (headerHeight - top - bottom) / 2;
                canvas.drawText(firstHeaderName, (float) (left + this.textPaddingLeft), (float) baseLineY, this.mHeaderTxtPaint);
            }

            canvas.restore();
//            Log.i(this.TAG, "绘制悬浮头部");
        }
    }

    public void setOnHeaderClickListener(HeadDecoration.OnHeaderClickListener headerClickListener) {
        this.headerClickEvent = headerClickListener;
    }

    public void setOnDecorationHeadDraw(HeadDecoration.OnDecorationHeadDraw decorationHeadDraw) {
        this.headerDrawEvent = decorationHeadDraw;
    }

    public void loadImage(final String url, final int pos, ImageView imageView) {
        if (this.getImg(url) != null) {
            Log.i("qdx", "Glide 加载完图片" + pos);
            imageView.setImageDrawable(this.getImg(url));
        } else {
            Glide.with(this.mRecyclerView.getContext()).load(url).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                    Log.i("qdx", "Glide回调" + pos);
                    HeadDecoration.this.headViewMap.remove(pos);
                    HeadDecoration.this.imgDrawableMap.put(url, resource);
                    HeadDecoration.this.mRecyclerView.postInvalidate();
                }
            });
        }

    }

    private Drawable getImg(String url) {
        return (Drawable) this.imgDrawableMap.get(url);
    }

    public void onDestory() {
        this.headViewMap.clear();
        this.imgDrawableMap.clear();
        this.stickyHeaderPosArray.clear();
        this.mRecyclerView = null;
        this.setOnHeaderClickListener(null);
        this.setOnDecorationHeadDraw(null);
    }

    public void setHeaderHeight(int headerHeight) {
        this.headerHeight = headerHeight;
    }

    public void setTextPaddingLeft(int textPaddingLeft) {
        this.textPaddingLeft = textPaddingLeft;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        this.mHeaderTxtPaint.setTextSize((float) textSize);
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        this.mHeaderTxtPaint.setColor(textColor);
    }

    public void setHeaderContentColor(int headerContentColor) {
        this.headerContentColor = headerContentColor;
        this.mHeaderContentPaint.setColor(headerContentColor);
    }

    public interface OnDecorationHeadDraw {
        View getHeaderView(int var1);
    }

    public interface OnHeaderClickListener {
        void headerClick(int var1);
    }
}
