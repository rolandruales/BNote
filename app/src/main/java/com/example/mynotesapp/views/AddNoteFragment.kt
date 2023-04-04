package com.example.mynotesapp.views

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mynotesapp.R
import com.example.mynotesapp.data.Note
import com.example.mynotesapp.databinding.FragmentAddNoteBinding
import com.example.mynotesapp.databinding.FragmentNotesBinding
import com.example.mynotesapp.viewmodel.NotesViewModel
import java.text.SimpleDateFormat
import java.util.*


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

        selectDate()
        saveNoteButton()
    }

    // open date picker
    private fun selectDate() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(calendar)
        }

        binding.calendarButton.setOnClickListener {
            DatePickerDialog(requireContext(), datePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    // update label for date picker
    private fun updateLabel(calendar: Calendar) {
        val dateFormat = "MMMM d, yyyy - EEEE h:mm a"
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        binding.dateTextView.editText?.setText(simpleDateFormat.format(calendar.time))
    }

    // show dialog before saving note
    private fun showDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add new")
            .setMessage("Do you want to save?")
            .setPositiveButton("Yes") { _, _ ->
                saveToDatabase()
                Toast.makeText(requireContext(), "Note saved successfully", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { _, _ ->

            }.create()

        dialog.show()
    }

    // save note function
    private fun saveNoteButton() {
        binding.apply {
            addNoteFab.setOnClickListener {
                showDialog()
            }
        }
    }

    // save to database
    private fun saveToDatabase() {
        binding.apply {
            // if any in title, date, description is empty, it will not save
            if (titleTextView.editText?.text.toString().isEmpty() ||
                descriptionTextView.editText?.text.toString().isEmpty() ||
                dateTextView.editText?.text.toString().isEmpty()
            ) {
                Toast.makeText(requireContext(), "Do not leave fields empty", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // save notes in database
                notesViewModel.upsert(
                    Note(
                        0,
                        titleTextView.editText?.text.toString(),
                        descriptionTextView.editText?.text.toString(),
                        dateTextView.editText?.text.toString()
                    )
                )
                findNavController().navigateUp()
            }
        }
    }
}