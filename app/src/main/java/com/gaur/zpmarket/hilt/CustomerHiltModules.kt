package com.gaur.zpmarket.hilt

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.gaur.zpmarket.data.repository.*
import com.gaur.zpmarket.domain.repository.ProductSearchRepository
import com.gaur.zpmarket.domain.repository.ProfileRepository
import com.gaur.zpmarket.domain.repository.RazorPayPaymentRepository
import com.gaur.zpmarket.local.room.ZPMarketDao
import com.gaur.zpmarket.local.ui.cart.CartRepository
import com.gaur.zpmarket.local.ui.product_details.ProductDetailsRepository
import com.gaur.zpmarket.local.ui.reviews.CustomerReviewRepository
import com.gaur.zpmarket.presentation.auth.CustomerAuthenticationRepository
import com.gaur.zpmarket.presentation.home.CustomerHomeRepository
import com.gaur.zpmarket.presentation.profile.CustomerProfileRepository
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
        dataSourcesInterface: DataSourcesInterface,
        sharedPreferences: SharedPreferences
    ): CartRepository {
        return CartRepository(customerRetrofitInterface, dataSourcesInterface, sharedPreferences)
    }


    @Provides
    fun provideProductDetailsRepository(dataSourcesInterface: DataSourcesInterface): ProductDetailsRepository {
        return ProductDetailsRepository(dataSourcesInterface)
    }


    @Provides
    fun provideAuthRepositoryImpl(customerRetrofitInterface: CustomerRetrofitInterface): com.gaur.zpmarket.domain.repository.AuthRepository {
        return com.gaur.zpmarket.data.repository.AuthRepositoryImpl(customerRetrofitInterface)
    }


    @Provides
    fun provideCustomerHomeRepositoryImpl(
        customerRetrofitInterface: CustomerRetrofitInterface,
        dataSourcesInterface: DataSourcesInterface,
        dao: ZPMarketDao
    ): com.gaur.zpmarket.domain.repository.CustomerHomeRepository {
        return CustomerHomeRepositoryImpl(customerRetrofitInterface, dataSourcesInterface, dao)
    }


    @Provides
    fun provideProfileRepositoryImpl(customerRetrofitInterface: CustomerRetrofitInterface): ProfileRepository {
        return ProfileRepositoryImpl(customerRetrofitInterface)
    }

    @Provides
    fun provideRazorPayPaymentRepository(customerRetrofitInterface: CustomerRetrofitInterface): RazorPayPaymentRepository {
        return RazorPayPaymentRepositoryImpl(customerRetrofitInterface)
    }

    @Provides
    fun provideCartRepository(
        dataSourcesInterface: DataSourcesInterface,
        sharedPreferences: SharedPreferences,
        customerRetrofitInterface: CustomerRetrofitInterface
    ): com.gaur.zpmarket.domain.repository.CartRepository {
        return CartRepositoryImpl(
            dataSourcesInterface,
            sharedPreferences,
            customerRetrofitInterface
        )
    }


    @Provides
    fun provideSearchRepository(customerRetrofitInterface: CustomerRetrofitInterface): ProductSearchRepository {
        return ProductSearchRepositoryImpl(customerRetrofitInterface)
    }


    @Provides
    fun provideOrderRepository(customerRetrofitInterface: CustomerRetrofitInterface): com.gaur.zpmarket.domain.repository.OrderRepository {
        return OrderRepositoryImpl(customerRetrofitInterface)
    }


}
