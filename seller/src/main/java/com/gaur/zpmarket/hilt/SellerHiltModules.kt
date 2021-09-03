package com.gaur.zpmarket.hilt

import android.content.SharedPreferences
import com.gaur.zpmarket.local.room.ZPMarketDao
import com.gaur.zpmarket.local.ui.auth.AuthRepository
import com.gaur.zpmarket.local.ui.auth.AuthRepositoryImpl
import com.gaur.zpmarket.local.ui.product.add.AddProductRepository
import com.gaur.zpmarket.local.ui.product.show_product.ShowProductRepositoryImpl
import com.gaur.zpmarket.remote.DataSourcesInterface
import com.gaur.zpmarket.remote.SellerRetrofitInterface
import com.gaur.zpmarket.utils.SellerConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SellerHiltModules {


    @Singleton
    @Provides
    fun provideSellerRetrofitInterface(): SellerRetrofitInterface {

        val httpClient = OkHttpClient.Builder().callTimeout(2,TimeUnit.MINUTES).readTimeout(1,TimeUnit.MINUTES).writeTimeout(1,TimeUnit.MINUTES).build()

        return Retrofit.Builder().baseUrl(SellerConstants.BASE_URL)
            .client(httpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(SellerRetrofitInterface::class.java)
    }

    @Provides
    fun provideAuthRepository(sellerRetrofitInterface: SellerRetrofitInterface): AuthRepository {
        return AuthRepositoryImpl(sellerRetrofitInterface)
    }


    @Provides
    fun provideShowProductsRepository(
        retrofitInterface: SellerRetrofitInterface,
        dataSourcesInterface: DataSourcesInterface,
        databaseDAO: ZPMarketDao
    ): ShowProductRepositoryImpl {
        return ShowProductRepositoryImpl(retrofitInterface, dataSourcesInterface, databaseDAO)
    }

    @Provides
    fun provideAddProductsRepository(
        zpMarketDao: ZPMarketDao,
        sellerRetrofitInterface: SellerRetrofitInterface
    ,sharedPreferences: SharedPreferences
    ): AddProductRepository {
        return AddProductRepository(zpMarketDao, sellerRetrofitInterface,sharedPreferences)
    }


}