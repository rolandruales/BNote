package com.example.mynotesapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao{

    //get all list in database
    @Query("SELECT * FROM Notes")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM Notes Where id = :id")
    fun getItemById(id: Int): LiveData<Note>

    //insert or update existing record in db
    @Upsert
    suspend fun upsert(note: Note)

    //delete item
    @Delete
    suspend fun deleteNote(note: Note)
}