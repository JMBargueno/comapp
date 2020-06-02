package com.jmbargueno.comapp.client

import com.jmbargueno.comapp.common.Constants
import com.jmbargueno.comapp.common.SharedPreferencesManager
import com.jmbargueno.comapp.service.ComappService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ComappApi {
    companion object {
        var instance: ComappApi? = null
        lateinit var service: ComappService
        var retrofit: Retrofit? = null
        var loggingInterceptor: HttpLoggingInterceptor? = null
        var httpClient: OkHttpClient.Builder? = null
    }

    fun createClient(): ComappService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).addInterceptor(TokenInterceptor()).build())
            .build()
            .create(ComappService::class.java)
    }

}


