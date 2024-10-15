package com.example.valve.Approved_flow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

import com.example.valve.R;

public class A_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        // Add the TicketFragment to the fragment_carrier_layout_id
        if (savedInstanceState == null) {
            A_ticket aTicket = new A_ticket();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_carrier_layout_id, aTicket);
            transaction.commit();
        }
    }
}
