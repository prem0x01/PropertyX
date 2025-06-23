package com.example.landfinder;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PropertyDetailsActivity extends AppCompatActivity {

    TextView propertyName, propertyLocation, propertyPrice;
    ImageView propertyImage;
    Button btnBookAppointment;
    DatabaseHelper db;
    int propertyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        propertyName = findViewById(R.id.property_name);
        propertyLocation = findViewById(R.id.property_location);
        propertyPrice = findViewById(R.id.property_price);
        propertyImage = findViewById(R.id.property_image);
        btnBookAppointment = findViewById(R.id.btn_book_appointment);
        db = new DatabaseHelper(this);

        propertyId = getIntent().getIntExtra("property_id", -1);
        loadPropertyDetails();

        btnBookAppointment.setOnClickListener(view -> {
            Intent intent = new Intent(PropertyDetailsActivity.this, BookAppointmentActivity.class);
            intent.putExtra("property_id", propertyId);
            startActivity(intent);
        });
    }



    private void loadPropertyDetails() {
        Cursor cursor = db.getPropertyById(propertyId);
        if (cursor.moveToFirst()) {
            propertyName.setText(cursor.getString(1));
            propertyLocation.setText(cursor.getString(2));
            propertyPrice.setText("₹" + cursor.getString(3));

            Uri imageUri = Uri.parse(cursor.getString(4));
            propertyImage.setImageURI(imageUri);
        }
    }
}
