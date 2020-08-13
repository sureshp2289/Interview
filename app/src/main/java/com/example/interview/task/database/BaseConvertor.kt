package com.example.interview.task.database

import androidx.room.TypeConverter
import com.example.interview.task.datamodel.ListModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class BaseConvertor {
    @TypeConverter
    fun stringToMeasurements(json: String?): List<ListModel?>? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<ListModel?>?>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun measurementsToString(list: List<ListModel?>?): String? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<ListModel?>?>() {}.type
        return gson.toJson(list, type)
    }
}