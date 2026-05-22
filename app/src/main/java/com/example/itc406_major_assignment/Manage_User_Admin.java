package com.example.itc406_major_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class Manage_User_Admin extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<UserModel> userList;
    UserAdapter adapter;

    FirebaseFirestore firestore;

    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user_admin);

        // FIREBASE
        firestore = FirebaseFirestore.getInstance();

        // RECYCLER
        recyclerView = findViewById(R.id.recyclerViewUsers);
        backBtn = findViewById(R.id.imageButton5);

        userList = new ArrayList<>();

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        adapter = new UserAdapter(this, userList);
        recyclerView.setAdapter(adapter);

        loadUsers();

        // BACK BUTTON
        backBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, Admin_Dashboard.class));
            finish();
        });
    }

    private void loadUsers() {

        firestore.collection("Users")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    userList.clear();

                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                        userList.add(new UserModel(
                                doc.getId(),
                                doc.getString("firstName"),
                                doc.getString("lastName"),
                                doc.getString("role")
                        ));
                    }

                    adapter.notifyDataSetChanged();

                })
                .addOnFailureListener(e ->
                        Toast.makeText(this,
                                "Failed: " + e.getMessage(),
                                Toast.LENGTH_LONG).show()
                );
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUsers();
    }
}