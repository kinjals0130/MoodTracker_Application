package com.example.moodtracker_application;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SQLiteManager extends SQLiteOpenHelper {

    private static final String DB_NAME = "MoodTracker.db";
    private static final String TABLE_NAME = "JournalEntry";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_COLOUR = "colour";
    private static final String COLUMN_EMOTION = "emotion";
    private static final String COLUMN_PRIVATE = "private";
    private static final String COLUMN_VOICE = "voicenote";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final int DB_VERSION = 3;
    //second table to hold the pin
    private static final String TABLE_PIN = "UserPin";
    private static final String COLUMN_PIN_ID = "id";
    private static final String COLUMN_PIN_DATE = "datetime";
    private static final String COLUMN_PIN_ENTRY = "pin";
    private static SQLiteManager sqLiteManager;

    public SQLiteManager(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    public static SQLiteManager instanceOfDatabase(Context context) {
        if (sqLiteManager == null) {
            sqLiteManager = new SQLiteManager(context);
        }

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sq) {

        //notes table
        StringBuilder sql_String;
        sql_String = new StringBuilder().append("CREATE TABLE ").append(TABLE_NAME).append("(").append(COLUMN_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ").append(COLUMN_TITLE).append(" TEXT, ").append(COLUMN_EMOTION).append(" TEXT, ").append(COLUMN_DESCRIPTION).append(" TEXT, ").append(COLUMN_DATE).append(" TEXT, ").append(COLUMN_PRIVATE).append(" INTEGER, ").append(COLUMN_VOICE).append(" BLOB, ").append(COLUMN_COLOUR).append(" TEXT, ").append(COLUMN_LONGITUDE).append(" DOUBLE, ").append(COLUMN_LATITUDE).append(" DOUBLE )");

        sq.execSQL(sql_String.toString());

        //table for pins
        sql_String = new StringBuilder().append("CREATE TABLE ").append(TABLE_PIN).append("(").append(COLUMN_PIN_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ").append(COLUMN_PIN_DATE).append(" TEXT, ").append(COLUMN_PIN_ENTRY).append(" TEXT)");

        sq.execSQL(sql_String.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PIN);
        onCreate(db);
    }

    //adding a new entry to the table
    public long addNewEntry(MyModel myModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        if (myModel.getId() != null) cv.put(COLUMN_ID, myModel.getId()); //
        cv.put(COLUMN_EMOTION, myModel.getEmotion());
        cv.put(COLUMN_TITLE, myModel.getTitle());
        cv.put(COLUMN_DESCRIPTION, myModel.getDescription());
        cv.put(COLUMN_DATE, myModel.getDate());
        cv.put(COLUMN_COLOUR, myModel.getColour());
        cv.put(COLUMN_PRIVATE, myModel.getPrivate());
        //not sure how to go about handling adding voice note to the table

        if (myModel.getLongitude() != 0.0) cv.put(COLUMN_LONGITUDE, myModel.getLongitude()); // if they are uninitialized
        if (myModel.getLatitude() != 0.0) cv.put(COLUMN_LATITUDE, myModel.getLatitude()); //

        long result = db.insert(TABLE_NAME, null, cv);
        db.close();
        return result;
    }
    public int deletePrivateEntries() {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_NAME, COLUMN_PRIVATE + " = ?", new String[]{"1"});
        db.close();
        return rowsAffected;
    }

    public int deleteEntry(String dbID) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{dbID});
        db.close();
        return rowsAffected;
    }


    public long addPin(String pin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateFormat.format(calendar.getTime());

        cv.put(COLUMN_PIN_DATE, formattedDateTime);
        cv.put(COLUMN_PIN_ENTRY, pin);
        //not sure how to go about handling adding voice note to the table
        long result = db.insert(TABLE_PIN, null, cv);
        db.close();
        return result;
    }

    public String getPin() {
        SQLiteDatabase db = this.getWritableDatabase();
        String pin = null;

        String queryString = "SELECT " + COLUMN_PIN_ENTRY + " FROM " + TABLE_PIN + " ORDER BY " + COLUMN_PIN_DATE + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor != null && cursor.moveToFirst()) {
            pin = cursor.getString(0);
            cursor.close();
        }
        db.close();
        return pin;
    }
    public List<MyModel> getAllEntries() {
        List<MyModel> myModelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                MyModel myModel = new MyModel();
                myModel.setId(cursor.getString(0));
                myModel.setTitle(cursor.getString(1));
                myModel.setEmotion(cursor.getString(2));
                myModel.setDescription(cursor.getString(3));
                myModel.setDate(cursor.getString(4));
                myModel.setPrivate(cursor.getInt(5));
                //idk how to go about handling the voice note but that will be colIndex 6
                myModel.setColour(cursor.getString(7));
                myModel.setLongitude(cursor.getDouble(8));
                myModel.setLatitude(cursor.getDouble(9));

                myModelList.add(myModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return myModelList;

    }


}
