package com.example.hackmate.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.LoginActivity;
import com.example.hackmate.POJOClasses.POST.LoginEmail;
import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpAccountFragment extends Fragment {

    View view;
    private EditText email_create_your_account, password_create_your_account, password1_create_your_account;
    private Button login;
    private LoginActivity loginActivity;
    private FirebaseAuth mAuth;
    private loginAPI loginAPI;
    String idToken;
    LoginEmail loginEmail;
    private static final String TAG = "Sign Up account Fragment";
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up_account, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialise();
        progressBar.setVisibility(View.GONE);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailText = email_create_your_account.getText().toString();
                String password = password_create_your_account.getText().toString();
                String password1 = password1_create_your_account.getText().toString();

                progressBar.setVisibility(View.VISIBLE);
                login.setEnabled(false);

                if (email_create_your_account.getText().toString().isEmpty()) {
                    email_create_your_account.setError("Email Required");
                    email_create_your_account.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email_create_your_account.getText().toString()).matches()) {
                    email_create_your_account.setError("Valid Email Required");
                    email_create_your_account.requestFocus();
                    return;
                }
                if (password_create_your_account.getText().toString().isEmpty()) {
                    password_create_your_account.setError("Password Required");
                    password_create_your_account.requestFocus();
                    return;
                }
                if (password_create_your_account.getText().toString().length() < 6) {
                    password_create_your_account.setError("Min 6 char required");
                    password_create_your_account.requestFocus();
                    return;
                }
                if (password1_create_your_account.getText().toString().isEmpty() || !password1_create_your_account.getText().toString().equals(password_create_your_account.getText().toString())) {
                    password1_create_your_account.setError("Password does not matches !!");
                    password1_create_your_account.requestFocus();
                    return;
                }

//                loginEmail.setEmail(emailText);
//                loginEmail.setPassword(password);


                registerUser(emailText, password);


            }
        });

    }

    public void initialise() {
        email_create_your_account = view.findViewById(R.id.email_create_your_account);
        password1_create_your_account = view.findViewById(R.id.password1_create_your_account);
        password_create_your_account = view.findViewById(R.id.password_create_your_account);
        mAuth = FirebaseAuth.getInstance();
        loginActivity = (LoginActivity) getActivity();
        login = view.findViewById(R.id.login);
        progressBar = view.findViewById(R.id.progressBar6);
    }

    public void registerUser(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                mAuth.getCurrentUser().getIdToken(true)
                                        .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                            public void onComplete(@NonNull Task<GetTokenResult> task) {
                                                if (task.isSuccessful()) {
                                                    idToken = task.getResult().getToken();
                                                    Log.i("xx", idToken);

                                                    loginAPI = RetrofitInstance.getRetrofitInstance().create(loginAPI.class);
                                                    Call<Response<Map<String, String>>> call = loginAPI.setClaim("Bearer " + idToken);

                                                    call.enqueue(new Callback<Response<Map<String, String>>>() {
                                                        @Override
                                                        public void onResponse(Call<Response<Map<String, String>>> call, Response<Response<Map<String, String>>> response) {
                                                            Log.e(TAG, "onResponse: "+response.code());
                                                            progressBar.setVisibility(View.GONE);
                                                            login.setEnabled(true);
                                                            FragmentManager fragmentManager = loginActivity.getSupportFragmentManager();
                                                            Bundle args = new Bundle();
                                                            args.putString("email", email_create_your_account.getText().toString());
                                                            loginActivity.fragmentLogin.setArguments(args);
                                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                            fragmentTransaction.replace(R.id.bodyFragment, loginActivity.fragmentLogin).commit();

                                                            email_create_your_account.setText("");
                                                            password_create_your_account.setText("");
                                                            password1_create_your_account.setText("");
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Response<Map<String, String>>> call, Throwable t) {
                                                            Log.e(TAG, "onFailure: " + t.getMessage());
                                                        }
                                                    });


                                                }
                                            }
                                        });
                            }
                        });
                    } else {
                        Toast.makeText(loginActivity, "Couldn't create account!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}