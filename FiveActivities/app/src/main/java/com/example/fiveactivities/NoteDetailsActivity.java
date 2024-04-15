package com.example.fiveactivities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.Date;

public class NoteDetailsActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText descriptionEditText;
    private ShapeableImageView shapeableImageView;

    private Note selectedNote;
    private int selectedIconId;

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
            Note newNote = new Note(id, title, description, this.getResources().getResourceName(selectedIconId));
            Note.notes.add(newNote);
            databaseManager.addNote(newNote);
        } else {
            selectedNote.setTitle(title);
            selectedNote.setDescription(description);
            selectedNote.setIcon(this.getResources().getResourceName(selectedIconId));
            databaseManager.updateNote(selectedNote);
        }

        finish();
    }

    public void handleSelectIconEvent(View view) {
        Intent selectIconIntent = new Intent(this, SelectIconActivity.class);
        selectIconResultLauncher.launch(selectIconIntent);
    }

    private final ActivityResultLauncher<Intent> selectIconResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null){
                            return;
                        }
                        selectedIconId = data.getIntExtra("selectedIconId", -1);
                        if (selectedIconId == -1){
                            selectedIconId = R.drawable.image;
                        }
                        shapeableImageView.setImageResource(selectedIconId);
                    }
                }
            });

    public void handleDeleteNoteEvent(View view) {
        selectedNote.setDeleted(new Date());
        SQLiteManager databaseManager = SQLiteManager.getInstance(this);
        databaseManager.updateNote(selectedNote);

        finish();
    }

    private void initializeWidgets(){
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        shapeableImageView = findViewById(R.id.shapeableImageView);
        deleteNoteButton = findViewById(R.id.deleteNoteButton);
    }

    private void checkForEditNote() {
        Intent previousIntent = getIntent();

        int passedNoteId = previousIntent.getIntExtra(Note.NOTE_EDIT_EXTRA, -1);
        selectedNote = Note.getNoteWithId(passedNoteId);

        if (selectedNote == null) {
            selectedIconId = R.drawable.image;
            shapeableImageView.setImageResource(R.drawable.image);
            deleteNoteButton.setVisibility(View.INVISIBLE);
            return;
        }

        selectedIconId = selectedNote.getIconResourceId(this);
        titleEditText.setText(selectedNote.getTitle());
        descriptionEditText.setText(selectedNote.getDescription());
        shapeableImageView.setImageResource(selectedNote.getIconResourceId(this));
    }

    public void handleBackEvent(View view) {
        finish();
    }
}