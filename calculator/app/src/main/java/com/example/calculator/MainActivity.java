package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText etFirstNumber, etSecondNumber;
    private TextView tvResult;
    private Button btnAdd, btnSubtract, btnMultiply, btnDivide;
    private Button[] numberButtons = new Button[10];
    private Button btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        etFirstNumber = findViewById(R.id.etFirstNumber);
        etSecondNumber = findViewById(R.id.etSecondNumber);
        tvResult = findViewById(R.id.tvResult);
        btnAdd = findViewById(R.id.btnAdd);
        btnSubtract = findViewById(R.id.btnSubtract);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnDivide = findViewById(R.id.btnDivide);
        btnClear = findViewById(R.id.btnClear);

        // Initialize number buttons
        int[] buttonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        for (int i = 0; i < buttonIds.length; i++) {
            numberButtons[i] = findViewById(buttonIds[i]);
            final int number = i;
            numberButtons[i].setOnClickListener(v -> appendNumber(String.valueOf(number)));
        }

        // Set operation button listeners
        btnAdd.setOnClickListener(v -> performOperation('+'));
        btnSubtract.setOnClickListener(v -> performOperation('-'));
        btnMultiply.setOnClickListener(v -> performOperation('*'));
        btnDivide.setOnClickListener(v -> performOperation('/'));

        // Clear button
        btnClear.setOnClickListener(v -> clearFields());
    }

    private void appendNumber(String number) {
        EditText focusedField = getFocusedEditText();
        if (focusedField != null) {
            String currentText = focusedField.getText().toString();
            focusedField.setText(currentText + number);
        }
    }

    private EditText getFocusedEditText() {
        if (etFirstNumber.isFocused()) {
            return etFirstNumber;
        } else if (etSecondNumber.isFocused()) {
            return etSecondNumber;
        }
        return null;
    }

    private void performOperation(char operator) {
        String num1Str = etFirstNumber.getText().toString();
        String num2Str = etSecondNumber.getText().toString();

        if (num1Str.isEmpty() || num2Str.isEmpty()) {
            tvResult.setText("Please enter both numbers");
            return;
        }

        try {
            double num1 = Double.parseDouble(num1Str);
            double num2 = Double.parseDouble(num2Str);
            double result = 0;

            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 == 0) {
                        tvResult.setText("Cannot divide by zero");
                        return;
                    }
                    result = num1 / num2;
                    break;
            }

            // Format result to remove decimal if whole number
            if (result == (long) result) {
                tvResult.setText("Result: " + (long) result);
            } else {
                tvResult.setText("Result: " + result);
            }

        } catch (NumberFormatException e) {
            tvResult.setText("Invalid input");
        }
    }

    private void clearFields() {
        etFirstNumber.setText("");
        etSecondNumber.setText("");
        tvResult.setText("Result: ");
        etFirstNumber.requestFocus();
    }
}