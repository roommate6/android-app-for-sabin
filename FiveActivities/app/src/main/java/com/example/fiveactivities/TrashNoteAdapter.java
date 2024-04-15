package com.example.fiveactivities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TrashNoteAdapter extends ArrayAdapter<Note>{
    private List<Note> trashNotes;

    public TrashNoteAdapter(Context context, List<Note> notes) {
        super(context, 0, notes);
        trashNotes = notes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Note note = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trash_note_cell,parent, false);
        }

        TextView title = convertView.findViewById(R.id.cellTitle2);
        TextView description = convertView.findViewById(R.id.cellDescription2);
        ImageView icon = convertView.findViewById(R.id.cellIcon3);
        ImageButton revertButton = convertView.findViewById(R.id.cellRevertButton);
        ImageButton permanentDeleteButton = convertView.findViewById(R.id.cellPermanentDeleteButton);

        title.setText(note.getTitle());
        description.setText(note.getDescription());
        icon.setImageResource(note.getIconResourceId(this.getContext()));

        revertButton.setOnClickListener(v -> {
            SQLiteManager databaseManager = SQLiteManager.getInstance(getContext());
            note.setDeleted(null);
            databaseManager.updateNote(note);
            trashNotes.remove(position);
            notifyDataSetChanged();
        });
        permanentDeleteButton.setOnClickListener(v -> {
            SQLiteManager databaseManager = SQLiteManager.getInstance(getContext());
            databaseManager.deleteNote(note);
            trashNotes.remove(position);
            notifyDataSetChanged();
        });

        return convertView;
    }
}
