package com.gaur.zpmarket.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.gaur.zpmarket.databinding.FragmentCustomerHomeBinding
import com.gaur.zpmarket.presentation.home.adapters.CategoriesAdapter
import com.gaur.zpmarket.presentation.home.adapters.NewestArrivedAdapter
import com.gaur.zpmarket.presentation.home.adapters.RecentlyViewedAdapter
import com.gaur.zpmarket.presentation.home.adapters.ZpMarketAssuredAdapter
import com.gaur.zpmarket.utils.customerBottomNavigationViewVisibilityVisible
import com.gaur.zpmarket.utils.makeToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CustomerHomeFragment : Fragment() {

    private var _binding: FragmentCustomerHomeBinding? = null
    val binding: FragmentCustomerHomeBinding
        get() = _binding!!

    private val categoriesAdapter = CategoriesAdapter()
    private val newestAdapter = NewestArrivedAdapter()
    private val zpAssuredAdapter = ZpMarketAssuredAdapter()

    private val recentlyViewedAdapter = RecentlyViewedAdapter()

    private val viewModel: CustomerHomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRecyclerView()

        binding.searchView.setOnClickListener {
            findNavController().navigate(CustomerHomeFragmentDirections.actionCustomerHomeFragmentToSearchFragment())
        }

        binding.categoriesViewAll.setOnClickListener {
            findNavController().navigate(
                CustomerHomeFragmentDirections.actionCustomerHomeFragmentToCustomerHomeDetailsFragment(
                    categories = true,
                    categoriesId = null
                )
            )
        }

        binding.newestViewAll.setOnClickListener {
            findNavController().navigate(
                CustomerHomeFragmentDirections.actionCustomerHomeFragmentToCustomerHomeDetailsFragment(
                    newestProducts = true, categoriesId = null
                )
            )
        }

        binding.zpAssuredViewAll.setOnClickListener {
            findNavController().navigate(
                CustomerHomeFragmentDirections.actionCustomerHomeFragmentToCustomerHomeDetailsFragment(
                    zpAssured = true, categoriesId = null
                )
            )
        }

        categoriesAdapter.itemClickListener {
            findNavController().navigate(
                CustomerHomeFragmentDirections.actionCustomerHomeFragmentToCustomerHomeDetailsFragment(
                    categoriesId = it
                )
            )
        }

        zpAssuredAdapter.itemClickListener {
            findNavController().navigate(
                CustomerHomeFragmentDirections.actionCustomerHomeFragmentToCustomerProductDetailsFragment(
                    productDetails = it
                )
            )
        }

        newestAdapter.itemClickListener {
            findNavController().navigate(
                CustomerHomeFragmentDirections.actionCustomerHomeFragmentToCustomerProductDetailsFragment(
                    productDetails = it
                )
            )
        }

        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.recentlyViewedProducts.collect {

                if (it.isLoading) {
                }
                if (it.error.isNotBlank()) {
                }
                if (it.data.isNotEmpty()) {

                }
                it.data.let {
                    if (it.isEmpty()) binding.customerHomeRecentlyViewedText.visibility =
                        View.GONE
                    recentlyViewedAdapter.setContentList(it)
                }

            }
        }

        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.homeResponse.collect {

                if (it.isLoading) {
                }
                if (it.error.isNotBlank()) {
                    requireContext().makeToast(it.error)
                }

                it.data?.let {
                    binding.homeResponse = it
                    categoriesAdapter.setContentList(it.result.categories)
                    newestAdapter.setContentList(it.result.newestArrived)
                    zpAssuredAdapter.setContentList(it.result.zp_market_assured)
                }

            }
        }
    }

    private fun setUpRecyclerView() {
        binding.apply {
            newestRecycler.adapter = newestAdapter
            categoriesHome.adapter = categoriesAdapter
            zpAssuredRecycler.adapter = zpAssuredAdapter
            recentlyViewedRecycler.adapter = recentlyViewedAdapter
        }
    }

    override fun onStart() {
        viewModel.getAllRecentlyViewedProducts()
        customerBottomNavigationViewVisibilityVisible(requireActivity())
        super.onStart()
    }

    override fun onResume() {
        viewModel.getAllRecentlyViewedProducts()
        customerBottomNavigationViewVisibilityVisible(requireActivity())
        super.onResume()
    }
}
