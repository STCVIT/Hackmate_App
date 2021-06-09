package com.example.hackmate.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hackmate.R;
import com.google.android.material.chip.ChipGroup;

public class InviteParticipantFragment extends Fragment {

    Button sendInvite, goProfile;
    LinearLayout filter;
    ImageView downArrow, upArrow;
    ChipGroup chips;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invite_participant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sendInvite = view.findViewById(R.id.sendInviteButton);
        goProfile = view.findViewById(R.id.participantProfileButton);
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

        sendInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Invite sent successfully !!", Toast.LENGTH_SHORT).show();
            }
        });

        goProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParticipantProfileFragment frag = new ParticipantProfileFragment();

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