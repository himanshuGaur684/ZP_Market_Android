package com.gaur.zpmarket.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gaur.zpmarket.local.typeconvertors.RoomTypeConvertor
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.remote.response_customer.category.Category

@Database(entities = [Category::class, Product::class], exportSchema = false, version = 1)
@TypeConverters(RoomTypeConvertor::class)
abstract class ZPMarketLocalDatabase() : RoomDatabase() {

    companion object {
        fun getZPMarketDatabaseInstance(context: Context): ZPMarketLocalDatabase {
            return Room.databaseBuilder(context, ZPMarketLocalDatabase::class.java, "db")
                .fallbackToDestructiveMigration().build()
        }
    }

    abstract fun getZPLocalDatabaseDAO(): ZPMarketDao
}
