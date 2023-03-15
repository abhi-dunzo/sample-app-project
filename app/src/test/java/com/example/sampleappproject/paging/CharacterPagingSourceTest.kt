package com.example.sampleappproject.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.sampleappproject.api.CharacterService
import com.example.sampleappproject.models.CharacterList
import com.example.sampleappproject.models.Info
import com.example.sampleappproject.models.Location
import com.example.sampleappproject.models.Result
import com.google.gson.Gson

import com.google.gson.GsonBuilder
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterPagingSourceTest{

    private val character = listOf(
        Result("1" ,"" ,1 ,"MYiMAGE"  ,"TEST" ,"2" ,"2","2","www.google.com",
        Location("location for testing"),1
    ))
        val res = CharacterList(info = Info(2,"2",1,"2") , results = character)


    private lateinit var pagingSource: CharacterPagingSource
    @Mock
    private lateinit var characterService:CharacterService
    @Before
    fun setup(){
        pagingSource = CharacterPagingSource(characterService)
    }
//
//    @Test
//    fun getItems() = runTest{
//        val mockedResponse = Mockito.mock(CharacterList::class.java)
//
//      Mockito.`when`(characterService.getCharacters(1)).thenReturn(mockedResponse)
//    }
}