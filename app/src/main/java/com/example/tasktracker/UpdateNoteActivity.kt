package com.example.tasktracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tasktracker.databinding.ActivityUpdateNoteBinding

// Activity for updating an existing note
class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: NoteDatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using ViewBinding
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the database helper
        db = NoteDatabaseHelper(this)

        // Get the note ID passed from the intent
        noteId = intent.getIntExtra("note_id", -1)
        // If note ID is invalid, finish the activity
        if (noteId == -1) {
            finish()
            return
        }

        // Retrieve the note from the database using its ID
        val note = db.getNoteByID(noteId)
        // Populate EditText fields with note data
        binding.updateTitleEditText.setText(note.title)
        binding.updateContentEditText.setText(note.content)

        // Set OnClickListener for the save button
        binding.updateSaveButton.setOnClickListener {
            // Get updated title and content from EditText fields
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()
            // Create a new Note object with updated data
            val updateNote = Note(noteId, newTitle, newContent)
            // Update the note in the database
            db.updateNote(updateNote)
            // Finish the activity
            finish()
            // Display a toast message indicating that the changes have been saved
            Toast.makeText(this, "Change Saved", Toast.LENGTH_SHORT).show()
        }
    }
}
