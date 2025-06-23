package com.example.landfinder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button btnLogin;
    TextView tvRegister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);
        db = new DatabaseHelper(this);

        btnLogin.setOnClickListener(view -> {
            String userEmail = email.getText().toString();
            String userPassword = password.getText().toString();

            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            Cursor cursor = db.getUser(userEmail, userPassword);
            if (cursor != null && cursor.getCount() > 0) {
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                // Navigate to ClientHomeActivity
                Intent intent = new Intent(LoginActivity.this, ClientHomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });

        tvRegister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
