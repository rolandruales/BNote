package com.example.mynotesapp.repository

import androidx.lifecycle.LiveData
import com.example.mynotesapp.data.Note
import com.example.mynotesapp.data.NoteDao
import com.example.mynotesapp.database.NotesDatabase
import kotlinx.coroutines.flow.Flow


class NotesRepository(private val db: NotesDatabase) {

    suspend fun upsert(note: Note) = db.getNoteDao().upsert(note)

    // Use this if parameter is database
    fun getNote() = db.getNoteDao().getAllNotes()

    fun retrieveItem(id: Int) = db.getNoteDao().getItemById(id)


}