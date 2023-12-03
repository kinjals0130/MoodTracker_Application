package com.example.moodtracker_application;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;
    private static final String DB_NAME = "MoodTracker.db";
    private static final String TABLE_NAME = "JournalEntry";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_COLOUR = "colour";
    private static final int DB_VERSION = 1;
    public SQLiteManager(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    public static SQLiteManager instanceOfDatabase(Context context){
        if(sqLiteManager == null){
            sqLiteManager = new SQLiteManager(context);
        }

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sq) {
        StringBuilder sql_String;
        sql_String = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COLUMN_ID)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(COLUMN_TITLE)
                .append(" TEXT, ")
                .append(COLUMN_DESCRIPTION)
                .append(" TEXT, ")
                .append(COLUMN_DATE)
                .append(" TEXT, ")
                .append(COLUMN_COLOUR)
                .append(" TEXT)");

        sq.execSQL(sql_String.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //adding a new entry to the table
    public void addNewEntry(MyModel myModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, myModel.getId());
        cv.put(COLUMN_TITLE, myModel.getTitle());
        cv.put(COLUMN_DESCRIPTION, myModel.getDescription());
        cv.put(COLUMN_DATE, myModel.getDate());
        cv.put(COLUMN_COLOUR, myModel.getColour());

        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public List<MyModel> getAllEntries(){
        List<MyModel> myModelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                MyModel myModel = new MyModel();
                myModel.setId(cursor.getString(0));
                myModel.setTitle(cursor.getString(1));
                myModel.setDescription(cursor.getString(2));
                myModel.setDate(cursor.getString(3));
                myModel.setColour(cursor.getString(4));

                myModelList.add(myModel);
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return myModelList;

    }
}
