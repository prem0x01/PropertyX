package com.example.landfinder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;

public class AddPropertyActivity extends AppCompatActivity {

    EditText propertyName, propertyLocation, propertyPrice;
    Button btnUploadImage, btnAddProperty;
    ImageView propertyImage;
    DatabaseHelper db;
    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        propertyName = findViewById(R.id.property_name);
        propertyLocation = findViewById(R.id.property_location);
        propertyPrice = findViewById(R.id.property_price);
        btnUploadImage = findViewById(R.id.btn_upload_image);
        btnAddProperty = findViewById(R.id.btn_add_property);
        propertyImage = findViewById(R.id.property_image);
        db = new DatabaseHelper(this);

        btnUploadImage.setOnClickListener(view -> openImageChooser());

        btnAddProperty.setOnClickListener(view -> {
            String name = propertyName.getText().toString();
            String location = propertyLocation.getText().toString();
            String price = propertyPrice.getText().toString();

            if (name.isEmpty() || location.isEmpty() || price.isEmpty() || selectedImageUri == null) {
                Toast.makeText(AddPropertyActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isInserted = db.insertProperty(name, location, price, selectedImageUri.toString());
            if (isInserted) {
                Toast.makeText(AddPropertyActivity.this, "Property Added Successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(AddPropertyActivity.this, "Failed to Add Property", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Register ActivityResultLauncher
    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                        propertyImage.setImageBitmap(bitmap);
                        propertyImage.setVisibility(View.VISIBLE);
                    } catch (IOException e) {
                        Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        imagePickerLauncher.launch(Intent.createChooser(intent, "Select Property Image"));
    }
}
