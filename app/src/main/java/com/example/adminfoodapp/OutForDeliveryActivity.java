package com.example.adminfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminfoodapp.adapter.DeliveryAdapter;

import java.util.ArrayList;

public class OutForDeliveryActivity extends AppCompatActivity {

    private ImageButton backButton;
    private RecyclerView deliveryRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_for_delivery); // XML bạn gửi phải tên này

        // Ánh xạ view từ layout XML
        backButton = findViewById(R.id.backButton);
        deliveryRecyclerView = findViewById(R.id.deliveryRecyclerView);

        // Bắt sự kiện nút quay lại
        backButton.setOnClickListener(v -> finish());

        // Dữ liệu mẫu
        ArrayList<String> customerNames = new ArrayList<>();
        customerNames.add("John Doe");
        customerNames.add("Jane Smith");
        customerNames.add("Mike Johnson");

        ArrayList<String> moneyStatuses = new ArrayList<>();
        moneyStatuses.add("received");
        moneyStatuses.add("notReceived");
        moneyStatuses.add("Pending");

        // Gắn adapter cho RecyclerView
        DeliveryAdapter adapter = new DeliveryAdapter(customerNames, moneyStatuses);
        deliveryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        deliveryRecyclerView.setAdapter(adapter);
    }
}
