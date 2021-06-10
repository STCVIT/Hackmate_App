package com.example.hackmate.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.hackmate.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TeamsFragment extends Fragment {

    Button joinTeamDetails, joinCode;
    BottomNavigationView bottomNavigation;
    int GET_NAV_CODE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teams, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            GET_NAV_CODE = bundle.getInt("Key", 0);
        }

        joinTeamDetails = view.findViewById(R.id.joinTeamDetails);
        joinCode = view.findViewById(R.id.joinUsingCode);
        bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);

        joinCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Code will be asked though dialog box", Toast.LENGTH_SHORT).show();
            }
        });

        joinTeamDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestTeamFragment frag = new RequestTeamFragment();

                if(GET_NAV_CODE==1) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("Key", 1);
                    frag.setArguments(bundle);
                }
                bottomNavigation.setVisibility(View.GONE);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,frag)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }
}