package com.example.valve.Review_flow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

import com.example.valve.R;

public class Re_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.re_main);

        // Add the TicketFragment to the fragment_carrier_layout_id
        if (savedInstanceState == null) {
            Re_ticket reTicket = new Re_ticket();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_carrier_id_ap, reTicket);
            transaction.commit();
        }
    }
}
