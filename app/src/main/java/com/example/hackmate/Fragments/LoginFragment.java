package com.example.hackmate.Fragments;

import static com.example.hackmate.LoginActivity.EMAIL;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.LoginActivity;
import com.example.hackmate.MainActivity;
import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginFragment extends Fragment {

    View view;
    int ans;
    String idToken, msg;
    private EditText email_login, password_login;
    private TextView newAccount, forgot_password;
    private LoginActivity loginActivity;
    private FirebaseAuth mAuth;
    private Button login;
    private ProgressBar progressBar;
    private loginAPI loginAPI;

    @Override
    public void onStart() {
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {

            if (!mAuth.getCurrentUser().isEmailVerified()) {
                Toast.makeText(getContext(), "Please Verify your Email to continue", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();

            } else if (loginActivity.preferences.getInt("response", 0) == 200) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("idToken", LoginActivity.getidToken());
                startActivity(intent);
                loginActivity.finish();
            } else if (loginActivity.preferences.getInt("response", 0) == 404) {
                Bundle args = new Bundle();
                args.putString("response", LoginActivity.getidToken());
                loginActivity.fragmentCreateAccount.setArguments(args);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.bodyFragment, loginActivity.fragmentCreateAccount)
                        .addToBackStack(null)
                        .commit();
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

        newAccount.setOnClickListener(v -> {

            setEnableFalse();

            FragmentManager fragmentManager = loginActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.bodyFragment, loginActivity.fragmentNewAccount)
                    .addToBackStack(null)
                    .commit();

        });

        login.setOnClickListener(v -> {


            String emailText = email_login.getText().toString();
            String passWord = password_login.getText().toString();

            setEnableFalse();

            if (email_login.getText().toString().isEmpty()) {
                email_login.setError("Email Required");
                email_login.requestFocus();
                setEnableTrue();
                return;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email_login.getText().toString()).matches()) {
                email_login.setError("Valid Email Required");
                email_login.requestFocus();
                setEnableTrue();
                return;
            } else if (password_login.getText().toString().isEmpty()) {
                password_login.setError("Password Required");
                password_login.requestFocus();
                setEnableTrue();
                return;
            } else if (password_login.getText().toString().length() < 6) {
                password_login.setError("Min 6 char required");
                password_login.requestFocus();
                setEnableTrue();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            loginUser(emailText, passWord);

        });

        forgot_password.setOnClickListener(v -> {
                progressBar.setVisibility(View.VISIBLE);
            setEnableFalse();
            Pattern p = Pattern.compile(EMAIL);
            Matcher link = p.matcher(email_login.getText().toString());
            if(!link.find()){
                email_login.setError("Please Enter Valid Email!!");
                email_login.requestFocus();
                setEnableTrue();
                progressBar.setVisibility(View.GONE);
                return;
            }
            if (email_login.getText().toString().trim().length() > 0) {
                mAuth.sendPasswordResetEmail(email_login.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                msg = "Reset link sent to your email !!!";

                            }else
                                msg = "Unable to send reset link...Please try again later!!";

                            Snackbar.make(v, msg, Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null)
                                    .setBackgroundTint(Color.parseColor("#DAED10"))
                                    .setTextColor(Color.BLACK)
                                    .show();

                                progressBar.setVisibility(View.GONE);
                            setEnableTrue();
                        });
            }
            else{
                    Toast.makeText(getActivity(), "Please Enter Email and click on Forgot Password Again!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                setEnableTrue();
            }
        });

    }

    public void initialise() {
        newAccount = view.findViewById(R.id.new_account);
        email_login = view.findViewById(R.id.email_login);
        password_login = view.findViewById(R.id.password_login);
        mAuth = FirebaseAuth.getInstance();
        login = view.findViewById(R.id.login_button);
        loginActivity = (LoginActivity) getActivity();
        loginAPI = RetrofitInstance.getRetrofitInstance().create(loginAPI.class);
        forgot_password = view.findViewById(R.id.forgot_password);
        progressBar = view.findViewById(R.id.progressBar);
    }

    public void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (!task.isSuccessful()) {
                        Toast.makeText(getContext(), "Invalid Email / Password", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        setEnableTrue();

                    } else {
                        mAuth.getCurrentUser().getIdToken(true)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        idToken = task1.getResult().getToken();
                                        Log.i("xx", idToken);


                                        Call<Response<Void>> call = loginAPI.getLoginStatus("Bearer " + idToken);
                                        call.enqueue(new Callback<Response<Void>>() {
                                            @Override
                                            public void onResponse(Call<Response<Void>> call, Response<Response<Void>> response) {

                                                ans = response.code();
                                                Log.i("response code", response.toString());
                                                Log.i("ans", String.valueOf(ans));
                                                checkIfEmailVerified(email, ans);
                                            }

                                            @Override
                                            public void onFailure(Call<Response<Void>> call, Throwable t) {
                                                Log.i("error", t.getMessage());
                                                FirebaseAuth.getInstance().signOut();
                                                progressBar.setVisibility(View.GONE);
                                                setEnableTrue();
                                            }
                                        });


                                    }
                                });
                    }
                });
    }

    private void checkIfEmailVerified(String email, int responses) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        if (user.isEmailVerified()) {
            if (responses == 404) {

                loginActivity.preferences.edit().putInt("response", responses).apply();
                Log.i("responsesxx", String.valueOf(loginActivity.preferences.getInt("response", 0)));
                Bundle args = new Bundle();
                args.putString("email", email);
                loginActivity.fragmentCreateAccount.setArguments(args);
                setEnableTrue();
                assert getFragmentManager() != null;
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.bodyFragment, loginActivity.fragmentCreateAccount)
                        .addToBackStack(null)
                        .commit();
            } else if (responses == 200) {
                loginActivity.preferences.edit().putInt("response", responses).apply();
                Log.i("responsesxx", String.valueOf(loginActivity.preferences.getInt("response", 0)));
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("idToken", idToken);
                setEnableTrue();
                startActivity(intent);
                loginActivity.finish();
                Toast.makeText(getContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();

            }
        } else {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
            Toast.makeText(getContext(), "Please Verify your Email to continue", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            progressBar.setVisibility(View.GONE);
            setEnableTrue();

            //restart this activity

        }
    }

    public void setEnableFalse(){
        email_login.setEnabled(false);
        password_login.setEnabled(false);
        login.setEnabled(false);
        forgot_password.setEnabled(false);
        newAccount.setEnabled(false);
    }

    public void setEnableTrue(){
        email_login.setEnabled(true);
        password_login.setEnabled(true);
        login.setEnabled(true);
        forgot_password.setEnabled(true);
        newAccount.setEnabled(true);
    }

}