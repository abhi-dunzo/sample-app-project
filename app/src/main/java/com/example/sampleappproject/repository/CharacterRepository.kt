package com.example.sampleappproject.repository

import android.content.Context
import android.net.Network
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.example.sampleappproject.api.CharacterService
import com.example.sampleappproject.db.CharactersDatabase
import com.example.sampleappproject.models.CharacterList
import com.example.sampleappproject.models.Info
import com.example.sampleappproject.models.Result
import com.example.sampleappproject.paging.CharacterPagingSource
import com.example.sampleappproject.paging.CharacterRemoteMediator
import com.example.sampleappproject.utils.NetworkUtils
import javax.inject.Inject
import javax.sql.StatementEvent

// TODO TESTING FOR REPOSITORY
class CharacterRepository (
    // TODO Data injection implementation for testing
    private val characterService: CharacterService,
    private val charactersDatabase: CharactersDatabase,
    private val applicationContext: Context
) {
    private var messagesMutableLiveData = MutableLiveData<String>()
    val messages : LiveData<String> get() = messagesMutableLiveData
//
//    private val charactersMutableLiveData= MutableLiveData<CharacterList>()
//    val characters: LiveData<CharacterList> get() = charactersMutableLiveData
//// TODO BEST PRACTICES OF ANDROID
@OptIn(ExperimentalPagingApi::class)
     fun getData() = Pager(
        config = PagingConfig(pageSize = 20,
         maxSize = 100
          ),
    remoteMediator = CharacterRemoteMediator (characterService , charactersDatabase),
        pagingSourceFactory = {charactersDatabase.characterDao().getChar()}
    ).liveData
     fun getDataFromApi() = Pager(
        config = PagingConfig(pageSize = 20 , maxSize = 100),
        pagingSourceFactory = {   CharacterPagingSource(characterService) }
    ).liveData

//   fun getSomeData() :LiveData<PagingData<Result>> {
//       return if (NetworkUtils.isInternetAvailable(applicationContext)) {
//           getData()
//       } else {
//           getDataFromApi()
//       }
//   }
//        suspend fun getCharacters(page: Int)   {
//// TODO remove unnecessary try catch blocks // generic
//
//            if (NetworkUtils.isInternetAvailable(applicationContext)) {
//                Log.d("internet available", "internet")
//
//                val result = characterService.getCharacters(page)
//                println(result)
//
//
//                if (result.isSuccessful) {
//                    if (result.body() != null) {
//                       result.body()!!.results.forEach { u->
//                            u.page=page
//                        }
//                        println(result.body()!!.results)
//
//                        charactersDatabase.characterDao().addCharacters(result.body()!!.results)
//
//                        charactersMutableLiveData.postValue(result.body())
//                        messagesMutableLiveData.postValue("Data Fetched Successfully")
//                        Log.d("charService", result.body().toString())
//                    }
//                    else{
//                        //handle this
//                    }
//                } else {
//                    fetchFromDatabase(page)
//                    //fetch these values from strings xml
//                    messagesMutableLiveData.postValue("api failed showing database values")
//                    Log.d("error", "Some error has occurred")
//                }
//            } else {
//                fetchFromDatabase(page)
//                messagesMutableLiveData.postValue("no internet showing database values")
//                Log.d("internet unavailable", "no internet")
//            }
//        }
//
//
//     private suspend fun fetchFromDatabase(page: Int) {
//         if (NetworkUtils.isInternetAvailable(applicationContext)) {
//             val characters = charactersDatabase.characterDao().getCharacters(page)
//             Log.d("offline data", characters.toString())
//             // TODO can this be removed hardcoding
//             val info = Info(1, "2", 1, 2)
//             val charactersList = CharacterList(info, characters)
//             charactersMutableLiveData.postValue(charactersList)
//         }
//
//
//    }
}




