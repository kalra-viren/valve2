package com.example.valve.Inbox_flow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

import com.example.valve.R;

public class I_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.i_main);

        // Add the TicketFragment to the fragment_carrier_layout_id
        if (savedInstanceState == null) {
            I_ticket iTicket = new I_ticket();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_carrier_id_ap, iTicket);
            transaction.commit();
        }
    }
}
