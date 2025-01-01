package com.example.valve.Request_flow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.valve.R;


public class R_sel_permit extends Fragment {


    public R_sel_permit() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_r_sel_permit, container, false);


        Button nextButton=view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                R_photos rPhotos=new R_photos();
                FragmentTransaction transaction= getParentFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.fragment_carrier_id_ap,rPhotos);
                transaction.commit();
            }
        });

        return view;
    }
}