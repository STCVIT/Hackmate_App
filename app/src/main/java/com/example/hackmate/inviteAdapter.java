package com.example.hackmate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class inviteAdapter extends RecyclerView.Adapter<inviteAdapter.Viewholder> {
    private Context context;
    private ArrayList<invite_Model> InviteArrayList;

    public inviteAdapter(ArrayList<invite_Model> InviteArrayList) {
        this.context = context;
        this.InviteArrayList = InviteArrayList;
    }

    @NonNull
    @Override
    public inviteAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.invite, parent, false);
        return new inviteAdapter.Viewholder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        invite_Model.InviteModel model = (invite_Model.InviteModel) InviteArrayList.get(position);

        holder.Invite.setText(model.getInviteTitle());
    holder.MemName1.setText(model.getMemName());
    holder.TeamName.setText(model.getTeamName());
        holder.Profilephoto.setImageResource(model.getProfilephoto());
        /*holder.accept.setOnClickListener(new View.OnClickListener(){
        public void onClick(View v) {
            Toast.makeText(context, "Invite is accepted", Toast.LENGTH_SHORT).show();
        }
    });
        holder.reject.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Toast.makeText(context, "Invite is accepted", Toast.LENGTH_SHORT).show();
            }
        });*/

boolean isExpandable= ((invite_Model.InviteModel) InviteArrayList.get(position)).isExpandable();
holder.explandable_layout.setVisibility(isExpandable?View.VISIBLE:View.GONE);
    }


   /* public void onBindViewHolder(@NonNull teamMemberAdapter.Viewholder holder, int position) {
        invite_Model.InviteModel model = (invite_Model.InviteModel) InviteArrayList.get(position);
holder.MemName1.setText(model.getMemName());


    }
*/
    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return InviteArrayList.size();
    }




    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder{

        public TextView MemName1,TeamName,Invite;
        public ImageView Profilephoto;
        //public Button accept,reject;
        LinearLayout linearLayout;
        RelativeLayout explandable_layout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
Invite=itemView.findViewById(R.id.Invite);
            MemName1 = itemView.findViewById(R.id.InviteeName);
            TeamName = itemView.findViewById(R.id.teamName_invite);
            Profilephoto = itemView.findViewById(R.id.inviteePhoto);
            //accept=itemView.findViewById(R.id.acceptButton);
            //reject = itemView.findViewById(R.id.rejectButton);
            linearLayout=itemView.findViewById(R.id.linear_layout1);
            explandable_layout=itemView.findViewById(R.id.explandable_layout);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    invite_Model.InviteModel  invite_Model= (com.example.hackmate.invite_Model.InviteModel) InviteArrayList.get(getAdapterPosition());
                    invite_Model.setExpandable(!invite_Model.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }

}
