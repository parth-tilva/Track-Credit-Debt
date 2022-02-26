package com.example.rack

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "friend")
data class Friend (
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    @ColumnInfo(name = "name") val fiendName:String,
    @ColumnInfo(name = "total") var total:Int=0,
    @ColumnInfo(name = "DebtList") var list:ArrayList<String> = arrayListOf()
    )

class FriendTypeConverter{
    @TypeConverter
    fun fromString(value:String?):ArrayList<String>{
        val listType = object : TypeToken<ArrayList<String>>() {}.type

        return Gson().fromJson(value,listType)
    }

    @TypeConverter
    fun fromArrayList(list:ArrayList<String>):String{
        return Gson().toJson(list)
    }
}