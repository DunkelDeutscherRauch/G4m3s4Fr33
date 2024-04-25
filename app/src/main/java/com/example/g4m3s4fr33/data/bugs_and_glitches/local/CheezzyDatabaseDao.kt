package com.example.g4m3s4fr33.data.bugs_and_glitches.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.g4m3s4fr33.data.model.user.NoLifer

@Dao
interface CheezzyDatabaseDao {

    @Query("SELECT * FROM kek_table")
    fun getUser(): LiveData<NoLifer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsetUser(noLifer: NoLifer)

    @Query("UPDATE kek_table SET name= :name WHERE ID = 0")
    suspend fun updateUserName(name: String)

    @Query("UPDATE kek_table SET userImage= :userImage WHERE ID = 0")
    suspend fun updateUserImage(userImage: String)

    @Query("SELECT gameId FROM rage_quit_table WHERE userId = 0")
    fun getFavGames(): LiveData<List<Int>>

    @Query("INSERT INTO rage_quit_table VALUES (:gameId,0)")
    suspend fun addFavGame(gameId: Int)

}