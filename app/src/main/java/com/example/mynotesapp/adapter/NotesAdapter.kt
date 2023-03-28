package com.example.mynotesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.data.Note
import com.example.mynotesapp.databinding.ItemNotesRecviewBinding

class NotesAdapter(): ListAdapter<Note, NotesAdapter.NotesViewHolder>(DiffCallBack()) {

    private lateinit var binding: ItemNotesRecviewBinding
    inner class NotesViewHolder(binding: ItemNotesRecviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.titleItemRecView.text = note.noteTitle
            binding.descriptionItemRecView.text = note.noteDescription
            binding.dateItemRecView.text = note.noteDate
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        binding = ItemNotesRecviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val getItem = getItem(position)

        binding.apply {
            titleItemRecView.text = getItem.noteTitle
            descriptionItemRecView.text = getItem.noteDescription
            dateItemRecView.text = getItem.noteDate
        }

    }

    class DiffCallBack : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }

}