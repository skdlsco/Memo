package com.example.eka.memo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.list_text.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by eka on 2017. 7. 31..
 */
class MyAdapter(private val context: Context, private var items: ArrayList<item>) : RecyclerView.Adapter<MyAdapter.ViewHolder>(), MyItemTouchHelperCallBack.MyItemTouchHelperAdapter {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.bind(items.get(position))
        holder.itemView.setOnClickListener {
            itemClick!!.onItemClick(holder.itemView, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder
            = ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_text, null))

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            var params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            itemView.layoutParams = params
        }
        fun bind(item: item) {
            itemView.textContent.text = item.content
            itemView.textTime.text = timeToString(item.date)
        }

        fun timeToString(date: Date): String {
            val simpleDataFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
            val string: String = simpleDataFormat.format(date)
            return string
        }
    }

    override fun onItemSwiped(position: Int, rv: RecyclerView) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    var itemClick: ItemClick? = null

    interface ItemClick {
        fun onItemClick(view: View?, position: Int)
    }
}