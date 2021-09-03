package com.gaur.zpmarket.local.ui.auth.registration

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
import com.gaur.zpmarket.remote.response_seller.register.SellerRegistrationObject
import com.gaur.zpmarket.remote.response_seller.register.SellerRegistrationResponseObject
import com.gaur.zpmarket.seller.R
import com.gaur.zpmarket.seller.databinding.FragmentSellerRegistrationBinding
import com.gaur.zpmarket.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class SellerRegistrationFragment : Fragment() {


    @Inject
    lateinit var sharedPreferences: SharedPreferences

    lateinit var binding: FragmentSellerRegistrationBinding

    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSellerRegistrationBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModelObserve()

        binding.sellerGoToLoginPage.setOnClickListener {
            findNavController().navigate(R.id.sellerLoginFragment)
        }

        binding.sellerRegisterButton.setOnClickListener {
            binding.root.hideKeyboard()
            viewModel.registrationObservables.apply {
                val validator = AuthUtils.sellerRegistrationValidator(
                    name,
                    email,
                    mobileNumber,
                    password,
                    confirmPassword
                )
                if (validator.isEmpty()) {
                    viewModel.sellerRegistration(
                        SellerRegistrationObject(
                            email,
                            mobileNumber,
                            name,
                            password
                        )
                    )
                } else {
                    binding.root.makeSnack(validator)
                }

            }

        }

    }

    private fun viewModelObserve() {
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.registration.collect {
                when (it.getContentIfNotHandled()?.status) {
                    Status.LOADING -> {
                        binding.sellerProgress.visibility = View.VISIBLE
                        binding.sellerRegisterButton.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        binding.sellerProgress.visibility = View.GONE
                        binding.sellerRegisterButton.visibility = View.VISIBLE
                        it.peekContent().message?.let {
                            if (it.isNotEmpty()) binding.root.makeSnack(it)
                        }
                    }
                    Status.SUCCESS -> {
                        binding.sellerProgress.visibility = View.GONE
                        binding.sellerRegisterButton.visibility = View.VISIBLE
                        it.peekContent().data?.let {
                            updateSharedPreferences(it)
                        }
                        requireContext().makeToast("Successfully Registered")
                    }
                    Status.EMPTY -> {
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
            putString(SellerConstants.SELLER_ID,sellerRegistrationResponseObject.seller._id).apply()
            putString(SellerConstants.TOKEN, sellerRegistrationResponseObject.token).apply()
            putString(SellerConstants.SELLER_REFRESH_TOKEN,sellerRegistrationResponseObject.refreshToken).apply()
        }.apply()
        startActivity(Intent(requireContext(),SellerContainerActivity::class.java))
        requireActivity().finish()
    }


}