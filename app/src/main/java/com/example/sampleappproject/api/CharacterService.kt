package com.example.sampleappproject.api

import com.example.sampleappproject.models.CharacterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {
    @GET("/api/character")
    suspend fun getCharacters(@Query("page")page:Int) : Response<CharacterList>

}