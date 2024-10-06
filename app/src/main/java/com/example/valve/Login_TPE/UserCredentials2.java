package com.example.valve.Login_TPE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class UserCredentials2 {
    private static UserCredentials2 instance;
    private static final String PREFS_NAME = "UserCredentials2Prefs";
    private static final String KEY_PO_NUMBER = "po_number";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_AIC_STE_NUMBER = "aic_ste_number";
    private static final String KEY_SITE_SUPERVISOR = "site_supervisor";
    private static final String TAG = "UserCredentials2";
    private SharedPreferences sharedPreferences;
    private List<String> credentialsList;

    // Private constructor to prevent instantiation
    private UserCredentials2(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        credentialsList = new ArrayList<>();
    }

    // Get the singleton instance
    public static synchronized UserCredentials2 getInstance(Context context) {
        if (instance == null) {
            instance = new UserCredentials2(context);
        }
        return instance;
    }

    // Save user credentials
    public void saveCredentials(String poNumber, String password, String aicSteNumber) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PO_NUMBER, poNumber);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_AIC_STE_NUMBER, aicSteNumber);

        editor.apply();

        credentialsList.clear(); // Clear previous entries if needed
        credentialsList.add(poNumber);
        credentialsList.add(password);
        credentialsList.add(aicSteNumber);

    }

    // Getters for user credentials
    public String getPoNumber() {
        return sharedPreferences.getString(KEY_PO_NUMBER, null);
    }

    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }

    public String getAicSteNumber() {
        return sharedPreferences.getString(KEY_AIC_STE_NUMBER, null);
    }


    public List<String> getCredentialsList() {
        return new ArrayList<>(credentialsList); // Return a copy of the list
    }
}