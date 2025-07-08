package com.example.adminfoodapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminProfileActivity extends AppCompatActivity {

    private EditText editName, editAddress, editEmail, editPhone, editPassword;
    private TextView editButton;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        // Ánh xạ view
        editName = findViewById(R.id.name);
        editAddress = findViewById(R.id.address);
        editEmail = findViewById(R.id.email);
        editPhone = findViewById(R.id.phone);
        editPassword = findViewById(R.id.password);

        editButton = findViewById(R.id.editButton);
        backButton = findViewById(R.id.backButton);

        // Nút quay lại
        backButton.setOnClickListener(v -> finish());

        // Nút cho phép chỉnh sửa
        editButton.setOnClickListener(v -> {
            editName.setEnabled(true);
            editAddress.setEnabled(true);
            editEmail.setEnabled(true);
            editPhone.setEnabled(true);
            editPassword.setEnabled(true);
            Toast.makeText(this, "Edit mode enabled", Toast.LENGTH_SHORT).show();
        });

        // Nút lưu thông tin
        findViewById(R.id.saveButton).setOnClickListener(v -> {
            String name = editName.getText().toString();
            String address = editAddress.getText().toString();
            String email = editEmail.getText().toString();
            String phone = editPhone.getText().toString();
            String password = editPassword.getText().toString();

            // TODO: Validate + gửi thông tin đến server hoặc lưu cục bộ
            Toast.makeText(this, "Saved successfully!", Toast.LENGTH_SHORT).show();
        });
    }
}
