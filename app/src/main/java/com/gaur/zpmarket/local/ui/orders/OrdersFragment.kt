package com.gaur.zpmarket.local.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.gaur.zpmarket.databinding.FragmentOrdersBinding
import com.gaur.zpmarket.local.ui.orders.adapters.OrderPagingAdapter
import com.gaur.zpmarket.utils.customerBottomNavigationViewVisibilityVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null
    val binding: FragmentOrdersBinding
        get() = _binding!!

    private val orderViewModel: OrderViewModel by viewModels()

    private val orderPagingAdapter = OrderPagingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.customerOrderRecycler.apply {
            this.adapter = orderPagingAdapter
        }

        lifecycle.coroutineScope.launchWhenCreated {
            orderViewModel.orderList.collect {
                orderPagingAdapter.submitData(it)
            }
        }

        orderPagingAdapter.itemClickListener {
            findNavController().navigate(
                OrdersFragmentDirections.actionOrdersFragmentToCustomerProductDetailsFragment(
                    it.product,
                    isOrder = true
                )
            )
        }
    }

    override fun onResume() {
        customerBottomNavigationViewVisibilityVisible(requireActivity())
        super.onResume()
    }

    override fun onStart() {
        customerBottomNavigationViewVisibilityVisible(requireActivity())
        super.onStart()
    }
}
