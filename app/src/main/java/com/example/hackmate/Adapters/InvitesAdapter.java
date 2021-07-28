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
import com.example.hackmate.POJOClassesKavita.PtSkill;
import com.example.hackmate.POJOClassesKavita.Received;
import com.example.hackmate.POJOClassesKavita.teamIdPOJO;
import com.example.hackmate.R;
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

    public void setMyInvites(List<Received> recieved_objs) {
        this.InvitesArrayList = recieved_objs;
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
        String id1 = InvitesArrayList.get(position).getParticipantId();
        Log.i("the id we actually get", id1);
        String id2 = InvitesArrayList.get(position).getTeamId();
        Log.i("the teamidweactuallyget", id2);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient1 = new OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                //.addInterceptor(loggingInterceptor1)
                .build();
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl("https://hackportalbackend.herokuapp.com/")
                .client(okHttpClient1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mAuth.getCurrentUser().getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            idToken += task.getResult().getToken();
                            Log.i("xx", idToken);
                            // Send token to your backend via HTTPS
                            // ...
                           // myTeamsAPI myTeamsAPI = retrofit1.create(myTeamsAPI.class);
                            JSONPlaceHolderAPI jsonPlaceHolderAPI = retrofit1.create(JSONPlaceHolderAPI.class);
                            Call<teamIdPOJO> call1 = jsonPlaceHolderAPI.getTeamId(idToken, recieveds.getTeamId());
                            Log.i("callback problemmyTeams", "errorMyTeams");
                            call1.enqueue(new Callback<teamIdPOJO>() {
                                @Override
                                public void onResponse(Call<teamIdPOJO> call1, Response<teamIdPOJO> response1) {
                                    Log.i("callback problem3MT", "error3MT");
                                    if (!response1.isSuccessful()) {
                                        Log.i("not sucess1", "code: " + response1.code());
                                        return;
                                    }
                                    teamIdPOJO teamIdPOJOS = response1.body();
                                    Log.i("response sucess", String.valueOf(teamIdPOJOS));
                                    // adminID2=teamIdPOJOS.getTeam1().getAdmin_id();
                                    //if(adminID2==id1)
                                    ParticiapntName = teamIdPOJOS.getTeam1().getName();
                                    //myTeamsPOJO myTeamsPOJOS =  response1.body();
                                    //Log.i("Response body",ParticiapntName);
                                    //List<Final2> final_objs2 = myTeamsPOJOS.getFinal2();
                                    List<PtSkill> skill_objs2 = teamIdPOJOS.getPt_skills();
                                    adminID = skill_objs2.get(position).getParticipant().get_id();
                                    //if(adminID==id1)
                                    UserName = response1.body().getPt_skills().get(position).getParticipant().getName();

                                    Log.i("Response body3", "list sending to adapter sucessfull");
                                    holder.ParticipantName_invites.setText(UserName);
                                    Log.i("hope not null", UserName);
                                    holder.InviteeTeamName_invites.setText(ParticiapntName);
                                    Log.i("hope not null", ParticiapntName);
                                }

                                @Override
                                public void onFailure(Call<teamIdPOJO> call1, Throwable t) {
                                    Log.i("failed1", t.getMessage());
                                }
                            });


                        }
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
