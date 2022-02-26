package com.example.rack

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
val p ="parth"
@Dao
interface FriendDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(friend: Friend)

    @Update
    suspend fun update(friend: Friend)

    @Delete
    suspend fun delete(friend: Friend)

    @Query("Select * from friend Order by id ASC")
    fun getAll(): Flow<List<Friend>>


    @Query("SELECT * FROM friend WHERE id = :id ")
    fun getFriendById(id: Int): Flow<Friend>

    @Query("Select * from friend Where id= :id")
    fun getLiveFriend(id:Int ): LiveData<Friend>

}