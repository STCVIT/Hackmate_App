package com.example.hackmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

import org.jetbrains.annotations.NotNull;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseAuth.getInstance()
                .getAccessToken(true)
                .addOnSuccessListener(getTokenResult -> {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent.putExtra("idToken", getTokenResult.getToken());
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    //Log.e(TAG, "onCreate: ", e);
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                });
    }
}