package com.example.sampleappproject.db

import com.example.sampleappproject.models.Location
import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun locationToText(value: Location): String {
        return value.name
    }

    @TypeConverter
    fun textToLocation(value: String): Location {
        return Location(value)
    }
}