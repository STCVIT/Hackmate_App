package com.example.hackmate.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.Fragments.ProfileViewFragment;
import com.example.hackmate.JSONPlaceholders.JSONPlaceHolderAPI;
import com.example.hackmate.MainActivity;

import com.example.hackmate.POJOClasses.JoinTeamPOJO;
import com.example.hackmate.POJOClasses.PtSkill;
import com.example.hackmate.R;
import com.example.hackmate.Models.teamMember_Model;
import com.example.hackmate.util.RetrofitInstance;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class teamMemberAdapter extends RecyclerView.Adapter<teamMemberAdapter.Viewholder> {
    private Context context;
    private List<PtSkill> teamMemberArrayList;
    public String adminId, id, UserID, TeamId;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String idToken = "Bearer ";

    public teamMemberAdapter(Context context, List<PtSkill> teamMemberArrayList) {
        this.context = context;
        this.teamMemberArrayList = teamMemberArrayList;
    }

    public void setMemberList(List<PtSkill> teamMemberAL, String adminId, String UserID, String teamID) {
        this.teamMemberArrayList = teamMemberAL;
        this.adminId = adminId;
        this.UserID = UserID;
        this.TeamId = teamID;
        notifyDataSetChanged();
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
        PtSkill ptSkills1 = teamMemberArrayList.get(position);


        Log.i("ConditionCheck", ptSkills1.getParticipant().get_id());
        Log.i("adminCheck", adminId);
        id = ptSkills1.getParticipant().get_id();

        holder.SerialNo.setText(String.valueOf(position + 1));
        holder.MemName.setText(id.equals(UserID) ? "Me" : ptSkills1.getParticipant().getName());
        holder.MemEmail.setText(ptSkills1.getParticipant().getEmail());
        holder.MemPosition.setText(id.equals(adminId) ? "Leader" : "");
        // holder.Profilephoto.setImageResource(model.getProfilephoto());
        holder.LeaveOption.setText(id.equals(UserID) ? "Leave" : "");
        if (holder.LeaveOption.getText().equals("Leave")) {
            holder.LeaveOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONPlaceHolderAPI jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);
                    Call<JoinTeamPOJO> call = jsonPlaceHolderAPI.leaveTeam(MainActivity.getIdToken(), TeamId);
                    call.enqueue(new Callback<JoinTeamPOJO>() {
                        @Override
                        public void onResponse(Call<JoinTeamPOJO> call, Response<JoinTeamPOJO> response) {
                            Log.i("teamMemberAdapter", "code: " + response.code());
                            Log.i("teamMemberAdapter", "body: " + response.body());
                            Toast.makeText(context, "Leave successful !!", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<JoinTeamPOJO> call, Throwable t) {
                            Log.i("failed1", t.getMessage());
                        }
                    });
                }
            });
        }

        holder.teamMembercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileViewFragment frag3 = new ProfileViewFragment();
                Log.i("IdCheck",id);
                Bundle bundle3 = new Bundle();
                bundle3.putString("id", teamMemberArrayList.get(position).getParticipant().get_id());
                frag3.setArguments(bundle3);

                MainActivity activity = (MainActivity) v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, frag3)
                        .addToBackStack(null)
                        .commit();
            }
        });
        //holder.LeaveOption.setText(id.equals(adminId)? "":"leave");
        //teamMember_Model.TeamMemberModel model = (teamMember_Model.TeamMemberModel) teamMemberArrayList.get(position);
       /* holder.SerialNo.setText(model.getSerialNo());
        holder.MemName.setText(model.getMemName());
        holder.MemEmail.setText(model.getMemEmail());
        holder.MemPosition.setText(model.getMemPosition());
        holder.Profilephoto.setImageResource(model.getProfilephoto());
        holder.LeaveOption.setVisibility(model.isMember() ? View.GONE:View.VISIBLE);*/
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view.
        return teamMemberArrayList.size();
    }


    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView SerialNo, MemName, MemEmail, MemPosition, LeaveOption;
        private ImageView Profilephoto;
        private CardView teamMembercard;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            SerialNo = itemView.findViewById(R.id.serialNo);
            MemName = itemView.findViewById(R.id.memberName);
            MemEmail = itemView.findViewById(R.id.memberEmail);
            MemPosition = itemView.findViewById(R.id.memberPosition);
            Profilephoto = itemView.findViewById(R.id.profilePhoto);
            LeaveOption = itemView.findViewById(R.id.leaveOption);
            teamMembercard=itemView.findViewById(R.id.TeamMember);
            /*////////////Uncomment once mauth is is universal or is delecraled in mainactivity///////////////
            MemName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProfileViewFragment frag1 = new ProfileViewFragment();
                    Log.i("participant id", String.valueOf(id));
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", String.valueOf(id));
                    frag1.setArguments(bundle);

                    MainActivity activity = (MainActivity) itemView.getContext();
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment, frag1)
                            .addToBackStack(null)
                            .commit();
                }
            });*/
        }
    }
}
