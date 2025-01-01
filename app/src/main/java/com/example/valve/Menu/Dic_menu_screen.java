package com.example.valve.Menu;

import android.app.Activity; // Ensure you have the correct import
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.valve.Approved_flow.A_main;
//import com.example.valve.Inbox_flow;
//import com.example.valve.Closure_flow.C_main;
//import com.example.valve.Login_TPE.UserCredentials2;
import com.example.valve.Closure_flow.C_main;
import com.example.valve.Inbox_flow.I_main;
import com.example.valve.Login_AIC.UserCredentials;
import com.example.valve.R;
import com.example.valve.Request_flow.R_main;
import com.example.valve.Review_flow.Re_main;

public class Dic_menu_screen extends Activity { // Ensure it extends Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dic_menu_screen); // Replace with your actual layout

        UserCredentials userCredentials= UserCredentials.getInstance(this);

        LinearLayout request_ll =findViewById(R.id.dic_request_ll);
        request_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dic_menu_screen.this, I_main.class);
                startActivity(intent);
            }
        });

        LinearLayout approval_ll=findViewById(R.id.dic_approval_ll);
        approval_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dic_menu_screen.this, Re_main.class);
                startActivity(intent);
            }
        });

        LinearLayout closure_ll=findViewById(R.id.dic_closure_ll);
        closure_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Dic_menu_screen.this, C_main.class);
                startActivity(intent);
            }
        });


    }
}