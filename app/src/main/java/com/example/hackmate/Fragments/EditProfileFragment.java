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
import com.example.hackmate.POJOClasses.ProjectPOJO;
import com.example.hackmate.POJOClasses.TeamProject;
import com.example.hackmate.POJOClasses.loginPOJO;
import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

import java.util.ArrayList;
import java.util.List;

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
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String idToken, id;
    Retrofit retrofit;
    private loginAPI loginAPI;

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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Changes saved !!!", Toast.LENGTH_SHORT).show();
            }
        });


        String[] years1 = {"2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
        ArrayAdapter<String> YOG_arrayAdapter1 = new ArrayAdapter<String>(getContext(), R.layout.option_item, years1);

        projects_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        ProjectModel model2 = new ProjectModel("Hackmate",
//                "Project for team building for hackathons",
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Tristique mauris, " +
//                        "nec vitae cursus phasellus a proin et. Sit in velit duis iaculis est. " +
//                        "At odio sociis venenatis ut commodo. Aliquet eget morbi faucibus nisl " +
//                        "nec quis suscipit ut. Mus vestibulum risus at ante lorem volutpat. " +
//                        "In vitae vitae, tortor a ipsum ipsum. Ipsum cras eu odio natoque blandit commodo aliquam.",
//                "abc@gmail.com", "abc@gmail.com", "abc@gmail.com");
//        ArrayList arrayList1 = new ArrayList<ProjectModel>();
//        arrayList1.add(model2);
//        arrayList1.add(model2);
//        projects_recyclerView.setAdapter(new ProjectAdapterEP(getContext(), arrayList1));

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
                Log.i("id22", id);
                Call<ProjectPOJO> caller = loginAPI.getProject("Bearer " + MainActivity.getidToken());
                Log.i("tag", "tag");
                caller.enqueue(new Callback<ProjectPOJO>() {
                    @Override
                    public void onResponse(Call<ProjectPOJO> call, Response<ProjectPOJO> response) {
                        ProjectPOJO projectPOJO = response.body();
//                        Log.i("abc", projectPOJO.getTeam().getName().toString());
                        List<IndividualProject> individualProjectsList = projectPOJO.getIndividualProjects();
//                        Log.i("pt_skill", String.valueOf(pt_skills.get(0).getParticipant().getName()));
                        List<TeamProject> teamProjectsList = projectPOJO.getTeams();
                        ProjectAdapterEP projectAdapterEP = new ProjectAdapterEP(getContext(), individualProjectsList);
                        projects_recyclerView.setAdapter(projectAdapterEP);
                        projectAdapterEP.setGetProjectEP(individualProjectsList);

                    }

                    @Override
                    public void onFailure(Call<ProjectPOJO> call, Throwable t) {
                        Log.i("error", t.getMessage());
                    }
                });

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
    }

}
