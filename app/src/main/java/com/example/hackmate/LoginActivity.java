package com.example.hackmate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.hackmate.Fragments.SignUpAccountFragment;
import com.example.hackmate.Fragments.CreateAccountFragment;
import com.example.hackmate.Fragments.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    public Fragment fragmentLogin, fragmentNewAccount, fragmentCreateAccount;
    public SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fragmentLogin = new LoginFragment();
        fragmentNewAccount = new SignUpAccountFragment();
        fragmentCreateAccount = new CreateAccountFragment();

        preferences = this.getSharedPreferences("package com.example.hackmate", Context.MODE_PRIVATE);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.bodyFragment, fragmentLogin).commit();
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else if(getFragmentManager().getBackStackEntryCount() == 1) {
            moveTaskToBack(false);
        }
        else {
            getFragmentManager().popBackStack();
        }
    }
}