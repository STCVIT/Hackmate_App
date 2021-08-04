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

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackmate.Adapters.JoinAdapter;
import com.example.hackmate.JSONPlaceholders.API;
import com.example.hackmate.POJOClasses.POST.Code;
import com.example.hackmate.POJOClasses.JoinHackTeams.JoinHackTeamPOJO;
import com.example.hackmate.POJOClasses.SearchAndJoinHackPOJO;
import com.example.hackmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class FindTeamsFragment extends Fragment {

    private LinearLayout filter;
    private ImageView downArrow, upArrow;
    private ChipGroup chips;
    private TextView joinUsingCode, appBarName;
    private CardView appBar;

    int GET_NAV_CODE = 0;
    private API api;
    private String[] team_name;
    private RecyclerView joinTeamList;
    public String hackId,hackName;
    String[][] domains;
    private EditText searchTeam;
    private String idToken;
    private Retrofit retrofit;
    private ProgressBar progressBar;
    private Chip frontend, backend, ml, ui_ux, management, appdev, cybersecurity;

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
        appBarName = view.findViewById(R.id.appBarName);

        frontend = view.findViewById(R.id.chipDomain1);
        backend = view.findViewById(R.id.chipDomain2);
        ml = view.findViewById(R.id.chipDomain3);
        ui_ux = view.findViewById(R.id.chipDomain4);
        management = view.findViewById(R.id.chipDomain5);
        appdev = view.findViewById(R.id.chipDomain6);
        cybersecurity = view.findViewById(R.id.chipDomain7);

        searchTeam = view.findViewById(R.id.searchTeamJoin);
        chips = view.findViewById(R.id.chips);
        joinUsingCode = view.findViewById(R.id.joinTeamCode);
        progressBar = view.findViewById(R.id.progressBarLoad);
        joinTeamList = view.findViewById(R.id.joinList);

        if (GET_NAV_CODE == 1) {
            appBar.setVisibility(View.VISIBLE);
            hackId = bundle.getString("HackId", "");
            hackName = bundle.getString("HackName","");
            appBarName.setText(hackName+" Teams");
        } else {
            appBar.setVisibility(View.GONE);
            hackId = "null";
        }

        joinTeamList.setLayoutManager(new LinearLayoutManager(getContext()));
        joinTeamList.setAdapter(new JoinAdapter(team_name,domains,GET_NAV_CODE,hackName));

        retrofit();
        getIDToken();
        chipOnClick();

        searchTeam.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == event.KEYCODE_ENTER)) {
                    try {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    if (GET_NAV_CODE != 1)
                        searchHackTeamToJoin(searchTeam.getText().toString());
                    else
                        searchHackTeamToJoin(searchTeam.getText().toString());

                    return true;
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
    }

    public void chipOnClick() {
        chips.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                joinTeamList.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                if (frontend.isChecked() || backend.isChecked() || ml.isChecked() || ui_ux.isChecked() ||
                        management.isChecked() || appdev.isChecked() || cybersecurity.isChecked()) {
                    if (frontend.isChecked())
                        getDomainHackTeams("frontend");
                    else if (backend.isChecked())
                        getDomainHackTeams("backend");
                    else if (ml.isChecked())
                        getDomainHackTeams("ml");
                    else if (ui_ux.isChecked())
                        getDomainHackTeams("ui/ux");
                    else if (management.isChecked())
                        getDomainHackTeams("management");
                    else if (appdev.isChecked())
                        getDomainHackTeams("appdev");
                    else
                        getDomainHackTeams("cybersecurity");
                } else
                    getHackTeamsToJoin();
            }
        });
    }

    public void getDomainHackTeams(String domain) {
        Call<JoinHackTeamPOJO> call = api.domainTeamHack(idToken, hackId, 1, domain);
        call.enqueue(new Callback<JoinHackTeamPOJO>() {
            @Override
            public void onResponse(Call<JoinHackTeamPOJO> call, Response<JoinHackTeamPOJO> response) {
                if (response.isSuccessful()) {
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
                    progressBar.setVisibility(View.GONE);
                    joinTeamList.setVisibility(View.VISIBLE);
                    joinTeamList.setAdapter(new JoinAdapter(team_name, domains, GET_NAV_CODE, hackName));
                } else if (response.code() == 404) {
                    progressBar.setVisibility(View.GONE);
                    joinTeamList.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "No Hack Team to Join !!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JoinHackTeamPOJO> call, Throwable t) {

            }
        });
    }

    public void retrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://hackportalbackend.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getIDToken() {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            idToken = "Bearer " + task.getResult().getToken();
                            api = retrofit.create(API.class);
                            if (!(frontend.isChecked() || backend.isChecked() || ml.isChecked() || ui_ux.isChecked() ||
                                    management.isChecked() || appdev.isChecked() || cybersecurity.isChecked()))
                                getHackTeamsToJoin();
                        }
                    }
                });
    }

    public void searchHackTeamToJoin(String name) {
        Call<List<SearchAndJoinHackPOJO>> call = api.searchTeamHack(idToken, hackId, name);
        call.enqueue(new Callback<List<SearchAndJoinHackPOJO>>() {
            @Override
            public void onResponse(Call<List<SearchAndJoinHackPOJO>> call, Response<List<SearchAndJoinHackPOJO>> response) {
                if (response.isSuccessful()) {
                    int length_team = response.body().size();
                    if (length_team > 8)
                        length_team = 8;

                    team_name = new String[length_team];
                    domains = new String[length_team][];
                    String skills[];

                    for (int i = 0; i < length_team; i++) {
                        team_name[i] = response.body().get(i).getTeam().getName();
                        int length_skills = response.body().get(i).getSkills().size();
                        ;
                        skills = new String[length_skills];
                        for (int j = 0; j < length_skills; j++)
                            skills[j] = response.body().get(i).getSkills().get(j).getSkill();

                        domains[i] = skills;
                    }
                    progressBar.setVisibility(View.GONE);
                    joinTeamList.setVisibility(View.VISIBLE);
                    joinTeamList.setAdapter(new JoinAdapter(team_name, domains, GET_NAV_CODE, hackName));
                } else if (response.code() == 404)
                    Toast.makeText(getContext(), "No team with such name exists ...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<SearchAndJoinHackPOJO>> call, Throwable t) {

            }
        });
    }

    public void getHackTeamsToJoin() {
        Call<JoinHackTeamPOJO> call = api.getHackTeams(idToken, hackId);
        call.enqueue(new Callback<JoinHackTeamPOJO>() {
            @Override
            public void onResponse(Call<JoinHackTeamPOJO> call, Response<JoinHackTeamPOJO> response) {
                if (response.isSuccessful()) {
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
                    progressBar.setVisibility(View.GONE);
                    joinTeamList.setVisibility(View.VISIBLE);
                    joinTeamList.setAdapter(new JoinAdapter(team_name, domains, GET_NAV_CODE, hackName));
                } else if (response.code() == 404) {
                    progressBar.setVisibility(View.GONE);
                    joinTeamList.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "No Hack Team to Join !!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JoinHackTeamPOJO> call, Throwable t) {

            }
        });
    }

    public void AlertDialogBox() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.custom_join_dialog_box, null);

        AppCompatButton joinTeam = (AppCompatButton) mView.findViewById(R.id.joinTeam);
        EditText teamCode = mView.findViewById(R.id.teamCode);
        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

        joinTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Code code = new Code();
                code.setCode(teamCode.getText().toString().trim());

                Call<Response<Void>> call = api.getTeam(idToken,code,hackId);
                call.enqueue(new Callback<Response<Void>>() {
                    @Override
                    public void onResponse(Call<Response<Void>> call, Response<Response<Void>> response) {
                        String message;
                        if(response.isSuccessful())
                        {
                            if(response.code()==201)
                                message = "You have been successfully added to the team";
                            else if(response.code()==200)
                                message = "Duplicate entry in team is not allowed";
                            else
                                message = "Invalid code...Please check and try again";
                        }
                        else
                            message = "Invalid code...Please check and try again";

                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Response<Void>> call, Throwable t) {
                        Toast.makeText(getContext(), "Try again later !!", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.dismiss();
            }
        });
    }
}