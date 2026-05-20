package com.example.itc406_major_assignment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Manage_User_Admin extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<UserModel> userList;

    UserAdapter adapter;

    DatabaseHelper db;

    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_user_admin);

        // RECYCLERVIEW
        recyclerView =
                findViewById(R.id.recyclerViewUsers);

        // BACK BUTTON
        backBtn =
                findViewById(R.id.imageButton3);

        db = new DatabaseHelper(this);

        userList = new ArrayList<>();

        loadUsers();

        adapter = new UserAdapter(this, userList);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        // BACK BUTTON CLICK
        backBtn.setOnClickListener(v -> {

            Intent intent = new Intent(
                    Manage_User_Admin.this,
                    Admin_Dashboard.class);

            startActivity(intent);

            finish();
        });
    }

    private void loadUsers() {

        Cursor cursor = db.getAllUsers();

        while(cursor.moveToNext()) {

            userList.add(

                    new UserModel(

                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(6)
                    )
            );
        }
    }
}