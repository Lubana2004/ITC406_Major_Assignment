package com.example.itc406_major_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class Admin_Dashboard extends AppCompatActivity {

    Button btnCreateUser, btnManageUser, btnLogout;

    TextView txtPatientCount, txtStaffCount;

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);


        firestore = FirebaseFirestore.getInstance();

        btnCreateUser = findViewById(R.id.btnAdmin2);
        btnManageUser = findViewById(R.id.btnAdmin5);
        btnLogout = findViewById(R.id.btnAdmin4);

        txtPatientCount = findViewById(R.id.txtPatientCount);
        txtStaffCount = findViewById(R.id.txtStaffCount);

        loadCounts();

        btnCreateUser.setOnClickListener(v -> {
            startActivity(new Intent(this, Create_User_Account_Admin.class));
        });

        btnManageUser.setOnClickListener(v -> {
            startActivity(new Intent(this, Manage_User_Admin.class));
        });

        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void loadCounts() {

        firestore.collection("Users")
                .whereEqualTo("role", "Patient")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    int count = queryDocumentSnapshots.size();
                    txtPatientCount.setText("Patient\n" + String.valueOf(count));

                });

        firestore.collection("Users")
                .whereIn("role",
                        java.util.Arrays.asList("Staff", "Admin"))
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    int count = queryDocumentSnapshots.size();

                    txtStaffCount.setText(
                            "Staff\n" + count
                    );

                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCounts();
    }
}