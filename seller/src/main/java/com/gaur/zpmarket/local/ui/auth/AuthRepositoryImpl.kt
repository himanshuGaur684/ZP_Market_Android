package com.gaur.zpmarket.local.ui.auth

import com.gaur.zpmarket.remote.SellerRetrofitInterface
import com.gaur.zpmarket.remote.response_seller.login.SellerLoginObject
import com.gaur.zpmarket.remote.response_seller.refresh_token.RefreshTokenBody
import com.gaur.zpmarket.remote.response_seller.refresh_token.RefreshTokenResponse
import com.gaur.zpmarket.remote.response_seller.register.SellerRegistrationObject
import com.gaur.zpmarket.remote.response_seller.register.SellerRegistrationResponseObject
import com.gaur.zpmarket.utils.Result
import com.gaur.zpmarket.utils.SafeApiRequest

class AuthRepositoryImpl(private val sellerRetrofitInterface: SellerRetrofitInterface) :
    AuthRepository {

    override suspend fun sellerRegistration(sellerRegistrationObject: SellerRegistrationObject): Result<SellerRegistrationResponseObject> {
        return SafeApiRequest.handleApiCall {
            sellerRetrofitInterface.sellerRegistration(
                sellerRegistrationObject
            )
        }
    }

    override suspend fun sellerLogin(sellerLoginObject: SellerLoginObject): Result<SellerRegistrationResponseObject> {
        return SafeApiRequest.handleApiCall {
            sellerRetrofitInterface.sellerLogin(
                sellerLoginObject
            )
        }
    }

    override suspend fun refreshToken(refreshTokenBody: RefreshTokenBody): Result<RefreshTokenResponse> {
        return SafeApiRequest.handleApiCall { sellerRetrofitInterface.refreshToken(refreshTokenBody) }
    }
}
