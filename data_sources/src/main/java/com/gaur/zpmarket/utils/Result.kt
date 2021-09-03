package com.gaur.zpmarket.utils

class Result<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> loading(message: String?): Result<T> {
            return Result(Status.LOADING, null, message)
        }

        fun <T> error(message: String?): Result<T> {
            return Result(Status.ERROR, null, message)
        }

        fun <T> empty(): Result<T> {
            return Result(Status.EMPTY, null, null)
        }
    }
}

enum class Status {
    SUCCESS,
    LOADING,
    ERROR,
    EMPTY
}
