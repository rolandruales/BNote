package com.example.mynotesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.room.Room
import com.example.mynotesapp.data.Note
import com.example.mynotesapp.database.NotesDatabase
import com.example.mynotesapp.databinding.ActivityMainBinding

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set notes fragment as nav host fragment in this activity
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

//        val db = Room.databaseBuilder(
//            applicationContext,
//            NotesDatabase::class.java, "notes_database"
//        )
//            .allowMainThreadQueries().build()
//        val noteDao = db.noteDao()
//        binding.addItem.setOnClickListener {
//            noteDao.insertNote(Note(0,"When","Awit", "June 2"))
//        }
    }

    // enable back button when navigating fragments
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}