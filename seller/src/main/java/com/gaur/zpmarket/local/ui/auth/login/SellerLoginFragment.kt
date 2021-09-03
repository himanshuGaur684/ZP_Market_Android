package com.gaur.zpmarket.local.ui.auth.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.gaur.zpmarket.SellerContainerActivity
import com.gaur.zpmarket.local.ui.auth.AuthUtils
import com.gaur.zpmarket.remote.response_seller.login.SellerLoginObject
import com.gaur.zpmarket.remote.response_seller.register.SellerRegistrationResponseObject
import com.gaur.zpmarket.seller.R
import com.gaur.zpmarket.seller.databinding.FragmentSellerLoginBinding
import com.gaur.zpmarket.utils.SellerConstants
import com.gaur.zpmarket.utils.Status
import com.gaur.zpmarket.utils.makeSnack
import com.gaur.zpmarket.utils.makeToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class SellerLoginFragment : Fragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    lateinit var binding: FragmentSellerLoginBinding

    private val viewModel: SellerLoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSellerLoginBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModelsObservables()

        binding.sellerGoToRegisterPage.setOnClickListener {
            findNavController().navigate(R.id.sellerRegistrationFragment)
        }

        binding.sellerLoginButton.setOnClickListener {
            viewModel.loginObservables.apply {
                val validator = AuthUtils.sellerLoginValidator(email, password)
                if (validator.isEmpty()) {
                    viewModel.sellerLogin(SellerLoginObject(email, password))
                } else {
                    requireContext().makeToast(validator)
                }
            }

        }

    }

    private fun viewModelsObservables() {
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.login.collect {
                when (it.getContentIfNotHandled()?.status) {
                    Status.LOADING -> {
                        binding.sellerLoginProgress.visibility = View.VISIBLE
                        binding.sellerLoginButton.visibility = View.GONE
                    }
                    Status.SUCCESS -> {
                        binding.sellerLoginProgress.visibility = View.GONE
                        binding.sellerLoginButton.visibility = View.VISIBLE
                        it.peekContent().data?.let {
                            updateSharedPreferences(it)
                        }
                        requireContext().makeToast("Login Successfully")
                    }
                    Status.ERROR -> {
                        binding.sellerLoginProgress.visibility = View.GONE
                        binding.sellerLoginButton.visibility = View.VISIBLE
                        it.peekContent().message?.let {
                            if (it.isNotEmpty()) binding.root.makeSnack(it)
                        }
                    }
                }
            }
        }

    }


    private fun updateSharedPreferences(sellerRegistrationResponseObject: SellerRegistrationResponseObject) {
        sharedPreferences.edit().apply {
            putString(SellerConstants.EMAIL, sellerRegistrationResponseObject.seller.email).apply()
            putString(
                SellerConstants.SELLER_MOBILE_NUMBER,
                sellerRegistrationResponseObject.seller.mobileNumber
            ).apply()
            putString(
                SellerConstants.SELLER_NAME,
                sellerRegistrationResponseObject.seller.name
            ).apply()
            putString(
                SellerConstants.SELLER_ID,
                sellerRegistrationResponseObject.seller._id
            ).apply()
            putString(SellerConstants.TOKEN, sellerRegistrationResponseObject.token).apply()
            putString(
                SellerConstants.SELLER_REFRESH_TOKEN,
                sellerRegistrationResponseObject.refreshToken
            ).apply()

        }.apply()

        startActivity(Intent(requireContext(), SellerContainerActivity::class.java))
        requireActivity().finish()
    }


}