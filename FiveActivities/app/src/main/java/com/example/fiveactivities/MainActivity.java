package com.example.fiveactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView notesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeWidgets();
        setOnClickListener();
        loadFromDatabaseToMemory();
        setNoteAdapter();
    }

    public void handleNewNoteEvent(View view) {
        Intent newNoteIntent = new Intent(this, NoteDetailsActivity.class);
        startActivity(newNoteIntent);
    }

    public void handleTrashEvent(View view) {
        Intent accessTrashIntent = new Intent(this, TrashActivity.class);
        startActivity(accessTrashIntent);
    }

    private void initializeWidgets(){
        notesListView = findViewById(R.id.notesListView);
    }

    private void loadFromDatabaseToMemory() {
        SQLiteManager databaseManager = SQLiteManager.getInstance(this);
        databaseManager.populateNotes();
    }

    private void setNoteAdapter() {
        NoteAdapter noteAdapter = new NoteAdapter(this, Note.getNonDeletedNotes());
        notesListView.setAdapter(noteAdapter);
    }

    private void setOnClickListener() {
        notesListView.setOnItemClickListener((parent, view, position, id) -> {
            Note selectedNote = (Note) notesListView.getItemAtPosition(position);
            Intent editNoteIntent = new Intent(this, NoteDetailsActivity.class);
            editNoteIntent.putExtra(Note.NOTE_EDIT_EXTRA, selectedNote.getId());
            startActivity(editNoteIntent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFromDatabaseToMemory();
        setNoteAdapter();
    }
}