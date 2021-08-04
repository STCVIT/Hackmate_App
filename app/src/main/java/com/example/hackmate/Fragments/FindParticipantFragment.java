package com.example.hackmate.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.hackmate.Adapters.InviteAdapter;
import com.example.hackmate.Adapters.JoinAdapter;
import com.example.hackmate.JSONPlaceholders.API;
import com.example.hackmate.POJOClasses.FindParticipant.invitePOJO;
import com.example.hackmate.POJOClasses.FindPtByName.FindPtByNamePOJO;
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

import static android.content.Context.INPUT_METHOD_SERVICE;

public class FindParticipantFragment extends Fragment {

    private LinearLayout filter;
    private ImageView downArrow, upArrow;
    private ChipGroup chips;
    private API api;
    private String[] names;
    private String[][] domains;
    private RecyclerView inviteProfile;
    private EditText findPtByName;
    private String idToken;
    private int count=0,page=1;

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
        findPtByName = view.findViewById(R.id.findPtByName);

        chips = view.findViewById(R.id.chips);

        retrofit();
        getIDToken();

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upArrow.getVisibility() == View.GONE) {
                    chips.setVisibility(View.VISIBLE);
                    downArrow.setVisibility(View.GONE);
                    upArrow.setVisibility(View.VISIBLE);
                } else {
                    chips.setVisibility(View.GONE);
                    upArrow.setVisibility(View.GONE);
                    downArrow.setVisibility(View.VISIBLE);
                }
            }
        });

        findPtByName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == event.KEYCODE_ENTER)) {
                    try {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    searchPtByName(findPtByName.getText().toString());

                    return true;
                }
                return false;
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

    public void getIDToken() {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            idToken = "Bearer " + task.getResult().getToken();
                            sendInvite();
                        }
                    }
                });
    }

    public void sendInvite() {
        Call<invitePOJO> call = api.inviteParticipants(idToken);
        call.enqueue(new Callback<invitePOJO>() {
            @Override
            public void onResponse(Call<invitePOJO> call, Response<invitePOJO> response) {
                names = new String[12];
                domains = new String[12][];

                String skills[];

                for (int i = 0; i < 12; i++) {
                    names[i] = response.body().getFinal().get(i).getParticipant().getName();
                    int length_skills = response.body().getFinal().get(i).getSkills().size();
                    if (length_skills >= 2)
                        skills = new String[2];
                    else
                        skills = new String[length_skills];
                    if (length_skills > 0) {
                        skills[0] = response.body().getFinal().get(i).getSkills().get(0).getSkill();
                        if (length_skills > 1)
                            skills[1] = "+" + String.valueOf(length_skills - 1) + " More";
                    }
                    domains[i] = skills;
                }

                inviteProfile.setAdapter(new InviteAdapter(names, domains));
            }

            @Override
            public void onFailure(Call<invitePOJO> call, Throwable t) {

            }
        });
    }

    public void searchPtByName(String name) {
        Call<FindPtByNamePOJO> call = api.getPtByName(idToken, name, 1);
        call.enqueue(new Callback<FindPtByNamePOJO>() {
            @Override
            public void onResponse(Call<FindPtByNamePOJO> call, Response<FindPtByNamePOJO> response) {
                if (response.isSuccessful() && response.message()!=null) {
                    int length_team = response.body().getLength();
                    if (length_team > 8)
                        length_team = 8;

                    names = new String[length_team];
                    domains = new String[length_team][];
                    String skills[];

                    for (int i = 0; i < length_team; i++) {
                        names[i] = response.body().getFinal().get(i).getParticipant().getName();
                        int length_skills = response.body().getFinal().get(i).getSkills().size();

                        if (length_skills >= 2)
                            skills = new String[2];
                        else
                            skills = new String[length_skills];

                        if (length_skills > 0) {
                            skills[0] = response.body().getFinal().get(i).getSkills().get(0).getSkill();
                            if (length_skills > 1)
                                skills[1] = "+" + String.valueOf(length_skills - 1) + " More";
                        }


                        domains[i] = skills;
                    }
                    inviteProfile.setAdapter(new InviteAdapter(names, domains));
                }

            }

            @Override
            public void onFailure(Call<FindPtByNamePOJO> call, Throwable t) {

            }
        });
    }
}
