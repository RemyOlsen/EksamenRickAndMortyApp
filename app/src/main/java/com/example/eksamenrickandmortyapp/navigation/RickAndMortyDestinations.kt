package com.example.eksamenrickandmortyapp.navigation

import kotlinx.serialization.Serializable


@Serializable
object FrontPage

@Serializable
object CharacterList

@Serializable
data class CharacterDetails(
    val characterId: Int
)

@Serializable
object CreateCharacter

@Serializable
object CustomCharacterList

@Serializable
object EpisodeList

@Serializable
data class EpisodeDetails(
    val episodeId: Int
)

