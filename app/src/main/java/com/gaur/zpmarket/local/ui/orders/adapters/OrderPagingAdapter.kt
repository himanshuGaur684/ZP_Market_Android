package com.gaur.zpmarket.local.ui.orders.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gaur.zpmarket.databinding.ViewHolderOrdersBinding
import com.gaur.zpmarket.pagination.orders.Order


class OrderPagingAdapter() :
    PagingDataAdapter<Order, OrderPagingAdapter.MyViewHolder>(DIFF_UTIL) {


    private var listener: ((Order) -> Unit)? = null

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Order>() {
            override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean =
                oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean =
                oldItem == newItem
        }
    }


    inner class MyViewHolder(val viewDataBinding: ViewHolderOrdersBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderPagingAdapter.MyViewHolder {
        val binding =
            ViewHolderOrdersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    fun itemClickListener(l: (Order) -> Unit) {
        listener = l
    }

    override fun onBindViewHolder(holder: OrderPagingAdapter.MyViewHolder, position: Int) {
        holder.viewDataBinding.order = getItem(position)

        holder.viewDataBinding.root.setOnClickListener{
            listener?.let {
                getItem(position)?.let { it1 -> it(it1) }
            }
        }


    }


}