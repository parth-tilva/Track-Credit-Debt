package com.example.rack

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "friend")
data class Friend(
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    @ColumnInfo(name = "name") val fiendName:String,
    @ColumnInfo(name = "total") var total:Int=0,
    @ColumnInfo(name = "DebtList") var listOfMoney: List<Money> = listOf<Money>()
    )


data class Money(
    val strMoney: String="",
    val amount: Int=11,
    val note: String="emt note",
    val time: Long,
    val Date: String=""
)


//class FriendTypeConverter{
//    @TypeConverter
//    fun stringToList(data: String?): List<Money?>?{
//        val listType = object : TypeToken<List<Money>>() {}.type
//        return Gson().fromJson(data,listType)
//    }
//
//    @TypeConverter
//    fun listToString(someObject: List<Money>?): String? {
//        return Gson().toJson(someObject)
//    }
//}


//stackoverflow
//@TypeConverter
//fun stringToListServer(data: String?): List<ServerEntity?>? {
//    if (data == null) {
//        return Collections.emptyList()
//    }
//    val listType: Type = object :
//        TypeToken<List<ServerEntity?>?>() {}.type
//    return gson.fromJson<List<ServerEntity?>>(data, listType)
//}
//
//@TypeConverter
//fun listServerToString(someObjects: List<ServerEntity?>?): String? {
//    return gson.toJson(someObjects)
//}



//class FriendTypeConverter{  tested -> run
//    @TypeConverter
//    fun listToJson(value: List<Money>?) = Gson().toJson(value)
//
//    @TypeConverter
//    fun jsonToList(value :String) = Gson().fromJson(value,Array<Money>::class.java).toList()
//
//}






















//
//class FriendTypeConverter{
//    @TypeConverter
//    fun fromString(value:String?):ArrayList<String>{
//        val listType = object : TypeToken<ArrayList<String>>() {}.type
//        return Gson().fromJson(value,listType)
//    }
//
//    @TypeConverter
//    fun fromArrayList(list:ArrayList<String>):String{
//        return Gson().toJson(list)
//    }
//}

