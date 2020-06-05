package com.jmbargueno.comapp.common

import com.jmbargueno.comapp.*
import com.jmbargueno.comapp.client.NetworkModule
import com.jmbargueno.comapp.ui.historic.OrderHistoricFragment
import com.jmbargueno.comapp.ui.home.OrderFragment
import com.jmbargueno.comapp.ui.myorders.MyOrdersFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = [NetworkModule::class, SharedPreferencesModule::class])
interface ApplicationComponent {
    fun inject(login: LoginActivity)
    fun inject (main: MainActivity)
    fun inject (signUp: SignUpActivity)
    fun inject (addOrder: AddOrderActivity)
    fun inject(orderDetailActivity: OrderDetailActivity)
    fun inject (orderFragment: OrderFragment)
    fun inject (myOrdersFragment: MyOrdersFragment)
    fun inject (historicFragment: OrderHistoricFragment)
}