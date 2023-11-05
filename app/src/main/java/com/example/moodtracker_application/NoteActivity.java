package com.example.moodtracker_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NoteActivity extends AppCompatActivity {

    Button btn_discard, btn_save;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        btn_discard = findViewById(R.id.btn_discardNote);
        btn_save = findViewById(R.id.btn_saveNote);

    }

    //this method is called when discord button is clicked, set in xml file
    public void mainActivity(View view){
        Intent mainPageIntent = new Intent(NoteActivity.this, MainActivity.class);
        startActivity(mainPageIntent);

    }
}