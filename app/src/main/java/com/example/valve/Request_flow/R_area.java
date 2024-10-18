package com.example.valve.Request_flow;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.valve.Login_TPE.UserCredentials2;
import com.example.valve.Login_TPE.tpe;
import com.example.valve.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class R_area extends Fragment {
    private Switch tpeSwitch;
    private EditText tpeStcInput;
    private Spinner districtSpinner;


    public R_area() {
        // Required empty public constructor
    }

    private void fetchDistricts() {
        String url = "http://10.0.2.2:5001/api/Districts/distinct-districts"; // API URL
//http://ip config:port number/api/districts/distinct-districts
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<String> districts = new ArrayList<>();
                        districts.add("Select District"); // Default selection

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                String districtName = response.getString(i); // Get string directly from the array
                                districts.add(districtName);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(
                                requireActivity(),
                                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                                districts
                        );
                        districtSpinner.setAdapter(districtAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log error details
                        Log.e("Volley Error", error.toString());
                    }

                });

        queue.add(jsonArrayRequest);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_r_area, container, false);
        UserCredentials2 userCredentials2 = UserCredentials2.getInstance(getActivity());
        String s = userCredentials2.getPoNumber();
        districtSpinner=view.findViewById(R.id.district_spinner);
        fetchDistricts();
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

//        String[] districts = {"Select District", "District 1", "District 2", "District 3", "District 4", "District 5"};
//        ArrayAdapter<String> districtAdapter;
//        districtAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, districts);
//        districtSpinner.setAdapter(districtAdapter);


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