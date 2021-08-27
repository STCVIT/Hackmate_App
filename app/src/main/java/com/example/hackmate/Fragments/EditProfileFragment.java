package com.example.hackmate.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.Adapters.ProjectAdapterEP;
import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.IndividualProject;
import com.example.hackmate.POJOClasses.POST.PatchDetails;
import com.example.hackmate.POJOClasses.POST.PostSkills;
import com.example.hackmate.POJOClasses.ProjectPOJO;
import com.example.hackmate.POJOClasses.Skill;
import com.example.hackmate.POJOClasses.TeamProject;
import com.example.hackmate.POJOClasses.loginPOJO;
import com.example.hackmate.R;
import com.example.hackmate.util.ClickListener;
import com.example.hackmate.util.Constants;
import com.example.hackmate.util.Functions;
import com.example.hackmate.util.RecyclerTouchListener;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import static com.example.hackmate.LoginActivity.URL_REGEX;

public class EditProfileFragment extends Fragment {
    private static final String TAG = "EditProfileFragment";
    Button saveButton;
    BottomNavigationView bottomNavigation;
    AutoCompleteTextView YOG_CompleteEditText;
    ImageView profile_pic_EP;
    TextView name_EP, username_EP, email_EP, college_EP, bio_EP, github_EP, linkedIn_EP, personal_website_EP;
    String id;
    PatchDetails patchDetails;
    ChipGroup chipGroup;
    PostSkills postSkills;
    ArrayAdapter<String> YOG_arrayAdapter1;
    private RecyclerView projects_recyclerView;
    private loginAPI loginAPI;
    private ProgressBar progressBar;

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
        YOG_arrayAdapter1 = new ArrayAdapter<String>(requireContext(), R.layout.option_item, years1);
        YOG_CompleteEditText.setAdapter(YOG_arrayAdapter1);

        projects_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        profile_pic_EP.setImageResource(R.drawable.bhavik);

