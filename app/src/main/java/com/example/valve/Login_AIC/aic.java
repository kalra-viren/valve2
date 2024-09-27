package com.example.valve.Login_AIC;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.valve.R;
import com.example.valve.Tabs_screen;

public class aic extends Fragment {

        public aic() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aic, container, false);

        Button submitButton = view.findViewById(R.id.next_button);

        // Set click listener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from EditText fields (assuming you have EditText views defined)
                EditText poNumberField = view.findViewById(R.id.input_field_1);
                EditText passwordField = view.findViewById(R.id.input_field_2);
                EditText aicSteNumberField = view.findViewById(R.id.input_field_3);
                EditText siteSupervisorField = view.findViewById(R.id.input_field_4);

                String poNumber = poNumberField.getText().toString();
                String password = passwordField.getText().toString();
                String aicSteNumber = aicSteNumberField.getText().toString();
                String siteSupervisor = siteSupervisorField.getText().toString();

                // Save credentials using UserCredentials model
                UserCredentials.getInstance(getActivity()).saveCredentials(poNumber, password, aicSteNumber, siteSupervisor);

                // Navigate to Tabs_screen activity
                navigateToTabsScreen();
            }
        });

        return view;
    }
    private void navigateToTabsScreen() {
        // Create an intent to start the Tabs_screen activity
        Intent intent = new Intent(getActivity(), Tabs_screen.class);
        startActivity(intent);
    }

}