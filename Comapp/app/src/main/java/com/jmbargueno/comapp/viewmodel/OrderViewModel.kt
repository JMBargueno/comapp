package com.jmbargueno.comapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jmbargueno.comapp.client.request.RequestNewOrder
import com.jmbargueno.comapp.client.response.ResponseCommunity
import com.jmbargueno.comapp.client.response.ResponseOrder
import com.jmbargueno.comapp.model.Order
import com.jmbargueno.comapp.repository.CommunityRepository
import com.jmbargueno.comapp.repository.OrderRepository
import javax.inject.Inject

class OrderViewModel @Inject constructor(orderRepository: OrderRepository) :
    ViewModel() {

    val respository = orderRepository

    fun getMyOrders(): LiveData<ResponseOrder> {
        return respository.getMyOrders()
    }

    fun newOrder(request: RequestNewOrder): LiveData<Order>{
        return respository.newOrder(request)
    }
}