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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hackmate.Adapters.myTeamsAdapter;
import com.example.hackmate.R;
import com.example.hackmate.Models.myTeamsModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MyTeamsFragment extends Fragment implements myTeamsAdapter.OnTeamsListener {
    private RecyclerView TeamsRV;
    private ArrayList<myTeamsModel> myTeamsArrayList;

    BottomNavigationView bottomNavigation;

    private ImageView Bell;
    private TextView newTeams;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_my_teams, container, false);

//inflate appbar for this particular fragment
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.myTeamsAppBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TeamsRV = view.findViewById(R.id.RVTeams);
        myTeamsArrayList = new ArrayList<>();

        myTeamsArrayList.add(new myTeamsModel.myTeams_Model("Desiderata","Code2Create","Leader",R.drawable.ic_baseline_arrow_forward_ios_24,"Frontend","Backend","Machine Laerning","Mobile Dev","Backend"));
        myTeamsArrayList.add(new myTeamsModel.myTeams_Model("Desiderata","Code2Create","Member",R.drawable.ic_baseline_arrow_forward_ios_24,"Frontend","Backend","Machine Laerning","Mobile Dev","Backend"));


        bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);

        myTeamsAdapter myTeamsAdapter = new myTeamsAdapter(getContext(), myTeamsArrayList,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        TeamsRV.setLayoutManager(linearLayoutManager);
        TeamsRV.setAdapter(myTeamsAdapter);


        Bell=view.findViewById(R.id.imageView10);



        Bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigation.setVisibility(View.GONE);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,new InviteOrRequestFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        newTeams=view.findViewById(R.id.newTeam);
        newTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigation.setVisibility(View.GONE);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,new TeamCreationFormFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void OnTeamsClick(int position, String a) {
        myTeamsArrayList.get(position);

        if(a =="Leader"){
            bottomNavigation.setVisibility(View.GONE);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment,new TeamProfileLeaderViewFragment())
                    .addToBackStack(null)
                    .commit();}
        else if (a=="Member" )
        {
            bottomNavigation.setVisibility(View.GONE);

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment,new TeamProfileMemberViewFragment())
                    .addToBackStack(null)
                    .commit();
        }

    }


}