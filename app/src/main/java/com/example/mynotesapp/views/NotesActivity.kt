package com.example.mynotesapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.mynotesapp.R
import com.example.mynotesapp.viewmodel.NotesViewModel
import com.example.mynotesapp.viewmodel.NotesViewModelFactory
import com.example.mynotesapp.database.NotesDatabase
import com.example.mynotesapp.databinding.ActivityMainBinding
import com.example.mynotesapp.repository.NotesRepository

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    lateinit var notesViewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        //set notes fragment as nav host fragment in this activity
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        try {
            setContentView(binding.root)
            val notesRepository = NotesRepository(NotesDatabase.getDatabase(this))
            val noteViewModelProviderFactory = NotesViewModelFactory(notesRepository)
            notesViewModel = ViewModelProvider(
                this,
                noteViewModelProviderFactory
            )[NotesViewModel::class.java]
        } catch (_: java.lang.Exception) {

        }
    }

    // enable back button when navigating fragments
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}