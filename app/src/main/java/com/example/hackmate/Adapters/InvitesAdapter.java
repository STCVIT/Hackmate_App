package com.example.hackmate.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.JSONPlaceholders.JSONPlaceHolderAPI;
import com.example.hackmate.POJOClasses.Kavita.Invites.AcceptPOJO;
import com.example.hackmate.POJOClasses.Kavita.Invites.invitePOJO;
import com.example.hackmate.POJOClasses.PtSkill;
import com.example.hackmate.POJOClasses.Kavita.Invites.Received;
import com.example.hackmate.POJOClasses.Kavita.teamIdPOJO;
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
    private String idToken = "Bearer ";
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
        holder.Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AcceptPOJO acceptPOJO = new AcceptPOJO(recieveds.getInv().getId().toString(),"accepted");
                JSONPlaceHolderAPI jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);
                Call<Void> call5 = jsonPlaceHolderAPI.postInviteStatus(idToken,recieveds.getInv().getId());
                call5.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call5, Response<Void> response5) {

                        if (!response5.isSuccessful()){
                            Log.i("sucess", "sucess");
                        }
                        //AcceptPOJO acceptPOJO1 = response5.body();
                        Log.i("inviteAdapter", String.valueOf(response5.code()));
                        Log.i("inviteAdapter",String.valueOf(response5.body()));


                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.i("error", t.getMessage());
                    }
                });

            }
        });
    }


    @Override
    public int getItemCount() {
        return InvitesArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        //private ImageView participantProfilePhoto_invites;
        private TextView ParticipantName_invites, InviteeTeamName_invites;
        private Button Accept, Reject;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            //participantProfilePhoto_invites=itemView.findViewById(R.id.inviteePhoto);
            ParticipantName_invites = itemView.findViewById(R.id.InviteeName);

            InviteeTeamName_invites = itemView.findViewById(R.id.teamName_invite);
            Accept = itemView.findViewById(R.id.acceptButton);
            Reject = itemView.findViewById(R.id.rejectButton);

           /* Accept.setOnClickListener(v ->

                    Toast.makeText(context, "You have accepted this invite", Toast.LENGTH_SHORT).show());*/
            Reject.setOnClickListener(v -> Toast.makeText(context, "You have rejected this invite", Toast.LENGTH_SHORT).show());

        }
    }


}
