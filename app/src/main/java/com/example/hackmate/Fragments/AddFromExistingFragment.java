package com.example.hackmate.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hackmate.Adapters.AddFromExistingAdapter;
import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.Models.AddFromExistingModel;
import com.example.hackmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddFromExistingFragment extends Fragment {

    RecyclerView addHackToTeam;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String idToken;
    private loginAPI loginAPI;
    Retrofit retrofit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_from_esisting, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialise();

        addHackToTeam.setLayoutManager(new LinearLayoutManager(getContext()));
        AddFromExistingModel model2 = new AddFromExistingModel("Desiderata", "Hello there",
                "Leader");
        AddFromExistingModel model3 = new AddFromExistingModel("Desiderata", "Hello there",
                "Member");
        ArrayList arrayList1 = new ArrayList<AddFromExistingModel>();
        arrayList1.add(model2);
        arrayList1.add(model3);
        String[][] domains = {{"App Dev","Frontend","Backend"},{"Frontend","Backend"}};
        addHackToTeam.setAdapter(new AddFromExistingAdapter(getContext(), arrayList1, domains));

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient =new OkHttpClient.Builder()
                //.addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(loggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://hackportalbackend.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        mAuth.getCurrentUser().getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            idToken = task.getResult().getToken();
                            Log.i("xx", idToken);

                            loginAPI = retrofit.create(loginAPI.class);


                        }
                    }
                });
    }

    public void initialise(){

        addHackToTeam = getView().findViewById(R.id.addHackToTeam_recyclerView);
    }
}