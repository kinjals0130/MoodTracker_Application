package com.example.moodtracker_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        MyAdapter.ViewHolder viewHolder = null;

        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.journal_item, parent, false);
                viewHolder = new ViewHolder(view);
                break;
            case 1:
                view = inflater.inflate(R.layout.private_journal_item, parent, false); // Replace with your private item layout
                viewHolder = new PrivateViewHolder(view);
                break;
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        MyModel myModel = getItem(position);

        switch (myModel.getPrivate()) {
            case 0: // bind to non private layout item
                ((ViewHolder) holder).bindNonPrivateItem(myModel);
                break;
            case 1: //bind to private layout item
                ((PrivateViewHolder) holder).bindPrivateItem(myModel);
                break;
        }

        /*
        holder.tv_title.setText(myModel.getEmotion()); // this is the emotion set by the user coordinating with the button
        holder.tv_description.setText(myModel.getTitle()); //in actuality this is the title of the note where available but i didnt feel like refactoring atm
        holder.tv_date.setText(myModel.getDate());

        holder.tv_colour.setText(myModel.getColour());
*/

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


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_cellTitle);
            tv_description = itemView.findViewById(R.id.tv_cellDesc);
            tv_date = itemView.findViewById(R.id.tv_cellDate);
            tv_colour = itemView.findViewById(R.id.tv_cellColour);

        }

        public void bindNonPrivateItem(MyModel myModel) {
            // Bind data for non-private item layout
            tv_title.setText(myModel.getEmotion());
            tv_description.setText(myModel.getTitle());
            tv_date.setText(myModel.getDate());
            tv_colour.setText(myModel.getColour());
        }
    }

    public static class PrivateViewHolder extends MyAdapter.ViewHolder {
        TextView tv_title, tv_date, tv_colour;
        ImageView mic; //if there is a voice note stored, enable the microphone object

        public PrivateViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_cellTitle);
            tv_date = itemView.findViewById(R.id.tv_cellDate);
            tv_colour = itemView.findViewById(R.id.tv_cellColour);
        }

        public void bindPrivateItem(MyModel myModel) {
            // Bind data for private item layout
            tv_title.setText(myModel.getEmotion());
            tv_date.setText(myModel.getDate());
            tv_colour.setText(myModel.getColour());

            //
        }
    }
}
