package com.jmbargueno.comapp.ui.historic

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
import com.jmbargueno.comapp.viewmodel.OrderViewModel
import javax.inject.Inject


/**
 * A fragment representing a list of Items.
 */
class OrderHistoricFragment : Fragment() {
    @Inject
    lateinit var orderViewModel: OrderViewModel
    private lateinit var myOrderHistoricAdapter: MyOrderHistoricRecyclerViewAdapter
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
        val view = inflater.inflate(R.layout.fragment_order_historic_list, container, false)
        myOrderHistoricAdapter = MyOrderHistoricRecyclerViewAdapter()
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = myOrderHistoricAdapter
            }
        }

        orderViewModel.getMyOrdersHistoric().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                orders = it
                myOrderHistoricAdapter.setData(orders)
            }
        })
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            OrderHistoricFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}