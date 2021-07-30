package com.example.breakingbad.persistence

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

//Room does not provide abstracts for easily dealing with non primative types, so serialization to string is needed
class StringListTypeConverter {
        companion object {
            var gson = Gson()

            @TypeConverter
            @JvmStatic
            fun stringToStringList(data: String?): List<String> {
                return data?.run {
                    val listType: Type =
                        object : TypeToken<List<String?>?>() {}.type
                        gson.fromJson<List<String>>(data, listType)
                } ?: listOf()
            }

            @TypeConverter
            @JvmStatic
            fun stringListToString(stringList: List<String?>?): String = gson.toJson(stringList)

            @TypeConverter
            @JvmStatic
            fun stringToIntList(data: String?): List<Int> {
                return data?.run {
                    val listType: Type =
                        object : TypeToken<List<Int?>?>() {}.type
                    gson.fromJson<List<Int>>(data, listType)
                } ?: listOf()

            }

            @TypeConverter
            @JvmStatic
            fun intListToString(intList: List<Int?>?): String = gson.toJson(intList)
        }
}
