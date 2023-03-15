package com.example.sampleappproject.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CharacterServiceTest {
    private var gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var characterService: CharacterService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        characterService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(CharacterService::class.java)

    }

    @Test
    fun testGetCharacters_expectedEmptyResult() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody("{info:{} ,results:[]}")
        mockWebServer.enqueue(mockResponse)
        val response = characterService.getCharacters(1)
        mockWebServer.takeRequest()
        print(response)
        Assert.assertEquals(0, response.body()?.results?.size)
    }

    @Test
    fun testGetCharacters_expectedResultListAsResponse() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody("{info:{} ,results:[{id:1 ,location:{name : a}} , {id:2}]}")
        mockWebServer.enqueue(mockResponse)
        val response = characterService.getCharacters(1)
        mockWebServer.takeRequest()
        print(response.body()?.results)
        Assert.assertEquals(2, response.body()?.results?.size)
    }

    @Test
    fun testGetCharacters_expectedError() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(404)
        mockResponse.setBody("Something Went Wrong")
        mockWebServer.enqueue(mockResponse)
        val response = characterService.getCharacters(1)
        mockWebServer.takeRequest()
        print(response.body()?.results)
        Assert.assertEquals(false, response.isSuccessful)
        Assert.assertEquals(404, response.code())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}