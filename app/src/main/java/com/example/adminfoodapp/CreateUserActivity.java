package com.example.adminfoodapp;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.adminfoodapp.databinding.ActivityCreateUserBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateUserActivity extends AppCompatActivity {
    private ActivityCreateUserBinding binding;
    private FirebaseAuth auth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        binding.backButton.setOnClickListener(v -> finish());

        binding.createUserButton.setOnClickListener(v -> {
            String name = binding.Name.getText().toString().trim();
            String email = binding.password.getText().toString().trim();
            String password = binding.passsword.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "User created successfully", Toast.LENGTH_SHORT).show();
                            String userId = auth.getCurrentUser().getUid();
                            com.example.adminfoodapp.model.UserModel user = new com.example.adminfoodapp.model.UserModel(name, "", email, password);
                            database.child("users").child(userId).setValue(user);
                            finish();
                        } else {
                            Toast.makeText(this, "User creation failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
    }
} 