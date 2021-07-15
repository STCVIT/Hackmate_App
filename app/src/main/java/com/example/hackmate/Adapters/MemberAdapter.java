package com.example.hackmate.Adapters;

import android.app.Person;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.Fragments.ParticipantProfileFragment;
import com.example.hackmate.Fragments.PersonProfileFragment;
import com.example.hackmate.Fragments.TeamsFragment;
import com.example.hackmate.MainActivity;
import com.example.hackmate.R;
import com.example.hackmate.teamMemberAdapter;
import com.example.hackmate.teamMember_Model;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.Viewholder> {

    private Context context;
    private ArrayList<teamMember_Model> teamMemberModelArrayList;

    public MemberAdapter(Context context, ArrayList<teamMember_Model> teamMemberModelArrayList) {
        this.context = context;
        this.teamMemberModelArrayList = teamMemberModelArrayList;
    }


    @NonNull
    @Override
    public MemberAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_member_row, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.Viewholder holder, int position) {

        teamMember_Model.TeamMemberModel model = (teamMember_Model.TeamMemberModel) teamMemberModelArrayList.get(position);
        holder.number_textView.setText(model.getSerialNo());
        holder.nameTextView.setText(model.getMemName());
        holder.email_textView.setText(model.getMemEmail());
        holder.designation_textView.setText(model.getMemPosition());
        holder.profile_imageView.setImageResource(model.getProfilephoto());

        holder.team_member_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonProfileFragment frag = new PersonProfileFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("Key", 1);
                frag.setArguments(bundle);

                MainActivity activity = (MainActivity) v.getContext();

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,frag)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return teamMemberModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        private TextView number_textView,nameTextView,designation_textView,email_textView;
        private ImageView profile_imageView;
        CardView team_member_card;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            number_textView = itemView.findViewById(R.id.number_textView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            designation_textView = itemView.findViewById(R.id.designation_textView);
            email_textView = itemView.findViewById(R.id.email_textView);
            team_member_card = itemView.findViewById(R.id.team_member_card);
            profile_imageView=itemView.findViewById(R.id.profile_imageView);
        }
    }


}
