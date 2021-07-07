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

public class ParticipantProfileFragment extends Fragment {

    Button inviteParticipant;
    int GET_NAV_CODE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_participant_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            GET_NAV_CODE = bundle.getInt("Key", 0);
        }

       /* inviteParticipant = view.findViewById(R.id.sendInviteParticipant);

        if(GET_NAV_CODE==1)
            inviteParticipant.setVisibility(View.VISIBLE);

        inviteParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Invite sent successfully !!", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        inviteParticipant.setVisibility(View.GONE);
    }
}
