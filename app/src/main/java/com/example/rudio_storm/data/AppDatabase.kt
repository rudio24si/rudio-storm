package com.example.rudio_storm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rudio_storm.data.api.KamarDao
import com.example.rudio_storm.data.api.NoteDao
import com.example.rudio_storm.data.model.KamarEntity
import com.example.rudio_storm.data.model.NoteEntity

@Database(entities = [NoteEntity::class, KamarEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun kamarDao(): KamarDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
        }
    }
}