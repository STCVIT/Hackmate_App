package com.example.hackmate.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hackmate.Adapters.ProjectAdapterMP;
import com.example.hackmate.Models.ProjectModel;
import com.example.hackmate.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    BottomNavigationView bottomNavigation;
    ImageView settingsImageView, editProfileImageView, addImageView, profile_pic;
    TextView editProfileTextView, addProjectTextView;
    ConstraintLayout add_project_constraint;
    CardView add_project_card;
    private RecyclerView projects_recyclerView;
    ChipGroup chipGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialise();

        settingsImageView.setOnClickListener(v -> settingsFrag());

        editProfileImageView.setOnClickListener(v -> editProfileFrag());

        editProfileTextView.setOnClickListener(v -> editProfileFrag());

        add_project_card.setOnClickListener(v -> addProjectFrag());

        add_project_constraint.setOnClickListener(v -> addProjectFrag());

        addProjectTextView.setOnClickListener(v -> addProjectFrag());

        addImageView.setOnClickListener(v -> addProjectFrag());

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
        projects_recyclerView.setAdapter(new ProjectAdapterMP(getContext(), arrayList1));

        String[] team_domains = {"App Development", "UI/UX"};

        for(int i=0;i<team_domains.length;i++)
        {
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

    public void initialise()
    {
        settingsImageView = getView().findViewById(R.id.settings_image);
        editProfileImageView = getView().findViewById(R.id.edit_profile_image);
        addImageView = getView().findViewById(R.id.add_image);
        editProfileTextView = getView().findViewById(R.id.edit_profile_click);
        addProjectTextView = getView().findViewById(R.id.add_a_project);
        add_project_constraint = getView().findViewById(R.id.add_project_constraint);
        add_project_card = getView().findViewById(R.id.cardView7);
        bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);
        projects_recyclerView = getView().findViewById(R.id.projects_recyclerView_MP);
        chipGroup = getView().findViewById(R.id.chipGroup2);
        profile_pic = getView().findViewById(R.id.profile_pic_MP);

    }

    public void editProfileFrag()
    {
        bottomNavigation.setVisibility(View.GONE);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment,new EditProfileFragment())
                .addToBackStack(null)
                .commit();
    }

    public void settingsFrag()
    {
        bottomNavigation.setVisibility(View.GONE);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment,new SettingsFragment())
                .addToBackStack(null)
                .commit();
    }

    public void addProjectFrag()
    {
        AddProjectFragment frag = new AddProjectFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("Key", 1);
        frag.setArguments(bundle);

        bottomNavigation.setVisibility(View.GONE);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment,frag)
                .addToBackStack(null)
                .commit();
    }
}