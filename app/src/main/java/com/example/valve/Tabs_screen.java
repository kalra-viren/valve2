package com.example.valve;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.valve.Login_AIC.UserCredentials;
import com.example.valve.Login_TPE.UserCredentials2;

import java.util.List;

public class Tabs_screen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_screen);

        TextView credentialsTextView = findViewById(R.id.credentials_text_view);
        UserCredentials userCredentials = UserCredentials.getInstance(this);
        UserCredentials2 userCredentials2=UserCredentials2.getInstance(this);
        List<String> credentialsList2=userCredentials2.getCredentialsList();
        List<String> credentialsList = userCredentials.getCredentialsList();

        // Check if the list is null or empty
        if (credentialsList2 == null || credentialsList2.isEmpty()) {
            credentialsTextView.setText("No credentials available.");
        } else {
            StringBuilder credentialsString = new StringBuilder();
            for (String credential : credentialsList2) {
                credentialsString.append(credential).append("\n");
            }
            // Set the text of the TextView
            credentialsTextView.setText(credentialsString.toString());
        }
//        credentialsTextView.setText(userCredentials.getPoNumber());
    }
}