package com.example.eksamenrickandmortyapp.data

import android.util.Log
import com.example.eksamenrickandmortyapp.data.entities.Episode
import okio.IOException

object EpisodeRepository {
    private val _apiService = ApiClient.apiService
    private val _episodeDao by lazy { CharacterRepository.getAppDatabase().episodeDao() }

    suspend fun getEpisodesFromApi(page: Int): List<Episode> {
        Log.d("EpisodeRepository", "Getting episodes from API from page: $page")
        try {

            val response = _apiService.getAllEpisodes(page)

            if (response.isSuccessful) {

                Log.d("EpisodeRepository", "Updating episodes from API from page: $page")

                val episodes = response.body()?.results ?: emptyList()

                // Storing the episodes in the database
                _episodeDao.insertEpisodes(episodes)

                Log.d("EpisodeRepository", "Successfully updated episodes from page: $page from the API")

                // Returning only the new episodes
                return episodes
            } else {
                throw Exception("Response was not successful")
            }
        } catch (e: IOException) {
            Log.e("EpisodeRepository", "Network error, unable to connect to the API, getting episodes from database", e)
            return emptyList()
        } catch (e: Exception) {
            Log.e("EpisodeRepository", "Failed to get list of episodes, getting episodes from database", e)
            return emptyList()
        }
    }

    // getting the total page count from the meta data
    suspend fun getTotalPagesCount(): Int? {
        Log.d("EpisodeRepository", "Getting total episode page count")
        try {
            val response = _apiService.getAllEpisodes(1)
            return if (response.isSuccessful) {
                response.body()?.info?.pages
            } else {
                null
            }
        } catch(e: Exception) {
            Log.e("EpisodeRepository", "Failed to get total page count", e)
            return null
        }
    }

    // Getting total episodes from the meta data
    suspend fun getTotalEpisodeCount(): Int? {
        Log.d("EpisodeRepository", "Getting total episode count")
        try {
            val response = _apiService.getAllEpisodes(1)
            return if (response.isSuccessful) {
                response.body()?.info?.count
            } else {
                null
            }

        } catch(e: Exception) {
            Log.e("EpisodeRepository", "Failed to get episode count", e)
            return null
        }
    }


    suspend fun getCachedEpisodes(): List<Episode> {
        Log.d("EpisodeRepository", "Getting episodes from database")
        return try {
            _episodeDao.getEpisodes()
        } catch (e: Exception) {
            Log.e("CharacterRepository", "Error fetching cached characters", e)
            emptyList()
        }

    }

    suspend fun getEpisodeById(episodeId: Int): Episode? {
        return _episodeDao.getEpisodeById(episodeId)
    }

    suspend fun getEpisodesByIds(episodeIds: List<Int>): List<Episode> {
        return try {
            _episodeDao.getEpisodesByIds(episodeIds)
        } catch (e: Exception) {
            Log.e("EpisodeRepository", "Error fetching episodes by IDs", e)
            emptyList()
        }
    }
}