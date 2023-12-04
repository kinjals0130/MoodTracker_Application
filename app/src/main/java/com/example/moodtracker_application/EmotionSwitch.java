package com.example.moodtracker_application;

import java.util.HashMap;

public class EmotionSwitch {

    private HashMap<String, String> emoSwitch, colourSw;
    private HashMap<String, Integer> emoSwitchReverse;

    public EmotionSwitch() {
        emoSwitch = new HashMap<>();
        emoSwitch.put("1", "very sad");
        emoSwitch.put("2", "sad");
        emoSwitch.put("3", "neutral");
        emoSwitch.put("4", "happy");
        emoSwitch.put("5", "very happy");

        emoSwitchReverse = new HashMap<>();
        emoSwitchReverse.put("very sad", 1);
        emoSwitchReverse.put("sad", 2);
        emoSwitchReverse.put("neutral", 3);
        emoSwitchReverse.put("happy", 4);
        emoSwitchReverse.put("very happy", 5);
    }

    public String getEmotion(String inputString) {
        return emoSwitch.getOrDefault(inputString.toLowerCase(), "neutral");
    }
    public int reverseEmotion(String inputString)
    {
        return emoSwitchReverse.getOrDefault(inputString.toLowerCase(), -1);
    }

}
