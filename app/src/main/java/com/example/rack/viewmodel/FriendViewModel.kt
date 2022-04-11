package com.example.rack

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import com.example.rack.data.database.FriendDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
class FriendViewModel(private val itemDao: FriendDao): ViewModel() {


    val allFriend :LiveData<List<Friend>> = itemDao.getAll().asLiveData()

    fun getLiveFriend(id:Int): LiveData<Friend> = itemDao.getLiveFriend(id)


    private fun insert(friend: Friend){
        viewModelScope.launch{
            itemDao.insert(friend)
        }
    }


    private fun getFriend(name:String):Friend{
        return Friend(
            fiendName = name
        )
    }

    fun addNewFriend(name:String ){
        val newFriend = getFriend(name)
        insert(newFriend)
    }

    fun addMoney(friend: Friend, money: Money){
        friend.listOfMoney = friend.listOfMoney.plus(money)
        friend.total = friend.total + money.amount
        viewModelScope.launch {
            itemDao.update(friend)
        }
    }

    fun removeDebt(money: Money,friend: Friend){
        friend.listOfMoney = friend.listOfMoney.minus(money)
        friend.total = friend.total - money.amount
        viewModelScope.launch {
            itemDao.update(friend)
        }
    }

    fun deleteFriend(item:Friend){
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }

}



class FViewModelFactory(private val itemDao: FriendDao): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FriendViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return FriendViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unkown FriendViewModel class")
    }
}