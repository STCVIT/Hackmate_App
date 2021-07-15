package com.example.hackmate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.R;
import com.example.hackmate.Models.myTeamsModel;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class myTeamsAdapter extends RecyclerView.Adapter<myTeamsAdapter.Viewholder> {

    public String a;
    private OnTeamsListener mOnteamsListener;
    private Context context;
    private ArrayList<myTeamsModel> myTeamsArrayList;

    public myTeamsAdapter(Context context, ArrayList<myTeamsModel> myTeamsArrayList, OnTeamsListener onTeamsListener) {
        this.context = context;
        this.myTeamsArrayList = myTeamsArrayList;
        this.mOnteamsListener=onTeamsListener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myteams_rv_layout, parent, false);
        return new Viewholder(view,mOnteamsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
       myTeamsModel.myTeams_Model model = (myTeamsModel.myTeams_Model) myTeamsArrayList.get(position);
       holder.TeamName_myTeams.setText(model.getTeamName());
       holder.HackName_myTeams.setText(model.getHackName());
       holder.Position_myTeams.setText(model.getTeamPosition());

       holder.Domain1.setText(model.getDomain1());

        holder.Domain2.setText(model.getDomain2());

        holder.Domain3.setText(model.getDomain3());

        holder.Domain4.setText(model.getDomain4());

        holder.Domain5.setText(model.getDomain5());
        holder.nextFragment_myTeams.setImageResource(model.getNextFragment());

    }

    @Override
    public int getItemCount() {
        return myTeamsArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView nextFragment_myTeams;
        private TextView TeamName_myTeams, HackName_myTeams,Position_myTeams;
        private Chip Domain1,Domain2,Domain3,Domain4,Domain5;
OnTeamsListener onTeamsListener;

        public Viewholder(@NonNull View itemView,OnTeamsListener onTeamsListener) {
            super(itemView);
            TeamName_myTeams=itemView.findViewById(R.id.teamName_myTeams);
            HackName_myTeams=itemView.findViewById(R.id.hackName_myTeams);
            Position_myTeams=itemView.findViewById(R.id.Position_myTeams);
            nextFragment_myTeams=itemView.findViewById(R.id.particularTeam_view);
            Domain1=itemView.findViewById(R.id.Domain1_Chip);
            Domain2=itemView.findViewById(R.id.Domain2_Chip);
            Domain3=itemView.findViewById(R.id.Domain3_Chip);
            Domain4=itemView.findViewById(R.id.Domain4_Chip);
            Domain5=itemView.findViewById(R.id.Domain5_Chip);
            this.onTeamsListener=onTeamsListener;
itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
             a= (String) Position_myTeams.getText();
onTeamsListener.OnTeamsClick(getAdapterPosition(),a);

        }
    }

    public interface OnTeamsListener{
        void OnTeamsClick(int position,String a);
    }
}
