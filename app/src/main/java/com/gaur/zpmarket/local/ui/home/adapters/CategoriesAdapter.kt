package com.gaur.zpmarket.local.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gaur.zpmarket.databinding.ViewHolderCategoriesBinding
import com.gaur.zpmarket.remote.response_customer.category.Category

class CategoriesAdapter() : RecyclerView.Adapter<CategoriesAdapter.MyViewHolder>() {

    private var list = listOf<Category>()
    private var listener: ((Category) -> Unit)? = null

    fun setContentList(list: List<Category>) {
        if (this.list.isEmpty()) {
            this.list = list
            notifyDataSetChanged()
            return
        }
        val d = DiffUtilHomeAdapter(oldList = this.list, newList = list)
        val diffUtilResult = DiffUtil.calculateDiff(d)
        diffUtilResult.dispatchUpdatesTo(this)
    }

    inner class MyViewHolder(val viewDataBinding: ViewHolderCategoriesBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesAdapter.MyViewHolder {
        val binding =
            ViewHolderCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    fun itemClickListener(l: (Category) -> Unit) {
        listener = l
    }

    override fun onBindViewHolder(holder: CategoriesAdapter.MyViewHolder, position: Int) {
        // holder.viewDataBinding.image= this.list[position].imageUrl
        holder.viewDataBinding.text = this.list[position].name
        holder.viewDataBinding.root.setOnClickListener {
            listener?.let {
                it(this.list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    inner class DiffUtilHomeAdapter(
        private val oldList: List<Category>,
        private val newList: List<Category>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            newList[newItemPosition]._id == oldList[oldItemPosition]._id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            newList[newItemPosition] == oldList[oldItemPosition]
    }
}
