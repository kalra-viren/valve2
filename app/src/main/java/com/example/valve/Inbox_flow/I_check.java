package com.example.valve.Inbox_flow;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import com.jsibbold.zoomage.ZoomageView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.valve.Approved_flow.A_ticket;
import com.example.valve.Menu.Dic_menu_screen;
import com.example.valve.Menu.Menu_screen;
import com.example.valve.R;
import com.example.valve.Util.APIS_URLs;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class I_check extends Fragment {

    private Button approve;
    private Button reject;
    private Button submit;
    private int id;
    private Map<String, String> imagePaths;
    private Button view_permit;



    public I_check() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_i_check, container, false);
        Bundle args = getArguments();
        if (args != null) {
            id = args.getInt("id");
            // Use the received integer as needed
            Log.d("A_TicketApproved", "Received integer: " + id);
        }
        approve=view.findViewById(R.id.Approve_btn);
        reject=view.findViewById(R.id.Reject_btn);
        LinearLayout inp=view.findViewById(R.id.Reject_inp_ll);
        submit=view.findViewById(R.id.submit_btn);
        view_permit=view.findViewById(R.id.view_permit_btn);



        fetchImagePaths(id,getContext(),view);

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Do you want to Approve this permit?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            dialog.dismiss();

                            // Show a ProgressDialog to block the screen interaction
                            ProgressDialog progressDialog = new ProgressDialog(getActivity());
                            progressDialog.setMessage("Processing...");
                            progressDialog.setCancelable(false); // Prevent dismissing with back button
                            progressDialog.show();

                            // Simulate the approval process (e.g., database update)
                            updateTicketStatus(id); // Updates the status in the permit database

                            // Dismiss ProgressDialog and navigate to the next screen
                            progressDialog.dismiss();

                            // Navigate to the new screen
                            Intent intent = new Intent(getActivity(), Dic_menu_screen.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            getActivity().finish();

                            // Show a success confirmation Toast
                            Toast.makeText(getActivity(), "Permit has been approved successfully", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        });



        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Reject Permit")
                        .setMessage("Do you want to Reject this permit?")
                        .setPositiveButton("Yes", (dialog, which) -> {

                            inp.setVisibility(View.VISIBLE);

                            dialog.dismiss();
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText inputField = inp.findViewById(R.id.input_for_reject); // Cast the View to TextInputEditText
                String s = inputField.getText().toString();
                if (s != null && !s.trim().isEmpty()) {
                    updateTicketStatus_r(id, s); // Updates the status in the permit database

                    // Create the dialog
                    AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("Permit has been rejected")
                            .setMessage("The permit has been successfully rejected.")
                            .setPositiveButton("OK", (dialogInterface, which) -> {
                                // Navigate to the new screen
                                Intent intent = new Intent(getActivity(), Dic_menu_screen.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                getActivity().finish();
                                dialogInterface.dismiss();
                                Toast.makeText(getActivity(), "Permit has been rejected", Toast.LENGTH_SHORT).show();
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setCancelable(false) // Prevents back button
                            .create(); // Create the AlertDialog instance

                    // Prevent dismissing the dialog on outside touch
                    dialog.setCanceledOnTouchOutside(false);

                    dialog.show(); // Show the dialog
                } else {
                    Toast.makeText(getActivity(), "Please enter a message for rejection", Toast.LENGTH_SHORT).show();
                }
            }
        });


        view_permit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                I_ViewPermit iViewPermit = new I_ViewPermit();
                Bundle bundle = new Bundle();
                bundle.putInt("id", id); // Assuming id is an integer, change type if necessary

                // Set arguments on the fragment
                iViewPermit.setArguments(bundle);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_carrier_id_ap, iViewPermit);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        return view;
    }

    private void fetchImagePaths(int permitId, Context context, View view) {
        // Define the API URL
        String url = APIS_URLs.fetchImagePaths_url + permitId;

        // Create a RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Create a JsonObjectRequest
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the response into a Map
                            imagePaths = new HashMap<>();
                            Iterator<String> keys = response.keys();

                            while (keys.hasNext()) {
                                String key = keys.next();
                                String value = response.getString(key);
                                imagePaths.put(key, value);

                                System.out.println("Column: " + key + ", Path: " + value);
                                Log.d("ImagePaths", "Column: " + key + ", Path: " + value);
                            }

                            // Do something with the imagePaths map
                            // Example: Pass it to the next screen or log it
                            for (Map.Entry<String, String> entry : imagePaths.entrySet()) {
                                System.out.println("Column: " + entry.getKey() + ", Path: " + entry.getValue());
                            }
//                            fetchAndDisplayImages(imagePaths,view);
                            loadImages(view);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        error.printStackTrace();
                    }
                }
        );

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }

