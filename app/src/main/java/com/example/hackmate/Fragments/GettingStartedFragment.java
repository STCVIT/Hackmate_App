package com.example.hackmate.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.hackmate.LoginActivity;
import com.example.hackmate.MainActivity;
import com.example.hackmate.R;


public class GettingStartedFragment extends Fragment {


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_getting_started, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LoginActivity loginActivity = (LoginActivity) getActivity();

        Button createProfile = view.findViewById(R.id.createProfile);
        createProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        AutoCompleteTextView YOG_CompleteTextView = view.findViewById(R.id.year_of_graduation);
        String[] years = {"Year of Graduation", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
        ArrayAdapter<String> YOG_arrayAdapter = new ArrayAdapter<String>(getContext(),R.layout.option_item, years);
        YOG_CompleteTextView.setText(YOG_arrayAdapter.getItem(0).toString(), false);
        YOG_CompleteTextView.setAdapter(YOG_arrayAdapter);



        AutoCompleteTextView gender_CompleteTextView = view.findViewById(R.id.gender);
        String[] gender = {"Gender", "Male", "Female", "Other"};
        ArrayAdapter<String> gender_arrayAdapter = new ArrayAdapter<String>(getContext(),R.layout.option_item, gender);
        gender_CompleteTextView.setText(gender_arrayAdapter.getItem(0).toString(), false);
        gender_CompleteTextView.setAdapter(gender_arrayAdapter);



    }

}