package com.example.hackmate.Fragments;

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

import com.example.hackmate.Adapters.myTeamsAdapter;
import com.example.hackmate.JSONPlaceholders.JSONPlaceHolderAPI;
import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.Kavita.myTeams.Final2;
import com.example.hackmate.POJOClasses.Kavita.myTeamsPOJO;
import com.example.hackmate.POJOClasses.loginPOJO;
import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
<<<<<<< Updated upstream

import static android.media.CamcorderProfile.get;
=======
import retrofit2.internal.EverythingIsNonNull;

public class MyTeamsFragment extends Fragment {
    private static final String TAG = "MyTeamsFragment";
    public String id3;
    MyTeamsAdapter adapter;
    private ChipGroup chipGroup;
    private boolean isLoading = false, isLastPage = false;
    private int earlier_pos = 0, page = 1;
ImageView imageView;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
>>>>>>> Stashed changes

public class MyTeamsFragment extends Fragment implements myTeamsAdapter.OnTeamsListener {
    private RecyclerView TeamsRV;
    private List<Final2> myTeamsArrayList;
    private ChipGroup chips;
    // private  ArrayList<myTeamsModel> myTeamsArrayList;
    BottomNavigationView bottomNavigation;

    private ImageView Bell;
    private TextView newTeams;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String idToken = "Bearer ";
public String id3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_teams, container, false);

//inflate appbar for this particular fragment
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.myTeamsAppBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
<<<<<<< Updated upstream
        bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);

<<<<<<< Updated upstream
        /*TeamsRV = view.findViewById(R.id.RVTeams);
        myTeamsArrayList = new ArrayList<>();

        myTeamsArrayList.add(new myTeamsModel("Desiderata","Code2Create","Leader","Frontend","Backend","Machine Laerning","Mobile Dev","Backend"));
        myTeamsArrayList.add(new myTeamsModel("Desiderata","Code2Create","Member","Frontend","Backend","Machine Laerning","Mobile Dev","Backend"));
=======
=======
        bottomNavigationView = requireActivity().findViewById(R.id.bottom_nav_bar);
        TextView newTeams = view.findViewById(R.id.newTeam);
        ImageView bell = view.findViewById(R.id.imageView10);
        imageView=view.findViewById(R.id.imageView14);
        chipGroup = view.findViewById(R.id.skillGrp);
        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyTeamsAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);
        imageView.setVisibility(View.GONE);
>>>>>>> Stashed changes
        bell.setOnClickListener(v -> {
            bottomNavigationView.setVisibility(View.GONE);
view.findViewById(R.id.imageView11).setVisibility(View.GONE);
            //getChildFragmentManager()
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new InviteOrRequestFragment())
                    .addToBackStack(null)
                    .commit();
        });
>>>>>>> Stashed changes




        myTeamsAdapter myTeamsAdapter = new myTeamsAdapter(getContext(), myTeamsArrayList,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        TeamsRV.setLayoutManager(linearLayoutManager);
        TeamsRV.setAdapter(myTeamsAdapter);*/
        // in below two lines we are setting layoutmanager and adapter to our recycler view.
       /* TeamsRV.setLayoutManager(linearLayoutManager);
        TeamsRV.setAdapter(myTeamsAdapter);
        TeamsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        String[] team_name = {"Desiderata","The Hustlers"};
        String[]  hack_name ={"code2create","code2create"};
        String[]  teamPosition ={"Leader","Member"};
        String[][] domains = {{"App Dev","Frontend","Backend"},{"Frontend","Backend"}};
        TeamsRV.setAdapter(new myTeamsAdapter(getContext(),team_name,hack_name,teamPosition,domains,this));*/


        Bell = view.findViewById(R.id.imageView10);
        idToken = MainActivity.getidToken();

        Bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigation.setVisibility(View.GONE);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new InviteOrRequestFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        newTeams = view.findViewById(R.id.newTeam);
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
                        .replace(R.id.nav_host_fragment, frag)
                        .addToBackStack(null)
                        .commit();
            }
        });
        chips = view.findViewById(R.id.skillGrp);
        TeamsRV = view.findViewById(R.id.RVTeams);
        myTeamsArrayList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        TeamsRV.setLayoutManager(layoutManager);
        myTeamsAdapter myTeamsAdapter = new myTeamsAdapter(getContext(), myTeamsArrayList, this);
        TeamsRV.setAdapter(myTeamsAdapter);



       /* HttpLoggingInterceptor loggingInterceptor =new HttpLoggingInterceptor();
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
*/
        // Send token to your backend via HTTPS
        // ...
        //JSONPlaceHolderAPI jsonPlaceHolderAPI = retrofit1.create(JSONPlaceHolderAPI.class);

        Log.d("MyTeams", String.valueOf(idToken));
        JSONPlaceHolderAPI jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);
        loginAPI loginAPI = RetrofitInstance.getRetrofitInstance().create(loginAPI.class);

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
                loginPOJO loginPOJOS=response1.body();
                Log.i("Response bodykAVITA", loginPOJOS.getId());
                id3 = loginPOJOS.getId();
                Log.i("Response bodyid2", id3);
                Log.d("MyTeams", "jsonPlaceHolder");
                Call<myTeamsPOJO> call1 = jsonPlaceHolderAPI.getmyTeams(idToken);
                Log.i("callback problemmyTeams", "errorMyTeams");
                call1.enqueue(new Callback<myTeamsPOJO>() {
                    @Override
                    public void onResponse(Call<myTeamsPOJO> call1, Response<myTeamsPOJO> response1) {
                        Log.i("callback problem3MT", "error3MT");
                        if (!response1.isSuccessful()) {
                            Log.i("not sucess1", "code: " + response1.code());
                            return;
                        }
                        myTeamsPOJO myTeamsPOJOS = response1.body();
                        Log.i("Response body", myTeamsPOJOS.getLength().toString());
                        List<Final2> final_objs2 = myTeamsPOJOS.getFinal2();
                        Log.i("Response body1", final_objs2.get(0).getHackName());

                        myTeamsAdapter.setmyTeams(final_objs2,id3);
                    }

                    @Override
                    public void onFailure(Call<myTeamsPOJO> call1, Throwable t) {
                        Log.i("failed1", t.getMessage());
                    }
                });

            }

            @Override
            public void onFailure(Call<loginPOJO> call2, Throwable t) {
                Log.i("failed1", t.getMessage());
            }
        });

