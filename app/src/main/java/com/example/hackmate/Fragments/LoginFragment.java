package com.example.hackmate.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackmate.LoginActivity;
import com.example.hackmate.MainActivity;
import com.example.hackmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {

    View view;
    private EditText email_login, password_login;
    private TextView newAccount;
    private LoginActivity loginActivity;
    private FirebaseAuth mAuth;
    private Button login;
    private ProgressBar progressBar;

    @Override
    public void onStart() {
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {

            if (!mAuth.getCurrentUser().isEmailVerified()) {
                Toast.makeText(getContext(), "Please Verify your Email to continue", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();

            } else {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                loginActivity.finish();
            }
        }
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialise();

        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = loginActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.bodyFragment, loginActivity.fragmentNewAccount)
                        .addToBackStack(null)
                        .commit();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar = view.findViewById(R.id.progressBar);

                String emailText = email_login.getText().toString();
                String passWord = password_login.getText().toString();

                if (email_login.getText().toString().isEmpty()) {
                    email_login.setError("Email Required");
                    email_login.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email_login.getText().toString()).matches()) {
                    email_login.setError("Valid Email Required");
                    email_login.requestFocus();
                    return;
                }
                if (password_login.getText().toString().isEmpty()) {
                    password_login.setError("Password Required");
                    password_login.requestFocus();
                    return;
                }
                if (password_login.getText().toString().length() < 6) {
                    password_login.setError("Min 6 char required");
                    password_login.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                loginUser(emailText, passWord);


            }
        });

    }

    public void initialise()
    {
        newAccount = view.findViewById(R.id.new_account);
        email_login = view.findViewById(R.id.email_login);
        password_login = view.findViewById(R.id.password_login);
        mAuth = FirebaseAuth.getInstance();
        loginActivity = (LoginActivity) getActivity();
        login = view.findViewById(R.id.login_button);
    }

    public void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (!task.isSuccessful()) {
                        Toast.makeText(getContext(), "Invalid Email / Password", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                    } else {
                        checkIfEmailVerified(email);
                    }
                });
    }

    private void checkIfEmailVerified(String email)
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
            FirebaseAuth.getInstance().signOut();

            //restart this activity

        }
    }


}