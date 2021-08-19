package com.example.hackmate.Fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackmate.Fragments.FindParticipant.FindParticipantFragment;
import com.example.hackmate.Fragments.HackProfile.HackProfileFragment;
import com.example.hackmate.JSONPlaceholders.JSONPlaceHolderAPI;
import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.MainActivity;

import com.example.hackmate.POJOClasses.JoinTeamPOJO;
import com.example.hackmate.POJOClasses.Kavita.Projects.TeamProject;
import com.example.hackmate.POJOClasses.PtSkill;
import com.example.hackmate.POJOClasses.Kavita.Hacks.hackByIdPOJO;


import com.example.hackmate.R;
import com.example.hackmate.Adapters.teamMember_LeaderAdapter;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class TeamProfileLeaderViewFragment extends Fragment {
    private RecyclerView team_LeaderRV, projectRV;
    // Arraylist for storing data
    //private ArrayList<teamMember_Model> teamModelArrayList;
    private List<PtSkill> teamModelArrayList;
    //private ArrayList<ProjectModel> projectModelArrayList;
    private List<TeamProject> projectModelArrayList;
    ImageView editProject, addProjectIcon;
    TextView addProjectTV, InviteParticpant, TeamName_Leader, Hackname_Leader, teamCode_Leader, deleteTeam, Remove;
    TextView project_nameTextView_Leader, bio_textView_Leader, link1_textView_Leader, link2_textView_Leader, link3_textView_Leader,projectsTitle_Leader,teamMembersTitle_Leader;
    int GET_NAV_CODE = 0,team;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String idToken = "Bearer ";
    public String admin, hackID, id3, teamID, TeamName, name , ProjectIDLeader, displayStatus;
    private List<String> members;
    private CardView cardview3,cardView_Leader,cardView2_Leader,inviteParticipantCard,cardView4_Leader;
    ProgressBar progressBar;
    public boolean isLoading=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_team_profile_leader_view, container, false);

//inflate appbar for this particular fragment
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.particularTeamAppBar_Leader);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return v;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            GET_NAV_CODE = bundle.getInt("Key", 0);
            // ProjectIDLeader = bundle.getString("ProjectId", "");
            //displayStatus = bundle.getString("display", "no");
            team=bundle.getInt("Team",0);
            teamID = bundle.getString("teamID", null);
