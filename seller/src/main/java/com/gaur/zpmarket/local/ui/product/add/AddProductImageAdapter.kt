package com.gaur.zpmarket.local.ui.product.add

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gaur.zpmarket.seller.databinding.ViewHolderAddProductImagesBinding

class AddProductImageAdapter() : RecyclerView.Adapter<AddProductImageAdapter.MyViewHolder>() {

    private var listener: ((Int) -> Unit)? = null

    var list = mutableListOf<Uri>()

    fun setContentList(list: List<Uri>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }


    inner class MyViewHolder(val viewDataBinding: ViewHolderAddProductImagesBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddProductImageAdapter.MyViewHolder {
        val binding = ViewHolderAddProductImagesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    fun removeImage(l: (Int) -> Unit) {
        listener = l
    }

    override fun onBindViewHolder(holder: AddProductImageAdapter.MyViewHolder, position: Int) {
        holder.viewDataBinding.addProductsImage.setImageURI(this.list[position])
        holder.viewDataBinding.removeProductImage.setOnClickListener {
            listener?.let {
                it(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

}