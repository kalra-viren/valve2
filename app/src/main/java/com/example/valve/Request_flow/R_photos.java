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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.valve.R;

public class R_photos extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    public R_photos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_r_photos, container, false);

        ImageView photoField1 = view.findViewById(R.id.photo_field1);
        photoField1.setOnClickListener(v -> openCamera());

        // Repeat for other photo fields...
        ImageView photoField2 = view.findViewById(R.id.photo_field2);
        photoField2.setOnClickListener(v -> openCamera());

        ImageView photoField3 = view.findViewById(R.id.photo_field3);
        photoField3.setOnClickListener(v -> openCamera());

        ImageView photoField4 = view.findViewById(R.id.photo_field4);
        photoField4.setOnClickListener(v -> openCamera());

        return view;
    }

    private void openCamera() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        } else {
            launchCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            } else {
                Toast.makeText(getActivity(), "Camera permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void launchCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == -1 && data != null) { // -1 is RESULT_OK
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // Here you can display or save the imageBitmap in the respective EditText or ImageView.
            // For example, if you want to show it in an ImageView:
            // ImageView imageView = view.findViewById(R.id.image_view); // Make sure to add an ImageView in your layout
            // imageView.setImageBitmap(imageBitmap);
        }
    }
}