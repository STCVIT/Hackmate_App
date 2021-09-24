package com.example.hackmate.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hackmate.Adapters.ProjectAdapterMP;
import com.example.hackmate.Adapters.ProjectAdapterP;
import com.example.hackmate.JSONPlaceholders.API;
import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.GetParticipantPOJO;
import com.example.hackmate.POJOClasses.IndividualProject;
import com.example.hackmate.POJOClasses.ProjectPOJO;
import com.example.hackmate.POJOClasses.Skill;
import com.example.hackmate.POJOClasses.TeamProject;
import com.example.hackmate.POJOClasses.loginPOJO;
import com.example.hackmate.R;
import com.example.hackmate.util.Functions;
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
    int GET_NAV_CODE = 0, check;
    String id = "yash", teamId = "yash";
    private com.example.hackmate.JSONPlaceholders.loginAPI loginAPI;
    TextView name_PV, username_PV, email_PV, college_PV, bio_PV,
            github_PV, linkedIn_PV, personal_website_PV, yog_PV, projects_availability_PV;
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
            check = bundle.getInt("invited",0);
            teamId = bundle.getString("TeamId","yash");
        }

        initialise();
        projects_availability_PV.setVisibility(View.GONE);

        if (GET_NAV_CODE == 1)
            invite.setVisibility(View.GONE);
        else {
            invite.setVisibility(View.VISIBLE);
            if(check==1)
            {
                invite.setText("INVITED");
                invite.setBackground(getResources().getDrawable(R.drawable.ic_button_border_bg));
                invite.setTextColor(getResources().getColor(R.color.green));
            }
        }

        github_PV.setOnClickListener(v -> {
            try {
                Uri uri = Uri.parse(github_PV.getText().toString()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }catch (Exception e) {
                Toast.makeText(getContext(), "Github link not provided !!", Toast.LENGTH_SHORT).show();
            }
        });

        linkedIn_PV.setOnClickListener(v -> {
            try {
                Uri uri = Uri.parse(linkedIn_PV.getText().toString()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }catch (Exception e) {
                Toast.makeText(getContext(), "LinkedIn link not provided !!", Toast.LENGTH_SHORT).show();
            }
        });

        personal_website_PV.setOnClickListener(v -> {
            try {
                Uri uri = Uri.parse(personal_website_PV.getText().toString()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }catch (Exception e) {
                Toast.makeText(getContext(), "Personal Website link not provided !!", Toast.LENGTH_SHORT).show();
            }
        });


        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if(!invite.getText().equals("INVITED"))
                {
                    API api = RetrofitInstance.getRetrofitInstance().create(API.class);

                    Call<Void> calls = api.sendInvitation(MainActivity.getIdToken(), teamId, id);
                    calls.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> calls, Response<Void> response) {
                            if (response.isSuccessful() && response.code() == 201) {
                                invite.setText("INVITED");
                                invite.setBackground(getResources().getDrawable(R.drawable.ic_button_border_bg));
                                invite.setTextColor(getResources().getColor(R.color.green));
                                Toast.makeText(getContext(), "Invite Sent!!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> calls, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
                else {
                    Toast.makeText(getContext(), "Already Invited !!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        loginAPI = RetrofitInstance.getRetrofitInstance().create(loginAPI.class);


        Call<GetParticipantPOJO> call = loginAPI.getParticipantByID("Bearer " + MainActivity.getIdToken(), id);//replace with participant id getting from previous fragment 60f2c642c28f930015dc3de3
        call.enqueue(new Callback<GetParticipantPOJO>() {
            @Override
            public void onResponse(Call<GetParticipantPOJO> call, Response<GetParticipantPOJO> response) {

                Glide.with(getContext()).
                        load(response.body().participant.getPhoto()).
                        placeholder(R.drawable.download).
                        into(profile_pic);

                Log.i("response22", String.valueOf(response.body().participant.get_id()));
                name_PV.setText(response.body().participant.getName());
                username_PV.setText(response.body().participant.getUsername());
                email_PV.setText(response.body().participant.getEmail());
                college_PV.setText(response.body().participant.getCollege());
                yog_PV.setText(String.valueOf(response.body().participant.getGraduation_year()));
                bio_PV.setText(response.body().participant.getBio());
                github_PV.setText(Html.fromHtml("<u>" + response.body().participant.getGithub() + "</u>"));
                linkedIn_PV.setText(Html.fromHtml("<u>" + response.body().participant.getLinkedIn() + "</u>"));
                id = String.valueOf(response.body().participant.get_id());
                if (response.body().participant.getWebsite() != null) {
                    personal_website_PV.setText(Html.fromHtml("<u>" + response.body().participant.getWebsite() + "</u>"));
                }

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
                                } else if (skillList.get(i).getSkill().equals("blockchain")) {
                                    chip.setText("Blockchain");
                                } else if (skillList.get(i).getSkill().equals("cybersecurity")) {
                                    chip.setText("Cyber Security");
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
                        } else{
                            projects_availability_PV.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onFailure(Call<ProjectPOJO> call, Throwable t) {
                        Log.i("error", t.getMessage());
                        Toast.makeText(getActivity(), "Failed To Fetch", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        projects_availability_PV.setVisibility(View.VISIBLE);
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
        projects_availability_PV = getView().findViewById(R.id.projects_availability_PV);
    }
}