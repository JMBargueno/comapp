package com.jmbargueno.comapp.common

import android.app.Application
import android.content.Context

class MyApp : Application() {
    //TODO definimos el @Component para tener acceso a las dependencias en la aplicación
    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()

    companion object {
        lateinit var instance: MyApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    fun getAppCompontent(): ApplicationComponent{
        return appComponent
    }
}