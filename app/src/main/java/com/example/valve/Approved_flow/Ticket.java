package com.example.valve.Approved_flow;

public class Ticket {
    private String title;
    private String description;

    public Ticket(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
