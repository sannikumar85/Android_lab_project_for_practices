package com.example.splashscreen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText etStudentName, etSubject1, etMarks1, etSubject2, etMarks2,
            etSubject3, etMarks3, etSubject4, etMarks4, etSubject5, etMarks5;
    private TextView tvResult;
    private Button btnCalculate, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setClickListeners();
    }

    private void initializeViews() {
        etStudentName = findViewById(R.id.et_student_name);
        etSubject1 = findViewById(R.id.et_subject1);
        etMarks1 = findViewById(R.id.et_marks1);
        etSubject2 = findViewById(R.id.et_subject2);
        etMarks2 = findViewById(R.id.et_marks2);
        etSubject3 = findViewById(R.id.et_subject3);
        etMarks3 = findViewById(R.id.et_marks3);
        etSubject4 = findViewById(R.id.et_subject4);
        etMarks4 = findViewById(R.id.et_marks4);
        etSubject5 = findViewById(R.id.et_subject5);
        etMarks5 = findViewById(R.id.et_marks5);
        tvResult = findViewById(R.id.tv_result);
        btnCalculate = findViewById(R.id.btn_calculate);
        btnReset = findViewById(R.id.btn_reset);
    }

    private void setClickListeners() {
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateMarksheet();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });
    }

    private void calculateMarksheet() {
        // Validate all fields are filled
        if (!validateFields()) {
            return;
        }

        // Get marks and validate they are <= 100
        int[] marks = new int[5];
        EditText[] marksFields = {etMarks1, etMarks2, etMarks3, etMarks4, etMarks5};

        for (int i = 0; i < 5; i++) {
            try {
                marks[i] = Integer.parseInt(marksFields[i].getText().toString().trim());
                if (marks[i] > 100) {
                    Toast.makeText(this, "Marks cannot be more than 100", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (marks[i] < 0) {
                    Toast.makeText(this, "Marks cannot be negative", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter valid marks", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Calculate total and percentage
        int totalMarks = 0;
        for (int mark : marks) {
            totalMarks += mark;
        }

        double percentage = (totalMarks / 500.0) * 100;
        DecimalFormat df = new DecimalFormat("#.##");

        // Create result string
        StringBuilder result = new StringBuilder();
        result.append("STUDENT MARKSHEET\n");
        result.append("================\n\n");
        result.append("Student Name: ").append(etStudentName.getText().toString().trim()).append("\n\n");

        String[] subjects = {
                etSubject1.getText().toString().trim(),
                etSubject2.getText().toString().trim(),
                etSubject3.getText().toString().trim(),
                etSubject4.getText().toString().trim(),
                etSubject5.getText().toString().trim()
        };

        for (int i = 0; i < 5; i++) {
            result.append(subjects[i]).append(": ").append(marks[i]).append("/100\n");
        }

        result.append("\n================\n");
        result.append("Total Marks: ").append(totalMarks).append("/500\n");
        result.append("Percentage: ").append(df.format(percentage)).append("%\n");

        // Add grade
        String grade = getGrade(percentage);
        result.append("Grade: ").append(grade);

        tvResult.setText(result.toString());
    }

    private boolean validateFields() {
        EditText[] allFields = {etStudentName, etSubject1, etMarks1, etSubject2, etMarks2,
                etSubject3, etMarks3, etSubject4, etMarks4, etSubject5, etMarks5};

        for (EditText field : allFields) {
            if (field.getText().toString().trim().isEmpty()) {
                field.setError("This field is mandatory");
                Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private String getGrade(double percentage) {
        if (percentage >= 90) return "A+";
        else if (percentage >= 80) return "A";
        else if (percentage >= 70) return "B+";
        else if (percentage >= 60) return "B";
        else if (percentage >= 50) return "C";
        else if (percentage >= 40) return "D";
        else return "F";
    }

    private void resetFields() {
        etStudentName.setText("");
        etSubject1.setText("");
        etMarks1.setText("");
        etSubject2.setText("");
        etMarks2.setText("");
        etSubject3.setText("");
        etMarks3.setText("");
        etSubject4.setText("");
        etMarks4.setText("");
        etSubject5.setText("");
        etMarks5.setText("");
        tvResult.setText("");

        Toast.makeText(this, "All fields cleared", Toast.LENGTH_SHORT).show();
    }
}