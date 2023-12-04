package com.example.moodtracker_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class PastEntries_Activity extends AppCompatActivity {

    RecyclerView rv_entries;
    NoteAdapter noteAdapter;
    List<MyModel> entries_list = new ArrayList<>();
    SQLiteManager sqLiteManager;

    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_entries);

        rv_entries = findViewById(R.id.rv_entries);
        btn_back = findViewById(R.id.btn_Back);

        sqLiteManager = new SQLiteManager(this);

        btn_back.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

    }

    private void setEntriesAdapter() {
//        entries_list.clear();
//        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
//        sqLiteManager.getAllEntries();
//
//        noteAdapter = new NoteAdapter(entries_list, getApplicationContext(), sqLiteManager);
//        rv_entries.setAdapter(noteAdapter);
        entries_list.clear();
        entries_list.addAll(sqLiteManager.getAllEntries());
        rv_entries.setLayoutManager(new LinearLayoutManager((getApplicationContext())));

        noteAdapter = new NoteAdapter(entries_list, getApplicationContext(), sqLiteManager);
        rv_entries.setAdapter(noteAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setEntriesAdapter();
    }
}