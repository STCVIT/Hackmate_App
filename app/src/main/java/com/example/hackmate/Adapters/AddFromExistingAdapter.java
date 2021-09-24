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
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.JoinTeamPOJO;
import com.example.hackmate.POJOClasses.POST.PatchTeamDetails;
import com.example.hackmate.POJOClasses.PtSkill;
import com.example.hackmate.POJOClasses.Team;

import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddFromExistingAdapter extends RecyclerView.Adapter<AddFromExistingAdapter.Viewholder> {

    private Context context;
    List<JoinTeamPOJO> teamDetails;
    loginAPI loginAPI;
    PatchTeamDetails patchTeamDetails;
    String hack_id;


    public AddFromExistingAdapter(Context context, List<JoinTeamPOJO> teamDetails) {
        this.context = context;
        this.teamDetails = teamDetails;
    }

    public void setGetTeams(List<JoinTeamPOJO> teamDetails, String hack_id) {
        this.teamDetails = teamDetails;
        this.hack_id = hack_id;
    }


    @NonNull
    @Override
    public AddFromExistingAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_hack_to_team_row, parent, false);
        return new AddFromExistingAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddFromExistingAdapter.Viewholder holder, int position) {

        JoinTeamPOJO teamDetail = teamDetails.get(position);
        holder.projectName_AFE.setText(teamDetail.getTeam().getName());
        Log.i("team name", teamDetail.getTeam().getName().toString());
//        holder.description_AFE.setText(teamDetail.);
//        holder.designation_AFE.setText(model.getDesignation());

        loginAPI = RetrofitInstance.getRetrofitInstance().create(loginAPI.class);
        patchTeamDetails = new PatchTeamDetails();
        patchTeamDetails.setHack_id(hack_id);

        holder.addHack_button_AFE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) v.getContext();

                Call<Void> call = loginAPI.patchTeamDetails("Bearer " + MainActivity.getIdToken(),
                        teamDetail.getTeam().get_id(), patchTeamDetails);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(activity, "Team has been added to Hack!!", Toast.LENGTH_SHORT).show();
                        holder.addHack_button_AFE.setBackground(activity.getResources().getDrawable(R.drawable.ic_added_button));
                        holder.addHack_button_AFE.setTextColor(activity.getResources().getColor(R.color.text));
                        holder.addHack_button_AFE.setText("Added");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.i("nhi huaaaaa", t.getMessage());
                    }
                });
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
