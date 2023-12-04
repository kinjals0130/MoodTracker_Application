package com.example.moodtracker_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

//TODO: Refactor this class to have a more descriptive name as we have multiple recycler views and adaptor classes in this project
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final List<MyModel> entriesList;
    Context context;
    SQLiteManager sqLiteManager;

    public MyAdapter(List<MyModel> entriesList, Context context, SQLiteManager sqLiteManager) {
        this.entriesList = entriesList;
        this.context = context;
        this.sqLiteManager = sqLiteManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        MyModel myModel = getItem(position);

        holder.tv_title.setText(myModel.getEmotion()); // this is the emotion set by the user coordinating with the button
        holder.tv_description.setText(myModel.getTitle()); //in actuality this is the title of the note where available but i didnt feel like refactoring atm
        holder.tv_date.setText(myModel.getDate());

        holder.tv_colour.setText(myModel.getColour());


    }

    @Override
    public int getItemCount() {
        return entriesList.size();
    }

    public MyModel getItem(int position) {
        return entriesList.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_description, tv_date, tv_colour;

        RelativeLayout journal_layout;

        MyModel myModel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_cellTitle);
            tv_description = itemView.findViewById(R.id.tv_cellDesc);
            tv_date = itemView.findViewById(R.id.tv_cellDate);
            tv_colour = itemView.findViewById(R.id.tv_cellColour);

            //tv_colour.setText(myModel.getColour());

//            journal_layout = itemView.findViewById(R.id.relativeLayout_journal);
//            journal_layout.setBackgroundColor(Integer.parseInt(String.valueOf(tv_colour)));

        }
    }
}
