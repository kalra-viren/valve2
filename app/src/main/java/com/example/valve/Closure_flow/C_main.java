package com.example.valve.Closure_flow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;


import com.example.valve.R;

public class C_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_main);

        // Add the TicketFragment to the fragment_carrier_layout_id
        if (savedInstanceState == null) {
            C_ticket cTicket = new C_ticket();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_carrier_id_ap, cTicket);
            transaction.commit();
        }
    }
}
