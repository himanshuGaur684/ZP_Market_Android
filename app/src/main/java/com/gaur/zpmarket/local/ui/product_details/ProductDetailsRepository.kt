package com.gaur.zpmarket.local.ui.product_details

import com.gaur.zpmarket.remote.DataSourcesInterface
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.utils.Result
import com.gaur.zpmarket.utils.SafeApiRequest

class ProductDetailsRepository(private val dataSourcesInterface: DataSourcesInterface) {

    suspend fun getSingleProduct(id: String): Result<Product> {
        return SafeApiRequest.handleApiCall { dataSourcesInterface.getSingleProduct(id) }
    }
}
