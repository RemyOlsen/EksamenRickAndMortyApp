package com.example.eksamenrickandmortyapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_character")
data class CustomCharacter(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val status: String = "unknown",
    val species: String = "unknown",
    val gender: String = "unknown",
    val origin: String = "unknown",
    val description: String? = null,
    val imageUrl: String = "https://rickandmortyapi.com/api/character/avatar/19.jpeg",
)