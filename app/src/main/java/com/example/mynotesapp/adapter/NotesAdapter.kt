package com.example.mynotesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.data.Note
import com.example.mynotesapp.databinding.ItemNotesRecviewBinding

class NotesAdapter(private val onItemClicked: (Note) -> Unit) :
    ListAdapter<Note, NotesAdapter.NotesViewHolder>(
        DiffCallBack()
    ) {

    private lateinit var binding: ItemNotesRecviewBinding

    inner class NotesViewHolder(binding: ItemNotesRecviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.apply {
                titleItemRecView.text = note.noteTitle
                descriptionItemRecView.text = note.noteDescription
                dateItemRecView.text = note.noteDate
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        binding =
            ItemNotesRecviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(currentItem)
        }

        holder.itemView.setOnLongClickListener {
            Toast.makeText(holder.itemView.context, "Long pressed", Toast.LENGTH_SHORT).show()
            true
        }

        holder.bind(currentItem)
    }

    private class DiffCallBack : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }

}