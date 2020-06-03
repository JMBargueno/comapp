package com.jmbargueno.comapp.common

import android.app.Application
import android.content.Context

class MyApp : Application() {
    companion object {
        lateinit var instance: MyApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}