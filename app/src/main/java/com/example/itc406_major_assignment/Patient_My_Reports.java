package com.example.itc406_major_assignment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class Patient_My_Reports extends AppCompatActivity {

    RecyclerView recyclerViewUsers;
    EditText edtSearchUser2;
    ImageButton imageButton6;

    FirebaseFirestore firestore;

    ArrayList<ReportModel> reportList;
    ArrayList<ReportModel> filteredList;

    ReportAdapter adapter;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_my_reports);

        firestore = FirebaseFirestore.getInstance();

        // GET LOGGED-IN USERNAME
        username = getIntent().getStringExtra("username");

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        edtSearchUser2 = findViewById(R.id.edtSearchUser2);
        imageButton6 = findViewById(R.id.imageButton6);

        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));

        reportList = new ArrayList<>();
        filteredList = new ArrayList<>();

        adapter = new ReportAdapter(filteredList);
        recyclerViewUsers.setAdapter(adapter);

        loadReports();

        // SEARCH FUNCTION
        edtSearchUser2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterReports(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // BACK BUTTON
        imageButton6.setOnClickListener(v -> finish());
    }

    private void loadReports() {

        firestore.collection("Reports")
                .whereEqualTo("patientName", username) // IMPORTANT FILTER
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

                    filteredList.clear();
                    filteredList.addAll(reportList);
                    adapter.notifyDataSetChanged();
                });
    }

    private void filterReports(String text) {

        filteredList.clear();

        for (ReportModel model : reportList) {

            if (model.getReportType().toLowerCase().contains(text.toLowerCase()) ||
                    model.getDoctorName().toLowerCase().contains(text.toLowerCase())) {

                filteredList.add(model);
            }
        }

        adapter.notifyDataSetChanged();
    }
}