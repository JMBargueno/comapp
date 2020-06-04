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
import com.jmbargueno.comapp.client.request.RequestNewOrder
import com.jmbargueno.comapp.common.MyApp
import com.jmbargueno.comapp.viewmodel.AppUserViewModel
import com.jmbargueno.comapp.viewmodel.OrderViewModel
import javax.inject.Inject

class AddOrderActivity : AppCompatActivity() {

    @Inject
    lateinit var orderViewModel: OrderViewModel

    @BindView(R.id.editTextAddOrderTitle)
    lateinit var editTextAddOrderTitle: EditText

    @BindView(R.id.editTextMultiLineAddOrderComment)
    lateinit var editTextMultiLineAddOrderComment: EditText

    @BindView(R.id.buttonAddOrderAdd)
    lateinit var buttonAddOrderAdd: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addorder)
        (applicationContext as MyApp).getAppCompontent().inject(this)
        ButterKnife.bind(this)
        setListeners()
    }

    private fun setListeners() {

        buttonAddOrderAdd.setOnClickListener(View.OnClickListener {
            orderViewModel.newOrder(
                RequestNewOrder(
                    title = editTextAddOrderTitle.text.toString(),
                    comment = editTextMultiLineAddOrderComment.text.toString()
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