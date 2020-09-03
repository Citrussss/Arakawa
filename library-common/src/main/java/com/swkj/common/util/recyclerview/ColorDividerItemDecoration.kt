package com.bs.blaufuchslive.ui.widget.recyclerview

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * author pisa
 * date  2020/5/18
 * version 1.0
 * effect :颜色分割线
 */
class ColorDividerItemDecoration : ItemDecoration {
    constructor(context: Context, orientation: Int) {
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        if (mDivider == null) {
            Log.w(TAG, "@android:attr/listDivider was not set in the theme used for this "
                    + "DividerItemDecoration. Please set that attribute all call setDrawable()")
        }
        a.recycle()
        setOrientation(orientation)
    }

    val HORIZONTAL = LinearLayout.HORIZONTAL
    val VERTICAL = LinearLayout.VERTICAL

    private val TAG = "DividerItem"
    private val ATTRS = intArrayOf(R.attr.listDivider)

    private var mDivider: Drawable? = null

    /**
     * Current orientation. Either [.HORIZONTAL] or [.VERTICAL].
     */
    private var mOrientation = 0

    private val mBounds = Rect()

    public var isPaddingMode = false
    public var leftPadding = 0
    public var rightPadding = 0
    public var topPadding = 0
    public var bottomPadding = 0
    /**
     * Creates a divider [RecyclerView.ItemDecoration] that can be used with a
     * [LinearLayoutManager].
     *
     * @param context Current context, it will be used to access resources.
     * @param orientation Divider orientation. Should be [.HORIZONTAL] or [.VERTICAL].
     */
    fun DividerItemDecoration(context: Context, orientation: Int) {
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        if (mDivider == null) {
            Log.w(TAG, "@android:attr/listDivider was not set in the theme used for this "
                    + "DividerItemDecoration. Please set that attribute all call setDrawable()")
        }
        a.recycle()
        setOrientation(orientation)
    }

    /**
     * Sets the orientation for this divider. This should be called if
     * [RecyclerView.LayoutManager] changes orientation.
     *
     * @param orientation [.HORIZONTAL] or [.VERTICAL]
     */
    fun setOrientation(orientation: Int) {
        require(!(orientation != HORIZONTAL && orientation != VERTICAL)) { "Invalid orientation. It should be either HORIZONTAL or VERTICAL" }
        mOrientation = orientation
    }

    /**
     * Sets the [Drawable] for this divider.
     *
     * @param drawable Drawable that should be used as a divider.
     */
    fun setDrawable(drawable: Drawable) {
        requireNotNull(drawable) { "Drawable cannot be null." }
        mDivider = drawable
    }

    /**
     * @return the [Drawable] for this divider.
     */
    fun getDrawable(): Drawable? {
        return mDivider
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (parent.layoutManager == null || mDivider == null) {
            return
        }
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left: Int
        val right: Int
        if (isPaddingMode) {
            left = leftPadding
            right = (parent.width - rightPadding).coerceAtLeast(left)
        } else if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(left, parent.paddingTop, right,
                    parent.height - parent.paddingBottom)
        } else {
            left = 0
            right = parent.width
        }
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            val bottom = mBounds.bottom + Math.round(child.translationY)
            val top = bottom - (mDivider?.intrinsicHeight ?: 0)
            mDivider?.setBounds(left, top, right, bottom)
            mDivider?.draw(canvas)
        }
        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val top: Int
        val bottom: Int
        if (parent.clipToPadding) {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            canvas.clipRect(parent.paddingLeft, top,
                    parent.width - parent.paddingRight, bottom)
        } else {
            top = 0
            bottom = parent.height
        }
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.layoutManager?.getDecoratedBoundsWithMargins(child, mBounds)
            val right = mBounds.right + Math.round(child.translationX)
            val left = right - (mDivider?.intrinsicWidth ?: 0)
            mDivider?.setBounds(left, top, right, bottom)
            mDivider?.draw(canvas)
        }
        canvas.restore()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (mDivider == null) {
            outRect[0, 0, 0] = 0
            return
        }
        if (mOrientation == VERTICAL) {
            outRect[0, 0, 0] = mDivider?.intrinsicHeight ?: 0
        } else {
            outRect[0, 0, mDivider?.intrinsicWidth ?: 0] = 0
        }
    }
}