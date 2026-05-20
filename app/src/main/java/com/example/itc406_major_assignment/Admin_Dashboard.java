package com.example.itc406_major_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Admin_Dashboard extends AppCompatActivity {

    Button btnCreateUser, btnManageUser, btnLogout;

    TextView txtPatientCount, txtStaffCount;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);

        // DATABASE
        db = new DatabaseHelper(this);

        // BUTTONS
        btnCreateUser = findViewById(R.id.btnAdmin2);
        btnManageUser = findViewById(R.id.btnAdmin5);
        btnLogout = findViewById(R.id.btnAdmin4);

        // TEXTVIEWS
        txtPatientCount = findViewById(R.id.txtPatientCount);
        txtStaffCount = findViewById(R.id.txtStaffCount);

        // SHOW COUNTS
        txtPatientCount.setText(
                String.valueOf(db.getPatientCount()));

        txtStaffCount.setText(
                String.valueOf(db.getStaffCount()));

        // CREATE USER BUTTON
        btnCreateUser.setOnClickListener(v -> {

            Intent intent = new Intent(
                    Admin_Dashboard.this,
                    Create_User_Account_Admin.class);

            startActivity(intent);
        });

        // MANAGE USER BUTTON
        btnManageUser.setOnClickListener(v -> {

            Intent intent = new Intent(
                    Admin_Dashboard.this,
                    Manage_User_Admin.class);

            startActivity(intent);
        });

        // LOGOUT BUTTON
        btnLogout.setOnClickListener(v -> {

            Intent intent = new Intent(
                    Admin_Dashboard.this,
                    Login_Admin.class);

            startActivity(intent);

            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // REFRESH COUNTS WHEN RETURNING
        txtPatientCount.setText(
                String.valueOf(db.getPatientCount()));

        txtStaffCount.setText(
                String.valueOf(db.getStaffCount()));
    }
}
