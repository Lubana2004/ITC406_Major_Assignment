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

public class Edit_User_Admin extends AppCompatActivity {

    EditText edtFirstName,
            edtLastName,
            edtAddress,
            edtNumber,
            edtUsername,
            edtPassword;

    Spinner spinnerGender, spinnerRole;

    Button btnSave;
    ImageButton backBtn;

    FirebaseFirestore firestore;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_admin);

        // FIREBASE INIT
        firestore = FirebaseFirestore.getInstance();

        // EDITTEXTS
        edtFirstName = findViewById(R.id.edtFirstName3);
        edtLastName = findViewById(R.id.edtFirstName4);
        edtAddress = findViewById(R.id.edtAddress2);
        edtNumber = findViewById(R.id.edtNumber2);
        edtUsername = findViewById(R.id.edtUsername2);
        edtPassword = findViewById(R.id.edtTemporaryPassword2);

        // SPINNERS
        spinnerGender = findViewById(R.id.spinnerGender2);
        spinnerRole = findViewById(R.id.spinnerRole2);

        // BUTTONS
        btnSave = findViewById(R.id.btnSave);
        backBtn = findViewById(R.id.imageButton4);

        // SPINNER DATA
        String[] gender = {"Female", "Male", "Other"};
        String[] role = {"Staff", "Patient","Admin"};

        ArrayAdapter<String> genderAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        gender);

        ArrayAdapter<String> roleAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        role);

        spinnerGender.setAdapter(genderAdapter);
        spinnerRole.setAdapter(roleAdapter);

        // GET USER ID FROM INTENT
        userId = getIntent().getStringExtra("id");

        // LOAD USER DATA
        loadUserData();

        // SAVE / UPDATE USER
        btnSave.setOnClickListener(v -> {

            firestore.collection("Users")
                    .document(userId)
                    .update(
                            "firstName", edtFirstName.getText().toString(),
                            "lastName", edtLastName.getText().toString(),
                            "gender", spinnerGender.getSelectedItem().toString(),
                            "address", edtAddress.getText().toString(),
                            "phoneNumber", edtNumber.getText().toString(),
                            "role", spinnerRole.getSelectedItem().toString(),
                            "username", edtUsername.getText().toString(),
                            "password", edtPassword.getText().toString()
                    )
                    .addOnSuccessListener(aVoid -> {

                        Toast.makeText(
                                Edit_User_Admin.this,
                                "User Updated Successfully",
                                Toast.LENGTH_SHORT
                        ).show();

                        finish();

                    })
                    .addOnFailureListener(e -> {

                        Toast.makeText(
                                Edit_User_Admin.this,
                                "Update Failed: " + e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    });
        });

        // BACK BUTTON
        backBtn.setOnClickListener(v -> finish());
    }

    private void loadUserData() {

        firestore.collection("Users")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {

                    if (documentSnapshot.exists()) {

                        edtFirstName.setText(documentSnapshot.getString("firstName"));
                        edtLastName.setText(documentSnapshot.getString("lastName"));
                        edtAddress.setText(documentSnapshot.getString("address"));
                        edtNumber.setText(documentSnapshot.getString("phoneNumber"));
                        edtUsername.setText(documentSnapshot.getString("username"));
                        edtPassword.setText(documentSnapshot.getString("password"));

                        String gender = documentSnapshot.getString("gender");
                        String role = documentSnapshot.getString("role");

                        if (gender != null) {
                            spinnerGender.setSelection(
                                    ((ArrayAdapter) spinnerGender.getAdapter())
                                            .getPosition(gender)
                            );
                        }

                        if (role != null) {
                            spinnerRole.setSelection(
                                    ((ArrayAdapter) spinnerRole.getAdapter())
                                            .getPosition(role)
                            );
                        }
                    }
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(
                            this,
                            "Failed to load user: " + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                });
    }
}