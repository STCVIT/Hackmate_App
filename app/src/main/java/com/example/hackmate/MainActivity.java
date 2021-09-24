package com.example.hackmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.hackmate.Fragments.HackListFragment;
import com.example.hackmate.Fragments.MyTeamsFragment;
import com.example.hackmate.Fragments.MyProfileFragment;
import com.example.hackmate.Fragments.FindTeams.FindTeamsFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private static String idToken=null;

    //Fragments
    private Fragment activeFragment;
    private HackListFragment homeFragment = new HackListFragment();
    private MyTeamsFragment myTeamsFragment = new MyTeamsFragment();
    private MyProfileFragment myProfileFragment = new MyProfileFragment();
    private FindTeamsFragment findTeamsFragment = new FindTeamsFragment();

    int value;

    public static void setIdToken(String token) {
        idToken = token;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent().getStringExtra("idToken")!=null)
            idToken = getIntent().getStringExtra("idToken");

        if(getIntent().getIntExtra("Frag",0)!=0) {
            value = getIntent().getIntExtra("Frag",0);
        }

        if(value==2)
            activeFragment = myTeamsFragment;
        else if(value==3)
            activeFragment = myProfileFragment;
        else
            activeFragment = homeFragment;

        bottomNavigation = findViewById(R.id.bottom_nav_bar);
        bottomNavigation.setVisibility(View.VISIBLE);
        bottomNavigation();

    }
    public static String getIdToken() {
        return idToken;
    }

    private void bottomNavigation() {
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, myTeamsFragment).hide(myTeamsFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, findTeamsFragment).hide(findTeamsFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, myProfileFragment).hide(myProfileFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, homeFragment).hide(homeFragment).commit();


        getSupportFragmentManager().beginTransaction().show(activeFragment).commit();
        //activeFragment = homeFragment;
        if(value==2)
            bottomNavigation.setSelectedItemId(R.id.nav_myTeams);
        else if(value==3)
            bottomNavigation.setSelectedItemId(R.id.nav_profile);
        else
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

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag("Create");
        if(fragment==null)
        {
            if(fm.getBackStackEntryCount()==0)
            {
                if(bottomNavigation.getSelectedItemId() ==  R.id.nav_home) {
                    count++;
                    if(count==1)
                    {
                        Snackbar.make(findViewById(android.R.id.content), "Press back again to exit", BaseTransientBottomBar.LENGTH_SHORT)
                                .addCallback(new Snackbar.Callback() {
                                    @Override
                                    public void onDismissed(Snackbar transientBottomBar, int event) {
                                        count = 0;
                                    }
                                })
                                .setBackgroundTint(ContextCompat.getColor(getBaseContext(),R.color.pill_color ))
                                .setTextColor(ContextCompat.getColor(getBaseContext(),R.color.background))
                                .show();
                    }else
                        super.onBackPressed();
                }
                else
                    bottomNavigation.setSelectedItemId(R.id.nav_home);
            }
            else
                super.onBackPressed();
        }
        else if(fm.findFragmentByTag("Invite")==null && fm.findFragmentByTag("Add")==null){
            fm.popBackStack();
            fm.popBackStack();
        }
        else
            super.onBackPressed();
    }
}