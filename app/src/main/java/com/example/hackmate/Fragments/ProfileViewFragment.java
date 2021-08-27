package com.example.hackmate.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackmate.Adapters.ProjectAdapterMP;
import com.example.hackmate.Adapters.ProjectAdapterP;
import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.MainActivity;
import com.example.hackmate.Models.ProjectModel;
import com.example.hackmate.POJOClasses.GetParticipantPOJO;
import com.example.hackmate.POJOClasses.IndividualProject;
import com.example.hackmate.POJOClasses.ProjectPOJO;
import com.example.hackmate.POJOClasses.Skill;
import com.example.hackmate.POJOClasses.TeamProject;
import com.example.hackmate.POJOClasses.loginPOJO;
import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileViewFragment extends Fragment {

    Button invite;
    private RecyclerView projects_recyclerView;
    ChipGroup chipGroup;
    ImageView profile_pic;
    int GET_NAV_CODE = 0;
    String id = "yash";
    private com.example.hackmate.JSONPlaceholders.loginAPI loginAPI;
    TextView name_PV, username_PV, email_PV, college_PV, bio_PV,
            github_PV, linkedIn_PV, personal_website_PV, yog_PV;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            GET_NAV_CODE = bundle.getInt("Key", 0);
            id = bundle.getString("id", "yash");
        }

        initialise();

        if (GET_NAV_CODE == 1)
            invite.setVisibility(View.GONE);
        else
            invite.setVisibility(View.VISIBLE);


        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invite.setBackground(getResources().getDrawable(R.drawable.ic_button_border_bg));
                invite.setTextColor(getResources().getColor(R.color.green));
                Toast.makeText(getContext(), "Invite Sent!!", Toast.LENGTH_SHORT).show();
            }
        });

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        loginAPI = RetrofitInstance.getRetrofitInstance().create(loginAPI.class);


        Call<GetParticipantPOJO> call = loginAPI.getParticipantByID("Bearer " + MainActivity.getIdToken(), id);//replace with participant id getting from previous fragment 60f2c642c28f930015dc3de3
        call.enqueue(new Callback<GetParticipantPOJO>() {
            @Override
            public void onResponse(Call<GetParticipantPOJO> call, Response<GetParticipantPOJO> response) {

                Log.i("response22", String.valueOf(response.body().participant.get_id()));
                name_PV.setText(response.body().participant.getName());
                username_PV.setText(response.body().participant.getUsername());
                email_PV.setText(response.body().participant.getEmail());
                college_PV.setText(response.body().participant.getCollege());
                yog_PV.setText(String.valueOf(response.body().participant.getGraduation_year()));
                bio_PV.setText(response.body().participant.getBio());
                github_PV.setText(response.body().participant.getGithub());
                linkedIn_PV.setText(response.body().participant.getLinkedIn());
                id = String.valueOf(response.body().participant.get_id());

                Call<List<Skill>> listCall = loginAPI.getSkillsP("Bearer " + MainActivity.getIdToken(),id);
                listCall.enqueue(new Callback<List<Skill>>() {
                    @Override
                    public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {
                        if (response.body() != null) {
                            List<Skill> skillList = response.body();
                            for (int i = 0; i < skillList.size(); i++) {
                                Log.i("SKILLS", skillList.get(i).getSkill());

                                Chip chip = new Chip(getContext());
                                if (skillList.get(i).getSkill().equals("ml")) {
                                    chip.setText("Machine Learning");
                                } else if (skillList.get(i).getSkill().equals("frontend")) {
                                    chip.setText("Frontend");
                                } else if (skillList.get(i).getSkill().equals("backend")) {
                                    chip.setText("Backend");
                                } else if (skillList.get(i).getSkill().equals("ui/ux")) {
                                    chip.setText("UI/UX Design");
                                } else if (skillList.get(i).getSkill().equals("management")) {
                                    chip.setText("Management");
                                } else if (skillList.get(i).getSkill().equals("appdev")) {
                                    chip.setText("App Development");
                                }
//                                chip.setChipStrokeColorResource(R.color.pill_color);
//                                chip.setChipBackgroundColor(getResources().getColorStateList(R.color.pill_color));
//                                chip.setTextColor(getResources().getColorStateList(R.color.text));
//                                chip.setChipStrokeWidth(4);
//                                chip.setClickable(false);
//                                chipGroup.addView(chip);

                                chip.setChipStrokeColorResource(R.color.pill_color);
                                chip.setChipBackgroundColor(getResources().getColorStateList(R.color.chip_background_color));
                                chip.setTextColor(getResources().getColorStateList(R.color.chip_text_color));
                                chip.setChipStrokeWidth(4);
                                chip.setClickable(false);
                                chipGroup.addView(chip);

                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Skill>> call, Throwable t) {
                        Toast.makeText(getActivity(), "Failed To Fetch", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });

                progressBar.setVisibility(View.VISIBLE);

                Call<ProjectPOJO> caller = loginAPI.getProjectP("Bearer " + MainActivity.getIdToken(), id);//new route for this will be made
                Log.i("tag", id);
                caller.enqueue(new Callback<ProjectPOJO>() {
                    @Override
                    public void onResponse(Call<ProjectPOJO> call, Response<ProjectPOJO> response) {
                        Log.i("projectsssssss", String.valueOf(response.body()));
                        projects_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        if (response.body() != null) {
                            ProjectPOJO projectPOJO = response.body();
//                        Log.i("abc", projectPOJO.getTeam().getName().toString());
                            List<IndividualProject> individualProjectsList = projectPOJO.getIndividualProjects();
//                        Log.i("pt_skill", String.valueOf(pt_skills.get(0).getParticipant().getName()));
                            List<TeamProject> teamProjectsList = projectPOJO.getTeams();
                            ProjectAdapterP projectAdapterP = new ProjectAdapterP(getContext(), individualProjectsList, teamProjectsList);
                            projects_recyclerView.setAdapter(projectAdapterP);
                            projectAdapterP.setGetProjectP(individualProjectsList, teamProjectsList);
                            progressBar.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onFailure(Call<ProjectPOJO> call, Throwable t) {
                        Log.i("error", t.getMessage());
                        Toast.makeText(getActivity(), "Failed To Fetch", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });

            }

            @Override
            public void onFailure(Call<GetParticipantPOJO> call, Throwable t) {
                Log.i("error", t.getMessage());
                Toast.makeText(getActivity(), "Failed To Fetch", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });


    }


    public void initialise() {

        invite = getView().findViewById(R.id.invite_button);
        projects_recyclerView = getView().findViewById(R.id.projects_recyclerView_P);
        chipGroup = getView().findViewById(R.id.chipGroup112);
        profile_pic = getView().findViewById(R.id.profile_pic_P);
        name_PV = getView().findViewById(R.id.name_PV);
        username_PV = getView().findViewById(R.id.username_PV);
        email_PV = getView().findViewById(R.id.email_PV);
        college_PV = getView().findViewById(R.id.college_PV);
        yog_PV = getView().findViewById(R.id.year_of_graduation_PV);
        bio_PV = getView().findViewById(R.id.bio_PV);
        github_PV = getView().findViewById(R.id.github_PV);
        linkedIn_PV = getView().findViewById(R.id.linkedIn_PV);
        personal_website_PV = getView().findViewById(R.id.personal_website_PV);
        progressBar = getView().findViewById(R.id.progressBarP);
    }
}