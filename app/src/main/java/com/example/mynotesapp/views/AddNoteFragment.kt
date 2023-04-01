package com.example.mynotesapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.fragment.app.activityViewModels
import com.example.mynotesapp.R
import com.example.mynotesapp.data.Note
import com.example.mynotesapp.databinding.FragmentAddNoteBinding
import com.example.mynotesapp.databinding.FragmentNotesBinding
import com.example.mynotesapp.viewmodel.NotesViewModel


class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!
    private val notesViewModel: NotesViewModel by activityViewModels ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
         _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            addNoteFab.setOnClickListener {
                // if any in title, date, description is empty, it will not save
                if (titleTextView.editText?.text.toString().isEmpty() ||
                        descriptionTextView.editText?.text.toString().isEmpty() ||
                        dateTextView.editText?.text.toString().isEmpty()){
                    Toast.makeText(requireContext(), "Do not leave fields empty", Toast.LENGTH_SHORT).show()
                } else {
                    // save notes in database
                    notesViewModel.upsert(
                        Note(0,
                        titleTextView.editText?.text.toString(),
                        descriptionTextView.editText?.text.toString(),
                        dateTextView.editText?.text.toString())
                    )
                    Toast.makeText(requireContext(), "Save successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}