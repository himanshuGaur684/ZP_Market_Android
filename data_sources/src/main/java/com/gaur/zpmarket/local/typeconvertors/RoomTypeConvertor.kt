package com.gaur.zpmarket.local.typeconvertors

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomTypeConvertor {

    @TypeConverter
    fun imageListToString(s: List<String>): String {
        return Gson().toJson(s)
    }

    @TypeConverter
    fun stringToImageList(l: String): ArrayList<String> {
        return Gson().fromJson(l, object : TypeToken<ArrayList<String>>() {}.type)
    }
}
