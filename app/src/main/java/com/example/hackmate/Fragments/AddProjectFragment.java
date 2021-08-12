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
import android.widget.TextView;
import android.widget.Toast;


import com.example.hackmate.JSONPlaceholders.JSONPlaceHolderAPI;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.Kavita.addProjectPOJO;
import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class AddProjectFragment extends Fragment {
    public String idToken;
    Button submitProject;
    TextView projectName,projectDescription,gitHubLink,designLink,demoLink;
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
        idToken=MainActivity.getIdToken();


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            GET_NAV_CODE = bundle.getInt("Key", 0);
        }
        projectName= view.findViewById(R.id.projectName);
        projectDescription= view.findViewById(R.id.projectDescription);
        gitHubLink= view.findViewById(R.id.githubLink);
        designLink= view.findViewById(R.id.designLink);
        demoLink= view.findViewById(R.id.demoLink);
        submitProject = view.findViewById(R.id.submitProject);
        submitProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProject();
                Toast.makeText(getActivity(), "Project has been added successfully !!!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void addProject() {

        // addProjectPOJO addProjectPOJO = new addProjectPOJO(" Niddhi","zfcxhxfhfgugjfjgcjjfghj","gfasdgfjszbf","jdhiasudgiuad","hdvuasd");
        addProjectPOJO addProjectPOJO = new addProjectPOJO(projectName.getText().toString(),projectDescription.getText().toString(),gitHubLink.getText().toString(),designLink.getText().toString(),demoLink.getText().toString());
        JSONPlaceHolderAPI jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);
        Call<addProjectPOJO> call= jsonPlaceHolderAPI.addProject(idToken,addProjectPOJO);

        call.enqueue(new Callback<com.example.hackmate.POJOClasses.Kavita.addProjectPOJO>() {
            @Override
            public void onResponse(Call<com.example.hackmate.POJOClasses.Kavita.addProjectPOJO> call, Response<com.example.hackmate.POJOClasses.Kavita.addProjectPOJO> response) {

                if (!response.isSuccessful()){
                    Log.i("sucess", "sucess");
                }
                com.example.hackmate.POJOClasses.Kavita.addProjectPOJO addProjectPOJOResponse = response.body();
                Log.i("addProject", String.valueOf(response.code()));
                projectName.setText(addProjectPOJO.getProjectName());
                projectDescription.setText(addProjectPOJO.getDescription());
                gitHubLink.setText(addProjectPOJO.getGithubLink());
                designLink.setText(addProjectPOJO.getDesignLink());
                demoLink.setText(addProjectPOJO.getDemoLink());
            }

            @Override
            public void onFailure(Call<com.example.hackmate.POJOClasses.Kavita.addProjectPOJO> call, Throwable t) {
                Log.i("error", t.getMessage());
            }
        });

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
