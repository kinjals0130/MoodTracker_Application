package com.example.moodtracker_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_newEntry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_newEntry = findViewById(R.id.btn_newEntry);

    }

    //this is called in the xml for the new entry button
    public void newEntryActivity(View view) {
        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
        startActivity(intent);
    }
}