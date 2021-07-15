package com.example.hackmate.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hackmate.Adapters.ProjectAdapterP;
import com.example.hackmate.Models.ProjectModel;
import com.example.hackmate.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;


public class PersonProfileFragment extends Fragment {

    Button invite;
    private RecyclerView projects_recyclerView;
    ChipGroup chipGroup;
    ImageView profile_pic;
    int GET_NAV_CODE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            GET_NAV_CODE = bundle.getInt("Key", 0);
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
    }


    public void initialise() {

        invite = getView().findViewById(R.id.invite_button);
        projects_recyclerView = getView().findViewById(R.id.projects_recyclerView_P);
        chipGroup = getView().findViewById(R.id.chipGroup112);
        profile_pic = getView().findViewById(R.id.profile_pic_P);
    }
}