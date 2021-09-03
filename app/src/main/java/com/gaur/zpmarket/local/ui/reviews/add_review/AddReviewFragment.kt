package com.gaur.zpmarket.local.ui.reviews.add_review

import android.app.AlertDialog
import android.content.SharedPreferences
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
import com.cesarferreira.tempo.Tempo
import com.gaur.zpmarket.databinding.FragmentAddReviewBinding
import com.gaur.zpmarket.local.ui.reviews.CustomerReviewViewModel
import com.gaur.zpmarket.remote.response_customer.reviews.add_review.AddReviewPostBody
import com.gaur.zpmarket.utils.CustomerConstants
import com.gaur.zpmarket.utils.Status
import com.gaur.zpmarket.utils.hideKeyboard
import com.gaur.zpmarket.utils.makeToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@AndroidEntryPoint
class AddReviewFragment : Fragment() {


    @Inject
    lateinit var sharedPreferences: SharedPreferences


    private val viewModel: CustomerReviewViewModel by viewModels()


    private var _binding: FragmentAddReviewBinding? = null
    val binding: FragmentAddReviewBinding
        get() = _binding!!


    private val args: AddReviewFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddReviewBinding.inflate(inflater, container, false)
        return _binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.addReviewToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        args.review?.let {
            binding.reviewRating.rating = it.rating.toFloat()
            binding.reviewEditText.setText(it.comment)
        }

        binding.submitReviewButton.setOnClickListener {
            binding.root.hideKeyboard()
            submitReview()
        }
        binding.deleteReview.setOnClickListener {
            binding.root.hideKeyboard()
            deleteReview()
        }
    }

    private fun submitReview() {
        val comment = binding.reviewEditText.text.toString().trim()
        val rating = binding.reviewRating.rating
        if (comment.isEmpty()) {
            requireContext().makeToast("Please write Something")
            return
        }



        args.product?.let {
            viewModel.postReview(
                sharedPreferences.getString(CustomerConstants.CUSTOMER_AUTH_TOKEN, "")
                    .toString(),
                AddReviewPostBody(
                    comment,
                    sharedPreferences.getString(CustomerConstants.CUSTOMER_ID, "").toString(),
                    rating.toString(), Tempo.now.toString()
                ), it._id
            )
        }

        /**  Submit Review Observable  **/
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.postReview.collect {
                when (it.getContentIfNotHandled()?.status) {
                    Status.LOADING -> {
                        binding.addReviewProgress.visibility = View.VISIBLE
                        binding.submitReviewButton.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        binding.addReviewProgress.visibility = View.GONE
                        binding.submitReviewButton.visibility = View.VISIBLE
                        it.peekContent().message?.let {
                            if (it.isNotEmpty()) requireContext().makeToast(it)
                        }
                    }
                    Status.SUCCESS -> {
                        binding.addReviewProgress.visibility = View.GONE
                        binding.submitReviewButton.visibility = View.VISIBLE
                        it.peekContent().data?.let {
                            requireContext().makeToast(it.message)
                        }
                        findNavController().popBackStack()
                    }
                }
            }
        }

    }

    private fun deleteReview() {
        val token =
            sharedPreferences.getString(CustomerConstants.CUSTOMER_AUTH_TOKEN, "").toString()
        Log.d("TAG", "deleteReview: ${token}")
        args.review?.let {
            AlertDialog.Builder(requireContext()).setTitle("Delete Review")
                .setMessage("Are you sure?").setPositiveButton("Ok") { _, _ ->
                    viewModel.deleteReview(token, it._id)
                }.setNeutralButton("Cancel") { _, _ ->
                    requireContext().makeToast("Cancel")
                }.show()

        }

        /**  Delete Review Observable  **/
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.deleteReview.collect {
                when (it.getContentIfNotHandled()?.status) {
                    Status.LOADING -> {
                        binding.addReviewProgress.visibility = View.VISIBLE
                        binding.submitReviewButton.visibility = View.GONE
                    }
                    Status.SUCCESS -> {
                        binding.addReviewProgress.visibility = View.GONE
                        binding.submitReviewButton.visibility = View.VISIBLE
                        it.peekContent().data?.let {
                            requireContext().makeToast(it.message)
                        }
                        findNavController().popBackStack()
                    }
                    Status.ERROR -> {
                        binding.addReviewProgress.visibility = View.GONE
                        binding.submitReviewButton.visibility = View.VISIBLE
                        it.peekContent().message?.let {
                            if (it.isNotEmpty()) requireContext().makeToast(it)
                        }
                    }
                }
            }
        }


    }

}