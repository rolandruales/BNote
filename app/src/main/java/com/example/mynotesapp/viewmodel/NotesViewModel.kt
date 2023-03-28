package com.example.mynotesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mynotesapp.data.Note
import com.example.mynotesapp.repository.NotesRepository

class NotesViewModel(private val notesRepository: NotesRepository) : ViewModel(){


//    fun upsert(item: Note) = CoroutineScope(Dispatchers.IO).launch {
//        notesRepository.upsert(item)
//    }
//
//    fun delete(item: Note) = CoroutineScope(Dispatchers.IO).launch {
//        notesRepository.delete(item)
//    }

    fun getNotes(): LiveData<List<Note>> = notesRepository.getNote()

}

