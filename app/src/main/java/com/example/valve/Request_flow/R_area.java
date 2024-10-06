package com.example.valve.Request_flow;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.valve.Login_TPE.UserCredentials2;
import com.example.valve.Login_TPE.tpe;
import com.example.valve.R;


public class R_area extends Fragment {
    private Switch tpeSwitch;
    private EditText tpeStcInput;



    public R_area() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_r_area, container, false);
        UserCredentials2 userCredentials2 = UserCredentials2.getInstance(getActivity());
        String s = userCredentials2.getPoNumber();
        AppCompatSpinner districtSpinner=view.findViewById(R.id.district_spinner);
        AppCompatSpinner numberSpinner = view.findViewById(R.id.number_spinner);
        AppCompatSpinner dicSpinner = view.findViewById(R.id.dic_spinner);
        AppCompatSpinner nameSpinner = view.findViewById(R.id.name_spinner);
        tpeSwitch = view.findViewById(R.id.tpe_switch);
        tpeStcInput = view.findViewById(R.id.tpe_stc_input);

        tpeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Show EditText when TPE is available
                tpeStcInput.setVisibility(View.VISIBLE);
            } else {
                // Hide EditText when TPE is not available
                tpeStcInput.setVisibility(View.GONE);
            }
        });

        String[] districts = {"Select District", "District 1", "District 2", "District 3", "District 4", "District 5"};
        ArrayAdapter<String> districtAdapter;
        districtAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, districts);
        districtSpinner.setAdapter(districtAdapter);


        String[] name = {"Select Name", "Name 1", "Name 2", "Name 3", "Name 4", "Name 5"};
        ArrayAdapter<String> nameAdapter = new ArrayAdapter<>(requireActivity(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, name);
        nameSpinner.setAdapter(nameAdapter);

        // Populate other spinners similarly if needed
        String[] numbers = {"Select Number", "Number 1", "Number 2", "Number 3", "Number 4", "Number 5", "Number 1", "Number 2", "Number 3", "Number 4", "Number 5", "Number 1", "Number 2", "Number 3", "Number 4", "Number 5", "Number 1", "Number 2", "Number 3", "Number 4", "Number 5"};
        ArrayAdapter<String> numberAdapter = new ArrayAdapter<>(requireActivity(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, numbers);
        numberSpinner.setAdapter(numberAdapter);

        String[] dicNames = {"Select DIC", "DIC Name 1", "DIC Name 2", "DIC Name 3", "DIC Name 4", "DIC Name 5"};
        ArrayAdapter<String> dicAdapter = new ArrayAdapter<>(requireActivity(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dicNames);
        dicSpinner.setAdapter(dicAdapter);

        TextView carrier=view.findViewById(R.id.carrier);
        carrier.setText(s+"Inside Fragment r_area");


        UserSelection userSelection=UserSelection.getInstance();

        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDistrict=(String) parent.getItemAtPosition(position);
                userSelection.setSelectedDistrict(selectedDistrict);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        numberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedNumber = (String) parent.getItemAtPosition(position);
                userSelection.getInstance().setSelectedNumber(selectedNumber);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        dicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDIC = (String) parent.getItemAtPosition(position);
                userSelection.getInstance().setSelectedDIC(selectedDIC);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Button nextButton=view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                R_activity rat=new R_activity();
                FragmentTransaction transaction= getParentFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.fragment_carrier_layout_id,rat);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}