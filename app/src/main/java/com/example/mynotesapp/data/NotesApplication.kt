package com.example.mynotesapp.data

import android.app.Application
import com.example.mynotesapp.database.NotesDatabase

class NotesApplication : Application() {
    val database: NotesDatabase by lazy { NotesDatabase.getDatabase(this) }
}