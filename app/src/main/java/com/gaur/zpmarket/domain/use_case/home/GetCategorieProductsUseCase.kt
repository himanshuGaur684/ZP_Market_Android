package com.gaur.zpmarket.domain.use_case.home

import com.gaur.zpmarket.presentation.home.CustomerHomeRepository
import javax.inject.Inject

class GetCategorieProductsUseCase @Inject
constructor(private val customerHomeRepository: CustomerHomeRepository) {


    fun invoke(id: String) = customerHomeRepository.getParticularCategoriesProductList(id)

}