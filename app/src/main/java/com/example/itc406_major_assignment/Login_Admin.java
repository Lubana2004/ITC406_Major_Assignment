package com.example.itc406_major_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Login_Admin extends AppCompatActivity {

    ImageButton backBtn;

    EditText edtUsername, edtPassword;

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_admin);

        backBtn = findViewById(R.id.imageButton2);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.editTextTextPassword);

        btnLogin = findViewById(R.id.button);

        backBtn.setOnClickListener(v -> {

            Intent intent = new Intent(Login_Admin.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnLogin.setOnClickListener(v -> {

            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if(username.equals("Admin") && password.equals("Admin")) {

                Toast.makeText(this,
                        "Login Successful",
                        Toast.LENGTH_SHORT).show();

                Intent intent =
                        new Intent(Login_Admin.this,
                                Admin_Dashboard.class);

                startActivity(intent);

            } else {

                Toast.makeText(this,
                        "Incorrect Username or Password",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}