package com.example.mynotesapp.data

import android.app.Application
import com.example.mynotesapp.database.NotesDatabase
import com.example.mynotesapp.repository.NotesRepository

class NotesApplication : Application() {
    private val database: NotesDatabase by lazy { NotesDatabase.getDatabase(this) }

}