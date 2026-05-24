package com.example.itc406_major_assignment;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Staff_Upload_Medical_Report extends AppCompatActivity {

    Spinner spinner2, spinner3;

    EditText editTextDate, patientName;

    TextView txtFileName;

    Button btnUpload, btnSave, btnCancel;

    ImageButton imageButton15;

    FirebaseFirestore firestore;

    String selectedFileName = "No file selected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_upload_medical_report);

        // FIRESTORE
        firestore = FirebaseFirestore.getInstance();

        // SPINNERS
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);

        // EDITTEXTS
        editTextDate = findViewById(R.id.editTextDate);
        patientName = findViewById(R.id.patientName);

        // TEXTVIEW
        txtFileName = findViewById(R.id.txtFileName);

        // BUTTONS
        btnUpload = findViewById(R.id.btnUpload);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        // BACK BUTTON
        imageButton15 = findViewById(R.id.imageButton15);

        // -----------------------------
        // REPORT TYPES
        // -----------------------------
        String[] reportTypes = {
                "Blood Test",
                "X-Ray",
                "MRI",
                "Prescription",
                "ECG"
        };

        ArrayAdapter<String> reportAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        reportTypes
                );

        spinner2.setAdapter(reportAdapter);

        // -----------------------------
        // DOCTOR NAMES
        // -----------------------------
        String[] doctors = {
                "Dr Smith",
                "Dr Kumar",
                "Dr Ali"
        };

        ArrayAdapter<String> doctorAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        doctors
                );

        spinner3.setAdapter(doctorAdapter);

        // -----------------------------
        // CHOOSE FILE
        // -----------------------------
        btnUpload.setOnClickListener(v -> {

            // SIMULATED FILE PICK
            selectedFileName = "medical_report.pdf";

            txtFileName.setText(selectedFileName);

            Toast.makeText(
                    this,
                    "File Selected",
                    Toast.LENGTH_SHORT
            ).show();
        });

        // -----------------------------
        // SAVE REPORT
        // -----------------------------
        btnSave.setOnClickListener(v -> {

            String patient =
                    patientName.getText().toString();

            String reportType =
                    spinner2.getSelectedItem().toString();

            String doctorName =
                    spinner3.getSelectedItem().toString();

            String reportDate =
                    editTextDate.getText().toString();

            // VALIDATION
            if(patient.isEmpty()) {

                Toast.makeText(
                        this,
                        "Enter patient name",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            if(reportDate.isEmpty()) {

                Toast.makeText(
                        this,
                        "Enter report date",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            if(selectedFileName.equals("No file selected")) {

                Toast.makeText(
                        this,
                        "Choose a file",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // CREATE REPORT DATA
            Map<String, Object> report =
                    new HashMap<>();

            report.put("patientName", patient);
            report.put("reportType", reportType);
            report.put("doctorName", doctorName);
            report.put("reportDate", reportDate);
            report.put("fileName", selectedFileName);

            // SAVE TO FIRESTORE
            firestore.collection("Reports")
                    .add(report)
                    .addOnSuccessListener(documentReference -> {

                        Toast.makeText(
                                this,
                                "Medical Report Saved",
                                Toast.LENGTH_SHORT
                        ).show();

                        // CLEAR FIELDS
                        patientName.setText("");
                        editTextDate.setText("");
                        txtFileName.setText("No file selected");

                    })
                    .addOnFailureListener(e -> {

                        Toast.makeText(
                                this,
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    });
        });

        // -----------------------------
        // CANCEL BUTTON
        // -----------------------------
        btnCancel.setOnClickListener(v -> finish());

        // -----------------------------
        // BACK BUTTON
        // -----------------------------
        imageButton15.setOnClickListener(v -> finish());
    }
}