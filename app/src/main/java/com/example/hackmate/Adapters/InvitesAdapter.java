package com.example.hackmate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.Models.InvitesModel;
import com.example.hackmate.R;

import java.util.ArrayList;

public class InvitesAdapter extends RecyclerView.Adapter<InvitesAdapter.Viewholder>{

    private Context context;
    private ArrayList<InvitesModel> InvitesArrayList;

    public InvitesAdapter(Context context, ArrayList<InvitesModel> invitesArrayList) {
        this.context = context;
        InvitesArrayList = invitesArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.invite, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        InvitesModel.Invites_Model model = (InvitesModel.Invites_Model) InvitesArrayList.get(position);
        holder.participantProfilePhoto_invites.setImageResource(model.getParticipantphoto());
        holder.ParticipantName_invites.setText(model.getParticipantName_invites());
        holder.InviteeTeamName_invites.setText(model.getTeamName_invites());


    }

    @Override
    public int getItemCount() {
        return InvitesArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
private ImageView participantProfilePhoto_invites;
private TextView ParticipantName_invites,InviteeTeamName_invites;
private Button Accept,Reject;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            participantProfilePhoto_invites=itemView.findViewById(R.id.inviteePhoto);
            ParticipantName_invites=itemView.findViewById(R.id.InviteeName);

            InviteeTeamName_invites=itemView.findViewById(R.id.teamName_invite);
            Accept=itemView.findViewById(R.id.acceptButton);
            Reject=itemView.findViewById(R.id.rejectButton);

            Accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "You have accepted this invite", Toast.LENGTH_SHORT).show();
                }
            });
            Reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "You have rejected this invite", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }



}
