package com.example.itc406_major_assignment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Edit_User_Admin extends AppCompatActivity {

    EditText edtFirstName,
            edtLastName,
            edtAddress,
            edtNumber,
            edtUsername,
            edtPassword;

    Spinner spinnerGender, spinnerRole;

    Button btnSave;

    ImageButton backBtn;

    DatabaseHelper db;

    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_user_admin);

        db = new DatabaseHelper(this);

        // EDITTEXTS
        edtFirstName = findViewById(R.id.edtFirstName3);
        edtLastName = findViewById(R.id.edtFirstName4);
        edtAddress = findViewById(R.id.edtAddress2);
        edtNumber = findViewById(R.id.edtNumber2);
        edtUsername = findViewById(R.id.edtUsername2);
        edtPassword = findViewById(R.id.edtTemporaryPassword2);

        // SPINNERS
        spinnerGender = findViewById(R.id.spinnerGender2);
        spinnerRole = findViewById(R.id.spinnerRole2);

        // BUTTON
        btnSave = findViewById(R.id.btnSave);

        // BACK BUTTON
        backBtn = findViewById(R.id.imageButton4);

        // GENDER SPINNER
        String[] gender = {"Female", "Male", "Other"};

        ArrayAdapter<String> genderAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        gender);

        spinnerGender.setAdapter(genderAdapter);

        // ROLE SPINNER
        String[] role = {"Staff", "Patient"};

        ArrayAdapter<String> roleAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        role);

        spinnerRole.setAdapter(roleAdapter);

        // GET USER ID
        userId = getIntent().getIntExtra("id", -1);

        loadUserData();

        // SAVE BUTTON
        btnSave.setOnClickListener(v -> {

            boolean updated = db.updateUser(

                    userId,

                    edtFirstName.getText().toString(),
                    edtLastName.getText().toString(),
                    spinnerGender.getSelectedItem().toString(),
                    edtAddress.getText().toString(),
                    edtNumber.getText().toString(),
                    spinnerRole.getSelectedItem().toString(),
                    edtUsername.getText().toString(),
                    edtPassword.getText().toString()
            );

            if(updated) {

                Toast.makeText(
                        this,
                        "User Updated Successfully",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Update Failed",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        // BACK BUTTON
        backBtn.setOnClickListener(v -> {

            Intent intent = new Intent(
                    Edit_User_Admin.this,
                    Manage_User_Admin.class);

            startActivity(intent);

            finish();
        });
    }

    private void loadUserData() {

        Cursor cursor = db.getUserById(userId);

        if(cursor.moveToFirst()) {

            edtFirstName.setText(cursor.getString(1));
            edtLastName.setText(cursor.getString(2));

            edtAddress.setText(cursor.getString(4));
            edtNumber.setText(cursor.getString(5));

            edtUsername.setText(cursor.getString(7));
            edtPassword.setText(cursor.getString(8));

            String gender = cursor.getString(3);
            String role = cursor.getString(6);

            spinnerGender.setSelection(
                    ((ArrayAdapter) spinnerGender.getAdapter())
                            .getPosition(gender));

            spinnerRole.setSelection(
                    ((ArrayAdapter) spinnerRole.getAdapter())
                            .getPosition(role));
        }
    }
}