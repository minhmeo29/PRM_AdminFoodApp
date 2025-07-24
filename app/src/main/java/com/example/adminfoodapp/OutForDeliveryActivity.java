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
import com.example.adminfoodapp.model.OrderDetails;
import com.google.firebase.database.Query;

public class OutForDeliveryActivity extends AppCompatActivity {

    private ImageButton backButton;
    private RecyclerView deliveryRecyclerView;
    private DatabaseReference databaseOrderDetails;
    private ArrayList<OrderDetails> listOfCompleteOrderList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_for_delivery); // XML bạn gửi phải tên này

        // Ánh xạ view từ layout XML
        backButton = findViewById(R.id.backButton);
        deliveryRecyclerView = findViewById(R.id.deliveryRecyclerView);

        // Bắt sự kiện nút quay lại
        backButton.setOnClickListener(v -> finish());

        retrieveCompleteOrderDetail();
    }

    private void retrieveCompleteOrderDetail() {
        // Lấy dữ liệu từ OrderDetails, chỉ lấy đơn orderAccepted = true
        Query orderDetailsReference = FirebaseDatabase.getInstance().getReference()
            .child("OrderDetails").orderByChild("orderAccepted").equalTo(true);
        orderDetailsReference.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
                listOfCompleteOrderList.clear();
                for (com.google.firebase.database.DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    OrderDetails completeOrder = orderSnapshot.getValue(OrderDetails.class);
                    if (completeOrder != null) {
                        listOfCompleteOrderList.add(completeOrder);
                    }
                }
                java.util.Collections.reverse(listOfCompleteOrderList);
                setDataIntoRecyclerView();
            }
            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError error) {
                // Handle error if needed
            }
        });
    }

    private void setDataIntoRecyclerView() {
        com.example.adminfoodapp.adapter.DeliveryAdapter adapter = new com.example.adminfoodapp.adapter.DeliveryAdapter(listOfCompleteOrderList);
        deliveryRecyclerView.setAdapter(adapter);
        deliveryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