//            Log.i("teamID",teamID);
            // Log.i("ProjectIDLeader", ProjectIDLeader);
            // Log.i("displayStatus", displayStatus);

        }
        if(team==1){
            Toast.makeText(getContext(), "Team Created !!!", Toast.LENGTH_LONG).show();
        }
        projectsTitle_Leader=view.findViewById(R.id.projectsTitle_Leader);
        teamMembersTitle_Leader=view.findViewById(R.id.teamMembersTitle_Leader);
        cardView_Leader=view.findViewById(R.id.cardView_Leader);
        cardView2_Leader=view.findViewById(R.id.cardView2_Leader);
        inviteParticipantCard=view.findViewById(R.id.inviteParticipantCard);
        cardView4_Leader=view.findViewById(R.id.cardView4_Leader);
        progressBar=view.findViewById(R.id.progressBar2);
        team_LeaderRV = view.findViewById(R.id.rvTeam_Leader);
        editProject=view.findViewById(R.id.editProject_Leader);
        addProjectIcon = view.findViewById(R.id.addProject_Icon);
        addProjectTV = view.findViewById(R.id.addProject_TextView);
        InviteParticpant = view.findViewById(R.id.inviteparticipant_textView);
        TeamName_Leader = view.findViewById(R.id.TeamName_Leader);
        Hackname_Leader = view.findViewById(R.id.HackName_Leader);
        teamCode_Leader = view.findViewById(R.id.teamCode_Leader);
        deleteTeam = view.findViewById(R.id.deleteTeam);
        Remove = view.findViewById(R.id.leaveOption);
        project_nameTextView_Leader = view.findViewById(R.id.project_nameTextView_Leader);
        bio_textView_Leader = view.findViewById(R.id.bio_textView_Leader);
        link1_textView_Leader = view.findViewById(R.id.link1_textView_Leader);
        link2_textView_Leader = view.findViewById(R.id.link2_textView_Leader);
        link3_textView_Leader = view.findViewById(R.id.link3_textView_Leader);
        cardview3 = view.findViewById(R.id.cardView3);
        teamModelArrayList = new ArrayList<>();
        teamMember_LeaderAdapter teamMember_LeaderAdapter = new teamMember_LeaderAdapter(getContext(), teamModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        team_LeaderRV.setLayoutManager(linearLayoutManager);
        team_LeaderRV.setAdapter(teamMember_LeaderAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            teamModelArrayList.sort((o1, o2) -> (o1.getParticipant().get_id().equals(admin) ? "Leader" : "").compareTo((o2.getParticipant().get_id().equals(admin) ? "Leader" : "")));
        }

        addProjectIcon.setOnClickListener(v -> getFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, new AddProjectFragment())
                .addToBackStack(null)
                .commit());
        addProjectTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putString("teamID", teamID);
                AddProjectFragment addProjectFragment = new AddProjectFragment();
                addProjectFragment.setArguments(bundle1);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, addProjectFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        InviteParticpant.setOnClickListener(v -> getFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, new FindParticipantFragment(hackID, teamID, members))
                .addToBackStack(null)
                .commit());
        teamCode_Leader.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Team Code", teamCode_Leader.getText());
            clipboard.setPrimaryClip(clip);

            Toast.makeText(getActivity(), "Team Code copied to clipboard", Toast.LENGTH_LONG).show();
        });

        editProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle2 = new Bundle();
                // bundle2.putString("ProjectID", ProjectIDLeader);
                bundle2.putString("teamID", teamID);
                EditProjectFragment editProjectFragment = new EditProjectFragment();
                editProjectFragment.setArguments(bundle2);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, editProjectFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        idToken = MainActivity.getIdToken();
        Visibility(1);
        JSONPlaceHolderAPI jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);
        loginAPI loginAPI = RetrofitInstance.getRetrofitInstance().create(loginAPI.class);

        Call<JoinTeamPOJO> call = loginAPI.getTeam("Bearer " + MainActivity.getIdToken(), getArguments().getString("teamID"));
        call.enqueue(new Callback<JoinTeamPOJO>() {
            @Override
            public void onResponse(Call<JoinTeamPOJO> call, Response<JoinTeamPOJO> response) {
                if (response.body() != null) {
                    Visibility(0);
                    JoinTeamPOJO joinTeamPOJO = response.body();
                    Log.i("abc", joinTeamPOJO.getTeam().getName());
                    admin = joinTeamPOJO.getTeam().getAdmin_id();
                    teamID = joinTeamPOJO.getTeam().get_id();
                    TeamName_Leader.setText(joinTeamPOJO.getTeam().getName());
                    teamCode_Leader.setText(joinTeamPOJO.getTeam().getTeam_code());

                    hackID=joinTeamPOJO.getTeam().getHack_id();
                    if (hackID == null)
                        hackID = "null";

                    else {
                        Call<hackByIdPOJO> call7 = jsonPlaceHolderAPI.getHackById( MainActivity.getIdToken(), hackID);
                        call7.enqueue(new Callback<hackByIdPOJO>() {
                            @Override
                            public void onResponse(Call<hackByIdPOJO> call7, Response<hackByIdPOJO> response7) {
                                hackByIdPOJO hackByIdPOJO = response7.body();
                                name = hackByIdPOJO.getName();
                                Hackname_Leader.setText(name);
                            }

                            @Override
                            public void onFailure(Call<hackByIdPOJO> call7, Throwable t) {
                                Log.i("HackName", t.getMessage());
                            }
                        });
                    }

                    /**/
                    if (joinTeamPOJO.getTeam().getProject_name() != null) {
                        cardview3.setVisibility(View.VISIBLE);
                        project_nameTextView_Leader.setText(joinTeamPOJO.getTeam().getProject_name());
                        cardView4_Leader.setVisibility(GONE);

                    } else {
                        cardview3.setVisibility(View.GONE);
                        project_nameTextView_Leader.setText("");

                    }
                    if (joinTeamPOJO.getTeam().getProject_description() != null)
                        bio_textView_Leader.setText(joinTeamPOJO.getTeam().getProject_description());
                    else
                        bio_textView_Leader.setText("");
                    if (joinTeamPOJO.getTeam().getProject_name() != null)
                        link1_textView_Leader.setText(joinTeamPOJO.getTeam().getCode());
                    else
                        link1_textView_Leader.setText("");
                    if (joinTeamPOJO.getTeam().getProject_name() != null)
                        link2_textView_Leader.setText(joinTeamPOJO.getTeam().getDemonstration());
                    else

                        link2_textView_Leader.setText("");
                    if (joinTeamPOJO.getTeam().getProject_name() != null)
                        link3_textView_Leader.setText(joinTeamPOJO.getTeam().getDesign());
                    else
                        link3_textView_Leader.setText("");
                    //break;


                    List<PtSkill> pt_skills = joinTeamPOJO.getPt_skills();

                    members = new ArrayList<>();
                    for (int i = 0; i < pt_skills.size(); i++) {
                        if (pt_skills.get(i).participant.get_id().equals(admin))
                            continue;
                        members.add(pt_skills.get(i).participant.get_id());

                    }
                    Log.i("pt_skill", String.valueOf(pt_skills.get(0).getParticipant().getName()));
                    teamMember_LeaderAdapter.setMemberLeaderList(pt_skills, admin, teamID);

                }
            }

            @Override
            public void onFailure(Call<JoinTeamPOJO> call, Throwable t) {
                Log.i("error33", t.getMessage());
                Visibility(0);

            }
        });




        deleteTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("TeamId=>", teamID);
                Call<Void> call8 = jsonPlaceHolderAPI.deleteTeam(MainActivity.getIdToken(), teamID);
                Log.i("callback problem82", "error82");
                call8.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call8, Response<Void> response8) {
                        Log.i("callback problem83", "error83");


                        if (!response8.isSuccessful()) {
                            Log.i("not sucess8", "code: " + response8.code());
                                   /* getFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.nav_host_fragment, new MyTeamsFragment())
                                            .addToBackStack(null)
                                            .commit();*/
                            Toast.makeText(getContext(), "Error in deleting team !!", Toast.LENGTH_SHORT).show();
                            return;

                        }

                        Log.i("deleteTeam", "delete Team works");
                        Log.i("Response body3", String.valueOf(response8.body()));
                        Toast.makeText(getContext(), "Team deleted successfully !!!", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onFailure(Call<Void> call8, Throwable t) {
                        Log.i("failed8", t.getMessage());
                        Toast.makeText(getContext(), "Error in deleting team !!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        //}

            /*@Override
            public void onFailure(Call<teamIdPOJO> call1, Throwable t) {
                Log.i("failed1", t.getMessage());
            }
        });*/

        /*Remove.setOnClickListener(v -> {
            if(Remove.getText()=="Remove")
            {

//                removeParticipant(teamID,participantID);}
        });*/
        //}
        //}
        //});
        ////////////Uncomment once mauth is is universal or is delecraled in mainactivity///////////////
        Hackname_Leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HackProfileFragment frag3 = new HackProfileFragment();
                Log.i("hackId", String.valueOf(hackID));
                Bundle bundle3 = new Bundle();
                bundle3.putString("ID", hackID);
                frag3.setArguments(bundle3);


                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, frag3)
                        .addToBackStack(null)
                        .commit();
            }
        });



    }

    /*private void removeParticipant(String TeamID, String participantID) {
        com.example.hackmate.POJOClasses.Kavita.Remove remove1 = new Remove(teamID,);
        //addProjectPOJO addProjectPOJO = new addProjectPOJO(projectName.getText().toString(),projectDescription.getText().toString(),gitHubLink.getText().toString(),designLink.getText().toString(),demoLink.getText().toString());

        JSONPlaceHolderAPI jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);
        Call<Remove> call= jsonPlaceHolderAPI.removeMember(idToken,remove1);

        call.enqueue(new Callback<Remove>() {
            @Override
            public void onResponse(Call<Remove> call, Response<Remove> response) {

                if (!response.isSuccessful()){
                    Log.i("sucess", "sucess");
                }
                Remove remove = response.body();
                Log.i("remove", String.valueOf(response.code()));

            }

            @Override
            public void onFailure(Call<Remove> call, Throwable t) {
                Log.i("error", t.getMessage());
            }
        });*/


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (GET_NAV_CODE == 0) {
            BottomNavigationView bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);
            bottomNavigation.setVisibility(View.VISIBLE);
        }
    }
    public void Visibility(int a){
        switch (a){
            case 1 :
                progressBar.setVisibility(VISIBLE);
                cardView_Leader.setVisibility(GONE);
                cardView2_Leader.setVisibility(GONE);
                inviteParticipantCard.setVisibility(GONE);
                cardView4_Leader.setVisibility(GONE);
                cardview3.setVisibility(GONE);
                projectsTitle_Leader.setVisibility(GONE);
                teamMembersTitle_Leader.setVisibility(GONE);

                break;

            case 0:
                progressBar.setVisibility(GONE);
                cardView_Leader.setVisibility(VISIBLE);
                cardView2_Leader.setVisibility(VISIBLE);
                inviteParticipantCard.setVisibility(VISIBLE);
                cardView4_Leader.setVisibility(VISIBLE);
                cardview3.setVisibility(VISIBLE);
                projectsTitle_Leader.setVisibility(VISIBLE);
                teamMembersTitle_Leader.setVisibility(VISIBLE);
                break;
        }
    }
}
