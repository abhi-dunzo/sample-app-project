package com.example.sampleappproject.db

import androidx.paging.PagedList
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sampleappproject.models.Result
import com.example.sampleappproject.paging.CharacterPagingSource

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacters(characters :List<Result>)
    @Query("SELECT * FROM characters WHERE page=:page")
    suspend fun getCharacters(page:Int): List<Result>

    @Query("SELECT * FROM characters")
     fun getChar(): PagingSource<Int ,Result>
    @Query("DELETE FROM characters")
    suspend fun delete()


}