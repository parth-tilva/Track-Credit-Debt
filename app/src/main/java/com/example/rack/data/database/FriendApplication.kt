package com.example.rack.data.database

import android.app.Application
import com.example.rack.data.database.AppDataBase

class FriendApplication:Application() {
    val dataBase: AppDataBase by lazy {
        AppDataBase.getDatabase(this)
    }
}