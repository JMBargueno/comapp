package com.jmbargueno.comapp.repository

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jmbargueno.comapp.MainActivity
import com.jmbargueno.comapp.client.request.RequestLogin
import com.jmbargueno.comapp.client.request.RequestSignUp
import com.jmbargueno.comapp.client.response.APIError
import com.jmbargueno.comapp.client.response.ResponseLogin
import com.jmbargueno.comapp.client.response.ResponseSignUp
import com.jmbargueno.comapp.common.Constants
import com.jmbargueno.comapp.common.MyApp
import com.jmbargueno.comapp.common.SharedPreferencesModule
import com.jmbargueno.comapp.model.AppUser
import com.jmbargueno.comapp.service.ComappService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppUserRepository @Inject constructor(
    private var service: ComappService,
    private var retrofit: Retrofit
) {


    private val user: MutableLiveData<AppUser> = MutableLiveData<AppUser>()


    fun login(request: RequestLogin): MutableLiveData<AppUser> {
        val call: Call<ResponseLogin> = service.login(request)
        call?.enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful) {
                    user.value = response.body()?.user

                    SharedPreferencesModule().setStringValue(
                        Constants.SHARED_PREFERENCES_TOKEN,
                        response.body()!!.token
                    )
                    SharedPreferencesModule().setStringValue(
                        Constants.SHARED_PREFERENCES_USER_ID,
                        response.body()!!.user.id.toString()
                    )
                    Log.d(
                        "TOKEN",
                        SharedPreferencesModule().getSharedPreferences().getString(
                            Constants.SHARED_PREFERENCES_TOKEN,
                            "null"
                        )
                    )
                } else {
                    user.postValue(null)
                    SharedPreferencesModule().removeStringValue(Constants.SHARED_PREFERENCES_TOKEN)
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

    fun parseError(response: Response<*>): APIError {
        val jsonObject = JSONObject(response.errorBody()!!.string())
        return APIError(jsonObject.getInt("status_code"), jsonObject.getString("status_message"))
    }

    fun signUp(request: RequestSignUp): MutableLiveData<AppUser> {
        val call: Call<ResponseSignUp> = service.signUp(request)
        call?.enqueue(object : Callback<ResponseSignUp> {
            override fun onFailure(call: Call<ResponseSignUp>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ResponseSignUp>,
                response: Response<ResponseSignUp>
            ) {
                if (response.isSuccessful) {
                    login(RequestLogin(username = request.username, password = request.password))
                    Log.d(
                        "TOKEN",
                        SharedPreferencesModule().getSharedPreferences().getString(
                            Constants.SHARED_PREFERENCES_TOKEN,
                            "null"
                        )
                    )
                } else {
                    user.postValue(null)
                    SharedPreferencesModule().removeStringValue(Constants.SHARED_PREFERENCES_TOKEN)
                    Toast.makeText(
                        MyApp.instance,
                        "El usuario o contraseña no es correcto",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        })
        return user
    }
}