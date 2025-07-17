package com.example.adminfoodapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.adminfoodapp.adapter.MenuItemAdapter;
import com.example.adminfoodapp.databinding.ActivityAllItemBinding;
import com.example.adminfoodapp.model.AllMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllItemActivity extends AppCompatActivity {

    private ActivityAllItemBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<AllMenu> menuItems = new ArrayList<>();

    private MenuItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        retrieveMenuItems();

        binding.backButton.setOnClickListener(v -> finish());
    }

    private void retrieveMenuItems() {
        DatabaseReference foodRef = database.getReference("menu");

        foodRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                menuItems.clear();

                for (DataSnapshot foodSnapshot : snapshot.getChildren()) {
                    AllMenu menuItem = foodSnapshot.getValue(AllMenu.class);
                    if (menuItem != null) {
                        menuItems.add(menuItem);
                    }
                }

                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("DatabaseError", "Error: " + error.getMessage());
            }
        });
    }

    private void setAdapter() {
        adapter = new MenuItemAdapter(this, menuItems, databaseReference, new MenuItemAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                deleteMenuItem(position);
            }
        });

        binding.MenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.MenuRecyclerView.setAdapter(adapter);
    }


    private void deleteMenuItem(int position) {
        AllMenu itemToDelete = menuItems.get(position);
        String key = itemToDelete.getKey();

        if (key != null) {
            DatabaseReference foodRef = database.getReference("menu").child(key);
            foodRef.removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    menuItems.remove(position);
                    adapter.notifyItemRemoved(position);
                } else {
                    Toast.makeText(this, "Item not deleted", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}