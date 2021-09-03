package com.gaur.zpmarket.local.ui.product.details_products

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
import androidx.recyclerview.widget.PagerSnapHelper
import com.gaur.zpmarket.local.ui.product.add.AddProductViewModel
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.seller.databinding.FragmentProductDetailsBinding
import com.gaur.zpmarket.utils.Status
import com.gaur.zpmarket.utils.bottomNavigationVisibilityGone
import com.gaur.zpmarket.utils.makeToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SellerProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    val binding: FragmentProductDetailsBinding
        get() = _binding!!

    private val pageSnap = PagerSnapHelper()
    private val imageListAdapter = DetailsProductImageAdapter()

    private val viewModel: AddProductViewModel by viewModels()

    private var _product: Product? = null
    val product: Product
        get() = _product!!

    private val args: SellerProductDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        args.id?.let {
            viewModel.getSingleProduct(it)
        }
        binding.productDetailsRecycler.apply {
            adapter = imageListAdapter
            pageSnap.attachToRecyclerView(this)
        }

        binding.productDetailsToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.sellerEditProducts.setOnClickListener {
            Log.d("TAG", "onViewCreated: $product")

            findNavController().navigate(
                SellerProductDetailsFragmentDirections.actionProductDetailsFragmentToEditProductFragment(
                    editProduct = product
                )
            )
        }

        binding.sellerDeleteProducts.setOnClickListener {
            viewModel.deleteProduct(product._id)
        }

        /**  Delete Product Responses Flow Collector **/
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.delete.collect {
                when (it.getContentIfNotHandled()?.status) {
                    Status.LOADING -> {
                        binding.deleteProgress.visibility = View.VISIBLE
                        binding.sellerDeleteProducts.visibility = View.INVISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.deleteProgress.visibility = View.INVISIBLE
                        binding.sellerDeleteProducts.visibility = View.VISIBLE
                        it.peekContent().data?.let {
                            findNavController().popBackStack()
                            requireContext().makeToast(it.message)
                        }
                    }
                    Status.ERROR -> {
                        binding.deleteProgress.visibility = View.INVISIBLE
                        binding.sellerDeleteProducts.visibility = View.VISIBLE
                        it.peekContent().message?.let { message ->
                            if (message.isNotEmpty()) requireContext().makeToast(message)
                        }
                    }
                }
            }
        }

        /**  Single Product Responses Flow Collector **/
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.product.collect {
                when (it.getContentIfNotHandled()?.status) {
                    Status.LOADING -> {
                        binding.sellerProductDetailsProgress.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.sellerProductDetailsProgress.visibility = View.GONE
                        it.peekContent().data?.let {
                            binding.detailsProduct = it.product
                            _product = it.product
                            imageListAdapter.setContentList(it.product.imageUrl)
                        }
                    }
                    Status.ERROR -> {
                        binding.sellerProductDetailsProgress.visibility = View.GONE
                        it.peekContent().message?.let { message ->
                            if (message.isNotEmpty()) requireContext().makeToast(message)
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        bottomNavigationVisibilityGone(requireActivity())
        super.onStart()
    }

    override fun onResume() {
        args.id?.let {
            viewModel.getSingleProduct(it)
        }
        bottomNavigationVisibilityGone(requireActivity())
        super.onResume()
    }
}
