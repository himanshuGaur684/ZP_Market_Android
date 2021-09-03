package com.gaur.zpmarket

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.gaur.zpmarket.utils.CustomerConstants
import com.gaur.zpmarket.utils.SellerConstants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (window.insetsController != null) {
                val insetsController = window.insetsController
                if (insetsController != null) {
                    insetsController.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                    insetsController.systemBarsBehavior =
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            }
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        Handler(Looper.myLooper()!!).postDelayed(
            {
                /** check if customer token is not empty **/
                if (sharedPreferences.getString(CustomerConstants.CUSTOMER_AUTH_TOKEN, "").toString()
                    .isNotEmpty()
                ) {
                    startActivity(Intent(this, ContainerActivity::class.java))
                    finish()
                }
                /**  Check if seller token is not empty  **/
                else if (sharedPreferences.getString(SellerConstants.TOKEN, "").toString()
                    .isNotEmpty()
                ) {
                    startActivity(Intent(this, SellerContainerActivity::class.java))
                    finish()
                }
                /**  check if customer token is empty then jump onto customerAuthActivity  **/
                else if (sharedPreferences.getString(CustomerConstants.CUSTOMER_AUTH_TOKEN, "")
                    .toString().isEmpty()
                ) {
                    startActivity(Intent(this, CustomerAuthActivity::class.java))
                    finish()
                }
            },
            1200
        )
    }
}
