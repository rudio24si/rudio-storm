package com.example.rudio_storm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kamar")
data class KamarEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: String,
    val imageUrl: String
)