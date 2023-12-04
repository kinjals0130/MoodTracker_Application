package com.example.moodtracker_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditNote_Activity extends AppCompatActivity {

    EditText et_title, et_description, et_colour;

    TextView tv_date;
    CheckBox cb_private;
    Button btn_viewMap;

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

    }
}