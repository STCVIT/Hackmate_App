package com.example.hackmate.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hackmate.Fragments.ProfileViewFragment;
import com.example.hackmate.Fragments.TeamProfileParticipantViewFragment;
import com.example.hackmate.JSONPlaceholders.JSONPlaceHolderAPI;
import com.example.hackmate.MainActivity;

import com.example.hackmate.POJOClasses.Kavita.Invites.invitePOJO;
import com.example.hackmate.POJOClasses.PtSkill;
import com.example.hackmate.POJOClasses.Kavita.Invites.Received;

import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InvitesAdapter extends RecyclerView.Adapter<InvitesAdapter.Viewholder> {

    private Context context;
    private List<Received> InvitesArrayList;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String idToken ;
    public String UserName;
    public String ParticiapntName;
    public String adminID, adminID2;

    public InvitesAdapter(Context context, List<Received> invitesArrayList) {
        this.context = context;
        InvitesArrayList = invitesArrayList;
    }

    public void setMyInvites(List<Received> recieved_objs, String idToken) {
        this.InvitesArrayList = recieved_objs;
        this.idToken = idToken;

        notifyDataSetChanged();

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
        //InvitesModel.Invites_Model model = (InvitesModel.Invites_Model) InvitesArrayList.get(position);

        //invitePOJO invitePOJO =InvitesArrayList.get(position);
        //holder.participantProfilePhoto_invites.setImageResource(model.getParticipantphoto());

        Received recieveds = InvitesArrayList.get(position);

        holder.ParticipantName_invites.setText(recieveds.getLeader().getName());
        holder.InviteeTeamName_invites.setText(recieveds.getTeam().getName());
        Glide.with(context).
                load( InvitesArrayList.get(position).getLeader().getPhoto()).
                placeholder(R.drawable.download).
                into(holder.participantProfilePhoto_invites);
        JSONPlaceHolderAPI jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);
        holder.Accept.setOnClickListener(v -> {

            Call<Void> call5 = jsonPlaceHolderAPI.postInviteStatus( MainActivity.getIdToken(),"accepted",recieveds.getInv());
            call5.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call5, Response<Void> response5) {

                    if (!response5.isSuccessful()){
                        Log.i("sucess", "sucess");
                    }
                    //AcceptPOJO acceptPOJO1 = response5.body();
                    Log.i("inviteAdapter", String.valueOf(response5.code()));
                    Log.i("inviteAdapter",String.valueOf(response5.body()));
                    int newPosition = holder.getAdapterPosition();
                    InvitesArrayList.remove(newPosition);
                    notifyItemRemoved(newPosition);
                    notifyItemRangeChanged(newPosition, InvitesArrayList.size());


                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.i("error", t.getMessage());
                }
            });

        });
        holder.Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call5 = jsonPlaceHolderAPI.postInviteStatus(MainActivity.getIdToken(),"rejected",recieveds.getInv());
                call5.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call5, Response<Void> response5) {

                        if (!response5.isSuccessful()){
                            Log.i("sucess", "sucess");
                        }
                        //AcceptPOJO acceptPOJO1 = response5.body();
                        Log.i("inviteAdapter", String.valueOf(response5.code()));
                        Log.i("inviteAdapter",String.valueOf(response5.body()));
                        int newPosition = holder.getAdapterPosition();
                        InvitesArrayList.remove(newPosition);
                        notifyItemRemoved(newPosition);
                        notifyItemRangeChanged(newPosition, InvitesArrayList.size());


                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.i("error", t.getMessage());
                    }
                });

            }
        });

        holder.InviteeTeamName_invites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamProfileParticipantViewFragment frag3 = new TeamProfileParticipantViewFragment();
                Log.i("IdCheck",InvitesArrayList.get(position).getTeam().getId());
                Bundle bundle3 = new Bundle();
                bundle3.putString("teamId",InvitesArrayList.get(position).getTeam().getId() );
                bundle3.putInt("Invited", 1);
                bundle3.putInt("Key", 1);
                frag3.setArguments(bundle3);

                MainActivity activity = (MainActivity) v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, frag3)
                        .addToBackStack(null)
                        .commit();
            }
        });

        holder.ParticipantName_invites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileViewFragment frag3 = new ProfileViewFragment();
                Log.i("IdCheck",InvitesArrayList.get(position).getLeader().getId());
                Bundle bundle3 = new Bundle();
                bundle3.putString("id", InvitesArrayList.get(position).getLeader().getId());
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
        return InvitesArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView ParticipantName_invites, InviteeTeamName_invites;
        private Button Accept, Reject;
        ImageView participantProfilePhoto_invites;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            participantProfilePhoto_invites=itemView.findViewById(R.id.inviteePhoto);
            ParticipantName_invites = itemView.findViewById(R.id.InviteeName);

            InviteeTeamName_invites = itemView.findViewById(R.id.teamName_invite);
            Accept = itemView.findViewById(R.id.acceptButton);
            Reject = itemView.findViewById(R.id.rejectButton);



        }
    }


}