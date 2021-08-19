package com.example.hackmate.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hackmate.JSONPlaceholders.API;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.AddSkill;
import com.example.hackmate.POJOClasses.CreateTeamResponse;
import com.example.hackmate.POJOClasses.POST.CreateTeam;
import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TeamCreationFormFragment extends Fragment {

    private Button createTeam;
    private EditText teamName;
    private int GET_NAV_CODE = 0;
    private Retrofit retrofit;
    private String idToken, id, hackId;
    private API api;
    private ProgressBar progressBar;
    private AddSkill addSkill;
    private Chip frontend, backend, ml, ui, management, appdev, cyber,blockchain;
    private ArrayList<String> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_creation_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createTeam = view.findViewById(R.id.createTeamButtonFilter);
        teamName = view.findViewById(R.id.teamName);
        progressBar = view.findViewById(R.id.progress);
        frontend = view.findViewById(R.id.chipDomain1);
        backend = view.findViewById(R.id.chipDomain2);
        ml = view.findViewById(R.id.chipDomain3);
        ui = view.findViewById(R.id.chipDomain4);
        management = view.findViewById(R.id.chipDomain5);
        appdev = view.findViewById(R.id.chipDomain6);
        cyber = view.findViewById(R.id.chipDomain7);
        blockchain = view.findViewById(R.id.chipDomain8);

        Bundle bundle = this.getArguments();
        if (bundle != null)
            GET_NAV_CODE = bundle.getInt("Keys", 0);

        if(GET_NAV_CODE==1)
            hackId = bundle.getString("HackId","");
        else
            hackId = "null";

        Log.i("HackID",hackId);

        idToken = MainActivity.getIdToken();
        api = RetrofitInstance.getRetrofitInstance().create(API.class);


        createTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teamName.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter the team name!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                list = new ArrayList<String>();
                if (frontend.isChecked() || backend.isChecked() || ml.isChecked() || ui.isChecked() ||
                        management.isChecked() || appdev.isChecked() || cyber.isChecked() || blockchain.isChecked()) {
                    if (frontend.isChecked())
                        list.add("frontend");
                    if (backend.isChecked())
                        list.add("backend");
                    if (cyber.isChecked())
                        list.add("cybersecurity");
                    if (ml.isChecked())
                        list.add("ml");
                    if (ui.isChecked())
                        list.add("ui/ux");
                    if (management.isChecked())
                        list.add("management");
                    if (appdev.isChecked())
                        list.add("appdev");
                    if (blockchain.isChecked())
                        list.add("blockchain");
                }
                else {
                    Toast.makeText(getContext(), "Please choose the skill required", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                CreateTeam();
            }
        });
    }

    public void CreateTeam()
    {
        CreateTeam createTeam = new CreateTeam();
        createTeam.setName(teamName.getText().toString().trim());

        Call<CreateTeamResponse> call = api.createTeam(idToken,createTeam,hackId);
        call.enqueue(new Callback<CreateTeamResponse>() {
            @Override
            public void onResponse(Call<CreateTeamResponse> call, Response<CreateTeamResponse> response) {
                if(response.isSuccessful() && response.code()==201)
                {
                    id = response.body().getId();
                    Log.i("teamId",id);
                    addSkill = new AddSkill();
                    addSkill.setSkills(list);
                    addSkillToTeam();
                }
                else if(response.code()==403){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Already going to the same Hack..", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(), "Error !1", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CreateTeamResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Try Again Later !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addSkillToTeam()
    {
        Call<List<CreateTeamResponse>> responseCall = api.addSkill(idToken,addSkill,id);
        responseCall.enqueue(new Callback<List<CreateTeamResponse>>() {
            @Override
            public void onResponse(Call<List<CreateTeamResponse>> call, Response<List<CreateTeamResponse>> response) {
                progressBar.setVisibility(View.GONE);
                TeamProfileLeaderViewFragment frag = new TeamProfileLeaderViewFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("Key", 1);
                bundle.putInt("Team",1);
                bundle.putString("teamID",id);
                frag.setArguments(bundle);


                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,frag)
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onFailure(Call<List<CreateTeamResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Error in adding skill !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (GET_NAV_CODE == 0) {
            BottomNavigationView bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);
            bottomNavigation.setVisibility(View.VISIBLE);
        }
    }
}