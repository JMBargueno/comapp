package com.jmbargueno.comapp.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.jmbargueno.comapp.client.request.RequestNewOrder
import com.jmbargueno.comapp.client.response.ResponseCommunity
import com.jmbargueno.comapp.client.response.ResponseOrder
import com.jmbargueno.comapp.common.Constants
import com.jmbargueno.comapp.common.MyApp
import com.jmbargueno.comapp.common.SharedPreferencesModule
import com.jmbargueno.comapp.model.Order
import com.jmbargueno.comapp.service.ComappService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(
    private var service: ComappService,
    private var retrofit: Retrofit
) {
    private val orders: MutableLiveData<ResponseOrder> = MutableLiveData<ResponseOrder>()
    private val lastOrder: MutableLiveData<Order> = MutableLiveData<Order>()

    fun getMyOrders(): MutableLiveData<ResponseOrder> {
        val call: Call<ResponseOrder> = service.getMyOrders()
        call.enqueue(object : Callback<ResponseOrder> {
            override fun onFailure(call: Call<ResponseOrder>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseOrder>, response: Response<ResponseOrder>) {
                if (response.isSuccessful) {
                    orders.value = response.body()
                } else {

                }
            }
        })
        return orders
    }

    fun newOrder(request: RequestNewOrder): MutableLiveData<Order> {
        val call: Call<Order> = service.newOrder(
            SharedPreferencesModule().getStringValue(Constants.SHARED_PREFERENCES_COMMUNITY) as String,
            request
        )

        call.enqueue(object : Callback<Order> {
            override fun onFailure(call: Call<Order>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    lastOrder.value = response.body()
                } else {

                }
            }
        })
        return lastOrder
    }

    fun getOrderById(id: String): MutableLiveData<Order> {
        val call: Call<Order> = service.getOrderById(id)
        call.enqueue(object : Callback<Order> {
            override fun onFailure(call: Call<Order>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                lastOrder.value = response.body()
            }

        })
        return lastOrder
    }

    fun assingOrder(id: String): MutableLiveData<Order> {
        val call: Call<Order> = service.assingOrder(id)
        call.enqueue(object : Callback<Order> {
            override fun onFailure(call: Call<Order>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                lastOrder.value = response.body()
            }

        })
        return lastOrder
    }

    fun deleteOrder(id: String): Boolean {
        var community = SharedPreferencesModule().getStringValue(Constants.SHARED_PREFERENCES_COMMUNITY)
        Log.d("COMMUNITY", community)
        Log.d("ORDER", id)
        val call: Call<Void> = service.deleteOrder(community!!, id)
        call.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {

            }

        })
        return true
    }

    fun getMyOrdersHistoric(): MutableLiveData<ResponseOrder> {
        val call: Call<ResponseOrder> = service.getMyOrdersHistoric()
        call.enqueue(object : Callback<ResponseOrder> {
            override fun onFailure(call: Call<ResponseOrder>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseOrder>, response: Response<ResponseOrder>) {
                if (response.isSuccessful) {
                    orders.value = response.body()
                } else {

                }
            }


        })
        return orders
    }



}