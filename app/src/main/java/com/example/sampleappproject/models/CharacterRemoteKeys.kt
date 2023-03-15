package com.example.sampleappproject.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    val prevPage : Int?,
    val nextPage:Int ?
)
