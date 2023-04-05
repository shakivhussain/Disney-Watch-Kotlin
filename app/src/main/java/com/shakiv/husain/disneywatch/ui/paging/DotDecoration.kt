package com.shakiv.husain.disneywatch.ui.paging

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DotDecoration(
    private val activeColor: Int, private val inactiveColor: Int, private val radius: Float,
    private val padding: Float
) : RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val itemCount = parent.adapter?.itemCount ?: 0
        val activePosition =
            (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        // Draw dots for all the items in the RecyclerView
        for (i in 0 until itemCount) {
            val x = (parent.width / 2) + ((i - activePosition) * 2 * (padding + radius)).toInt()
            val y = (parent.height - 2 * radius-padding).toInt()
            val paint = Paint().apply {
                isAntiAlias = true
                color = if (i == activePosition) activeColor else inactiveColor
            }
            c.drawCircle(x.toFloat(), y.toFloat(), radius, paint)
        }
    }

    // Set the spacing between the dots
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.set(padding.toInt(), 0, padding.toInt(), 0)
    }
}
