package com.evapharma.limitless.presentation.util
import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

class CategoryHomeGrid(context:Context, spanCount:Int): GridLayoutManager(context, spanCount){
    override fun canScrollVertically(): Boolean {
        return false
    }
}