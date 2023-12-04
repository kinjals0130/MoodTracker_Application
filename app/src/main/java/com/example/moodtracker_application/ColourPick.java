package com.example.moodtracker_application;

import java.util.HashMap;

public class ColourPick {

    private HashMap<String, String> colours;

    public ColourPick() {
        colours = new HashMap<>();
        colours.put("red", "#ED6A5A");
        colours.put("yellow", "#FCB97D");
        colours.put("light green", "#9CC5A1");
        colours.put("green", "#88AB75");
        colours.put("dark green", "#49A078");

    }

    public String getColour(String colorName) {
        // Get the hex code for the provided color name
        return colours.getOrDefault(colorName.toLowerCase(), "invalid");
    }
}
