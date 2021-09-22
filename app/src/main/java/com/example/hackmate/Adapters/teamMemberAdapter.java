package com.example.hackmate.Adapters;

import android.content.Context;
import android.content.Intent;
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

import com.bumptech.glide.Glide;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.particularteam_team_member_rv_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        PtSkill ptSkills1 = teamMemberArrayList.get(position);


        Log.i("ConditionCheck", ptSkills1.getParticipant().get_id());
        Log.i("adminCheck", adminId);
        id = ptSkills1.getParticipant().get_id();

        holder.SerialNo.setText(String.valueOf(position + 1));
        holder.MemName.setText(id.equals(UserID) ? "Me" : ptSkills1.getParticipant().getName());
        holder.MemEmail.setText(ptSkills1.getParticipant().getEmail());
        holder.MemPosition.setText(id.equals(adminId) ? "Leader" : "");
        Glide.with(context).
                load(ptSkills1.getParticipant().getPhoto()).
                placeholder(R.drawable.download).
                into(holder. Profilephoto);
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
                            Intent intent = new Intent(context,MainActivity.class);
                            intent.putExtra("Frag",2);
                            context.startActivity(intent);
                            ((MainActivity) context).finish();
                            Toast.makeText(context, "You left the team", Toast.LENGTH_SHORT).show();

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
                bundle3.putInt("Key",1);
                frag3.setArguments(bundle3);

                MainActivity activity = (MainActivity) v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, frag3)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return teamMemberArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView SerialNo, MemName, MemEmail, MemPosition, LeaveOption;
        ImageView Profilephoto;
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
        }
    }
}