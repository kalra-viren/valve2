package com.example.valve.Menu;

import android.app.Activity; // Ensure you have the correct import
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.valve.Approved_flow.A_main;
import com.example.valve.Login_TPE.UserCredentials2;
import com.example.valve.R;
import com.example.valve.Request_flow.R_main;

public class Menu_screen extends Activity { // Ensure it extends Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_screen); // Replace with your actual layout
        TextView tv=findViewById(R.id.textView);
        UserCredentials2 userCredentials2= UserCredentials2.getInstance(this);
        tv.setText("Welcome "+userCredentials2.getPoNumber()+"!");

        LinearLayout request_ll =findViewById(R.id.request_ll);
        request_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu_screen.this, R_main.class);
                startActivity(intent);
            }
        });

        LinearLayout approval_ll=findViewById(R.id.approval_ll);
        approval_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu_screen.this, A_main.class);
                startActivity(intent);
            }
        });


    }
}