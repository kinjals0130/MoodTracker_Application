package com.example.moodtracker_application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button btn_newEntry, btn_rate1, btn_rate2, btn_rate3, btn_rate4, btn_rate5, btn_Menu;
    RecyclerView heatmap;
    RecyclerView.LayoutManager heatmapLayoutManager;
    HeatMapAdapter heatmapAdapter;

    MyModel[] Dataset;

    SQLiteManager database;

    int CREATE_NOTE_RESULTCODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  Heat map Code
        heatmap = findViewById(R.id.heatMapGridView);

        //get heatmap dataset
        database = new SQLiteManager(this);
        Dataset = database.getAllEntries().toArray(new MyModel[0]);
        Log.d("MainActivity Log", "Dataset.length: " + Dataset.length);

        heatmapLayoutManager = new GridLayoutManager(MainActivity.this, 5);
        heatmapAdapter = new HeatMapAdapter(this, Dataset);

        heatmap.setLayoutManager(heatmapLayoutManager);

        heatmap.setAdapter(heatmapAdapter);


        // Defining the buttons
        btn_newEntry = findViewById(R.id.btn_newEntry);
        btn_Menu = findViewById(R.id.btn_Menu);

        btn_rate1 = findViewById(R.id.btn_one);
        btn_rate2 = findViewById(R.id.btn_two);
        btn_rate3 = findViewById(R.id.btn_three);
        btn_rate4 = findViewById(R.id.btn_four);
        btn_rate5 = findViewById(R.id.btn_five);


//        TODO: REplace with DB.insert calls to the respective emotion
        TextView tv_moodMessage = findViewById(R.id.tv_moodMessage);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        btn_rate1.setOnClickListener(v -> {
            database.addNewEntry(new MyModel(day + "-" + (month + 1) + "-" + year, "very sad", "#ED6A5A"));
            Dataset = database.getAllEntries().toArray(new MyModel[0]);
            Log.d("MainActivity log", "got new Data length: " + Dataset.length);
            heatmapAdapter.Dataset = Dataset;
            heatmapAdapter.notifyDataSetChanged();
        }/*tv_moodMessage.setText(getString(R.string.sad_message))*/);

        btn_rate2.setOnClickListener(v -> {
            database.addNewEntry(new MyModel(day + "-" + (month + 1) + "-" + year, "sad", "#FCB97D"));
            Dataset = database.getAllEntries().toArray(new MyModel[0]);
            Log.d("MainActivity log", "got new Data length: " + Dataset.length);
            heatmapAdapter.Dataset = Dataset;
            heatmapAdapter.notifyDataSetChanged();
        }/*tv_moodMessage.setText(getString(R.string.not_good_message))*/);

        btn_rate3.setOnClickListener(v -> {
            database.addNewEntry(new MyModel(day + "-" + (month + 1) + "-" + year, "neutral", "#9CC5A1"));
            Dataset = database.getAllEntries().toArray(new MyModel[0]);
            Log.d("MainActivity log", "got new Data length: " + Dataset.length);
            heatmapAdapter.Dataset = Dataset;
            heatmapAdapter.notifyDataSetChanged();
        } /*tv_moodMessage.setText(getString(R.string.okay_message))*/);

        btn_rate4.setOnClickListener(v -> {
            database.addNewEntry(new MyModel(day + "-" + (month + 1) + "-" + year, "happy", "#88AB75"));
            Dataset = database.getAllEntries().toArray(new MyModel[0]);
            Log.d("MainActivity log", "got new Data length: " + Dataset.length);
            heatmapAdapter.Dataset = Dataset;
            heatmapAdapter.notifyDataSetChanged();
        } /*tv_moodMessage.setText(getString(R.string.good_message))*/);

        btn_rate5.setOnClickListener(v -> {
            database.addNewEntry(new MyModel(day + "-" + (month + 1) + "-" + year, "very happy", "#49A078"));
            Dataset = database.getAllEntries().toArray(new MyModel[0]);
            Log.d("MainActivity log", "got new Data length: " + Dataset.length);
            heatmapAdapter.Dataset = Dataset;
            heatmapAdapter.notifyDataSetChanged();
        }/*tv_moodMessage.setText(getString(R.string.happy_message))*/);


        btn_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });


    }

    //this is called in the xml for the new entry button
    public void newEntryActivity(View view) {
        Intent intent = new Intent(MainActivity.this, NoteActivity.class);

        //noinspection deprecation
        startActivityForResult(intent, CREATE_NOTE_RESULTCODE);

    }


    //Suppressing this bc efficiency is not our priority right now and this method is the most robust
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_NOTE_RESULTCODE) {
            // Obtain new dataset from database and update the heatmap adapter
            Dataset = database.getAllEntries().toArray(new MyModel[0]);
            Log.d("MainActivity log", "got new Data length: " + Dataset.length);
            heatmapAdapter.Dataset = Dataset;
            heatmapAdapter.notifyDataSetChanged();
        }
    }


    // custom adapter class for the heat map
    public class HeatMapAdapter extends RecyclerView.Adapter<HeatMapAdapter.MyViewHolder> {

        private final Context context;
        MyModel[] Dataset;

        public HeatMapAdapter(Context context, MyModel[] Dataset) {
            this.context = context;
            this.Dataset = Dataset;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.heatmap_day, parent, false);

            return new MyViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Log.d("MainActivity Log", "Binding position: " + position);
            holder.diaryDay.setText(Dataset[holder.getAdapterPosition()].date);

            Drawable drawable = AppCompatResources.getDrawable(context, R.drawable.shape);
//            GradientDrawable backgroundDrawable =(GradientDrawable) holder.background;
            Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.shape);
            Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
            DrawableCompat.setTint(wrappedDrawable, Color.parseColor(Dataset[holder.getAdapterPosition()].colour));
            holder.background.setBackground(wrappedDrawable);
        }

        @Override
        public int getItemCount() {
            return Dataset.length;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView diaryDay;
            public View background;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                diaryDay = itemView.findViewById(R.id.dayNumber);
                background = itemView.findViewById(R.id.view);


            }


        }
    }

}