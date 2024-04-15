package com.example.fiveactivities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class SQLiteManager extends SQLiteOpenHelper {
    private static SQLiteManager instance;

    private static final String DATABASE_NAME = "NotesDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Note";
    private static final String COUNTER = "Counter";

    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String DELETED_FIELD = "deleted";
    private static final String ICON_FIELD = "icon";

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    private SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager getInstance(Context context){
        if (instance == null){
            instance = new SQLiteManager(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        StringBuilder query;
        query = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(TITLE_FIELD)
                .append(" TEXT, ")
                .append(DESCRIPTION_FIELD)
                .append(" TEXT, ")
                .append(DELETED_FIELD)
                .append(" TEXT, ")
                .append(ICON_FIELD)
                .append(" TEXT)");

        database.execSQL(query.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addNote(Note note){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, note.getId());
        contentValues.put(TITLE_FIELD, note.getTitle());
        contentValues.put(DESCRIPTION_FIELD, note.getDescription());
        contentValues.put(DELETED_FIELD, getStringFromDate(note.getDeleted()));
        contentValues.put(ICON_FIELD, note.getIcon());

        database.insert(TABLE_NAME, null, contentValues);
    }

    public void updateNote(Note note) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, note.getId());
        contentValues.put(TITLE_FIELD, note.getTitle());
        contentValues.put(DESCRIPTION_FIELD, note.getDescription());
        contentValues.put(DELETED_FIELD, getStringFromDate(note.getDeleted()));
        contentValues.put(ICON_FIELD, note.getIcon());

        database.update(TABLE_NAME, contentValues, ID_FIELD + " =? "
                ,new String[]{ String.valueOf(note.getId())});
    }

    public void deleteNote(Note note) {
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(TABLE_NAME, ID_FIELD + " =? "
                ,new String[]{ String.valueOf(note.getId())});
    }

    public void populateNotes(){
        SQLiteDatabase database = this.getReadableDatabase();

        try(Cursor result = database.rawQuery("SELECT * FROM " + TABLE_NAME, null)){
            if(result.getCount() > 0) {
                Note.notes = new ArrayList<>();
                while(result.moveToNext()) {
                    int id = result.getInt(1);
                    String title = result.getString(2);
                    String description = result.getString(3);
                    String stringDeleted = result.getString(4);
                    String icon = result.getString(5);
                    Date deleted = getDateFromString(stringDeleted);
                    Note note = new Note(id, title, description, deleted, icon);
                    Note.notes.add(note);
                }
            }
        }
    }

    private String getStringFromDate(Date date) {
        if (date == null) {
            return null;
        }
        return dateFormat.format(date);
    }

    private Date getDateFromString(String string) {
        try {
            return dateFormat.parse(string);
        } catch (Exception e) {
            return null;
        }
    }

    public void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}
