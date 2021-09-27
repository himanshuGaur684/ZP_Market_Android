package com.gaur.zpmarket.domain.use_case.home

import com.gaur.zpmarket.remote.response_customer.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRecentlyViewedProductsUseCase @Inject constructor(private val customerHomeRepository: com.gaur.zpmarket.domain.repository.CustomerHomeRepository) {

    operator fun invoke(): Flow<List<Product>> = flow<List<Product>> {
        emit(customerHomeRepository.getLimitedRecentlyViewedProducts())
    }


}