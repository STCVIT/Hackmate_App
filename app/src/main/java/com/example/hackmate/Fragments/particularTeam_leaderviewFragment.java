package com.example.hackmate.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hackmate.Fragments.AddProjectFragment;
import com.example.hackmate.Fragments.EditProjectFragment;
import com.example.hackmate.Fragments.InviteParticipantFragment;
import com.example.hackmate.R;
import com.example.hackmate.teamMember_LeaderAdapter;
import com.example.hackmate.teamMember_Model;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class particularTeam_leaderviewFragment extends Fragment {
    private RecyclerView team_LeaderRV;
    // Arraylist for storing data
    private ArrayList<teamMember_Model> teamModelArrayList;

    ImageView editProject,addProjectIcon;

    TextView addProjectTV,InviteParticpant;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_particular_team_leaderview, container, false);

//inflate appbar for this particular fragment
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.particularTeamAppBar_Leader);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        team_LeaderRV = view.findViewById(R.id.rvTeam_Leader);
        editProject = view.findViewById(R.id.editProject);
        addProjectIcon = view.findViewById(R.id.addProject_Icon);
addProjectTV=view.findViewById(R.id.addProject_TextView);
InviteParticpant=view.findViewById(R.id.inviteparticipant_textView);
// here we have created new array list and added data to it.
        teamModelArrayList = new ArrayList<>();

        teamModelArrayList.add(new teamMember_Model.TeamMemberModel("1.", "Deep Gandhi","itsverydeep@gmail.com","Leader",R.drawable.imageholder,true));
        teamModelArrayList.add(new teamMember_Model.TeamMemberModel("2.", "Deep Gandhi","itsverydeep@gmail.com"," ",R.drawable.imageholder,false));
        teamModelArrayList.add(new teamMember_Model.TeamMemberModel("3.", "Deep Gandhi","itsverydeep@gmail.com"," ",R.drawable.imageholder,false));
        teamModelArrayList.add(new teamMember_Model.TeamMemberModel("4.", "Deep Gandhi","itsverydeep@gmail.com"," ",R.drawable.imageholder,false));
        // we are initializing our adapter class and passing our arraylist to it.
        teamMember_LeaderAdapter teamMember_LeaderAdapter = new teamMember_LeaderAdapter(getContext(), teamModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        team_LeaderRV.setLayoutManager(linearLayoutManager);
        team_LeaderRV.setAdapter(teamMember_LeaderAdapter);

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

        addProjectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,new AddProjectFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });
        addProjectTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,new AddProjectFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });
        InviteParticpant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,new InviteParticipantFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        BottomNavigationView bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigation.setVisibility(View.VISIBLE);
    }
}
