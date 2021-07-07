package com.example.hackmate.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.hackmate.R;
import com.example.hackmate.inviteAdapter;
import com.example.hackmate.invite_Model;
import com.example.hackmate.teamMember_Model;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class RequestTallyFragment extends Fragment {

    Button request,accept,reject;
    private RecyclerView RVinvite;
    private ArrayList<invite_Model> InviteArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_request_tally, container, false);

//inflate appbar for this particular fragment
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.InviteAppBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return v;
    }

    @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RVinvite = view.findViewById(R.id.InviteRV);
        initData();

        setRecyclerView();
        accept=view.findViewById(R.id.acceptButton);
        reject = view.findViewById(R.id.rejectButton);
        accept.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Invite is accepted", Toast.LENGTH_SHORT).show();
            }
        });
        reject.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Invite is rejected", Toast.LENGTH_SHORT).show();
            }
        });

        /*request = view.findViewById(R.id.requestButton);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Invites can be accepted and rejected !!!", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void setRecyclerView() {
        inviteAdapter inviteAdapter= new inviteAdapter(InviteArrayList);
        RVinvite.setAdapter(inviteAdapter);
    }

    private void initData() {
        InviteArrayList = new ArrayList<>();

        InviteArrayList.add(new invite_Model.InviteModel("Muhammad Muaazz Zuberi","GreenCoders",R.drawable.imageholder,"Invites"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        BottomNavigationView bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigation.setVisibility(View.VISIBLE);
    }

}
