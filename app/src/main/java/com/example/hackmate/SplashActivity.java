package com.example.hackmate;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;

        if(connected) {

            FirebaseAuth.getInstance()
                    .getAccessToken(true)
                    .addOnSuccessListener(getTokenResult -> {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
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
        else
        {
            Snackbar.make(findViewById(android.R.id.content), "No internet connection !!", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null)
                    .setBackgroundTint(ContextCompat.getColor(getBaseContext(),R.color.pill_color ))
                    .setTextColor(ContextCompat.getColor(getBaseContext(),R.color.background))
                    .show();
        }
    }
}