package com.example.eksamenrickandmortyapp.data.database.episode

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eksamenrickandmortyapp.data.entities.Episode

@Dao
interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<Episode>)

    @Query("SELECT * FROM episode")
    suspend fun getAllEpisodes(): List<Episode>

    @Query("SELECT * FROM episode WHERE id = :id")
    suspend fun getEpisodeById(id: Int): Episode?


}