package com.example.hackmate.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.Adapters.HomeAdapter;
import com.example.hackmate.HomeModel;
import com.example.hackmate.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements HomeAdapter.OnHackListerner {
    private RecyclerView HackRV;
    // Arraylist for storing data
    private ArrayList<HomeModel> HomeArrayList;
    //Button hackInfo;
    BottomNavigationView bottomNavigation;
    private Button hackInfo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_home, container, false);

//inflate appbar for this particular fragment
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.HomeAppBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // hackInfo = view.findViewById(R.id.hackInfoClick);
        bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);
        HackRV = view.findViewById(R.id.RVHack);
        HomeArrayList = new ArrayList<>();

        HomeArrayList.add(new HomeModel.home_Model(R.drawable.hackimage,"DevSoc","3 to 5","May 21, 2021","May 21,2021"));
        HomeArrayList.add(new HomeModel.home_Model(R.drawable.hackimage,"DevSoc","3 to 5","May 21, 2021","May 21,2021"));
        HomeArrayList.add(new HomeModel.home_Model(R.drawable.hackimage,"DevSoc","3 to 5","May 21, 2021","May 21,2021"));
        HomeArrayList.add(new HomeModel.home_Model(R.drawable.hackimage,"DevSoc","3 to 5","May 21, 2021","May 21,2021"));
        HomeArrayList.add(new HomeModel.home_Model(R.drawable.hackimage,"DevSoc","3 to 5","May 21, 2021","May 21,2021"));
        HomeArrayList.add(new HomeModel.home_Model(R.drawable.hackimage,"DevSoc","3 to 5","May 21, 2021","May 21,2021"));

        // we are initializing our adapter class and passing our arraylist to it.
        HomeAdapter homeAdapter = new HomeAdapter(getContext(), HomeArrayList,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        HackRV.setLayoutManager(linearLayoutManager);
        HackRV.setAdapter(homeAdapter);
      /* hackInfo=view.findViewById(R.id.hackInfo);

        hackInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigation.setVisibility(View.GONE);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,new HackInfoFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });*/

    }

    @Override
    public void OnHackClick(int position) {
        bottomNavigation.setVisibility(View.GONE);
        HomeArrayList.get(position);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment,new HackInfoFragment())
                .addToBackStack(null)
                .commit();

    }
}