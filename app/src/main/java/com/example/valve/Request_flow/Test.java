package com.example.valve.Request_flow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.valve.Login_TPE.UserCredentials2;
import com.example.valve.Menu.Menu_screen;
import com.example.valve.R;
import com.example.valve.Util.APIS_URLs;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class Test extends Fragment {

    private LinearLayout container;// Your parent layout for CardViews
    private Button submitButton;
    private ArrayAdapter<String> spinnerAdapter;
    private String previousHeading = "";
    private CardView currentCardView = null;
    private LinearLayout currentInnerLayout = null;
    private HashMap<String, Integer> headingCountMap = new HashMap<>();
    private HashMap<String, String> colData = new HashMap<>();
    private int currentCount = 0;
    int count = 0;
    private List<String> encounteredHeadings = new ArrayList<>();// To keep track of the current count for the current heading
    private List<EditText> editTextList = new ArrayList<>();
    private List<Spinner> spinnerList = new ArrayList<>();
    private String url = "";
    private List<String> LS = new ArrayList<>();
    private List<String> LI = new ArrayList<>();
    private List<String> L = new ArrayList<>();
    private HashMap<String, VolleyMultipartRequest.DataPart> receivedMap;
    private EditText et;

    View view;
//    private int d=0,a=0,b=0,r=0,f=0;

    public Test() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_test, container, false);
        this.container = view.findViewById(R.id.container);
        Bundle arguments = getArguments();
        if (getArguments() != null) {
            receivedMap = (HashMap<String, VolleyMultipartRequest.DataPart>) getArguments().getSerializable("image_map");

            if (receivedMap != null) {
//                uploadBitmap(receivedMap);
            }
        } else {
            Toast.makeText(getActivity(), "No data passed to fragment", Toast.LENGTH_SHORT).show();
        }

//        Clearing the lists
//        Sessions logic
        encounteredHeadings.clear();
        headingCountMap.clear();
        spinnerList.clear();
        editTextList.clear();
        LS.clear();
        LI.clear();
        colData.clear();

//        for (QuestionData question : questionsList) {
//            createCardView(question);
//        }
//        finalizeCardView();
        // Your LinearLayout ID
        List<String> options = Arrays.asList("Select", "Yes", "No", "NA");
        spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, options);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Log.d("till here", "reached");
        et = view.findViewById(R.id.input_field_1);

        fetchQuestions(); // Fetch questions when the fragment is created
//        submitButton=view.findViewById(R.id.Submit_btn);
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Create an AlertDialog for submission confirmation
//                new AlertDialog.Builder(getActivity())
//                        .setTitle("Submit Permit")
//                        .setMessage("Do you want to submit the permit?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //here call a function which will call an API which will post the data of the
//                                // permit taking it from the model class and store it in the database
//                                //submitPermitData();
//
//                                // Show success dialog
//                                new AlertDialog.Builder(getActivity())
//                                        .setTitle("Success")
//                                        .setMessage("Permit submitted successfully!")
//                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.dismiss();
//                                                Intent intent = new Intent(getActivity(), Menu_screen.class);
//                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                                                startActivity(intent);
//                                                getActivity().finish();
//                                            }
//                                        })
//                                        .setIcon(android.R.drawable.ic_dialog_info)
//                                        .show();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        })
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .show();
//            }
//        });

