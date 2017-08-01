package com.example.eka.memo

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

/**
 * Created by eka on 2017. 8. 1..
 */
class MyItemTouchHelperCallBack(val rv: RecyclerView, val adapter: MyItemTouchHelperAdapter) : ItemTouchHelper.Callback() {

    interface MyItemTouchHelperAdapter {
        fun onItemSwiped(position: Int, rv: RecyclerView)
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        if (viewHolder == null)
            return
        adapter.onItemSwiped(viewHolder.adapterPosition, rv)
    }
}