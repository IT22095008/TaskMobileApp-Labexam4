package com.example.tasktracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasktracker.databinding.ActivityMainBinding

// Main activity for displaying notes and adding new ones
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NoteDatabaseHelper
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the database helper
        db = NoteDatabaseHelper(this)

        // Initialize the NotesAdapter with all notes from the database
        notesAdapter = NotesAdapter(db.getAllNotes(), this)

        // Set layout manager and adapter for RecyclerView
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = notesAdapter

        // Set OnClickListener for the add button to navigate to AddNoteActivity
        binding.addButton.setOnClickListener{
            val intent= Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        // Initialize the search EditText
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filterNotes(s.toString())
            }
        })
    }

    private fun filterNotes(query: String) {
        val filteredNotes = if (query.isEmpty()) {
            db.getAllNotes()
        } else {
            db.searchNotes(query)
        }
        notesAdapter.refreshData(filteredNotes)
    }

    // Refresh data in the adapter when the activity resumes
    override fun onResume() {
        super.onResume()
        notesAdapter.refreshData(db.getAllNotes())
    }
}
