package com.gaur.zpmarket.local.ui.product.edit_product

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.PagerSnapHelper
import com.gaur.zpmarket.local.ui.product.add.AddProductViewModel
import com.gaur.zpmarket.local.ui.product.details_products.DetailsProductImageAdapter
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.seller.databinding.FragmentEditProductBinding
import com.gaur.zpmarket.utils.Status
import com.gaur.zpmarket.utils.bottomNavigationVisibilityGone
import com.gaur.zpmarket.utils.hideKeyboard
import com.gaur.zpmarket.utils.makeToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class EditProductFragment : Fragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val viewModel: AddProductViewModel by viewModels()

    private var file: File? = null

    private var productCategoryId = ""

    private var _product: Product? = null
    val product: Product
        get() = _product!!


    private var _binding: FragmentEditProductBinding? = null
    val binding: FragmentEditProductBinding
        get() = _binding!!

    private val imageListAdapter = DetailsProductImageAdapter()
    private val pageSnap = PagerSnapHelper()


    private val args: EditProductFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditProductBinding.inflate(inflater, container, false)
        viewModel.getAllProductCategory()
        return _binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.file = file.toString()

        /** Initialize the recycler on the top  **/
        binding.editSellerProductImage.apply {
            adapter = imageListAdapter
            pageSnap.attachToRecyclerView(this)
        }

        args.editProduct?.let { p ->
            Log.d("TAG", "onViewCreated:in teh edit fragment $p")
            _product = p
            binding.apply {

                productCategoryId= p.categoryId

                editSellerProductName.setText(p.name)
                editSellerDiscountPrice.setText(p.discountPrice.toString())
                editSellerProductDescription.setText(p.description)
                imageListAdapter.setContentList(p.imageUrl)
                editSellerProductMarketPrice.setText(p.marketPrice.toString())
                setCategoryNameToEditText(p.categoryId)
                editSellerProductDetails.setText(p.productDetails)
                editSellerProductFeatures.setText(p.productFeatures)
                editSellerProductPackagingDetails.setText(p.packagingDetails)
                editCashOnDelivery.isChecked = p.cashOnDelivery
            }
        }

        binding.editProductCategory.setOnClickListener {
            setProductCategory()
        }

        binding.editProductImagesButton.setOnClickListener {
            editViewModelObserver()
            binding.root.hideKeyboard()
            modifyProduct()
        }


    }

    private fun modifyProduct() {

        /** Extracting the Data from views  **/
        val name = binding.editSellerProductName.text.toString()
        val productDescription =
            binding.editSellerProductDescription.text.toString()
        val categoryId = productCategoryId
        val marketPrice = binding.editSellerProductMarketPrice.text.toString()
        val discountPrice = binding.editSellerDiscountPrice.text.toString()
        val market = binding.editSellerProductMarketPrice.text.toString().toFloat()
        val discounts = binding.editSellerDiscountPrice.text.toString().toFloat()
        val percentage = (market.minus(discounts).div(market))
        val discount =
            (100 * percentage).toInt().toString()
        val packaging = binding.editSellerProductPackagingDetails.text.toString().trim()
        val productDetails = binding.editSellerProductDetails.text.toString().trim()
        val cashOnDelivery = binding.editCashOnDelivery.isChecked
        val productFeatures = binding.editSellerProductFeatures.text.toString().trim()

        /** Validate the Inputs  **/
        val result = EditProductUtils.validateData(
            name,
            productDescription,
            categoryId,
            marketPrice,
            discountPrice,
            packaging,
            productDetails,
            productFeatures
        )

        if (result.isNotEmpty()) {
            requireContext().makeToast(result)
            return
        }

        /** Update the Product  **/
        viewModel.editProducts(
            id = product._id,
            name = name,
            categoryId = categoryId,
            description = productDescription,
            discount = discount,
            discountPrice = discountPrice,
            marketPrice = marketPrice,
            cashOnDelivery = cashOnDelivery,
            packagingDetails = packaging,
            productDetails = productDetails,
            productFeatures = productFeatures
        )


    }

    /** Observe the changes of edit Observer **/
    private fun editViewModelObserver() {
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.postProduct.collect {
                when (it.getContentIfNotHandled()?.status) {
                    Status.ERROR -> {
                        binding.editProductProgress.visibility = View.GONE
                        binding.editProductImagesButton.visibility = View.VISIBLE
                        it.peekContent()?.message?.let {
                            if (it.isNotEmpty()) requireContext().makeToast(it)
                        }
                    }
                    Status.LOADING -> {
                        binding.editProductImagesButton.visibility = View.GONE
                        binding.editProductProgress.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.editProductProgress.visibility = View.GONE
                        binding.editProductImagesButton.visibility = View.VISIBLE
                        it.peekContent().data?.let {
                            if (it.message.isNotEmpty()) requireContext().makeToast(it.message)
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
        bottomNavigationVisibilityGone(requireActivity())
        super.onResume()
    }

    private fun setProductCategory() {
        val categoryList = viewModel.categoryList.keys.toTypedArray()
        MaterialAlertDialogBuilder(requireContext()).setTitle("Product Categories")
            .setSingleChoiceItems(categoryList, 0) { dialog, which ->
                val key = categoryList[which]
                binding.editProductCategory.setText(key)
                productCategoryId = viewModel.categoryList.get(key).toString()
            }.setPositiveButton("Ok") { dialog, which ->
                dialog.dismiss()
            }.show()
    }

    private fun setCategoryNameToEditText(categoryId: String) {
        viewModel.getEditTextProductCategory()
        viewModel.editCategoryList.observe(viewLifecycleOwner) {
            it?.let {
                val categoryName = it.keys.toList()
                val keysList = it.values.toList()
                val index = keysList.indexOf(categoryId)
                binding.editProductCategory.setText(categoryName[index])
            }
        }

    }


}