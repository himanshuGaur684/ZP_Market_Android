package com.gaur.zpmarket.local.ui.auth

import com.gaur.zpmarket.remote.response_seller.login.SellerLoginObject
import com.gaur.zpmarket.remote.response_seller.refresh_token.RefreshTokenBody
import com.gaur.zpmarket.remote.response_seller.refresh_token.RefreshTokenResponse
import com.gaur.zpmarket.remote.response_seller.register.SellerRegistrationObject
import com.gaur.zpmarket.remote.response_seller.register.SellerRegistrationResponseObject
import com.gaur.zpmarket.utils.Result

interface AuthRepository {

    suspend fun sellerRegistration(sellerRegistrationObject: SellerRegistrationObject): Result<SellerRegistrationResponseObject>

    suspend fun sellerLogin(sellerLoginObject: SellerLoginObject): Result<SellerRegistrationResponseObject>

    suspend fun refreshToken(refreshTokenBody: RefreshTokenBody): Result<RefreshTokenResponse>
}
