package com.example.myregistrationform;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etPassword, etContent, etAddress;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etContent = findViewById(R.id.etContent);
        etAddress = findViewById(R.id.etAddress);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String content = etContent.getText().toString().trim();
            String address = etAddress.getText().toString().trim();

            // Validation
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) ||
                    TextUtils.isEmpty(password) || TextUtils.isEmpty(content) ||
                    TextUtils.isEmpty(address)) {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!email.contains("@")) {
                Toast.makeText(this, "Email must contain @", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() > 8 || !password.matches(".[@#$%^&+=!].")) {
                Toast.makeText(this, "Password must be max 8 chars & contain special symbol", Toast.LENGTH_SHORT).show();
                return;
            }

            // If valid → Go to LoginActivity
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            startActivity(intent);
        });
    }
}