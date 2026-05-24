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

public class Staff_Patient_Reports extends AppCompatActivity {

    RecyclerView recyclerViewUsers;
    EditText edtReports;
    ImageButton imageButton12;

    FirebaseFirestore firestore;

    ArrayList<ReportModel> reportList;
    ArrayList<ReportModel> filteredList;

    ReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_patient_reports);

        firestore = FirebaseFirestore.getInstance();

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        edtReports = findViewById(R.id.edtReports);
        imageButton12 = findViewById(R.id.imageButton12);

        recyclerViewUsers.setLayoutManager(
                new LinearLayoutManager(this));

        reportList = new ArrayList<>();
        filteredList = new ArrayList<>();

        adapter = new ReportAdapter(filteredList);
        recyclerViewUsers.setAdapter(adapter);

        loadReports();

        edtReports.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterReports(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        imageButton12.setOnClickListener(v -> finish());
    }

    private void loadReports() {

        firestore.collection("Reports")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    reportList.clear();

                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                        reportList.add(new ReportModel(
                                doc.getString("patientName"),
                                doc.getString("reportType"),
                                doc.getString("doctorName"),
                                doc.getString("reportDate")
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

            if (model.getPatientName()
                    .toLowerCase()
                    .contains(text.toLowerCase())) {

                filteredList.add(model);
            }
        }

        adapter.notifyDataSetChanged();
    }
}