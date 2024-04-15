package com.example.fiveactivities;

import java.util.ArrayList;
import java.util.Date;

public class Note {
    public static ArrayList<Note> notes = new ArrayList<>();
    public static final String NOTE_EDIT_EXTRA = "noteEdit";

    private int id;
    private String title;
    private String description;
    private Date deleted;

    public Note(int id, String title, String description, Date deleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deleted = deleted;
    }

    public Note(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        deleted = null;
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
}
