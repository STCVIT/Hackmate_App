package com.example.hackmate.Fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackmate.Adapters.ProjectAdapterTP;
import com.example.hackmate.JSONPlaceholders.JSONPlaceHolderAPI;
import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.MainActivity;
import com.example.hackmate.Models.ProjectModel;
import com.example.hackmate.POJOClasses.Kavita.Projects.IndividualProject;
import com.example.hackmate.POJOClasses.Kavita.Projects.TeamProject;
import com.example.hackmate.POJOClasses.Kavita.Projects.getProjectPOJO;
import com.example.hackmate.POJOClasses.PtSkill;
import com.example.hackmate.POJOClasses.Kavita.hackByIdPOJO;
import com.example.hackmate.POJOClasses.Kavita.teamIdPOJO;
import com.example.hackmate.POJOClasses.loginPOJO;
import com.example.hackmate.R;
import com.example.hackmate.Adapters.teamMemberAdapter;
import com.example.hackmate.util.RetrofitInstance;
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

public class TeamProfileMemberViewFragment extends Fragment {
    private RecyclerView teamRV, projectParticipantRV;
    // Arraylist for storing data
    //private ArrayList<teamMember_Model>teamMemberArrayList;
    private List<PtSkill> teamMemberArrayList;
    //private ArrayList<ProjectModel> projectParticipantModelArrayList;
    private List<TeamProject> projectParticipantModelArrayList;
    int GET_NAV_CODE = 0;
    TextView teamCode, TeamName, Hackname;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String idToken = "Bearer ";
    public String admin, hackID, id3, teamID, teamNameText;
    public String adminID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_team_profile_member_view, container, false);

//inflate appbar for this particular fragment
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.particularTeamAppBar);
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

        teamRV = view.findViewById(R.id.rvTeam);
        teamCode = view.findViewById(R.id.teamCode);
        TeamName = view.findViewById(R.id.TeamName);
        Hackname = view.findViewById(R.id.HackName);
