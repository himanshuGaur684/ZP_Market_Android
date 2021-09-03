package com.gaur.zpmarket.hilt

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.gaur.zpmarket.local.room.ZPMarketDao
import com.gaur.zpmarket.local.ui.auth.CustomerAuthenticationRepository
import com.gaur.zpmarket.local.ui.cart.CartRepository
import com.gaur.zpmarket.local.ui.home.CustomerHomeRepository
import com.gaur.zpmarket.local.ui.orders.OrderRepository
import com.gaur.zpmarket.local.ui.product_details.ProductDetailsRepository
import com.gaur.zpmarket.local.ui.product_details.payment.PaymentRepository
import com.gaur.zpmarket.local.ui.profile.CustomerProfileRepository
import com.gaur.zpmarket.local.ui.reviews.CustomerReviewRepository
import com.gaur.zpmarket.local.ui.search.SearchRepository
import com.gaur.zpmarket.remote.CustomerRetrofitInterface
import com.gaur.zpmarket.remote.DataSourcesInterface
import com.gaur.zpmarket.utils.CustomerConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CustomerHiltModules {


    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("pref", MODE_PRIVATE)
    }


    @Provides
    @Singleton
    fun provideCustomerRetrofitInterface(): CustomerRetrofitInterface {
        val client = OkHttpClient.Builder().callTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(5, TimeUnit.SECONDS).readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES).build()
        return Retrofit.Builder().baseUrl(CustomerConstants.BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(CustomerRetrofitInterface::class.java)
    }


    @Provides
    fun provideCustomerHomeRepository(
        customerRetrofitInterface: CustomerRetrofitInterface,
        dataSourcesInterface: DataSourcesInterface,
        zpMarketDao: ZPMarketDao
    ): CustomerHomeRepository {
        return CustomerHomeRepository(customerRetrofitInterface, dataSourcesInterface, zpMarketDao)
    }


    @Provides
    fun provideCustomerRegistrationRepository(customerRetrofitInterface: CustomerRetrofitInterface): CustomerAuthenticationRepository {
        return CustomerAuthenticationRepository(customerRetrofitInterface)
    }

    @Provides
    fun provideCustomerProfile(customerRetrofitInterface: CustomerRetrofitInterface): CustomerProfileRepository {
        return CustomerProfileRepository(customerRetrofitInterface)
    }


    @Provides
    fun provideCustomerReviewRepository(dataSourcesInterface: DataSourcesInterface): CustomerReviewRepository {
        return CustomerReviewRepository(dataSourcesInterface)
    }


    @Provides
    fun provideCustomerCartRepository(
        customerRetrofitInterface: CustomerRetrofitInterface,
        dataSourcesInterface: DataSourcesInterface, sharedPreferences: SharedPreferences
    ): CartRepository {
        return CartRepository(customerRetrofitInterface, dataSourcesInterface, sharedPreferences)
    }


    @Provides
    fun provideSearchRepository(customerRetrofitInterface: CustomerRetrofitInterface): SearchRepository {
        return SearchRepository(customerRetrofitInterface)
    }

    @Provides
    fun providePaymentRepository(customerRetrofitInterface: CustomerRetrofitInterface): PaymentRepository {
        return PaymentRepository(customerRetrofitInterface)
    }

    @Provides
    fun provideProductDetailsRepository(dataSourcesInterface: DataSourcesInterface): ProductDetailsRepository {
        return ProductDetailsRepository(dataSourcesInterface)
    }

    @Provides
    fun provideCustomerOrderRepository(
        sharedPreferences: SharedPreferences,
        customerRetrofitInterface: CustomerRetrofitInterface
    ): OrderRepository {
        return OrderRepository(sharedPreferences, customerRetrofitInterface)
    }

}