package com.example.g4m3s4fr33.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.g4m3s4fr33.data.model.NoLifer

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



}