package com.example.itc406_major_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FIRESTORE
        firestore = FirebaseFirestore.getInstance();

        // CONNECT XML IDS
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // LOGIN BUTTON
        btnLogin.setOnClickListener(v -> {

            String username =
                    edtUsername.getText().toString().trim();

            String password =
                    edtPassword.getText().toString().trim();

            // VALIDATION
            if (username.isEmpty()) {

                edtUsername.setError("Enter Username");
                return;
            }

            if (password.isEmpty()) {

                edtPassword.setError("Enter Password");
                return;
            }

            // CHECK USER IN FIRESTORE
            firestore.collection("Users")
                    .whereEqualTo("username", username)
                    .whereEqualTo("password", password)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            String role = queryDocumentSnapshots
                                    .getDocuments()
                                    .get(0)
                                    .getString("role");

                            String patientName = queryDocumentSnapshots
                                    .getDocuments()
                                    .get(0)
                                    .getString("name");

                            String userId = queryDocumentSnapshots
                                    .getDocuments()
                                    .get(0)
                                    .getId();

                            Toast.makeText(
                                    MainActivity.this,
                                    "Login Successful",
                                    Toast.LENGTH_SHORT
                            ).show();

                            // -------------------------
                            // ADMIN LOGIN
                            // -------------------------
                            if (role.equalsIgnoreCase("Admin")) {

                                Intent intent =
                                        new Intent(
                                                MainActivity.this,
                                                Admin_Dashboard.class
                                        );

                                startActivity(intent);
                                finish();
                            }

                            // -------------------------
                            // STAFF LOGIN
                            // -------------------------
                            else if (role.equalsIgnoreCase("Staff")) {

                                Intent intent =
                                        new Intent(
                                                MainActivity.this,
                                                Staff_Dashboard.class
                                        );

                                startActivity(intent);
                                finish();
                            }

                            // -------------------------
                            // PATIENT LOGIN
                            // -------------------------
                            else if (role.equalsIgnoreCase("Patient")) {

                                Intent intent =
                                        new Intent(
                                                MainActivity.this,
                                                Patient_Dashboard.class
                                        );

                                // SEND USER DETAILS
                                intent.putExtra("username", username);
                                intent.putExtra("name", patientName);
                                intent.putExtra("userId", userId);

                                startActivity(intent);
                                finish();
                            }

                        } else {

                            Toast.makeText(
                                    MainActivity.this,
                                    "Invalid Username or Password",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }

                    })
                    .addOnFailureListener(e -> {

                        Toast.makeText(
                                MainActivity.this,
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    });

        });
    }
}