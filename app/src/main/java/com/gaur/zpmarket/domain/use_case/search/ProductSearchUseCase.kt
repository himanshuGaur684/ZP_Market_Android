package com.gaur.zpmarket.domain.use_case.search

import com.gaur.zpmarket.domain.repository.ProductSearchRepository
import javax.inject.Inject

class ProductSearchUseCase @Inject constructor(private val productSearchRepository: ProductSearchRepository) {
    fun invoke(query: String) = productSearchRepository.getSearchPagingProducts(query)
}