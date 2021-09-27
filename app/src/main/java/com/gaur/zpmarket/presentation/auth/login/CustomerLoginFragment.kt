package com.gaur.zpmarket.presentation.auth.login

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
import com.gaur.zpmarket.ContainerActivity
import com.gaur.zpmarket.SellerActivity
import com.gaur.zpmarket.databinding.FragmentCustomerLoginBinding
import com.gaur.zpmarket.presentation.auth.CustomerAuthUtils
import com.gaur.zpmarket.presentation.auth.CustomerAuthViewModel
import com.gaur.zpmarket.remote.response_customer.auth.login.CustomerLoginDTO
import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegistrationResponse
import com.gaur.zpmarket.utils.CustomerConstants
import com.gaur.zpmarket.utils.hideKeyboard
import com.gaur.zpmarket.utils.makeToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class CustomerLoginFragment : Fragment() {

    private var _binding: FragmentCustomerLoginBinding? = null
    val binding: FragmentCustomerLoginBinding
        get() = _binding!!

    private val viewModel: CustomerAuthViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerLoginBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.viewModel = viewModel

        binding.customerGoToRegisterPage.setOnClickListener {
            findNavController().navigate(CustomerLoginFragmentDirections.actionCustomerLoginFragmentToCustomerRegistrationFragment())
        }

        binding.sellerLogin.setOnClickListener {
            startActivity(Intent(requireContext(), SellerActivity::class.java))
            requireActivity().finish()
        }

        binding.customerLoginButton.setOnClickListener {
            binding.root.hideKeyboard()
            val v = CustomerAuthUtils.customerLoginValidation(
                viewModel.customerLoginObservables.email,
                viewModel.customerLoginObservables.password
            )
            if (v.isEmpty()) {
                val body = CustomerLoginDTO(
                    viewModel.customerLoginObservables.email,
                    viewModel.customerLoginObservables.password
                )
                viewModel.login(body)
            } else {
                requireContext().makeToast(v)
            }
        }

        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.login.collect {

                if (it.error.isNotBlank()) {
                    binding.customerLoginProgress.visibility = View.GONE
                    binding.customerLoginButton.visibility = View.VISIBLE
                }
                if (it.isLoading) {
                    binding.customerLoginProgress.visibility = View.VISIBLE
                    binding.customerLoginButton.visibility = View.GONE
                }
                it.data?.let {
                    binding.customerLoginProgress.visibility = View.GONE
                    binding.customerLoginButton.visibility = View.VISIBLE
                    updateSharedPreferences(it)
                    startActivity(Intent(requireContext(), ContainerActivity::class.java))
                    requireActivity().finish()
                }

            }
        }
    }

    private fun updateSharedPreferences(it: CustomerRegistrationResponse) {
        sharedPreferences.edit()?.apply {
            putString(CustomerConstants.CUSTOMER_ADDRESS, it.custumer.address).apply()
            putString(CustomerConstants.CUSTOMER_AUTH_TOKEN, it.token).apply()
            putString(CustomerConstants.CUSTOMER_EMAIL, it.custumer.email).apply()
            putString(CustomerConstants.CUSTOMER_ID, it.custumer._id).apply()
            putString(CustomerConstants.CUSTOMER_MOBILE_NUMBER, it.custumer.mobileNumber).apply()
            putString(CustomerConstants.CUSTOMER_PIN_CODE, it.custumer.pincode).apply()
            putString(CustomerConstants.CUSTOMER_REFRESH_TOKEN, it.refreshToken).apply()
        }
    }
}
