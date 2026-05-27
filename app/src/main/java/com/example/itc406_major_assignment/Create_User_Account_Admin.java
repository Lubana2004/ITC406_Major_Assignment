package com.example.itc406_major_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Create_User_Account_Admin extends AppCompatActivity {

    Spinner spinnerGender, spinnerRole;

    EditText edtFirstName,
            edtLastName,
            edtAddress,
            edtNumber,
            edtUsername,
            edtPassword;

    Button btnCreateAccount, btnClearForm;

    ImageButton backBtn;

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_account_admin);

        firestore = FirebaseFirestore.getInstance();

        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerRole = findViewById(R.id.spinnerRole);

        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtFirstName2);
        edtAddress = findViewById(R.id.edtAddress);
        edtNumber = findViewById(R.id.edtNumber);
        edtUsername = findViewById(R.id.edtUsername1);
        edtPassword = findViewById(R.id.edtTemporaryPassword);

        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnClearForm = findViewById(R.id.btnClearForm);

        backBtn = findViewById(R.id.imageButton);

        String[] gender = {"Female", "Male", "Other"};

        ArrayAdapter<String> genderAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item,
                        gender);

        genderAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerGender.setAdapter(genderAdapter);

        String[] role = {"Staff", "Patient","Admin"};


        ArrayAdapter<String> roleAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item,
                        role);

        roleAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerRole.setAdapter(roleAdapter);

        btnCreateAccount.setOnClickListener(v -> {

            String firstName = edtFirstName.getText().toString().trim();
            String lastName = edtLastName.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();
            String number = edtNumber.getText().toString().trim();
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            String genderValue = spinnerGender.getSelectedItem().toString();
            String roleValue = spinnerRole.getSelectedItem().toString();

            if (firstName.isEmpty() || lastName.isEmpty() ||
                    address.isEmpty() || number.isEmpty() ||
                    username.isEmpty() || password.isEmpty()) {

                Toast.makeText(this,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> user = new HashMap<>();
            user.put("firstName", firstName);
            user.put("lastName", lastName);
            user.put("gender", genderValue);
            user.put("address", address);
            user.put("phoneNumber", number);
            user.put("role", roleValue);
            user.put("username", username);
            user.put("password", password);

            firestore.collection("Users")
                    .add(user)
                    .addOnSuccessListener(documentReference -> {

                        Toast.makeText(
                                Create_User_Account_Admin.this,
                                "Account Created Successfully",
                                Toast.LENGTH_SHORT
                        ).show();

                        edtFirstName.setText("");
                        edtLastName.setText("");
                        edtAddress.setText("");
                        edtNumber.setText("");
                        edtUsername.setText("");
                        edtPassword.setText("");

                        spinnerGender.setSelection(0);
                        spinnerRole.setSelection(0);

                    })
                    .addOnFailureListener(e -> {

                        Toast.makeText(
                                Create_User_Account_Admin.this,
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    });
        });

        btnClearForm.setOnClickListener(v -> {

            edtFirstName.setText("");
            edtLastName.setText("");
            edtAddress.setText("");
            edtNumber.setText("");
            edtUsername.setText("");
            edtPassword.setText("");

            spinnerGender.setSelection(0);
            spinnerRole.setSelection(0);
        });

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(
                    Create_User_Account_Admin.this,
                    Admin_Dashboard.class
            );
            startActivity(intent);
            finish();
        });
    }
}