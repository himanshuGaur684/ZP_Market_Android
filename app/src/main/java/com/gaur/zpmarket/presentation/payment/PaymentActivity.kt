package com.gaur.zpmarket.presentation.payment

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.coroutineScope
import com.cesarferreira.tempo.Tempo
import com.gaur.zpmarket.R
import com.gaur.zpmarket.databinding.ActivityPaymentBinding
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.remote.response_customer.order.PostOrder
import com.gaur.zpmarket.utils.CustomerConstants
import com.gaur.zpmarket.utils.makeToast
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class PaymentActivity : AppCompatActivity(), PaymentResultListener {

    lateinit var binding: ActivityPaymentBinding

    lateinit var product: Product
    private var quantity = -1
    private var address = ""
    private var mobileNumber = ""
    private var amountPaid = -1
    private val viewModel: PaymentViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)

        product = intent?.extras?.get("product") as Product
        binding.product = product

        initBinding()

        binding.fillDetailsNavigation.setNavigationOnClickListener {
            finish()
        }
        binding.paymentKaro.setOnClickListener {
            val mobileNumber = binding.orderDetailsMobileNumber.text.toString().trim()
            val address = binding.orderAddress.text.toString().trim()
            val quantity = binding.orderQuantity.text.toString().trim()
            if (mobileNumber.isEmpty() || mobileNumber.length < 10
            ) {
                this.makeToast("Please enter valid mobile number")
                return@setOnClickListener
            }
            if (address.isEmpty()) {
                this.makeToast("Please enter Current Address")
                return@setOnClickListener
            }
            if (quantity.toInt() > product.stocks) {
                this.makeToast("Only ${product.stocks} is left")
                return@setOnClickListener
            }
            this.address = address
            this.mobileNumber = mobileNumber
            this.quantity = quantity.toInt()

            generateOrderId(mobileNumber, address, this.quantity)
        }
    }

    private fun initBinding() {
        binding.orderDetailsMobileNumber.setText(
            sharedPreferences.getString(
                CustomerConstants.CUSTOMER_MOBILE_NUMBER,
                ""
            )
        )
        quantity = 1
        binding.orderQuantity.setText(quantity.toString())
    }

    private fun payment(orderId: String, mobileNumber: String, address: String, quantity: Int) {
        try {
            val checkout = Checkout()
            checkout.setKeyID("rzp_test_IDbE99OEUBbwsX")
            val options = JSONObject()
            options.put("name", "ZP Market")
            options.put("description", product.name)
            options.put("order_id", orderId)
            options.put("theme.color", "#06307A")
            options.put("currency", "INR")
            amountPaid = quantity * product.discountPrice * 100
            options.put(
                "amount",
                "$amountPaid"
            )
            options.put(
                "prefill.email",
                sharedPreferences.getString(CustomerConstants.CUSTOMER_EMAIL, "")
            )
            options.put(
                "prefill.contact",
                mobileNumber
            )
            checkout.open(this, options)
        } catch (e: Exception) {
            Log.d("TAG", "buyProduct: ${e.message}")
        }
    }

    override fun onPaymentSuccess(transactionID: String?) {
        viewModel.postOrder(
            PostOrder(
                address = address,
                cashOnDelivery = product.cashOnDelivery,
                productId = product._id,
                time = Tempo.now.toString(),
                transactionID = transactionID.toString(),
                amountPaid = amountPaid,
                customerId = sharedPreferences.getString(CustomerConstants.CUSTOMER_ID, "")
                    .toString(),
                productQuantity = quantity,
                mobileNumber = binding.orderDetailsMobileNumber.text.toString().trim()
            )
        )

        paymentObserver()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Log.d("TAG", "onPaymentError: $p1")
        this.makeToast("Something went wrong")
    }

    private fun paymentObserver() {
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.postOrderId.collect {
                if (it.isLoading) {
                }
                if (it.error.isNotBlank()) {
                    this@PaymentActivity.makeToast("Error Occurred")
                }
                it.data?.let {
                    Handler(Looper.myLooper()!!).postDelayed(
                        {
                            this@PaymentActivity.makeToast("Payment Successful")
                            finish()
                        },
                        2000
                    )
                }
            }
        }
    }

    private fun generateOrderId(mobileNumber: String, address: String, quantity: Int) {
        amountPaid = quantity * product.discountPrice * 100
        viewModel.getOrderId(amountPaid)

        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.paymentOrderId.collect {

                if (it.isLoading) {
                    binding.paymentKaro.visibility = View.GONE
                }
                if (it.error.isNotBlank()) {
                    binding.paymentKaro.visibility = View.VISIBLE
                }

                it.data?.let {
                    binding.paymentKaro.visibility = View.VISIBLE
                    payment(it.id, mobileNumber, address, quantity)
                }

            }
        }
    }
}
