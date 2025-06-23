package com.example.landfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class ClientHomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);
    }

    // Method to handle button click
    public void viewProperties(View view) {
        startActivity(new Intent(ClientHomeActivity.this, PropertyListActivity.class));
    }
}
