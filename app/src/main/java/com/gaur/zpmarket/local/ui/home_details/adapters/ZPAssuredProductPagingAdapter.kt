package com.gaur.zpmarket.local.ui.home_details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gaur.zpmarket.databinding.ViewHolderZpAssuredBinding
import com.gaur.zpmarket.remote.response_customer.Product

class ZPAssuredProductPagingAdapter() :
    PagingDataAdapter<Product, ZPAssuredProductPagingAdapter.MyViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }

    inner class MyViewHolder(val viewDataBinding: ViewHolderZpAssuredBinding) : RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ZPAssuredProductPagingAdapter.MyViewHolder {
        val binding = ViewHolderZpAssuredBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ZPAssuredProductPagingAdapter.MyViewHolder, position: Int) {
        holder.viewDataBinding.product = getItem(position)
    }
}