//    loadImages- Function which displays images one by one in a loop

    private void loadImages(View view) {
        for (Map.Entry<String, String> entry : imagePaths.entrySet()) {
            String key = entry.getKey(); // The ImageView ID
            String filePath = entry.getValue(); // The file path for the image

            // Construct the full API URL
            String apiUrl = APIS_URLs.loadImages_url + filePath;

            // Find the ImageView by ID
            int imageViewId = getResources().getIdentifier(key, "id", requireActivity().getPackageName());
//            ImageView imageView = view.findViewById(imageViewId);
            ZoomageView imageView=view.findViewById(imageViewId);
            if (imageView != null) {
                // Make a Volley request to fetch the image
                ImageRequest imageRequest = new ImageRequest(
                        apiUrl,
                        response -> {
                            // Use Glide to load the image into the ImageView
                            Glide.with(imageView.getContext())
                                    .load(apiUrl)
                                    .into(imageView);
                        },
                        0, 0, ImageView.ScaleType.CENTER_CROP,
                        Bitmap.Config.RGB_565,
                        error -> {
                            // Handle Volley error
                            Log.e("ImageLoadError", "Failed to load image: " + error.getMessage());
                        }
                );

                // Add the request to the Volley request queue
                Volley.newRequestQueue(getActivity()).add(imageRequest);
            } else {
                Log.e("ImageViewError", "No ImageView found for key: " + key);
            }
        }
    }

    private void openZoomDialog(Bitmap image) {

        // Create the fullscreen dialog
        Dialog dialog = new Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.zoom_image_dialog);

        ImageView imageView = dialog.findViewById(R.id.zoomableImageView);
        ImageButton closeButton = dialog.findViewById(R.id.closeButton);

        // Set the bitmap to the ImageView
        imageView.setImageBitmap(image);

        // Add touch listener for zoom and pan (same as before)
        imageView.setOnTouchListener(new View.OnTouchListener() {
            private float scaleFactor = 1.0f;
            private ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(requireContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
                @Override
                public boolean onScale(ScaleGestureDetector detector) {
                    scaleFactor *= detector.getScaleFactor();
                    scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 10.0f));
                    imageView.setScaleX(scaleFactor);
                    imageView.setScaleY(scaleFactor);
                    return true;
                }
            });

            private float startX = 0f, startY = 0f;
            private float translateX = 0f, translateY = 0f;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX() - translateX;
                        startY = event.getY() - translateY;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        translateX = event.getX() - startX;
                        translateY = event.getY() - startY;
                        imageView.setTranslationX(translateX);
                        imageView.setTranslationY(translateY);
                        break;
                }
                scaleGestureDetector.onTouchEvent(event);
                return true;
            }
        });

        // Set up close button click listener
        closeButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }



    private void updateTicketStatus(int ticketId) {
        String url = APIS_URLs.updateTicketStatus_url; // Replace with your API URL

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id", ticketId);
            jsonBody.put("status", "APPROVED");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                response -> {
                    // Handle successful response
                    Log.d("API Response", "Ticket status updated successfully: " + response.toString());
                },
                error -> {
                    // Handle error
                    error.printStackTrace();
                    Log.e("API Error", "Failed to update ticket status");
                }
        );

        queue.add(request);
    }

    private void updateTicketStatus_r(int ticketId,String s) {
        String url = APIS_URLs.updateTicketStatus_url; // Replace with your API URL

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id", ticketId);
            jsonBody.put("status", "REJECTED");
            jsonBody.put("reject_ref_msg",s);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                response -> {
                    // Handle successful response
                    Log.d("API Response", "Ticket status updated successfully: " + response.toString());
                },
                error -> {
                    // Handle error
                    error.printStackTrace();
                    Log.e("API Error", "Failed to update ticket status");
                }
        );

        queue.add(request);
    }
}