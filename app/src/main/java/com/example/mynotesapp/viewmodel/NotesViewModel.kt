package com.example.mynotesapp.viewmodel

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mynotesapp.data.Note
import com.example.mynotesapp.repository.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(private val notesRepository: NotesRepository) : ViewModel(){


    fun upsert(item: Note) = CoroutineScope(Dispatchers.IO).launch {
        notesRepository.upsert(item)
    }

    fun getNotes(): LiveData<List<Note>> = notesRepository.getNote()

    fun getItemById(id: Int): LiveData<Note> {
        return notesRepository.retrieveItem(id)
    }

    fun deleteNote(item: Note) = CoroutineScope(Dispatchers.IO).launch {
        notesRepository.delete(item)
    }
}

