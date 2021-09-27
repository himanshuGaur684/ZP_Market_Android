package com.gaur.zpmarket.domain.use_case.cart

import com.gaur.zpmarket.common.Resource
import com.gaur.zpmarket.domain.repository.CartRepository
import com.gaur.zpmarket.remote.response_customer.cart.CartPostDTO
import com.gaur.zpmarket.remote.response_seller.ServerMessageDTO
import com.gaur.zpmarket.utils.ErrorBody
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostCartUseCase @Inject constructor(private val cartRepository: CartRepository) {


    operator fun invoke(postBody: CartPostDTO): Flow<Resource<ServerMessageDTO>> =
        flow {
            try {
                emit(Resource.Loading())
                val response = cartRepository.postCart(postBody)
                if (response.isSuccessful) {
                    emit(Resource.Success(data = response.body()!!))
                } else {
                    val e = response.errorBody()?.string()
                    val message = Gson().fromJson<ErrorBody>(e, ErrorBody::class.java)
                    emit(Resource.Error(message = message.message))
                }

            } catch (e: HttpException) {
                emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = e.localizedMessage ?: "Check out Internet Connection"
                    )
                )
            } catch (e: Exception) {

            }
        }


}