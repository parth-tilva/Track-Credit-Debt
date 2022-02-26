package com.example.rack

import android.app.Application

class FriendApplication:Application() {
    val dataBase:AppDataBase by lazy {
        AppDataBase.getDatabase(this)
    }
}