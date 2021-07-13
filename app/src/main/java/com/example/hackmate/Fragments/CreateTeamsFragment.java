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


public class CreateTeamsFragment extends Fragment {

    Button createTeam;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_teams, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createTeam = view.findViewById(R.id.createTeamButtonFilter);

        createTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                particularTeam_leaderviewFragment frag = new particularTeam_leaderviewFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("Key", 1);
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