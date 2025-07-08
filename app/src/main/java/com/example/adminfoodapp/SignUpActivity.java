package com.example.adminfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adminfoodapp.databinding.ActivitySignUpBinding;
import com.example.adminfoodapp.model.UserModel;
import com.google.firebase.Firebase;
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

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        // Set up location dropdown
        String[] locationList = {"Hà Nội", "Nam Định", "Hải Phòng", "Quảng Ninh"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                locationList
        );
        binding.listOfLocation.setAdapter(adapter);

        // Click listener for Create Account
        binding.createUserButton.setOnClickListener(v -> {
            userName = binding.name.getText().toString().trim();
            nameOfRestaurant = binding.restaurantName.getText().toString().trim();
            email = binding.emailOrPhone.getText().toString().trim();
            password = binding.passsword.getText().toString().trim();

            if (userName.isEmpty() || nameOfRestaurant.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
            } else {
                createAccount(email, password);
            }
        });

        // Click listener for Already Have Account
        binding.alreadyHaveAccountButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });
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
        userName = binding.name.getText().toString().trim();
        nameOfRestaurant = binding.restaurantName.getText().toString().trim();
        email = binding.emailOrPhone.getText().toString().trim();
        password = binding.passsword.getText().toString().trim();
        UserModel user = new UserModel(userName, nameOfRestaurant, email, password);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database.child("user").child(userId).setValue(user);
    }
}