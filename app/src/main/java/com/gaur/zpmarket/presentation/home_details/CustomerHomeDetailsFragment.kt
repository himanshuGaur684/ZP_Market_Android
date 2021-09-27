package com.gaur.zpmarket.presentation.home_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gaur.zpmarket.databinding.FragmentCustomerHomeDetailsBinding
import com.gaur.zpmarket.presentation.home.adapters.CategoriesAdapter
import com.gaur.zpmarket.presentation.home_details.adapters.NewestProductsPagingAdapter
import com.gaur.zpmarket.presentation.home_details.adapters.ZPAssuredProductPagingAdapter
import com.gaur.zpmarket.utils.customerBottomNavigationViewVisibilityGone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CustomerHomeDetailsFragment : Fragment() {

    private var _binding: FragmentCustomerHomeDetailsBinding? = null
    val binding: FragmentCustomerHomeDetailsBinding
        get() = _binding!!

    private val viewModel: CustomerHomeDetailsViewModel by viewModels()

    private val categoriesAdapter = CategoriesAdapter()
    private val newestAdapter = NewestProductsPagingAdapter()
    private val zpAssuredAdapter = ZPAssuredProductPagingAdapter()
    private val categoriesProductListAdapter = ZPAssuredProductPagingAdapter()

    private val args: CustomerHomeDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerHomeDetailsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.customerHomeDetailsToolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }

        if (args.categories) {
            binding.customerHomeDetailsToolbarTitle.text = "Categories"
            binding.customerHomeDetailsRecycler.adapter = categoriesAdapter
            viewModel.getAllCategories()
            viewModelObservers()
        }
        if (args.newestProducts) {
            binding.customerHomeDetailsToolbarTitle.text = "New Arrivals"
            binding.customerHomeDetailsRecycler.adapter = newestAdapter
            lifecycle.coroutineScope.launchWhenCreated {
                viewModel.newestProductFlow.collect {

                    newestAdapter.submitData(it)
                }
            }
        }
        if (args.zpAssured) {
            binding.customerHomeDetailsToolbarTitle.text = "ZP Assured"
            binding.customerHomeDetailsRecycler.adapter = zpAssuredAdapter
            lifecycle.coroutineScope.launchWhenCreated {
                viewModel.zpAssuredFlow.collect {
                    Log.d("TAG", "onViewCreated: $it")
                    zpAssuredAdapter.submitData(it)
                }
            }
        }

        args.categoriesId?.let {
            binding.customerHomeDetailsToolbarTitle.text = it.name
            viewModel.categoriesId.postValue(it)
            binding.customerHomeDetailsRecycler.adapter = categoriesProductListAdapter
            viewModel.categoriesProductList.observe(viewLifecycleOwner) {
                it?.let {
                    categoriesProductListAdapter.submitData(lifecycle, it)
                }
            }
        }
    }

    private fun viewModelObservers() {
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.categoriesList.collect {

                if (it.isLoading) {
                }
                if (it.error.isNotBlank()) {
                }

                if (it.data.isNotEmpty()) {
                    categoriesAdapter.setContentList(it.data)
                }

            }
        }
    }

    override fun onStart() {
        customerBottomNavigationViewVisibilityGone(requireActivity())
        super.onStart()
    }

    override fun onResume() {
        customerBottomNavigationViewVisibilityGone(requireActivity())
        super.onResume()
    }
}
