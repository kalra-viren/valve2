package com.example.valve.Request_flow;

public class PermitDataHolder {
    private static PermitData instance;

    public static PermitData getInstance() {
        if (instance == null) {
            instance = new PermitData();
        }
        return instance;
    }
}
