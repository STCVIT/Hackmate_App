package com.example.hackmate.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.example.hackmate.POJOClasses.Kavita.addProjectPOJO;
import com.example.hackmate.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class AddProjectFragment extends Fragment {

    Button submitProject;
    int GET_NAV_CODE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_add_project, container, false);


        Toolbar toolbar = (Toolbar) v.findViewById(R.id.topAppBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            GET_NAV_CODE = bundle.getInt("Key", 0);
        }

        submitProject = view.findViewById(R.id.submitProject);
        submitProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Project has been added successfully !!!", Toast.LENGTH_SHORT).show();
            }
        });

        addProject();
    }

    private void addProject() {

        addProjectPOJO addProjectPOJO = new addProjectPOJO(" Niddhi","zfcxhxfhfgugjfjgcjjfghj");

        /*Call<addProjectPOJO> call= jsonPlaceHolderApi.addProject(addProjectPOJO);

        call.enqueue(new Callback<com.example.hackmate.POJOClasses.addProjectPOJO>() {
            @Override
            public void onResponse(Call<com.example.hackmate.POJOClasses.addProjectPOJO> call, Response<com.example.hackmate.POJOClasses.addProjectPOJO> response) {

                if (!response.isSuccessful()){
                    Log.i("sucess", "sucess");
                }
            }

            @Override
            public void onFailure(Call<com.example.hackmate.POJOClasses.addProjectPOJO> call, Throwable t) {
                Log.i("error", t.getMessage());
            }
        });*/

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(GET_NAV_CODE==1) {
            BottomNavigationView bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);
            bottomNavigation.setVisibility(View.VISIBLE);
        }
    }
}
