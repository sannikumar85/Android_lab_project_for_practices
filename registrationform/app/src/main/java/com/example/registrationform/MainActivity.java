package com.example.registrationform;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText etName, etSemester, etDepartment, etCollege, etEmail;
    Button btnSubmit, btnReset;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        etName = findViewById(R.id.editTextName);
        etSemester = findViewById(R.id.editTextSemester);
        etDepartment = findViewById(R.id.editTextDepartment);
        etCollege = findViewById(R.id.editTextCollege);
        etEmail = findViewById(R.id.editTextEmail);
        btnSubmit = findViewById(R.id.buttonSubmit);
        btnReset = findViewById(R.id.buttonReset);
        tvResult = findViewById(R.id.textViewResult);


        btnSubmit.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String semester = etSemester.getText().toString().trim();
            String department = etDepartment.getText().toString().trim();
            String college = etCollege.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            String result = "Name: " + name +
                    "\nSemester: " + semester +
                    "\nDepartment: " + department +
                    "\nCollege: " + college +
                    "\nEmail: " + email;

            tvResult.setText(result);
        });

        btnReset.setOnClickListener(v -> {
            etName.setText("");
            etSemester.setText("");
            etDepartment.setText("");
            etCollege.setText("");
            etEmail.setText("");
            tvResult.setText("");
        });
    }
}