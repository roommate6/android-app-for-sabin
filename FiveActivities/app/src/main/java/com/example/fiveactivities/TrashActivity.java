package com.example.fiveactivities;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TrashActivity extends AppCompatActivity {
    private ListView trashNotesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);

        initializeWidgets();
        setTrashNoteAdapter();
    }

    private void setTrashNoteAdapter() {
        TrashNoteAdapter noteAdapter = new TrashNoteAdapter(this, Note.getDeletedNotes());
        trashNotesListView.setAdapter(noteAdapter);
    }

    private void initializeWidgets(){
        trashNotesListView = findViewById(R.id.trashNotesListView);
    }

    private void loadFromDatabaseToMemory() {
        SQLiteManager databaseManager = SQLiteManager.getInstance(this);
        databaseManager.populateNotes();
    }

    public void handleBackEvent(View view) {
        finish();
    }
}