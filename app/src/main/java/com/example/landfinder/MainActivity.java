package com.example.landfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PropertyAdapter propertyAdapter;
    private List<PropertyModel> propertyList;
    private Button btnLogin, btnRegister;
    private String API_URL = "https://realtymole-rental-estimate.p.rapidapi.com/rentalPrice?city=New%20York";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        propertyList = new ArrayList<>();
        propertyAdapter = new PropertyAdapter(this, propertyList, this::openPropertyDetails);



        recyclerView.setAdapter(propertyAdapter);

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));
        btnRegister.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, RegisterActivity.class)));

        fetchProperties();
    }

    private void openPropertyDetails(PropertyModel property) {
        Intent intent = new Intent(MainActivity.this, PropertyDetailsActivity.class);
        intent.putExtra("property_name", property.getName());
        startActivity(intent);
    }


    private void fetchProperties() {
        if (propertyList.isEmpty()) {
            Toast.makeText(MainActivity.this, "No properties available", Toast.LENGTH_SHORT).show();
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API_URL, null,
                response -> {
                    try {
                        JSONArray properties = response.getJSONArray("listings");
                        for (int i = 0; i < properties.length(); i++) {
                            JSONObject obj = properties.getJSONObject(i);
                            String name = obj.getString("streetAddress");
                            String price = obj.getString("price");
                            String location = obj.getString("formattedAddress");
                            String imageUrl = obj.getString("photoURL");

                            propertyList.add(new PropertyModel(name, price, location, imageUrl));
                        }
                        propertyAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(MainActivity.this, "Failed to load properties", Toast.LENGTH_SHORT).show());

        queue.add(request);
    }
}
