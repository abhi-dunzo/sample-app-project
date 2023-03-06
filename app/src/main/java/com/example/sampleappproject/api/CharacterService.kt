package com.example.sampleappproject.api

import com.example.sampleappproject.models.CharacterList
import retrofit2.Response
import retrofit2.http.GET

interface CharacterService {
    @GET("/api/character")
    suspend fun getCharacters() : Response<CharacterList>
}