package com.gaur.zpmarket.presentation.payment.cancel_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gaur.zpmarket.databinding.FragmentOrderCancelBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderCancelFragment : Fragment() {

    private var _binding: FragmentOrderCancelBinding? = null
    val binding: FragmentOrderCancelBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderCancelBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }
}
