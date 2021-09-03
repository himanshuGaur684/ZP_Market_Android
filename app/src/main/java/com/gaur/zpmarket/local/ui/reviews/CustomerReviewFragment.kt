package com.gaur.zpmarket.local.ui.reviews

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gaur.zpmarket.databinding.FragmentCustomerReviewBinding
import com.gaur.zpmarket.local.ui.reviews.adapters.CustomerReviewPagingAdapter
import com.gaur.zpmarket.utils.CustomerConstants
import com.gaur.zpmarket.utils.customerBottomNavigationViewVisibilityGone
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CustomerReviewFragment : Fragment() {


    private var _binding: FragmentCustomerReviewBinding? = null
    val binding: FragmentCustomerReviewBinding
        get() = _binding!!


    private val args: CustomerReviewFragmentArgs by navArgs()
    private val viewModel: CustomerReviewViewModel by viewModels()
    private val customerReviewAdapter = CustomerReviewPagingAdapter()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerReviewBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.customerReviewsRecycler.apply {
            adapter = customerReviewAdapter
        }

        args.productId?.let {
            handleProductsReview(it)
        }

        args.customerId?.let {
            handleCustomerProductsReviews(it)
        }

        binding.customerReviewToolbarNavigationBack.setOnClickListener {
            findNavController().popBackStack()
        }


        customerReviewAdapter.itemClickListener {
            if (sharedPreferences.getString(CustomerConstants.CUSTOMER_ID, "")
                    .toString() == it.customer._id
            ) {
                findNavController().navigate(
                    CustomerReviewFragmentDirections.actionCustomerReviewFragmentToAddReviewFragment(
                        review = it
                    )
                )
            }
        }
    }

    private fun handleCustomerProductsReviews(it: String) {
        binding.customerReviewsToolbar.title = "Your Reviews"
        viewModel.postCustomerId(it)
        viewModel.customerReviewList.observe(viewLifecycleOwner) {
            it?.let {
                customerReviewAdapter.submitData(lifecycle, it)
            }
        }

    }

    private fun handleProductsReview(it: String) {
        binding.customerReviewsToolbar.title = "Product Reviews"
        viewModel.postProductId(it)
        viewModel.reviewList.observe(viewLifecycleOwner) {
            it?.let {
                customerReviewAdapter.submitData(lifecycle, it)
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