package com.example.itc406_major_assignment;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Staff_Upload_Medical_Report extends AppCompatActivity {

    Spinner spinnerPatient, spinnerReportType, spinnerDoctor;
    TextView txtFileName;
    Button btnSave, btnCancel;
    ImageButton backBtn;

    FirebaseFirestore firestore;

    ArrayList<String> patientList = new ArrayList<>();
    ArrayList<String> patientIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_upload_medical_report);

        firestore = FirebaseFirestore.getInstance();

        spinnerReportType = findViewById(R.id.spinner2);
        spinnerDoctor = findViewById(R.id.spinner3);

        txtFileName = findViewById(R.id.txtFileName);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        backBtn = findViewById(R.id.imageButton15);

        // SIMPLE DATA
        String[] reportTypes = {"Blood Test", "X-Ray", "MRI", "Prescription"};
        String[] doctors = {"Dr Smith", "Dr John", "Dr Patel"};

        spinnerReportType.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                reportTypes));

        spinnerDoctor.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                doctors));

        loadPatients();

        btnSave.setOnClickListener(v -> saveReport());

        btnCancel.setOnClickListener(v -> finish());

        backBtn.setOnClickListener(v -> finish());
    }

    private void loadPatients() {

        firestore.collection("Users")
                .whereEqualTo("role", "Patient")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    patientList.clear();
                    patientIds.clear();

                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                        patientList.add(
                                doc.getString("firstName") + " " +
                                        doc.getString("lastName")
                        );

                        patientIds.add(doc.getId());
                    }

                    spinnerPatient.setAdapter(
                            new ArrayAdapter<>(this,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    patientList)
                    );
                });
    }

    private void saveReport() {

        int index = spinnerPatient.getSelectedItemPosition();

        String patientId = patientIds.get(index);
        String patientName = patientList.get(index);

        String reportType = spinnerReportType.getSelectedItem().toString();
        String doctor = spinnerDoctor.getSelectedItem().toString();

        Map<String, Object> report = new HashMap<>();
        report.put("patientId", patientId);
        report.put("patientName", patientName);
        report.put("reportType", reportType);
        report.put("doctor", doctor);
        report.put("fileName", txtFileName.getText().toString());
        report.put("date", System.currentTimeMillis());

        firestore.collection("MedicalReports")
                .add(report)
                .addOnSuccessListener(doc -> {

                    Toast.makeText(this,
                            "Report Saved",
                            Toast.LENGTH_SHORT).show();

                    txtFileName.setText("No file selected");
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(this,
                            e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }
}