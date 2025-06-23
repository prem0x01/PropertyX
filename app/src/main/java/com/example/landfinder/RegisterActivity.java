package com.example.landfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.R;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText fullname, email, password, confirmPassword;
    Button btnRegister;
    TextView tvLogin;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        btnRegister = findViewById(R.id.btn_register);
        tvLogin = findViewById(R.id.tv_login);
        db = new DatabaseHelper(this);

        btnRegister.setOnClickListener(view -> {
            String name = fullname.getText().toString();
            String userEmail = email.getText().toString();
            String userPassword = password.getText().toString();
            String confirmPass = confirmPassword.getText().toString();

            if (name.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!userPassword.equals(confirmPass)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isInserted = db.insertUser(name, userEmail, userPassword);
            if (isInserted) {
                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });

        tvLogin.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }
}
