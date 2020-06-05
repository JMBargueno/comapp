package com.jmbargueno.comapp.client

import com.jmbargueno.comapp.common.Constants
import com.jmbargueno.comapp.service.ComappService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    @Named("url")
    fun provideBaseUrl(): String = Constants.BASE_URL



    @Singleton
    @Provides
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        return with(OkHttpClient.Builder()) {
            addInterceptor(tokenInterceptor)
            connectTimeout(Constants.TIMEOUT_INMILIS, TimeUnit.MILLISECONDS)
            build()
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(@Named("url") baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        var logging : HttpLoggingInterceptor = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(logging).build())
            .build()
    }

    @Singleton
    @Provides
    fun provideTheMovieDBRetrofitService(retrofit: Retrofit): ComappService {
        return retrofit.create(ComappService::class.java)
    }
}