package com.teamfillin.fillin.presentation.map

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*

class SpaceDecoration(private val margin: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: State
    ) {
        with(outRect) {
            right = margin
            bottom = margin
        }
    }
}