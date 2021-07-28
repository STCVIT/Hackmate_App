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
import com.example.hackmate.POJOClassesKavita.Sent;
import com.example.hackmate.POJOClassesKavita.participantPOJO;
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

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.Viewholder>{

    private Context context;
    private  List<Sent> RequestsArrayList;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String idToken = "Bearer ";
    public String UserName1;
    public String ParticiapntName1;

    public RequestsAdapter(Context context, List<Sent> requestsArrayList) {
        this.context = context;
        RequestsArrayList = requestsArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request, parent, false);
        return new Viewholder(view);
    }

    public void setMyRequests(List<Sent> sent_objs) {
        this.RequestsArrayList  =  sent_objs;
        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Sent sents = RequestsArrayList.get(position);

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
                            //myTeamsAPI myTeamsAPI = retrofit1.create(myTeamsAPI.class);
                            JSONPlaceHolderAPI jsonPlaceHolderAPI = retrofit1.create(JSONPlaceHolderAPI.class);
                            Call<teamIdPOJO> call1 = jsonPlaceHolderAPI.getTeamId(idToken,sents.getTeamId());
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
                                    ParticiapntName1 = teamIdPOJOS.getTeam1().getName();
                                    //myTeamsPOJO myTeamsPOJOS =  response1.body();
                                    //Log.i("Response body",ParticiapntName);
                                    //List<Final2> final_objs2 = myTeamsPOJOS.getFinal2();

                                    Call<participantPOJO> call2 = jsonPlaceHolderAPI.getParticiapnt(idToken,sents.getParticipantId());
                                    Log.i("callback problemP", "errorP");
                                    call2.enqueue(new Callback<participantPOJO>() {
                                        @Override
                                        public void onResponse(Call<participantPOJO> call2, Response<participantPOJO> response2) {
                                            Log.i("callback problem3P", "error3P");
                                            if (!response1.isSuccessful()) {
                                                Log.i("not sucess1", "code: " + response1.code());
                                                return;
                                            }
                                            participantPOJO participantPOJOS = response2.body();
                                            Log.i("response sucess", String.valueOf(teamIdPOJOS));

                                           UserName1=participantPOJOS.getParticipant1().getName();
                                            //UserName1 = response1.body().getPt_skills().get(position).getParticipant().getName();

                                            Log.i("Response body3", "list sending to adapter sucessfull");
                                            holder.ParticipantName_req.setText(UserName1);
                                            Log.i("hope not null", UserName1);

                                        }

                                        @Override
                                        public void onFailure(Call<participantPOJO> call2, Throwable t) {
                                            Log.i("failed1", t.getMessage());
                                        }
                                    });

                                   // List<PtSkill> skill_objs2 = teamIdPOJOS.getPt_skills();
                                   // adminID = skill_objs2.get(position).getParticipant().get_id();
                                    //if(adminID==id1)
                                    //UserName1 = response1.body().getPt_skills().get(position).getParticipant().getName();

                                    //Log.i("Response body3", "list sending to adapter sucessfull");
                                   // holder.ParticipantName_req.setText(RequestsArrayList.get(position).getParticipantId());
                                    //Log.i("hope not null", UserName1);
                                    holder.InviteeTeamName_req.setText(ParticiapntName1);
                                    Log.i("hope not null", ParticiapntName1);
                                }

                                @Override
                                public void onFailure(Call<teamIdPOJO> call1, Throwable t) {
                                    Log.i("failed1", t.getMessage());
                                }
                            });


                        }
                    }
                });


        //RequestsModel.Requests_Model model = ( RequestsModel.Requests_Model) RequestsArrayList.get(position);
        //holder.participantProfilePhoto_req.setImageResource(model.getParticipantphoto_req());
        //holder.ParticipantName_req.setText(model.getParticipantName_req());
        //holder.InviteeTeamName_req.setText(model.getTeamName_req());
    }

    @Override
    public int getItemCount() {
        return RequestsArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

       // private ImageView participantProfilePhoto_req;
        private TextView ParticipantName_req,InviteeTeamName_req;
        private Button Accept_req,Remove_req;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

           // participantProfilePhoto_req=itemView.findViewById(R.id.RequestPhoto);
            ParticipantName_req=itemView.findViewById(R.id.RequestName);

            InviteeTeamName_req=itemView.findViewById(R.id.teamName_req);
            Accept_req=itemView.findViewById(R.id.acceptButton_req);
            Remove_req=itemView.findViewById(R.id.removeButton_req);

            Accept_req.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "You have accepted this request", Toast.LENGTH_SHORT).show();
                }
            });
            Remove_req.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "this request has been removed", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
