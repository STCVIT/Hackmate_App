package com.example.hackmate.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hackmate.Adapters.ProjectAdapterMP;
import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.Models.ProjectModel;
import com.example.hackmate.POJOClasses.ProjectPOJO;
import com.example.hackmate.POJOClasses.loginPOJO;
import com.example.hackmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class MyProfileFragment extends Fragment {

    BottomNavigationView bottomNavigation;
    ImageView settingsImageView, addImageView, profile_pic;
    TextView editProfileTextView, addProjectTextView, name_MP, username_MP, email_MP, college_MP, bio_MP,
    github_MP, linkedIn_MP, personal_website_MP, yog_MP;
    ConstraintLayout add_project_constraint;
    CardView add_project_card;
    private RecyclerView projects_recyclerView;
    ChipGroup chipGroup;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String idToken, id = "yash";
    private loginAPI loginAPI;
    Retrofit retrofit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialise();

        settingsImageView.setOnClickListener(v -> settingsFrag());

        //editProfileImageView.setOnClickListener(v -> editProfileFrag());

        editProfileTextView.setOnClickListener(v -> editProfileFrag());

        add_project_card.setOnClickListener(v -> addProjectFrag());

        add_project_constraint.setOnClickListener(v -> addProjectFrag());

        addProjectTextView.setOnClickListener(v -> addProjectFrag());

        addImageView.setOnClickListener(v -> addProjectFrag());

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


                            Call<loginPOJO> call = loginAPI.getParticipant("Bearer " + idToken);
                            call.enqueue(new Callback<loginPOJO>() {
                                @Override
                                public void onResponse(Call<loginPOJO> call, Response<loginPOJO> response) {

                                    Log.i("response22", String.valueOf(response.body().getId()));
                                    name_MP.setText(response.body().getName());
                                    username_MP.setText(response.body().getUsername());
                                    email_MP.setText(response.body().getEmail());
                                    college_MP.setText(response.body().getCollege());
                                    yog_MP.setText(String.valueOf(response.body().getGraduation_year()));
                                    bio_MP.setText(response.body().getBio());
                                    github_MP.setText(response.body().getGithub());
                                    linkedIn_MP.setText(response.body().getLinkedIn());
                                    id = String.valueOf(response.body().getId());
                                    Call<ProjectPOJO> caller = loginAPI.getProject("Bearer " + idToken, id);
                                    Log.i("tag" , "tag");
                                    caller.enqueue(new Callback<ProjectPOJO>() {
                                        @Override
                                        public void onResponse(Call<ProjectPOJO> call, Response<ProjectPOJO> response) {
                                            Log.i("project_response" , String.valueOf(response.body()));
                                        }

                                        @Override
                                        public void onFailure(Call<ProjectPOJO> call, Throwable t) {
                                            Log.i("error" , t.getMessage());
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Call<loginPOJO> call, Throwable t) {
                                    Log.i("error", t.getMessage());
                                }
                            });




//                            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//                            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//                            OkHttpClient okHttpClient =new OkHttpClient.Builder()
//                                    //.addInterceptor(loggingInterceptor)
//                                    .addNetworkInterceptor(loggingInterceptor)
//                                    .build();
//
//                            retrofit = new Retrofit.Builder()
//                                    .baseUrl("https://hackportalbackend.herokuapp.com/")
//                                    .addConverterFactory(GsonConverterFactory.create())
//                                    .client(okHttpClient)
//                                    .build();




<<<<<<< Updated upstream
=======
        Call<loginPOJO> call = loginAPI.getParticipant("Bearer " + MainActivity.getIdToken());
        call.enqueue(new Callback<loginPOJO>() {
            @Override
            public void onResponse(Call<loginPOJO> call, Response<loginPOJO> response) {

//                Log.i("response22", String.valueOf(response.body().getId()));
                name_MP.setText(response.body().getName());
                username_MP.setText(response.body().getUsername());
                email_MP.setText(response.body().getEmail());
                college_MP.setText(response.body().getCollege());
                yog_MP.setText(String.valueOf(response.body().getGraduation_year()));
                bio_MP.setText(response.body().getBio());
                github_MP.setText(response.body().getGithub());
                linkedIn_MP.setText(response.body().getLinkedIn());
                id = String.valueOf(response.body().getId());
                if (response.body().getWebsite() != null) {
                    personal_website_MP.setText(response.body().getWebsite());
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<loginPOJO> call, Throwable t) {
                Log.i("error", t.getMessage());
                Toast.makeText(getActivity(), "Failed To Fetch", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        progressBar.setVisibility(View.VISIBLE);

        Call<ProjectPOJO> caller = loginAPI.getProject("Bearer " + MainActivity.getIdToken());
        Log.i("tag", "tag");
        caller.enqueue(new Callback<ProjectPOJO>() {
            @Override
            public void onResponse(Call<ProjectPOJO> call, Response<ProjectPOJO> response) {
                Log.i("project_response", String.valueOf(response.body()));
                if (response.body() != null) {
                    ProjectPOJO projectPOJO = response.body();
//                        Log.i("abc", projectPOJO.getTeam().getName().toString());
                    List<IndividualProject> individualProjectsList = projectPOJO.getIndividualProjects();
//                        Log.i("pt_skill", String.valueOf(pt_skills.get(0).getParticipant().getName()));
                    List<TeamProject> teamProjectsList = projectPOJO.getTeams();
                    ProjectAdapterMP projectAdapterMP = new ProjectAdapterMP(getContext(), individualProjectsList, teamProjectsList);
                    projects_recyclerView.setAdapter(projectAdapterMP);
                    projectAdapterMP.setGetProjectMP(individualProjectsList, teamProjectsList);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ProjectPOJO> call, Throwable t) {
                Log.i("error", t.getMessage());
                Toast.makeText(getActivity(), "Failed To Fetch", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        progressBar.setVisibility(View.VISIBLE);

        Call<List<Skill>> call1 = loginAPI.getSkills("Bearer " + MainActivity.getIdToken());
        call1.enqueue(new Callback<List<Skill>>() {
            @Override
            public void onResponse(Call<List<Skill>> call1, Response<List<Skill>> response) {
                if (response.body() != null) {
                    Log.i("skilssssss", response.body().toString());
                    if (response.body() != null) {
                        List<Skill> skillList = response.body();
                        for (int i = 0; i < skillList.size(); i++) {
                            Log.i("SKILLS", skillList.get(i).getSkill());

                            Chip chip = new Chip(getContext());
                            if (skillList.get(i).getSkill().equals("ml")) {
                                chip.setText("Machine Learning");
                            } else if (skillList.get(i).getSkill().equals("frontend")) {
                                chip.setText("Frontend");
                            } else if (skillList.get(i).getSkill().equals("backend")) {
                                chip.setText("Backend");
                            } else if (skillList.get(i).getSkill().equals("ui/ux")) {
                                chip.setText("UI/UX Design");
                            } else if (skillList.get(i).getSkill().equals("management")) {
                                chip.setText("Management");
                            } else if (skillList.get(i).getSkill().equals("appdev")) {
                                chip.setText("App Development");
                            }
                            chip.setChipStrokeColorResource(R.color.pill_color);
                            chip.setChipBackgroundColor(getResources().getColorStateList(R.color.pill_color));
                            chip.setTextColor(getResources().getColorStateList(R.color.text));
                            chip.setChipStrokeWidth(4);
                            chip.setClickable(false);
                            chipGroup.addView(chip);
>>>>>>> Stashed changes
                        }
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
                "abc@gmail.com", "abc@gmail.com", "abc@gmail.com");
        ArrayList arrayList1 = new ArrayList<ProjectModel>();
        arrayList1.add(model2);
        arrayList1.add(model2);
        projects_recyclerView.setAdapter(new ProjectAdapterMP(getContext(), arrayList1));

        String[] team_domains = {"App Development", "UI/UX"};

        for (int i = 0; i < team_domains.length; i++) {
            Chip chip = new Chip(getContext());
            chip.setText(team_domains[i]);
            chip.setChipStrokeColorResource(R.color.pill_color);
            chip.setChipBackgroundColor(getResources().getColorStateList(R.color.pill_color));
            chip.setTextColor(getResources().getColorStateList(R.color.text));
            chip.setChipStrokeWidth(4);
            chip.setClickable(false);
            chipGroup.addView(chip);
        }

        profile_pic.setImageResource(R.drawable.bhavik);
    }

    public void initialise() {
        settingsImageView = getView().findViewById(R.id.settings_image);
        //editProfileImageView = getView().findViewById(R.id.edit_profile_image);
        addImageView = getView().findViewById(R.id.add_image);
        editProfileTextView = getView().findViewById(R.id.edit_profile_click);
        addProjectTextView = getView().findViewById(R.id.add_a_project);
        add_project_constraint = getView().findViewById(R.id.add_project_constraint);
        add_project_card = getView().findViewById(R.id.cardView7);
        bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);
        projects_recyclerView = getView().findViewById(R.id.projects_recyclerView_MP);
        chipGroup = getView().findViewById(R.id.skills_MP);
        profile_pic = getView().findViewById(R.id.profile_pic_MP);
        name_MP = getView().findViewById(R.id.name_MP);
        username_MP = getView().findViewById(R.id.username_MP);
        email_MP = getView().findViewById(R.id.email_MP);
        college_MP = getView().findViewById(R.id.college_MP);
        yog_MP = getView().findViewById(R.id.year_of_graduation_MP);
        bio_MP = getView().findViewById(R.id.bio_MP);
        github_MP = getView().findViewById(R.id.github_MP);
        linkedIn_MP = getView().findViewById(R.id.linkedIn_MP);
        personal_website_MP = getView().findViewById(R.id.personal_website_MP);

    }

    public void editProfileFrag() {
        bottomNavigation.setVisibility(View.GONE);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, new EditProfileFragment())
                .addToBackStack(null)
                .commit();
    }

    public void settingsFrag() {
        bottomNavigation.setVisibility(View.GONE);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, new SettingsFragment())
                .addToBackStack(null)
                .commit();
    }

    public void addProjectFrag() {
        AddProjectFragment frag = new AddProjectFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("Key", 1);
        frag.setArguments(bundle);

        bottomNavigation.setVisibility(View.GONE);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, frag)
                .addToBackStack(null)
                .commit();
    }
}