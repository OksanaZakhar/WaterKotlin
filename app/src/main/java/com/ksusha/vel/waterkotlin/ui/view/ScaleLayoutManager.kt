package com.ksusha.vel.waterkotlin.ui.view

import android.content.Context
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import kotlin.math.abs
import kotlin.math.min

class ScaleLayoutManager(@NonNull context: Context, orientation: Int, reverseLayout: Boolean) :
    LinearLayoutManager(context, orientation, reverseLayout) {

    private val shrinkAmount = 0.04f
    private val shrinkDistance = 0.9f


    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: Recycler,
        state: RecyclerView.State
    ): Int {
        val orientation = orientation
        return if (orientation == HORIZONTAL) {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            val midpoint = width / 2f
            val d0 = 0f
            val d1 = shrinkDistance * midpoint
            val s0 = 1f
            val s1 = 1f - shrinkAmount
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                val childMidpoint = (getDecoratedRight(child!!) + getDecoratedLeft(child)) / 2f
                val d = min(d1, abs(midpoint - childMidpoint))
                val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                child.scaleY = scale
            }
            scrolled
        } else {
            0
        }
    }

}