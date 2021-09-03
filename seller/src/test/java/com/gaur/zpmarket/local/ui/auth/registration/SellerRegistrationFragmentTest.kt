package com.gaur.zpmarket.local.ui.auth.registration

import android.service.autofill.Validators.not
import android.support.test.filters.SmallTest
import com.gaur.zpmarket.local.ui.auth.AuthUtils
import junit.framework.Assert.assertEquals


import org.junit.Test
import javax.xml.validation.Validator

@SmallTest
class SellerRegistrationFragmentTest {


    @Test
    fun `enter empty fields`() {
        assertEquals(AuthUtils.sellerRegistrationValidator(
            "",
            "him@gmail.com",
            "9838883095",
            "Himanshu@1999",
            "Himanshu@1999"
        ),"Name field must be not empty")
    }

    @Test
    fun `enter non valid email fields`() {
        assertEquals(AuthUtils.sellerRegistrationValidator(
            "HImanshu",
            "himgmail.com",
            "9838883095",
            "Himanshu@1999",
            "Himanshu@1999"
        ),"Please enter valid Email")
    }

    @Test
    fun `enter mobile number less than 10 numbers fields`() {
        assertEquals(AuthUtils.sellerRegistrationValidator(
            "HImanshu",
            "him@gmail.com",
            "983888309",
            "Himanshu@1999",
            "Himanshu@1999"
        ),"Mobile Number length must be 10")
    }

    @Test
    fun `enter mobile number more than 10 numbers fields`() {
        assertEquals(AuthUtils.sellerRegistrationValidator(
            "HImanshu",
            "him@gmail.com",
            "98388830955",
            "Himanshu@1999",
            "Himanshu@1999"
        ),"Mobile Number length must be 10")
    }

    @Test
    fun `password is not matching`() {
        assertEquals(AuthUtils.sellerRegistrationValidator(
            "HImanshu",
            "him@gmail.com",
            "9838883095",
            "Himanshu@199",
            "Himanshu@1999"
        ),"Password and Confirm Password must be same")
    }


    @Test
    fun `enter password length greater than 8 charecotors fields`() {

        assertEquals(AuthUtils.sellerRegistrationValidator(
            "HImanshu",
            "him@gmail.com",
            "9838883095",
            "Himan",
            "Himan"
        ),"Password Length must be greater than 8")
    }

}