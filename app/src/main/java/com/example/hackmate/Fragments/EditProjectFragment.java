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
import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.JoinTeamPOJO;
import com.example.hackmate.POJOClasses.Kavita.Projects.addOReditProject.editProjectPOJO;
import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditProjectFragment extends Fragment {
    private TextView ProjectName, ProjectDescription, githubLink, designLink, demoLink, appBarTitle;
    Button saveButton;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String idToken = "Bearer ";
    public String ProjectIDEdit, teamID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_project, container, false);

//inflate appbar for this particular fragment
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.projectAppBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        saveButton = view.findViewById(R.id.saveChange);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ProjectName.getText().toString().isEmpty() ) {
                    ProjectName.setError("Project Name  Required");
                    ProjectName.requestFocus();
                    return;        }
                else if (ProjectDescription.getText().toString().isEmpty()) {
                    ProjectDescription.setError("Project Description  Required");
                    ProjectDescription.requestFocus();
                    return;
                }
                updateProject();


            }
        });

        ProjectName = view.findViewById(R.id.projectNameEdit);
        ProjectDescription = view.findViewById(R.id.projectDescriptionEdit);
        githubLink = view.findViewById(R.id.githubLinkEdit);
        designLink = view.findViewById(R.id.designLinkEdit);
        demoLink = view.findViewById(R.id.demoLinkEdit);
        appBarTitle = view.findViewById(R.id.editProject_title2);
        Bundle bundle = this.getArguments();
        ProjectIDEdit = bundle.getString("ProjectID", "");
        teamID = bundle.getString("teamID", "");

        getRetrofit();

    }

    private void getRetrofit() {


        idToken = MainActivity.getIdToken();
        JSONPlaceHolderAPI jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);
        loginAPI loginAPI = RetrofitInstance.getRetrofitInstance().create(loginAPI.class);
        Log.i("EDitProject", ProjectIDEdit);
        // Call<List<editProjectPOJO>> call5 = jsonPlaceHolderAPI.getEditProject(idToken, ProjectIDEdit);
        Call<JoinTeamPOJO> call3 = loginAPI.getTeam("Bearer " + MainActivity.getIdToken(), getArguments().getString("teamID"));
        call3.enqueue(new Callback<JoinTeamPOJO>() {
            @Override
            public void onResponse(Call<JoinTeamPOJO> call3, Response<JoinTeamPOJO> response5) {
                if (!response5.isSuccessful()) {
                    Log.i("not sucess5", "code: " + response5.code());
                    return;
                }

                ProjectName.setText(response5.body().getTeam().getProject_name());

                ProjectDescription.setText(response5.body().getTeam().getProject_description());

                githubLink.setText(response5.body().getTeam().getCode());

                designLink.setText(response5.body().getTeam().getDesign());

                demoLink.setText(response5.body().getTeam().getDemonstration());

                appBarTitle.setText(response5.body().getTeam().getProject_name());

            }

            @Override
            public void onFailure(Call<JoinTeamPOJO> call3, Throwable t) {
                Log.i("failed5", t.getMessage());
            }
        });
    }

    private void updateProject() {
        editProjectPOJO editProjectPOJO = new editProjectPOJO(ProjectName.getText().toString(), ProjectDescription.getText().toString(), githubLink.getText().toString(), designLink.getText().toString(), demoLink.getText().toString());
        JSONPlaceHolderAPI jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);

        Call<editProjectPOJO> call = jsonPlaceHolderAPI.updateProject(idToken, teamID, editProjectPOJO);
        call.enqueue(new Callback<editProjectPOJO>() {
            @Override
            public void onResponse(Call<editProjectPOJO> call, Response<editProjectPOJO> response) {

                if (!response.isSuccessful()) {
                    Log.i("sucess", "sucess");
                }
                editProjectPOJO editProjectPOJOResponse = response.body();
                Log.i("editProject", String.valueOf(response.code()));
               /*Log.i("editProjectname",editProjectPOJOResponse.getProjectName());
                Log.i("editProjectdescription",editProjectPOJOResponse.getDescription());
                Log.i("editProjectGitHUB",editProjectPOJOResponse.getGithubLink());
                Log.i("editProjectDesign",editProjectPOJOResponse.getDesignLink());
                Log.i("editProjectDemo",editProjectPOJOResponse.getDemoLink());*/
                Toast.makeText(getContext(), "Changes Saved !!!", Toast.LENGTH_LONG).show();
                /*TeamProfileLeaderViewFragment frag = new TeamProfileLeaderViewFragment();


                Bundle bundle = new Bundle();
                bundle.putInt("Keys", 1);
               *//* bundle.putString("ProjectId",ProjectIDEdit);
                bundle.putString("display","yes");*//*
                bundle.putString("teamID",teamID);
                frag.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,frag)
                        .addToBackStack(null)
                        .commit();*/
            }

            @Override
            public void onFailure(Call<editProjectPOJO> call, Throwable t) {
                Log.i("error", t.getMessage());
            }
        });
    }



}


