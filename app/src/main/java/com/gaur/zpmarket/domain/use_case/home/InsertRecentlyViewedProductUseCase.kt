package com.gaur.zpmarket.domain.use_case.home

import com.gaur.zpmarket.domain.repository.CustomerHomeRepository
import com.gaur.zpmarket.remote.response_customer.Product
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertRecentlyViewedProductUseCase @Inject constructor(private val customerHomeRepository: CustomerHomeRepository) {


    operator fun invoke(product: Product) = flow<Any> {
        customerHomeRepository.insertRecentlyViewedProduct(product)
    }


}