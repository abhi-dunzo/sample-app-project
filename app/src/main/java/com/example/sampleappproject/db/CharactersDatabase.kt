package com.example.sampleappproject.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sampleappproject.models.CharacterRemoteKeys
import com.example.sampleappproject.models.Result


@Database(entities = [Result::class , CharacterRemoteKeys::class], version = 1)
@TypeConverters(Converters::class)
abstract class CharactersDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun characterRemoteDao() :CharacterRemoteDao
    companion object {
        private var INSTANCE: CharactersDatabase? = null
        fun getDatabase(context: Context): CharactersDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        CharactersDatabase::class.java,
                        "characters_database"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE!!
        }
    }
}