package com.example.fiveactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class NoteDetailsActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText descriptionEditText;

    private Note selectedNote;

    private Button deleteNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        initializeWidgets();
        checkForEditNote();
    }

    public void handleSaveNoteEvent(View view) {
        SQLiteManager databaseManager = SQLiteManager.getInstance(this);
        String title = String.valueOf(titleEditText.getText());
        String description = String.valueOf(descriptionEditText.getText());

        if (selectedNote == null) {
            int id = Note.notes.size();
            Note newNote = new Note(id, title, description);
            Note.notes.add(newNote);
            databaseManager.addNote(newNote);
        } else {
            selectedNote.setTitle(title);
            selectedNote.setDescription(description);
            databaseManager.updateNote(selectedNote);
        }

        finish();
    }

    public void handleDeleteNoteEvent(View view) {
        selectedNote.setDeleted(new Date());
        SQLiteManager databaseManager = SQLiteManager.getInstance(this);
        databaseManager.updateNote(selectedNote);

        finish();
    }

    private void initializeWidgets(){
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        deleteNoteButton = findViewById(R.id.deleteNoteButton);
    }

    private void checkForEditNote() {
        Intent previousIntent = getIntent();

        int passedNoteId = previousIntent.getIntExtra(Note.NOTE_EDIT_EXTRA, -1);
        selectedNote = Note.getNoteWithId(passedNoteId);

        if (selectedNote == null) {
            deleteNoteButton.setVisibility(View.INVISIBLE);
            return;
        }

        titleEditText.setText(selectedNote.getTitle());
        descriptionEditText.setText(selectedNote.getDescription());
    }
}