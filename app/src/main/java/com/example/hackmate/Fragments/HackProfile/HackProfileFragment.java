package com.example.hackmate.Fragments.HackProfile;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackmate.Fragments.AddFromExistingFragment;
import com.example.hackmate.Fragments.FindTeams.FindTeamsFragment;
import com.example.hackmate.Fragments.FindTeams.FindTeamsViewModel;
import com.example.hackmate.Fragments.TeamCreationFormFragment;
import com.example.hackmate.JSONPlaceholders.API;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.hackProfilePOJO;
import com.example.hackmate.R;
import com.example.hackmate.util.Functions;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HackProfileFragment extends Fragment {

    private TextView hackName,start,end,min,max,venue,prize,description;
    private API hackProfileApi;
    private String website;
    private String hackID, hackNaming;
    private HackProfileViewModel viewModel;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HackProfileViewModel.class);

    }

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

        hackProfileApi = RetrofitInstance.getRetrofitInstance().create(API.class);
        Bundle bundle = this.getArguments();
        hackID = bundle.getString("ID", null);
        Log.i("HACKING", "onViewCreated: " + viewModel.getHackName());

        if(viewModel.getHackName()==null)
            fetchData();
        else
            showData();


        fetchData();

        view.findViewById(R.id.viewWebsite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You will be directed to the hack website", Toast.LENGTH_SHORT).show();
                //Uri uri = Uri.parse(website); // missing 'http://' will cause crashed
                //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                //startActivity(intent);
            }
        });

        view.findViewById(R.id.participateNow).setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onPause() {
        super.onPause();
        viewModel.setHackName(hackName.getText().toString());
        viewModel.setStart(start.getText().toString());
        viewModel.setEnd(end.getText().toString());
        viewModel.setMin(min.getText().toString());
        viewModel.setMax(max.getText().toString());
        viewModel.setVenue(venue.getText().toString());
        viewModel.setPrize(prize.getText().toString());
        viewModel.setDescription(description.getText().toString());
    }

    public void fetchData()
    {
        Call<hackProfilePOJO> call = hackProfileApi.hackProfile(MainActivity.getIdToken(),hackID);
        call.enqueue(new Callback<hackProfilePOJO>() {
            @Override
            public void onResponse(Call<hackProfilePOJO> call, Response<hackProfilePOJO> response) {
                if(response.code()==401)
                    Functions.fetchToken(requireActivity(), () -> fetchData());
                if(!response.isSuccessful())
                {
                    getView().findViewById(R.id.hackProfileProgress).setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Error in retrieving the Data !!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(response.body() != null) {
                    getView().findViewById(R.id.hackProfileProgress).setVisibility(View.GONE);
                    getView().findViewById(R.id.hackProfileScroll).setVisibility(View.VISIBLE);
                    getView().findViewById(R.id.toolBar).setVisibility(View.VISIBLE);
                    hackNaming = response.body().name;
                    hackName.setText(response.body().name);
                    start.setText(response.body().start.substring(0, 10));
                    end.setText(response.body().end.substring(0, 10));
                    max.setText(response.body().max);
                    min.setText(response.body().min);
                    venue.setText(response.body().venue);
                    prize.setText(response.body().prize);
                    description.setText(response.body().description);
                    website = response.body().website;
                }
            }

            @Override
            public void onFailure(Call<hackProfilePOJO> call, Throwable t) {
                getView().findViewById(R.id.hackProfileProgress).setVisibility(View.GONE);
                Log.i("apple5",t.getMessage());
            }
        });
    }

    public void showData()
    {
        hackName.setText(viewModel.getHackName());
        start.setText(viewModel.getStart());
        end.setText(viewModel.getEnd());
        max.setText(viewModel.getMax());
        min.setText(viewModel.getMin());
        venue.setText(viewModel.getVenue());
        prize.setText(viewModel.getPrize());
        description.setText(viewModel.getDescription());
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
                        .replace(R.id.nav_host_fragment,new AddFromExistingFragment(hackID))
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
                bundle.putString("HackId",hackID);
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