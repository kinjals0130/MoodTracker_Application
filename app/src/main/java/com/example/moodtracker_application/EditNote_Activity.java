package com.example.moodtracker_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EditNote_Activity extends AppCompatActivity {

    EditText et_title, et_description, et_colour;

    TextView tv_date;
    CheckBox cb_private;

    Button btn_discard, btn_save, btn_rate1, btn_rate2, btn_rate3, btn_rate4, btn_rate5, btn_voiceNote, btn_pickDate, btn_viewMap;

    EmotionSwitch emotion = new EmotionSwitch();
    LinearLayout entry_layout;
    SQLiteManager sqLiteManager;
    MyModel myModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        et_title = findViewById(R.id.et_Title);
        et_description = findViewById(R.id.et_noteDescription);
        et_colour  = findViewById(R.id.et_colour);
        tv_date = findViewById(R.id.DateText);
        cb_private = findViewById(R.id.cb_privateEntry);

        btn_discard = findViewById(R.id.btn_discardNote);

        btn_save = findViewById(R.id.btn_saveNote);

        btn_voiceNote = findViewById(R.id.btn_voiceNote);

        // Set the map button to launch a map activity
        btn_viewMap = (Button) findViewById(R.id.btn_viewMap);
        btn_viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditNote_Activity.this, MapsMarkerActivity.class);
                intent.putExtra("Longitude",myModel.getLongitude());
                intent.putExtra("Latitude",myModel.getLatitude());
                startActivity(intent);
            }
        });

        entry_layout = findViewById(R.id.linearLayout_noteDetails);


        //get all the details of the note from database
        sqLiteManager = new SQLiteManager(this);

        String id = getIntent().getStringExtra("dbID");
        myModel = sqLiteManager.getEntryByID(id);//new MyModel();
        if(myModel.getId() != null){
            et_title.setText(myModel.getTitle());
            et_description.setText(myModel.getDescription());
            tv_date.setText(myModel.getDate());
            et_colour.setText(myModel.getColour());

            entry_layout.setBackgroundColor(Color.parseColor(et_colour.getText().toString()));
        }

        // get all frogs and set OnClicks
        btn_rate1 = findViewById(R.id.btn_one);
        btn_rate2 = findViewById(R.id.btn_two);
        btn_rate3 = findViewById(R.id.btn_three);
        btn_rate4 = findViewById(R.id.btn_four);
        btn_rate5 = findViewById(R.id.btn_five);

        //sets the background colour based on the mood clicked
        btn_rate1.setOnClickListener(v -> {
            et_colour.setText("#ED6A5A");
            emotion.getNumber("1");
            entry_layout.setBackgroundResource(R.color.ratingOne);
        });

        btn_rate2.setOnClickListener(v -> {
            et_colour.setText("#FCB97D");
            emotion.getNumber("2");
            entry_layout.setBackgroundResource(R.color.ratingTwo);
        });

        btn_rate3.setOnClickListener(v -> {
            et_colour.setText("#9CC5A1");
            emotion.getNumber("3");
            entry_layout.setBackgroundResource(R.color.ratingThree);
        });

        btn_rate4.setOnClickListener(v -> {
            et_colour.setText("#88AB75");
            emotion.getNumber("4");
            entry_layout.setBackgroundResource(R.color.ratingFour);
        });

        btn_rate5.setOnClickListener(v -> {
            et_colour.setText("#49A078");
            emotion.getNumber("5");
            entry_layout.setBackgroundResource(R.color.ratingFive);
        });

    }

    //this method is called when discard button is clicked, set in xml file
    public void mainActivity(View view) {
//        Intent mainPageIntent = new Intent(NoteActivity.this, MainActivity.class);
//        startActivity(mainPageIntent);
        finish();
    }

    public void savePressed(View view) {
        String title = et_title.getText().toString();
        String desc = et_description.getText().toString();
        String date = tv_date.getText().toString();
        String colour = et_colour.getText().toString();
        // TODO: ACTUALLY GET the real value for emotion
        String emotion = "Set emotion value";
        int priv = (cb_private.isActivated()) ? 1 : 0;

        // TODO: if any invalid flag is raised then surround that field with a Red Outline
        if (date.isEmpty()) Toast.makeText(this, "Please enter a date", Toast.LENGTH_SHORT).show();

        else {
            MyModel myModel = new MyModel(date, emotion, title, desc, colour, priv,this.myModel.longitude,this.myModel.latitude);

            long result = sqLiteManager.updateEntryInDatabase(myModel);
            if (result == -1) {
                Toast.makeText(this, "Could not Save Entry", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Entry saved successfully.", Toast.LENGTH_SHORT).show();

                finish(); // Return to the activity that called this
            }
        }
    }

    //looks for onClick on voice note button
    public void recordVoiceNote(View view) {
        Intent voiceNoteIntent = new Intent(EditNote_Activity.this, VoiceNoteActivity.class);
        startActivity(voiceNoteIntent);
    }



}