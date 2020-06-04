package com.jmbargueno.comapp.ui.home

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jmbargueno.comapp.R
import com.jmbargueno.comapp.model.Order


/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyOrderRecyclerViewAdapter() : RecyclerView.Adapter<MyOrderRecyclerViewAdapter.ViewHolder>() {
    private var values: List<Order> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.title
        holder.comment.text = item.comment
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.textViewOrderFragmentTitle)
        val comment: TextView = view.findViewById(R.id.textViewOrderFragmentComments)
    }

    fun setData(orders: List<Order>?) {
        this.values = orders!!
        notifyDataSetChanged()
    }
}