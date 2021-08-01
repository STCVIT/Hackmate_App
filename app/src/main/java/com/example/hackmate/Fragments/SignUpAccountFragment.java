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
import android.widget.Toast;

import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.LoginActivity;
import com.example.hackmate.POJOClasses.loginPOJO;
import com.example.hackmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SignUpAccountFragment extends Fragment {

    View view;
    private EditText email_create_your_account, password_create_your_account, password1_create_your_account;
    private Button login;
    private LoginActivity loginActivity;
    private FirebaseAuth mAuth;
    String idToken = "Bearer ";
    int responses, ans;
    private loginAPI loginAPI;
    Retrofit retrofit;

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

        retrofit = new Retrofit.Builder()
                .baseUrl("https://hackportalbackend.herokuapp.com/participant/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailText = email_create_your_account.getText().toString();
                String password = password_create_your_account.getText().toString();
                String password1 = password1_create_your_account.getText().toString();


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

                registerUser(emailText, password);


            }
        });

    }

    public void initialise() {
        email_create_your_account = view.findViewById(R.id.email_create_account);
        password1_create_your_account = view.findViewById(R.id.password1_create_account);
        password_create_your_account = view.findViewById(R.id.password_create_account);
        mAuth = FirebaseAuth.getInstance();
        loginActivity = (LoginActivity) getActivity();
        login = view.findViewById(R.id.login);
    }

    public void registerUser(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Please verify email to continue", Toast.LENGTH_SHORT).show();

                                mAuth.getCurrentUser().getIdToken(true)
                                        .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                            public void onComplete(@NonNull Task<GetTokenResult> task) {
                                                if (task.isSuccessful()) {
                                                    idToken += task.getResult().getToken();
                                                    Log.i("xx", idToken);
                                                    // Send token to your backend via HTTPS
                                                    // ...

                                                    loginAPI = retrofit.create(loginAPI.class);

                                                    responses = NewAccount();
                                                    Log.i("response", String.valueOf(responses));

                                                    FragmentManager fragmentManager = loginActivity.getSupportFragmentManager();
                                                    Bundle args = new Bundle();
                                                    args.putString("email", email_create_your_account.getText().toString());
                                                    loginActivity.fragmentLogin.setArguments(args);
                                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                    fragmentTransaction.replace(R.id.bodyFragment, loginActivity.fragmentLogin)
                                                            .addToBackStack(null)
                                                            .commit();
                                                }
                                            }
                                        });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("failure", "Email not sent" + e.getMessage());
                            }
                        });

                    } else {
                        Toast.makeText(getContext(), "Email ID already exists..", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public int NewAccount() {

        Call<Response<Void>> call = loginAPI.getLoginStatus("Bearer " + idToken);
        call.enqueue(new Callback<Response<Void>>() {
            @Override
            public void onResponse(Call<Response<Void>> call, Response<Response<Void>> response) {
                ans = response.code();
                Log.i("response code", String.valueOf(ans));
            }

            @Override
            public void onFailure(Call<Response<Void>> call, Throwable t) {
                Log.i("error", t.getMessage());
            }
        });

        return ans;
    }

}