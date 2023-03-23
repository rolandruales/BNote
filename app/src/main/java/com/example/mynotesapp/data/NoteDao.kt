package com.example.mynotesapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao{

    //get all list in database
    @Query("SELECT * FROM notes ORDER BY note_title ASC")
    fun getAll(): Flow<List<Note>>

    //insert or update existing record in db
    @Upsert
    fun insertNote(note: Note)

    //delete item
    @Delete
    suspend fun deleteNote(note: Note)
}