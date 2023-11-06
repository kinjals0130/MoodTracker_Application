package com.example.moodtracker_application;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;
    private static final String DB_NAME = "MoodTracker.db";
    private static final String TABLE_NAME = "JournalEntry";
    private static final String COUNTER = "Counter";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_RATE = "rate";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_NAME = "name";
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
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(COLUMN_ID)
                .append(" INT, ")
                .append(COLUMN_RATE)
                .append(" TEXT, ")
                .append(COLUMN_DATE)
                .append(" TEXT, ")
                .append(COLUMN_DESCRIPTION)
                .append(" TEXT, ")
                .append(COLUMN_NAME)
                .append(" TEXT)");

        sq.execSQL(sql_String.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
