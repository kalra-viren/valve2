package com.example.valve.Review_flow;

import android.app.AlertDialog;
import android.app.Dialog;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.valve.Review_flow.Re_TicketApproved;
import com.example.valve.Menu.Dic_menu_screen;
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

public class Re_TicketApproved extends Fragment {
    private int id;
    private Button submit;
    private Button permit;
    private Map<String, String> imagePaths;


    public Re_TicketApproved() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_re__ticket_approved, container, false);
        Bundle args = getArguments();
        if (args != null) {
            id = args.getInt("id");
            // Use the received integer as needed
            Log.d("A_TicketApproved", "Received integer: " + id);
        }
        submit=view.findViewById(R.id.submit_btn);
        permit=view.findViewById(R.id.view_permit_btn);
        LinearLayout inp=view.findViewById(R.id.Review_ll);

        fetchImagePaths(id,getContext(),view);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText inputField = inp.findViewById(R.id.input_for_review); // Cast the View to TextInputEditText
                String s = inputField.getText().toString();

                if (s != null && !s.trim().isEmpty()) {
                    sendReview(id, s); // Updates the status in the permit database

                    // Create the AlertDialog
                    AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("Review message has been sent")
                            .setMessage("Your review has been successfully submitted.")
                            .setPositiveButton("OK", (dialogInterface, which) -> {
                                // Navigate to the Dic_menu_screen
                                Intent intent = new Intent(getActivity(), Dic_menu_screen.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                getActivity().finish();
                                dialogInterface.dismiss();
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setCancelable(false) // Prevent back button dismissal
                            .create(); // Create the AlertDialog instance

                    // Prevent dismissing the dialog when touching outside
                    dialog.setCanceledOnTouchOutside(false);

                    dialog.show(); // Show the dialog
                } else {
                    Toast.makeText(getActivity(), "Please enter a message for review", Toast.LENGTH_SHORT).show();
                }
            }
        });


        permit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Re_ViewPermit reViewPermit = new Re_ViewPermit();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("id", id); // Assuming id is an integer, change type if necessary

                // Set arguments on the fragment
                reViewPermit.setArguments(bundle);
                transaction.replace(R.id.fragment_carrier_id_ap, reViewPermit);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });



        return view;
    }
    private void sendReview(int a,String s){
        String url = APIS_URLs.ReviewMessage_url; // Replace with your API URL

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id", a);
            jsonBody.put("review_msg",s);
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

//    private void fetchAndDisplayImages(Map<String, String> map, View view) {
//        // Prepare the list of file paths (values from your map)
//        List<String> filePaths = new ArrayList<>(map.values());
//
//        // Create a JSON Array from the file paths
//        JSONArray jsonArray = new JSONArray(filePaths);
//
//        // Prepare the API URL
//        String apiUrl = APIS_URLs.ImageUplaod_url;
//
//        // Create a JSON request
//        JsonObjectRequest jsonRequest = new JsonObjectRequest(
//                Request.Method.POST,
//                apiUrl,
//                null,
//                response -> {
//                    try {
//                        // Loop through the map to display images in their respective ImageViews
//                        for (Map.Entry<String, String> entry : map.entrySet()) {
//                            String key = entry.getKey(); // ImageView ID
//                            String filePath = entry.getValue(); // File path
//
//                            // Find the ImageView by ID
//                            int imageViewId = view.getResources().getIdentifier(key, "id", requireContext().getPackageName());
//                            ImageView imageView = getView().findViewById(imageViewId);
//
//                            // Get the base64 image string from the API response
//                            String base64Image = response.optString(filePath);
//
//                            if (base64Image != null && !base64Image.isEmpty()) {
//                                // Decode base64 string to bitmap
//                                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
//                                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//
//                                // Set the bitmap to the ImageView
//                                imageView.setImageBitmap(decodedBitmap);
//
//                                // Add click listener to open the zoom dialog
//                                imageView.setOnClickListener(v -> openZoomDialog(decodedBitmap));
//                            } else {
//                                Log.e("ImageError", "Image not found for filePath: " + filePath);
//                            }
//                        }
//                    } catch (Exception e) {
//                        Log.e("ResponseError", "Error processing API response: " + e.getMessage());
//                    }
//                },
//                error -> {
//                    // Handle Volley error
//                    Log.e("APIError", "Failed to fetch images: " + error.getMessage());
//                }
//        ) {
//            @Override
//            public byte[] getBody() {
//                return jsonArray.toString().getBytes(StandardCharsets.UTF_8);
//            }
//
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//        };
//
//        // Add the request to the Volley queue
//        Volley.newRequestQueue(requireContext()).add(jsonRequest);
//    }

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

    private void loadImages(View view) {
        for (Map.Entry<String, String> entry : imagePaths.entrySet()) {
            String key = entry.getKey(); // The ImageView ID
            String filePath = entry.getValue(); // The file path for the image

            // Construct the full API URL
            String apiUrl = APIS_URLs.loadImages_url + filePath;

            // Find the ImageView by ID
            int imageViewId = getResources().getIdentifier(key, "id", requireActivity().getPackageName());
            ImageView imageView = view.findViewById(imageViewId);

            if (imageView != null) {
                // Make a Volley request to fetch the image
                ImageRequest imageRequest = new ImageRequest(
                        apiUrl,
                        response -> {
                            // Use Glide to load the image into the ImageView
                            Glide.with(imageView.getContext())
                                    .load(apiUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
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

}