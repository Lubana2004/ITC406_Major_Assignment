package com.example.itc406_major_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Patient_Dashboard extends AppCompatActivity {

    TextView txtWelcome, txtPatientId;
    Button btnReports, btnMyProfile, btnLogout;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);

        // GET DATA FROM LOGIN
        username = getIntent().getStringExtra("username");

        // INIT VIEWS
        txtWelcome = findViewById(R.id.txtWelcome);
        txtPatientId = findViewById(R.id.txtWelcome2);

        btnReports = findViewById(R.id.btnReports);
        btnMyProfile = findViewById(R.id.btnMyPofile);
        btnLogout = findViewById(R.id.btnLogout);

        // SHOW USERNAME
        txtPatientId.setText("Patient Name: " + username);

        // REPORTS BUTTON
        btnReports.setOnClickListener(v -> {

            Intent intent = new Intent(
                    Patient_Dashboard.this,
                    Patient_My_Reports.class
            );

            intent.putExtra("username", username);
            startActivity(intent);
        });

        // PROFILE BUTTON
        btnMyProfile.setOnClickListener(v -> {

            Intent intent = new Intent(
                    Patient_Dashboard.this,
                    Patient_My_Profile.class
            );

            intent.putExtra("username", username);
            startActivity(intent);
        });

        // LOGOUT BUTTON
        btnLogout.setOnClickListener(v -> {

            Intent intent = new Intent(
                    Patient_Dashboard.this,
                    MainActivity.class
            );

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        });
    }
}