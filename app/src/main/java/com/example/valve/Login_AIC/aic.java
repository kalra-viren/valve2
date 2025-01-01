package com.example.valve.Login_AIC;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
//import com.example.valve.Login_TPE.UserCredentials2;
import com.example.valve.Login_TPE.UserCredentials2;
import com.example.valve.Menu.Dic_menu_screen;
import com.example.valve.R;
import com.example.valve.Tabs_screen;
import com.example.valve.Util.APIS_URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class aic extends Fragment {

    public aic() {
        // Required empty public constructor
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aic, container, false);

        Button submitButton = view.findViewById(R.id.next_button);
        EditText empid = view.findViewById(R.id.input_field_1);
        EditText user_id = view.findViewById(R.id.input_field_2);
        EditText pass = view.findViewById(R.id.input_field_3);
        ImageView togglePassword = view.findViewById(R.id.toggle_password_visibility);
        @SuppressLint("ClickableViewAccessibility")
        FrameLayout fl = view.findViewById(R.id.root_layout);
        fl.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                hideKeyboard();
            }
            return false; // Allow touch events to propagate
        });

        togglePassword.setOnClickListener(v ->

        {
            if (pass.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                togglePassword.setImageResource(R.drawable.ic_eye); // Update to open-eye icon
            } else {
                pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                togglePassword.setImageResource(R.drawable.ic_eye_closed); // Update to closed-eye icon
            }
            pass.setSelection(pass.getText().length()); // Keep cursor at the end
        });


        // Set click listener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from EditText fields (assuming you have EditText views defined)


                String e = empid.getText().toString();
                String uid = user_id.getText().toString();
                String pas = pass.getText().toString();

                if (TextUtils.isEmpty(e) || TextUtils.isEmpty(uid) || TextUtils.isEmpty(pas)) {
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }


                // Save credentials using UserCredentials model
                UserCredentials.getInstance(getActivity()).saveCredentials(e, uid, pas);


                // Navigate to Tabs_screen activity
//                Commet below line to remove the bypass
//                navigateToTabsScreen();

                validateCredentials(e, uid, pas);
            }
        });

        return view;
    }
    private void hideKeyboard() {
        View view = requireActivity().getCurrentFocus(); // Get the current focused view
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


    private void validateCredentials(String empid, String userid, String pass) {
        String url = APIS_URLs.validateCredentials_url; //My api url

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("empId", empid);
            jsonBody.put("userId", userid);
            jsonBody.put("password", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String isValid = response.getString("valid"); // Adjust according to your API's JSON structure
                            if ("yes".equals(isValid.trim())) {
                                Toast.makeText(getActivity(), "Credentials are valid", Toast.LENGTH_SHORT).show();
                                Log.i("API Response", "Credentials are valid");
                                UserCredentials userCredentials = UserCredentials.getInstance(getActivity());
                                userCredentials.saveCredentials(empid, userid, pass);
                                navigateToTabsScreen();
                            } else {
                                Toast.makeText(getActivity(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                                Log.i("API Response", "Invalid credentials");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error parsing response", Toast.LENGTH_SHORT).show();
                            Log.e("API Error", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "An error occurred";
                        if (error.networkResponse != null) {
                            errorMessage = "Error Code: " + error.networkResponse.statusCode;
                        } else if (error instanceof com.android.volley.TimeoutError) {
                            errorMessage = "Request Timeout. Please try again.";
                        } else if (error instanceof com.android.volley.NoConnectionError) {
                            errorMessage = "No Internet connection.";
                        }

                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                        Log.e("API Error", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json"); // Use application/json for JSON requests
                return headers;
            }
        };

        queue.add(jsonObjectRequest);
    }

    private void navigateToTabsScreen() {
        // Create an intent to start the Tabs_screen activity
        Intent intent = new Intent(getActivity(), Dic_menu_screen.class);
        startActivity(intent);
    }

}