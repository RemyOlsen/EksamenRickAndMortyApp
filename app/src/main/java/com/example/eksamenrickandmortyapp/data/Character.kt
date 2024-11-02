package com.example.eksamenrickandmortyapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.eksamenrickandmortyapp.data.database.type_converter.CharacterTypeConverter


@Entity(tableName = "character")
@TypeConverters(CharacterTypeConverter::class)
data class Character (
    @PrimaryKey
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String,
)

//@Entity(tableName = "custom_character")
//data class CustomCharacter(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int = 0,
//    val character: Character
//)


data class Origin(
    val name: String,
    val url: String
)

data class Location(
    val name: String,
    val url: String
)

data class CharacterListResponse(
    val info: Info,
    val results: List<Character>
)

data class Info (
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)