package com.example.hackmate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.Models.AddFromExistingModel;
import com.example.hackmate.MainActivity;
import com.example.hackmate.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class AddFromExistingAdapter extends RecyclerView.Adapter<AddFromExistingAdapter.Viewholder> {

    private Context context;
    private ArrayList<AddFromExistingModel> addFromExistingModelArrayList;
    String domains[][];

    public AddFromExistingAdapter(Context context, ArrayList<AddFromExistingModel> addFromExistingModelArrayList, String[][] domains) {
        this.context = context;
        this.addFromExistingModelArrayList = addFromExistingModelArrayList;
        this.domains = domains;
    }


    @NonNull
    @Override
    public AddFromExistingAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_hack_to_team_row, parent, false);
        return new AddFromExistingAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddFromExistingAdapter.Viewholder holder, int position) {

        AddFromExistingModel model = addFromExistingModelArrayList.get(position);
        holder.projectName_AFE.setText(model.getProjectName());
        holder.description_AFE.setText(model.getDescription());
        holder.designation_AFE.setText(model.getDesignation());

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

        String[] team_domains = domains[position];


        for(int i=0;i<team_domains.length;i++)
        {
            Chip chip = new Chip(context);
            chip.setText(team_domains[i]);
            chip.setChipStrokeColorResource(R.color.pill_color);
            chip.setChipBackgroundColor(context.getResources().getColorStateList(R.color.chip_background_color));
            chip.setTextColor(context.getResources().getColorStateList(R.color.chip_text_color));
            chip.setChipStrokeWidth(4);
            chip.setClickable(false);
            holder.domainGrp.addView(chip);
        }
    }

    @Override
    public int getItemCount() {
        return addFromExistingModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        TextView projectName_AFE, description_AFE, designation_AFE;
        Button addHack_button_AFE;
        ChipGroup domainGrp;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            projectName_AFE = itemView.findViewById(R.id.projectName_AFE);
            description_AFE = itemView.findViewById(R.id.description_AFE);
            designation_AFE = itemView.findViewById(R.id.designation_AFE);
            addHack_button_AFE = itemView.findViewById(R.id.addHack_button_AFE);
            domainGrp = itemView.findViewById(R.id.domaingrp);
        }
    }
}
