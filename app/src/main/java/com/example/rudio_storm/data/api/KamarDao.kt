package com.example.rudio_storm.data.api

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.rudio_storm.data.model.KamarEntity

@Dao
interface KamarDao {

    @Insert
    suspend fun insert(kamar: KamarEntity)

    @Query("SELECT * FROM kamar ORDER BY id DESC")
    suspend fun getAll(): List<KamarEntity>

    @Delete
    suspend fun delete(kamar: KamarEntity)
}