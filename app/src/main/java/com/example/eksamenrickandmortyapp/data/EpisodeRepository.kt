package com.example.eksamenrickandmortyapp.data

import android.util.Log

import com.example.eksamenrickandmortyapp.data.entities.Episode

object EpisodeRepository {
    private val _episodeDao by lazy { CharacterRepository.getAppDatabase().episodeDao() }

    suspend fun insertEpisodes(episodes: List<Episode>) {
        _episodeDao.insertEpisodes(episodes)
    }

    suspend fun getAllEpisodes(): List<Episode> {
        return _episodeDao.getAllEpisodes()

    }

    suspend fun getEpisodeById(id: Int): Episode? {
        return _episodeDao.getEpisodeById(id)
    }


}