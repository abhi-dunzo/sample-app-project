package com.example.sampleappproject.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sampleappproject.models.CharacterRemoteKeys
import com.example.sampleappproject.models.Result

@Dao
interface CharacterRemoteDao {

    @Query("SELECT * FROM CharacterRemoteKeys WHERE id=:id")
    suspend fun getRemoteKeys(id :Int):CharacterRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharactersKeys(remoteKeys: List<CharacterRemoteKeys>)


    @Query("DELETE FROM CharacterRemoteKeys")
    suspend fun delete()
}