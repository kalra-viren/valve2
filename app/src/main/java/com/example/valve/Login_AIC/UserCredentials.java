package com.example.valve.Login_AIC;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class UserCredentials {
    private static UserCredentials instance;
    private static final String PREFS_NAME = "UserCredentialsPrefs";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPLOYEE_ID = "employee_id";
    private static final String KEY_SITE_SUPERVISOR = "site_supervisor";
    private static final String TAG = "UserCredentials";
    private SharedPreferences sharedPreferences;
    private List<String> credentialsList;

    // Private constructor to prevent instantiation
    private UserCredentials(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        credentialsList = new ArrayList<>();
    }

    // Get the singleton instance
    public static synchronized UserCredentials getInstance(Context context) {
        if (instance == null) {
            instance = new UserCredentials(context);
        }
        return instance;
    }

    // Save user credentials
    public void saveCredentials(String empid, String user_id,String pass) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMPLOYEE_ID, empid);

        editor.putString(KEY_USER_ID, user_id);
        editor.putString(KEY_PASSWORD,pass);

        editor.apply();

        credentialsList.clear(); // Clear previous entries if needed
        credentialsList.add(empid);
        credentialsList.add(user_id);
        credentialsList.add(pass);

        Log.d(TAG, "First credential: " + credentialsList.get(0));
    }

    // Getters for user credentials
    public String getKeyEmployeeId() {
        return sharedPreferences.getString(KEY_EMPLOYEE_ID, null);
    }

    public String getKeyUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public String getKeyPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }

    public String getSiteSupervisor() {
        return sharedPreferences.getString(KEY_SITE_SUPERVISOR, null);
    }
    public List<String> getCredentialsList() {
        return new ArrayList<>(credentialsList); // Return a copy of the list
    }
}