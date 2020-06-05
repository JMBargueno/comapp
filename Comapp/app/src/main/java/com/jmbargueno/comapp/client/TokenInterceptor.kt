package com.jmbargueno.comapp.client

import android.util.Log
import com.jmbargueno.comapp.common.Constants
import com.jmbargueno.comapp.common.SharedPreferencesModule
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()

        val token = SharedPreferencesModule().getStringValue(Constants.SHARED_PREFERENCES_TOKEN)

        request = request.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }

}