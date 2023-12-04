package com.example.moodtracker_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PinChangeActivity extends AppCompatActivity {

    private EditText currentPinEditText, newPinEditText;
    private Button saveButton, backButton;
    private SQLiteManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_change);

        currentPinEditText = findViewById(R.id.currentpin_et);
        newPinEditText = findViewById(R.id.newpin_et);
        saveButton = findViewById(R.id.save_btn);
        backButton = findViewById(R.id.back_btn);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePin();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void changePin() {
        String currentPin = currentPinEditText.getText().toString().trim();
        String newPin = newPinEditText.getText().toString().trim();


        //determine if there is a current value in the database for a pin, if yes, set to variable
        String correctPin = db.getPin();

        if (TextUtils.isEmpty(currentPin) && TextUtils.isEmpty(newPin)) {
            Toast.makeText(this, "Please enter current and new PIN", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(currentPin)) {
            //if current pin is empty, check if they are trying to set a pin (there is nothing in db)
            db.deletePrivateEntries();
            db.addPin(newPin);
            if (!correctPin.isEmpty() && !correctPin.equals("") && !correctPin.equals(null)) {
                // //create a toast that says "Pin changed.  Previous private entries deleted"
                Toast.makeText(this, "Pin set.  Previous private entries deleted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Pin changed.  Previous private entries deleted.", Toast.LENGTH_SHORT).show();
            }
        } else if (TextUtils.isEmpty(newPin)) {
            Toast.makeText(this, "Please enter a PIN", Toast.LENGTH_SHORT).show();
        } else {
            if (correctPin.equals(currentPin)) {

            } else {
                Toast.makeText(this, "Current PIN is incorrect.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}