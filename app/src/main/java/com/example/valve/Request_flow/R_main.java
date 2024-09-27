package com.example.valve.Request_flow;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity; // Change this import
import androidx.fragment.app.FragmentTransaction;

import com.example.valve.Login_TPE.UserCredentials2;
import com.example.valve.R;

public class R_main extends AppCompatActivity { // Change Activity to AppCompatActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_main); // Replace with your actual layout for the new screen

        UserCredentials2 userCredentials2 = UserCredentials2.getInstance(this);
        String s = userCredentials2.getPoNumber();

        R_area rArea = new R_area();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.fragment_carrier_layout_id, rArea);
        transaction.commit();
    }
}