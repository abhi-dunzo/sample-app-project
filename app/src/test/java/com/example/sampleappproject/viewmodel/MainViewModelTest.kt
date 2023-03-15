package com.example.sampleappproject.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
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
    lateinit var  mainviewmodel :MainViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: CharacterRepository


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        mainviewmodel = MainViewModel(repository)

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

@Test
//    fun test_items_contain_one_to_ten() = runTest {
//        // Get the Flow of PagingData from the ViewModel under test
//     val pagingData : LiveData<PagingData<Result>> = mainviewmodel.list
//
////        val itemsSnapshot: List<com.example.sampleappproject.models.Result> = pagingData.s(
////            coroutineScope = this
////        ) {
////            // Each operation inside the lambda waits for the data to settle before continuing
////            scrollTo(index = 50)
////
////            // While you can’t view the items within the asSnapshot call,
////            // you can continuously scroll in a direction while some condition is true
////            // i.e., in this case until you hit the first header.
////            appendScrollWhile {  item: String -> item != "Header 1" }
////        }
//
//        // With the asSnapshot complete, you can now verify that the snapshot
//        // has the expected values
//        assertEquals(
//            expected = (0..50).map(Int::toString),
//            actual = itemsSnapshot
//        )
//    }



//
    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun test_CharactersLiveData() = runTest{
//        Mockito.`when`(repository.getData()).thenReturn(pagingData)
//        val sut = mainviewmodel
//
//        val res = sut.list.getOrAwaitValue()
//        assertEquals(null, res)
//    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
