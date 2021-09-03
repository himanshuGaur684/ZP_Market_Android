package com.gaur.zpmarket.utils

import android.app.Activity
import android.content.Context
import android.location.LocationManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.gaur.zpmarket.SellerContainerActivity
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

// hide and show keyboard
fun View.hideKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun EditText.showKeyBoard() {
    this.requestFocus()
    this.post {
        val keyboard: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.showSoftInput(this, 0)
    }
}

// make toast
fun Context.makeToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

// make Snackbar
fun View.makeSnack(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

// check location is enabled or not by user
fun isLocationEnabled(activity: Activity): Boolean {
    val locationManager: LocationManager =
        activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER
    )
}

fun String.makeToRequestBody(): RequestBody {
    return this.toRequestBody("text/plain".toMediaTypeOrNull())
}

fun bottomNavigationVisibilityGone(activity: Activity) {
    (activity as SellerContainerActivity).bottomNavigationView.visibility = View.GONE
}

fun bottomNavigationVisibilityVisible(activity: Activity) {
    (activity as SellerContainerActivity).bottomNavigationView.visibility = View.VISIBLE
}
