package com.jmbargueno.comapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import butterknife.ButterKnife
import com.jmbargueno.comapp.common.MyApp
import com.jmbargueno.comapp.viewmodel.AppUserViewModel
import com.jmbargueno.comapp.viewmodel.OrderViewModel
import javax.inject.Inject

class OrderDetailActivity : AppCompatActivity() {
    @Inject
    lateinit var orderViewModel: OrderViewModel

    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        (applicationContext as MyApp).getAppCompontent().inject(this)
        ButterKnife.bind(this)
        id = intent.getStringExtra("id")
    }
}