package com.example.eksamenrickandmortyapp.data.database.episode

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eksamenrickandmortyapp.data.entities.Episode

@Dao
interface EpisodeDao {

    // Get all episodes
    @Query("SELECT * FROM episode ORDER BY id ASC")
    suspend fun getEpisodes(): List<Episode>

    // Get episode by Id
    @Query("SELECT * FROM episode WHERE id = :id")
    suspend fun getEpisodeById(id: Int): Episode?

    // Get multiple episodes by the IDs
    @Query("SELECT * FROM episode WHERE id IN (:ids)")
    suspend fun getEpisodesByIds(ids: List<Int>): List<Episode>

    // Insert episodes into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<Episode>)

}