package com.gaur.zpmarket.utils

import android.util.Log
import com.google.gson.Gson
import retrofit2.Response

object SafeApiRequest {
    suspend fun <T : Any> handleApiCall(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                Log.d("TAG", "handleApiCall: ${response.body()}")
                Result(Status.SUCCESS, response.body(), null)
            } else {
                val b = response.errorBody()?.string().toString()
                Log.d("TAG", "handleApiCall: $b")
                val errorBody =
                    Gson().fromJson(b, ErrorBody::class.java)
                Log.d("TAG", "handleApiCall: $errorBody")
                Result(Status.ERROR, null, errorBody.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (e.message == "timeout") {
                return Result(Status.ERROR, null, "Timeout")
            }
            Result(Status.ERROR, null, e.message)
        }
    }
}
