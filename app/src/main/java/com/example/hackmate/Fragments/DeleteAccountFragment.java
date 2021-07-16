package com.example.hackmate.Fragments;

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

import java.util.Random;


public class DeleteAccountFragment extends Fragment {

    AppCompatButton deleteButton;
    private int code;

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

        deleteButton = (AppCompatButton) view.findViewById(R.id.DeleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Account has been deleted successfully !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sendEmailVerificationCode()
    {
        Random random = new Random();
        code = random.nextInt(999999);
    }
}