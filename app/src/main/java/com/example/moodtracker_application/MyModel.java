package com.example.moodtracker_application;

import android.provider.ContactsContract;

import java.io.Serializable;

//TODO: add 2 doubles of Longitude and Latitude here and in the database
public class MyModel implements Serializable {
    //initialize variables
    String id, date, emotion, title, description, colour;


    double longitude, latitude;
    byte[] audio;
    int priv; //private is restricted keyword

    public MyModel() {

    }

    // this one is used from the new note screen
    public MyModel(String date, String emotion, String title, String description, String colour, int p, double longitude, double latitude) {
        this.date = date; //required
        this.emotion = emotion; //required
        this.title = title;
        this.description = description;
        this.colour = colour; //required
        this.priv = p;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    //quick note entry
    public MyModel(String id, String date, String emotion, String colour) {
        this.id = id;
        this.date = date;
        this.emotion = emotion;
        this.title = "";
        this.description = "";
        this.colour = colour;
        this.priv = 0; //by default not private for quick notes
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

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
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

    public int getPrivate() { // boolean in db
        return priv;
    }

    public void setPrivate(int p) {
        this.priv = p;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


}
