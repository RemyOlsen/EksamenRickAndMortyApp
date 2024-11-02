package com.example.eksamenrickandmortyapp.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {

//    @GET("character")
//    suspend fun getAllCharacters(): Response<CharacterListResponse>


    @GET("character")
    suspend fun getAllCharacters(@Query("page") page: Int): Response<CharacterListResponse>

//    @GET
//    suspend fun getCharacters(@Url pageUrl: String? = null): CharacterResponse

}