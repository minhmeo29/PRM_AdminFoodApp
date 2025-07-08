package com.example.adminfoodapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.adminfoodapp.adapter.OrderDetailsAdapter;
import com.example.adminfoodapp.databinding.ActivityOrderDetailsBinding;
import com.example.adminfoodapp.model.OrderDetails;
import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {
    private ActivityOrderDetailsBinding binding;
    private String userName;
    private String address;
    private String phoneNumber;
    private String totalPrice;
    private ArrayList<String> foodNames = new ArrayList<>();
    private ArrayList<String> foodImages = new ArrayList<>();
    private ArrayList<Integer> foodQuantity = new ArrayList<>();
    private ArrayList<String> foodPrices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backeButton.setOnClickListener(v -> finish());
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        OrderDetails receivedOrderDetails = (OrderDetails) getIntent().getSerializableExtra("UserOrderDetails");
        if (receivedOrderDetails != null) {
            userName = receivedOrderDetails.getUserName();
            foodNames = (ArrayList<String>) receivedOrderDetails.getFoodNames();
            foodImages = (ArrayList<String>) receivedOrderDetails.getFoodImages();
            foodQuantity = (ArrayList<Integer>) receivedOrderDetails.getFoodQuantities();
            address = receivedOrderDetails.getAddress();
            phoneNumber = receivedOrderDetails.getPhoneNumber();
            foodPrices = (ArrayList<String>) receivedOrderDetails.getFoodPrices();
            totalPrice = receivedOrderDetails.getTotalPrice();
            setUserDetail();
            setAdapter();
        }
    }

    private void setUserDetail() {
        binding.name.setText(userName);
        binding.address.setText(address);
        binding.phone.setText(phoneNumber);
        binding.totalPay.setText(totalPrice);
    }

    private void setAdapter() {
        binding.orderDetailRecyclerVew.setLayoutManager(new LinearLayoutManager(this));
        OrderDetailsAdapter adapter = new OrderDetailsAdapter(this, foodNames, foodImages, foodQuantity, foodPrices);
        binding.orderDetailRecyclerVew.setAdapter(adapter);
    }
} 