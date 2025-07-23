package com.example.adminfoodapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminfoodapp.databinding.ActivityLoginBinding;
import com.example.adminfoodapp.model.UserModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private String userName = null;
    private String nameOfRestaurant = null;
    private String email;
    private String password;

    private FirebaseAuth auth;
    private DatabaseReference database;
    private GoogleSignInClient googleSignInClient;

    private ActivityLoginBinding binding;

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            GoogleSignIn.getSignedInAccountFromIntent(data).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    GoogleSignInAccount account = task.getResult();
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    auth.signInWithCredential(credential).addOnCompleteListener(authTask -> {
                        if (authTask.isSuccessful()) {
                            Toast.makeText(this, "Successfully sign-in with Google", Toast.LENGTH_SHORT).show();
                            updateUi(authTask.getResult().getUser());
                            finish();
                        } else {
                            Toast.makeText(this, "Google Sign-in failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(this, "Google Sign-in failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Init Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Init Firebase Realtime DB
        database = FirebaseDatabase.getInstance().getReference();

        // Optional: Enable Google Sign-in

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        // Login button
        binding.loginButton.setOnClickListener(v -> {
            email = binding.email.getText().toString().trim();
            password = binding.password.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_SHORT).show();
            } else {
                createUserAccount(email, password);
            }
        });

        // Google login
        binding.GoogleButton.setOnClickListener(v -> {
            if (googleSignInClient != null) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                launcher.launch(signInIntent);
            } else {
                Toast.makeText(this, "Google Sign-In not initialized", Toast.LENGTH_SHORT).show();
            }
        });

        binding.dontHaveAccountButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void createUserAccount(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = auth.getCurrentUser();
                Toast.makeText(this, "Login SuccessFull", Toast.LENGTH_SHORT).show();
                updateUi(user);
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task2 -> {
                    if (task2.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        Toast.makeText(this, "Create User & Login SuccessFull", Toast.LENGTH_SHORT).show();
                        saveUserData();
                        updateUi(user);
                    } else {
                        Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        Log.d("Account", "createUserAccount: Authentication failed", task2.getException());
                    }
                });
            }
        });
    }

    private void saveUserData() {
        email = binding.email.getText().toString().trim();
        password = binding.password.getText().toString().trim();

        UserModel user = new UserModel(userName, email, password);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (userId != null) {
            database.child("user").child(userId).setValue(user);
        }
    }

    private void updateUi(FirebaseUser user) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = auth.getCurrentUser();
//        if (currentUser != null) {
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        }
//    }
}
