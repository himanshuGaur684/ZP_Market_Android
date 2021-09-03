package com.gaur.zpmarket.local.ui.search

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gaur.zpmarket.databinding.FragmentSearchBinding
import com.gaur.zpmarket.local.ui.search.adapters.SearchPagingAdapter
import com.gaur.zpmarket.utils.customerBottomNavigationViewVisibilityGone
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : Fragment() {


    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val searchPagingAdapter = SearchPagingAdapter()

    private val viewModel: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    val binding: FragmentSearchBinding
        get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return _binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.searchProductsEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.postSearch(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })


        searchViewModelObservable()

    }

    private fun searchViewModelObservable() {
        binding.searchRecyclerView.apply {
            adapter = searchPagingAdapter
        }

        searchPagingAdapter.itemClickListener {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToCustomerProductDetailsFragment(productDetails =  it))
        }


        viewModel.searchList.observe(viewLifecycleOwner) {
            it?.let {
                searchPagingAdapter.submitData(lifecycle, it)
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        customerBottomNavigationViewVisibilityGone(requireActivity())
        super.onSaveInstanceState(outState)
    }

    override fun onResume() {
        customerBottomNavigationViewVisibilityGone(requireActivity())
        super.onResume()
    }

}