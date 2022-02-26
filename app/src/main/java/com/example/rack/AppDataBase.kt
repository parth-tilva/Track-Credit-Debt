package com.example.rack

import android.content.Context
import androidx.room.*

@Database(entities = [Friend::class],version = 1, exportSchema = false)
@TypeConverters(FriendTypeConverter::class)
abstract class AppDataBase:RoomDatabase() {
    abstract fun friendDao(): FriendDao

    companion object {

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}