package com.example.landfinder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    TextView profileName, profileEmail;
    Button btnLogout;
    DatabaseHelper db;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profile_name);
        profileEmail = findViewById(R.id.profile_email);
        btnLogout = findViewById(R.id.btn_logout);
        db = new DatabaseHelper(this);

        // Get logged-in user's email from intent
        userEmail = getIntent().getStringExtra("email");

        if (userEmail != null) {
            loadUserProfile(userEmail);
        } else {
            Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnLogout.setOnClickListener(view -> {
            Toast.makeText(ProfileActivity.this, "Logged out!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void loadUserProfile(String email) {
        Cursor cursor = db.getUserByEmail(email);
        if (cursor != null && cursor.moveToFirst()) {
            profileName.setText(cursor.getString(1)); // Name
            profileEmail.setText(cursor.getString(2)); // Email
        } else {
            Toast.makeText(this, "Error loading profile", Toast.LENGTH_SHORT).show();
        }
    }
}
