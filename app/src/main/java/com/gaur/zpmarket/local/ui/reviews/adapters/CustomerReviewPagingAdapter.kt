package com.gaur.zpmarket.local.ui.reviews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gaur.zpmarket.databinding.ViewHolderCustomerReviewsBinding
import com.gaur.zpmarket.pagination.reviews.review_paging_response.Review

class CustomerReviewPagingAdapter :
    PagingDataAdapter<Review, CustomerReviewPagingAdapter.MyViewHolder>(DIFF_UTIL) {

    var listener: ((Review) -> Unit)? = null

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class MyViewHolder(val viewDataBinding: ViewHolderCustomerReviewsBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onBindViewHolder(holder: CustomerReviewPagingAdapter.MyViewHolder, position: Int) {
        holder.viewDataBinding.review = getItem(position)

        holder.viewDataBinding.root.setOnClickListener {
            listener?.let {
                getItem(position)?.let {
                    it(it)
                }
            }
        }
    }

    fun itemClickListener(l: (Review) -> Unit) {
        listener = l
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomerReviewPagingAdapter.MyViewHolder {
        val binding = ViewHolderCustomerReviewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }
}
