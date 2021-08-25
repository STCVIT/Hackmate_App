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

<<<<<<< Updated upstream
=======
import com.bumptech.glide.Glide;
import com.example.hackmate.Fragments.ProfileViewFragment;
import com.example.hackmate.Fragments.TeamProfileParticipantViewFragment;
import com.example.hackmate.JSONPlaceholders.JSONPlaceHolderAPI;
import com.example.hackmate.MainActivity;
>>>>>>> Stashed changes
import com.example.hackmate.POJOClasses.Kavita.Requests.ReceivedRequest;
import com.example.hackmate.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.Viewholder>{

    private Context context;
    private  List<ReceivedRequest> RequestsArrayList;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String idToken = "Bearer ";
    public String UserName1;
    public String ParticiapntName1;

    public RequestsAdapter(Context context, List<ReceivedRequest> requestsArrayList) {
        this.context = context;
        RequestsArrayList = requestsArrayList;
    }
    public void setMyRequests(List<ReceivedRequest> sent_objs) {
        this.RequestsArrayList  =  sent_objs;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request, parent, false);
        return new Viewholder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        ReceivedRequest receivedRequest = RequestsArrayList.get(position);
        //Sent sents = RequestsArrayList.get(position);
UserName1=receivedRequest.getParticipant().getName();
        holder.ParticipantName_req.setText(UserName1);
        holder.InviteeTeamName_req.setText(receivedRequest.getTeam().getName());
<<<<<<< Updated upstream
=======
        Glide.with(context).
                load(RequestsArrayList.get(position).getParticipant().getPhoto()).
                placeholder(R.drawable.download).
                into(holder.participantProfilePhoto_req);
        JSONPlaceHolderAPI jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);
        holder.Accept_req.setOnClickListener(v -> {

            Call<Void> call5 = jsonPlaceHolderAPI.postRequestStatus(MainActivity.getIdToken(),"accepted",receivedRequest.getReq());
            call5.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call5, Response<Void> response5) {

                    if (!response5.isSuccessful()){
                        Log.i("sucess", "sucess");
                    }
                    //AcceptPOJO acceptPOJO1 = response5.body();
                    Log.i("requestAdapter", String.valueOf(response5.code()));
                    Log.i("requestAdapter",String.valueOf(response5.body()));
                    Toast.makeText(context, "You have accepted this request", Toast.LENGTH_SHORT).show();
                    int newPosition = holder.getAdapterPosition();
                    RequestsArrayList.remove(newPosition);
                    notifyItemRemoved(newPosition);
                    notifyItemRangeChanged(newPosition,RequestsArrayList.size());


                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.i("error", t.getMessage());
                }
            });

        });

        holder.Remove_req.setOnClickListener(v -> {
            Call<Void> call5 = jsonPlaceHolderAPI.postRequestStatus(MainActivity.getIdToken(),"rejected",receivedRequest.getReq());
            call5.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call5, Response<Void> response5) {

                    if (!response5.isSuccessful()){
                        Log.i("sucess", "sucess");
                    }

                    Log.i("inviteAdapter", String.valueOf(response5.code()));
                    Log.i("inviteAdapter",String.valueOf(response5.body()));
                    Toast.makeText(context, "this request has been removed", Toast.LENGTH_SHORT).show();

                    int newPosition = holder.getAdapterPosition();
                    RequestsArrayList.remove(newPosition);
                    notifyItemRemoved(newPosition);
                    notifyItemRangeChanged(newPosition, RequestsArrayList.size());


                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.i("error", t.getMessage());
                }
            });

        });

        holder.InviteeTeamName_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamProfileParticipantViewFragment frag3 = new TeamProfileParticipantViewFragment();
                Log.i("IdCheck",RequestsArrayList.get(position).getTeam().getId());
                Bundle bundle3 = new Bundle();
                bundle3.putString("teamId",RequestsArrayList.get(position).getTeam().getId() );
                frag3.setArguments(bundle3);

                MainActivity activity = (MainActivity) v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, frag3)
                        .addToBackStack(null)
                        .commit();
            }
        });

        holder.ParticipantName_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileViewFragment frag3 = new ProfileViewFragment();
                Log.i("IdCheck",RequestsArrayList.get(position).getParticipant().getId());
                Bundle bundle3 = new Bundle();
                bundle3.putString("id", RequestsArrayList.get(position).getParticipant().getId());
                frag3.setArguments(bundle3);

                MainActivity activity = (MainActivity) v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, frag3)
                        .addToBackStack(null)
                        .commit();
            }
        });
>>>>>>> Stashed changes

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

        ImageView participantProfilePhoto_req;
        private TextView ParticipantName_req,InviteeTeamName_req;
        private Button Accept_req,Remove_req;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

             participantProfilePhoto_req=itemView.findViewById(R.id.RequestPhoto);
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
