package com.example.hackmate.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackmate.LoginActivity;
import com.example.hackmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {

    TextView delete,reset;
    LinearLayout signOut;
    private FirebaseAuth mAuth;
    private String email,msg ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signOut = view.findViewById(R.id.signOutButton);
        delete = view.findViewById(R.id.deleteAccountButton);
        reset = view.findViewById(R.id.resetPasswordButton);
        Bundle bundle = new Bundle();
        email = bundle.getString("email"," n");
        Log.i("email",email);

        mAuth = FirebaseAuth.getInstance();

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.custom_dialog_box,null);
                MaterialButton sign_out = (MaterialButton)mView.findViewById(R.id.signOut);
                MaterialButton cancel = (MaterialButton)mView.findViewById(R.id.No);

                alert.setView(mView);

                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();

                sign_out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        mAuth.signOut();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        Toast.makeText(getActivity(), "Sign out successfully!!!", Toast.LENGTH_SHORT).show();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,new DeleteAccountFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.custom_dialog_box,null);
                TextView text = mView.findViewById(R.id.dialogText);
                MaterialButton sign_out = (MaterialButton)mView.findViewById(R.id.signOut);
                MaterialButton cancel = (MaterialButton)mView.findViewById(R.id.No);

                text.setText("Are you sure you want to reset password ?");
                sign_out.setText("RESET");
                alert.setView(mView);

                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();

                sign_out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mAuth.sendPasswordResetEmail("bhavikagrawal0902@gmail.com")
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                            msg = "Reset link sent to your email !!!";
                                        else
                                            msg = "Unable to send reset link...Please try again later!!";

                                        Snackbar.make(v,msg,Snackbar.LENGTH_SHORT)
                                                .setAction("Action",null)
                                                .setBackgroundTint(Color.parseColor("#DAED10"))
                                                .setTextColor(Color.BLACK)
                                                .show();
                                    }
                                });

                        alertDialog.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                //Toast.makeText(getActivity(), "Reset Link will be sent to your registered email ID !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        BottomNavigationView bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigation.setVisibility(View.VISIBLE);
    }
}