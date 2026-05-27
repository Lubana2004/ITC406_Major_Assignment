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

public class Patient_My_Reports extends AppCompatActivity {

    RecyclerView recyclerViewUsers;
    ImageButton imageButton6;

    FirebaseFirestore firestore;

    ArrayList<ReportModel> reportList;
    ReportAdapter adapter;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_my_reports);

        firestore = FirebaseFirestore.getInstance();

        username = getIntent().getStringExtra("username");

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        imageButton6 = findViewById(R.id.imageButton6);

        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));

        reportList = new ArrayList<>();

        adapter = new ReportAdapter(reportList);
        recyclerViewUsers.setAdapter(adapter);

        loadReports();

        imageButton6.setOnClickListener(v -> finish());
    }

    private void loadReports() {

        firestore.collection("Reports")
                .whereEqualTo("patientName", username)
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