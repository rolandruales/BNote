package com.example.mynotesapp.views

import android.content.ClipData.Item
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.mynotesapp.adapter.NotesAdapter
import com.example.mynotesapp.databinding.FragmentAddNoteBinding
import com.example.mynotesapp.databinding.FragmentContentBinding
import com.example.mynotesapp.viewmodel.NotesViewModel

class ContentFragment : Fragment(){

    private var _binding: FragmentContentBinding? = null
    private val binding get() = _binding!!
    lateinit var note: com.example.mynotesapp.data.Note
    private val navigationArgs: ContentFragmentArgs by navArgs()
    private val viewModel: NotesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get the navigation argument from notes fragment
        val id = navigationArgs.id

        // observe data from database based on item's id
        viewModel.getItemById(id).observe(viewLifecycleOwner) { selectedItem ->
            note = selectedItem
            bind(note)
        }
    }

    // set views from entity
    private fun bind(note: com.example.mynotesapp.data.Note) {
        binding.apply {
            titleTextView.editText?.setText(note.noteTitle)
            descriptionTextView.editText?.setText(note.noteDescription)
            dateTextView.editText?.setText(note.noteDate)
        }
    }

}