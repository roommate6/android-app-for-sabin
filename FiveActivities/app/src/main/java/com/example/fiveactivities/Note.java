package com.example.fiveactivities;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

public class Note {
    public static ArrayList<Note> notes = new ArrayList<>();
    public static final String NOTE_EDIT_EXTRA = "noteEdit";

    private int id;
    private String title;
    private String description;
    private Date deleted;
    private String icon;

    public Note(int id, String title, String description, String icon) {
        this.id = id;
        this.title = title;
        this.description = description;
        deleted = null;
        this.icon = icon;
    }

    public Note(int id, String title, String description, Date deleted, String icon) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deleted = deleted;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getIconResourceId(Context context) {
        return context.getResources().getIdentifier(getIcon(), "drawable", context.getPackageName());
    }

    public static Note getNoteWithId(int id) {
        for (Note note : notes) {
            if(note.getId() == id){
                return note;
            }
        }
        return null;
    }

    public static ArrayList<Note> getNonDeletedNotes(){
        ArrayList<Note> nonDeletedNotes = new ArrayList<>();

        for (Note note : notes){
            if (note.getDeleted() == null) {
                nonDeletedNotes.add(note);
            }
        }

        return nonDeletedNotes;
    }

    public static ArrayList<Note> getDeletedNotes(){
        ArrayList<Note> deletedNotes = new ArrayList<>();

        for (Note note : notes){
            if (note.getDeleted() != null) {
                deletedNotes.add(note);
            }
        }

        return deletedNotes;
    }
}
