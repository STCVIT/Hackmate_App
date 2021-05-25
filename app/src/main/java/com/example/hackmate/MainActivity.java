package com.example.hackmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.hackmate.Fragments.HomeFragment;
import com.example.hackmate.Fragments.MyTeamsFragment;
import com.example.hackmate.Fragments.ProfileFragment;
import com.example.hackmate.Fragments.TeamsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

    private ChipNavigationBar bottomNavigation;

    //Fragments
    private HomeFragment homeFragment = new HomeFragment();
    private MyTeamsFragment myTeamsFragment = new MyTeamsFragment();
    private TeamsFragment teamsFragment = new TeamsFragment();
    private ProfileFragment profileFragment = new ProfileFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottom_nav_bar);
        bottomNavigation.setVisibility(View.VISIBLE);

        //Automatically select the home Fragment
        if(savedInstanceState==null)
        {
            bottomNavigation.setItemSelected(R.id.nav_home,true);
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,homeFragment).commit();
        }

        bottomNavigation();

    }

    private void bottomNavigation() {

        bottomNavigation.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment selectedFragment = null;
                switch (i)
                {
                    case R.id.nav_home:
                        selectedFragment = homeFragment;
                        break;
                    case R.id.nav_teams:
                        selectedFragment = teamsFragment;
                        break;
                    case R.id.nav_myTeams:
                        selectedFragment = myTeamsFragment;
                        break;
                    case R.id.nav_profile:
                        selectedFragment = profileFragment;
                        break;
                }
                if(selectedFragment!=null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment,selectedFragment)
                            .commit();
                }
            }

        });

    }
}