package com.example.mynotesapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotesapp.adapter.NotesAdapter
import com.example.mynotesapp.data.*
import com.example.mynotesapp.database.NotesDatabase
import com.example.mynotesapp.databinding.FragmentNotesBinding
import com.example.mynotesapp.viewmodel.NotesViewModel

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvAdapter: NotesAdapter
    private val viewModel: NotesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = view.findNavController()

        val db = NotesDatabase.getDatabase(requireContext())
        val noteDao = db.getNoteDao()

//        binding.addItem.setOnClickListener {
//            noteDao.upsert(Note(0,"When","Awit", "June 2"))
//        }

        binding.recViewNotes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            rvAdapter = NotesAdapter()
            adapter = rvAdapter
        }

        viewModel.getNotes().observe(viewLifecycleOwner) {list ->
            rvAdapter.submitList(list)

        }

        binding.fab.setOnClickListener {
            val action = NotesFragmentDirections.actionNotesFragmentToAddNoteFragment()
            findNavController().navigate(action)
        }
    }


}