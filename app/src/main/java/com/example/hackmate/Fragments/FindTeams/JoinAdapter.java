package com.example.hackmate.Fragments.FindTeams;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.Fragments.TeamProfileParticipantViewFragment;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.JoinHackTeams.Final;
import com.example.hackmate.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class JoinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private BottomNavigationView bottomNavigation;
    private List<Final> joinArrayList;
    int GET_NAV_CODE;
    private Context context;
    private String hackName;
    private String name;

    public JoinAdapter(Context context, List<Final> joinArrayList, int code, String hackName) {
        this.context = context;
        this.joinArrayList = joinArrayList;
        this.GET_NAV_CODE = code;
        this.hackName = hackName;
    }

    public void addItems(List<Final> list) {
        int position = this.joinArrayList.size();
        this.joinArrayList.addAll(list);
        this.name = name;
        notifyItemRangeInserted(position, list.size());
    }

    @Override
    public int getItemViewType(int position) {
        if(joinArrayList.get(position) == null)
            return VIEW_TYPE_LOADING;
        return VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM)
            return new ProgramViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.join_search_team_list, parent, false));
        else
            return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProgramViewHolder)
            populateItem((ProgramViewHolder) holder, position);
    }

    private void populateItem(ProgramViewHolder holder, int position) {
        holder.teamName.setText(joinArrayList.get(position).getTeam().getName());
        if (hackName == null)
            holder.hackNaming.setVisibility(View.GONE);
        else {
            holder.hackNaming.setVisibility(View.VISIBLE);
            holder.hackNaming.setText(hackName);
        }

        List<String> domains = new ArrayList<>();

        int length_domains = joinArrayList.get(position).getSkills().size();
        for (int i=0;i<length_domains;i++)
            domains.add(joinArrayList.get(position).getSkills().get(i).getSkill());

        holder.domainGrp.removeAllViews();
        for (String d : domains) {
            Chip chip = new Chip(context);
            chip.setText(d);
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
                TeamProfileParticipantViewFragment frag = new TeamProfileParticipantViewFragment();
                Bundle bundle = new Bundle();
                if (GET_NAV_CODE == 1)
                    bundle.putInt("Key", 1);

                if(hackName!=null)
                    bundle.putString("hackName",hackName);
                bundle.putString("teamId",joinArrayList.get(position).getTeam()._id);
                frag.setArguments(bundle);

                MainActivity activity = (MainActivity) v.getContext();

                bottomNavigation = activity.findViewById(R.id.bottom_nav_bar);
                bottomNavigation.setVisibility(View.GONE);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, frag)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return joinArrayList.size();
    }

    public void showProgress() {
        if (!joinArrayList.contains(null)) {
            joinArrayList.add(null);
            notifyItemInserted(joinArrayList.size() - 1);
        }
    }

    public void removeProgress() {
        int position = joinArrayList.indexOf(null);
        notifyItemRemoved(position);
        joinArrayList.remove(null);
    }

    public void clearList() {
        joinArrayList.clear();
        joinArrayList.add(null);
    }

    public List<Final> getList() { return this.joinArrayList;}

    public static class ProgramViewHolder extends RecyclerView.ViewHolder {
        TextView teamName, hackNaming;
        ChipGroup domainGrp;
        ImageView detailsArrow;

        public ProgramViewHolder(@NonNull View itemView) {
            super(itemView);
            teamName = itemView.findViewById(R.id.teamName);
            domainGrp = itemView.findViewById(R.id.chipsGroup);
            detailsArrow = itemView.findViewById(R.id.joinTeamDetails);
            hackNaming = itemView.findViewById(R.id.hackNaming);
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NotNull View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar);
        }
    }
}