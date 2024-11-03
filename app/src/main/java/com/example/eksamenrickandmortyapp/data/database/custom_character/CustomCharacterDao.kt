package com.example.eksamenrickandmortyapp.data.database.custom_character

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eksamenrickandmortyapp.data.CustomCharacter

@Dao
interface CustomCharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomCharacter(customCharacter: CustomCharacter)

    @Query("SELECT * FROM custom_character ORDER BY id ASC")
    suspend fun getAllCustomCharacters(): List<CustomCharacter>

}