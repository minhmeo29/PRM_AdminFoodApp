package com.example.adminfoodapp;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminfoodapp.databinding.ActivityAddItemBinding;
import com.example.adminfoodapp.model.AllMenu;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddItemActivity extends AppCompatActivity {

    private ActivityAddItemBinding binding;

    private String foodName;
    private String foodPrice;
    private String foodDescription;
    private String foodIngredient;
    private Uri foodImageUri;

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    private final ActivityResultLauncher<String> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    foodImageUri = uri;
                    binding.selectedImage.setImageURI(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        binding.selectImage.setOnClickListener(v -> pickImageLauncher.launch("image/*"));

        binding.AddItemButton.setOnClickListener(v -> {
            foodName = binding.foodName.getText().toString().trim();
            foodPrice = binding.foodPrice.getText().toString().trim();
            foodDescription = binding.description.getText().toString().trim();
            foodIngredient = binding.ingredint.getText().toString().trim();

            if (foodName.isEmpty() || foodPrice.isEmpty() || foodDescription.isEmpty() || foodIngredient.isEmpty()) {
                Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show();
            } else if (!isNumeric(foodPrice)) {
                Toast.makeText(this, "Price must be a number", Toast.LENGTH_SHORT).show();
            } else {
                uploadData();
            }
        });

        binding.backButton.setOnClickListener(v -> finish());
    }

    private void uploadData() {
        DatabaseReference menuRef = database.getReference("menu");
        String newItemKey = menuRef.push().getKey();

        if (foodImageUri != null && newItemKey != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference imageRef = storageRef.child("menu_images/" + newItemKey + ".jpg");

            imageRef.putFile(foodImageUri).addOnSuccessListener(taskSnapshot ->
                    imageRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                        AllMenu newItem = new AllMenu(
                                newItemKey,
                                foodName,
                                foodPrice,
                                foodDescription,
                                downloadUri.toString(),
                                foodIngredient
                        );

                        menuRef.child(newItemKey).setValue(newItem)
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(this, "Item Add Successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(this, "Data upload failed", Toast.LENGTH_SHORT).show());
                    })
            ).addOnFailureListener(e ->
                    Toast.makeText(this, "Image Upload failed", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
