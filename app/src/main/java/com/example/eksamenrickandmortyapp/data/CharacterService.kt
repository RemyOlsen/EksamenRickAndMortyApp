package com.example.eksamenrickandmortyapp.data

import com.example.eksamenrickandmortyapp.data.entities.CharacterListResponse
import com.example.eksamenrickandmortyapp.data.entities.Episode
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {

    @GET("character")
    suspend fun getAllCharacters(@Query("page") page: Int): Response<CharacterListResponse>

    @GET("episode")
    suspend fun getAllEpisodes(@Query("page") page: Int): Response<List<Episode>>


}