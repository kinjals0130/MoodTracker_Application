package com.example.moodtracker_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_newEntry, btn_rate1, btn_rate2, btn_Rate3, btn_rate4, btn_rate5, btn_Menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_newEntry = findViewById(R.id.btn_newEntry);
        btn_Menu = findViewById(R.id.btn_Menu);


    }

    //this is called in the xml for the new entry button
    public void newEntryActivity(View view) {
        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
        startActivity(intent);
    }
}