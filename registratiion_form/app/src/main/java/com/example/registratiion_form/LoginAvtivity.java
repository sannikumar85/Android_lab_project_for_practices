package com.example.myregistrationform;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public

















class LoginActivity extends AppCompatActivity {

    EditText etLoginEmail, etLoginPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Get registration data
        Intent intent = getIntent();
        String regEmail = intent.getStringExtra("email");
        String regPassword = intent.getStringExtra("password");

        btnLogin.setOnClickListener(v -> {
            String loginEmail = etLoginEmail.getText().toString().trim();
            String loginPass = etLoginPassword.getText().toString().trim();

            if (TextUtils.isEmpty(loginEmail) || TextUtils.isEmpty(loginPass)) {
                Toast.makeText(this, "Please enter details", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!loginEmail.contains("@")) {
                Toast.makeText(this, "Enter valid email with @", Toast.LENGTH_SHORT).show();
                return;
            }

            if (loginPass.length() > 8 || !loginPass.matches(".[@#$%^&+=!].")) {
                Toast.makeText(this, "Password must be max 8 chars & contain special symbol", Toast.LENGTH_SHORT).show();
                return;
            }

            if (loginEmail.equals(regEmail) && loginPass.equals(regPassword)) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }
}