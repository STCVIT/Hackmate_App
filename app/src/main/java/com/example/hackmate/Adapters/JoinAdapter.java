package com.example.hackmate.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.Fragments.RequestTeamFragment;
import com.example.hackmate.MainActivity;
import com.example.hackmate.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class JoinAdapter extends RecyclerView.Adapter<JoinAdapter.ProgramViewHolder>{

    String[] teamNames;
    String[][] domains;
    private BottomNavigationView bottomNavigation;
    int GET_NAV_CODE;
    private Context context;

    public JoinAdapter(String[] teamNames, String[][] domains,int GET_NAV_CODE) {
        this.teamNames=teamNames;
        this.domains=domains;
        this.GET_NAV_CODE=GET_NAV_CODE;
    }

    @NonNull
    @Override
    public JoinAdapter.ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.join_search_team_list,parent,false);
        context = parent.getContext();
        return new JoinAdapter.ProgramViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JoinAdapter.ProgramViewHolder holder, int position) {
        String showName = teamNames[position];
        String[] team_domains = domains[position];

        holder.teamName.setText(showName);

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestTeamFragment frag = new RequestTeamFragment();

                if(GET_NAV_CODE==1) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("Key", 1);
                    frag.setArguments(bundle);
                }
                MainActivity activity = (MainActivity) v.getContext();

                bottomNavigation = activity.findViewById(R.id.bottom_nav_bar);
                bottomNavigation.setVisibility(View.GONE);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,frag)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return teamNames.length;
    }

    public class ProgramViewHolder extends RecyclerView.ViewHolder {
        TextView teamName;
        ChipGroup domainGrp;
        ImageView detailsArrow;
        public ProgramViewHolder(@NonNull View itemView) {
            super(itemView);
            teamName = itemView.findViewById(R.id.teamName);
            domainGrp = itemView.findViewById(R.id.chipsGroup);
            detailsArrow = itemView.findViewById(R.id.joinTeamDetails);
        }
    }
}