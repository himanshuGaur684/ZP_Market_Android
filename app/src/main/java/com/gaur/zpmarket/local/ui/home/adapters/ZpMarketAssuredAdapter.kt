package com.gaur.zpmarket.local.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gaur.zpmarket.databinding.ViewHolderZpAssuredBinding
import com.gaur.zpmarket.remote.response_customer.Product



class ZpMarketAssuredAdapter() : RecyclerView.Adapter<ZpMarketAssuredAdapter.MyViewHolder>() {

    private var list = listOf<Product>()


    private var listener: ((Product) -> Unit)? = null


    fun setContentList(list: List<Product>) {
        if (this.list.isEmpty()) {
            this.list = list
            notifyDataSetChanged()
            return
        }
        val d = DiffUtilHomeAdapter(oldList = this.list, newList = list)
        val diffUtilResult = DiffUtil.calculateDiff(d)
        diffUtilResult.dispatchUpdatesTo(this)
    }

    inner class MyViewHolder(val viewDataBinding: ViewHolderZpAssuredBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ZpMarketAssuredAdapter.MyViewHolder {
        val binding =
            ViewHolderZpAssuredBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ZpMarketAssuredAdapter.MyViewHolder, position: Int) {
        holder.viewDataBinding.product = this.list[position]

        holder.viewDataBinding.root.setOnClickListener {
            listener?.let {
                it(this.list[position])
            }
        }
    }

    fun itemClickListener(l: (Product) -> Unit) {
        listener = l
    }


    override fun getItemCount(): Int {
        return this.list.size
    }


    inner class DiffUtilHomeAdapter(
        private val oldList: List<Product>,
        private val newList: List<Product>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            newList[newItemPosition]._id == oldList[oldItemPosition]._id




        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            newList[newItemPosition] == oldList[oldItemPosition]
    }

}