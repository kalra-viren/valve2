package com.example.valve.Login_TPE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.valve.Menu.Menu_screen;
import com.example.valve.R;
import com.example.valve.Select_Activity.Select_Activity;
import com.example.valve.Util.APIS_URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

public class tpe extends Fragment {
    private SharedPreferences encryptedSharedPreferences;
    private CheckBox rememberMeCheckBox;


    public tpe() {
        // Required empty public constructor
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tpe, container, false);
        Button submitButton = view.findViewById(R.id.next_button);
        EditText poNumberField = view.findViewById(R.id.input_field_1);
        EditText pass = view.findViewById(R.id.input_field_2);
        EditText aicSteNumberField = view.findViewById(R.id.input_field_3);
        ImageView togglePassword = view.findViewById(R.id.toggle_password_visibility);

        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            encryptedSharedPreferences = EncryptedSharedPreferences.create(
                    "user_credentials",  // SharedPreferences file name
                    masterKeyAlias,      // Master key for encryption
                    getActivity(),                // Context
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        // Call the prefill method to populate fields if credentials are saved
        prefillCredentials(view);
        @SuppressLint("ClickableViewAccessibility")
        FrameLayout fl = view.findViewById(R.id.root_layout);
        fl.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                hideKeyboard();
            }
            return false; // Allow touch events to propagate
        });


        togglePassword.setOnClickListener(a -> {
            if (pass.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                togglePassword.setImageResource(R.drawable.ic_eye); // Update to open-eye icon
            } else {
                pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                togglePassword.setImageResource(R.drawable.ic_eye_closed); // Update to closed-eye icon
            }
            pass.setSelection(pass.getText().length()); // Keep cursor at the end
        });
        rememberMeCheckBox = view.findViewById(R.id.remember_me_checkbox);


        // Set click listener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from EditText fields


                String poNumber = poNumberField.getText().toString();
                String password = pass.getText().toString();
                String aicSteNumber = aicSteNumberField.getText().toString();


                // Validate inputs (optional)
                if (TextUtils.isEmpty(poNumber) || TextUtils.isEmpty(password) || TextUtils.isEmpty(aicSteNumber)) {
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Call the API to validate credentials

//                Commet below line to remove the bypass
//                navigateToTabsScreen();



                validateCredentials(poNumber, password, aicSteNumber);


            }
        });
        return view;
    }

    private void validateCredentials(String poNumber, String password, String stcNo) {
        ProgressBar progressBar = getView().findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        String url = APIS_URLs.validateCredentials_url_tpe;

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("PONo", poNumber);
            jsonBody.put("Password", password);
            jsonBody.put("STCNo", stcNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            String isValid = response.getString("valid"); // Adjust according to your API's JSON structure
                            if ("yes".equals(isValid.trim())) {
                                Toast.makeText(getActivity(), "Credentials are valid", Toast.LENGTH_SHORT).show();
                                Log.i("API Response", "Credentials are valid");



                                UserCredentials2 userCredentials = UserCredentials2.getInstance(getActivity());
                                userCredentials.saveCredentials(poNumber, password, stcNo);

                                if (rememberMeCheckBox.isChecked()) {
                                    // Save credentials securely
                                    encryptedSharedPreferences.edit()
                                            .putString("po_number", poNumber)
                                            .putString("password", password)
                                            .putString("stc_number", stcNo)
                                            .apply();
                                } else {
                                    // Clear saved credentials if "Remember Password" is unchecked
                                    encryptedSharedPreferences.edit().clear().apply();
                                }

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
                        progressBar.setVisibility(View.GONE);
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

    private void hideKeyboard() {
        View view = requireActivity().getCurrentFocus(); // Get the current focused view
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    private void navigateToTabsScreen() {
        Intent intent = new Intent(getActivity(), Select_Activity.class);
        startActivity(intent);
    }

    private void prefillCredentials(View view) {
        String savedPO = encryptedSharedPreferences.getString("po_number", null);
        String savedPassword = encryptedSharedPreferences.getString("password", null);
        String savedSTC = encryptedSharedPreferences.getString("stc_number", null);

        if (savedPO != null && savedPassword != null && savedSTC != null) {
            // Prefill the EditText fields
            EditText poField = view.findViewById(R.id.input_field_1);
            EditText passwordField = view.findViewById(R.id.input_field_2);
            EditText stcField = view.findViewById(R.id.input_field_3);

            poField.setText(savedPO);
            passwordField.setText(savedPassword);
            stcField.setText(savedSTC);
        }
    }

}