package com.gaur.zpmarket.local.room

import androidx.room.*
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.remote.response_customer.category.Category

@Dao
interface ZPMarketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategory(list: List<Category>)

    @Query("SELECT * FROM Category")
    suspend fun getAllCategory(): List<Category>

    /** Recently Viewed Products **/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentlyViewedProducts(products: Product)

    @Delete
    suspend fun deleteRecentlyViewedProducts(products: Product)

    @Query("SELECT * FROM Product")
    suspend fun getAllRecentlyViewedProducts(): List<Product>
}
