package com.example.moodtracker_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditNote_Activity extends AppCompatActivity {

    EditText et_title, et_description, et_colour;

    TextView tv_date;
    CheckBox cb_private;

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

        entry_layout = findViewById(R.id.linearLayout_noteDetails);

        sqLiteManager = new SQLiteManager(this);

        myModel = new MyModel();
        if(myModel.getId() != null){
            et_title.setText(myModel.getTitle());
            et_description.setText(myModel.getDescription());
            tv_date.setText(myModel.getDate());
            et_colour.setText(myModel.getColour());

            entry_layout.setBackgroundColor(Integer.parseInt(et_colour.toString()));
        }

    }
}