package com.example.eksamenrickandmortyapp.data.database.character

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eksamenrickandmortyapp.data.entities.Character

@Dao
interface CharacterDao {

    // Get all characters
    @Query("SELECT * FROM character ORDER BY id ASC")
    suspend fun getCharacters(): List<Character>

    // Get character by Id
    @Query("SELECT * FROM character WHERE :characterId = id")
    suspend fun getCharacterById(characterId: Int): Character?

    // Get multiple characters by the IDs
    @Query("SELECT * FROM character WHERE id IN (:ids)")
    suspend fun getCharactersByIds(ids: List<Int>): List<Character>

    // Search for characters by name
    // (Old Search function, I search directly from the viewModel now when it gets characters for optimization)
    @Query("SELECT * FROM character WHERE name LIKE '%' || :name || '%' COLLATE NOCASE")
    suspend fun getCharactersByName(name: String): List<Character>

    // Insert characters into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)
}