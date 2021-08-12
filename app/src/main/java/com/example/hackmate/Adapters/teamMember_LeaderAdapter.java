package com.example.hackmate.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.JSONPlaceholders.JSONPlaceHolderAPI;
import com.example.hackmate.POJOClasses.PtSkill;
import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class teamMember_LeaderAdapter extends RecyclerView.Adapter<teamMember_LeaderAdapter.Viewholder> {
    private Context context;
    private List<PtSkill> teamMemberLeaderArrayList;

    public String adminId, id, teamID;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String idToken = "Bearer ";

    public teamMember_LeaderAdapter(Context context, List<PtSkill> teamMemberLeaderArrayList) {
        this.context = context;
        this.teamMemberLeaderArrayList = teamMemberLeaderArrayList;

    }

    public void setMemberLeaderList(List<PtSkill> teamMemberLeaderAL, String adminId,String teamID) {
        this.teamMemberLeaderArrayList = teamMemberLeaderAL;
        this.adminId = adminId;
        this.teamID=teamID;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.particularteam_team_member_rv_layout, parent, false);
        return new teamMember_LeaderAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        // Get the data model based on position
        PtSkill ptSkills = teamMemberLeaderArrayList.get(position);
        //teamMember_Model.TeamMemberModel model = (teamMember_Model.TeamMemberModel) teamMemberLeaderArrayList.get(position);


        Log.i("ConditionCheck", ptSkills.getParticipant().get_id());
        Log.i("adminCheck", adminId);
        id = ptSkills.getParticipant().get_id();

        holder.SerialNo.setText(String.valueOf(position + 1));
        holder.MemName.setText(ptSkills.getParticipant().getName());
        holder.MemEmail.setText(ptSkills.getParticipant().getEmail());
        holder.MemPosition.setText(id.equals(adminId) ? "Leader" : "");
        // holder.Profilephoto.setImageResource(model.getProfilephoto());
        holder.LeaveOption.setText(id.equals(adminId) ? "leave" : "Remove");
        if(holder.LeaveOption.getText().equals("Remove")){
            holder.LeaveOption.setOnClickListener(v -> {
                JSONPlaceHolderAPI jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);
                Call<Void> call = jsonPlaceHolderAPI.removeMember(idToken, teamID,id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.i("teamMemberAdapter1", "code: " + response.code());
                        Log.i("teamMemberAdapter2", "body: " + response.body());

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.i("failed1", t.getMessage());
                    }
                });
            });
        }
        else{
            holder.LeaveOption.setOnClickListener(v -> {
                JSONPlaceHolderAPI jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);
                Call<Void> call = jsonPlaceHolderAPI.leaveTeam(idToken, teamID);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.i("teamLeaderAdapter3", "code: " + response.code());
                        Log.i("teamLeaderAdapter4", "body: " + response.body());

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.i("failed2", t.getMessage());
                    }
                });
            });
        }
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return teamMemberLeaderArrayList.size();
    }


    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView SerialNo, MemName, MemEmail, MemPosition, LeaveOption;
        //private ImageView Profilephoto;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            SerialNo = itemView.findViewById(R.id.serialNo);
            MemName = itemView.findViewById(R.id.memberName);
            MemEmail = itemView.findViewById(R.id.memberEmail);
            MemPosition = itemView.findViewById(R.id.memberPosition);
            //Profilephoto = itemView.findViewById(R.id.profilePhoto);
            LeaveOption = itemView.findViewById(R.id.leaveOption);


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

