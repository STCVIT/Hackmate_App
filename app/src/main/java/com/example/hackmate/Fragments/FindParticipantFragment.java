package com.example.hackmate.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.hackmate.Adapters.InviteAdapter;
import com.example.hackmate.R;
import com.google.android.material.chip.ChipGroup;

public class FindParticipantFragment extends Fragment {

    private LinearLayout filter;
    private ImageView downArrow, upArrow;
    private ChipGroup chips;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_participant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        filter = view.findViewById(R.id.domainFilterTeammate);
        downArrow = view.findViewById(R.id.downArrow);
        upArrow = view.findViewById(R.id.upArrow);

        chips = view.findViewById(R.id.chips);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(upArrow.getVisibility() == View.GONE) {
                    chips.setVisibility(View.VISIBLE);
                    downArrow.setVisibility(View.GONE);
                    upArrow.setVisibility(View.VISIBLE);
                }
                else
                {
                    chips.setVisibility(View.GONE);
                    upArrow.setVisibility(View.GONE);
                    downArrow.setVisibility(View.VISIBLE);
                }
            }
        });

        RecyclerView inviteProfile = view.findViewById(R.id.inviteList);
        inviteProfile.setLayoutManager(new LinearLayoutManager(getContext()));
        String[] names = {"Bhavik Agrawal","Yash Jasani"};
        String[] domains = {"App Dev","Frontend"};
        inviteProfile.setAdapter(new InviteAdapter(names,domains));

    }
}