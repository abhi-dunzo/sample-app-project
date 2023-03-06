package com.example.sampleappproject.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sampleappproject.models.Result


@Database(entities = [Result::class], version = 1)
abstract class CharactersDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    companion object {
        private var INSTANCE: CharactersDatabase? = null
        fun getDatabase(context: Context): CharactersDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        CharactersDatabase::class.java,
                        "characters_database"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}