package com.example.valve.Request_flow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.valve.R;


public class R_activity extends Fragment {



    public R_activity() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_r_activity, container, false);
        UserSelection userSelection=UserSelection.getInstance();
        TextView t3=view.findViewById(R.id.textView3);
        t3.setText("data of area's fragment: "+userSelection.getSelectedNumber());

        Button next_button=view.findViewById(R.id.next_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                R_permit rPermit=new R_permit();
                FragmentTransaction transaction= getParentFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.fragment_carrier_layout_id,rPermit);
                transaction.commit();
            }
        });



        return view;
    }
}