package com.example.sampleappproject

import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.sampleappproject.db.CharacterDao
import com.example.sampleappproject.db.CharactersDatabase
import com.example.sampleappproject.models.Location
import com.example.sampleappproject.models.Result
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CharacterDaoTest {
private lateinit var charactersDatabase: CharactersDatabase
private lateinit var characterDao: CharacterDao

    @Before
    fun setUp(){
        charactersDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CharactersDatabase::class.java
        ).allowMainThreadQueries().build()
        characterDao = charactersDatabase.characterDao()

     }

    @Test
    fun insertCharacter_expectedSingleCharacter() = runBlocking{
        val character = listOf(Result("1" ,"" ,1 ,"MYiMAGE"  ,"TEST" ,"2" ,"2","2","www.google.com",
            Location("location for testing"),1
        ))
        characterDao.addCharacters(character)

        val result  = characterDao.getCharacters(1)
        Log.d("hlo" ,result[0].toString())

        assertEquals(1 , result.size)
        assertEquals(1 , result[0].id)

    }

    @Test
    fun deleteCharacter() = runBlocking{
        val character = listOf(Result("1" ,"" ,1 ,"MYiMAGE"  ,"TEST" ,"2" ,"2","2","www.google.com",
            Location("location for testing"),1
        ))
        characterDao.addCharacters(character)
        characterDao.delete()
        val result = characterDao.getCharacters(1)

        assertEquals(0 , result.size)
    }

    @After
    fun tearDown(){
        charactersDatabase.close()
    }

}