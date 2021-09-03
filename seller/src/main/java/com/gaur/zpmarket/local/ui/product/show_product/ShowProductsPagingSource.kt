package com.gaur.zpmarket.local.ui.product.show_product

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gaur.zpmarket.remote.SellerRetrofitInterface
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.utils.SafeApiRequest
import java.lang.Exception

private const val STARTING_PAGE_INDEX = 1
class ShowProductsPagingSource(private val retrofitInterface: SellerRetrofitInterface):PagingSource<Int,Product>() {


    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
      return try{
           val position = params.key?: STARTING_PAGE_INDEX
           val response= SafeApiRequest.handleApiCall { retrofitInterface.getAllSellerProducts(position,10) }
           LoadResult.Page(
               response.data?.results!!,
               prevKey = if(position==0)null else position-1,
               nextKey = if(response.data!!.results.isNullOrEmpty()) null else position+1
           )
       }catch (e:Exception){
           LoadResult.Error(e)
       }
    }
}