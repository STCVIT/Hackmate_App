package com.example.hackmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.telecom.Call;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.hackmate.Fragments.HackListFragment;
import com.example.hackmate.Fragments.MyTeamsFragment;
import com.example.hackmate.Fragments.MyProfileFragment;
import com.example.hackmate.Fragments.FindTeamsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private static String idToken=null;

    //Fragments
    private Fragment activeFragment;
    private HackListFragment homeFragment = new HackListFragment();
    private MyTeamsFragment myTeamsFragment = new MyTeamsFragment();
    private FindTeamsFragment findTeamsFragment = new FindTeamsFragment();
    private MyProfileFragment myProfileFragment = new MyProfileFragment();
    BadgeDrawable badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idToken = getIntent().getStringExtra("idToken");
        bottomNavigation = findViewById(R.id.bottom_nav_bar);
        bottomNavigation.setVisibility(View.VISIBLE);
        badge = bottomNavigation.getOrCreateBadge(R.id.nav_myTeams);
        badge.setVisible(true);
        bottomNavigation();

    }
    public static String getidToken() {
        return idToken;
    }

    private void bottomNavigation() {
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, myTeamsFragment).hide(myTeamsFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, findTeamsFragment).hide(findTeamsFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, myProfileFragment).hide(myProfileFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, homeFragment).commit();

        activeFragment = homeFragment;
        bottomNavigation.setSelectedItemId(R.id.nav_home);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Fragment selectedFragment = homeFragment;
                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                selectedFragment = homeFragment;
                                break;
                            case R.id.nav_myTeams:
                                selectedFragment = myTeamsFragment;
                                break;
                            case R.id.nav_teams:
                                selectedFragment = findTeamsFragment;
                                break;
                            case R.id.nav_profile:
                                selectedFragment = myProfileFragment;
                                break;
                        }
                        if (selectedFragment != activeFragment) {
                            //Bundle bundle = new Bundle();
                            //bundle.putString("idToken", Mainid);
                            //SelectedFragment.setArguments(bundle);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .show(selectedFragment)
                                    .hide(activeFragment)
                                    .commit();
                            activeFragment = selectedFragment;
                        }
                    }
                });
                return true;
            }
        });

    }
    int count =0;

    /*
    @Override
    public void onBackPressed() {
        if(bottomNavigation.getSelectedItemId()==R.id.nav_home){
            count++;
            if(count==1){
                Snackbar.make(findViewById(android.R.id.content), "Press back again to exit", BaseTransientBottomBar.LENGTH_SHORT)
                        .addCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                count = 0;
                            }
                        })
                        .show();
            }else
                super.onBackPressed();
        }else
            bottomNavigation.setSelectedItemId(R.id.nav_home);
    }
    */

}