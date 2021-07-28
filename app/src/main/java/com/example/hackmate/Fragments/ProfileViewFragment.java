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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackmate.Adapters.ProjectAdapterP;
import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.Models.ProjectModel;
import com.example.hackmate.POJOClasses.GetParticipantPOJO;
import com.example.hackmate.POJOClasses.ProjectPOJO;
import com.example.hackmate.POJOClasses.loginPOJO;
import com.example.hackmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileViewFragment extends Fragment {

    Button invite;
    private RecyclerView projects_recyclerView;
    ChipGroup chipGroup;
    ImageView profile_pic;
    int GET_NAV_CODE = 0;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String idToken, id = "yash";
    private com.example.hackmate.JSONPlaceholders.loginAPI loginAPI;
    Retrofit retrofit;
    TextView name_PV, username_PV, email_PV, college_PV, bio_PV,
            github_PV, linkedIn_PV, personal_website_PV, yog_PV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            GET_NAV_CODE = bundle.getInt("Key", 0);
            id = bundle.getString("id" , "yash");
        }

        initialise();

        if(GET_NAV_CODE==1)
            invite.setVisibility(View.GONE);
        else
            invite.setVisibility(View.VISIBLE);


        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invite.setBackground(getResources().getDrawable(R.drawable.ic_button_border_bg));
                invite.setTextColor(getResources().getColor(R.color.green));
                Toast.makeText(getContext(), "Invite Sent!!", Toast.LENGTH_SHORT).show();
            }
        });

        projects_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ProjectModel model2 = new ProjectModel("Hackmate",
                "Project for team building for hackathons",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Tristique mauris, " +
                        "nec vitae cursus phasellus a proin et. Sit in velit duis iaculis est. " +
                        "At odio sociis venenatis ut commodo. Aliquet eget morbi faucibus nisl " +
                        "nec quis suscipit ut. Mus vestibulum risus at ante lorem volutpat. " +
                        "In vitae vitae, tortor a ipsum ipsum. Ipsum cras eu odio natoque blandit commodo aliquam.",
                "abc@gmail.com", "abc@gmail.com","abc@gmail.com");
        ArrayList arrayList1 = new ArrayList<ProjectModel>();
        arrayList1.add(model2);
        arrayList1.add(model2);
        projects_recyclerView.setAdapter(new ProjectAdapterP(getContext(), arrayList1));

        String[] team_domains = {"App Development", "UI/UX", "Machine Learning"};

        for(int i=0;i<team_domains.length;i++)
        {
            Chip chip = new Chip(getContext());
            chip.setText(team_domains[i]);
            chip.setChipStrokeColorResource(R.color.pill_color);
            chip.setChipBackgroundColor(getResources().getColorStateList(R.color.chip_background_color));
            chip.setTextColor(getResources().getColorStateList(R.color.chip_text_color));
            chip.setChipStrokeWidth(4);
            chip.setClickable(false);
            chipGroup.addView(chip);
        }

        profile_pic.setImageResource(R.drawable.bhavik);

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


                            Call<GetParticipantPOJO> call = loginAPI.getParticipantByID("Bearer " + idToken, id);//replace with participant id getting from previous fragment 60f2c642c28f930015dc3de3
                            call.enqueue(new Callback<GetParticipantPOJO>() {
                                @Override
                                public void onResponse(Call<GetParticipantPOJO> call, Response<GetParticipantPOJO> response) {

                                    Log.i("response22", String.valueOf(response.body().participant.getName()));
                                    name_PV.setText(response.body().participant.getName());
                                    username_PV.setText(response.body().participant.getUsername());
                                    email_PV.setText(response.body().participant.getEmail());
                                    college_PV.setText(response.body().participant.getCollege());
                                    yog_PV.setText(String.valueOf(response.body().participant.getGraduation_year()));
                                    bio_PV.setText(response.body().participant.getBio());
                                    github_PV.setText(response.body().participant.getGithub());
                                    linkedIn_PV.setText(response.body().participant.getLinkedIn());
//                                    id = String.valueOf(response.body().participant.getId());
                                    Call<ProjectPOJO> caller = loginAPI.getProject("Bearer " + idToken, id);
                                    Log.i("tag", "tag");
                                    caller.enqueue(new Callback<ProjectPOJO>() {
                                        @Override
                                        public void onResponse(Call<ProjectPOJO> call, Response<ProjectPOJO> response) {
                                            Log.i("project_response", String.valueOf(response.body()));
                                        }

                                        @Override
                                        public void onFailure(Call<ProjectPOJO> call, Throwable t) {
                                            Log.i("error", t.getMessage());
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Call<GetParticipantPOJO> call, Throwable t) {
                                    Log.i("error", t.getMessage());
                                }
                            });


                        }
                    }
                });
    }


    public void initialise(){

        invite = getView().findViewById(R.id.invite_button);
        projects_recyclerView = getView().findViewById(R.id.projects_recyclerView_P);
        chipGroup = getView().findViewById(R.id.chipGroup112);
        profile_pic = getView().findViewById(R.id.profile_pic_P);
        name_PV = getView().findViewById(R.id.name_PV);
        username_PV = getView().findViewById(R.id.username_PV);
        email_PV = getView().findViewById(R.id.email_PV);
        college_PV = getView().findViewById(R.id.college_PV);
        yog_PV = getView().findViewById(R.id.year_of_graduation_PV);
        bio_PV = getView().findViewById(R.id.bio_PV);
        github_PV = getView().findViewById(R.id.github_PV);
        linkedIn_PV = getView().findViewById(R.id.linkedIn_PV);
        personal_website_PV = getView().findViewById(R.id.personal_website_PV);
    }
}