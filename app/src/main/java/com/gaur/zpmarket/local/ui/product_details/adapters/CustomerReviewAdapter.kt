package com.gaur.zpmarket.local.ui.product_details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gaur.zpmarket.databinding.ViewHolderCustomerReviewsBinding
import com.gaur.zpmarket.pagination.reviews.review_paging_response.Review


class CustomerReviewAdapter() : RecyclerView.Adapter<CustomerReviewAdapter.MyViewHolder>() {

    private var list = listOf<Review>()
    private var listener: ((Review) -> Unit)? = null

    fun setContentList(list: List<Review>) {

        this.list = list
        notifyDataSetChanged()
        return


    }

    inner class MyViewHolder(val viewDataBinding: ViewHolderCustomerReviewsBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            ViewHolderCustomerReviewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MyViewHolder(binding)
    }

    fun itemClickListener(l: (Review) -> Unit) {
        listener = l
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.viewDataBinding.review = this.list[position]

        holder.viewDataBinding.root.setOnClickListener {
            listener?.let {
                it(this.list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return this.list.size
    }


}