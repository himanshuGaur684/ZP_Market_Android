package com.gaur.zpmarket.presentation.home_details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gaur.zpmarket.databinding.ViewHolderNewestProductBinding
import com.gaur.zpmarket.remote.response_customer.Product

class NewestProductsPagingAdapter :
    PagingDataAdapter<Product, NewestProductsPagingAdapter.MyViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }

    inner class MyViewHolder(val viewDataBinding: ViewHolderNewestProductBinding) : RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewestProductsPagingAdapter.MyViewHolder {
        val binding = ViewHolderNewestProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewestProductsPagingAdapter.MyViewHolder, position: Int) {
        holder.viewDataBinding.product = getItem(position)
    }
}
