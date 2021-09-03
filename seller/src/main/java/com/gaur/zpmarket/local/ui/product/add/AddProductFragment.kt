package com.gaur.zpmarket.local.ui.product.add

import android.app.Activity
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.gaur.zpmarket.seller.databinding.FragmentAddProductBinding
import com.gaur.zpmarket.utils.*
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.util.FileUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


@AndroidEntryPoint
class AddProductFragment : Fragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val viewModel: AddProductViewModel by viewModels()

    private var file: File? = null


    private val imageListAdapter = AddProductImageAdapter()
    private var imageList = HashMap<Uri, File>()
    private val imagePartList = mutableListOf<MultipartBody.Part>()


    private var productCategoryId = ""

    private var _binding: FragmentAddProductBinding? = null
    val binding: FragmentAddProductBinding
        get() = _binding!!


    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == Activity.RESULT_OK) {
                file = FileUtil.getTempFile(requireContext(), data?.data!!)
                //  binding.sellerProductImage.setImageURI(data?.data!!)
                file?.let {
                    imageList[data.data!!] = it
                    imageListAdapter.setContentList(imageList.keys.toMutableList())
                }

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return _binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bottomNavigationVisibilityGone(requireActivity())
        binding.viewModel = viewModel
        binding.addEditToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.sellerProductCategory.setOnClickListener {
            setProductCategory()
        }
        /**Initialize the product **/
        imageUploading()
        imageUploadingObservables()
    }


    override fun onStart() {
        bottomNavigationVisibilityGone(requireActivity())
        viewModel.getAllProductCategory()
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
                binding.sellerProductCategory.setText(key)
                productCategoryId = viewModel.categoryList.get(key).toString()
            }.setPositiveButton("Ok") { dialog, which ->
                dialog.dismiss()
            }.show()
    }


    private fun imageUploading() {

        /** Initialize the Upload Image Recycler View **/
        binding.addImageRecycler.apply {
            val pagerSnapHelper = PagerSnapHelper()
            adapter = imageListAdapter
            pagerSnapHelper.attachToRecyclerView(this)
        }

        /**  Remove the images from selected recycler view  **/
        imageListAdapter.removeImage {
            imageList.remove(imageListAdapter.list[it])
            imageListAdapter.list.removeAt(it)
            imageListAdapter.notifyItemRemoved(it)
        }

        /** Pick the image from internal storage  **/
        binding.sellerProductImage.setOnClickListener {
            if (imageList.size != 4) {
                ImagePicker.with(requireActivity())
                    .compress(500)
                    .crop()//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(
                        1080,
                        1080
                    )//Final image resolution will be less than 1080 x 1080(Optional)
                    .createIntent {
                        startForProfileImageResult.launch(it)
                    }
            } else {
                requireContext().makeToast("You can upload only 4 images")
            }
        }


        /**  Upload Product Image Details **/
        binding.uploadProductImages.setOnClickListener {
            binding.root.hideKeyboard()
            if (imageList.size == 0) {
                binding.root.makeSnack("Please Select Product Image")
                return@setOnClickListener
            }

            /** Create the list of multipart **/
            imageList.values.toList().forEach {
                val request = it.asRequestBody("image/*".toMediaTypeOrNull())
                val imagePart =
                    MultipartBody.Part.createFormData("image", it.name, request)
                imagePartList.add(imagePart)
            }

            val name = binding.sellerProductName.text.toString().makeToRequestBody()
            val productDescription = binding.sellerProductDescription.text.toString()
                .makeToRequestBody()
            val categoryId = productCategoryId.makeToRequestBody()
            val marketPrice = binding.sellerProductMarketPrice.text.toString()
                .makeToRequestBody()
            val discountPrice = binding.sellerDiscountPrice.text.toString()
                .makeToRequestBody()

            val market = binding.sellerProductMarketPrice.text.toString().toFloat()
            val discounts = binding.sellerDiscountPrice.text.toString().toFloat()
            val percentage = (market.minus(discounts).div(market))
            val discount =
                (100 * percentage).toInt().toString()
                    .makeToRequestBody()

            val sellerId = sharedPreferences.getString(SellerConstants.SELLER_ID, "").toString()
                .makeToRequestBody()

            val productDetails = binding.sellerProductDetails.text.toString()
                .makeToRequestBody()
            val productFeatures = binding.sellerProductFeatures.text.toString()
                .makeToRequestBody()
            val productPackaging = binding.sellerProductPackagingDetails.text.toString()
                .makeToRequestBody()
            val cashOnDelivery = if (viewModel.addObservables.cashOnDelivery) "true" else "false"
            val requestBodyCOD =
                cashOnDelivery.toString().makeToRequestBody()
            Log.d("TAG", "imageUploading: ${viewModel.addObservables.cashOnDelivery}")
            /** Post the product to the server  **/
            viewModel.postProduct(
                imagePartList,
                name,
                categoryId,
                productDescription,
                discount,
                discountPrice,
                marketPrice,
                sellerId,
                productDetails,productFeatures,productPackaging,viewModel.addObservables.cashOnDelivery
            )
        }

    }

    private fun imageUploadingObservables() {
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.postProduct.collect {
                when (it.getContentIfNotHandled()?.status) {
                    Status.LOADING -> {

                        binding.addProductProgress.visibility = View.VISIBLE
                        binding.uploadProductImages.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        binding.addProductProgress.visibility = View.GONE
                        binding.uploadProductImages.visibility = View.VISIBLE
                        it.peekContent().message?.let {
                            if (it.isNotEmpty())
                                requireContext().makeToast(it)
                        }
                    }
                    Status.SUCCESS -> {
                        binding.addProductProgress.visibility = View.GONE
                        binding.uploadProductImages.visibility = View.VISIBLE
                        it.peekContent().data?.let {
                            requireContext().makeToast(it.message)
                            imageList.clear()
                            imagePartList.clear()
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }
    }

}