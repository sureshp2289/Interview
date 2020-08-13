package com.example.interview.task.datamodel

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.interview.task.database.BaseConvertor
import com.google.gson.annotations.SerializedName


@Entity(tableName = "datalist")
@TypeConverters(BaseConvertor::class)
class MainModel {
    @PrimaryKey()
    public var id=1
    @SerializedName("title")
    public  var title: String? = null
    @SerializedName("rows")
    public var rows: List<ListModel>? = null

}