package com.example.moodtracker_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    Button btn_discard, btn_save, btn_rate1, btn_rate2, btn_rate3, btn_rate4, btn_rate5, btn_voiceNote;
    EditText et_date, et_description, et_name;
    CheckBox cb_private;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        btn_discard = findViewById(R.id.btn_discardNote);
        btn_save = findViewById(R.id.btn_saveNote);
        btn_voiceNote = findViewById(R.id.btn_voiceNote);

        btn_rate1 = findViewById(R.id.btn_rate1);
        btn_rate2 = findViewById(R.id.btn_rate2);
        btn_rate3 = findViewById(R.id.btn_rate3);
        btn_rate4 = findViewById(R.id.btn_rate4);
        btn_rate5 = findViewById(R.id.btn_rate5);

        et_date = findViewById(R.id.et_date);
        et_description = findViewById(R.id.et_noteDescription);
        et_name = findViewById(R.id.et_Name);

        cb_private = findViewById(R.id.cb_privateEntry);

    }

    public void savePressed(View view){

    }

    //this method is called when discord button is clicked, set in xml file
    public void mainActivity(View view){
        Intent mainPageIntent = new Intent(NoteActivity.this, MainActivity.class);
        startActivity(mainPageIntent);

    }

    //looks for onClick on voice note button
    public void recordVoiceNote(View view){
        Intent voiceNoteIntent = new Intent(NoteActivity.this, VoiceNoteActivity.class);
        startActivity(voiceNoteIntent);
    }
}