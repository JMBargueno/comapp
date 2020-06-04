package com.jmbargueno.comapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.jmbargueno.comapp.R
import com.jmbargueno.comapp.common.MyApp
import com.jmbargueno.comapp.model.Order
import com.jmbargueno.comapp.viewmodel.CommunityViewModel

import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */
class OrderFragment : Fragment() {
    @Inject
    lateinit var communityViewModel: CommunityViewModel
    private lateinit var communityOrdersAdapter: MyOrderRecyclerViewAdapter
    private var orders: List<Order> = ArrayList()


    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).getAppCompontent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_list, container, false)
        communityOrdersAdapter =
            MyOrderRecyclerViewAdapter()
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = communityOrdersAdapter
            }
        }

        communityViewModel.getMyCommunity().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                orders = it.orders
                communityOrdersAdapter.setData(orders)
            }
        })
        return view
    }
}


