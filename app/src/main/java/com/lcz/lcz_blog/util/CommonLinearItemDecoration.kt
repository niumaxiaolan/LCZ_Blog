package com.lcz.lcz_blog.util

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.liuchuanzheng.baselib.util.lcz.LCZUtil.dp2px


/**
 * <pre>
 * <img width="128" height="110" src="https://tse3-mm.cn.bing.net/th/id/OIP-C.en-w_oH-yn2UsSRfWnOsGAHaGY?w=198&h=180&c=7&r=0&o=5&dpr=1.25&pid=1.7" alt="马刺">
 * 作者:     <a href="https://github.com/liuchuanzheng">刘传政</a>
 * 创建时间:  16:37 2021/11/2 0002
 * QQ:      1052374416
 * 电话:     18501231486
 * 描述:     通用的recyclerview的分割线。只适用竖向的。可设置第一个，最后一个是否有分割线。可设置分割线颜色，分割线的左右边距
 * </pre>
 */
class CommonLinearItemDecoration(
    var dividerColor: Int = 0,
    var dividerHeight: Int = dp2px(10f),
    var leftMargin: Int = 0,
    var rightMargin: Int = 0,
    var firstVisible: Boolean = false,
    var lastVisible: Boolean = false,
) : ItemDecoration() {
    private lateinit var mPaint: Paint

    init {
        mPaint = Paint()
        mPaint.isAntiAlias = true //抗锯齿
        mPaint.color = dividerColor //颜色
    }

    //设置每个item的边距
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (isLinearVertical(parent).not()) {
            return
        }
        val viewPosition = parent.layoutManager!!.getPosition(view)
        val childCount = parent.adapter!!.itemCount
        if (viewPosition == 0) {
            //第一个ItemView
            if (firstVisible) {
                outRect.top = dividerHeight //指相对itemView顶部的偏移量
            }
        } else if (viewPosition > 0 && viewPosition < childCount - 1) {
            //中间ItemView
            outRect.top = dividerHeight
        } else if (viewPosition == childCount - 1) {
            //最后一个ItemView
            outRect.top = dividerHeight
            if (lastVisible) {
                outRect.bottom = dividerHeight
            }

        }
    }

    //绘制在recycleview背景。也就是每个分割线.这里针对的是可见的
    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        if (isLinearVertical(parent).not()) {
            return
        }
        if (dividerColor == 0) {
            return
        }
        val childCount = parent.childCount //这是可见的count，不是所有的
        //因为getItemOffsets是针对每一个ItemView，而onDraw方法是针对RecyclerView本身，所以需要循环遍历来设置
        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            val index = parent.getChildAdapterPosition(view)
            var dividerTop = view.top - dividerHeight
            var dividerLeft = parent.paddingLeft + leftMargin
            var dividerBottom = view.top
            var dividerRight = parent.width - parent.paddingRight - rightMargin

            //第一个ItemView不需要绘制
            if (index == 0) {
                if (firstVisible) {
                    canvas.drawRect(
                        dividerLeft.toFloat(),
                        dividerTop.toFloat(),
                        dividerRight.toFloat(),
                        dividerBottom.toFloat(),
                        mPaint
                    )
                }
            } else if (index > 0 && index <= parent.adapter!!.itemCount - 1) {
                canvas.drawRect(
                    dividerLeft.toFloat(),
                    dividerTop.toFloat(),
                    dividerRight.toFloat(),
                    dividerBottom.toFloat(),
                    mPaint
                )
            }
            if (index == parent.adapter!!.itemCount - 1) {
                //最后一个ItemView
                if (lastVisible) {
                    dividerTop = view.top
                    dividerLeft = parent.paddingLeft + leftMargin
                    dividerBottom = view.bottom + dividerHeight
                    dividerRight = parent.width - parent.paddingRight - rightMargin
                    canvas.drawRect(
                        dividerLeft.toFloat(),
                        dividerTop.toFloat(),
                        dividerRight.toFloat(),
                        dividerBottom.toFloat(),
                        mPaint
                    )
                }
            }
        }
    }

    //绘制在recycleview上边
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }

    private fun isLinearVertical(parent: RecyclerView): Boolean {
        if (parent.layoutManager != null) {
            if (parent.layoutManager is LinearLayoutManager) {
                if ((parent.layoutManager as LinearLayoutManager?)!!.orientation == RecyclerView.VERTICAL) {
                    return true
                }
            }
        }
        return false
    }

}