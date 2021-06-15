package com.example.hackmate.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hackmate.LoginActivity;
import com.example.hackmate.MainActivity;
import com.example.hackmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class GettingStartedFragment extends Fragment {

    View view;
    Button create_profile;
    LoginActivity loginActivity;
    private EditText email_create_your_account;

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

        initialise();
        String email = getArguments().getString("email");

        create_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkIfEmailVerified(email);

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

    public void initialise()
    {
        create_profile = view.findViewById(R.id.createProfile_button);
        loginActivity = (LoginActivity) getActivity();
//        email_create_your_account = view.findViewById(R.id.email_create_account);
    }

    public void checkIfEmailVerified(String email)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        if (user.isEmailVerified())
        {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra("Email", email);
            startActivity(intent);
            loginActivity.finish();
            Toast.makeText(getContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
            Toast.makeText(getContext(), "Please Verify your Email to continue", Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = loginActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.bodyFragment, loginActivity.fragmentLogin)
                    .addToBackStack(null)
                    .commit();
            FirebaseAuth.getInstance().signOut();

            //restart this activity

        }
    }

}