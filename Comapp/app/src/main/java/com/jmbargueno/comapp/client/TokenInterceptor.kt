package com.jmbargueno.comapp.client

import com.jmbargueno.comapp.common.Constants
import com.jmbargueno.comapp.common.SharedPreferencesManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.jetbrains.annotations.NotNull
import java.io.IOException

class TokenInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val request: Request
        val token = SharedPreferencesManager().getSharedPreferences()
            .getString(Constants.APP_SETTINGS_FILE, "")

        val requestBuilder: Request.Builder =
            original.newBuilder().header("Authorization", "Bearer $token")
        request = requestBuilder.build()


        return chain.proceed(request)
    }
}