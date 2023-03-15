package com.example.sampleappproject.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters


@Entity(tableName = "characters")
data class Result(
    val created: String,
    val gender: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: String,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String,
    @TypeConverters
    val location: Location,
    var page: Int,

//    @TypeConverters
//    val origin: Origin
)