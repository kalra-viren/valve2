package com.example.valve.Request_flow;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.valve.Menu.Menu_screen;
import com.example.valve.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class R_photos extends Fragment {
    private ImageView currentImageView;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private Button btn;
    private File imageFile;
    private Map<String, VolleyMultipartRequest.DataPart> L;
    private TextView hintText1;
    private TextView hintText2;
    private TextView hintText3;
    private TextView hintText4;
    private TextView hintText5;
    private TextView hintText6;
    int flag;

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
        flag = 0;
        View view = inflater.inflate(R.layout.fragment_r_photos, container, false);
        L = new HashMap<>();
        hintText1 = view.findViewById(R.id.hint_text1);
        setupImageViewClickListener(view, R.id.photo_field1, hintText1);

        hintText2 = view.findViewById(R.id.hint_text2);
        setupImageViewClickListener(view, R.id.photo_field2, hintText2);

        hintText3 = view.findViewById(R.id.hint_text3);
        setupImageViewClickListener(view, R.id.photo_field3, hintText3);

        hintText4 = view.findViewById(R.id.hint_text4);
        setupImageViewClickListener(view, R.id.photo_field4, hintText4);

        hintText5 = view.findViewById(R.id.hint_text5);
        setupImageViewClickListener(view, R.id.photo_field5, hintText5);

        hintText6 = view.findViewById(R.id.hint_text6);
        setupImageViewClickListener(view, R.id.photo_field6, hintText6);


        btn = view.findViewById(R.id.submit_b);
        btn.setOnClickListener(view1 -> {

//            make this flag<3 to have compulsion for first 3 photos
            if (flag < 3) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Submit Permit")
                        .setMessage("Please upload first 3 images")
                        .setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            } else {
                HashMap<String, VolleyMultipartRequest.DataPart> mapToSend = new HashMap<>(L);
                Bundle bundle = new Bundle();
                bundle.putSerializable("image_map", mapToSend);

                // Navigate to the next fragment
                Test test = new Test();
                test.setArguments(bundle);
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_carrier_id_ap, test);
                transaction.addToBackStack(null);
                transaction.commit();
            }
            // Serialize the map and pass it to the new fragment
        });

        return view;
    }

    private void setupImageViewClickListener(View view, int imageViewId, TextView textView) {
        ImageView photoField = view.findViewById(imageViewId);
        photoField.setOnClickListener(v -> {
            currentImageView = photoField;
            String s = (String) textView.getText();
            openCamera(createFileName(s));
            textView.setVisibility(View.GONE);


        });
    }

    private String createFileName(String name) {

        if (name.contains("Gascoseeker")) {
            return "Gascoseeker";
        } else if (name.contains("Hira")) {
            return "Hira";
        } else if (name.contains("STC")) {
            return "STC";
        } else if (name.contains("Tool")) {
            return "Tool";
        } else if (name.contains("Technician")) {
            return "Technician";
        } else if (name.contains("Emergency")) {
            return "Emergency";
        } else {
            return "";
        }
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
        imageFile = new File(getActivity().getFilesDir(), s + ".jpg");
        Uri imageUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", imageFile);

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        cameraLauncher.launch(cameraIntent);
    }

    public void fillMap() {
        byte[] fileData = fileToByteArray(imageFile);
        if (fileData != null && fileData.length > 0) {
//            String uniqueKey = "file_" + System.currentTimeMillis();
//            String v = ; // Get the file name
//            StringBuilder a = new StringBuilder(); // Initialize StringBuilder

            // Loop through the file name and append characters until '_'
//            for (int i = 0; i < v.length(); i++) {
//                if (v.charAt(i) == '_') {
//                    break; // Stop when encountering an underscore
//                }
//                a.append(v.charAt(i)); // Append character to StringBuilder
//            }

            // Convert StringBuilder to String for comparison
            String prefix = imageFile.getName();
            Log.d("name", " " + prefix);

            // Check the prefix against predefined conditions
            if (prefix.contains("Gascoseeker") ||
                    prefix.contains("Hira") ||
                    prefix.contains("Tool")) {
                flag++;
                Log.d("Flags value", " " + flag);
            }
            L.put(createFileName(prefix),new VolleyMultipartRequest.DataPart(imageFile.getName(), fileData));
            printMap();
        }
    }

    private void printMap() {
        Log.d("MapDebug", "Contents of Map L:");
        for (Map.Entry<String, VolleyMultipartRequest.DataPart> entry : L.entrySet()) {
            String key = entry.getKey();
            VolleyMultipartRequest.DataPart value = entry.getValue();
            Log.d("MapDebug", "Key: " + key + ", FileName: " + value.getFileName() + ", FileLength: " + value.getContent().length);
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

    private void loadImage(ImageView imgView, String filePath) {
        File imagePath = new File(filePath);
        if (imagePath.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath.getAbsolutePath());
            imgView.setImageBitmap(bitmap);
            fillMap();
        } else {
            Toast.makeText(getActivity(), "Image file not found", Toast.LENGTH_SHORT).show();
        }
    }
}
