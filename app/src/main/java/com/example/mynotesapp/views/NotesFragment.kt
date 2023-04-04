package com.example.mynotesapp.views

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotesapp.R
import com.example.mynotesapp.adapter.NotesAdapter
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

        binding.recViewNotes.apply {
            layoutManager = LinearLayoutManager(requireContext())

            // adapter's parameter is the navigation when item in recyclerview is clicked
            // passing the id of the item as parameter
            rvAdapter = NotesAdapter{
                val action = NotesFragmentDirections.actionNotesFragmentToContentFragment(it.id)
                this.findNavController().navigate(action)
            }
            adapter = rvAdapter
        }

        //observe the data from database and display data
        viewModel.getNotes().observe(viewLifecycleOwner) {list ->
            rvAdapter.submitList(list)
        }

        // floating action button for adding a new note
        binding.fab.setOnClickListener {
            val action = NotesFragmentDirections.actionNotesFragmentToAddNoteFragment()
            findNavController().navigate(action)
        }

    }


}