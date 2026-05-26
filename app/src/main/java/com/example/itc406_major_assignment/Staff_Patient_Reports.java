package com.example.itc406_major_assignment;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class Staff_Patient_Reports extends AppCompatActivity {

    RecyclerView recyclerViewUsers;
    ImageButton imageButton12;

    FirebaseFirestore firestore;

    ArrayList<ReportModel> reportList;
    ReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_patient_reports);

        firestore = FirebaseFirestore.getInstance();

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        imageButton12 = findViewById(R.id.imageButton12);

        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));

        reportList = new ArrayList<>();

        adapter = new ReportAdapter(reportList);
        recyclerViewUsers.setAdapter(adapter);

        loadReports();

        imageButton12.setOnClickListener(v -> finish());
    }

    private void loadReports() {

        firestore.collection("Reports")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    reportList.clear();

                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                        String patientName = doc.getString("patientName");
                        String reportType = doc.getString("reportType");
                        String doctorName = doc.getString("doctorName");
                        String reportDate = doc.getString("reportDate");

                        reportList.add(new ReportModel(
                                patientName,
                                reportType,
                                doctorName,
                                reportDate
                        ));
                    }

                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}