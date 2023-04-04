package com.example.mynotesapp.views

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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

        // handle back press in home fragment
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val dialog = AlertDialog.Builder(this@NotesActivity)
                    .setTitle("Exit")
                    .setMessage("Do you really want to exit?")
                    .setPositiveButton("Yes") { _, _ ->
                        finish()
                    }
                    .setNegativeButton("No") {_, _ ->

                    }.create()
                dialog.show()
            }
        })
    }

    // enable back button when navigating fragments
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}