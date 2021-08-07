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

import com.example.hackmate.Adapters.AddFromExistingAdapter;
import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.MainActivity;
import com.example.hackmate.Models.AddFromExistingModel;
import com.example.hackmate.POJOClasses.TeamDetails;
import com.example.hackmate.POJOClasses.GetMyTeamPOJO;
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
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String idToken;
    private loginAPI loginAPI;
    Retrofit retrofit;

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

        addHackToTeam.setLayoutManager(new LinearLayoutManager(getContext()));
//        AddFromExistingModel model2 = new AddFromExistingModel("Desiderata", "Hello there",
//                "Leader");
//        AddFromExistingModel model3 = new AddFromExistingModel("Desiderata", "Hello there",
//                "Member");
//        ArrayList arrayList1 = new ArrayList<AddFromExistingModel>();
//        arrayList1.add(model2);
//        arrayList1.add(model3);
//        String[][] domains = {{"App Dev", "Frontend", "Backend"}, {"Frontend", "Backend"}};


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        loginAPI = RetrofitInstance.getRetrofitInstance().create(loginAPI.class);

        Call<loginPOJO> call1 = loginAPI.getParticipant("Bearer " + MainActivity.getidToken());
        call1.enqueue(new Callback<loginPOJO>() {
            @Override
            public void onResponse(Call<loginPOJO> call1, Response<loginPOJO> response) {
                String id = String.valueOf(response.body().getId());
                Log.i("idd", id);

                Call<GetMyTeamPOJO> call = loginAPI.getMyTeam("Bearer " + MainActivity.getidToken());//will get id from previous fragment
                call.enqueue(new Callback<GetMyTeamPOJO>() {
                    @Override
                    public void onResponse(Call<GetMyTeamPOJO> call, Response<GetMyTeamPOJO> response) {
                        GetMyTeamPOJO getMyTeamPOJO = response.body();
                        Log.i("teams_length", String.valueOf(getMyTeamPOJO.getLength()));
                        List<TeamDetails> teamDetailsList = getMyTeamPOJO.getTeamDetails();
                        List<TeamDetails> teamDetails = new ArrayList<>();
                        for (int i = 0; i < teamDetailsList.size(); i++) {

                            if (teamDetailsList.get(i).getTeam().getHack_id() == null) {
                                Log.i("admin id", teamDetailsList.get(i).getTeam().getAdmin_id());
                                if(teamDetailsList.get(i).getTeam().getAdmin_id().equals(id)){
                                    teamDetails.add(teamDetailsList.get(i));
                                    Log.i("space", "aabb");
                                    Log.i("hack names", String.valueOf(teamDetailsList.get(i).getTeam().getHack_id()));
                                }
                            }
                        }

                        Log.i("String value of", String.valueOf(teamDetailsList));
                        Log.i("team details", String.valueOf(teamDetailsList.get(0).getTeam().getMembers().get(0).get_id()));
                        AddFromExistingAdapter existingAdapter = new AddFromExistingAdapter(getContext(), teamDetailsList);
                        addHackToTeam.setAdapter(existingAdapter);
                        existingAdapter.setGetTeams(teamDetails);
                    }

                    @Override
                    public void onFailure(Call<GetMyTeamPOJO> call, Throwable t) {
                        Log.i("error33", t.getMessage());
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
    }
}