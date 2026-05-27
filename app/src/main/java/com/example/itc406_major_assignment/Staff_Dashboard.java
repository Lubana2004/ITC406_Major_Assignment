package com.example.itc406_major_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Staff_Dashboard extends AppCompatActivity {
    Button btnPatientRecords, btnUploadReports, btnMyProfile, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_staff_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String username =
                getIntent().getStringExtra("username");

        btnPatientRecords = findViewById(R.id.btnPatientRecords);
        btnUploadReports = findViewById(R.id.btnUploadReports);
        btnMyProfile = findViewById(R.id.btnMyProfile);
        btnLogout = findViewById(R.id.btnLogout);

        btnPatientRecords.setOnClickListener(v -> {
            Intent intent = new Intent(Staff_Dashboard.this,
                    Staff_Patient_Reports.class);
            startActivity(intent);
        });


        btnUploadReports.setOnClickListener(v -> {
            Intent intent = new Intent(Staff_Dashboard.this,
                    Staff_Upload_Medical_Report.class);
            startActivity(intent);
        });

        btnMyProfile.setOnClickListener(v -> {
            Intent intent =
                    new Intent(Staff_Dashboard.this,
                            Staff_My_Profile.class);

            intent.putExtra("Users", username);

            startActivity(intent);

        });


        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

    }
}