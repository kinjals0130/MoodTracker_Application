package com.example.moodtracker_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    Button btn_discard, btn_save, btn_rate1, btn_rate2, btn_rate3, btn_rate4, btn_rate5, btn_voiceNote;
    EditText et_date, et_description, et_Title, et_Colour;
    CheckBox cb_private;
    LinearLayout entry_layout;

    SQLiteManager sqLiteManager;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        btn_discard = findViewById(R.id.btn_discardNote);
        btn_save = findViewById(R.id.btn_saveNote);
        btn_voiceNote = findViewById(R.id.btn_voiceNote);

        btn_rate1 = findViewById(R.id.btn_one);
        btn_rate2 = findViewById(R.id.btn_two);
        btn_rate3 = findViewById(R.id.btn_three);
        btn_rate4 = findViewById(R.id.btn_four);
        btn_rate5 = findViewById(R.id.btn_five);

        et_date = findViewById(R.id.et_date);
        et_description = findViewById(R.id.et_noteDescription);
        et_Title = findViewById(R.id.et_Title);
        et_Colour = findViewById(R.id.et_colour);

        cb_private = findViewById(R.id.cb_privateEntry);

        entry_layout = findViewById(R.id.linearLayout_noteDetails);

        sqLiteManager = new SQLiteManager(this);
        //sets the background colour based on the mood clicked
        //setEntryBackgroundCol();

        btn_rate1.setOnClickListener(v -> {
            et_Colour.setText("#ED6A5A");
            entry_layout.setBackgroundResource(R.color.ratingOne);
        });

        btn_rate2.setOnClickListener(v -> {
            et_Colour.setText("#FCB97D");
            entry_layout.setBackgroundResource(R.color.ratingTwo);
        });

        btn_rate3.setOnClickListener(v -> {
            et_Colour.setText("#9CC5A1");
            entry_layout.setBackgroundResource(R.color.ratingThree);
        });

        btn_rate4.setOnClickListener(v -> {
            et_Colour.setText("#88AB75");
            entry_layout.setBackgroundResource(R.color.ratingFour);
        });

        btn_rate5.setOnClickListener(v -> {
            et_Colour.setText("#49A078");
            entry_layout.setBackgroundResource(R.color.ratingFive);
        });

    }


    public void savePressed(View view) {
        String title = et_Title.getText().toString();
        String desc = et_description.getText().toString();
        String date = et_date.getText().toString();
        String colour = et_Colour.getText().toString();

        // TODO: if any invalid flag is raised then surround that field with a Red Outline
        if (title.isEmpty())
            Toast.makeText(this, "Please enter a title.", Toast.LENGTH_SHORT).show();

        else if (desc.isEmpty())
            Toast.makeText(this, "Please enter a description", Toast.LENGTH_SHORT).show();

        else if (date.isEmpty())
            Toast.makeText(this, "Please enter a date", Toast.LENGTH_SHORT).show();

        else {
            MyModel myModel = new MyModel(title, desc, date, colour);
            sqLiteManager.addNewEntry(myModel);
            Toast.makeText(this, "Entry saved successfully.", Toast.LENGTH_SHORT).show();
            finish(); // Return to the activity that called this
        }
    }
    //this method is called when discard button is clicked, set in xml file
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