package com.example.hackmate.Fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackmate.Adapters.ProjectAdapterTPL;
import com.example.hackmate.JSONPlaceholders.JSONPlaceHolderAPI;
import com.example.hackmate.Models.ProjectModel;
import com.example.hackmate.POJOClassesKavita.PtSkill;
import com.example.hackmate.POJOClassesKavita.hackByIdPOJO;
import com.example.hackmate.POJOClassesKavita.teamIdPOJO;
import com.example.hackmate.R;
import com.example.hackmate.Adapters.teamMember_LeaderAdapter;
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

public class TeamProfileLeaderViewFragment extends Fragment {
    private RecyclerView team_LeaderRV, projectRV;
    // Arraylist for storing data
    //private ArrayList<teamMember_Model> teamModelArrayList;
    private List<PtSkill> teamModelArrayList;
    private ArrayList<ProjectModel> projectModelArrayList;
    ImageView editProject, addProjectIcon;
    TextView addProjectTV, InviteParticpant, TeamName_Leader, Hackname_Leader, teamCode_Leader,deleteTeam;
    int GET_NAV_CODE = 0;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String idToken = "Bearer ";
    public String admin, hackID;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            GET_NAV_CODE = bundle.getInt("Key", 0);
        }

        team_LeaderRV = view.findViewById(R.id.rvTeam_Leader);

        addProjectIcon = view.findViewById(R.id.addProject_Icon);
        addProjectTV = view.findViewById(R.id.addProject_TextView);
        InviteParticpant = view.findViewById(R.id.inviteparticipant_textView);
        TeamName_Leader = view.findViewById(R.id.TeamName_Leader);
        Hackname_Leader = view.findViewById(R.id.HackName_Leader);
        teamCode_Leader = view.findViewById(R.id.teamCode_Leader);
        deleteTeam = view.findViewById(R.id.deleteTeam);
