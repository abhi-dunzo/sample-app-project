package com.example.sampleappproject

import android.app.Application
import com.example.sampleappproject.api.CharacterService
import com.example.sampleappproject.api.RetrofitHelper
import com.example.sampleappproject.db.CharactersDatabase
import com.example.sampleappproject.repository.CharacterRepository

class CharacterApplication:Application() {
    lateinit var characterRepository: CharacterRepository
    override fun onCreate() {
        super.onCreate()
        initialize()
    }
    private fun initialize(){
        val characterService = RetrofitHelper.getInstance().create(CharacterService::class.java)
        val database = CharactersDatabase.getDatabase(applicationContext)
        characterRepository = CharacterRepository(characterService , database , applicationContext)
    }

}