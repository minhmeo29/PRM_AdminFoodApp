package com.example.adminfoodapp;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    CardView cardDispatch;
    CardView cardProfile;
    CardView cardCreateUser;
    CardView cardAllItemMenu;
    CardView cardAddMenu;

    CardView cardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Layout chính của MainActivity

        // Áp dụng EdgeToEdge cho trải nghiệm toàn màn hình
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các CardView từ layout
        cardDispatch = findViewById(R.id.cardDispatch);
        cardProfile = findViewById(R.id.cardProfile);
        cardCreateUser = findViewById(R.id.cardCreateUser);
        cardAllItemMenu = findViewById(R.id.cardAllItemMenu);
        cardAddMenu = findViewById(R.id.cardAddMenu);
        cardLogout = findViewById(R.id.cardLogout);


        // Xử lý khi click vào Dispatch (chuyển sang màn hình OutForDelivery)
        cardDispatch.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, OutForDeliveryActivity.class);
            startActivity(intent);
        });

        // Xử lý khi click vào Profile (chuyển sang màn hình AdminProfile)
        cardProfile.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AdminProfileActivity.class);
            startActivity(intent);
        });

        // Xử lý khi click vào Create New User (chuyển sang màn hình CreateUserActivity)
        cardCreateUser.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreateUserActivity.class);
            startActivity(intent);
        });

        // Xử lý khi click vào All Item Menu (chuyển sang màn hình AllItemActivity)
        cardAllItemMenu.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AllItemActivity.class);
            startActivity(intent);
        });

        cardAddMenu.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            startActivity(intent);
        });


        cardLogout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut(); // <== Đăng xuất khỏi FirebaseAuth
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa toàn bộ back stack
            startActivity(intent);
            finish(); // Đóng MainActivity
        });


    }


}
