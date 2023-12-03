package com.example.moodtracker_application;

import java.io.Serializable;

public class MyModel implements Serializable {
    //initialize variables
    String id, date, title, description, colour;

    public MyModel(){

    }

    public MyModel(String date, String title, String description, String colour){
        this.date = date;
        this.title = title;
        this.description = description;
        this.colour = colour;
    }

    public MyModel(String id, String date, String title, String description, String colour) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.description = description;
        this.colour = colour;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }


}
