package com.jmbargueno.comapp.ui.home

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jmbargueno.comapp.OrderDetailActivity
import com.jmbargueno.comapp.R
import com.jmbargueno.comapp.common.MyApp
import com.jmbargueno.comapp.model.Order


/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyOrderRecyclerViewAdapter() : RecyclerView.Adapter<MyOrderRecyclerViewAdapter.ViewHolder>() {
    private var values: List<Order> = ArrayList()
    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Order
            var detail: Intent = Intent(MyApp.instance, OrderDetailActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            MyApp.instance.startActivity(detail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.title
        holder.comment.text = item.comment
        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mView: View = view
        val title: TextView = view.findViewById(R.id.textViewOrderFragmentTitle)
        val comment: TextView = view.findViewById(R.id.textViewOrderFragmentComments)
    }

    fun setData(orders: List<Order>?) {
        this.values = orders!!
        notifyDataSetChanged()
    }
}