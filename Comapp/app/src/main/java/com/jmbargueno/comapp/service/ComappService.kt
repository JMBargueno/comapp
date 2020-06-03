package com.jmbargueno.comapp.service

import com.jmbargueno.comapp.client.request.RequestLogin
import com.jmbargueno.comapp.client.request.RequestSignUp
import com.jmbargueno.comapp.client.response.ResponseCommunity
import com.jmbargueno.comapp.client.response.ResponseLogin
import com.jmbargueno.comapp.client.response.ResponseSignUp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ComappService {

    @POST("/auth/login")
    fun login(@Body request: RequestLogin): Call<ResponseLogin>

    @POST("/signup")
    fun login(@Body request: RequestSignUp): Call<ResponseSignUp>

    @GET("/user/me")
    fun getMe(): Call<ResponseLogin>

    @GET("/community/{id}")
    fun getCommunityById(@Path("id") id: Long): Call<ResponseCommunity>

}