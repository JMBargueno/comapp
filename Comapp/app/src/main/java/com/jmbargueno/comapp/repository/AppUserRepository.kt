package com.jmbargueno.comapp.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jmbargueno.comapp.client.ComappApi
import com.jmbargueno.comapp.client.request.RequestLogin
import com.jmbargueno.comapp.client.response.ResponseLogin
import com.jmbargueno.comapp.client.response.ResponseUser
import com.jmbargueno.comapp.common.Constants
import com.jmbargueno.comapp.common.MyApp
import com.jmbargueno.comapp.common.SharedPreferencesManager
import com.jmbargueno.comapp.model.AppUser
import com.jmbargueno.comapp.service.ComappService
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AppUserRepository {

    companion object{
        var instance: AppUserRepository? = null
        var service: ComappService = ComappApi.service
    }

    private val user: MutableLiveData<AppUser> = MutableLiveData<AppUser>()

    fun UserRepository() {
        service = ComappApi.service
    }

    fun login(request: RequestLogin): MutableLiveData<AppUser> {
        val call: Call<ResponseLogin> = service.login(request)
        call?.enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful) {
                    user.value = response.body()?.user

                    SharedPreferencesManager().setStringValue(
                        Constants.SHARED_PREFERENCES_TOKEN,
                        response.body()!!.token
                    )
                    Log.d(
                        "TOKEN",
                        SharedPreferencesManager().getSharedPreferences().getString(
                            Constants.SHARED_PREFERENCES_TOKEN,
                            "null"
                        )
                    )
                } else {
                    user.postValue(null)
                    SharedPreferencesManager().removeStringValue(Constants.SHARED_PREFERENCES_TOKEN)
                    Toast.makeText(
                        MyApp.instance,
                        "El usuario o contraseña no es correcto",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_SHORT).show()

            }
        })
        return user
    }


}