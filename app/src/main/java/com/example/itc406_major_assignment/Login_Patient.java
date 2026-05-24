package com.example.itc406_major_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Login_Patient extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;
    ImageButton backBtn;

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_patient);

        firestore = FirebaseFirestore.getInstance();

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.button2);
        backBtn = findViewById(R.id.imageButton11);

        // BACK BUTTON
        backBtn.setOnClickListener(v -> finish());

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
                    .whereEqualTo("role", "Patient")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                                String user = doc.getString("username");

                                Intent intent = new Intent(
                                        Login_Patient.this,
                                        Patient_Dashboard.class
                                );

                                intent.putExtra("username", user);

                                startActivity(intent);
                                finish();
                            }

                        } else {
                            Toast.makeText(
                                    this,
                                    "Invalid Patient Login",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }

                    })
                    .addOnFailureListener(e -> {

                        Toast.makeText(
                                this,
                                e.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    });
        });
    }
}