<<<<<<< Updated upstream

=======
    public void getMyTeams(int page3) {
        adapter.showProgress();
        JSONPlaceHolderAPI jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);
        Call<myTeamsPOJO> call1 = jsonPlaceHolderAPI.getmyTeams(MainActivity.getIdToken(), page3);
        Log.i("callback problem m", "errorMyTeams");
        call1.enqueue(new Callback<myTeamsPOJO>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<myTeamsPOJO> call1, Response<myTeamsPOJO> response1) {
                isLoading = false;
                adapter.removeProgress();
                swipeRefreshLayout.setRefreshing(false);
                Log.i("callback problem3MT", "error3MT");
                if (!response1.isSuccessful()) {
                    Log.i(TAG, "code: " + response1.code());
                    imageView.setVisibility(View.VISIBLE);
                    return;
                }
                myTeamsPOJO myTeamsPOJOS = response1.body();
                Log.i("Response body", myTeamsPOJOS.getLength().toString());
                if(myTeamsPOJOS.getLength()<8)
                    isLastPage = true;
                List<Final2> final_objs2 = myTeamsPOJOS.getFinal2();

                Log.i("Response body1", final_objs2.get(0).getHackName());
                adapter.setmyTeams(final_objs2, id3);
                /*if (final_objs2.size() == 0) {
                    --page;
                }*/
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<myTeamsPOJO> call1, Throwable t) {
                isLoading = false;
                adapter.removeProgress();
                imageView.setVisibility(View.VISIBLE);
                Log.i("failed1", t.getMessage());
            }
        });
>>>>>>> Stashed changes
    }

//}
    //});

    //}

    @Override
    public void OnTeamsClick(int position, String a, String teamId) {
        get(position);

        if (a == "Leader") {
            bottomNavigation.setVisibility(View.GONE);
            TeamProfileLeaderViewFragment frag = new TeamProfileLeaderViewFragment();
            Bundle bundle = new Bundle();
            bundle.putString("teamID", String.valueOf(teamId));
            frag.setArguments(bundle);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, frag)
                    .addToBackStack(null)
                    .commit();
        } else if (a == "Member") {
            //Log.i("MyTeams", "Member");
            bottomNavigation.setVisibility(View.GONE);
            TeamProfileMemberViewFragment frag1 = new TeamProfileMemberViewFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putString("teamID", String.valueOf(teamId));
            frag1.setArguments(bundle1);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, frag1)
                    .addToBackStack(null)
                    .commit();
        }

    }


}