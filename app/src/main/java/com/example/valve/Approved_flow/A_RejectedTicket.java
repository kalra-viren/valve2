package com.example.valve.Approved_flow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.valve.R;
import com.example.valve.Util.APIS_URLs;

import org.json.JSONException;
import org.json.JSONObject;

public class A_RejectedTicket extends Fragment {
    private int id;

    private TextView rejectMessageTextView; // To display the reject_ref_msg

    public A_RejectedTicket() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a_rejected_ticket, container, false);
        Bundle args = getArguments();
        if (args != null) {
            id = args.getInt("id");
            // Use the received integer as needed
            Log.d("A_TicketApproved", "Received integer: " + id);
        }

        // Initialize the TextView
        rejectMessageTextView = view.findViewById(R.id.reject_message_textview);

        // Call the API to fetch the reject_ref_msg
        fetchRejectMessage();

        return view;
    }

    private void fetchRejectMessage() {
        // Replace this with your API URL and dynamic ID if needed
        String API_URL = APIS_URLs.reject_ref_msg_url+id;

        // Initialize the Volley RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

        // Create a GET request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                API_URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Extract reject_ref_msg from the response
                            String rejectMessage = response.getString("reject_ref_msg");

                            // Display the message in TextView
                            rejectMessageTextView.setText(rejectMessage);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(requireContext(), "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors like network failure or 404
                        if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
                            rejectMessageTextView.setText("No reject message found.");
                        } else {
                            Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        // Add the request to the queue
        requestQueue.add(jsonObjectRequest);
    }
}
