package com.example.adminfoodapp;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class MainActivity extends AppCompatActivity {

    CardView cardDispatch;
    CardView cardProfile;

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
    }


}
