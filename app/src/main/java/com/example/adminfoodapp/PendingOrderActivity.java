package com.example.adminfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.adminfoodapp.adapter.PendingOrderAdapter;
import com.example.adminfoodapp.databinding.ActivityPendingOrderBinding;
import com.example.adminfoodapp.model.OrderDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class PendingOrderActivity extends AppCompatActivity implements PendingOrderAdapter.OnItemClicked {
    private ActivityPendingOrderBinding binding;
    private final List<String> listOfName = new ArrayList<>();
    private final List<String> listOfTotalPrice = new ArrayList<>();
    private final List<String> listOfImageFirstFoodOrder = new ArrayList<>();
    private final ArrayList<OrderDetails> listOfOrderItem = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference databaseOrderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPendingOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        databaseOrderDetails = database.getReference().child("OrderDetails");
        getOrdersDetails();
        binding.backButton.setOnClickListener(v -> finish());
    }

    private void getOrdersDetails() {
        databaseOrderDetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    OrderDetails orderDetails = orderSnapshot.getValue(OrderDetails.class);
                    if (orderDetails != null) {
                        listOfOrderItem.add(orderDetails);
                    }
                }
                addDataToListForRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error if needed
            }
        });
    }

    private void addDataToListForRecyclerView() {
        for (OrderDetails orderItem : listOfOrderItem) {
            if (orderItem.getUserName() != null) listOfName.add(orderItem.getUserName());
            if (orderItem.getTotalPrice() != null) listOfTotalPrice.add(orderItem.getTotalPrice());
            if (orderItem.getFoodImages() != null) {
                for (String img : orderItem.getFoodImages()) {
                    if (img != null && !img.isEmpty()) {
                        listOfImageFirstFoodOrder.add(img);
                    }
                }
            }
        }
        setAdapter();
    }

    private void setAdapter() {
        binding.pendingOrderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        PendingOrderAdapter adapter = new PendingOrderAdapter(this, listOfName, listOfTotalPrice, listOfImageFirstFoodOrder, this);
        binding.pendingOrderRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClickListener(int position) {
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        OrderDetails userOrderDetails = listOfOrderItem.get(position);
        intent.putExtra("UserOrderDetails", userOrderDetails);
        startActivity(intent);
    }

    @Override
    public void onItemAcceptClickListener(int position) {
        String childItemPushKey = listOfOrderItem.get(position).getItemPushKey();
        DatabaseReference clickItemOrderReference = childItemPushKey != null ? database.getReference().child("OrderDetails").child(childItemPushKey) : null;
        if (clickItemOrderReference != null) {
            clickItemOrderReference.child("orderAccepted").setValue(true);
        }
        updateOrderAcceptStatus(position);
    }

    @Override
    public void onItemDispatchClickListener(int position) {
        String dispatchItemPushKey = listOfOrderItem.get(position).getItemPushKey();
        DatabaseReference dispatchItemOrderReference = database.getReference().child("CompletedOrder").child(dispatchItemPushKey);
        dispatchItemOrderReference.setValue(listOfOrderItem.get(position)).addOnSuccessListener(aVoid -> deleteThisItemFromOrderDetails(dispatchItemPushKey));
    }

    private void deleteThisItemFromOrderDetails(String dispatchItemPushKey) {
        DatabaseReference orderDetailsItemsReference = database.getReference().child("OrderDetails").child(dispatchItemPushKey);
        orderDetailsItemsReference.removeValue().addOnSuccessListener(aVoid -> Toast.makeText(this, "OrderIs is Dispatched", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(this, "OrderIs is not Dispatched", Toast.LENGTH_SHORT).show());
    }

    private void updateOrderAcceptStatus(int position) {
        String userIdOfClickedItem = listOfOrderItem.get(position).getUserUid();
        String pushKeyOfClickedItem = listOfOrderItem.get(position).getItemPushKey();
        DatabaseReference buyHistoryReference = database.getReference().child("user").child(userIdOfClickedItem).child("BuyHistory").child(pushKeyOfClickedItem);
        buyHistoryReference.child("orderAccepted").setValue(true);
        databaseOrderDetails.child(pushKeyOfClickedItem).child("orderAccepted").setValue(true);
    }
} 