package com.example.moodtracker_application;

import java.util.HashMap;

public class EmotionSwitch {

    private HashMap<String, Integer> emoSwitch;

    public EmotionSwitch() {
        emoSwitch = new HashMap<>();
        emoSwitch.put("very sad", 1);
        emoSwitch.put("sad", 2);
        emoSwitch.put("neutral", 3);
        emoSwitch.put("happy", 4);
        emoSwitch.put("very happy", 5);
    }

    public int getNumber(String inputString) {
        return emoSwitch.getOrDefault(inputString.toLowerCase(), -1);
    }

}
