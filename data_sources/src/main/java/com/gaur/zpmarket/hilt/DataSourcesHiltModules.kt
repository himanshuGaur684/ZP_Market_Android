package com.gaur.zpmarket.hilt

import android.content.Context
import com.gaur.zpmarket.local.room.ZPMarketDao
import com.gaur.zpmarket.local.room.ZPMarketLocalDatabase
import com.gaur.zpmarket.remote.DataSourcesInterface
import com.gaur.zpmarket.utils.DataSourcesConstants
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

@Module
@InstallIn(SingletonComponent::class)
object DataSourcesHiltModules {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        val client =
            OkHttpClient.Builder().callTimeout(5, TimeUnit.MINUTES).readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS).build()
        return Retrofit.Builder().client(client).addConverterFactory(GsonConverterFactory.create())
            .baseUrl(DataSourcesConstants.BASE_URL).build()
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): ZPMarketLocalDatabase {
        return ZPMarketLocalDatabase.getZPMarketDatabaseInstance(context)
    }

    @Provides
    @Singleton
    fun provideZPMarketDatabase(zpMarketLocalDatabase: ZPMarketLocalDatabase): ZPMarketDao {
        return zpMarketLocalDatabase.getZPLocalDatabaseDAO()
    }

    @Provides
    @Singleton
    fun provideDataSourcesInterface(retrofit: Retrofit): DataSourcesInterface {
        return retrofit.create(DataSourcesInterface::class.java)
    }
}
