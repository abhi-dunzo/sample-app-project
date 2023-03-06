package com.example.sampleappproject.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sampleappproject.models.Result

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacters(characters :List<Result>)

    @Query("SELECT * FROM characters")
    suspend fun getCharacters(): List<Result>
}