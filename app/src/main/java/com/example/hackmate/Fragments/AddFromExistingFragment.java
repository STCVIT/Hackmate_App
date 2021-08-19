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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackmate.Adapters.AddFromExistingAdapter;
import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.MainActivity;
import com.example.hackmate.Models.AddFromExistingModel;
import com.example.hackmate.POJOClasses.JoinTeamPOJO;
import com.example.hackmate.POJOClasses.Team;


import com.example.hackmate.POJOClasses.loginPOJO;
import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddFromExistingFragment extends Fragment {

    RecyclerView addHackToTeam;
    private loginAPI loginAPI;
    private String hackId;
    TextView goingForHackTextView;
    ProgressBar progressBar;

    public AddFromExistingFragment(String hackId) {
        this.hackId = hackId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_from_esisting, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialise();

        goingForHackTextView.setVisibility(View.GONE);

        Log.i("HACKID_SDVSV",hackId);
        addHackToTeam.setLayoutManager(new LinearLayoutManager(getContext()));


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        loginAPI = RetrofitInstance.getRetrofitInstance().create(loginAPI.class);

        Call<loginPOJO> call1 = loginAPI.getParticipant("Bearer " + MainActivity.getIdToken());
        call1.enqueue(new Callback<loginPOJO>() {
            @Override
            public void onResponse(Call<loginPOJO> call1, Response<loginPOJO> response1) {
                String id = String.valueOf(response1.body().getId());
                Log.i("idd", id);

                Call<List<JoinTeamPOJO>> call = loginAPI.getMyTeam("Bearer " + MainActivity.getIdToken(),
                        hackId);//will get id from previous fragment
                call.enqueue(new Callback<List<JoinTeamPOJO>>() {
                    @Override
                    public void onResponse(Call<List<JoinTeamPOJO>> call, Response<List<JoinTeamPOJO>> response) {
                        List<JoinTeamPOJO> teamList = response.body();
                        if (response.body() != null) {
                            Log.i("String value of", String.valueOf(teamList));
//                            Log.i("team details", String.valueOf(teamList.get(0).getTeam().getMembers().get(0).get_id()));
                            AddFromExistingAdapter existingAdapter = new AddFromExistingAdapter(getContext(), teamList);
                            addHackToTeam.setAdapter(existingAdapter);
                            existingAdapter.setGetTeams(teamList, hackId);

                        } else{
                            goingForHackTextView.setVisibility(View.VISIBLE);
                            goingForHackTextView.setText("All teams are registered for a hack, please make a new team to register for this hack!!!");
//                            Toast.makeText(getActivity(), "No teams exist which is not going for this hack!", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<List<JoinTeamPOJO>> call, Throwable t) {
                        Log.i("error33", t.getMessage());
                        Toast.makeText(getActivity(), "Failed To Fetch!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });

            }

            @Override
            public void onFailure(Call<loginPOJO> call, Throwable t) {

            }
        });
    }

    public void initialise() {

        addHackToTeam = getView().findViewById(R.id.addHackToTeam_recyclerView);
        goingForHackTextView = getView().findViewById(R.id.going_for_hack_textView);
        progressBar = getView().findViewById(R.id.progressBar_AFE);
    }
}