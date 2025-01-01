package com.example.valve.Select_Activity;

import android.os.Bundle;
import android.app.Activity; // Ensure you have the correct import
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.valve.Approved_flow.A_main;
import com.example.valve.Login_TPE.UserCredentials2;
import com.example.valve.Menu.Menu_screen;
import com.example.valve.R;
import com.example.valve.Request_flow.R_main;

public class Select_Activity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        LinearLayout vc=findViewById(R.id.valve_cleaning_ll);
        vc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Select_Activity.this, Menu_screen.class);
                startActivity(intent);
            }
        });

    }
}
