package com.example.moodtracker_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

public class EditNote_Activity extends AppCompatActivity {

    EditText et_title, et_description, et_date;
    CheckBox cb_private;

    SQLiteManager sqLiteManager;
    MyModel myModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        et_title = findViewById(R.id.et_Title);
        et_description = findViewById(R.id.et_noteDescription);
        et_date = findViewById(R.id.et_date);
        cb_private = findViewById(R.id.cb_privateEntry);

        sqLiteManager = new SQLiteManager(this);

        myModel = new MyModel();
        if(myModel.getId() != null){
            et_title.setText(myModel.getTitle());
            et_description.setText(myModel.getDescription());
            et_date.setText(myModel.getDate().toString());

        }

    }
}