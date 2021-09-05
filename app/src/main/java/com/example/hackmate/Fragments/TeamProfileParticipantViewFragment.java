package com.example.hackmate.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.Adapters.MemberAdapter;
import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.JoinTeamPOJO;
import com.example.hackmate.POJOClasses.Member;
import com.example.hackmate.POJOClasses.POST.PatchTeamDetails;
import com.example.hackmate.POJOClasses.ProjectPOJO;
import com.example.hackmate.POJOClasses.PtSkill;
import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamProfileParticipantViewFragment extends Fragment {
    private static final String TAG = "TeamProfileParticipantV";
    Button requestJoin;
    int GET_NAV_CODE = 0, invited;
    RecyclerView participants_recyclerView, projects_recyclerView;
    TextView team_name, hack_names, project_name, project_description, project_team_name, project_link1, project_link2,
            project_link3;
    String id, name = "", admin_id;
    PatchTeamDetails patchTeamDetails;
    JoinTeamPOJO joinTeamPOJO;
    CardView cardView;
    ProgressBar progressBar;
    private loginAPI loginAPI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_profile_participant_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            GET_NAV_CODE = bundle.getInt("Key", 0);
            id = bundle.getString("teamId", id);
            name = bundle.getString("hackName", "");
            invited = bundle.getInt("Invited",0);
        }

        initialise();
        cardView.setVisibility(View.GONE);
        if(invited==1)
            requestJoin.setVisibility(View.GONE);

        participants_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loginAPI = RetrofitInstance.getRetrofitInstance().create(loginAPI.class);

        Call<JoinTeamPOJO> call = loginAPI.getTeam("Bearer " + MainActivity.getIdToken(), id);//will get id from previous fragment
        call.enqueue(new Callback<JoinTeamPOJO>() {
            @Override
            public void onResponse(Call<JoinTeamPOJO> call, Response<JoinTeamPOJO> response) {
                if (response.body() != null) {
                    joinTeamPOJO = response.body();
                    Log.i(TAG, "onResponse =>" + joinTeamPOJO.getTeam().getName());
                    team_name.setText(joinTeamPOJO.getTeam().getName());

                    hack_names.setText(name);
                    if (joinTeamPOJO.getTeam().getProject_name() != null) {
                        cardView.setVisibility(View.VISIBLE);
                        project_name.setText(joinTeamPOJO.getTeam().getProject_name());
                        project_team_name.setText(joinTeamPOJO.getTeam().getName());
                    } else
                        project_name.setText("");
                    project_team_name.setText("");
                    if (joinTeamPOJO.getTeam().getProject_description() != null)
                        project_description.setText(joinTeamPOJO.getTeam().getProject_description());
                    else
                        project_description.setText("");
                    if (joinTeamPOJO.getTeam().getProject_name() != null)
                        project_link1.setText(joinTeamPOJO.getTeam().getCode());
                    else
                        project_link1.setText("");
                    if (joinTeamPOJO.getTeam().getProject_name() != null)
                        project_link2.setText(joinTeamPOJO.getTeam().getDemonstration());
                    else
                        project_link2.setText("");
                    if (joinTeamPOJO.getTeam().getProject_name() != null)
                        project_link3.setText(joinTeamPOJO.getTeam().getDesign());
                    else
                        project_link3.setText("");

                    List<Member> memberList = joinTeamPOJO.getTeam().getMembers();
                    Log.i(TAG,"memberList"+ memberList);
                    Log.i(TAG,"admin_id"+ joinTeamPOJO.getTeam().getAdmin_id());

                    for (int i = 0; i < memberList.size(); i++) {
                        if (memberList.get(i).uid.equals(joinTeamPOJO.getTeam().getAdmin_id())) {
                            admin_id = memberList.get(i).getUid();
                            Log.i(TAG, "uid" + memberList.get(i).getUid());
                        }
                    }

                    List<PtSkill> pt_skills = joinTeamPOJO.getPt_skills();
                    Log.i(TAG, "pt_skill" + pt_skills.get(0).getParticipant().getName());
                    MemberAdapter memberAdapter = new MemberAdapter(getContext(), pt_skills);
                    participants_recyclerView.setAdapter(memberAdapter);
                    memberAdapter.setJoinTeam(pt_skills, admin_id);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<JoinTeamPOJO> call, Throwable t) {
                Log.i(TAG, t.getMessage());
                Toast.makeText(getActivity(), "Failed To Fetch", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        Log.i(TAG, "tag44");
        Call<ProjectPOJO> caller = loginAPI.getProject("Bearer " + MainActivity.getIdToken());
        caller.enqueue(new Callback<ProjectPOJO>() {
            @Override
            public void onResponse(Call<ProjectPOJO> call, Response<ProjectPOJO> response) {
                Log.i("project_response44", String.valueOf(response.body()));
            }

            @Override
            public void onFailure(Call<ProjectPOJO> call, Throwable t) {
                Log.i("error44", t.getMessage());
            }
        });

        requestJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                patchTeamDetails = new PatchTeamDetails();

                Call<String> call1 = loginAPI.postTeamCode("Bearer " + MainActivity.getIdToken(),
                        joinTeamPOJO.getTeam().get_id());
                Log.i("team id", joinTeamPOJO.getTeam().get_id());
                call1.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.i(TAG, "onClick: " + response.code());
                        Log.i(TAG, "onClick: " + response.body());
                        if (response.code() == 409) {
                            Toast.makeText(getActivity(), "Request has already been sent!", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 201) {
                            Toast.makeText(getActivity(), "Request Sent !!", Toast.LENGTH_SHORT).show();
                            requestJoin.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.ic_button_border_bg));
                            requestJoin.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e(TAG, "onFailure: ", t);
                    }
                });

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (GET_NAV_CODE != 1) {
            BottomNavigationView bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);
            bottomNavigation.setVisibility(View.VISIBLE);
        }
    }

    public void initialise() {
        requestJoin = getView().findViewById(R.id.requestJoinTeam);
        participants_recyclerView = getView().findViewById(R.id.participants_recyclerView);
        team_name = getView().findViewById(R.id.team_name);
        hack_names = getView().findViewById(R.id.hack_names);
        project_name = getView().findViewById(R.id.project_nameTextView_abc);
        project_description = getView().findViewById(R.id.bio_textView_abc);
        project_team_name = getView().findViewById(R.id.descriptionTextView_abc);
        project_link1 = getView().findViewById(R.id.link1_textView_abc);
        project_link2 = getView().findViewById(R.id.link2_textView_abc);
        project_link3 = getView().findViewById(R.id.link3_textView_abc);
        cardView = getView().findViewById(R.id.cardView8);
        progressBar = getView().findViewById(R.id.progressBarTPPV);
    }
}