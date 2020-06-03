package com.jmbargueno.comapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jmbargueno.comapp.client.request.RequestLogin
import com.jmbargueno.comapp.model.AppUser
import com.jmbargueno.comapp.repository.AppUserRepository

class AppUserViewModel {
    companion object {
        val repository = AppUserRepository.instance
    }

    fun doLogin(request: RequestLogin): MutableLiveData<AppUser>? {
        return repository?.login(request)
    }
}