// here we have created new array list and added data to it.
        teamModelArrayList = new ArrayList<>();

        //teamModelArrayList.add(new teamMember_Model.TeamMemberModel("1.", "Deep Gandhi","itsverydeep@gmail.com","Leader",R.drawable.imageholder,true));
        // teamModelArrayList.add(new teamMember_Model.TeamMemberModel("2.", "Deep Gandhi","itsverydeep@gmail.com"," ",R.drawable.imageholder,false));
        // teamModelArrayList.add(new teamMember_Model.TeamMemberModel("3.", "Deep Gandhi","itsverydeep@gmail.com"," ",R.drawable.imageholder,false));
        //teamModelArrayList.add(new teamMember_Model.TeamMemberModel("4.", "Deep Gandhi","itsverydeep@gmail.com"," ",R.drawable.imageholder,false));
        // we are initializing our adapter class and passing our arraylist to it.
        teamMember_LeaderAdapter teamMember_LeaderAdapter = new teamMember_LeaderAdapter(getContext(), teamModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        team_LeaderRV.setLayoutManager(linearLayoutManager);
        team_LeaderRV.setAdapter(teamMember_LeaderAdapter);


        addProjectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new AddProjectFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });
        addProjectTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new AddProjectFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });
        InviteParticpant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new FindParticipantFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        teamCode_Leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Team Code", teamCode_Leader.getText());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getActivity(), "Team Code copied to clipboard", Toast.LENGTH_LONG).show();
            }
        });

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient1 = new OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                //.addInterceptor(loggingInterceptor1)
                .build();
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl("https://hackportalbackend.herokuapp.com/")
                .client(okHttpClient1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mAuth.getCurrentUser().getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            idToken += task.getResult().getToken();
                            Log.i("xx", idToken);
                            // Send token to your backend via HTTPS
                            // ...
                            //myTeamsAPI myTeamsAPI = retrofit1.create(myTeamsAPI.class);
                            JSONPlaceHolderAPI jsonPlaceHolderAPI = retrofit1.create(JSONPlaceHolderAPI.class);

                            Call<teamIdPOJO> call1 = jsonPlaceHolderAPI.getTeamId(idToken, "60f647fd7aa44d77a0dc2805");
                            Log.i("callback problemLeader", "errorLeader");
                            call1.enqueue(new Callback<teamIdPOJO>() {
                                @Override
                                public void onResponse(Call<teamIdPOJO> call1, Response<teamIdPOJO> response1) {
                                    Log.i("callback problem3MT", "error3MT");
                                    if (!response1.isSuccessful()) {
                                        Log.i("not sucess1", "code: " + response1.code());
                                        return;
                                    }
                                    teamIdPOJO teamIdPOJOS = response1.body();
                                    Log.i("response sucess", String.valueOf(teamIdPOJOS));
                                    TeamName_Leader.setText(teamIdPOJOS.getTeam1().getName());
                                    hackID = teamIdPOJOS.getTeam1().getHack_id();
                                    teamCode_Leader.setText(teamIdPOJOS.getTeam1().getTeam_code());
                                    admin = teamIdPOJOS.getTeam1().getAdmin_id();
                                    Log.i("checkAdminId", admin);
                                    List<PtSkill> member_objs2 = teamIdPOJOS.getPt_skills();
                                    // adminID2=teamIdPOJOS.getTeam1().getAdmin_id();
                                    teamMember_LeaderAdapter.setMemberLeaderList(member_objs2, admin);
                                    //myTeamsPOJO myTeamsPOJOS =  response1.body();
                                    //Log.i("Response body",ParticiapntName);
                                    //List<Final2> final_objs2 = myTeamsPOJOS.getFinal2();



                                    Log.i("Response body3", "list sending to adapter sucessfull");

                                   // JSONPlaceHolderAPI jsonPlaceHolderAPI = retrofit1.create(JSONPlaceHolderAPI.class);
                                    //hackListAPI hackListAPI1 = retrofit1.create(hackListAPI.class);

                                    Log.i("callback problem", "error");
                                    Call<hackByIdPOJO> call5 = jsonPlaceHolderAPI.getHackById(idToken, hackID);
                                    //Call<hackByIdPOJO> call5 = hackListAPI1.getHackById(idToken, hackID);
                                    Log.i("callback problem2", "error2");
                                    call5.enqueue(new Callback<hackByIdPOJO>() {
                                        @Override
                                        public void onResponse(Call<hackByIdPOJO> call5, Response<hackByIdPOJO> response5) {
                                            Log.i("callback problem3", "error3");
                                            if (!response5.isSuccessful()) {
                                                Log.i("not sucess5", "code: " + response5.code());
                                                return;
                                            }

                                            Hackname_Leader.setText(response5.body().getName());
                                            Log.i("Response body3", "list sending to adapter sucessfull");


                                        }

                                        @Override
                                        public void onFailure(Call<hackByIdPOJO> call5, Throwable t) {
                                            Log.i("failed5", t.getMessage());
                                        }
                                    });

                                    deleteTeam.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Log.i("callback problem81", "error81");
                                            Call<teamIdPOJO> call8 = jsonPlaceHolderAPI.deleteTeam(idToken, teamIdPOJOS.team1.get_id());
                                            Log.i("callback problem82", "error82");
                                            call8.enqueue(new Callback<teamIdPOJO>() {
                                                @Override
                                                public void onResponse(Call<teamIdPOJO> call8, Response<teamIdPOJO> response8) {
                                                    Log.i("callback problem83", "error83");
                                                    if (!response8.isSuccessful()) {
                                                        Log.i("not sucess8", "code: " + response8.code());
                                                        return;
                                                    }

                                                    Log.i("deleteTeam", "delete Team works");
                                                    Log.i("Response body3", "list sending to adapter sucessfull");


                                                }

                                                @Override
                                                public void onFailure(Call<teamIdPOJO> call8, Throwable t) {
                                                    Log.i("failed8", t.getMessage());
                                                }
                                            });

                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Call<teamIdPOJO> call1, Throwable t) {
                                    Log.i("failed1", t.getMessage());
                                }
                            });


                        }
                    }
                });
        /*   ////////////Uncomment once mauth is is universal or is delecraled in mainactivity///////////////
        Hackname_Leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HackProfileFragment frag = new HackProfileFragment();
                Log.i("hackId", String.valueOf(hackID));
                Bundle bundle = new Bundle();
                bundle.putString("ID", String.valueOf(getId()));
                frag.setArguments(bundle);


                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, frag)
                        .addToBackStack(null)
                        .commit();
            }
        });*/

        projectRV = view.findViewById(R.id.ProjectRV);
        projectModelArrayList = new ArrayList<>();
        projectModelArrayList.add(new ProjectModel("Hackmate", "Project for team building for hackathons", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Tristique mauris, " +
                "nec vitae cursus phasellus a proin et. Sit in velit duis iaculis est. " +
                "At odio sociis venenatis ut commodo. Aliquet eget morbi faucibus nisl " +
                "nec quis suscipit ut. Mus vestibulum risus at ante lorem volutpat. " +
                "In vitae vitae, tortor a ipsum ipsum. Ipsum cras eu odio natoque blandit commodo aliquam.",
                "abc@gmail.com", "abc@gmail.com", "abc@gmail.com"));

        projectModelArrayList.add(new ProjectModel("Hackmate", "Project for team building for hackathons", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Tristique mauris, " +
                "nec vitae cursus phasellus a proin et. Sit in velit duis iaculis est. " +
                "At odio sociis venenatis ut commodo. Aliquet eget morbi faucibus nisl " +
                "nec quis suscipit ut. Mus vestibulum risus at ante lorem volutpat. " +
                "In vitae vitae, tortor a ipsum ipsum. Ipsum cras eu odio natoque blandit commodo aliquam.",
                "abc@gmail.com", "abc@gmail.com", "abc@gmail.com"));
        ProjectAdapterTPL projectAdapterTPL = new ProjectAdapterTPL(getContext(), projectModelArrayList);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        projectRV.setLayoutManager(linearLayoutManager1);
        projectRV.setAdapter(projectAdapterTPL);

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
