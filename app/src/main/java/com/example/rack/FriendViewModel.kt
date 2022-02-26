package com.example.rack

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
const val TAG = "FriendViewModel"
class FriendViewModel(private val itemDao: FriendDao): ViewModel() {
    val allFriend :LiveData<List<Friend>> = itemDao.getAll().asLiveData()
//    var _friend : LiveData<Friend> =itemDao.getFriendByName(18).asLiveData()
    fun GetFriendById(id: Int): Flow<Friend> = itemDao.getFriendById(id)

    fun getLiveFriend(id:Int): LiveData<Friend> = itemDao.getLiveFriend(id)


    private fun insert(friend: Friend){
        viewModelScope.launch{
            itemDao.insert(friend)
        }
        Log.d(TAG ,"Insert called in viewModel")
    }

//     fun retriveFriend(id: Int): Flow<Friend> {
//
//         val fff = itemDao.getFriendByName(id))
//        return fff
//    }

    private fun getFriend(name:String):Friend{
        return Friend(
            fiendName = name
        )
    }

    fun addNewFriend(name:String ){
        val newFriend = getFriend(name)
        insert(newFriend)
    }

    fun addDebt(id:Int,friend: Friend,number:String, money: Money):Friend{
        val temp = mutableListOf<Money>()
        temp.addAll(friend.listOfMoney)
        temp.add(money)
        val list:List<Money> = temp
        val num = number.toInt()
        val total = friend.total + num
        friend.listOfMoney = list
        friend.total = total
        viewModelScope.launch {
            itemDao.update(friend)
        }
        return friend
    }
//
//    fun removeDebt(str: String,fri: Friend):Friend{
//        val list = fri.listOfMoney
//        list.remove(str)
//        val num = str.toInt()
//        val total = fri.total - num
//        fri.listOfMoney = list
//        fri.total = total
//        viewModelScope.launch {
//            itemDao.update(fri)
//        }
//        return fri
//    }
//
//    fun deleteFriend(item:Friend){
//        viewModelScope.launch {
//            itemDao.delete(item)
//        }
//    }

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