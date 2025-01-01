package com.example.valve.Approved_flow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Reading {
    private String time;
    private String reading;
    private String type;

    public Reading(String reading, String type) {
        this.time = getCurrentTime(); // Automatically set the current time
        this.reading = reading;
        this.type=type;
    }

    public String getTime() {
        return time;
    }

    public String getReading() {
        return reading;
    }

    public String getType() { return type; }

    // Method to get the current time in "HH:mm" format
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }
}
