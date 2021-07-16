package com.example.hackmate.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hackmate.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HackProfileFragment extends Fragment {

    AppCompatButton viewWebsite, participateNow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hack_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewWebsite = view.findViewById(R.id.viewWebsite);
        participateNow = view.findViewById(R.id.participateNow);

        viewWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You will be directed to the hack website", Toast.LENGTH_SHORT).show();
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
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,new TeamCreationFormFragment())
                        .addToBackStack(null)
                        .commit();
                alertDialog.dismiss();
            }
        });
    }
}