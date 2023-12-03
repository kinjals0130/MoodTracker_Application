package com.example.moodtracker_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class PastEntries_Activity extends AppCompatActivity {

    RecyclerView rv_entries;
    MyAdapter myAdapter;
    List<MyModel> entries_list = new ArrayList<>();
    SQLiteManager sqLiteManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_entries);

        rv_entries = findViewById(R.id.rv_entries);

        sqLiteManager = new SQLiteManager(this);

    }

    private void setEntriesAdapter(){
//        entries_list.clear();
//        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
//        sqLiteManager.getAllEntries();
//
//        myAdapter = new MyAdapter(entries_list, getApplicationContext(), sqLiteManager);
//        rv_entries.setAdapter(myAdapter);
        entries_list.clear();
        entries_list.addAll(sqLiteManager.getAllEntries());
        rv_entries.setLayoutManager(new LinearLayoutManager((getApplicationContext())));

        myAdapter = new MyAdapter(entries_list, getApplicationContext(), sqLiteManager);
        rv_entries.setAdapter(myAdapter);

    }

    @Override
    protected void onResume(){
        super.onResume();
        setEntriesAdapter();
    }
}