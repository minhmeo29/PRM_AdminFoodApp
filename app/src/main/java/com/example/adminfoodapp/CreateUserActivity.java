package com.example.adminfoodapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.adminfoodapp.databinding.ActivityCreateUserBinding;

public class CreateUserActivity extends AppCompatActivity {
    private ActivityCreateUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backButton.setOnClickListener(v -> finish());
    }
} 