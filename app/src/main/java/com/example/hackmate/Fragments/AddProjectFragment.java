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
import com.example.hackmate.POJOClasses.Kavita.Projects.IndividualProject;
import com.example.hackmate.POJOClasses.Kavita.Projects.addOReditProject.addProjectPOJO;
import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddProjectFragment extends Fragment {
    public String idToken, projectID, teamID;
    public int ProjectType;
    Button submitProject;
    TextView projectName, projectDescription, gitHubLink, designLink, demoLink;
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
        idToken = MainActivity.getIdToken();


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            GET_NAV_CODE = bundle.getInt("Key", 0);
            teamID = bundle.getString("teamID", null);
            ProjectType=bundle.getInt("ProjectType",0);
//            Log.i("teamIDADDPROJECR", teamID);
        }
        projectName = view.findViewById(R.id.projectName);
        projectDescription = view.findViewById(R.id.projectDescription);
        gitHubLink = view.findViewById(R.id.githubLink);
        designLink = view.findViewById(R.id.designLink);
        demoLink = view.findViewById(R.id.demoLink);
        submitProject = view.findViewById(R.id.submitProject);

        submitProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (projectName.getText().toString().isEmpty() ) {
                    projectName.setError("Project Name  Required");
                    projectName.requestFocus();
                    return;        }
                else if (projectDescription.getText().toString().isEmpty()) {
                    projectDescription.setError("Project Description  Required");
                    projectDescription.requestFocus();
                    return;
                }
                addProject(teamID,ProjectType);
                // Toast.makeText(getActivity(), "Project has been added successfully !!!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void addProject(String a, int b) {
        JSONPlaceHolderAPI jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);
        addProjectPOJO addProjectPOJO = new addProjectPOJO(projectName.getText().toString(), projectDescription.getText().toString(), gitHubLink.getText().toString(), designLink.getText().toString(), demoLink.getText().toString());
        if(a!= null && b==0) {

            Call<addProjectPOJO> call = jsonPlaceHolderAPI.addProject(idToken, teamID, addProjectPOJO);
      /*  if (projectName.getText().toString().isEmpty()) {
            projectName.setError("Project Name  Required");
            projectName.requestFocus();
            return;
        } else if (projectDescription.getText().toString().isEmpty()) {
            projectDescription.setError("Project description required");
            projectDescription.requestFocus();
            return;
        }
          else {*/
            call.enqueue(new Callback<com.example.hackmate.POJOClasses.Kavita.Projects.addOReditProject.addProjectPOJO>() {
                @Override
                public void onResponse(Call<com.example.hackmate.POJOClasses.Kavita.Projects.addOReditProject.addProjectPOJO> call, Response<com.example.hackmate.POJOClasses.Kavita.Projects.addOReditProject.addProjectPOJO> response) {

                    if (!response.isSuccessful()) {
                        Log.i("sucess", "sucess");
                    }
                    com.example.hackmate.POJOClasses.Kavita.Projects.addOReditProject.addProjectPOJO addProjectPOJOResponse = response.body();
                    Log.i("addProject", String.valueOf(response.code()));
                    Toast.makeText(getContext(), "Project created !!!", Toast.LENGTH_LONG).show();
                    /*Log.i("addproject2", addProjectPOJOResponse.getProjectName());
                    Log.i("addproject3", addProjectPOJOResponse.getDescription());
                    Log.i("addproject4", addProjectPOJOResponse.getGithubLink());
                    Log.i("addproject5", addProjectPOJOResponse.getDesignLink());
                    Log.i("addProject6", addProjectPOJO.getDemoLink());*/
              /*projectID=addProjectPOJOResponse.getProjectID();
              Log.i("addProject7","ProjectID:"+projectID);*/
                   /* TeamProfileLeaderViewFragment frag = new TeamProfileLeaderViewFragment();


                    Bundle bundle = new Bundle();
                    bundle.putInt("Keys", 1);

                    bundle.putString("teamID", teamID);
                    frag.setArguments(bundle);

                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment, frag)
                            .addToBackStack(null)
                            .commit();*/
                }

                @Override
                public void onFailure(Call<com.example.hackmate.POJOClasses.Kavita.Projects.addOReditProject.addProjectPOJO> call, Throwable t) {
                    Log.i("error", t.getMessage());
                }
            });
        }

        else if(a==null && b==1){
            IndividualProject individualProject=new IndividualProject(projectName.getText().toString(), projectDescription.getText().toString(), gitHubLink.getText().toString(), designLink.getText().toString(), demoLink.getText().toString());
            Call<IndividualProject> call1 = jsonPlaceHolderAPI.addMyProject(idToken,individualProject);
            call1.enqueue(new Callback<IndividualProject>() {
                @Override
                public void onResponse(Call<IndividualProject> call1, Response<IndividualProject> response1) {
                    if (!response1.isSuccessful()) {
                        Log.i("sucess", "sucess");
                    }
                    IndividualProject individualProjectResponse = response1.body();
                    Log.i("IndividualProject", String.valueOf(response1.code()));
                    Toast.makeText(getContext(), "Project created !!!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<IndividualProject> call1, Throwable t) {
                    Log.i("error", t.getMessage());
                }
            });
        }
        //}

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (GET_NAV_CODE == 1) {
            BottomNavigationView bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);
            bottomNavigation.setVisibility(View.VISIBLE);
        }
    }
}