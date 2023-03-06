package com.example.sampleappproject.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sampleappproject.api.CharacterService
import com.example.sampleappproject.db.CharactersDatabase
import com.example.sampleappproject.models.CharacterList
import com.example.sampleappproject.models.Info
import com.example.sampleappproject.models.Result
import com.example.sampleappproject.utils.NetworkUtils

class CharacterRepository(
    private val characterService: CharacterService,
    private val charactersDatabase: CharactersDatabase,
    private val applicationContext: Context
) {

    private val charactersLiveData  = MutableLiveData<CharacterList>()
    val characters : LiveData<CharacterList>
    get() = charactersLiveData
    suspend fun getCharacters(){

        if(NetworkUtils.isInternetAvailable(applicationContext)){
            Log.d("internet avaialable" ,"hi from internet")
            val result  =  characterService.getCharacters()
            println(result)
            if(result.body() !=null){
                charactersDatabase.characterDao().addCharacters(result.body()!!.results)
                Log.d("charService" ,result.body().toString())
                charactersLiveData.postValue(result.body())
            }

        }
        else {
            val characters = charactersDatabase.characterDao().getCharacters()
            var info = Info(1 , "2" , 1, 2)
            val charactersList = CharacterList(info , characters)
            charactersLiveData.postValue(charactersList)
            Log.d("internet unavailable" ,"hi from without internet")

        }

    }
}