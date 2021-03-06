package com.gaur.zpmarket.presentation.profile

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.gaur.zpmarket.databinding.FragmentCustomerProfileBinding
import com.gaur.zpmarket.utils.CustomerConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class CustomerProfileFragment : Fragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private var _binding: FragmentCustomerProfileBinding? = null
    val binding: FragmentCustomerProfileBinding
        get() = _binding!!

    private val viewModel: CustomerProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerProfileBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = sharedPreferences.getString(CustomerConstants.CUSTOMER_ID, "").toString()
        viewModel.getCustomerProfile(id)
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.profile.collect {


                if (it.isLoading) {
                }
                if (it.error.isNotBlank()) {
                }

                it.data?.let {
                    binding.customer = it.customer
                }

            }
        }
    }
}
