package com.example.valve.Request_flow;

public class UserSelection {
    private static UserSelection instance;

    private String selectedDistrict;
    private String selectedNumber;
    private String selectedDIC;

    // Private constructor to prevent instantiation
    private UserSelection() {}

    // Singleton pattern to get the single instance of UserSelection
    public static UserSelection getInstance() {
        if (instance == null) {
            instance = new UserSelection();
        }
        return instance;
    }

    public String getSelectedDistrict() {
        return selectedDistrict;
    }

    public void setSelectedDistrict(String selectedDistrict) {
        this.selectedDistrict = selectedDistrict;
    }

    public String getSelectedNumber() {
        return selectedNumber;
    }

    public void setSelectedNumber(String selectedNumber) {
        this.selectedNumber = selectedNumber;
    }

    public String getSelectedDIC() {
        return selectedDIC;
    }

    public void setSelectedDIC(String selectedDIC) {
        this.selectedDIC = selectedDIC;
    }
}