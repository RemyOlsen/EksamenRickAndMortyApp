package com.example.eksamenrickandmortyapp.data.database.character

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eksamenrickandmortyapp.data.Character

@Dao
interface CharacterDao {

    // Get all characters
    @Query("SELECT * FROM character ORDER BY id ASC")
    suspend fun getCharacters(): List<Character>

    // Get character by Id
    @Query("SELECT * FROM character WHERE :characterId = id")
    suspend fun getCharacterById(characterId: Int): Character?

    // Get the last page number
//    @Query("SELECT MAX(page) FROM Character")
//    suspend fun getLastPage(): Int?

    // Search for characters by name
    @Query("SELECT * FROM character WHERE name LIKE '%' || :name || '%' COLLATE NOCASE")
    suspend fun getCharactersByName(name: String): List<Character>

//    @Query("SELECT * FROM Character WHERE name LIKE '%rick%'")
//    suspend fun getRickCharacters(): List<Character>

//    @Query("SELECT * FROM Character ORDER BY id ASC")
//    suspend fun getCharacters(): List<Character>

    // Insert characters into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)
}