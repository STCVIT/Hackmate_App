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
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class ParticularTeamFragment extends Fragment {

    Button inviteTeammate, profileParticipant,addProjectTeam, editProject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_particular_team, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inviteTeammate = view.findViewById(R.id.inviteTeammateButton);
        profileParticipant = view.findViewById(R.id.profileParticipantButton);
        addProjectTeam = view.findViewById(R.id.addProjectTeam);
        editProject = view.findViewById(R.id.editProjectButton);

        inviteTeammate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,new InviteParticipantFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });

        profileParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,new ParticipantProfileFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        addProjectTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,new AddProjectFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        editProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,new EditProjectFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ChipNavigationBar bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigation.setVisibility(View.VISIBLE);
    }
}