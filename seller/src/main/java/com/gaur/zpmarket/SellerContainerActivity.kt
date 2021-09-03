package com.gaur.zpmarket

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.auth0.android.jwt.JWT
import com.gaur.zpmarket.local.ui.auth.registration.RegistrationViewModel
import com.gaur.zpmarket.seller.R
import com.gaur.zpmarket.seller.databinding.ActivitySellerContainerBinding
import com.gaur.zpmarket.utils.SellerConstants
import com.gaur.zpmarket.utils.Status
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class SellerContainerActivity : AppCompatActivity() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val viewModel: RegistrationViewModel by viewModels()

    val binding: ActivitySellerContainerBinding? = null
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_container)
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        val navController = findNavController(R.id.seller_container_view)
        bottomNavigationView.setupWithNavController(navController)

        val token = sharedPreferences.getString(SellerConstants.TOKEN, "").toString()
        if (token.isNotEmpty()) {
            val jwt = JWT(token)
            Log.d("TAG", "onCreate: ${jwt.isExpired(1)}")
            if (jwt.isExpired(1)) {
                viewModel.refreshToken(token)
                viewModelRefreshTokenObserver()
            }
        }
    }

    fun viewModelRefreshTokenObserver() {
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.refreshToken.collect {
                when (it.getContentIfNotHandled()?.status) {

                    Status.SUCCESS -> {
                        it.peekContent().data?.let {
                            sharedPreferences.edit()
                                .putString(SellerConstants.TOKEN, it.accessToken).apply()
                            sharedPreferences.edit()
                                .putString(SellerConstants.SELLER_REFRESH_TOKEN, it.refreshToken)
                                .apply()
                            startActivity(Intent(this@SellerContainerActivity, SellerContainerActivity::class.java))
                            finish()
                        }
                    }
                    Status.ERROR -> {
                        startActivity(Intent(this@SellerContainerActivity, SellerContainerActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}
