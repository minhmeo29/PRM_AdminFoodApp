package com.example.adminfoodapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adminfoodapp.databinding.ActivityAdminProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminProfileActivity extends AppCompatActivity {

    private ActivityAdminProfileBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference adminReference;

    private boolean isEnable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        adminReference = database.getReference().child("user");

        binding.backButton.setOnClickListener(view -> finish());

        binding.saveButton.setOnClickListener(view -> updateUserData());

        // Disable input fields initially
        setInputsEnabled(false);

        binding.editButton.setOnClickListener(view -> {
            isEnable = !isEnable;
            setInputsEnabled(isEnable);
            if (isEnable) {
                binding.name.requestFocus();
            }
        });

        retrieveUserData();
    }

    private void setInputsEnabled(boolean enabled) {
        binding.name.setEnabled(enabled);
        binding.address.setEnabled(enabled);
        binding.email.setEnabled(enabled);
        binding.phone.setEnabled(enabled);
        binding.password.setEnabled(enabled);
        binding.saveButton.setEnabled(enabled);
    }

    private void retrieveUserData() {
        String currentUserUid = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;

        if (currentUserUid != null) {
            DatabaseReference userReference = adminReference.child(currentUserUid);

            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Object ownerName = snapshot.child("name").getValue();
                        Object email = snapshot.child("email").getValue();
                        Object password = snapshot.child("password").getValue();
                        Object address = snapshot.child("address").getValue();
                        Object phone = snapshot.child("phone").getValue();

                        Log.d("TAG", "onDataChange: " + ownerName);
                        setDataToTextView(ownerName, email, password, address, phone);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(AdminProfileActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setDataToTextView(Object name, Object email, Object password, Object address, Object phone) {
        binding.name.setText(name != null ? name.toString() : "");
        binding.email.setText(email != null ? email.toString() : "");
        binding.password.setText(password != null ? password.toString() : "");
        binding.phone.setText(phone != null ? phone.toString() : "");
        binding.address.setText(address != null ? address.toString() : "");
    }

    private void updateUserData() {
        String updateName = binding.name.getText().toString();
        String updateEmail = binding.email.getText().toString();
        String updatePassword = binding.password.getText().toString();
        String updatePhone = binding.phone.getText().toString();
        String updateAddress = binding.address.getText().toString();

        String currentUserUid = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;

        if (currentUserUid != null) {
            DatabaseReference userReference = adminReference.child(currentUserUid);
            userReference.child("name").setValue(updateName);
            userReference.child("email").setValue(updateEmail);
            userReference.child("password").setValue(updatePassword);
            userReference.child("phone").setValue(updatePhone);
            userReference.child("address").setValue(updateAddress);

            Toast.makeText(this, "Profile Updated Successfully 😊", Toast.LENGTH_SHORT).show();

            // Update email and password in Firebase Authentication
            if (auth.getCurrentUser() != null) {
                auth.getCurrentUser().updateEmail(updateEmail);
                auth.getCurrentUser().updatePassword(updatePassword);
            }
        } else {
            Toast.makeText(this, "Profile Update Failed 😒", Toast.LENGTH_SHORT).show();
        }
    }
}
