package com.gaur.zpmarket.local.ui.auth.login

import android.support.test.filters.SmallTest
import com.gaur.zpmarket.local.ui.auth.AuthUtils
import org.junit.Assert.assertEquals
import org.junit.Test

@SmallTest
class SellerLoginFragmentTest {


    @Test
    fun `email is Empty`() {
        assertEquals(
            AuthUtils.sellerLoginValidator("", "Himanshu1999@"),
            "Email field must be not empty"
        )
    }

    @Test
    fun `password is Empty`() {
        assertEquals(
            AuthUtils.sellerLoginValidator("himan@gmail.com", ""),
            "Password field must be not empty"
        )
    }

    @Test
    fun `email is not valid`() {
        assertEquals(
            AuthUtils.sellerLoginValidator("himangmail.com", "jklfjkaljfakl"),
            "Please enter valid Email"
        )
    }

    @Test
    fun `password length is less than 8`() {
        assertEquals(
            AuthUtils.sellerLoginValidator("him@gmail.com", "kjljf"),
            "Password Length must be greater than 8"
        )
    }

}