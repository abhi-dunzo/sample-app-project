package com.example.sampleappproject.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.map
import com.example.sampleappproject.api.CharacterService
import com.example.sampleappproject.getOrAwaitValue
import com.example.sampleappproject.models.CharacterList
import com.example.sampleappproject.models.Info
import com.example.sampleappproject.models.Location
import com.example.sampleappproject.models.Result
import com.example.sampleappproject.paging.CharacterPagingSource
import com.example.sampleappproject.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

//TODO wildcard imports
class MainViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: CharacterRepository


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    private val characterList = CharacterList(
        Info(1, "2", 2, ""),
        listOf(
            Result(
            created = "2017-11-0T18:48:46.250Z",
            gender = "Male",
            id = 1,
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            name = "Rick Sanchez",
            species = "Human",
            status = "Alive",
            type = "",
            url = "https://rickandmortyapi.com/api/character/1",
            location = Location("hi"),1
        )
        )
    )
    private val pagingLiveData = MutableLiveData<PagingData<Result>>()

    private val pagingData : LiveData<PagingData<Result>>
        get()  = pagingLiveData


    private val charactersLiveData = MutableLiveData(characterList)

    private val characters: LiveData<CharacterList>
        get() = charactersLiveData
//
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_CharactersLiveData() = runTest{
        Mockito.`when`(repository.getData()).thenReturn(pagingData)
        val sut = MainViewModel(repository)

        val res = sut.list.getOrAwaitValue()
        assertEquals(null, res)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
