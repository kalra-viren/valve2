package com.example.valve.Inbox_flow;

public class Ticket {
    private String title;
    private String description;
    private String status;
    private int id;
    private String tpE_name;
    private String agency_name;
    private String name;
    private String valve_name;
    private String formattedDate;
    private String formattedTime;
    private String district;

    // Constructor with all fields
    public Ticket(String title, String description, String status, int id, String tpE_name, String district,
                  String agency_name, String name, String valve_name, String formattedDate, String formattedTime) {
        this.title = title;
        this.description = description;
        this.status = status != null ? status : "REQUESTED";  // Handle default value for status
        this.id = id;
        this.tpE_name = tpE_name;
        this.district = district;
        this.agency_name = agency_name;
        this.name = name;
        this.valve_name = valve_name;
        this.formattedDate = formattedDate;
        this.formattedTime = formattedTime;
    }

    // Getter for title
    public String getTitle() {
        return title;
    }

    // Setter for title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Setter for description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter for status
    public String getStatus() {
        return status != null ? status : "REQUESTED";  // Default to "REQUESTED" if status is null
    }

    // Setter for status
    public void setStatus(String status) {
        this.status = status;
    }

    // Getter for id
    public int getId() {
        return id;
    }

    // Setter for id
    public void setId(int id) {
        this.id = id;
    }

    // Getter for tpE_name
    public String getTpE_name() {
        return tpE_name;
    }

    // Setter for tpE_name
    public void setTpE_name(String tpE_name) {
        this.tpE_name = tpE_name;
    }

    public String getDistrict(){
        return district;
    }
    public void setDistrict(String district){
        this.district=district;
    }

    // Getter for agency_name
    public String getAgency_name() {
        return agency_name;
    }

    // Setter for agency_name
    public void setAgency_name(String agency_name) {
        this.agency_name = agency_name;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for valve_name
    public String getValve_name() {
        return valve_name;
    }

    // Setter for valve_name
    public void setValve_name(String valve_name) {
        this.valve_name = valve_name;
    }

    // Getter for formattedDate
    public String getFormattedDate() {
        return formattedDate;
    }

    // Setter for formattedDate
    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    // Getter for formattedTime
    public String getFormattedTime() {
        return formattedTime;
    }

    // Setter for formattedTime
    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }
}