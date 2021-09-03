package com.gaur.zpmarket.local.ui.auth

import java.util.regex.Matcher
import java.util.regex.Pattern

object AuthUtils {

    const val EMAIL_NOT_VALID = "Enter valid Email"
    const val EMPTY_NAME = "Please enter name"
    const val MOBILE_NUMBER = "Please enter Mobile Number"
    const val NOT_MATCH_PASSWORD = "Password not matches correctly"

    fun isEmailValid(email: CharSequence): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun sellerRegistrationValidator(
        name: String,
        email: String,
        mobileNumber: String,
        password: String,
        confirmPassword: String
    ): String {

        if (name.isEmpty()) {
            return "Name field must be not empty"
        }
        if (email.isEmpty()) {
            return "Email field must be not empty"
        }
        if (mobileNumber.isEmpty()) {
            return "Mobile Number field must be not empty"
        }
        if (password.isEmpty()) {
            return "Password field must be not empty"
        }

        if (!isEmailValid(email)) {
            return "Please enter valid Email"
        }
        if (mobileNumber.length < 10) {
            return "Mobile Number length must be 10"
        }
        if (mobileNumber.length > 10) {
            return "Mobile Number length must be 10"
        }
        if (password.length < 8) {
            return "Password Length must be greater than 8"
        }
        if (password != confirmPassword) {
            return "Password and Confirm Password must be same"
        }
        return ""
    }

    fun sellerLoginValidator(email: String, password: String): String {
        if (email.isEmpty()) return "Email field must be not empty"
        if (password.isEmpty()) return "Password field must be not empty"
        if (!isEmailValid(email)) return "Please enter valid Email"
        if (password.length < 8) {
            return "Password Length must be greater than 8"
        }
        return ""
    }
}
