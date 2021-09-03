package com.gaur.zpmarket.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText

@BindingAdapter("loadImages")
fun loadImage(view: ImageView, url: String?) {
    url?.let {
        Glide.with(view).load(url).into(view)
    }
}

/**    Add product discount  **/

@BindingAdapter(value = ["marketPrice", "discountedPrice"], requireAll = true)
fun findDiscount(view: TextInputEditText, marketPrice: String, discountedPrice: String) {
    if (marketPrice.isNotEmpty() && discountedPrice.isNotEmpty()) {
        val m = marketPrice.toInt()
        val d = discountedPrice.toInt()
        val discount = m.minus(d).div(m) * 100
        Log.d("TAG", "findDiscount: $discount")
        view.setText(discount.toString())
    } else {
        view.setText("")
    }
}
