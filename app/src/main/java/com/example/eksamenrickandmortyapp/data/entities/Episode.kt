package com.example.eksamenrickandmortyapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.eksamenrickandmortyapp.data.database.character.CharacterTypeConverter

@Entity(tableName = "episode")
@TypeConverters(CharacterTypeConverter::class)
data class Episode(
    @PrimaryKey
    val id: Int,
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)