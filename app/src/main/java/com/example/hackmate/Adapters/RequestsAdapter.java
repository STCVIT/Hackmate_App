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

import com.example.hackmate.R;
import com.example.hackmate.Models.RequestsModel;

import java.util.ArrayList;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.Viewholder>{

    private Context context;
    private ArrayList<RequestsModel> RequestsArrayList;

    public RequestsAdapter(Context context, ArrayList<RequestsModel> requestsArrayList) {
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

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        RequestsModel.Requests_Model model = ( RequestsModel.Requests_Model) RequestsArrayList.get(position);
        holder.participantProfilePhoto_req.setImageResource(model.getParticipantphoto_req());
        holder.ParticipantName_req.setText(model.getParticipantName_req());
        holder.InviteeTeamName_req.setText(model.getTeamName_req());
    }

    @Override
    public int getItemCount() {
        return RequestsArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView participantProfilePhoto_req;
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
