package com.example.hackmate.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.Models.AddFromExistingModel;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.PtSkill;
import com.example.hackmate.POJOClasses.TeamDetails;
import com.example.hackmate.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class AddFromExistingAdapter extends RecyclerView.Adapter<AddFromExistingAdapter.Viewholder> {

    private Context context;
    private ArrayList<AddFromExistingModel> addFromExistingModelArrayList;
    String domains[][] = {{"App Dev", "Frontend", "Backend"}, {"Frontend", "Backend"}};
    List<TeamDetails> teamDetails;


    public AddFromExistingAdapter(Context context, List<TeamDetails> teamDetails) {
        this.context = context;
        this.teamDetails = teamDetails;
    }

    public void setGetTeams(List<TeamDetails> teamDetails) {
        this.teamDetails = teamDetails;
    }

    @NonNull
    @Override
    public AddFromExistingAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_hack_to_team_row, parent, false);
        return new AddFromExistingAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddFromExistingAdapter.Viewholder holder, int position) {

        TeamDetails teamDetail = teamDetails.get(position);
        holder.projectName_AFE.setText(teamDetail.getTeam().getName());
//        holder.description_AFE.setText(teamDetail.);
//        holder.designation_AFE.setText(model.getDesignation());

        holder.addHack_button_AFE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) v.getContext();
                holder.addHack_button_AFE.setBackground(activity.getResources().getDrawable(R.drawable.ic_added_button));
                holder.addHack_button_AFE.setTextColor(activity.getResources().getColor(R.color.text));
                holder.addHack_button_AFE.setText("Added");
                Toast.makeText(activity, "Hack has been added to Team Profile !!", Toast.LENGTH_SHORT).show();
            }
        });


//        List<String> team_domains = new ArrayList<>();
//        int i,j;
//        for(i=0;i<teamDetail.getPtp_skill().size();i++){
//            for(j=0; j<teamDetail.getPtp_skill().get(i).getSkills().size();j++)
//            Log.i("skills", String.valueOf(teamDetail.getPtp_skill().get(i).getSkills().get(j).getSkill()));
//        }

//        if(teamDetail.getPtp_skill().get(position).getSkills() != null) {
//            for (i = 0; i < teamDetail.getPtp_skill().get(position).getSkills().size(); i++) {
//                team_domains.add(teamDetail.getPtp_skill().get(position).getSkills().get(i).getSkill());
//            }
//        }
//
//        if (team_domains != null) {
//            for (i = 0; i < team_domains.size(); i++) {
//                Chip chip = new Chip(context);
//                chip.setText(team_domains.get(i));
//                chip.setChipStrokeColorResource(R.color.pill_color);
//                chip.setChipBackgroundColor(context.getResources().getColorStateList(R.color.chip_background_color));
//                chip.setTextColor(context.getResources().getColorStateList(R.color.chip_text_color));
//                chip.setChipStrokeWidth(4);
//                chip.setClickable(false);
//                holder.domainGrp.addView(chip);
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return teamDetails.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView projectName_AFE, description_AFE, designation_AFE;
        Button addHack_button_AFE;
        ChipGroup domainGrp;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            projectName_AFE = itemView.findViewById(R.id.projectName_AFE);
            designation_AFE = itemView.findViewById(R.id.designation_AFE);
            addHack_button_AFE = itemView.findViewById(R.id.addHack_button_AFE);
            domainGrp = itemView.findViewById(R.id.domaingrp);
        }
    }
}
