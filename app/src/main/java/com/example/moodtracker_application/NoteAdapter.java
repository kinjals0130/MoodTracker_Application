package com.example.moodtracker_application;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
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
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private final List<MyModel> entriesList;
    Context context;
    SQLiteManager sqLiteManager;

    public NoteAdapter(List<MyModel> entriesList, Context context, SQLiteManager sqLiteManager) {
        this.entriesList = entriesList;
        this.context = context;
        this.sqLiteManager = sqLiteManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        NoteAdapter.ViewHolder viewHolder = null;

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
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        MyModel myModel = getItem(position);

        switch (myModel.getPrivate()) {
            case 0: // bind to non private layout item
                ((ViewHolder) holder).bindNonPrivateItem(myModel);
                break;
            case 1: //bind to private layout item
                ((PrivateViewHolder) holder).bindPrivateItem(myModel);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return entriesList.size();
    }

    public MyModel getItem(int position) {
        return entriesList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_description, tv_date, tv_colour, tv_dbID;
        ImageView emotionImg, deletebtn;
        ImageView mic; //if there is a voice note stored, enable the microphone object

        EmotionSwitch emotionSwitch = new EmotionSwitch();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_cellTitle);
            tv_description = itemView.findViewById(R.id.tv_cellDesc);
            tv_date = itemView.findViewById(R.id.tv_cellDate);
            tv_colour = itemView.findViewById(R.id.tv_cellColour);
            deletebtn = itemView.findViewById(R.id.delete_btn);
            emotionImg = itemView.findViewById(R.id.note_emoji);
            mic = itemView.findViewById(R.id.mic_icon);
            tv_dbID = itemView.findViewById(R.id.entry_id);

            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String dbId = tv_dbID.getText().toString();
                        sqLiteManager.deleteEntry(dbId); // Access sqLiteManager from the outer class
                        entriesList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, entriesList.size());
                    }
                }
            });


            //set action for entire item (go Strait to edit activity
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Context context = itemView.getContext();
                        Intent intent = new Intent(context, EditNote_Activity.class);
                        context.startActivity(intent);
                    }
                }
            });

        }

        public void bindNonPrivateItem(MyModel myModel) {

            // Bind data for non-private item layout
            tv_title.setText(myModel.getEmotion());
            tv_description.setText(myModel.getTitle());
            tv_date.setText(myModel.getDate());
            tv_colour.setText(myModel.getColour());
            tv_dbID.setText(myModel.getId());

            //based on the emotion need to set the correct image
            switch (emotionSwitch.getNumber(myModel.getEmotion())) {
                case 1:
                    emotionImg.setImageResource(R.drawable.level1frog);
                    break;
                case 2:
                    emotionImg.setImageResource(R.drawable.level2frog);
                    break;
                case 3:
                    emotionImg.setImageResource(R.drawable.level3frog);
                    break;
                case 4:
                    emotionImg.setImageResource(R.drawable.level4frog);
                    break;
                case 5:
                    emotionImg.setImageResource(R.drawable.level5frog);
            }
        }
    }

    public class PrivateViewHolder extends NoteAdapter.ViewHolder {
        TextView tv_title, tv_date, tv_colour, tv_dbID;
        ImageView mic; //if there is a voice note stored, enable the microphone object
        ImageView emotionImg, deletebtn;
        EmotionSwitch emotionSwitch = new EmotionSwitch();

        public PrivateViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_cellTitle);
            tv_date = itemView.findViewById(R.id.tv_cellDate);
            tv_colour = itemView.findViewById(R.id.tv_cellColour);
            deletebtn = itemView.findViewById(R.id.priv_delete_btn);
            emotionImg = itemView.findViewById(R.id.priv_note_emoji);
            mic = itemView.findViewById(R.id.mic_icon);
            tv_dbID = itemView.findViewById(R.id.entry_id);
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String dbId = tv_dbID.getText().toString();
                        sqLiteManager.deleteEntry(dbId); // Access sqLiteManager from the outer class
                        entriesList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, entriesList.size());

                    }
                }
            });


            //set action for entire item (launch pin verification bc private item)
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Context context = itemView.getContext();
                        Intent intent = new Intent(context, PinCheck.class);
                        context.startActivity(intent);
                    }
                }
            });
        }

        public void bindPrivateItem(MyModel myModel) {
            // Bind data for private item layout
            tv_title.setText(myModel.getEmotion());
            tv_date.setText(myModel.getDate());
            tv_colour.setText(myModel.getColour());
            tv_dbID.setText(myModel.getId());

            //based on the emotion need to set the correct image
            switch (emotionSwitch.getNumber(myModel.getEmotion())) {
                case 1:
                    emotionImg.setImageResource(R.drawable.level1frog);
                    break;
                case 2:
                    emotionImg.setImageResource(R.drawable.level2frog);
                    break;
                case 3:
                    emotionImg.setImageResource(R.drawable.level3frog);
                    break;
                case 4:
                    emotionImg.setImageResource(R.drawable.level4frog);
                    break;
                case 5:
                    emotionImg.setImageResource(R.drawable.level5frog);
            }
        }
    }
}