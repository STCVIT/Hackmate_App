package com.example.hackmate.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.hackmate.LoginActivity;
import com.example.hackmate.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class Functions {
    private static final String TAG = "Functions";

    public static void fetchToken(Activity activity,TokenCallback tokenCallback) {
        FirebaseAuth.getInstance()
                .getAccessToken(true)
                .addOnSuccessListener(getTokenResult -> {
                    MainActivity.setIdToken("Bearer " + getTokenResult.getToken());
                    tokenCallback.doTask();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(activity, "Could not fetch Token! Please Login again", Toast.LENGTH_SHORT).show();
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                    activity.finish();
                });
    }
    public interface TokenCallback{
        void doTask();
    }
}
