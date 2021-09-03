package com.gaur.zpmarket.local.ui.product_details

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.PagerSnapHelper
import com.gaur.zpmarket.databinding.FragmentCustomerProductDetailsBinding
import com.gaur.zpmarket.local.ui.cart.CartViewModel
import com.gaur.zpmarket.local.ui.product.details_products.DetailsProductImageAdapter
import com.gaur.zpmarket.local.ui.product_details.adapters.CustomerReviewAdapter
import com.gaur.zpmarket.local.ui.product_details.payment.PaymentActivity
import com.gaur.zpmarket.local.ui.reviews.CustomerReviewViewModel
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.remote.response_customer.cart.CartPostBody
import com.gaur.zpmarket.utils.CustomerConstants
import com.gaur.zpmarket.utils.Status
import com.gaur.zpmarket.utils.customerBottomNavigationViewVisibilityGone
import com.gaur.zpmarket.utils.makeToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class CustomerProductDetailsFragment : Fragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val productDetailsViewModel: ProductDetailsViewModel by viewModels()

    private var _binding: FragmentCustomerProductDetailsBinding? = null
    val binding: FragmentCustomerProductDetailsBinding
        get() = _binding!!

    private val viewModel: CustomerReviewViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

    private val customerReviewAdapter = CustomerReviewAdapter()
    private val args: CustomerProductDetailsFragmentArgs by navArgs()

    private val addProductImageAdapter = DetailsProductImageAdapter()
    private val pageSnap = PagerSnapHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerProductDetailsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /** write a new Review **/
        binding.writeProductReview.setOnClickListener {
            findNavController().navigate(
                CustomerProductDetailsFragmentDirections.actionCustomerProductDetailsFragmentToAddReviewFragment(
                    product = args.productDetails
                )
            )
        }

        /**Toolbar navigation icon**/
        binding.productDetailsToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        /** Product Details navArgs  **/
        args.productDetails?.let { product ->
            cartViewModel.insertRecentlyViewedProduct(product)
            binding.customerProductDetailsReviewRecycler.apply {
                adapter = customerReviewAdapter
                pageSnap.attachToRecyclerView(this)
                this.setHasFixedSize(true)
            }

            /**  Setup the Product details image to recycler**/
            binding.customerProductDetailsRecyclerImage.apply {
                addProductImageAdapter.setContentList(product.imageUrl)
                adapter = addProductImageAdapter
            }

            /** See all reviews **/
            binding.customerProductDetailsSeeAllReview.setOnClickListener {
                findNavController().navigate(
                    CustomerProductDetailsFragmentDirections.actionCustomerProductDetailsFragmentToCustomerReviewFragment(
                        productId = product._id
                    )
                )
            }

            /** set xml product value**/
            binding.detailsProduct = product
            binding.isOrder = args.isOrder

            /** Initialize the binding**/
            init()
            /** add to cart handler  **/
            addToCart(product)

            /**  Get only 5 reviews  **/
            onlyFiveReview(product)

            /**  click on buy now product  **/
            buyProduct()
            /** Review list item Click listener **/
            customerReviewAdapter.itemClickListener {
                if (it.customer._id == sharedPreferences.getString(
                        CustomerConstants.CUSTOMER_ID,
                        ""
                    ).toString()
                ) {
                    findNavController().navigate(
                        CustomerProductDetailsFragmentDirections.actionCustomerProductDetailsFragmentToAddReviewFragment(
                            review = it
                        )
                    )
                }
            }
        }
    }

    private fun init() {
        if (args.isOrder) {
            binding.buyProductButton.text = "Cancel Order"
        }
    }

    private fun buyProduct() {
        binding.buyProductButton.setOnClickListener {
            if (!args.isOrder) {
                val intent = Intent(requireContext(), PaymentActivity::class.java)
                intent.putExtra("product", args.productDetails)
                startActivity(intent)
            } else {
                requireContext().makeToast("Making just a minute")
            }
        }
    }

    /**  add to cart  **/
    private fun addToCart(product: Product) {

        binding.customerAddToCartProducts.setOnClickListener {
            cartViewModel.postCart(
                CartPostBody(
                    sharedPreferences.getString(
                        CustomerConstants.CUSTOMER_ID,
                        ""
                    ).toString(),
                    product._id,
                    Date().toString()
                )
            )
        }

        lifecycle.coroutineScope.launchWhenCreated {
            cartViewModel.postCart.collect {
                when (it.getContentIfNotHandled()?.status) {
                    Status.LOADING -> {
                        binding.customerAddToCartProducts.isEnabled = false
                    }
                    Status.SUCCESS -> {
                        binding.customerAddToCartProducts.isEnabled = true
                        it.peekContent().data?.let {
                            requireContext().makeToast(it.message)
                        }
                    }
                    Status.ERROR -> {
                        binding.customerAddToCartProducts.isEnabled = true
                        it.peekContent().message?.let {
                            if (it.isNotEmpty()) requireContext().makeToast(it)
                        }
                    }
                }
            }
        }
    }

    /** Only five review get from server  **/
    private fun onlyFiveReview(product: Product) {
        viewModel.getReviewOnly5(product._id)
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.reviewListOnly5.collect {

                when (it.getContentIfNotHandled()?.status) {
                    Status.LOADING -> {
                        binding.apply {
                            noReviewYetText.visibility = View.GONE
                            reviewLoaderProgress.visibility = View.VISIBLE
                            customerProductDetailsReviewRecycler.visibility = View.GONE
                            customerProductDetailsSeeAllReview.visibility = View.GONE
                            seeReviewText.visibility = View.GONE
                        }
                    }
                    Status.ERROR -> {
                        binding.apply {
                            noReviewYetText.visibility = View.GONE
                            reviewLoaderProgress.visibility = View.GONE
                            customerProductDetailsReviewRecycler.visibility = View.GONE
                            customerProductDetailsSeeAllReview.visibility = View.GONE
                            seeReviewText.visibility = View.GONE
                        }
                        it.peekContent().message?.let {
                            if (it.isNotEmpty()) requireContext().makeToast(it)
                        }
                    }
                    Status.SUCCESS -> {

                        binding.reviewLoaderProgress.visibility = View.GONE
                        it.peekContent().data?.let {

                            if (it.results.isEmpty()) {
                                binding.apply {
                                    noReviewYetText.visibility = View.VISIBLE
                                    customerProductDetailsReviewRecycler.visibility = View.GONE
                                    customerProductDetailsSeeAllReview.visibility = View.GONE
                                    seeReviewText.visibility = View.GONE
                                }
                            } else {
                                binding.apply {
                                    noReviewYetText.visibility = View.GONE
                                    customerProductDetailsReviewRecycler.visibility =
                                        View.VISIBLE
                                    customerProductDetailsSeeAllReview.visibility = View.VISIBLE
                                    seeReviewText.visibility = View.VISIBLE
                                }
                                customerReviewAdapter.setContentList(it.results)
                            }
                        }
                    }
                }
            }
        }
    }

    /** Always update our Product**/
    private fun updateProduct(id: String) {
        productDetailsViewModel.getSingleProduct(id)

        lifecycle.coroutineScope.launchWhenCreated {
            productDetailsViewModel.product.collect {
                when (it.peekContent().status) {
                    Status.SUCCESS -> {
                        binding.detailsProduct = it.peekContent().data
                    }
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
        args.productDetails?.let {
            updateProduct(it._id)
        }
        super.onResume()
    }
}