// here we have created new array list and added data to it.
        teamMemberArrayList = new ArrayList<>();


        // we are initializing our adapter class and passing our arraylist to it.
        teamMemberAdapter teamMemberAdapter = new teamMemberAdapter(getContext(), teamMemberArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        teamRV.setLayoutManager(linearLayoutManager);
        teamRV.setAdapter(teamMemberAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            teamMemberArrayList.sort((o1, o2) -> (o1.getParticipant().get_id().equals(admin) ? "Leader" : "").compareTo((o2.getParticipant().get_id().equals(admin) ? "Leader" : "")));
        }
        teamCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard1 = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip1 = ClipData.newPlainText("Team Code", teamCode.getText());
                clipboard1.setPrimaryClip(clip1);

                Toast.makeText(getActivity(), "Team Code copied to clipboard", Toast.LENGTH_LONG).show();
            }
        });

        idToken = MainActivity.getidToken();
        projectParticipantRV = view.findViewById(R.id.ProjectRV_participant);
        projectParticipantModelArrayList = new ArrayList<>();
        ProjectAdapterTP projectAdapterTP = new ProjectAdapterTP(getContext(), projectParticipantModelArrayList);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        projectParticipantRV.setLayoutManager(linearLayoutManager2);
        projectParticipantRV.setAdapter(projectAdapterTP);

        /*HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
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
                            //myTeamsAPI myTeamsAPI = retrofit1.create(myTeamsAPI.class);*/
        teamID = getArguments().getString("teamID");
        loginAPI loginAPI = RetrofitInstance.getRetrofitInstance().create(loginAPI.class);
        JSONPlaceHolderAPI jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);
        // Call<teamIdPOJO> call1 = jsonPlaceHolderAPI.getTeamId(idToken, "60f647fd7aa44d77a0dc2805");
        Call<teamIdPOJO> call1 = jsonPlaceHolderAPI.getTeamId(idToken, getArguments().getString("teamID"));
        Log.i("callback problemLeader", "errorLeader");
        call1.enqueue(new Callback<teamIdPOJO>() {
            @Override
            public void onResponse(Call<teamIdPOJO> call4, Response<teamIdPOJO> response4) {
                Log.i("callback problem3MT", "error3MT");
                if (!response4.isSuccessful()) {
                    Log.i("not sucess1", "code: " + response4.code());
                    return;
                }
                Call<loginPOJO> call2 = loginAPI.getParticipant(idToken);
                Log.i("myTeamsLogin", "errorLogin");
                call2.enqueue(new Callback<loginPOJO>() {
                    @Override
                    public void onResponse(Call<loginPOJO> call2, Response<loginPOJO> response1) {
                        Log.i("callback problem3MT", "error3MT");
                        if (!response1.isSuccessful()) {
                            Log.i("not sucess1", "code: " + response1.code());
                            return;
                        }
                        loginPOJO loginPOJOS = response1.body();
                        Log.i("Response bodykAVITA", loginPOJOS.getId());
                        id3 = loginPOJOS.getId();
                        Log.i("Response bodyid2", id3);
                        teamIdPOJO teamIdPOJOS = response4.body();
                        Log.i("response sucess", String.valueOf(teamIdPOJOS));
                        TeamName.setText(teamIdPOJOS.getTeam1().getName());
                        hackID = teamIdPOJOS.getTeam1().getHackId();
                        teamCode.setText(teamIdPOJOS.getTeam1().getTeamCode());
                        admin = teamIdPOJOS.getTeam1().getAdminId();
                        Log.i("checkAdminId", admin);
                        teamID = teamIdPOJOS.getTeam1().getId();
                        teamNameText = teamIdPOJOS.getTeam1().getName();
                        List<PtSkill> member_objs3 = teamIdPOJOS.getPt_skills();
                        adminID = teamIdPOJOS.getTeam1().getAdminId();
                        ;
                        // adminID2=teamIdPOJOS.getTeam1().getAdmin_id();
                        teamMemberAdapter.setMemberList(member_objs3, admin, id3, teamID);
                        Log.i("admin id get",admin);
                        Call<getProjectPOJO> call6 = jsonPlaceHolderAPI.getProject1(idToken, admin);

                        Log.i("callback problem3", "call happening");
                        call6.enqueue(new Callback<getProjectPOJO>() {
                            @Override
                            public void onResponse(Call<getProjectPOJO> call6, Response<getProjectPOJO> response6) {
                                Log.i("callback problem3", "error3");
                                if (!response6.isSuccessful()) {
                                    Log.i("not sucess5", "code: " + response6.code());
                                    return;
                                }
                                getProjectPOJO getProjectPOJOS = response6.body();
                                List<IndividualProject> individualProjects = getProjectPOJOS.getIndividualProjects();
                                List<TeamProject> teamProjects = getProjectPOJOS.getTeams();
                                Log.i("TeamProfileMember", individualProjects.get(0).getName());
                                Log.i("TeamProfileMember", teamProjects.get(0).getProjectName());
                                Log.i("TeamProfileMember", teamNameText);
                                projectAdapterTP.setProjectMember(teamProjects, teamNameText);
                                Log.i("Response body3", "list sending to adapter sucessfull");
                            }
                            @Override
                            public void onFailure(Call<getProjectPOJO> call6, Throwable t) {
                                Log.i("failed5", t.getMessage());
                            }
                        });

                    }

                    public void onFailure(Call<loginPOJO> call2, Throwable t) {
                        Log.i("failed1", t.getMessage());
                    }
                });
             /*   teamIdPOJO teamIdPOJOS = response4.body();
                Log.i("response sucess", String.valueOf(teamIdPOJOS));
                TeamName.setText(teamIdPOJOS.getTeam1().getName());
                hackID = teamIdPOJOS.getTeam1().getHackId();
                teamCode.setText(teamIdPOJOS.getTeam1().getTeamCode());
                admin = teamIdPOJOS.getTeam1().getAdminId();
                Log.i("checkAdminId", admin);
                List<PtSkill> member_objs3 = teamIdPOJOS.getPt_skills();
                // adminID2=teamIdPOJOS.getTeam1().getAdmin_id();
                teamMemberAdapter.setMemberList(member_objs3, admin,id3);*/
                //myTeamsPOJO myTeamsPOJOS =  response1.body();
                //Log.i("Response body",ParticiapntName);
                //List<Final2> final_objs2 = myTeamsPOJOS.getFinal2();

                Log.i("Response body3", "list sending to adapter sucessfull");
                //hackListAPI hackListAPI1 = retrofit1.create(hackListAPI.class);
                //JSONPlaceHolderAPI jsonPlaceHolderAPI = retrofit1.create(JSONPlaceHolderAPI.class);
                Log.i("callback problem", "error");
                Call<hackByIdPOJO> call5 = jsonPlaceHolderAPI.getHackById(idToken, hackID);
                Log.i("callback problem2", "error2");
                call5.enqueue(new Callback<hackByIdPOJO>() {
                    @Override
                    public void onResponse(Call<hackByIdPOJO> call5, Response<hackByIdPOJO> response5) {
                        Log.i("callback problem3", "error3");
                        if (!response5.isSuccessful()) {
                            Log.i("not sucess5", "code: " + response5.code());
                            return;
                        }

                        Hackname.setText(response5.body().getName());
                        Log.i("Response body3", "list sending to adapter sucessfull");


                    }

                    @Override
                    public void onFailure(Call<hackByIdPOJO> call5, Throwable t) {
                        Log.i("failed5", t.getMessage());
                    }
                });
                Log.i("TeamProfileMember", String.valueOf(admin));
              /*  Call<getProjectPOJO> call6 = jsonPlaceHolderAPI.getProject1(idToken, admin);

                Log.i("callback problem3", "call happening");
                call6.enqueue(new Callback<getProjectPOJO>() {
                    @Override
                    public void onResponse(Call<getProjectPOJO> call6, Response<getProjectPOJO> response6) {
                        Log.i("callback problem3", "error3");
                        if (!response6.isSuccessful()) {
                            Log.i("not sucess5", "code: " + response6.code());
                            return;
                        }
                        getProjectPOJO getProjectPOJOS = response6.body();
                        List<IndividualProject> individualProjects = getProjectPOJOS.getIndividualProjects();
                        List<TeamProject> teamProjects = getProjectPOJOS.getTeams();
                        Log.i("TeamProfileMember", individualProjects.get(0).getName());
                        Log.i("TeamProfileMember", teamProjects.get(0).getProjectName());
                        Log.i("TeamProfileMember", teamNameText);
                        projectAdapterTP.setProjectMember(teamProjects, teamNameText);
                        Log.i("Response body3", "list sending to adapter sucessfull");
                    }
                    @Override
                    public void onFailure(Call<getProjectPOJO> call6, Throwable t) {
                        Log.i("failed5", t.getMessage());
                    }
                    });*/


            }
        public void onFailure (Call < teamIdPOJO > call4, Throwable t){
            Log.i("failed1", t.getMessage());
        }


                      });




     /*   myTeamsAPI myTeamsAPI = retrofit1.create(myTeamsAPI.class);
        Call<teamIdPOJO> call4 = myTeamsAPI.getProject(idToken, "60f647fd7aa44d77a0dc2805");
        Log.i("callback problemLeader", "errorLeader");
        call4.enqueue(new Callback<teamIdPOJO>() {
            @Override
            public void onResponse(Call<teamIdPOJO> call4, Response<teamIdPOJO> response4) {
                Log.i("callback problem3MT", "error3MT");
                if (!response4.isSuccessful()) {
                    Log.i("not sucess1", "code: " + response4.code());
                    return;
                }

                Log.i("working projects", response4.body().toString());

                public void onFailure (Call <teamIdPOJO> call4, Throwable t){
                    Log.i("failed1", t.getMessage());
                }
            }
        });*/

        /* ////////////Uncomment once mauth is is universal or is delecraled in mainactivity///////////////
Hackname.setOnClickListener(new View.OnClickListener() {
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


    /*Call<getProjectPOJO> call5 = jsonPlaceHolderAPI.getProject1(idToken, admin);
        Log.i("TeamProfileMember",String.valueOf(admin));
        Log.i("callback problem3","call happening");
        call5.enqueue(new Callback<getProjectPOJO>()

    {
        @Override
        public void onResponse
        (Call < getProjectPOJO > call5, Response < getProjectPOJO > response5){
        Log.i("callback problem3", "error3");
        if (!response5.isSuccessful()) {
            Log.i("not sucess5", "code: " + response5.code());
            return;
        }
        getProjectPOJO getProjectPOJOS = response5.body();
        List<IndividualProject> individualProjects = getProjectPOJOS.getIndividualProjects();
        List<TeamProject> teamProjects = getProjectPOJOS.getTeams();
        Log.i("TeamProfileLeader", individualProjects.get(0).getName());
        Log.i("TeamProfileLeader", teamProjects.get(0).getProjectName());
        Log.i("TeamProfileLeader", teamNameText);
        projectAdapterTP.setProjectMember(teamProjects, teamNameText);
        Log.i("Response body3", "list sending to adapter sucessfull");

    }

        @Override
        public void onFailure (Call < getProjectPOJO > call5, Throwable t){
        Log.i("failed5", t.getMessage());
    }
    });
*/

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

