package com.example.mynotesapp.views

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ClipData.Item
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mynotesapp.R
import com.example.mynotesapp.adapter.NotesAdapter
import com.example.mynotesapp.databinding.FragmentAddNoteBinding
import com.example.mynotesapp.databinding.FragmentContentBinding
import com.example.mynotesapp.viewmodel.NotesViewModel
import java.text.SimpleDateFormat
import java.util.*

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

        selectDate()
        showDeleteButton()

        //get the navigation argument from notes fragment
        val id = navigationArgs.id

        // observe data from database based on item's id
        viewModel.getItemById(id).observe(viewLifecycleOwner) { selectedItem ->
            note = selectedItem
            bind(note)
        }

        updateButton()
    }

    private fun deleteNote() {
        viewModel.deleteNote(note)
        findNavController().navigateUp()
    }

    private fun showDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Delete")
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Yes") { _, _ ->
                deleteNote()
                Toast.makeText(requireContext(), "Note successfully deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { _, _ ->

            }.create()

        dialog.show()
    }

    // set views from entity
    private fun bind(note: com.example.mynotesapp.data.Note) {
        binding.apply {
            titleTextView.editText?.setText(note.noteTitle)
            descriptionTextView.editText?.setText(note.noteDescription)
            dateTextView.editText?.setText(note.noteDate)
        }
    }

    //update note function
    private fun updateButton() {
        val id = navigationArgs.id
        binding.apply {
            updateButton.setOnClickListener {
                viewModel.upsert(
                    com.example.mynotesapp.data.Note(
                        id,
                    titleTextView.editText?.text.toString(),
                    descriptionTextView.editText?.text.toString(),
                    dateTextView.editText?.text.toString())
                )
                Toast.makeText(requireContext(), "Note successfully updated", Toast.LENGTH_SHORT).show()
            }
        }

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
            DatePickerDialog(requireContext(), datePicker, calendar.get(Calendar.YEAR), calendar.get(
                Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    // update label for date picker
    private fun updateLabel(calendar: Calendar) {
        val dateFormat = "MMMM d, yyyy - EEEE h:mm a"
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        binding.dateTextView.editText?.setText(simpleDateFormat.format(calendar.time))
    }

    //inflate delete button in this fragment
    private fun showDeleteButton() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.tool_bar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.deleteItem -> {
                        showDialog()
                        true
                    } else -> false
                }
            }
        }, viewLifecycleOwner)
    }

}