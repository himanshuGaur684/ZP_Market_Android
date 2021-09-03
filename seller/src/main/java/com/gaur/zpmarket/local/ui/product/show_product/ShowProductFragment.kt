package com.gaur.zpmarket.local.ui.product.show_product

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.gaur.zpmarket.local.ui.product.show_product.adapter.ShowProductsPagingAdapter
import com.gaur.zpmarket.seller.databinding.FragmentShowProductBinding
import com.gaur.zpmarket.utils.bottomNavigationVisibilityVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class ShowProductFragment : Fragment() {

    private var _binding: FragmentShowProductBinding? = null
    val binding: FragmentShowProductBinding
        get() = _binding!!

    private val showProductAdapter = ShowProductsPagingAdapter()

    private val viewModel: ShowProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowProductBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAllProductCategory()
        binding.sellerProductsRecycler.apply {
            adapter = showProductAdapter
        }


        binding.sellerShowProductSwipeToRefresh.setOnRefreshListener {
            binding.sellerShowProductSwipeToRefresh.isRefreshing = false
            showProductAdapter.refresh()
        }


        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.productsStreams.collect {
                showProductAdapter.submitData(it)
            }
        }

        binding.addSellerProducts.setOnClickListener {
            findNavController().navigate(
                ShowProductFragmentDirections.actionShowProductFragmentToAddProductFragment()
            )
        }

        showProductAdapter.itemClickListener {
            findNavController().navigate(
                ShowProductFragmentDirections.actionShowProductFragmentToProductDetailsFragment(
                    it._id
                )
            )
        }

    }




    override fun onStart() {
        bottomNavigationVisibilityVisible(requireActivity())
        super.onStart()
    }

    override fun onResume() {
        bottomNavigationVisibilityVisible(requireActivity())
        super.onResume()
    }

}