        loginAPI = RetrofitInstance.getRetrofitInstance().create(loginAPI.class);
        fetchData(view);
    }

    void fetchData(View view) {
        Call<loginPOJO> call = loginAPI.getParticipant("Bearer " + MainActivity.getIdToken());
        call.enqueue(new Callback<loginPOJO>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<loginPOJO> call, Response<loginPOJO> response) {
                if (response.code() == 401)
                    Functions.fetchToken(requireActivity(), () -> fetchData(view));
                Log.i(TAG, String.valueOf(response.body().getGraduation_year()));
                name_EP.setText(response.body().getName());
                username_EP.setText(response.body().getUsername());
                email_EP.setText(response.body().getEmail());
                college_EP.setText(response.body().getCollege());
                bio_EP.setText(response.body().getBio());
                github_EP.setText(response.body().getGithub());
                linkedIn_EP.setText(response.body().getLinkedIn());
                id = String.valueOf(response.body().getId());
                if (response.body().getWebsite() != null) {
                    personal_website_EP.setText(response.body().getWebsite());
                }
                Log.i("id22", id);
                YOG_CompleteEditText.setText(String.valueOf(response.body().getGraduation_year()), false);
                YOG_CompleteEditText.setAdapter(YOG_arrayAdapter1);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<loginPOJO> call, Throwable t) {
                Log.i("error", t.getMessage());
                YOG_CompleteEditText.setText("Year of Graduation", false);
                YOG_CompleteEditText.setAdapter(YOG_arrayAdapter1);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Failed To Fetch!", Toast.LENGTH_SHORT).show();
            }
        });

        progressBar.setVisibility(View.VISIBLE);

        Call<ProjectPOJO> caller = loginAPI.getProject("Bearer " + MainActivity.getIdToken());
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
                    projects_recyclerView.addOnItemTouchListener(new RecyclerTouchListener(requireContext(), projects_recyclerView, new ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            Log.e(TAG, "onClick: single tap");
                        }

                        @Override
                        public void onLongClick(View view, int position) {
                            if (position < individualProjectsList.size()) {
                                Log.e(TAG, "onLongClick: long tap" + position + "=>" + individualProjectsList.get(position).get_ids());

                                Call<Map<String, Object>> call1 = loginAPI.delProject("Bearer " + MainActivity.getIdToken(),
                                        individualProjectsList.get(position).get_ids());
                                call1.enqueue(new Callback<Map<String, Object>>() {
                                    @Override
                                    public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                                        Log.i(TAG, "Del proj" + String.valueOf(response.body()));
                                        individualProjectsList.remove(position);
                                        projectAdapterEP.notifyItemRemoved(position);

                                    }

                                    @Override
                                    public void onFailure(Call<Map<String, Object>> call, Throwable t) {

                                    }
                                });
                            } else {
                                Log.e(TAG, "onLongClick: long tap on team" + position);
                                Toast.makeText(getActivity(), "Team projects cannot be deleted from here!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }));
                    projectAdapterEP.setGetProjectEP(individualProjectsList, teamProjectsList);
                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ProjectPOJO> call, Throwable t) {
                Log.i("error", t.getMessage());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Failed To Fetch!", Toast.LENGTH_SHORT).show();
            }
        });

        progressBar.setVisibility(View.VISIBLE);

        Call<List<Skill>> call1 = loginAPI.getSkills("Bearer " + MainActivity.getIdToken());
        call1.enqueue(new Callback<List<Skill>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<List<Skill>> call1, Response<List<Skill>> response) {
                if (response.body() != null) {
                    List<Skill> skillList = response.body();
                    for (Skill skill : skillList) {
//                        Log.i("SKILLS", skillList.get(i).getSkill());
                        for (int j = 0; j < chipGroup.getChildCount(); j++) {
                            Chip chip = (Chip) chipGroup.getChildAt(j);
                            if (Constants.skills.get(skill.getSkill())
                                    .equalsIgnoreCase(chip.getText().toString())) {
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
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Skill>> call1, Throwable t) {
                Log.i(TAG, "nhi hua :((((");
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Failed To Fetch!", Toast.LENGTH_SHORT).show();
            }
        });
        saveButton.setOnClickListener(v -> {
            List<String> skill = new ArrayList<>();
            for (int id : chipGroup.getCheckedChipIds()) {
                Chip chip = view.findViewById(id);
                skill.add(Constants.skills.get(chip.getText().toString()));
            }

            Pattern p = Pattern.compile(URL_REGEX);
            Matcher link = p.matcher(linkedIn_EP.getText().toString());//replace with string to compare
            Matcher git = p.matcher(github_EP.getText().toString());
            Matcher web = p.matcher(personal_website_EP.getText().toString());

            if (linkedIn_EP.getText().toString().isEmpty() ) {
                linkedIn_EP.setText("--");
            }
            if(!link.find()){
                linkedIn_EP.setError("Please Enter Valid linkedIn link!!");
                linkedIn_EP.requestFocus();
                return;
            }
            if (github_EP.getText().toString().isEmpty()) {
                github_EP.setError("Github Link is Required");
                github_EP.requestFocus();
                return;
            }
            if(!git.find()){
                github_EP.setError("Please Enter Valid Github Link link!!");
                github_EP.requestFocus();
                return;
            }
            if (personal_website_EP.getText().toString().isEmpty()) {
                personal_website_EP.setText("--");
            }
            if(!web.find()){
                personal_website_EP.setError("Please Enter Valid website link!!");
                personal_website_EP.requestFocus();
                return;
            }

            postSkills = new PostSkills(skill);
            postSkills.setSkills(skill);

            Log.i("skills", String.valueOf(skill));

            Log.i("year", YOG_CompleteEditText.getText().toString());

            patchDetails = new PatchDetails((name_EP.getText().toString()), college_EP.getText().toString(),
                    bio_EP.getText().toString(), github_EP.getText().toString(), linkedIn_EP.getText().toString(),
                    personal_website_EP.getText().toString(), username_EP.getText().toString(), 2024);
            patchDetails.setName(name_EP.getText().toString());
            patchDetails.setCollege(college_EP.getText().toString());
            patchDetails.setBio(bio_EP.getText().toString());
            patchDetails.setGithub(github_EP.getText().toString());
            patchDetails.setLinkedIn(linkedIn_EP.getText().toString());
            patchDetails.setWebsite(personal_website_EP.getText().toString());
            patchDetails.setUsername(username_EP.getText().toString());
            patchDetails.setGraduation_year(Integer.parseInt(YOG_CompleteEditText.getText().toString()));

            Call<Response<Map<String, String>>> call11 = loginAPI.patchProfile("Bearer " +
                    MainActivity.getIdToken(), patchDetails);
            call11.enqueue(new Callback<Response<Map<String, String>>>() {
                @Override
                public void onResponse(Call<Response<Map<String, String>>> call11, Response<Response<Map<String, String>>> response) {

                }

                @Override
                public void onFailure(Call<Response<Map<String, String>>> call11, Throwable t) {
                    Toast.makeText(getActivity(), "Error! Please try again later", Toast.LENGTH_SHORT).show();
                }
            });

            Call<Void> call2 = loginAPI.postSkills("Bearer " +
                    MainActivity.getIdToken(), postSkills);
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
        YOG_CompleteEditText.setDropDownBackgroundResource(R.color.field_fill);
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
        progressBar = getView().findViewById(R.id.progressBarEP);
    }

}
