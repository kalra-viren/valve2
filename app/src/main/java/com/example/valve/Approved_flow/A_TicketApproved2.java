package com.example.valve.Approved_flow;

import static android.app.Activity.RESULT_OK;

import static androidx.core.os.BundleCompat.getSerializable;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.valve.Menu.Menu_screen;
import com.example.valve.R;
import com.example.valve.Request_flow.PermitDataCallback;
import com.example.valve.Request_flow.VolleyMultipartRequest;
import com.example.valve.Util.APIS_URLs;
import com.example.valve.Util.App_utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class A_TicketApproved2 extends Fragment {
    private ImageView currentImageView;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private Button btn;
    private File imageFile;
    private Map<String, VolleyMultipartRequest.DataPart> L;
    private int id;
    private TextView hintText1;
    private TextView hintText2;
    private TextView hintText3;
    int flag=0;
    private HashMap<String, String>V;


    public A_TicketApproved2() {
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Register the camera launcher
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        loadImage(currentImageView, imageFile.getAbsolutePath());
                    } else {
                        Toast.makeText(getActivity(), "Camera action cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_a__ticket_approved2, container, false);
        flag=0;

        Bundle args = getArguments();
        if (args != null) {
            id = args.getInt("id");
            V = (HashMap<String, String>) args.getSerializable("mapV");
            // Use the received integer as needed
            Log.d("A_TicketApproved", "Received integer: " + id);
        }
        if (V != null) {
            // Log each entry in the HashMap
            for (Map.Entry<String, String> entry : V.entrySet()) {
                Log.d("RecievedMapDebug", "Key: " + entry.getKey() + ", Value: " + entry.getValue());
            }
        } else {
            Log.d("RecievedMapDebug", "The map is null.");
        }

        // Setup ImageView for capturing image
        L= new HashMap<>();

        hintText1=view.findViewById(R.id.hint_text1);
        setupImageViewClickListener(view, R.id.photo_field1,hintText1);

        hintText2=view.findViewById(R.id.hint_text2);
        setupImageViewClickListener(view, R.id.photo_field2,hintText2);

        hintText3=view.findViewById(R.id.hint_text3);
        setupImageViewClickListener(view, R.id.photo_field3,hintText3);

        // Setup "Next" button
        btn = view.findViewById(R.id.submit_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView iv1 = view.findViewById(R.id.photo_field1);
                ImageView iv2 = view.findViewById(R.id.photo_field2);
                ImageView iv3 = view.findViewById(R.id.photo_field3);

                // Check if at least one image is uploaded
                if (flag != 1) {
                    showUploadDialog();
                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Submit Permit")
                            .setMessage("Do you want to close the permit?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                dialog.dismiss();
                                uploadBitmap(); // Saves the images in local directory and returns the path
                                updateTicketStatus(id); // Updates the status in the permit database

                                // Show another dialog for upload requirement
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("Permit has been closed successfully")
                                        .setPositiveButton("OK", (dialog2, which2) -> {
                                            dialog2.dismiss();
                                            Intent intent = new Intent(getActivity(), Menu_screen.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                            startActivity(intent);
                                            getActivity().finish();
                                        })
                                        .setCancelable(false)  // Prevent dialog from being canceled by the back button
                                        .show(); // Properly show the new dialog here
                            })
                            .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                }
            }
        });
        return view;
    }

    private void showUploadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Upload Required");
        builder.setMessage("Please upload at least one image before proceeding.");
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void updateTicketStatus(int ticketId) {
        String url = APIS_URLs.updateTicketStatus_url_approved; // Replace with your API URL

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        ProgressBar progressBar = getView().findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id", ticketId);
            jsonBody.put("status", "CLOSED");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                response -> {
                    // Handle successful response
                    progressBar.setVisibility(View.GONE);
                    Log.d("API Response", "Ticket status updated successfully: " + response.toString());
                },
                error -> {
                    // Handle error
                    progressBar.setVisibility(View.GONE);
                    error.printStackTrace();
                    Log.e("API Error", "Failed to update ticket status");
                }
        );

        queue.add(request);
    }

    private void setupImageViewClickListener(View view, int imageViewId,TextView textView) {
        ImageView photoField = view.findViewById(imageViewId);
        photoField.setOnClickListener(v -> {
            currentImageView = photoField;
            String s=(String) textView.getText();
            openCamera(s);
            textView.setVisibility(View.GONE);
        });
    }

    private void openCamera(String s) {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CAMERA}, 100);
        } else {
            launchCamera(s);
        }
    }

    private void launchCamera(String s) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        change the name of the file here accordint to the text on the text view. pass it look in the photos coe
        imageFile = new File(getActivity().getFilesDir(), s+ ".jpg");
        Uri imageUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", imageFile);

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        cameraLauncher.launch(cameraIntent);

    }

    private void loadImage(ImageView imgView, String filePath) {
        File imagePath = new File(filePath);
        if (imagePath.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath.getAbsolutePath());
            imgView.setImageBitmap(bitmap);
            flag=1;
            fillMap();
        } else {
            Toast.makeText(getActivity(), "Image file not found", Toast.LENGTH_SHORT).show();
            fillMap();
        }
    }

    public void fillMap(){
        byte[] fileData = fileToByteArray(imageFile);
        if(fileData!=null && fileData.length>0){
//            String uniqueKey = "file_" + String.valueOf(id);

            L.put(App_utils.removePrefix(imageFile.getName()),new VolleyMultipartRequest.DataPart(imageFile.getName(), fileData));
        }
        else{
                L.put("NoImageCaptured", new VolleyMultipartRequest.DataPart("NoImageCaptured", new byte[0]));
        }
    }

    public static byte[] fileToByteArray(File file) {
        try (FileInputStream fis = new FileInputStream(file); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            Log.e("FileUtil", "Error reading file to byte array", e);
            return null;
        }
    }

    private void uploadBitmap() {
        String url = APIS_URLs.uploadBitmap_url;
        ProgressBar progressBar = getView().findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject pathsArray = new JSONObject(new String(response.data));
                        // Get the 'paths' array from the JSON response
//                        JSONArray pathsArray = obj.getJSONArray("paths");
                        // Create a new HashMap to store the paths
                        Map<String, String> pathsMap = new HashMap<>();
                        for (int i = 0; i < pathsArray.length(); i++) {
//                            String path = pathsArray.getString(i);

                            // Switch-case based on the content of the path
//                            change the name of the images from the launchcamera function()
                            if (pathsArray.has("Photo1")) {
                                pathsMap.put("Photo1", pathsArray.getString("Photo1"));
                            } else if (pathsArray.has("Photo2")) {
                                pathsMap.put("Photo2", pathsArray.getString("Photo2"));
                            } else if (pathsArray.has("Photo3")) {
                                pathsMap.put("Photo3", pathsArray.getString("Photo3"));
                            }

                        }
                        pathsMap.put("pid",Integer.toString(id));
                        if (V != null) {
                            pathsMap.putAll(V); // Update `pathsMap` with the new values from V
                        }
                        for (Map.Entry<String, String> entry : pathsMap.entrySet()) {
                            Log.d("PathsMapDebug", "Key: " + entry.getKey() + ", Value: " + entry.getValue());
                        }
                        JSONObject j = new JSONObject(pathsMap);
//                        sendDataToApi(j);
                        Toast.makeText(getActivity(), pathsArray.getString("message"), Toast.LENGTH_SHORT).show();
//                        call the api to pass the map and store the file paths in the database



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    progressBar.setVisibility(View.GONE);

                    Log.e("GotError", "Error: " + error.toString());
                    if (error.networkResponse != null) {
                        Log.e("GotError", "Response code: " + error.networkResponse.statusCode);
                        Log.e("GotError", "Response data: " + new String(error.networkResponse.data));
                    }
                }) {

            @Override
            protected Map<String, DataPart> getByteData() {

                return L;

            }
            @Override
            protected Map<String, String> getParams() {
                // Add your text parameters here
//                Map<String, String> params = new HashMap<>();
                V.put("id", String.valueOf(id));


                return V;
            }
        };
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }

    private void sendDataToApi(JSONObject jsonObject) {
        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String API_URL=APIS_URLs.sendDataToApi_url;
        // Create the request to send to the API
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                API_URL,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle successful response
                        Log.d("API Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        Log.e("API Error", error.toString());
                    }
                });

        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest);
    }
}