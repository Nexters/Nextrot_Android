package com.nextrot.troter.banners

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nextrot.troter.CommonUtil


class BannerIndicatorDecoration : RecyclerView.ItemDecoration() {
    private val colorActive = Color.parseColor("#ffffff")
    private val colorInactive = Color.parseColor("#66ffffff")
    private val defaultPosLeft = CommonUtil.toDP(24)
    private val defaultPosBottom = CommonUtil.toDP(30)
    private val indicatorItemLength = CommonUtil.toDP(6)
    private val indicatorMargin = CommonUtil.toDP(6)
    private val indicatorRadius = CommonUtil.toDP(3)
    private val paint = Paint()

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val itemCount = parent.adapter!!.itemCount

        val indicatorStartX = defaultPosLeft
        val indicatorPosY = parent.height - defaultPosBottom

        drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount)

        val layoutManager = parent.layoutManager as LinearLayoutManager?
        val activePosition = layoutManager!!.findFirstVisibleItemPosition()

        if (activePosition == RecyclerView.NO_POSITION) {
            return
        }

        drawHighlight(c, indicatorStartX, indicatorPosY, activePosition)
    }

    private fun drawInactiveIndicators(c: Canvas, indicatorStartX: Float, indicatorPosY: Float, itemCount: Int) {
        paint.color = colorInactive

        // width of item indicator including padding

        var start = indicatorStartX
        for (i in 0 until itemCount) {
            // draw the line for every item
            c.drawCircle(start, indicatorPosY, indicatorRadius, paint)
            start += indicatorItemLength + indicatorMargin
        }
    }

    private fun drawHighlight(
        c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
        highlightPosition: Int
    ) {
        paint.color = colorActive

        val highlightStart = indicatorStartX + (indicatorItemLength + indicatorMargin) * highlightPosition
        c.drawCircle(highlightStart, indicatorPosY, indicatorRadius, paint)
    }
}