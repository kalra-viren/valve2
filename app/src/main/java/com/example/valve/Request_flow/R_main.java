package com.example.valve.Request_flow;

import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.valve.Login_TPE.UserCredentials2;
import com.example.valve.R;

public class R_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_main); // Replace with your actual layout for the new screen

        UserCredentials2 userCredentials2 = UserCredentials2.getInstance(this);
        String s = userCredentials2.getPoNumber();

        // Initialize and display the R_area fragment
        R_area rArea = new R_area();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.fragment_carrier_id_ap, rArea);
        transaction.commit();

        // Set up OnBackPressedCallback
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fragmentManager = getSupportFragmentManager();
                // Check if there are any fragments in the back stack
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    // If there are fragments, pop the last one
                    fragmentManager.popBackStack();
                } else {
                    finish(); // Close the activity if no fragments are left
                }
            }
        };

        // Add the callback to the dispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}