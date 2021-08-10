package com.example.hackmate.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hackmate.Adapters.myTeamsAdapter;
import com.example.hackmate.R;
import com.example.hackmate.Models.myTeamsModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

public class MyTeamsFragment extends Fragment implements myTeamsAdapter.OnTeamsListener {
    private RecyclerView TeamsRV;
    //private  List<Final2> myTeamsArrayList;
    private ChipGroup chips;
    private  ArrayList<myTeamsModel> myTeamsArrayList;
    BottomNavigationView bottomNavigation;

    private ImageView Bell;
    private TextView newTeams;
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private String idToken = "Bearer ";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_my_teams, container, false);

//inflate appbar for this particular fragment
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.myTeamsAppBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TeamsRV = view.findViewById(R.id.RVTeams);
        myTeamsArrayList = new ArrayList<>();

        myTeamsArrayList.add(new myTeamsModel("Desiderata","Code2Create","Leader","Frontend","Backend","Machine Laerning","Mobile Dev","Backend"));
        myTeamsArrayList.add(new myTeamsModel("Desiderata","Code2Create","Member","Frontend","Backend","Machine Laerning","Mobile Dev","Backend"));


        bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);

        myTeamsAdapter myTeamsAdapter = new myTeamsAdapter(getContext(), myTeamsArrayList,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        TeamsRV.setLayoutManager(linearLayoutManager);
        TeamsRV.setAdapter(myTeamsAdapter);
        // in below two lines we are setting layoutmanager and adapter to our recycler view.
       /* TeamsRV.setLayoutManager(linearLayoutManager);
        TeamsRV.setAdapter(myTeamsAdapter);
        TeamsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        String[] team_name = {"Desiderata","The Hustlers"};
        String[]  hack_name ={"code2create","code2create"};
        String[]  teamPosition ={"Leader","Member"};
        String[][] domains = {{"App Dev","Frontend","Backend"},{"Frontend","Backend"}};
        TeamsRV.setAdapter(new myTeamsAdapter(getContext(),team_name,hack_name,teamPosition,domains,this));*/


        Bell=view.findViewById(R.id.imageView10);



        Bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigation.setVisibility(View.GONE);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,new InviteOrRequestFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        newTeams=view.findViewById(R.id.newTeam);
        newTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigation.setVisibility(View.GONE);

                TeamCreationFormFragment frag = new TeamCreationFormFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("Key", 1);
                frag.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,frag)
                        .addToBackStack(null)
                        .commit();
            }
        });
        chips = view.findViewById(R.id.skillGrp);
      /* TeamsRV = view.findViewById(R.id.RVTeams);
        myTeamsArrayList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        TeamsRV.setLayoutManager(layoutManager);
        myTeamsAdapter myTeamsAdapter = new myTeamsAdapter(getContext(), myTeamsArrayList,this);
        TeamsRV.setAdapter(myTeamsAdapter);



        HttpLoggingInterceptor loggingInterceptor =new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient1 =new OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                //.addInterceptor(loggingInterceptor1)
                .build();
        Retrofit retrofit1= new Retrofit.Builder()
                .baseUrl("https://hackportalbackend.herokuapp.com/DN_Team/")
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
        myTeamsAPI myTeamsAPI = retrofit1.create(myTeamsAPI.class);

        Call<myTeamsPOJO> call1 =myTeamsAPI.getmyTeams(idToken);
                            Log.i("callback problemmyTeams", "errorMyTeams");
        call1.enqueue(new Callback<myTeamsPOJO>() {
            @Override
            public void onResponse(Call<myTeamsPOJO> call1, Response<myTeamsPOJO> response1) {
                Log.i("callback problem3MT", "error3MT");
                if(!response1.isSuccessful())
                {
                    Log.i("not sucess1", "code: "+response1.code());
                    return;
                }
                myTeamsPOJO myTeamsPOJOS =  response1.body();
                Log.i("Response body",myTeamsPOJOS.getLength().toString());
                List<Final2> final_objs2 = myTeamsPOJOS.getFinal2();
                Log.i("Response body1",final_objs2.get(0).getHackName());
               myTeamsAdapter.setmyTeams(final_objs2);
                Log.i("Response body3","list sending to adapter sucessfull");
                //myTeamsArrayList= (ArrayList<myTeamsPOJO>) response1.body();

               /* for(myTeamsPOJO myTeamsPOJO :myTeamsArrayList) {

                    Log.d("problem1",myTeamsArrayList.toString());

                    myTeamsAdapter.setmyTeams(myTeamsArrayList);
                }
            }

            @Override
            public void onFailure( Call<myTeamsPOJO> call1, Throwable t) {
                Log.i("failed1", t.getMessage());
            }
        });
                        }
                    }
                });*/

    }

    @Override
    public void OnTeamsClick(int position, String a) {
        get(position);

        if(a =="Leader"){
            bottomNavigation.setVisibility(View.GONE);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment,new TeamProfileLeaderViewFragment())
                    .addToBackStack(null)
                    .commit();}
        else if (a=="Member" )
        {
            bottomNavigation.setVisibility(View.GONE);

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment,new TeamProfileMemberViewFragment())
                    .addToBackStack(null)
                    .commit();
        }

    }


}