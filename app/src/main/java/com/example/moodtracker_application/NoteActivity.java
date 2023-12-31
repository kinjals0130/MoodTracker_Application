package com.example.moodtracker_application;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class NoteActivity extends AppCompatActivity {

    Button btn_discard, btn_save, btn_rate1, btn_rate2, btn_rate3, btn_rate4, btn_rate5, btn_voiceNote, btn_pickDate;
    EditText et_date, et_description, et_Title, et_Colour;
    TextView tv_date, emotionText;
    CheckBox cb_private;
    LinearLayout entry_layout;

    SQLiteManager sqLiteManager;

    EmotionSwitch emotion = new EmotionSwitch();
    int LOCATION_RESQUEST_CODE = 20;
    int RECORD_VOICE_VOTE =56;
    Location currentLocation = null;
    LocationManager locationManager;
    double latitude;
    double longitude;


    String currentEmotion = null;


    File voiceRecoding = new File("");

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
        emotionText = findViewById(R.id.emotionText);


//        et_date = findViewById(R.id.et_date);
        tv_date = (TextView) findViewById(R.id.DateText);
        btn_pickDate = (Button) findViewById(R.id.pickDate);
//        btn_pickDate.setOnClickListener(v ->{
//            DatePickerFragment newFragment = new DatePickerFragment();
//            newFragment.show(, "datePicker");
//        });

        // on below line we are adding click listener for our pick date button
        btn_pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        NoteActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                tv_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });
        et_description = findViewById(R.id.et_noteDescription);
        et_Title = findViewById(R.id.et_Title);
        et_Colour = findViewById(R.id.et_colour);

        cb_private = findViewById(R.id.cb_privateEntry);

        entry_layout = findViewById(R.id.linearLayout_noteDetails);

        sqLiteManager = new SQLiteManager(this);

        //sets the background colour based on the mood clicked
        btn_rate1.setOnClickListener(v -> {
            et_Colour.setText("#ED6A5A");
            emotionText.setText( emotion.getEmotion("1"));
            entry_layout.setBackgroundResource(R.color.ratingOne);
        });

        btn_rate2.setOnClickListener(v -> {
            et_Colour.setText("#FCB97D");
            emotionText.setText(  emotion.getEmotion("2"));
            entry_layout.setBackgroundResource(R.color.ratingTwo);
        });

        btn_rate3.setOnClickListener(v -> {
            et_Colour.setText("#9CC5A1");
            emotionText.setText( emotion.getEmotion("3"));
            entry_layout.setBackgroundResource(R.color.ratingThree);
        });

        btn_rate4.setOnClickListener(v -> {
            et_Colour.setText("#88AB75");
            emotionText.setText( emotion.getEmotion("4"));
            entry_layout.setBackgroundResource(R.color.ratingFour);
        });

        btn_rate5.setOnClickListener(v -> {
            et_Colour.setText("#49A078");
            emotionText.setText( emotion.getEmotion("5"));
            entry_layout.setBackgroundResource(R.color.ratingFive);
        });

        // get location permissions
        boolean permsGranted = isLocationPermissionGranted();
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationManager locManager;
        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        String gpsProvider = LocationManager.GPS_PROVIDER;
        if (!locManager.isProviderEnabled(gpsProvider)) {
            // would be a good idea to ask them first!
            String locConfig = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
            Intent enableGPS = new Intent(locConfig);
            startActivity(enableGPS);
        }
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(false);
        String recommended = locManager.getBestProvider(criteria, true);


        if(permsGranted) {
            //the if statement is a perms check
            @SuppressLint("MissingPermission") Location location = locManager.getLastKnownLocation(recommended);
            assert location != null;
             latitude = location.getLatitude();
             longitude = location.getLongitude();

            Log.d("Location","Longitude: "+longitude + "  Latitude: "+latitude);
        }

    }


    public void savePressed(View view) {
        String title = et_Title.getText().toString();
        String desc = et_description.getText().toString();
//        String date = et_date.getText().toString();
        String date = tv_date.getText().toString();
        String colour = et_Colour.getText().toString();
        String emotion = emotionText.getText().toString();
//       TODO: actually prevent saves without emotions
        int priv = (cb_private.isChecked()) ? 1 : 0;


        if (emotion.isEmpty()) Toast.makeText(this, "You must set an emotion!", Toast.LENGTH_SHORT).show();
       else  if (date.isEmpty()) Toast.makeText(this, "Please enter a date", Toast.LENGTH_SHORT).show();


        else {
            MyModel myModel = new MyModel(date, emotion, title, desc, colour, priv,longitude,latitude);
            if(voiceRecoding.exists() && voiceRecoding.canRead()){
                int size = (int) voiceRecoding.length();
                Log.d("voiceRecoding.length()", String.valueOf(voiceRecoding.length()));
                byte[] voiceBytes = new byte[size];
                try {
                    BufferedInputStream buf = new BufferedInputStream(new FileInputStream(voiceRecoding));
                    buf.read(voiceBytes, 0, voiceBytes.length);
                    Log.d("voiceBytes.Length", String.valueOf(voiceBytes.length));
                    buf.close();
                    myModel.setAudio(voiceBytes);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }

            long result = sqLiteManager.addNewEntry(myModel);
            if (result == -1) {
                Toast.makeText(this, "Could not Save Entry", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Entry saved successfully.", Toast.LENGTH_SHORT).show();

                finish(); // Return to the activity that called this
            }
        }
    }

    //this method is called when discard button is clicked, set in xml file
    public void mainActivity(View view) {
//        Intent mainPageIntent = new Intent(NoteActivity.this, MainActivity.class);
//        startActivity(mainPageIntent);
        finish();
    }

    //looks for onClick on voice note button
    public void recordVoiceNote(View view) {
        Intent voiceNoteIntent = new Intent(NoteActivity.this, VoiceNoteActivity.class);
        startActivityForResult(voiceNoteIntent, RECORD_VOICE_VOTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECORD_VOICE_VOTE) {
            // Obtain new dataset from database and update the heatmap adapter

            Log.d("NoteActivity log", "got new voice file path: " + data.getStringExtra("filePath"));
                File temp = new File(Objects.requireNonNull(data.getStringExtra("filePath")));
            if (temp.exists() && temp.canRead()){

                voiceRecoding = temp;
            }
        }
    }
    private Boolean isLocationPermissionGranted() {
        String[] perms = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, perms, LOCATION_RESQUEST_CODE);
            return false;
        } else {
            return true;
        }
    }


}