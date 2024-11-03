package com.example.eksamenrickandmortyapp.navigation

import kotlinx.serialization.Serializable

@Serializable
object CharacterList

@Serializable
data class CharacterDetails(
    val characterId: Int
)

@Serializable
object CreateCharacter