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
import android.widget.Button;
import android.widget.Toast;

import com.example.hackmate.Adapters.MemberAdapter;
import com.example.hackmate.Adapters.ProjectAdapterRT;
import com.example.hackmate.Models.ProjectModel;
import com.example.hackmate.R;
import com.example.hackmate.Models.teamMember_Model;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class RequestTeamFragment extends Fragment {

    Button requestJoin;
    int GET_NAV_CODE = 0;
    RecyclerView participants_recyclerView, projects_recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_team, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            GET_NAV_CODE = bundle.getInt("Key", 0);
        }

        initialise();

        requestJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Request Sent !!", Toast.LENGTH_SHORT).show();
                requestJoin.setBackground(getResources().getDrawable(R.drawable.ic_button_border_bg));
                requestJoin.setTextColor(getResources().getColor(R.color.green));
            }
        });

        participants_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        teamMember_Model.TeamMemberModel model = new teamMember_Model.TeamMemberModel("1.", "yash", "yash@gmail.com", "leader", R.drawable.bhavik, true);
        teamMember_Model.TeamMemberModel model1 = new teamMember_Model.TeamMemberModel("2.", "bhavik", "bhavik@gmail.com", "", R.drawable.bhavik, true);
        ArrayList arrayList = new ArrayList<teamMember_Model>();
        arrayList.add(model);
        arrayList.add(model1);
        arrayList.add(model);
        arrayList.add(model1);
        participants_recyclerView.setAdapter(new MemberAdapter(getContext(), arrayList));

        projects_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ProjectModel model2 = new ProjectModel("Hackmate",
                "Project for team building for hackathons",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Tristique mauris, " +
                        "nec vitae cursus phasellus a proin et. Sit in velit duis iaculis est. " +
                        "At odio sociis venenatis ut commodo. Aliquet eget morbi faucibus nisl " +
                        "nec quis suscipit ut. Mus vestibulum risus at ante lorem volutpat. " +
                        "In vitae vitae, tortor a ipsum ipsum. Ipsum cras eu odio natoque blandit commodo aliquam.",
                "abc@gmail.com", "abc@gmail.com","abc@gmail.com");
        ArrayList arrayList1 = new ArrayList<ProjectModel>();
        arrayList1.add(model2);
        arrayList1.add(model2);
        projects_recyclerView.setAdapter(new ProjectAdapterRT(getContext(), arrayList1));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(GET_NAV_CODE!=1) {
            BottomNavigationView bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);
            bottomNavigation.setVisibility(View.VISIBLE);
        }
    }

    public void initialise()
    {
        requestJoin = getView().findViewById(R.id.requestJoinTeam);
        participants_recyclerView = getView().findViewById(R.id.participants_recyclerView);
        projects_recyclerView = getView().findViewById(R.id.projects_recyclerView_RT);
    }
}