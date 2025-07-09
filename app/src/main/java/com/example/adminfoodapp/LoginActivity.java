package com.example.adminfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adminfoodapp.databinding.ActivityLoginBinding;
import com.example.adminfoodapp.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;


public class LoginActivity extends AppCompatActivity {

    private String userName = null;
    private String nameOfRestaurant = null;
    private String email;
    private String password;
    private FirebaseAuth auth;
    private DatabaseReference database;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sử dụng ViewBinding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo Firebase Auth và Database
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        // Xử lý sự kiện click cho loginButton
        binding.loginButton.setOnClickListener(v -> {
            // Lấy giá trị từ các trường nhập liệu
            email = binding.email.getText().toString().trim();
            password = binding.password.getText().toString().trim();

            // Kiểm tra trường rỗng
            if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Please Fill All Details", Toast.LENGTH_SHORT).show();
            } else {
                createUserAccount(email, password);
            }
        });

        // Xử lý sự kiện click cho dontHaveAccountButton
        binding.dontHaveAccountButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void createUserAccount(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        updateUI(user);
                    } else {
                        auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(this, task1 -> {
                                    if (task1.isSuccessful()) {
                                        FirebaseUser user = auth.getCurrentUser();
                                        Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        saveUserData(user);
                                        updateUI(user);
                                    } else {
                                        Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                        Log.d("Account", "createUserAccount: Authentication failed", task1.getException());
                                    }
                                });
                    }
                });
    }

    private void saveUserData(FirebaseUser user) {
        if (user != null) {
            // Lấy userId từ FirebaseAuth
            String userId = user.getUid();

            // Lấy text từ edit text

            email = binding.email.getText().toString().trim();
            password = binding.password.getText().toString().trim();

            // Tạo đối tượng UserModel
            UserModel userModel = new UserModel(userName,nameOfRestaurant,email, password);

            // Lưu dữ liệu vào Firebase Database
            database.child("users").child(userId).setValue(userModel);
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // Lấy userId
            String userId = user.getUid();

            // Lấy text từ edit text
            String email = binding.email.getText().toString().trim();
            String password = binding.password.getText().toString().trim();

            // Tạo đối tượng UserModel
            UserModel userModel = new UserModel(email, password);

            // Lưu dữ liệu vào Firebase Database (nếu cần)
            database.child("users").child(userId).setValue(userModel);

            // Chuyển sang activity khác (ví dụ: MainActivity)
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}