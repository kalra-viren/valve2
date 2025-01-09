package com.example.valve.Request_flow;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.BuildConfig;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.valve.Login_TPE.UserCredentials2;
import com.example.valve.R;
import com.example.valve.Util.APIS_URLs;
import com.example.valve.Util.App_utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class R_area extends Fragment {
    private Switch tpeSwitch;
    private EditText tpeStcInput;
    private Spinner districtSpinner;
    private AutoCompleteTextView districtAutoComplete;
    private AutoCompleteTextView nameAutoComplete;
    private Spinner nameSpinner;
    private Spinner numberSpinner;
    private AutoCompleteTextView numberAutoComplete;
    private Spinner dicSpinner;
    private UserSelection userSelection;
    private ProgressBar progress_bar_district,progress_bar_name,progress_bar_number,progress_bar_spinner,progress_bar_btn;




    public R_area() {
        // Required empty public constructor
    }

    private void fetchDistricts() {
        ProgressBar progressBar = progress_bar_district;
        progressBar.setVisibility(View.VISIBLE);
        String url = APIS_URLs.fetchDistricts_url; // API URL
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
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

                        // Use AutoCompleteTextView instead of Spinner
//                        districtAutoComplete = requireView().findViewById(R.id.districtAutoComplete);
                        ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(
                                requireActivity(),
                                android.R.layout.simple_dropdown_item_1line, // Simple dropdown layout
                                districts
                        );

                        districtAutoComplete.setAdapter(districtAdapter);
                        districtAutoComplete.setThreshold(1); // Show suggestions after 1 character
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log error details
                        progressBar.setVisibility(View.GONE);
                        Log.e("Volley Error", error.toString());
                    }
                });

        queue.add(jsonArrayRequest);
    }


    private void fetchValveChambers(String districtName) {
        ProgressBar progressBar = progress_bar_name;
        progressBar.setVisibility(View.VISIBLE);
        String url = APIS_URLs.fetchValveChambers_url + districtName; // API URL

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        List<String> chamberNames = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                chamberNames.add(response.getString(i)); // Assuming response is a simple array of strings
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Update AutoCompleteTextView with fetched chamber names
                        updateNameAutoComplete(chamberNames);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        // Handle error
                        error.printStackTrace();
                    }
                });

        queue.add(jsonArrayRequest);
    }

    private void updateNameAutoComplete(List<String> chamberNames) {
        ArrayAdapter<String> nameAdapter = new ArrayAdapter<>(
                requireActivity(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                chamberNames
        );

        nameAutoComplete = requireView().findViewById(R.id.nameAutoComplete);
        nameAutoComplete.setAdapter(nameAdapter);


    }


    private void fetchNumbersForName(String valveChamberName) {
        ProgressBar progressBar = progress_bar_number;
        progressBar.setVisibility(View.VISIBLE);
        String url = APIS_URLs.fetchNumbersForName_url + valveChamberName; // Ensure the URL is correctly formatted

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        List<String> numbers = new ArrayList<>();
                        numbers.add("Select Valve Chamber Id");
                        numbers.add(response); // Add the retrieved ValveChamberId to the list

                        // Automatically set the value if only one actual number exists
                        updateNumberAutoComplete(numbers, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        // Handle error
                        error.printStackTrace();
                    }
                });

        queue.add(stringRequest);
    }

    private void updateNumberAutoComplete(List<String> numbers, String response) {
        ArrayAdapter<String> numberAdapter = new ArrayAdapter<>(
                requireActivity(),
                android.R.layout.simple_dropdown_item_1line,
                numbers
        );
        numberAutoComplete.setAdapter(numberAdapter);

        // If there is only one actual number (excluding the default), set it directly
        if (numbers.size() == 2) { // One actual value plus the default
            numberAutoComplete.setText(response, false); // Set the value without triggering suggestions
        } else {
            numberAutoComplete.setText(""); // Clear any previous selection
        }
        userSelection.setSelectedNumber(response);

    }


    private void fetchDICNames() {
        ProgressBar progressBar = progress_bar_spinner;
        progressBar.setVisibility(View.VISIBLE);
        // Define the URL for your API endpoint
        String url = APIS_URLs.fetchDICNames_url; // Adjust the IP and port as necessary

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        // Use JsonArrayRequest since we're expecting a JSON array response
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        List<String> dicNames = new ArrayList<>();
                        dicNames.add("Select DIC Name"); // Add a default item

                        // Loop through the JSON array and add each name to the list
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                dicNames.add(response.getString(i)); // Add each name to the list
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Update your dicSpinner with the fetched data
                        updateDicSpinner(dicNames);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        // Handle error
                        error.printStackTrace();
                    }
                });

        queue.add(jsonArrayRequest);
    }

    private void updateDicSpinner(List<String> dicNames) {
        ArrayAdapter<String> dicAdapter = new ArrayAdapter<>(
                requireActivity(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                dicNames
        );
        dicSpinner.setAdapter(dicAdapter); // Set the adapter for your spinner
    }

    private void resetValveNamesDropdown() {
        List<String> defaultList = new ArrayList<>();
        defaultList.add("Select Valve Name"); // Default option

        // Update the valve names spinner (AutoCompleteTextView)
        ArrayAdapter<String> defaultAdapter = new ArrayAdapter<>(
                requireActivity(),
                android.R.layout.simple_dropdown_item_1line, // Simple dropdown layout
                defaultList
        );

        nameAutoComplete.setAdapter(defaultAdapter);
        nameAutoComplete.setText(""); // Clear the current selection
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_r_area, container, false);
        progress_bar_district=view.findViewById(R.id.progress_bar_district);
        progress_bar_name=view.findViewById(R.id.progress_bar_name);
        progress_bar_number=view.findViewById(R.id.progress_bar_number);
        progress_bar_spinner=view.findViewById(R.id.progress_bar_spinner);
        UserCredentials2 userCredentials2 = UserCredentials2.getInstance(getActivity());
        String s = userCredentials2.getPoNumber();
//        districtSpinner = view.findViewById(R.id.district_spinner);
        districtAutoComplete = view.findViewById(R.id.districtAutoComplete);
        fetchDistricts();
//        numberSpinner = view.findViewById(R.id.number_spinner);
        dicSpinner = view.findViewById(R.id.dic_spinner);
        fetchDICNames();
//        nameSpinner = view.findViewById(R.id.name_spinner);
        numberAutoComplete=view.findViewById(R.id.numberAutoComplete);
        nameAutoComplete=view.findViewById(R.id.nameAutoComplete);
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

        userSelection = UserSelection.getInstance();

        AutoCompleteTextView districtAutoComplete = view.findViewById(R.id.districtAutoComplete);
        districtAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected district
                String selectedDistrict = (String) parent.getItemAtPosition(position);
                resetValveNamesDropdown();


                // Perform actions based on the selected district
                userSelection.setSelectedDistrict(selectedDistrict);
                fetchValveChambers(selectedDistrict);
            }
        });



        nameAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = (String) parent.getItemAtPosition(position);
                userSelection.setSelectedName(selectedName); // Store the selected name
                fetchNumbersForName(selectedName); // Fetch related data
            }
        });


        dicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDIC = (String) parent.getItemAtPosition(position);
                userSelection.getInstance().setSelectedDIC(selectedDIC);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button nextButton = view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = userSelection.getSelectedDIC();
                String s2=userSelection.getSelectedDistrict();
                String s3=userSelection.getSelectedName();
                Log.d("Select District"," "+s2);

                // Check if DIC is selected
                if (s == null || s.equals("Select DIC Name") ||
                        s2 == null || s2.equals("null") || s2.isEmpty() ||
                        s3 == null || s3.equals("null") || s3.isEmpty()) {

                    new AlertDialog.Builder(getActivity())
                            .setTitle("Incomplete Selection")
                            .setMessage("Please ensure all fields are selected before proceeding.")
                            .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return; // Stop further execution
                } else {
                    String tpeInput = tpeStcInput.getText().toString().trim();

                    // Check if STC Input is provided
                    if (!tpeInput.isEmpty()) {
                        ProgressBar progressBar=view.findViewById(R.id.progress_bar_btn);
                        progressBar.setVisibility(View.VISIBLE);
                        // Create the URL for your API endpoint
                        String url = APIS_URLs.CheckSTCNo_url + App_utils.encode(tpeInput);
//                        String url = APIS_URLs.area_url + tpeInput;

                        // Create a Volley request queue
                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                        if(BuildConfig.DEBUG){
                            Log.d("checking api url", " "+url);
                        }
                        // Create the string request
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {

                                    @Override
                                    public void onResponse(String response) {
                                        progressBar.setVisibility(View.GONE);
                                        // Handle the response from the server
                                        try {
                                            // Parse the response (assuming it's in JSON format)
                                            JSONArray jsonArray = new JSONArray(response);
                                            if (jsonArray.length() > 0) {
                                                String a = jsonArray.get(0).toString();

                                                // Use the name or handle further actions
                                                userSelection.setTpeName(a);
                                                Toast.makeText(getActivity(), "Name: " + a, Toast.LENGTH_SHORT).show();
                                                R_photos rPhotos = new R_photos();
                                                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                                                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                                transaction.replace(R.id.fragment_carrier_id_ap, rPhotos);  // Transition to photos screen
                                                transaction.addToBackStack(null);
                                                transaction.commit();
                                            } else {
                                                Toast.makeText(getActivity(), "STCNo not found.", Toast.LENGTH_SHORT).show();
                                            }
//

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(getActivity(), "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        progressBar.setVisibility(View.GONE);
                                        // Handle error response
                                        Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<>();
                                // You can add headers if required (like authentication)
                                return headers;
                            }
                        };

                        // Add the request to the request queue
                        requestQueue.add(stringRequest);

                        // Transition to the next fragment (photos screen)

                    } else {
                        userSelection.setTpeName(null);
                        R_photos rPhotos = new R_photos();
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        transaction.replace(R.id.fragment_carrier_id_ap, rPhotos);  // Transition to photos screen
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }

                }
            }
        });

        return view;
    }
}