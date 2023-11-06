package com.example.moodtracker_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btn_newEntry, btn_rate1, btn_rate2, btn_rate3, btn_rate4, btn_rate5, btn_Menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_newEntry = findViewById(R.id.btn_newEntry);
        btn_Menu = findViewById(R.id.btn_Menu);

        btn_rate1 = findViewById(R.id.btn_one);
        btn_rate2 = findViewById(R.id.btn_two);
        btn_rate3 = findViewById(R.id.btn_three);
        btn_rate4 = findViewById(R.id.btn_four);
        btn_rate5 = findViewById(R.id.btn_five);

        TextView tv_moodMessage = findViewById(R.id.tv_moodMessage);

        btn_rate1.setOnClickListener(v ->
                tv_moodMessage.setText("You are feeling sad today.")
        );

        btn_rate2.setOnClickListener(v ->
                tv_moodMessage.setText("You are feeling not good today.")
        );

        btn_rate3.setOnClickListener(v ->
                tv_moodMessage.setText("You are feeling okay today.")
        );

        btn_rate4.setOnClickListener(v ->
                tv_moodMessage.setText("You are feeling good today.")
        );

        btn_rate5.setOnClickListener(v ->
                tv_moodMessage.setText("You are feeling happy today.")
        );




    }

    //this is called in the xml for the new entry button
    public void newEntryActivity(View view) {
        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
        startActivity(intent);
    }
}