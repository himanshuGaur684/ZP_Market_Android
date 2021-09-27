package com.gaur.zpmarket.presentation.auth.register

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.gaur.zpmarket.ContainerActivity
import com.gaur.zpmarket.databinding.FragmentCustomerRegistrationBinding
import com.gaur.zpmarket.presentation.auth.CustomerAuthUtils
import com.gaur.zpmarket.presentation.auth.CustomerAuthViewModel
import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegisterDTO
import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegistrationResponse
import com.gaur.zpmarket.utils.CustomerConstants
import com.gaur.zpmarket.utils.hideKeyboard
import com.gaur.zpmarket.utils.makeToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class CustomerRegistrationFragment : Fragment() {

    private var _binding: FragmentCustomerRegistrationBinding? = null
    val binding: FragmentCustomerRegistrationBinding
        get() = _binding!!

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val viewModel: CustomerAuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerRegistrationBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel

        binding.customerRegisterButton.setOnClickListener {
            binding.root.hideKeyboard()
            customerRegistrationValidation()
        }

        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.registration.collect {
                if (it.isLoading) {
                    binding.customerProgress.visibility = View.VISIBLE
                    binding.customerRegisterButton.visibility = View.GONE
                }
                if (it.error.isNotBlank()) {
                    binding.customerProgress.visibility = View.GONE
                    binding.customerRegisterButton.visibility = View.VISIBLE
                    requireContext().makeToast(it.error)
                }
                it.data?.let {
                    binding.customerProgress.visibility = View.GONE
                    binding.customerRegisterButton.visibility = View.VISIBLE

                    collectSharedPreferences(it)
                    startActivity(Intent(requireContext(), ContainerActivity::class.java))
                    requireActivity().finish()

                }
            }
        }
    }

    private fun collectSharedPreferences(it: CustomerRegistrationResponse) {
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

    private fun customerRegistrationValidation() {
        val message = CustomerAuthUtils.customerRegistrationValidation(
            name = viewModel.customerAuthObservables.name,
            email = viewModel.customerAuthObservables.email,
            mobileNumber = viewModel.customerAuthObservables.mobileNumber,
            password = viewModel.customerAuthObservables.password,
            confirmPassword = viewModel.customerAuthObservables.confirmPassword,
            address = viewModel.customerAuthObservables.address
        )
        if (message.isEmpty()) {
            val registrationBody = CustomerRegisterDTO(
                name = viewModel.customerAuthObservables.name,
                email = viewModel.customerAuthObservables.email,
                mobileNumber = viewModel.customerAuthObservables.mobileNumber,
                password = viewModel.customerAuthObservables.password,
                address = viewModel.customerAuthObservables.address,
                pincode = viewModel.customerAuthObservables.pinCode
            )
            viewModel.registration(registrationBody)
        } else {
            requireContext().makeToast(message)
        }
    }
}
