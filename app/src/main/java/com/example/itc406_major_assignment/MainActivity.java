package com.example.itc406_major_assignment;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnAdmin, btnStaff, btnPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnAdmin = findViewById(R.id.btnAdmin);
        btnStaff = findViewById(R.id.btnStaff);
        btnPatient = findViewById(R.id.btnPatient);

        btnAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Login_Admin.class);
            startActivity(intent);
        });

        btnStaff.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Login_Staff.class);
            startActivity(intent);
        });

        btnPatient.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Login_Patient.class);
            startActivity(intent);
        });
    }
}