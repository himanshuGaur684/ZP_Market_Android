package com.gaur.zpmarket.local.ui.cart.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gaur.zpmarket.databinding.ViewHolderCartBinding
import com.gaur.zpmarket.pagination.cart.cart_paging_response.Cart

class CartPagingAdapter() :
    PagingDataAdapter<Cart, CartPagingAdapter.MyViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Cart>() {
            override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean =
                oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean =
                oldItem == newItem
        }
    }

    inner class MyViewHolder(val viewDataBinding: ViewHolderCartBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartPagingAdapter.MyViewHolder {
        val binding =
            ViewHolderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartPagingAdapter.MyViewHolder, position: Int) {
        holder.viewDataBinding.product = getItem(position)?.product
        val strike = holder.viewDataBinding.cartListItemMarketPrice
        strike.paintFlags = strike.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }
}
