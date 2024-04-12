package com.example.g4m3s4fr33.data.local.user

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
    fun upsetUser(noLifer: NoLifer)



}