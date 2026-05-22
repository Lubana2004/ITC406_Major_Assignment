package com.example.itc406_major_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class Login_Staff extends AppCompatActivity {

    ImageButton backBtn;

    EditText edtUsername, edtPassword;
    Button btnLogin;

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_staff);

        firestore = FirebaseFirestore.getInstance();

        // BACK BUTTON
        backBtn = findViewById(R.id.imageButton2);

        // MATCH YOUR XML IDs
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.editTextTextPassword2);
        btnLogin = findViewById(R.id.btnLogin);

        // BACK BUTTON ACTION
        backBtn.setOnClickListener(v -> {
            finish();
        });

        // LOGIN BUTTON
        btnLogin.setOnClickListener(v -> {

            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            firestore.collection("Users")
                    .whereEqualTo("username", username)
                    .whereEqualTo("password", password)
                    .whereEqualTo("role", "Staff")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Login_Staff.this, Staff_Dashboard.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(this, "Invalid Staff Login", Toast.LENGTH_SHORT).show();
                        }

                    })
                    .addOnFailureListener(e -> {

                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });


        });
    }
}