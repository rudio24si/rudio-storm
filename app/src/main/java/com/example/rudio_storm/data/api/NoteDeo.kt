package com.example.rudio_storm.data.api

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.rudio_storm.data.model.NoteEntity

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: NoteEntity)

    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    suspend fun getAll(): List<NoteEntity>

    @Delete
    suspend fun delete(note: NoteEntity)
}