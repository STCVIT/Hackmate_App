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
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackmate.Adapters.MemberAdapter;
import com.example.hackmate.Adapters.ProjectAdapterRT;
import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.Models.ProjectModel;
import com.example.hackmate.POJOClasses.JoinTeamPOJO;
import com.example.hackmate.POJOClasses.ProjectPOJO;
import com.example.hackmate.POJOClasses.PtSkill;
import com.example.hackmate.POJOClasses.Team;
import com.example.hackmate.R;
import com.example.hackmate.Models.teamMember_Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class TeamProfileParticipantViewFragment extends Fragment {

    Button requestJoin;
    int GET_NAV_CODE = 0;
    RecyclerView participants_recyclerView, projects_recyclerView;
    TextView team_name, hack_names;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Retrofit retrofit;
    private loginAPI loginAPI;
    String idToken, id;

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
            id = bundle.getString("id", id);
        }

        initialise();

        requestJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Request Sent !!", Toast.LENGTH_SHORT).show();
                requestJoin.setBackground(getResources().getDrawable(R.drawable.ic_button_border_bg));
                requestJoin.setTextColor(getResources().getColor(R.color.green));
            }
        });

        participants_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        teamMember_Model.TeamMemberModel model = new teamMember_Model.TeamMemberModel("1.", "yash", "yash@gmail.com", "leader", R.drawable.bhavik, true);
//        teamMember_Model.TeamMemberModel model1 = new teamMember_Model.TeamMemberModel("2.", "bhavik", "bhavik@gmail.com", "", R.drawable.bhavik, true);
//        ArrayList arrayList = new ArrayList<PtSkill>();
//        arrayList.add(model);
//        arrayList.add(model1);
//        arrayList.add(model);
//        arrayList.add(model1);


        projects_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ProjectModel model2 = new ProjectModel("Hackmate",
                "Project for team building for hackathons",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Tristique mauris, " +
                        "nec vitae cursus phasellus a proin et. Sit in velit duis iaculis est. " +
                        "At odio sociis venenatis ut commodo. Aliquet eget morbi faucibus nisl " +
                        "nec quis suscipit ut. Mus vestibulum risus at ante lorem volutpat. " +
                        "In vitae vitae, tortor a ipsum ipsum. Ipsum cras eu odio natoque blandit commodo aliquam.",
                "abc@gmail.com", "abc@gmail.com", "abc@gmail.com");
        ArrayList arrayList1 = new ArrayList<ProjectModel>();
        arrayList1.add(model2);
        arrayList1.add(model2);
        projects_recyclerView.setAdapter(new ProjectAdapterRT(getContext(), arrayList1));

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //.addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(loggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://hackportalbackend.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        mAuth.getCurrentUser().getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            idToken = task.getResult().getToken();
                            Log.i("xx", idToken);

                            loginAPI = retrofit.create(com.example.hackmate.JSONPlaceholders.loginAPI.class);

                            Call<JoinTeamPOJO> call = loginAPI.getTeam("Bearer " + idToken, "60f92e19366cd000159bc89c");//will get id from previous fragment
                            call.enqueue(new Callback<JoinTeamPOJO>() {
                                @Override
                                public void onResponse(Call<JoinTeamPOJO> call, Response<JoinTeamPOJO> response) {
                                    JoinTeamPOJO joinTeamPOJO = response.body();
                                    Log.i("abc" , joinTeamPOJO.getTeam().getName().toString());
                                    List<PtSkill> pt_skills = joinTeamPOJO.getPt_skills();
                                    Log.i("pt_skill" , String.valueOf(pt_skills.get(0).getParticipant().getName()));
                                    MemberAdapter memberAdapter = new MemberAdapter(getContext(), pt_skills);
                                    participants_recyclerView.setAdapter(memberAdapter);
                                    memberAdapter.setJoinTeam(pt_skills);

//                                    Log.i("response33", String.valueOf());
                                }

                                @Override
                                public void onFailure(Call<JoinTeamPOJO> call, Throwable t) {
                                    Log.i("error33", t.getMessage());
                                }
                            });

                            Call<ProjectPOJO> caller = loginAPI.getProject("Bearer " + idToken, "60f92e19366cd000159bc89c");//will get id from previous fragment
                            Log.i("tag44", "tag44");
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


                        }
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
        projects_recyclerView = getView().findViewById(R.id.projects_recyclerView_RT);
        team_name = getView().findViewById(R.id.team_name);
        hack_names = getView().findViewById(R.id.hack_names);
    }
}