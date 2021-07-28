package com.example.hackmate.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackmate.Adapters.JoinAdapter;
import com.example.hackmate.JSONPlaceholders.API;
import com.example.hackmate.POJOClasses.GetTeams.getTeamsPOJO;
import com.example.hackmate.POJOClasses.JoinHackTeams.JoinHackTeamPOJO;
import com.example.hackmate.POJOClasses.SearchAndJoin.SearchAndJoinPOJO;
import com.example.hackmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
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

public class FindTeamsFragment extends Fragment {

    private LinearLayout filter;
    private ImageView downArrow, upArrow;
    private ChipGroup chips;
    private TextView joinUsingCode;
    private CardView appBar;

    int GET_NAV_CODE = 0;
    private API api;
    private String[] team_name;
    private RecyclerView joinTeamList;
    public String hackId;
    String[][] domains;
    private EditText searchTeam;
    Chip domain1,domain2,domain3,domain4,domain5,domain6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_teams, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            GET_NAV_CODE = bundle.getInt("Key", 0);
        }

        appBar = view.findViewById(R.id.appBarJoin);
        filter = view.findViewById(R.id.domainFilterTeammate);
        downArrow = view.findViewById(R.id.downArrow);
        upArrow = view.findViewById(R.id.upArrow);

        domain1 = view.findViewById(R.id.chipDomain1);
        domain2 = view.findViewById(R.id.chipDomain2);
        domain3 = view.findViewById(R.id.chipDomain3);
        domain4 = view.findViewById(R.id.chipDomain4);
        domain5 = view.findViewById(R.id.chipDomain5);
        domain6 = view.findViewById(R.id.chipDomain6);

        searchTeam = view.findViewById(R.id.searchTeamJoin);
        chips = view.findViewById(R.id.chips);
        joinUsingCode = view.findViewById(R.id.joinTeamCode);

        retrofit();

        if(GET_NAV_CODE==1) {
            appBar.setVisibility(View.VISIBLE);
            hackId = bundle.getString("HackId","");
        }
        else {
            appBar.setVisibility(View.GONE);
        }
        getIDToken();

        searchTeam.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode==KeyEvent.ACTION_DOWN)
                {
                    //searchTeamToJoin(searchTeam.getText().toString());
                }
                return false;
            }
        });

        joinUsingCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogBox();
            }
        });

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

        joinTeamList = view.findViewById(R.id.joinList);
        joinTeamList.setLayoutManager(new LinearLayoutManager(getContext()));
        //team_name = new String[] {"name"};
        //joinTeamList.setAdapter(new JoinAdapter(team_name,domains,GET_NAV_CODE));

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
                            if(GET_NAV_CODE!=1)
                                getNullTeamsToJoin(idToken);
                            else
                                getHackTeamsToJoin(idToken);
                        }
                    }
                });
    }

    public void searchTeamToJoin(String name)
    {
        String idToken="";
        Call<SearchAndJoinPOJO> call = api.searchTeam(idToken,name);
        call.enqueue(new Callback<SearchAndJoinPOJO>() {
            @Override
            public void onResponse(Call<SearchAndJoinPOJO> call, Response<SearchAndJoinPOJO> response) {
                if(response.isSuccessful())
                {

                }
            }

            @Override
            public void onFailure(Call<SearchAndJoinPOJO> call, Throwable t) {

            }
        });
    }

    public void getHackTeamsToJoin(String idToken)
    {
        Call<JoinHackTeamPOJO> call = api.getHackTeams(idToken,hackId);
        call.enqueue(new Callback<JoinHackTeamPOJO>() {
            @Override
            public void onResponse(Call<JoinHackTeamPOJO> call, Response<JoinHackTeamPOJO> response) {
                if(response.isSuccessful()) {
                    int length_team = response.body().getLength();
                    if (length_team > 8)
                        length_team = 8;

                    team_name = new String[length_team];
                    domains = new String[length_team][];
                    String skills[];

                    for (int i = 0; i < length_team; i++) {
                        team_name[i] = response.body().getFinal().get(i).getTeam().getName();
                        int length_skills = response.body().getFinal().get(i).getSkills().size();
                        skills = new String[length_skills];
                        for (int j = 0; j < length_skills; j++)
                            skills[j] = response.body().getFinal().get(i).getSkills().get(j).getSkill();

                        domains[i] = skills;
                    }
                    joinTeamList.setAdapter(new JoinAdapter(team_name, domains, GET_NAV_CODE));
                }
                else if(response.code()==404)
                    Toast.makeText(getContext(), "No Team to Join !!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JoinHackTeamPOJO> call, Throwable t) {

            }
        });
    }

    public void getNullTeamsToJoin(String idToken)
    {
        Call<getTeamsPOJO> call = api.getAllTeams(idToken);
        call.enqueue(new Callback<getTeamsPOJO>() {
            @Override
            public void onResponse(Call<getTeamsPOJO> call, Response<getTeamsPOJO> response) {
                if(response.isSuccessful()) {
                    int length_team = response.body().getLength();
                    if (length_team > 8)
                        length_team = 8;

                    team_name = new String[length_team];
                    domains = new String[length_team][];
                    String skills[];

                    for (int i = 0; i < (length_team); i++) {
                        team_name[i] = response.body().getFinal().get(i).getTeam().getName();
                        int length_skills = response.body().getFinal().get(i).getSkills().size();
                        skills = new String[length_skills];
                        for (int j = 0; j < length_skills; j++) {
                            skills[j] = response.body().getFinal().get(i).getSkills().get(j).getSkill();
                        }
                        domains[i] = skills;
                    }

                    joinTeamList.setAdapter(new JoinAdapter(team_name, domains, GET_NAV_CODE));
                }
                else if(response.code()==404)
                    Toast.makeText(getActivity(), "No Team to Join !!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<getTeamsPOJO> call, Throwable t) {
                Log.i("apple",t.getMessage());
            }
        });
    }

    public void AlertDialogBox()
    {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.custom_join_dialog_box,null);

        AppCompatButton joinTeam = (AppCompatButton) mView.findViewById(R.id.joinTeam);
        EditText teamCode = mView.findViewById(R.id.teamCode);
        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

        joinTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}