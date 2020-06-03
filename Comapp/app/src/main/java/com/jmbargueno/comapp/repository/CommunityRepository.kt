package com.jmbargueno.comapp.repository

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.jmbargueno.comapp.client.ComappApi
import com.jmbargueno.comapp.client.response.ResponseCommunity
import com.jmbargueno.comapp.common.MyApp
import com.jmbargueno.comapp.model.Order
import com.jmbargueno.comapp.service.ComappService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityRepository {
    companion object {
        var instance: CommunityRepository? = null
        var service: ComappService = ComappApi.service
    }

    private val community: MutableLiveData<ResponseCommunity> = MutableLiveData<ResponseCommunity>()
    private val orders: MutableLiveData<List<Order>> = MutableLiveData<List<Order>>()

    fun getCommunityById(id: Long): MutableLiveData<ResponseCommunity> {
        val call: Call<ResponseCommunity> = service.getCommunityById(id)
        call.enqueue(object : Callback<ResponseCommunity> {

            override fun onResponse(
                call: Call<ResponseCommunity>,
                response: Response<ResponseCommunity>
            ) {
                if (response.isSuccessful) {
                    community.value = response.body()
                    orders.value = response.body()?.orders
                } else {

                }
            }

            override fun onFailure(call: Call<ResponseCommunity>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_SHORT).show()
            }

        })
        return community
    }
}