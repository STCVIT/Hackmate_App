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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.hackmate.Adapters.InviteAdapter;
import com.example.hackmate.JSONPlaceholders.API;
import com.example.hackmate.POJOClasses.FindParticipant.invitePOJO;
import com.example.hackmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindParticipantFragment extends Fragment {

    private LinearLayout filter;
    private ImageView downArrow, upArrow;
    private ChipGroup chips;
    private API api;
    private String[] names;
    private String[][] domains;
    private RecyclerView inviteProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_participant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        filter = view.findViewById(R.id.domainFilterTeammate);
        downArrow = view.findViewById(R.id.downArrow);
        upArrow = view.findViewById(R.id.upArrow);

        chips = view.findViewById(R.id.chips);

        retrofit();
        getIDToken();

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(upArrow.getVisibility() == View.GONE) {
                    chips.setVisibility(View.VISIBLE);
                    downArrow.setVisibility(View.GONE);
                    upArrow.setVisibility(View.VISIBLE);
                }
                else
                {
                    chips.setVisibility(View.GONE);
                    upArrow.setVisibility(View.GONE);
                    downArrow.setVisibility(View.VISIBLE);
                }
            }
        });

        inviteProfile = view.findViewById(R.id.inviteList);
        inviteProfile.setLayoutManager(new LinearLayoutManager(getContext()));

    }
    public void retrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hackportalbackend.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API.class);
    }

    public void getIDToken()
    {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if(task.isSuccessful()) {
                            String idToken = "Bearer " + task.getResult().getToken();
                            sendInvite(idToken);
                        }
                    }
                });
    }

    public void sendInvite(String idToken)
    {
        Call<invitePOJO> call = api.inviteParticipants(idToken);
        call.enqueue(new Callback<invitePOJO>() {
            @Override
            public void onResponse(Call<invitePOJO> call, Response<invitePOJO> response) {
                names = new String[12];
                domains = new String[12][];

                String skills[];

                for(int i=0;i<12;i++)
                {
                    names[i] = response.body().getFinal().get(i).getPt().getName();
                    int length_skills = response.body().getFinal().get(i).getSkills().size();
                    if(length_skills>=2)
                        skills = new String[2];
                    else
                        skills = new String[length_skills];
                    if(length_skills>0)
                    {
                        skills[0] = response.body().getFinal().get(i).getSkills().get(0).getSkill();
                        if(length_skills>1)
                            skills[1] = "+" + String.valueOf(length_skills-1) +" More";
                    }
                    domains[i] = skills;
                }

                inviteProfile.setAdapter(new InviteAdapter(names,domains));
            }

            @Override
            public void onFailure(Call<invitePOJO> call, Throwable t) {

            }
        });
    }
}