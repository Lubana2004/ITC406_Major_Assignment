package com.example.itc406_major_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class MainActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firestore = FirebaseFirestore.getInstance();

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {

            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty()) {
                edtUsername.setError("Enter Username");
                return;
            }

            if (password.isEmpty()) {
                edtPassword.setError("Enter Password");
                return;
            }

            firestore.collection("Users")
                    .whereEqualTo("username", username)
                    .whereEqualTo("password", password)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            QueryDocumentSnapshot doc =
                                    (QueryDocumentSnapshot) queryDocumentSnapshots.getDocuments().get(0);

                            String role = doc.getString("role");
                            String name = doc.getString("firstName");
                            String userId = doc.getId();

                            Toast.makeText(this,
                                    "Login Successful",
                                    Toast.LENGTH_SHORT).show();


                            if (role.equalsIgnoreCase("Admin")) {

                                Intent intent = new Intent(MainActivity.this,
                                        Admin_Dashboard.class);

                                intent.putExtra("username", username);
                                intent.putExtra("userId", userId);

                                startActivity(intent);
                                finish();
                            }


                            else if (role.equalsIgnoreCase("Staff")) {

                                Intent intent = new Intent(MainActivity.this,
                                        Staff_Dashboard.class);

                                intent.putExtra("username", username);
                                intent.putExtra("userId", userId);

                                startActivity(intent);
                                finish();
                            }


                            else if (role.equalsIgnoreCase("Patient")) {

                                Intent intent = new Intent(MainActivity.this,
                                        Patient_Dashboard.class);

                                intent.putExtra("username", username);
                                intent.putExtra("name", name);
                                intent.putExtra("userId", userId);

                                startActivity(intent);
                                finish();
                            }

                        } else {
                            Toast.makeText(this,
                                    "Invalid Username or Password",
                                    Toast.LENGTH_SHORT).show();
                        }

                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this,
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    });
        });
    }
}