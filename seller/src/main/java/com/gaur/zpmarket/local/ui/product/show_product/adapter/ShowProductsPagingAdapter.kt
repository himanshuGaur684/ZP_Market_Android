package com.gaur.zpmarket.local.ui.product.show_product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.seller.databinding.ViewHolderShowProductsBinding

class ShowProductsPagingAdapter :
    PagingDataAdapter<Product, ShowProductsPagingAdapter.MyViewHolder>(DIFF_UTIL) {

    var listener: ((Product) -> Unit)? = null

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class MyViewHolder(val viewDataBinding: ViewHolderShowProductsBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onBindViewHolder(holder: ShowProductsPagingAdapter.MyViewHolder, position: Int) {
        holder.viewDataBinding.product = getItem(position)
        holder.viewDataBinding.root.setOnClickListener {
            listener?.let { product ->
                getItem(position)?.let {
                    product(it)
                }
            }
        }
    }

    fun itemClickListener(l: (Product) -> Unit) {
        listener = l
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowProductsPagingAdapter.MyViewHolder {
        val binding = ViewHolderShowProductsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }
}
