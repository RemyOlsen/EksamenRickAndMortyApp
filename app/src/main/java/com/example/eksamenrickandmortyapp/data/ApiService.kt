package com.example.eksamenrickandmortyapp.data

import com.example.eksamenrickandmortyapp.data.entities.CharacterListResponse
import com.example.eksamenrickandmortyapp.data.entities.EpisodeListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // Get all characters by page
    @GET("character")
    suspend fun getAllCharacters(@Query("page") page: Int): Response<CharacterListResponse>

    // Get all episodes by page
    @GET("episode")
    suspend fun getAllEpisodes(@Query("page") page: Int): Response<EpisodeListResponse>


}