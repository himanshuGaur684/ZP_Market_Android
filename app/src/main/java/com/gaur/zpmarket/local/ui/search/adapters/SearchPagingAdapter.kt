package com.gaur.zpmarket.local.ui.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.seller.databinding.ViewHolderShowProductsBinding

class SearchPagingAdapter :
    PagingDataAdapter<Product, SearchPagingAdapter.MyViewHolder>(DIFF_UTIL) {

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

    override fun onBindViewHolder(holder: SearchPagingAdapter.MyViewHolder, position: Int) {
        holder.viewDataBinding.product = getItem(position)

        holder.viewDataBinding.root.setOnClickListener {
            listener?.let {
                getItem(position)?.let { it1 -> it(it1) }
            }
        }
    }

    fun itemClickListener(l: (Product) -> Unit) {
        listener = l
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchPagingAdapter.MyViewHolder {
        val binding = ViewHolderShowProductsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }
}
