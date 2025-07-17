package com.example.adminfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adminfoodapp.databinding.ActivitySignUpBinding;
import com.example.adminfoodapp.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private String userName;
    private String nameOfRestaurant;
    private String email;
    private String password;

    private FirebaseAuth auth;
    private DatabaseReference database;

    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        // Button: Create Account
        binding.createAccountButton.setOnClickListener(view -> {
            userName = binding.name.getText().toString().trim();
            nameOfRestaurant = binding.restaurantName.getText().toString().trim();
            email = binding.emailOrPhone.getText().toString().trim();
            password = binding.password.getText().toString().trim();

            if (userName.isEmpty() || nameOfRestaurant.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show();
            } else {
                createAccount(email, password);
            }
        });

        // Button: Already have account
        binding.alreadyHaveAccount.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Location list for AutoComplete
        String[] locationList = {"Jaipur", "Odisha", "Bundi", "Sikar"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                locationList
        );
        binding.locationDropdown.setAdapter(adapter);
    }

    private void createAccount(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        saveUserData();

                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Account Creation Failed", Toast.LENGTH_SHORT).show();
                        Log.d("Account", "createAccount: Failure", task.getException());
                    }
                });
    }

    private void saveUserData() {
        // Truy suất thông tin người dùng từ các trường nhập liệu
        userName = binding.name.getText().toString().trim();
        nameOfRestaurant = binding.restaurantName.getText().toString().trim();
        email = binding.emailOrPhone.getText().toString().trim();
        password = binding.password.getText().toString().trim();

        UserModel user = new UserModel(userName, nameOfRestaurant, email, password, null, null);
        String userId = auth.getCurrentUser().getUid();

        // Lưu thông tin người dùng vào Firebase Realtime Database
        database.child("users").child(userId).setValue(user);
    }
}
