package com.gaur.zpmarket.domain.use_case.auth

import com.gaur.zpmarket.common.Resource
import com.gaur.zpmarket.domain.repository.AuthRepository
import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegisterDTO
import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegistrationResponse
import com.gaur.zpmarket.utils.ErrorBody
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class CustomerRegistrationUseCase @Inject constructor(private val authRepository: AuthRepository) {


    operator fun invoke(customerRegisterDTO: CustomerRegisterDTO): Flow<Resource<CustomerRegistrationResponse>> =
        flow {
            try {
                emit(Resource.Loading())
                val response = authRepository.registration(customerRegisterDTO)
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