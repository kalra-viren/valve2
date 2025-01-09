package com.example.valve.Review_flow;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.valve.Login_AIC.UserCredentials;
import com.example.valve.Login_TPE.UserCredentials2;
import com.example.valve.R;
import com.example.valve.Util.APIS_URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Re_ticket extends Fragment {

    private ListView ticketListView;
    private TicketAdapter ticketAdapter;
    private List<Ticket> ticketList; // List to hold tickets
    private View view;
    private LinearLayout noTicketsLayout;


    public Re_ticket() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_re_ticket, container, false);

        // Initialize ListView and Ticket list
        ticketListView = view.findViewById(R.id.ticket_list_view);
        ticketList = new ArrayList<>();

        // Set up the adapter
        ticketAdapter = new TicketAdapter(getActivity(), ticketList,getParentFragmentManager());
        ticketListView.setAdapter(ticketAdapter);
        noTicketsLayout=view.findViewById(R.id.no_tickets_layout);
        ProgressBar progressBar = view.findViewById(R.id.progress_bar);

        // Fetch tickets (this is where you would call your API)
        fetchTickets(progressBar);

        return view;
    }

    private void fetchTickets(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        UserCredentials userCredentials = UserCredentials.getInstance(getActivity());
        String empId = userCredentials.getKeyEmployeeId(); // Replace with actual STC number if needed
        String url = APIS_URLs.ReviewTickets_url + empId; // API endpoint
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ticketListView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        ticketList.clear(); // Clear any existing data

                        try {
                            Log.d("Response Length", " " + response.length());
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject ticketObject = response.getJSONObject(i);

                                // Log the JSON object for each iteration
                                Log.d("Ticket Object", ticketObject.toString());

                                // Extract data from JSON object
                                String poNo = ticketObject.optString("poNo", "N/A");
                                String district = ticketObject.optString("district", "N/A");
                                String status = ticketObject.optString("status", "REQUESTED"); // Default status if null
                                String tpE_name = ticketObject.optString("tpE_name", "N/A");
                                String agency_name = ticketObject.optString("agency_name", "N/A");
                                String name=ticketObject.optString("name","N/A");
                                String valve_name=ticketObject.optString("valve_name","N/A");
                                String createdAtString = ticketObject.optString("created_at", "N/A");
                                String formattedDate="";
                                String formattedTime="";

                                if (!createdAtString.equals("N/A")) {
                                    // Define the input and output formats
                                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

                                    try {
                                        // Parse the createdAtString to Date
                                        Date createdAtDate = inputFormat.parse(createdAtString);

                                        // Format the Date object into the desired formats
                                        formattedDate = dateFormat.format(createdAtDate); // "dd-MM-yyyy"
                                        formattedTime = timeFormat.format(createdAtDate); // "HH:mm"

                                        // You now have two strings: formattedDate and formattedTime
                                        System.out.println("Date: " + formattedDate);   // Example output: "11-12-2024"
                                        System.out.println("Time: " + formattedTime);   // Example output: "12:32"

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (status == null) {
                                    status = "REQUESTED"; // Set default if null
                                    Log.d("Status Default", "Status was null, setting to REQUESTED");
                                }
                                int id = ticketObject.getInt("permitDataId");

                                // Create a new Ticket object
                                Ticket ticket = new Ticket(poNo, district, status, id, tpE_name, district, agency_name,name,valve_name,formattedDate,formattedTime);


                                // Add to the list
                                ticketList.add(ticket);
                            }

                            // Notify the adapter of the data change
                            ticketAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ticketListView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        noTicketsLayout.setVisibility(View.VISIBLE);
                        error.printStackTrace();
                        // You can display an error message here if needed
                    }
                }
        );

        // Add the request to the RequestQueue
        queue.add(jsonArrayRequest);
    }
}