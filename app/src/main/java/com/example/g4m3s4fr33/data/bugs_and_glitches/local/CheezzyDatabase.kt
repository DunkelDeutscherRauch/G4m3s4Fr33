package com.example.g4m3s4fr33.data.bugs_and_glitches.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.g4m3s4fr33.data.model.user.NoLifer
import com.example.g4m3s4fr33.data.model.user.RageQuit

@Database(entities = [NoLifer::class, RageQuit::class], version = 1)
abstract class CheezzyDatabase : RoomDatabase() {
    abstract val noLiferDao: CheezzyDatabaseDao

    companion object {
        private lateinit var INSTANCE: CheezzyDatabase

        fun getDatabase(context: Context): CheezzyDatabase {

            synchronized(this) {

                if (!this::INSTANCE.isInitialized) {

                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CheezzyDatabase::class.java,
                        "kek_database"

                    )
                        .fallbackToDestructiveMigration() // just for the moment - see ToDo.kt
                        .build()
                }
                return INSTANCE

            }
        }
    }
}