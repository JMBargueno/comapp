package com.jmbargueno.comapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import butterknife.ButterKnife
import com.jmbargueno.comapp.common.MyApp
import com.jmbargueno.comapp.viewmodel.AppUserViewModel
import javax.inject.Inject

class SignUpActivity : AppCompatActivity() {

    @Inject
    lateinit var appUserViewModel: AppUserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        (applicationContext as MyApp).getAppCompontent().inject(this)
        ButterKnife.bind(this)
    }
}