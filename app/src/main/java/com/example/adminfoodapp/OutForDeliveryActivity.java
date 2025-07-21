package com.example.adminfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminfoodapp.adapter.DeliveryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;

import java.util.ArrayList;

public class OutForDeliveryActivity extends AppCompatActivity {

    private ImageButton backButton;
    private RecyclerView deliveryRecyclerView;
    private DatabaseReference databaseOrderDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_for_delivery); // XML bạn gửi phải tên này

        // Ánh xạ view từ layout XML
        backButton = findViewById(R.id.backButton);
        deliveryRecyclerView = findViewById(R.id.deliveryRecyclerView);

        // Bắt sự kiện nút quay lại
        backButton.setOnClickListener(v -> finish());

        databaseOrderDetails = FirebaseDatabase.getInstance().getReference().child("OrderDetails");
        getOrdersDetails();
    }

    private void getOrdersDetails() {
        databaseOrderDetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<com.example.adminfoodapp.model.OrderDetails> orderList = new ArrayList<>();
                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    com.example.adminfoodapp.model.OrderDetails order = orderSnapshot.getValue(com.example.adminfoodapp.model.OrderDetails.class);
                    if (order != null && order.isOrderAccepted()) {
                        orderList.add(order);
                    }
                }
                setAdapter(orderList);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error if needed
            }
        });
    }

    private void setAdapter(ArrayList<com.example.adminfoodapp.model.OrderDetails> orderList) {
        com.example.adminfoodapp.adapter.DeliveryAdapter adapter = new com.example.adminfoodapp.adapter.DeliveryAdapter(orderList);
        deliveryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        deliveryRecyclerView.setAdapter(adapter);
    }
}
