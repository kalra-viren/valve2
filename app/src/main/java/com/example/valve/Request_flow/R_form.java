package com.example.valve.Request_flow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.valve.Menu.Menu_screen;
import com.example.valve.R;


public class R_form extends Fragment {



    public R_form() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_r_form, container, false);
        String[] items = {"Select","Yes", "No", "N/A"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        PermitData permitData = PermitDataHolder.getInstance();

        Spinner spinner1 = view.findViewById(R.id.description_spinner_1);
        EditText input1 = view.findViewById(R.id.description_input_1);
        Spinner spinner2 = view.findViewById(R.id.description_spinner_2);
        EditText input2 = view.findViewById(R.id.description_input_2);

        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        permitData.setDescriptionSpinnerValue1(spinner1.getSelectedItem().toString());
        permitData.setDescriptionInputValue1(input1.getText().toString());
        permitData.setDescriptionSpinnerValue2(spinner2.getSelectedItem().toString());
        permitData.setDescriptionInputValue2(input2.getText().toString());
//        Acrssing this data
//        PermitData permitData = PermitDataHolder.getInstance();
//        String value1 = permitData.getDescriptionSpinnerValue1();
//        String inputValue1 = permitData.getDescriptionInputValue1();




        Spinner spinner3 = view.findViewById(R.id.atmosphere_spinner_1);
        EditText input3 = view.findViewById(R.id.atmosphere_input_1);
        Spinner spinner4 = view.findViewById(R.id.atmosphere_spinner_2);
        EditText input4 = view.findViewById(R.id.atmosphere_input_2);

        spinner3.setAdapter(adapter);
        spinner4.setAdapter(adapter);

        permitData.setAtmosphereSpinnerValue1(spinner3.getSelectedItem().toString());
        permitData.setAtmosphereInputValue1(input3.getText().toString());
        permitData.setAtmosphereSpinnerValue2(spinner4.getSelectedItem().toString());
        permitData.setAtmosphereInputValue2(input4.getText().toString());




        Spinner spinner5 = view.findViewById(R.id.blower_spinner_1);
        EditText input5 = view.findViewById(R.id.blower_input_1);
        Spinner spinner6 = view.findViewById(R.id.blower_spinner_2);
        EditText input6 = view.findViewById(R.id.blower_input_2);

        spinner5.setAdapter(adapter);
        spinner6.setAdapter(adapter);

        permitData.setBlowerSpinnerValue1(spinner5.getSelectedItem().toString());
        permitData.setBlowerInputValue1(input5.getText().toString());
        permitData.setBlowerSpinnerValue2(spinner6.getSelectedItem().toString());
        permitData.setBlowerInputValue2(input6.getText().toString());




        Spinner spinner7 = view.findViewById(R.id.rescue_spinner_1);
        EditText input7 = view.findViewById(R.id.rescue_input_1);
        Spinner spinner8 = view.findViewById(R.id.rescue_spinner_2);
        EditText input8 = view.findViewById(R.id.rescue_input_2);
        Spinner spinner9 = view.findViewById(R.id.rescue_spinner_3);
        EditText input9 = view.findViewById(R.id.rescue_input_3);
        Spinner spinner10 = view.findViewById(R.id.rescue_spinner_4);
        EditText input10 = view.findViewById(R.id.rescue_input_4);   // Assuming you have these in your layout
        Spinner spinner11 = view.findViewById(R.id.rescue_spinner_5); // Assuming you have these in your layout
        EditText input11 = view.findViewById(R.id.rescue_input_5);

        spinner7.setAdapter(adapter);
        spinner8.setAdapter(adapter);
        spinner9.setAdapter(adapter);
        spinner10.setAdapter(adapter);
        spinner11.setAdapter(adapter);

        permitData.setRescueSpinnerValue1(spinner7.getSelectedItem().toString());
        permitData.setRescueInputValue1(input7.getText().toString());
        permitData.setRescueSpinnerValue2(spinner8.getSelectedItem().toString());
        permitData.setRescueInputValue2(input8.getText().toString());
        permitData.setRescueSpinnerValue3(spinner9.getSelectedItem().toString());
        permitData.setRescueInputValue3(input9.getText().toString());
        permitData.setRescueSpinnerValue4(spinner10.getSelectedItem().toString());
        permitData.setRescueInputValue4(input10.getText().toString());
        permitData.setRescueSpinnerValue5(spinner11.getSelectedItem().toString());
        permitData.setRescueInputValue5(input11.getText().toString());




        Spinner spinner12 = view.findViewById(R.id.fire_spinner_1);
        EditText input12 = view.findViewById(R.id.fire_input_1);
        Spinner spinner13 = view.findViewById(R.id.fire_spinner_2);
        EditText input13 = view.findViewById(R.id.fire_input_2);
        Spinner spinner14 = view.findViewById(R.id.fire_spinner_3);
        EditText input14 = view.findViewById(R.id.fire_input_3);
        Spinner spinner15 = view.findViewById(R.id.fire_spinner_4); // Assuming you have these in your layout
        EditText input15 = view.findViewById(R.id.fire_input_4);   // Assuming you have these in your layout
        Spinner spinner16 = view.findViewById(R.id.fire_spinner_5); // Assuming you have these in your layout
        EditText input16 = view.findViewById(R.id.fire_input_5);   // Assuming you have these in your layout

        spinner12.setAdapter(adapter);
        spinner13.setAdapter(adapter);
        spinner14.setAdapter(adapter);
        spinner15.setAdapter(adapter);
        spinner16.setAdapter(adapter);

        permitData.setFireSpinnerValue1(spinner12.getSelectedItem().toString());
        permitData.setFireInputValue1(input12.getText().toString());
        permitData.setFireSpinnerValue2(spinner13.getSelectedItem().toString());
        permitData.setFireInputValue2(input13.getText().toString());
        permitData.setFireSpinnerValue3(spinner14.getSelectedItem().toString());
        permitData.setFireInputValue3(input14.getText().toString());
        permitData.setFireSpinnerValue4(spinner15.getSelectedItem().toString());
        permitData.setFireInputValue4(input15.getText().toString());
        permitData.setFireSpinnerValue5(spinner16.getSelectedItem().toString());
        permitData.setFireInputValue5(input16.getText().toString());


        Button submit_btn = view.findViewById(R.id.Submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an AlertDialog for submission confirmation
                new AlertDialog.Builder(getActivity())
                        .setTitle("Submit Permit")
                        .setMessage("Do you want to submit the permit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //here call a function which will call an API which will post the data of the
                                // permit taking it from the model class and store it in the database



                                // Show success dialog
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Success")
                                        .setMessage("Permit submitted successfully!")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Dismiss the success dialog
                                                dialog.dismiss();
                                                Intent intent = new Intent(getActivity(), Menu_screen.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                startActivity(intent);

                                                // Optionally finish this activity if you don't want to return here
                                                getActivity().finish();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_info) // Optional: Add an icon to the success dialog
                                        .show(); // Show the success dialog
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Dismiss the confirmation dialog
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert) // Optional: Add an icon to the confirmation dialog
                        .show(); // Show the confirmation dialog
            }
        });


        return view;
    }
}