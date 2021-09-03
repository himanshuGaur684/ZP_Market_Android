package com.gaur.zpmarket.utils

import android.app.Activity
import android.view.View
import com.gaur.zpmarket.ContainerActivity

fun customerBottomNavigationViewVisibilityGone(activity: Activity) {
    (activity as ContainerActivity).binding.customersBottomNavigationView.visibility = View.GONE
}

fun customerBottomNavigationViewVisibilityVisible(activity: Activity) {
    (activity as ContainerActivity).binding.customersBottomNavigationView.visibility = View.VISIBLE
}