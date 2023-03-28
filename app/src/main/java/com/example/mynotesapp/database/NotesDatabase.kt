package com.example.mynotesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mynotesapp.data.Note
import com.example.mynotesapp.data.NoteDao

@Database(
    entities = [Note::class],
    version = 1
)

abstract class NotesDatabase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao

    //check instance of the database if already running
    companion object {
        @Volatile
        private var INSTANCE: NotesDatabase? = null
        fun getDatabase(context: Context): NotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "notes_database"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}