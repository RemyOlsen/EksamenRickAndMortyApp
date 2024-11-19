package com.example.eksamenrickandmortyapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.eksamenrickandmortyapp.data.database.character.CharacterTypeConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "episode")
@TypeConverters(CharacterTypeConverter::class)
data class Episode(
    @PrimaryKey
    val id: Int,
    val name: String,
    @SerializedName("air_date") // Had to serialize this because it didn't match what it was in the API
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)

data class EpisodeListResponse(
    val info: EpisodeMeta,
    val results: List<Episode>
)

data class EpisodeMeta (
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)