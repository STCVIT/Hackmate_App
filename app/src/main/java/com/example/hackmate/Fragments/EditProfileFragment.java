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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hackmate.Adapters.ProjectAdapterEP;
import com.example.hackmate.ProjectModel;
import com.example.hackmate.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class EditProfileFragment extends Fragment {

    Button saveButton;
    BottomNavigationView bottomNavigation;
    AutoCompleteTextView YOG_CompleteEditText;
    private RecyclerView projects_recyclerView;
    ImageView profile_pic_EP;

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
        ArrayAdapter<String> YOG_arrayAdapter1 = new ArrayAdapter<String>(getContext(),R.layout.option_item, years1);
        YOG_CompleteEditText.setText("2024", false);
        YOG_CompleteEditText.setAdapter(YOG_arrayAdapter1);

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
        projects_recyclerView.setAdapter(new ProjectAdapterEP(getContext(), arrayList1));

        profile_pic_EP.setImageResource(R.drawable.bhavik);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        bottomNavigation.setVisibility(View.VISIBLE);
    }

    public void initialise()
    {
        saveButton = getView().findViewById(R.id.saveChangeButton);
        bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);
        YOG_CompleteEditText = getView().findViewById(R.id.year_of_graduation_edit);
        projects_recyclerView = getView().findViewById(R.id.projects_recyclerView_EP);
        profile_pic_EP = getView().findViewById(R.id.profile_pic_EP);
    }

}
