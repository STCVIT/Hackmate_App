package com.example.hackmate.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hackmate.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;


public class HomeFragment extends Fragment {

   Button hackInfo;
   ChipNavigationBar bottomNavigation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hackInfo = view.findViewById(R.id.hackInfoClick);
        bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);

        hackInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigation.setVisibility(View.GONE);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,new HackInfoFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}