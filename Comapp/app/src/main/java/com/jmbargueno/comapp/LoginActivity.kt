package com.jmbargueno.comapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import com.jmbargueno.comapp.client.request.RequestLogin
import com.jmbargueno.comapp.common.Constants
import com.jmbargueno.comapp.common.MyApp
import com.jmbargueno.comapp.common.SharedPreferencesModule
import com.jmbargueno.comapp.viewmodel.AppUserViewModel
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var appUserViewModel: AppUserViewModel

    @BindView(R.id.editTextLoginUsername)
    lateinit var editTextLoginUsername: EditText

    @BindView(R.id.editTextLoginPassword)
    lateinit var editTextLoginPassword: EditText

    @BindView(R.id.buttonLogin)
    lateinit var buttonLogin: Button

    @BindView(R.id.textViewLoginRegister)
    lateinit var textViewLoginRegister: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        (applicationContext as MyApp).getAppCompontent().inject(this)
        ButterKnife.bind(this)

        val token: String? = SharedPreferencesModule().getSharedPreferences()
            .getString(Constants.SHARED_PREFERENCES_TOKEN, "")

        if (!token.isNullOrEmpty()) {
            val toMain: Intent = Intent(MyApp.instance, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(toMain)
            finish()
        }

        setListeners()
    }

    private fun setListeners() {

        buttonLogin.setOnClickListener(View.OnClickListener { v ->
            appUserViewModel.login(
                RequestLogin(
                    username = editTextLoginUsername.text.toString(),
                    password = editTextLoginPassword.text.toString()
                )
            ).observe(this, Observer {
                if (it == null) {

                } else {
                    val toMain: Intent = Intent(MyApp.instance, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(toMain)
                    finish()
                }
            })
        })

        textViewLoginRegister.setOnClickListener(View.OnClickListener {
            val signUp: Intent = Intent(MyApp.instance, SignUpActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(signUp)
            finish()
        })
    }
}