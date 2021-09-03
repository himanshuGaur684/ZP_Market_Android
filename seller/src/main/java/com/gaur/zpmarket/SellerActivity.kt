package com.gaur.zpmarket

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.gaur.zpmarket.local.ui.auth.registration.RegistrationViewModel
import com.gaur.zpmarket.seller.R
import com.gaur.zpmarket.seller.databinding.ActivitySellerBinding
import com.gaur.zpmarket.utils.SellerConstants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SellerActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    private val viewModel: RegistrationViewModel by viewModels()

    private var _binding: ActivitySellerBinding? = null
    val binding: ActivitySellerBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_seller)


        if(sharedPreferences.getString(SellerConstants.TOKEN,"").toString().isNotEmpty()){
            startActivity(Intent(this,SellerContainerActivity::class.java))
            finish()
        }

    }


}