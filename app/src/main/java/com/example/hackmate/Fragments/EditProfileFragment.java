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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackmate.Adapters.ProjectAdapterEP;
import com.example.hackmate.Adapters.ProjectAdapterMP;
import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.MainActivity;
import com.example.hackmate.Models.ProjectModel;
import com.example.hackmate.POJOClasses.IndividualProject;
import com.example.hackmate.POJOClasses.POST.PatchDetails;
import com.example.hackmate.POJOClasses.POST.PostSkills;
import com.example.hackmate.POJOClasses.ProjectPOJO;
import com.example.hackmate.POJOClasses.Skill;
import com.example.hackmate.POJOClasses.TeamProject;
import com.example.hackmate.POJOClasses.loginPOJO;
import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProfileFragment extends Fragment {

    Button saveButton;
    BottomNavigationView bottomNavigation;
    AutoCompleteTextView YOG_CompleteEditText;
    private RecyclerView projects_recyclerView;
    ImageView profile_pic_EP;
    TextView name_EP, username_EP, email_EP, college_EP, bio_EP, github_EP, linkedIn_EP, personal_website_EP;
    String id;
    private loginAPI loginAPI;
    PatchDetails patchDetails;
    ChipGroup chipGroup;
    PostSkills postSkills;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialise();


        String[] years1 = {"2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
        ArrayAdapter<String> YOG_arrayAdapter1 = new ArrayAdapter<String>(getContext(), R.layout.option_item, years1);

        projects_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        profile_pic_EP.setImageResource(R.drawable.bhavik);

        loginAPI = RetrofitInstance.getRetrofitInstance().create(loginAPI.class);


        Call<loginPOJO> call = loginAPI.getParticipant("Bearer " + MainActivity.getidToken());
        call.enqueue(new Callback<loginPOJO>() {
            @Override
            public void onResponse(Call<loginPOJO> call, Response<loginPOJO> response) {

                Log.i("response22", String.valueOf(response.body().getGraduation_year()));
                name_EP.setText(response.body().getName());
                username_EP.setText(response.body().getUsername());
                email_EP.setText(response.body().getEmail());
                college_EP.setText(response.body().getCollege());
                bio_EP.setText(response.body().getBio());
                github_EP.setText(response.body().getGithub());
                linkedIn_EP.setText(response.body().getLinkedIn());
                id = String.valueOf(response.body().getId());
                if(response.body().getWebsite() != null) {
                    personal_website_EP.setText(response.body().getWebsite());
                }
                Log.i("id22", id);


                YOG_CompleteEditText.setText(String.valueOf(response.body().getGraduation_year()), false);
                YOG_CompleteEditText.setAdapter(YOG_arrayAdapter1);

            }

            @Override
            public void onFailure(Call<loginPOJO> call, Throwable t) {
                Log.i("error", t.getMessage());
                YOG_CompleteEditText.setText("Year of Graduation", false);
                YOG_CompleteEditText.setAdapter(YOG_arrayAdapter1);
            }
        });

        Call<ProjectPOJO> caller = loginAPI.getProject("Bearer " + MainActivity.getidToken());
        Log.i("tag", "tag");
        caller.enqueue(new Callback<ProjectPOJO>() {
            @Override
            public void onResponse(Call<ProjectPOJO> call, Response<ProjectPOJO> response) {
                ProjectPOJO projectPOJO = response.body();
                if (response.body() != null) {
//                        Log.i("abc", projectPOJO.getTeam().getName().toString());
                    List<IndividualProject> individualProjectsList = projectPOJO.getIndividualProjects();
//                        Log.i("pt_skill", String.valueOf(pt_skills.get(0).getParticipant().getName()));
                    List<TeamProject> teamProjectsList = projectPOJO.getTeams();
                    ProjectAdapterEP projectAdapterEP = new ProjectAdapterEP(getContext(), individualProjectsList, teamProjectsList);
                    projects_recyclerView.setAdapter(projectAdapterEP);
                    projectAdapterEP.setGetProjectEP(individualProjectsList, teamProjectsList);
                }

            }

            @Override
            public void onFailure(Call<ProjectPOJO> call, Throwable t) {
                Log.i("error", t.getMessage());
            }
        });

        Call<List<Skill>> call1 = loginAPI.getSkills("Bearer " + MainActivity.getidToken());
        call1.enqueue(new Callback<List<Skill>>() {
            @Override
            public void onResponse(Call<List<Skill>> call1, Response<List<Skill>> response) {
                Log.i("skilssssss", response.body().toString());
                if (response.body() != null) {
                    List<Skill> skillList = response.body();
                    for (int i = 0; i < skillList.size(); i++) {
//                        Log.i("SKILLS", skillList.get(i).getSkill());

                        for (int j = 0; j < chipGroup.getChildCount(); j++) {
                            Chip chip = (Chip) chipGroup.getChildAt(j);

                            if (skillList.get(i).getSkill().equals("ml")) {
                                if (chip.getText().equals("Machine Learning"))
                                    chip.setChecked(true);
                            } else if (skillList.get(i).getSkill().equals("frontend")) {
                                if (chip.getText().equals("Frontend"))
                                    chip.setChecked(true);
                            } else if (skillList.get(i).getSkill().equals("backend")) {
                                if (chip.getText().equals("Backend"))
                                    chip.setChecked(true);
                            } else if (skillList.get(i).getSkill().equals("ui/ux")) {
                                if (chip.getText().equals("UI/UX Design"))
                                    chip.setChecked(true);
                            } else if (skillList.get(i).getSkill().equals("management")) {
                                if (chip.getText().equals("Management"))
                                    chip.setChecked(true);
                            } else if (skillList.get(i).getSkill().equals("appdev")) {
                                if (chip.getText().equals("App Development"))
                                    chip.setChecked(true);
                            }

//                        chip.setChipStrokeColorResource(R.color.pill_color);
//                        chip.setChipBackgroundColor(getResources().getColorStateList(R.color.pill_color));
//                        chip.setTextColor(getResources().getColorStateList(R.color.text));
//                        chip.setChipStrokeWidth(4);
//                        chip.setClickable(false);
//                        chipGroup.addView(chip);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Skill>> call1, Throwable t) {
                Log.i("nhi hua", "nhi hua :((((");
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> skill =  new ArrayList<>();

                for (int i=0; i<chipGroup.getChildCount();i++){
                    Chip chip = (Chip)chipGroup.getChildAt(i);
                    String chipText = chip.getText().toString();
                    if(chip.isChecked()){
                        if(chipText.equals("Machine Learning")){
                            skill.add("ml");
                        }
                        else if(chipText.equals("Frontend")){
                            skill.add("frontend");
                        }
                        else if(chipText.equals("Backend")){
                            skill.add("backend");
                        }
                        else if(chipText.equals("UI/UX Design")){
                            skill.add("ui/ux");
                        }
                        else if(chipText.equals("Management")){
                            skill.add("management");
                        }
                        else if(chipText.equals("App Development")){
                            skill.add("appdev");
                        }
                    }
                    // Do something
                }

                postSkills = new PostSkills(skill);
                postSkills.setSkills(skill);

                Log.i("skills", String.valueOf(skill));

                Log.i("year", YOG_CompleteEditText.getText().toString());

                patchDetails = new PatchDetails((name_EP.getText().toString()), college_EP.getText().toString(),
                        bio_EP.getText().toString(), github_EP.getText().toString(), linkedIn_EP.getText().toString(),
                        personal_website_EP.getText().toString(), username_EP.getText().toString(),2024);
                patchDetails.setName(name_EP.getText().toString());
                patchDetails.setCollege(college_EP.getText().toString());
                patchDetails.setBio(bio_EP.getText().toString());
                patchDetails.setGithub(github_EP.getText().toString());
                patchDetails.setLinkedIn(linkedIn_EP.getText().toString());
                patchDetails.setWebsite(personal_website_EP.getText().toString());
                patchDetails.setUsername(username_EP.getText().toString());
                patchDetails.setGraduation_year(Integer.parseInt(YOG_CompleteEditText.getText().toString()));

                Call<Response<Map<String, String>>> call1 = loginAPI.patchProfile("Bearer " +
                        MainActivity.getidToken(), patchDetails);
                call1.enqueue(new Callback<Response<Map<String, String>>>() {
                    @Override
                    public void onResponse(Call<Response<Map<String, String>>> call1, Response<Response<Map<String, String>>> response) {


                    }

                    @Override
                    public void onFailure(Call<Response<Map<String, String>>> call1, Throwable t) {
                        Toast.makeText(getActivity(), "Error! Please try again later", Toast.LENGTH_SHORT).show();
                    }
                });

                Call<Void> call2 = loginAPI.postSkills("Bearer " +
                        MainActivity.getidToken(), postSkills);
                call2.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call2, Response<Void> response) {
                        Toast.makeText(getActivity(), "Changes saved !!!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call2, Throwable t) {
                        Toast.makeText(getActivity(), "Error! Please try again later", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        bottomNavigation.setVisibility(View.VISIBLE);
    }

    public void initialise() {
        saveButton = getView().findViewById(R.id.saveChangeButton);
        bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);
        YOG_CompleteEditText = getView().findViewById(R.id.year_of_graduation_edit);
        projects_recyclerView = getView().findViewById(R.id.projects_recyclerView_EP);
        profile_pic_EP = getView().findViewById(R.id.profile_pic_EP);
        name_EP = getView().findViewById(R.id.name_EP);
        username_EP = getView().findViewById(R.id.username_EP);
        email_EP = getView().findViewById(R.id.email_EP);
        college_EP = getView().findViewById(R.id.college_EP);
        github_EP = getView().findViewById(R.id.github_EP);
        bio_EP = getView().findViewById(R.id.bio_EP);
        linkedIn_EP = getView().findViewById(R.id.linkedIn_EP);
        personal_website_EP = getView().findViewById(R.id.personal_website_EP);
        chipGroup = getView().findViewById(R.id.chipGroup12);
    }

}
