package com.example.itc406_major_assignment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Staff_My_Profile extends AppCompatActivity {

    TextView txtStaffName, txtStaffRole, txtStaffPhone;

    EditText edtCurrentPassword2,
            edtNewPassword2,
            edtConfirmPassword2;

    Button btnUpdatePassword2;

    ImageButton imageButton16;

    FirebaseFirestore firestore;

    String username;
    String documentId;
    String currentPasswordFromDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_my_profile);

        firestore = FirebaseFirestore.getInstance();

        // TEXTVIEWS
        txtStaffName = findViewById(R.id.txtStaffName);
        txtStaffRole = findViewById(R.id.txtStaffRole);
        txtStaffPhone = findViewById(R.id.txtStaffPhone);

        // EDITTEXTS
        edtCurrentPassword2 = findViewById(R.id.edtCurrentPassword2);
        edtNewPassword2 = findViewById(R.id.edtNewPassword2);
        edtConfirmPassword2 = findViewById(R.id.edtConfirmPassword2);

        // BUTTON
        btnUpdatePassword2 = findViewById(R.id.btnUpdatePassword2);

        // BACK BUTTON
        imageButton16 = findViewById(R.id.imageButton16);

        // GET USERNAME FROM INTENT
        username = getIntent().getStringExtra("Users");

        if(username == null || username.isEmpty()) {

            Toast.makeText(this,
                    "Username not received",
                    Toast.LENGTH_LONG).show();

            return;
        }

        // LOAD STAFF DATA
        loadStaffData();

        // UPDATE PASSWORD
        btnUpdatePassword2.setOnClickListener(v -> {

            String currentPassword =
                    edtCurrentPassword2.getText().toString().trim();

            String newPassword =
                    edtNewPassword2.getText().toString().trim();

            String confirmPassword =
                    edtConfirmPassword2.getText().toString().trim();

            // VALIDATION
            if(currentPassword.isEmpty()
                    || newPassword.isEmpty()
                    || confirmPassword.isEmpty()) {

                Toast.makeText(this,
                        "Fill all fields",
                        Toast.LENGTH_SHORT).show();

                return;
            }

            // CHECK CURRENT PASSWORD
            if(!currentPassword.equals(currentPasswordFromDB)) {

                Toast.makeText(this,
                        "Current password incorrect",
                        Toast.LENGTH_SHORT).show();

                return;
            }

            // CHECK PASSWORD MATCH
            if(!newPassword.equals(confirmPassword)) {

                Toast.makeText(this,
                        "Passwords do not match",
                        Toast.LENGTH_SHORT).show();

                return;
            }

            // UPDATE FIRESTORE
            firestore.collection("Users")
                    .document(documentId)
                    .update("password", newPassword)
                    .addOnSuccessListener(unused -> {

                        Toast.makeText(this,
                                "Password Updated Successfully",
                                Toast.LENGTH_SHORT).show();

                        edtCurrentPassword2.setText("");
                        edtNewPassword2.setText("");
                        edtConfirmPassword2.setText("");

                    })
                    .addOnFailureListener(e -> {

                        Toast.makeText(this,
                                e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    });
        });

        // BACK BUTTON
        imageButton16.setOnClickListener(v -> finish());
    }

    private void loadStaffData() {

        firestore.collection("Users")
                .whereEqualTo("username", username)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    if(queryDocumentSnapshots.isEmpty()) {

                        Toast.makeText(this,
                                "No staff data found",
                                Toast.LENGTH_SHORT).show();

                        return;
                    }

                    for(QueryDocumentSnapshot doc :
                            queryDocumentSnapshots) {

                        documentId = doc.getId();

                        String firstName =
                                doc.getString("firstName");

                        String lastName =
                                doc.getString("lastName");

                        String role =
                                doc.getString("role");

                        String phone =
                                doc.getString("phoneNumber");

                        currentPasswordFromDB =
                                doc.getString("password");

                        // DISPLAY DATA
                        txtStaffName.setText(
                                "Name: " +
                                        firstName + " " + lastName);

                        txtStaffRole.setText(
                                "Role: " + role);

                        txtStaffPhone.setText(
                                "Phone: " + phone);
                    }
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(this,
                            e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }
}