package com.gaur.zpmarket.utils

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView


@BindingAdapter("loadImages")
fun loadImages(view: RoundedImageView, url: String?) {
    url?.let {
        Glide.with(view).load(url).into(view)
    }
}


@BindingAdapter("time")
fun setTime(view: TextView, time: String?) {
    time?.let {
        Log.d("TAG", "setTime: ${time}")
        val l = time.split("GMT")
        val t= l[0].split(" ")
        view.text = "${t[0]}, ${t[1]} ${t[2]}, ${t[3]}"
    }


}