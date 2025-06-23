package com.example.landfinder;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class PropertyListActivity extends AppCompatActivity {

    ListView propertyListView;
    DatabaseHelper db;
    ArrayList<String> propertyList;
    ArrayList<Integer> propertyIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list);

        propertyListView = findViewById(R.id.property_list_view);
        db = new DatabaseHelper(this);
        propertyList = new ArrayList<>();
        propertyIds = new ArrayList<>();

        loadProperties();

        propertyListView.setOnItemClickListener((parent, view, position, id) -> {
            int propertyId = propertyIds.get(position);
            Intent intent = new Intent(PropertyListActivity.this, PropertyDetailsActivity.class);
            intent.putExtra("property_id", propertyId);
            startActivity(intent);
        });
    }

    private void loadProperties() {
        Cursor cursor = db.getAllProperties();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No properties found", Toast.LENGTH_SHORT).show();
            return;
        }

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String location = cursor.getString(2);
            propertyList.add(name + " - " + location);
            propertyIds.add(id);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, propertyList);
        propertyListView.setAdapter(adapter);
    }
}
