package com.example.sampleappproject.repository

import android.content.Context
import android.net.Network
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sampleappproject.api.CharacterService
import com.example.sampleappproject.db.CharactersDatabase
import com.example.sampleappproject.models.CharacterList
import com.example.sampleappproject.models.Info
import com.example.sampleappproject.utils.NetworkUtils

class CharacterRepository(
    private val characterService: CharacterService,
    private val charactersDatabase: CharactersDatabase,
    private val applicationContext: Context
) {
    var messages = MutableLiveData<String>()

    private val charactersLiveData = MutableLiveData<CharacterList>()
    val characters: LiveData<CharacterList> get() = charactersLiveData

    suspend fun getCharacters(page: Int) {

        if (NetworkUtils.isInternetAvailable(applicationContext)) {
            Log.d("internet available", "internet")
            val result = characterService.getCharacters(page)
            println(result)
            if (result.isSuccessful) {
                if (result.body() != null) {
                        charactersDatabase.characterDao().addCharacters(result.body()!!.results)
                        charactersLiveData.postValue(result.body())
                        messages.postValue("Data Fetched Successfully")
                        Log.d("charService", result.body().toString())
                }
            } else {
                fetchFromDatabase()
                messages.postValue("api failed showing database values")
                Log.d("error", "Some error has occurred")
            }
        } else {
            fetchFromDatabase()
            messages.postValue("no internet showing database values")
            Log.d("internet unavailable", "no internet")
        }
    }

    private suspend fun fetchFromDatabase() {
        val characters = charactersDatabase.characterDao().getCharacters()
        Log.d("offline data", characters.toString())
        val info = Info(1, "2", 1, 2)
        val charactersList = CharacterList(info, characters)
        charactersLiveData.postValue(charactersList)

    }
}




