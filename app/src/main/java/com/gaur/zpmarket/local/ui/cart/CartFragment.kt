package com.gaur.zpmarket.local.ui.cart

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gaur.zpmarket.databinding.FragmentCartBinding
import com.gaur.zpmarket.local.ui.cart.adapters.CartPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    val binding: FragmentCartBinding
        get() = _binding!!


    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val cartViewModel: CartViewModel by viewModels()

    private val cartPagingAdapter = CartPagingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return _binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cartViewModel.postCustomerId(
            ""
        )

        binding.cartRecycler.apply {
            adapter = cartPagingAdapter
        }

        cartViewModel.cartList.observe(viewLifecycleOwner) {
            it?.let {
                cartPagingAdapter.submitData(lifecycle, it)
            }
        }

    }

}