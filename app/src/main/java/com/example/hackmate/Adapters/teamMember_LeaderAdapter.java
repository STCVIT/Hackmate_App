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
import com.example.hackmate.Models.teamMember_Model;

import java.util.ArrayList;


public class teamMember_LeaderAdapter extends RecyclerView.Adapter<teamMember_LeaderAdapter.Viewholder> {
    private Context context;
    private ArrayList<teamMember_Model> teamMemberLeaderArrayList;

    public teamMember_LeaderAdapter(Context context, ArrayList<teamMember_Model> teamMemberLeaderArrayList) {
        this.context = context;
        this.teamMemberLeaderArrayList = teamMemberLeaderArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.particularteam_team_member_rv_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        // Get the data model based on position

        teamMember_Model.TeamMemberModel model = (teamMember_Model.TeamMemberModel) teamMemberLeaderArrayList.get(position);
        holder.SerialNo.setText(model.getSerialNo());
        holder.MemName.setText(model.getMemName());
        holder.MemEmail.setText(model.getMemEmail());
        holder.MemPosition.setText(model.getMemPosition());
        holder.Profilephoto.setImageResource(model.getProfilephoto());
        holder.LeaveOption.setText(model.isMember() ? "Leave":"Remove");
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return teamMemberLeaderArrayList.size();
    }


    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder{

        private TextView SerialNo,MemName,MemEmail,MemPosition,LeaveOption;
        private ImageView Profilephoto;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            SerialNo = itemView.findViewById(R.id.serialNo);
            MemName = itemView.findViewById(R.id.memberName);
            MemEmail = itemView.findViewById(R.id.memberEmail);
            MemPosition = itemView.findViewById(R.id.memberPosition);
            Profilephoto = itemView.findViewById(R.id.profilePhoto);
            LeaveOption=itemView.findViewById(R.id.leaveOption);
        }
    }
}