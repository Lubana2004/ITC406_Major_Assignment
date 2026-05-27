package com.example.itc406_major_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Patient_My_Profile extends AppCompatActivity {

    EditText edtCurrentPassword, edtNewPassword, edtConfirmPassword;
    Button btnUpdatePassword;
    ImageButton backBtn;

    FirebaseFirestore firestore;

    String username;
    String documentId;
    String currentPasswordFromDB;
    TextView txtName, txtRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_my_profile);

        firestore = FirebaseFirestore.getInstance();

        username = getIntent().getStringExtra("username");

        edtCurrentPassword = findViewById(R.id.edtCurrentPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        txtName = findViewById(R.id.txtName);
        txtRole = findViewById(R.id.txtRole);

        btnUpdatePassword = findViewById(R.id.btnUpdatePassword);
        backBtn = findViewById(R.id.imageButton9);

        loadUser();

        btnUpdatePassword.setOnClickListener(v -> {

            String currentPassword = edtCurrentPassword.getText().toString().trim();
            String newPassword = edtNewPassword.getText().toString().trim();
            String confirmPassword = edtConfirmPassword.getText().toString().trim();

            if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!currentPassword.equals(currentPasswordFromDB)) {
                Toast.makeText(this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            firestore.collection("Users")
                    .document(documentId)
                    .update("password", newPassword)
                    .addOnSuccessListener(unused -> {

                        Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show();

                        edtCurrentPassword.setText("");
                        edtNewPassword.setText("");
                        edtConfirmPassword.setText("");

                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        });

        backBtn.setOnClickListener(v -> finish());
    }

    private void loadUser() {

        firestore.collection("Users")
                .whereEqualTo("username", username)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                        documentId = doc.getId();
                        currentPasswordFromDB = doc.getString("password");

                        String firstName = doc.getString("firstName");
                        String lastName = doc.getString("lastName");
                        String role = doc.getString("role");

                        txtName.setText("Name: " + firstName + " " + lastName);

                        txtRole.setText("Role: " + role);
                    }
                });
    }
}