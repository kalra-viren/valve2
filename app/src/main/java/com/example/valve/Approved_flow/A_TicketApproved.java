package com.example.valve.Approved_flow;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.valve.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class A_TicketApproved extends Fragment {
    private ListView ticketListView1;
    private ArrayList<Reading> ticketList1;
    private ReadingAdapter adapter1;

    private ListView ticketListView2;
    private ArrayList<Reading> ticketList2;
    private ReadingAdapter adapter2;

    private Button btn;
    private int id;
    private HashMap<String,String> V;
    private int flag1,flag2;

    private Button oxygen_b;
    private Button natural_gas;
    private RadioGroup rg;
    private RadioGroup rg2;

    public A_TicketApproved() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            id = args.getInt("id");
            Log.d("A_TicketApproved", "Received integer: " + id);
        }
        View view = inflater.inflate(R.layout.fragment_a__ticket_approved, container, false);
        oxygen_b=view.findViewById(R.id.oxygen_btn);
        natural_gas=view.findViewById(R.id.natural_gas_btn);
        ticketListView1 = view.findViewById(R.id.ticket_list_view1);
        ticketList1 = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        adapter1 = new ReadingAdapter(getActivity(), ticketList1);
        ticketListView1.setAdapter(adapter1);
        flag1=0;
        flag2=0;
        V=new HashMap<>();


// Inflate the custom layout
        LayoutInflater inflater2 = getLayoutInflater();
        View dialogView = inflater2.inflate(R.layout.dialog_custom_oxygen, null);


        oxygen_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate a new dialogView every time
                View dialogView = inflater2.inflate(R.layout.dialog_custom_oxygen, null);

                // Set the new dialogView to the builder
                builder.setView(dialogView);

                // Reference views in the new dialogView
                TextView heading = dialogView.findViewById(R.id.alert_heading);
                TextView textStatement = dialogView.findViewById(R.id.alert_text);
                rg=dialogView.findViewById(R.id.radioGroup1);
                RadioButton radioYes = dialogView.findViewById(R.id.radio_yes);
                RadioButton radioNo = dialogView.findViewById(R.id.radio_no);
                EditText inputField = dialogView.findViewById(R.id.alert_input_field);

                // Create and show the dialog
                builder.setPositiveButton("Add", (dialog, which) -> {
//                            String userInput = inputField.getText().toString();
                            boolean isYesSelected = radioYes.isChecked();
                            String readingValue = inputField.getText().toString();
                            if (!readingValue.isEmpty()) {
                                Reading newReading = new Reading(readingValue,"O2");
                                addTicketToFirstList(newReading);
                                String s = "O" + flag1;
                                int selectedId = rg.getCheckedRadioButtonId();

                                // Check which radio button was selected
                                String selectedValue = "";
                                if (selectedId == R.id.radio_yes) {
                                    selectedValue = "Yes";

                                } else if (selectedId == R.id.radio_no) {
                                    selectedValue = "No";

                                }
                                V.put("Oyes",selectedValue);
                                String value = V.get("Oyes");
                                Log.d("Radio Check"," "+value);
                                V.put(s, readingValue);
                                flag1++;
                                inputField.setText(""); // Clear input after adding
                                Toast.makeText(getActivity(), "Reading was added " + readingValue, Toast.LENGTH_SHORT).show();
                            } else {
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Please enter a reading")
                                        .setPositiveButton("OK", (dialog1, which1) -> {
                                            dialog.dismiss();
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .show();
                            }

                            // Handle the user input and radio selection
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        natural_gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate a new dialogView every time
                View dialogView = inflater2.inflate(R.layout.dialog_custom_natural, null);

                // Set the new dialogView to the builder
                builder.setView(dialogView);

                // Reference views in the new dialogView
                TextView heading = dialogView.findViewById(R.id.alert_heading);
                TextView textStatement = dialogView.findViewById(R.id.alert_text);
                rg2=dialogView.findViewById(R.id.radioGroup2);
                RadioButton radioYes = dialogView.findViewById(R.id.radio_yes);
                RadioButton radioNo = dialogView.findViewById(R.id.radio_no);
                EditText inputField = dialogView.findViewById(R.id.alert_input_field);

                // Create and show the dialog
                builder.setPositiveButton("Add", (dialog, which) -> {
//                            String userInput = inputField.getText().toString();
                            boolean isYesSelected = radioYes.isChecked();
                            String readingValue = inputField.getText().toString();
                            if (!readingValue.isEmpty()) {
                                Reading newReading = new Reading(readingValue,"NG");
                                addTicketToFirstList(newReading);
                                String s = "N" + flag2;
                                int selectedId = rg2.getCheckedRadioButtonId();

                                // Check which radio button was selected
                                String selectedValue = "";
                                if (selectedId == R.id.radio_yes) {
                                    selectedValue = "Yes";

                                } else if (selectedId == R.id.radio_no) {
                                    selectedValue = "No";

                                }
                                V.put("Nyes",selectedValue);
                                String value = V.get("Nyes");
                                Log.d("Radio Check"," "+value);
                                V.put(s, readingValue);
                                flag2++;
                                inputField.setText(""); // Clear input after adding
                                Toast.makeText(getActivity(), "Reading was added " + readingValue, Toast.LENGTH_SHORT).show();
                            } else {
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Please enter a reading")
                                        .setPositiveButton("OK", (dialog1, which1) -> {
                                            dialog.dismiss();
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .show();
                            }

                            // Handle the user input and radio selection
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        btn = view.findViewById(R.id.submit_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printMap();
                performApprovedAction(id);
            }
        });
        return view;
    }

    private void printMap() {
        if (V != null && !V.isEmpty()) {
            StringBuilder mapContents = new StringBuilder("Map Contents:\n");
            for (Map.Entry<String, String> entry : V.entrySet()) {
                mapContents.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            Log.d("A_TicketApproved", mapContents.toString());

            // Optionally, show it in a Toast or AlertDialog
            Toast.makeText(getActivity(), mapContents.toString(), Toast.LENGTH_LONG).show();
        } else {
            Log.d("A_TicketApproved", "The map is empty.");
        }
    }

    private void addTicketToFirstList(Reading reading) {
        ticketList1.add(reading);
        adapter1.notifyDataSetChanged();
    }
    private void addTicketToSecondList(Reading reading) {
        ticketList2.add(reading);
        adapter2.notifyDataSetChanged();
    }

    private void performApprovedAction(int x) {
        A_TicketApproved2 approvedFragment2 = new A_TicketApproved2();
        Bundle args = new Bundle();
        args.putInt("id", x);
        args.putSerializable("mapV", V);
        approvedFragment2.setArguments(args);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_carrier_id_ap, approvedFragment2); // Replace with your fragment container ID
        transaction.addToBackStack(null); // Optional: adds this transaction to the back stack
        transaction.commit();
    }
}