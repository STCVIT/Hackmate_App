package com.example.hackmate.Fragments;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackmate.JSONPlaceholders.API;
import com.example.hackmate.LoginActivity;
import com.example.hackmate.POJOClasses.hackProfilePOJO;
import com.example.hackmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HackProfileFragment extends Fragment {

    private AppCompatButton viewWebsite, participateNow;
    private TextView hackName,start,end,min,max,venue,prize,description;
    private API hackProfileApi;
    private String website;
    private String hackID, hackNaming;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hack_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hackName = view.findViewById(R.id.hackName);
        start = view.findViewById(R.id.dateStart);
        end = view.findViewById(R.id.dateEnd);
        venue = view.findViewById(R.id.venue);
        prize = view.findViewById(R.id.prizePool);
        description = view.findViewById(R.id.aboutHack);
        min = view.findViewById(R.id.minMem);
        max = view.findViewById(R.id.maxMem);
        viewWebsite = view.findViewById(R.id.viewWebsite);
        participateNow = view.findViewById(R.id.participateNow);

        retrofit();
        getIDToken();

        viewWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You will be directed to the hack website", Toast.LENGTH_SHORT).show();
                //Uri uri = Uri.parse(website); // missing 'http://' will cause crashed
                //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                //startActivity(intent);
            }
        });

        participateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogBox();
                //Toast.makeText(getActivity(), "Following three buttons will be shown in dialog box !!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BottomNavigationView bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigation.setVisibility(View.VISIBLE);
    }

    public void retrofit()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hackportalbackend.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        hackProfileApi = retrofit.create(API.class);
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
                            showData(idToken);
                        }
                    }
                });
    }

    public void showData(String idToken)
    {
        Bundle bundle = this.getArguments();
        hackID = bundle.getString("ID", null);

        Call<hackProfilePOJO> call = hackProfileApi.hackProfile(idToken,hackID);
        call.enqueue(new Callback<hackProfilePOJO>() {
            @Override
            public void onResponse(Call<hackProfilePOJO> call, Response<hackProfilePOJO> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(getActivity(), "Error in retrieving the Data !!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                hackNaming = response.body().name;
                hackName.setText(response.body().name);
                start.setText(response.body().start.substring(0,10));
                end.setText(response.body().end.substring(0,10));
                max.setText(response.body().max);
                min.setText(response.body().min);
                venue.setText(response.body().venue);
                prize.setText(response.body().prize);
                description.setText(response.body().description);
                website = response.body().website;
            }

            @Override
            public void onFailure(Call<hackProfilePOJO> call, Throwable t) {
                Log.i("apple5",t.getMessage());
            }
        });
    }
    public void AlertDialogBox()
    {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.custom_participate_now_dialog,null);

        AppCompatButton addFromExisting = (AppCompatButton) mView.findViewById(R.id.addFrommExisting);
        AppCompatButton joinTeam = (AppCompatButton) mView.findViewById(R.id.joinTeam);
        AppCompatButton createTeam = (AppCompatButton) mView.findViewById(R.id.createTeam);

        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

        addFromExisting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,new AddFromExistingFragment())
                        .addToBackStack(null)
                        .commit();
                alertDialog.dismiss();
            }
        });

        joinTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindTeamsFragment frag = new FindTeamsFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("Key", 1);
                bundle.putString("HackId",hackID);
                bundle.putString("HackName", hackNaming);
                frag.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,frag)
                        .addToBackStack(null)
                        .commit();
                alertDialog.dismiss();

            }
        });

        createTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamCreationFormFragment frag = new TeamCreationFormFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("Keys", 1);
                frag.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,frag)
                        .addToBackStack(null)
                        .commit();
                alertDialog.dismiss();
            }
        });
    }
}