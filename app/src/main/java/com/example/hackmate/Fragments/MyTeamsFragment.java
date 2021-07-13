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
import com.example.hackmate.Fragments.particularTeam_leaderviewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyTeamsFragment extends Fragment {

    Button particularTeam, tallyRequest,particularTeamLeaderView;
    BottomNavigationView bottomNavigation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_teams, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        particularTeam = view.findViewById(R.id.particularTeamButton);
        tallyRequest = view.findViewById(R.id.requestTally);
        particularTeamLeaderView = view.findViewById(R.id.particularTeamLeaderview);
        bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);

        particularTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigation.setVisibility(View.GONE);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,new ParticularTeamFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        tallyRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigation.setVisibility(View.GONE);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,new RequestTallyFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        particularTeamLeaderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomNavigation.setVisibility(View.GONE);

                particularTeam_leaderviewFragment frag = new particularTeam_leaderviewFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("Key", 0);
                frag.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,frag)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
