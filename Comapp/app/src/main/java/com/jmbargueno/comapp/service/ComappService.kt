package com.jmbargueno.comapp.service

import com.jmbargueno.comapp.client.request.RequestLogin
import com.jmbargueno.comapp.client.request.RequestNewOrder
import com.jmbargueno.comapp.client.request.RequestSignUp
import com.jmbargueno.comapp.client.response.ResponseCommunity
import com.jmbargueno.comapp.client.response.ResponseLogin
import com.jmbargueno.comapp.client.response.ResponseOrder
import com.jmbargueno.comapp.client.response.ResponseSignUp
import com.jmbargueno.comapp.model.Order
import retrofit2.Call
import retrofit2.http.*

interface ComappService {

    @POST("/auth/login")
    fun login(@Body request: RequestLogin): Call<ResponseLogin>

    @POST("/signup")
    fun signUp(@Body request: RequestSignUp): Call<ResponseSignUp>

    @GET("/user/me")
    fun getMe(): Call<ResponseLogin>

    @GET("/community/{id}")
    fun getCommunityById(@Path("id") id: Long): Call<ResponseCommunity>

    @GET("/community/mycommunity")
    fun getMyCommunity(): Call<ResponseCommunity>

    @GET("/order/myorders")
    fun getMyOrders(): Call<ResponseOrder>

    @GET("/order/myorders/historic")
    fun getMyOrdersHistoric(): Call<ResponseOrder>

    @POST("/order/{id}")
    fun newOrder(@Path("id") id: String, @Body request: RequestNewOrder): Call<Order>

    @DELETE("/order/{community}/{order}")
    fun deleteOrder(@Path("community") community: String, @Path("order") order: String): Call<Void>

    @GET("/order/{id}")
    fun getOrderById(@Path("id") id: String): Call<Order>

    @PUT("/order/{id}")
    fun assingOrder(@Path("id") id: String): Call<Order>

}