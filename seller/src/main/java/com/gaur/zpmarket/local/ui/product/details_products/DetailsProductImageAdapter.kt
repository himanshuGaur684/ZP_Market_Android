package com.gaur.zpmarket.local.ui.product.details_products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gaur.zpmarket.seller.databinding.ViewHolderProductDetailsBinding

class DetailsProductImageAdapter() :
    RecyclerView.Adapter<DetailsProductImageAdapter.MyViewHolder>() {

    var list = mutableListOf<String>()

    fun setContentList(list: List<String>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    inner class MyViewHolder(val viewDataBinding: ViewHolderProductDetailsBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailsProductImageAdapter.MyViewHolder {
        val binding = ViewHolderProductDetailsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailsProductImageAdapter.MyViewHolder, position: Int) {
        Glide.with(holder.viewDataBinding.root).load(this.list[position]).into(
            holder.viewDataBinding.productDetailsImage
        )
    }

    override fun getItemCount(): Int {
        return this.list.size
    }
}
