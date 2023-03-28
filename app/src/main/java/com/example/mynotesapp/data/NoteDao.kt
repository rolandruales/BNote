package com.example.mynotesapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao{

    //get all list in database
    @Query("SELECT * FROM Notes")
    fun getAllNotes(): LiveData<List<Note>>

    //insert or update existing record in db
    @Upsert
    fun upsert(note: Note)

    //delete item
    @Delete
    suspend fun deleteNote(note: Note)
}