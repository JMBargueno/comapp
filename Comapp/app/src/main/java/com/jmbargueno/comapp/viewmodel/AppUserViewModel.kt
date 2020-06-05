package com.jmbargueno.comapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jmbargueno.comapp.client.request.RequestLogin
import com.jmbargueno.comapp.client.request.RequestSignUp
import com.jmbargueno.comapp.common.Constants
import com.jmbargueno.comapp.common.SharedPreferencesModule
import com.jmbargueno.comapp.model.AppUser
import com.jmbargueno.comapp.repository.AppUserRepository
import javax.inject.Inject

class AppUserViewModel @Inject constructor(appUserRepository: AppUserRepository) : ViewModel() {
    val repository = appUserRepository

    fun login(request: RequestLogin): LiveData<AppUser> {
        return repository.login(request)
    }
    fun logOut(): Boolean {
        SharedPreferencesModule().removeStringValue(Constants.SHARED_PREFERENCES_TOKEN)
        return true
    }

    fun signUp(request: RequestSignUp): LiveData<AppUser>{
        return repository.signUp(request)
    }

}