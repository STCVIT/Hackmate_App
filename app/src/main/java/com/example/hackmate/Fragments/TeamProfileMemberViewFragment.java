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
import android.widget.TextView;

import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.POJOClasses.JoinTeamPOJO;
import com.example.hackmate.R;
import com.example.hackmate.Adapters.teamMemberAdapter;
import com.example.hackmate.Models.teamMember_Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TeamProfileMemberViewFragment extends Fragment {
    private RecyclerView teamRV;
    // Arraylist for storing data
    private ArrayList<teamMember_Model> courseModelArrayList;


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_team_profile_member_view, container, false);

//inflate appbar for this particular fragment
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.particularTeamAppBar_Leader);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialise();

        courseModelArrayList.add(new teamMember_Model.TeamMemberModel("1.", "Deep Gandhi", "itsverydeep@gmail.com", "Leader", R.drawable.imageholder, true));
        courseModelArrayList.add(new teamMember_Model.TeamMemberModel("2.", "Deep Gandhi", "itsverydeep@gmail.com", " ", R.drawable.imageholder, true));
        courseModelArrayList.add(new teamMember_Model.TeamMemberModel("3.", "Deep Gandhi", "itsverydeep@gmail.com", " ", R.drawable.imageholder, true));
        courseModelArrayList.add(new teamMember_Model.TeamMemberModel("4.", "Me", "itsverydeep@gmail.com", " ", R.drawable.imageholder, false));
        // we are initializing our adapter class and passing our arraylist to it.
        teamMemberAdapter teamMemberAdapter = new teamMemberAdapter(getContext(), courseModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        teamRV.setLayoutManager(linearLayoutManager);
        teamRV.setAdapter(teamMemberAdapter);



    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        BottomNavigationView bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigation.setVisibility(View.VISIBLE);
    }

    public void initialise() {
        teamRV = getView().findViewById(R.id.rvTeam);
        // here we have created new array list and added data to it.
        courseModelArrayList = new ArrayList<>();
    }
}
