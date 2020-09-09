package com.example.kotlin

import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max

class MyLayoutManager : RecyclerView.LayoutManager() {

    /**
     * 3.   可以滑动
     *
     * 使LayoutManager具有垂直滚动的功能
     */
    override fun canScrollVertically(): Boolean {
        return true
    }


    /**
     * mSumDy 保存所有移动过的dy，如果当前移动的距离<0，那么就不再累加dy，直接让它移动到y=0的位置
     * 因为之前已经移动的距离是mSumdy; 所以计算方法为：
     *      travel + mSumdy = 0
     *  => travel = -mSumdy
     */
    private var mSumDy = 0 //滑动总和

    /**
     *  4.  接收每次滚动的距离dy  控制上下滑动的范围
     *  @param dy 当手指由下往上滑时，dy>0   让所有子Item向上移动，向上移动明显是需要减去dy的
     *            当手指由上往下滑时，dy<0
     */
    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        var travel = dy
        if (mSumDy + dy < 0) { //滑动到顶部，不让继续滑动
            travel = -mSumDy
        } else if (mSumDy + dy > mTotalHeight - getVerticalSpace()) { //滑动到底部
            travel = mTotalHeight - getVerticalSpace() - mSumDy;
        }
        mSumDy += travel
        //平移 itemView
        offsetChildrenVertical(-travel)
        return dy
    }

    /**
     *  记录所有 item 高度
     *  mSumDy + dy表示当前的移动距离，mTotalHeight - getVerticalSpace()表示当滑动到底时滚动的总距离；
     *  travel + mSumDy = mTotalHeight - getVerticalSpace();
     *  => travel = mTotalHeight - getVerticalSpace() - mSumDy;
     */
    private var mTotalHeight = 0

    /**
     * 2.   布局文件
     */
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        if (recycler == null) return
        var offsetY = 0
        for (i in 0 until itemCount) {
            //获取 item 对应的 view
            val view = recycler.getViewForPosition(i)
            addView(view)
            //计算宽高
            measureChildWithMargins(view, 0, 0)
            val width = getDecoratedMeasuredWidth(view)
            val height = getDecoratedMeasuredHeight(view)
            //布局 view
            layoutDecorated(view, 0, offsetY, width, offsetY + height)
            offsetY += height
        }
        //如果所有子View的高度和没有填满RecyclerView的高度，
        // 则将高度设置为RecyclerView的高度
        mTotalHeight = max(offsetY, getVerticalSpace());
    }

    private fun getVerticalSpace(): Int {
        return height - paddingBottom - paddingTop
    }

    /**
     * 1. 默认布局 必须重写
     */
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }
}