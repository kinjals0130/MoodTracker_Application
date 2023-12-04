package com.example.moodtracker_application;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PinCheck extends AppCompatActivity {

    private EditText pinEditText;

    private SQLiteManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_check);

        pinEditText = findViewById(R.id.pinEditText);
    }

    public void onContinueClicked(View view) {

        String enteredPin = pinEditText.getText().toString();

       String correctPin = db.getPin();

       if (!correctPin.isEmpty() && !correctPin.equals("")) {
            if (enteredPin.equals(correctPin)) {
                //navigate to edit/view activity
            } else {
                Toast.makeText(this, "Incorrect PIN. Please try again.", Toast.LENGTH_SHORT).show();
            }
       }
       else
       {
           Toast.makeText(this, "There is no pin set for private notes!", Toast.LENGTH_SHORT).show();
       }
    }

    public void onBackClicked(View view) {
        // Go back to the previous activity
        finish();
    }
}