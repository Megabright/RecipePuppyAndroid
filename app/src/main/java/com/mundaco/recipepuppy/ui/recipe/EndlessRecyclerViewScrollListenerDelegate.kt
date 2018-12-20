package com.mundaco.recipepuppy.ui.recipe

import android.support.v7.widget.RecyclerView

interface EndlessRecyclerViewScrollListenerDelegate {
    fun onEndOfPageReached(page: Int, totalItemsCount: Int, view: RecyclerView)
}