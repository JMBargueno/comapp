package com.jmbargueno.comapp.common

import com.jmbargueno.comapp.LoginActivity
import com.jmbargueno.comapp.client.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = [NetworkModule::class, SharedPreferencesModule::class])
interface ApplicationComponent {
    fun inject(login: LoginActivity)
}