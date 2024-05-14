package com.example.tasktracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tasktracker.databinding.ActivityAddNoteBinding
import com.example.tasktracker.databinding.ActivityMainBinding

// Activity for adding a new note
class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: NoteDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using ViewBinding
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the database helper
        db = NoteDatabaseHelper(this)

        // Set OnClickListener for the save button
        binding.saveButton.setOnClickListener{
            // Get title and content from EditText fields
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            // Create a new Note object
            val note = Note(0, title, content)
            // Insert the note into the database
            db.insertNote(note)
            // Finish the activity
            finish()
            // Display a toast message indicating that the note has been saved
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
        }
    }
}
