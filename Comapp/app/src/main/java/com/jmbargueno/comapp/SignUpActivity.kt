package com.jmbargueno.comapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import com.jmbargueno.comapp.client.request.RequestLogin
import com.jmbargueno.comapp.client.request.RequestSignUp
import com.jmbargueno.comapp.common.MyApp
import com.jmbargueno.comapp.viewmodel.AppUserViewModel
import javax.inject.Inject

class SignUpActivity : AppCompatActivity() {

    @Inject
    lateinit var appUserViewModel: AppUserViewModel

    @BindView(R.id.editTextSignUpUsername)
    lateinit var editTextSignUpUsername: EditText

    @BindView(R.id.editTextSignUpPassword)
    lateinit var editTextSignUpPassword: EditText


    @BindView(R.id.editTextSignUpCommunity)
    lateinit var editTextSignUpCommunity: EditText

    @BindView(R.id.buttonSignUp)
    lateinit var buttonSignUp: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        (applicationContext as MyApp).getAppCompontent().inject(this)
        ButterKnife.bind(this)
        setListeners()
    }

    private fun setListeners() {

        buttonSignUp.setOnClickListener(View.OnClickListener {
            appUserViewModel.signUp(
                RequestSignUp(
                    username = editTextSignUpUsername.text.toString(),
                    password = editTextSignUpPassword.text.toString(),
                    community = editTextSignUpCommunity.text.toString()

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
    }
}