//        List<String> headingsList = new ArrayList<>(headingCountMap.keySet());
//        for (int i=0;i<headingsList.size();i++){
//            String heading = headingsList.get(i);
//            int count = headingCountMap.get(heading);
//            for (int j=0;j<count;j++){
//                String a="",b="";
//                String s=heading+"_S_"+j;
//                String v=heading+"_I_"+j;
//                EditText inp=view.findViewById(R.id.v);
//                Spinner spin=view.findViewById(R.id.s);
//                if (spin != null) {
//                    if (spin.getSelectedItem() != null) {
//                        a = spin.getSelectedItem().toString();
//                    } else {
//                        a = ""; // Default value if no item is selected
//                    }
//                }
//                // Check if the EditText is not null
//                if (inp != null) {
//                    b = inp.getText().toString(); // This will return an empty string if nothing is entered
//                } else {
//                    b = ""; // Default value if inp is null
//                }
//                colData.put(s,a);
//                colData.put(v,b);
//            }
//        }
        return view;
    }

    private void fetchQuestions() {
        url = APIS_URLs.fetchQuestions_url; // Replace with your API URL

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("API Response", response.toString());
                        parseJsonResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("API Error", error.toString());
                        Toast.makeText(getActivity(), "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(getActivity()).add(jsonArrayRequest);
    }

    private void parseJsonResponse(JSONArray jsonArray) {
        Gson gson = new Gson();

        try {
            ArrayList<QuestionData> questions = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject questionObject = jsonArray.getJSONObject(i);

                int headingId = questionObject.getInt("headingId");
                String headingName = questionObject.getString("headingName");
                int questionId = questionObject.getInt("questionId");
                String questionText = questionObject.getString("questionText");

                // Create a new QuestionData object with the extracted values
                QuestionData questionData = new QuestionData(questionId, headingId, questionText, headingName);
                questions.add(questionData);
                Log.d("Parsed Question", questionData.getQuestionText() + " - " + questionData.getHeadingName());
            }

            // Create CardViews for each question
            for (QuestionData q : questions) {
                createCardView(q);
            }

            if (currentCardView != null) {
                container.addView(currentCardView);
                if (headingCountMap.containsKey(previousHeading)) {
                    // If the key exists, retrieve the current value and add the currentCount
                    int existingCount = headingCountMap.get(previousHeading);
                    headingCountMap.put(previousHeading, existingCount + currentCount);
                } else {
                    // If the key doesn't exist, add it with the currentCount
                    headingCountMap.put(previousHeading, currentCount);
                }
            }

//            createAuthorizationCardView();
//            createCompetentPersonCardView();
            createSubmitButton();

            for (String key : headingCountMap.keySet()) {
                Log.d("Heading Count", key + ": " + headingCountMap.get(key));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createCardView(QuestionData question) {
        // Check if the current heading is different from the previous one
        boolean isNewHeading = !question.getHeadingName().equals(previousHeading);

        if (isNewHeading) {

            if (!encounteredHeadings.contains(question.getHeadingName())) {
                encounteredHeadings.add(question.getHeadingName());
            }

            if (previousHeading != null) {
                if (headingCountMap.containsKey(previousHeading)) {
                    // If the key exists, retrieve the current value and add the currentCount
                    int existingCount = headingCountMap.get(previousHeading);
                    int a = existingCount + currentCount;
                    headingCountMap.put(previousHeading, a);
                } else {
                    // If the key doesn't exist, add it with the currentCount
                    headingCountMap.put(previousHeading, currentCount);
                }
            }

            // Reset current count for the new heading
            currentCount = 0;

            // If we already have a current CardView, add it to the container before creating a new one
            if (currentCardView != null) {
                container.addView(currentCardView);
            }

            // Create new CardView for the new heading
            currentCardView = new CardView(getContext());
            LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            // Set margins for the new heading
            cardLayoutParams.setMargins(0, 24, 0, 24); // Adjust as needed
            currentCardView.setLayoutParams(cardLayoutParams);
            currentCardView.setCardBackgroundColor(getResources().getColor(android.R.color.white));
            currentCardView.setCardElevation(4);
            currentCardView.setRadius(16); // Set corner radius

            // Create inner layout
            currentInnerLayout = new LinearLayout(getContext());
            currentInnerLayout.setOrientation(LinearLayout.VERTICAL);
            currentInnerLayout.setPadding(16, 16, 16, 16); // Padding for inner layout

            // Add heading TextView only once for each new heading
            TextView headingText = new TextView(getContext());
            headingText.setText(question.getHeadingName());
            headingText.setTextSize(16);
            headingText.setTypeface(null, Typeface.BOLD);
            currentInnerLayout.addView(headingText);

            // Set inner layout to the current card view
            currentCardView.addView(currentInnerLayout);

            // Update previousHeading to the current heading
            previousHeading = question.getHeadingName();
        }
        currentCount++;
        // Add question TextView
        TextView questionText = new TextView(getContext());
        questionText.setText(question.getQuestionText());
        questionText.setMaxLines(3);
        questionText.setEllipsize(TextUtils.TruncateAt.END);
        currentInnerLayout.addView(questionText);

        // Create Spinner and set adapter

        Spinner spinner = new Spinner(getContext());
        spinner.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        spinner.setAdapter(spinnerAdapter); // Use the pre-initialized adapter
        spinner.setSelection(0); // Set "Select" as the initial hint
        currentInnerLayout.addView(spinner);

        // Add Spinner reference to the list
        spinnerList.add(spinner);

        // Create EditText for remarks
        EditText remarkInput = new EditText(getContext());
        remarkInput.setHint("Remark");
        remarkInput.setMaxLines(1);
        remarkInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        currentInnerLayout.addView(remarkInput);

        // Add EditText reference to the list
        editTextList.add(remarkInput);

        int c = 0;
        StringBuilder s = new StringBuilder();
        String qt = question.getQuestionText();
        for (int i = 0; i < qt.length(); i++) {
            if (qt.charAt(i) == ' ') {
                c++;
            }
            if (c == 4) {
                break;
            }
            s.append(qt.charAt(i));
        }
        String hd = question.getHeadingName();
        String S = (hd + "_" + s + "_S").trim().replace(" ", "_");
        String I = (hd + "_" + s + "_I").trim().replace(" ", "_");
        LS.add(S);
        LI.add(I);
    }

//    private void createAuthorizationCardView() {
//        // Create CardView
//        CardView cardView = new CardView(getContext());
//        LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        cardLayoutParams.setMargins(0, 16, 0, 0);
//        cardView.setLayoutParams(cardLayoutParams);
//        cardView.setCardBackgroundColor(getResources().getColor(android.R.color.white));
//        cardView.setCardElevation(4);
//        cardView.setRadius(16); // Set corner radius
//
//        // Create inner layout
//        LinearLayout innerLayout = new LinearLayout(getContext());
//        innerLayout.setOrientation(LinearLayout.VERTICAL);
//        innerLayout.setPadding(16, 16, 16, 16); // Padding for inner layout
//
//        // Add static content to the CardView
//        TextView authorizationText = new TextView(getContext());
//        authorizationText.setText("1) Authorization: MGL site in-charge will be the authorizing person for confined space entry");
//        authorizationText.setTextSize(12);
//        authorizationText.setTypeface(null, Typeface.BOLD); // Bold text
//
//        // Create a vertical layout for additional information
//        LinearLayout infoLayout = new LinearLayout(getContext());
//        infoLayout.setOrientation(LinearLayout.VERTICAL);
//
//        // First Row of TextViews
//        LinearLayout firstRow = new LinearLayout(getContext());
//        firstRow.setOrientation(LinearLayout.HORIZONTAL);
//
//        TextView siteText = new TextView(getContext());
//        siteText.setText("Site:");
//
//        TextView locationText = new TextView(getContext());
//        locationText.setText("Location:");
//
//        firstRow.addView(siteText);
//        firstRow.addView(locationText);
//
//        // Second Row of TextViews
//        LinearLayout secondRow = new LinearLayout(getContext());
//        secondRow.setOrientation(LinearLayout.HORIZONTAL);
//
//        TextView startDateText = new TextView(getContext());
//        startDateText.setText("Start Date:");
//
//        TextView nameText = new TextView(getContext());
//        nameText.setText("Name:");
//
//        secondRow.addView(startDateText);
//        secondRow.addView(nameText);
//
//        // Third Row of TextViews
//        LinearLayout thirdRow = new LinearLayout(getContext());
//        thirdRow.setOrientation(LinearLayout.HORIZONTAL);
//
//        TextView signatureText = new TextView(getContext());
//        signatureText.setText("Signature:");
//
//        thirdRow.addView(signatureText);
//
//        // Add rows to info layout
//        infoLayout.addView(firstRow);
//        infoLayout.addView(secondRow);
//        infoLayout.addView(thirdRow);
//
//        // Add all views to inner layout
//        innerLayout.addView(authorizationText);
//        innerLayout.addView(infoLayout);
//
//        // Add inner layout to CardView
//        cardView.addView(innerLayout);
//
//        // Add the Authorization CardView to your container layout
//        container.addView(cardView);
//    }
//
//    private void createCompetentPersonCardView() {
//        // Create CardView
//        CardView cardView = new CardView(getContext());
//        LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        cardLayoutParams.setMargins(0, 16, 0, 0); // Add top margin for spacing
//        cardView.setLayoutParams(cardLayoutParams);
//        cardView.setCardBackgroundColor(getResources().getColor(android.R.color.white));
//        cardView.setCardElevation(4);
//        cardView.setRadius(16); // Set corner radius
//
//        // Create inner layout
//        LinearLayout innerLayout = new LinearLayout(getContext());
//        innerLayout.setOrientation(LinearLayout.VERTICAL);
//        innerLayout.setPadding(16, 16, 16, 16); // Padding for inner layout
//
//        // Add static content to the CardView
//        TextView competentText = new TextView(getContext());
//        competentText.setText("2) Competent person at site: In case of Nallah crossing and tank cleaning, the competent person at site will be TPE. In case of Steel valve chamber entry, competent person will be MGL Technician or contractor representative (Engg/Sup.).");
//        competentText.setTextSize(12);
//        competentText.setTypeface(null, Typeface.BOLD); // Bold text
//
//        // Create a vertical layout for additional information
//        LinearLayout infoLayout = new LinearLayout(getContext());
//        infoLayout.setOrientation(LinearLayout.VERTICAL);
//
//        // First Row of TextViews
//        LinearLayout firstRow = new LinearLayout(getContext());
//        firstRow.setOrientation(LinearLayout.HORIZONTAL);
//
//        TextView siteText = new TextView(getContext());
//        siteText.setText("Site:");
//
//        TextView locationText = new TextView(getContext());
//        locationText.setText("Location:");
//
//        firstRow.addView(siteText);
//        firstRow.addView(locationText);
//
//        // Second Row of TextViews
//        LinearLayout secondRow = new LinearLayout(getContext());
//        secondRow.setOrientation(LinearLayout.HORIZONTAL);
//
//        TextView startDateText = new TextView(getContext());
//        startDateText.setText("Start Date:");
//
//        TextView nameText = new TextView(getContext());
//        nameText.setText("Name:");
//
//        secondRow.addView(startDateText);
//        secondRow.addView(nameText);
//
//        // Third Row of TextViews
//        LinearLayout thirdRow = new LinearLayout(getContext());
//        thirdRow.setOrientation(LinearLayout.HORIZONTAL);
//
//        TextView signatureText = new TextView(getContext());
//        signatureText.setText("Signature:");
//
//        thirdRow.addView(signatureText);
//
//        // Add rows to info layout
//        infoLayout.addView(firstRow);
//        infoLayout.addView(secondRow);
//        infoLayout.addView(thirdRow);
//
//        // Add all views to inner layout
//        innerLayout.addView(competentText);
//        innerLayout.addView(infoLayout);
//
//        // Add inner layout to CardView
//        cardView.addView(innerLayout);
//
//        // Add the Competent Person CardView to your container layout
//        container.addView(cardView);  // This ensures it is a separate CardView
//    }

    private void createSubmitButton() {
        submitButton = new Button(getContext());
        submitButton.setId(View.generateViewId());

        // Set the layout parameters
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics()),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.topMargin = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());

        submitButton.setLayoutParams(layoutParams);
        submitButton.setText("Submit"); // Text with only the first letter capitalized
        submitButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        submitButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        submitButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_button)); // Set rounded background

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupSpinners();   //  uncomment below for spinner selection check

                fun1();
                int v = fun2();
                if (v != 1) {
                    Log.d("Validation", "fun2() returned 0, waiting for user to fill in the data.");
                    // Show a message to the user to fill in missing data if needed
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Incomplete Data")
                            .setMessage("Please fill in the required data before submitting.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                    // Exit the onClick method so the user can re-submit after adding data
                    return;
                }
                beforeGatherInput();
                String cross_ref = et.getText().toString();
                colData.put("cross_ref_no", cross_ref);
                for (Map.Entry<String, String> entry : colData.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    Log.d("colData Entry", "Key: " + key + ", Value: " + value);
                }

                Log.d("Submit button clicked", "submit btn clicked");

                new AlertDialog.Builder(getActivity())
                        .setTitle("Submit Permit")
                        .setMessage("Do you want to submit the permit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(new Date());
                                Log.d("DateTime", "Captured Date and Time: " + currentDateTime);

                                submitPermitData(new PermitDataCallback() {
                                    @Override
                                    public void onPermitDataSubmitted(int id) {
                                        Log.d("PermitData", "Received ID: " + id);

                                        // Creating the success dialog
                                        AlertDialog successDialog = new AlertDialog.Builder(getActivity())
                                                .setTitle("Success")
                                                .setMessage("Permit submitted successfully! Your token number is " + id)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        Intent intent = new Intent(getActivity(), Menu_screen.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                        startActivity(intent);
                                                        getActivity().finish();
                                                    }
                                                })
                                                .setIcon(android.R.drawable.ic_dialog_info)
                                                .create(); // Create the AlertDialog instance

                                        // Set properties on the created dialog
                                        successDialog.setCancelable(false);  // Prevent dismissing with back button
                                        successDialog.setCanceledOnTouchOutside(false);  // Prevent dismissing when touched outside

                                        successDialog.show(); // Show the dialog
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setCancelable(false) // Prevent dismissing with back button
                        .create()  // Create the AlertDialog instance
                        .show();   // Show the dialog

            }
        });


        // Add the Submit button to your container layout
        container.addView(submitButton);
    }

    private void fun1() {

        for (int i = 0; i < spinnerList.size(); i++) {
            Log.d("spinenr list size", " " + spinnerList.size());
            Spinner s = spinnerList.get(i);
            if (s.getSelectedItemPosition() == 0) {
                Log.d("null found", "found");
                s.setTag(" " + i);
                String temp = " " + i;
                L.add(temp);
            }
        }
        for (int i = 0; i < L.size(); i++) {
            Log.d("output for fun1()", " " + L.get(i));
        }
    }

    private int fun2() {
        if (L.size() != 0) {
//            Highlight the spinner
            for (int i = 0; i < L.size(); i++) {
                Spinner s = (Spinner) view.findViewWithTag(L.get(i));
                s.setBackgroundResource(R.drawable.custom_red_border);
            }

            L.clear();
            return 0;
        }
        L.clear();
        return 1;
    }

    private void beforeGatherInput() {
        if ((LS.size() != LI.size()) || (spinnerList.size() != editTextList.size())) {
            Log.d("size mismatch", "size mismatch error");
        } else {
            for (int i = 0; i < LS.size(); i++) {
                Spinner spin = spinnerList.get(i);
                EditText inp = editTextList.get(i);
                String a = spin.getSelectedItem() != null ? spin.getSelectedItem().toString() : c();
                String b = inp != null ? inp.getText().toString() : ""; // This will return an empty string if nothing is entered
                colData.put(LS.get(i), a);
                colData.put(LI.get(i), b);
            }

            // Add fixed data
            UserCredentials2 userCredentials2 = UserCredentials2.getInstance(getActivity());
            colData.put("PONo", userCredentials2.getPoNumber());
            colData.put("STCNo", userCredentials2.getStcNo());
            UserSelection userSelection = UserSelection.getInstance();
            colData.put("AIC_name", userSelection.getSelectedDIC());
            colData.put("Valve_name", userSelection.getSelectedName());
            colData.put("District", userSelection.getSelectedDistrict());
            colData.put("Valve_id", userSelection.getSelectedNumber());
            colData.put("TPE_name", userSelection.getTpeName());


            // Initialize StringBuilder
            StringBuilder s = new StringBuilder();

            // Call the API and pass a callback to handle the response
            nameToEid(userSelection.getSelectedDIC(), s, new ApiCallback() {
                @Override
                public void onApiResponse(String response) {
                    // This is called when the response is received
                    Log.d("DIC Name", " " + response); // Now you can log it here
                    colData.put("Emp_id", response);
                }
            });
        }
    }


    private void nameToEid(String selectedDIC, StringBuilder s, ApiCallback callback) {
        String url = APIS_URLs.nameToEid_url;
        String finalUrl = url + selectedDIC;

        // Create a request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, finalUrl,
                response -> {
                    // Success callback
                    Log.d("API Response", response);

                    // Append response to StringBuilder
                    s.append(response);

                    // Notify the callback that the response is ready
                    if (callback != null) {
                        callback.onApiResponse(s.toString());
                    }
                },
                error -> {
                    // Error callback
                    Log.e("API Error", error.toString());
                });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    private String c() {

        return "";
    }

    private void setupSpinners() {
        for (int i = 0; i < spinnerList.size(); i++) {
            Spinner s = spinnerList.get(i);

            // Add the OnItemSelectedListener
            s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // Check if the selected item is not the placeholder (position 0)
                    if (position != 0) {
                        // Reset the background to the default state
                        s.setBackgroundResource(android.R.color.transparent);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Do nothing here, as this only occurs when no item is selected
                }
            });
        }
    }

    private void submitPermitData(PermitDataCallback callback) {
//        uploadBitmap(receivedMap); //for uploading the images in the database

        JSONObject data = new JSONObject();
        JSONObject dataWrapper = new JSONObject(); // Create a wrapper object

        try {
            // Add key-value pairs to the 'data' object
            for (Map.Entry<String, String> entry : colData.entrySet()) {
                data.put(entry.getKey(), entry.getValue());
            }

            // Now wrap the data object inside the 'data' field
            dataWrapper.put("data", data);

            // Create the URL for your API
            String url = APIS_URLs.dataEntry_url; // Replace with your actual API URL

            // Create a new JSON request
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, dataWrapper,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Handle the response from the server
                            try {
                                String message = response.getString("message");
                                int id = response.getInt("id");
                                Toast.makeText(getActivity(), message + " ID: " + id, Toast.LENGTH_SHORT).show();
                                callback.onPermitDataSubmitted(id);
//                                String key = "id";
//                                byte[] idBytes = String.valueOf(id).getBytes();
//                                VolleyMultipartRequest.DataPart dataPart = new VolleyMultipartRequest.DataPart("id", idBytes);
//                                receivedMap.put(key, dataPart);
                                uploadBitmap(receivedMap,id); //for uploading the images in the database
                                // Do something with the response
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "Error parsing response", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error
                            error.printStackTrace();
                        }
                    });

            // Add the request to the request queue
            Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void uploadBitmap(Map<String, VolleyMultipartRequest.DataPart> imageMap, int id) {
        String url = APIS_URLs.uploadImage_url;

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(new String(response.data));
                        // Get the 'paths' array from the JSON response
                        JSONArray pathsArray = obj.getJSONArray("paths");
                        // Create a new HashMap to store the paths
                        Map<String, String> pathsMap = new HashMap<>();
                        for (int i = 0; i < pathsArray.length(); i++) {
                            String path = pathsArray.getString(i);

                            // Switch-case based on the content of the path
//                            change the name of the images from the launchcamera function()
                            if (path.contains("Gascoseeker")) {
                                pathsMap.put("Gascoseeker", path);
                            } else if (path.contains("Hira")) {
                                pathsMap.put("Hira", path);
                            } else if (path.contains("Emergency")) {
                                pathsMap.put("Emergency", path);
                            } else if (path.contains("STC")) {
                                pathsMap.put("STC", path);
                            } else if (path.contains("Tool")) {
                                pathsMap.put("Tool", path);
                            } else if (path.contains("Technician")) {
                                pathsMap.put("Technician", path);
                            } else {
                                pathsMap.put("other_image", path); // Default case if no match
                            }
                        }
                        if (imageMap.containsKey("id")) {
                            VolleyMultipartRequest.DataPart idDataPart = imageMap.get("id");
                            if (idDataPart != null) {
                                // Convert the byte array to a string
                                String idValue = new String(idDataPart.getContent());
                                pathsMap.put("id", idValue);
                            }
                        }
//                        JSONObject j = new JSONObject(pathsMap);
//                        sendDataToApi(j);

                        Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.e("GotError", "Error: " + error.toString());
                    if (error.networkResponse != null) {
                        Log.e("GotError", "Response code: " + error.networkResponse.statusCode);
                        Log.e("GotError", "Response data: " + new String(error.networkResponse.data));
                    }
                }) {

            @Override
            protected Map<String, DataPart> getByteData() {

                return imageMap;
            }
            @Override
            protected Map<String, String> getParams() {
                // Add your text parameters here
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                return params;
            }
        };

        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }

//    private void sendDataToApi(JSONObject jsonObject) {
//        // Instantiate the RequestQueue
//        RequestQueue queue = Volley.newRequestQueue(getActivity());
//        String API_URL = APIS_URLs.uploadPath_url;
//        // Create the request to send to the API
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.POST,
//                API_URL,
//                jsonObject,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // Handle successful response
//                        Log.d("API Response", response.toString());
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Handle error response
//                        Log.e("API Error", error.toString());
//                    }
//                });
//
//        // Add the request to the RequestQueue
//        queue.add(jsonObjectRequest);
//    }

}