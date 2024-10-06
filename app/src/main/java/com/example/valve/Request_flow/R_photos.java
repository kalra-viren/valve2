package com.example.valve.Request_flow;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.valve.R;

public class R_photos extends Fragment {
    private ImageView currentImageView; // To keep track of the currently clicked ImageView
    private ActivityResultLauncher<Intent> cameraLauncher; // Declare the launcher
    private Button btn;


    public R_photos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Register the camera launcher
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == -1 && result.getData() != null) { // -1 is RESULT_OK
                        handleCameraResult(result.getData());
                    } else {
                        Toast.makeText(getActivity(), "Camera action cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_r_photos, container, false);

        setupImageViewClickListener(view, R.id.photo_field1);
        setupImageViewClickListener(view, R.id.photo_field2);
        setupImageViewClickListener(view, R.id.photo_field3);
        setupImageViewClickListener(view, R.id.photo_field4);
        btn=view.findViewById(R.id.submit_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                R_form rForm=new R_form();
                FragmentTransaction transaction= getParentFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.fragment_carrier_layout_id,rForm);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }



    private void setupImageViewClickListener(View view, int imageViewId) {
        ImageView photoField = view.findViewById(imageViewId);
        photoField.setOnClickListener(v -> {
            currentImageView = photoField; // Store reference to the clicked ImageView
            openCamera();
        });
    }

    private void openCamera() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CAMERA}, 100); // Request permission
        } else {
            launchCamera();
        }
    }

    private void launchCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            cameraLauncher.launch(cameraIntent); // Use the launcher to start the camera
        }
    }

    private void handleCameraResult(Intent data) {
        if (data != null && data.getExtras() != null) { // Check if data and extras are not null
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            if (imageBitmap != null && currentImageView != null) { // Ensure imageBitmap and currentImageView are not null
                currentImageView.setImageBitmap(imageBitmap); // Set the captured image to the clicked ImageView
            } else {
                Toast.makeText(getActivity(), "Failed to capture image", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "No data received from camera", Toast.LENGTH_SHORT).show();
        }
    }
}