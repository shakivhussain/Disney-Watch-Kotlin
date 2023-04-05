package com.shakiv.husain.disneywatch.util

import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller


class CustomLinearLayoutManager(context: Context?, orientation: Int, reverseLayout: Boolean) :
    LinearLayoutManager(context, orientation, reverseLayout) {
    override fun smoothScrollToPosition(
        recyclerView: RecyclerView, state: RecyclerView.State, position: Int
    ) {
        val smoothScroller: SmoothScroller = object : LinearSmoothScroller(recyclerView.context) {
            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return 10f / displayMetrics.densityDpi
            }
        }
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }
}
