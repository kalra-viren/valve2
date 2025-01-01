package com.example.valve.Request_flow;

// QuestionData.java
public class QuestionData {
    private int id;
    private int headingId;
    private String questionText;
    private String headingName;

    // Constructor
    public QuestionData(int id, int headingId, String questionText, String headingName) {
        this.id = id;
        this.headingId = headingId;
        this.questionText = questionText;
        this.headingName = headingName;
    }

    // Getters
    public int getId() { return id; }
    public int getHeadingId() { return headingId; }
    public String getQuestionText() { return questionText; }
    public String getHeadingName() { return headingName; }
}