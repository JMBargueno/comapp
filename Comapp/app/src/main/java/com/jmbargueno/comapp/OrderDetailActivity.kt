package com.jmbargueno.comapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import com.jmbargueno.comapp.common.Constants
import com.jmbargueno.comapp.common.MyApp
import com.jmbargueno.comapp.common.SharedPreferencesModule
import com.jmbargueno.comapp.model.Order
import com.jmbargueno.comapp.viewmodel.OrderViewModel
import javax.inject.Inject

class OrderDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var orderViewModel: OrderViewModel

    @BindView(R.id.textViewOrderDetailTitle)
    lateinit var textViewOrderDetailTitle: TextView

    @BindView(R.id.textViewOrderDetailComment)
    lateinit var textViewOrderDetailComment: TextView

    @BindView(R.id.textViewOrderDetailCreator)
    lateinit var textViewOrderDetailCreator: TextView

    @BindView(R.id.textViewOrderDetailMadeBy)
    lateinit var textViewOrderDetailMadeBy: TextView

    @BindView(R.id.imageViewOrderDetailDelete)
    lateinit var imageViewOrderDetailDelete: ImageView

    @BindView(R.id.buttonAssing)
    lateinit var buttonAssing: Button

    lateinit var id: String
    lateinit var order: Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        (applicationContext as MyApp).getAppCompontent().inject(this)
        ButterKnife.bind(this)
        id = intent.getStringExtra("id")

        orderViewModel.getOrderById(id).observe(this, Observer {
            if (it != null) {
                order = it
                textViewOrderDetailTitle.text = order.title
                textViewOrderDetailComment.text = order.comment
                textViewOrderDetailCreator.text = order.creator.username
                if (order.finished) {
                    textViewOrderDetailMadeBy.text = order.madeBy.username
                    buttonAssing.visibility = View.INVISIBLE
                } else {
                    textViewOrderDetailMadeBy.text = "Not assigned"
                    buttonAssing.visibility = View.VISIBLE
                }

                if (order.creator.id.toString() == SharedPreferencesModule().getStringValue(
                        Constants.SHARED_PREFERENCES_USER_ID
                    ) && !order.finished
                ) {

                    imageViewOrderDetailDelete.visibility = View.VISIBLE
                } else {
                    imageViewOrderDetailDelete.visibility = View.INVISIBLE
                }

            }
        })
        setListeners()

    }

    private fun setListeners() {

        buttonAssing.setOnClickListener(View.OnClickListener {
            orderViewModel.assingOrder(order.id.toString()).observe(this, Observer {
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

        imageViewOrderDetailDelete.setOnClickListener(View.OnClickListener {

            orderViewModel.deleteOrder(order.id.toString())

            val toMain: Intent = Intent(MyApp.instance, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(toMain)
            finish()

        })

    }


    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}