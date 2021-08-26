package com.example.hackmate.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.POJOClasses.Kavita.myTeams.Final2;
import com.example.hackmate.POJOClasses.Kavita.myTeams.PtSkill2;
import com.example.hackmate.POJOClasses.Kavita.myTeams.Skill2;
import com.example.hackmate.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MyTeamsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public String a;

    private Context context;
    // private  ArrayList<myTeamsModel> myTeamsArrayList;
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private List<Final2> myTeamsArrayList;
    public String teamId;
    public String id1, id2, tp;
    List<PtSkill2> outerList;
    List<Skill2> innerlist;
    List<String> list=new ArrayList<>();
   /* public myTeamsAdapter( Context context, ArrayList<myTeamsModel> myTeamsArrayList,OnTeamsListener mOnteamsListener) {
        this.context = context;
        this.myTeamsArrayList = myTeamsArrayList;
        this.mOnteamsListener = mOnteamsListener;
    }*/


    public MyTeamsAdapter(Context context, List<Final2> myTeamsArrayList) {
        this.context = context;
        this.myTeamsArrayList = myTeamsArrayList;
    }

    public void setmyTeams(List<Final2> final_objs2, String id2) {
        int position = myTeamsArrayList.size();
        myTeamsArrayList.addAll(final_objs2);
        notifyItemRangeInserted(position, final_objs2.size());
        this.id2 = id2;
    }
    public int getItemViewType(int position) {
        if(myTeamsArrayList.get(position) == null)
            return VIEW_TYPE_LOADING;
        return VIEW_TYPE_ITEM;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        if (viewType == VIEW_TYPE_ITEM)
            return new TeamViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.myteams_rv_layout, parent, false));
        else
            return new myTeamsLoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // myTeamsModel model = (myTeamsModel) myTeamsArrayList.get(position);
        //myTeamsPOJO myTeamsPOJO = myTeamsArrayList.get(position);
        //////Uncomment when backend to myteams is possible//////
        if (holder instanceof TeamViewholder)
            LoadTeamItem((TeamViewholder) holder, position);
    }
    private void LoadTeamItem(TeamViewholder holder, int position) {
        Final2 finals2 = myTeamsArrayList.get(position);

        id1 = myTeamsArrayList.get(position).getTeam().getAdminId();
        holder.TeamName_myTeams.setText(myTeamsArrayList.get(position).getTeam().getName());
        holder.HackName_myTeams.setText(myTeamsArrayList.get(position).getHackName());

        Log.i("myTeamsAdapter", id1);

        holder.Position_myTeams.setText(id1.equals(id2) ? "Leader" : "Member");
        outerList=new ArrayList<PtSkill2>();
        innerlist=new ArrayList<Skill2>();

        outerList.addAll(myTeamsArrayList.get(position).getPtSkill());
        //Log.i("innerListdata",String.valueOf(outerList.get(0).getSkills().get(0).getSkill()));
        Log.i("skillListSize",String.valueOf(outerList.get(0).getSkills().size()));
        for(int j =0;j<=outerList.size()-1;j++){
            for(int i =0;i<=outerList.get(j).getSkills().size()-1;i++){
                list.add(outerList.get(j).getSkills().get(i).getSkill());
            }}
        Log.i("skillList",String.valueOf(list));
        //innerlist=outerList.get(position).getSkills();
           /* List<String> list=new ArrayList<String>();
        int length_myTeams = outerList.size();
        Log.i("myTeamsAdapter",String.valueOf(length_myTeams));
        Log.i("outerList",String.valueOf(outerList));
//int length_myTeams = outerList.size();
        for (int i=0;i<=length_myTeams;i++)
            //innerlist.addAll(outerList.get(i).getSkills());
        //int length=innerlist.size();
        //for(int j=0;j<=outerList.get(i).getSkills().size();j++)

            //list.add(String.valueOf(innerlist.get(j).getSkill()));*/
        holder.skillGrp.removeAllViews();
        HashSet<String> UniqueSkills = new HashSet(list);
        for(String d : UniqueSkills)
        {
            Chip chip = new Chip(context);
            chip.setText(d);
            chip.setChipStrokeColorResource(R.color.pill_color);
            chip.setChipBackgroundColor(context.getResources().getColorStateList(R.color.chip_background_color));
            chip.setTextColor(context.getResources().getColorStateList(R.color.chip_text_color));
            chip.setChipStrokeWidth(4);
            chip.setClickable(false);
            holder.skillGrp.addView(chip);
            //outerList.clear();
            // list.clear();
        }
        list.clear();
    }
        /*for(int i=0;i<team_domains.length;i++)
        {
            Chip chip = new Chip(context);
            chip.setText(team_domains[i]);
            chip.setChipStrokeColorResource(R.color.pill_color);
            chip.setChipBackgroundColor(context.getResources().getColorStateList(R.color.chip_background_color));
            chip.setTextColor(context.getResources().getColorStateList(R.color.chip_text_color));
            chip.setChipStrokeWidth(4);
            chip.setClickable(false);
            holder.domainGrp.addView(chip);
        }*/


       /* holder.TeamName_myTeams.setText(model.getTeamName());
        holder.HackName_myTeams.setText(model.getHackName());
        holder.Position_myTeams.setText(model.getTeamPosition());

        holder.Domain1.setText(model.getDomain1());

        holder.Domain2.setText(model.getDomain2());

        holder.Domain3.setText(model.getDomain3());

        holder.Domain4.setText(model.getDomain4());

        holder.Domain5.setText(model.getDomain5());*/


    public int getItemCount() {
        return myTeamsArrayList.size();
    }

    public void showProgress() {
        if (!myTeamsArrayList.contains(null)) {
            myTeamsArrayList.add(null);
            notifyItemInserted(myTeamsArrayList.size() - 1);
        }
    }
    public void removeProgress() {
        int position = myTeamsArrayList.indexOf(null);
        notifyItemRemoved(position);
        myTeamsArrayList.remove(null);
    }

    public void clearList() {
        myTeamsArrayList.clear();
        myTeamsArrayList.add(null);
    }
    public List<Final2> getList() { return this. myTeamsArrayList;}

    public static class TeamViewholder extends RecyclerView.ViewHolder {
        TextView TeamName_myTeams, HackName_myTeams, Position_myTeams;
        Chip Domain1, Domain2, Domain3, Domain4, Domain5;
        ChipGroup skillGrp;

        public TeamViewholder(@NonNull View itemView) {
            super(itemView);
            TeamName_myTeams = itemView.findViewById(R.id.teamName_myTeams);
            HackName_myTeams = itemView.findViewById(R.id.hackName_myTeams);
            Position_myTeams = itemView.findViewById(R.id.Position_myTeams);


            skillGrp= itemView.findViewById(R.id.skillGrp);


        }

//        @Override
//        public void onClick(View v) {
//            teamId = myTeamsArrayList.get(getAdapterPosition()).getTeam().getId();
//            onTeamsListener.OnTeamsClick(getAdapterPosition(), Position_myTeams.getText(), teamId);
//
//        }


    }


    public static class myTeamsLoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public myTeamsLoadingViewHolder(@NotNull View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar);
        }
    }
}
