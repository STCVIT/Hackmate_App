package com.example.hackmate.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackmate.JSONPlaceholders.API;
import com.example.hackmate.LoginActivity;
import com.example.hackmate.MainActivity;
import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DeleteAccountFragment extends Fragment {

    private AppCompatButton deleteButton;
    private FirebaseAuth mAuth;
    private int code;
    private String email;
    private EditText password;
    private API api;

    public DeleteAccountFragment(String email) {
        this.email = email;
    }

    @Override
    public void onStart() {
        super.onStart();
        sendEmailVerificationCode();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        password = view.findViewById(R.id.otpEditText);

        api = RetrofitInstance.getRetrofitInstance().create(API.class);

        deleteButton = (AppCompatButton) view.findViewById(R.id.DeleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().trim().length()==0) {
                    Toast.makeText(getContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email,password.getText().toString().trim())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                View mView = getLayoutInflater().inflate(R.layout.custom_dialog_box, null);
                                TextView dialogText = mView.findViewById(R.id.dialogText);
                                MaterialButton delete = (MaterialButton) mView.findViewById(R.id.signOut);
                                MaterialButton cancel = (MaterialButton) mView.findViewById(R.id.No);

                                dialogText.setText("Are you sure you want to delete ?");
                                delete.setText("DELETE");

                                alert.setView(mView);

                                final AlertDialog alertDialog = alert.create();
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();

                                delete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dismiss();
                                        Call<Void> call = api.delete(MainActivity.getIdToken());
                                        call.enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                if(response.isSuccessful() && response.code()==200) {
                                                    mAuth.signOut();
                                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                                    startActivity(intent);
                                                    getActivity().finish();
                                                    Toast.makeText(getContext(), "Account deleted successfully!!!", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                Toast.makeText(getContext(), "Please try again later !!", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                });

                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dismiss();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(getContext(), "Invalid Password !!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void sendEmailVerificationCode()
    {
        Random random = new Random();
        code = random.nextInt(999999);
    }
}