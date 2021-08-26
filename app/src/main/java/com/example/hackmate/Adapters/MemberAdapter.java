package com.example.hackmate.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hackmate.Fragments.ProfileViewFragment;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.PtSkill;
import com.example.hackmate.R;

import java.util.ArrayList;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.Viewholder> {

    private Context context;
    private List<PtSkill> ptSkills;
    String admin_id;

    public MemberAdapter(Context context, List<PtSkill> ptSkills) {
        this.context = context;
        this.ptSkills = ptSkills;
    }

    public void setJoinTeam(List<PtSkill> ptSkills, String admin_id) {
        this.ptSkills = ptSkills;
        this.admin_id = admin_id;
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

        PtSkill ptSkill = ptSkills.get(position);

        Glide.with(context).
                load(ptSkill.getParticipant().getPhoto()).
                placeholder(R.drawable.download).
                into(holder.profile_imageView);
        holder.number_textView.setText(String.valueOf(position+1));
        holder.nameTextView.setText(ptSkill.getParticipant().getName());
        holder.email_textView.setText(ptSkill.getParticipant().getEmail());
        if(ptSkill.getParticipant().get_id().equals(admin_id)){
            holder.designation_textView.setText("Leader");
        } else{
            holder.designation_textView.setText("");
        }
//        if(ptSkill.getParticipant())
//        holder.designation_textView.setText(model.getMemPosition());
//        holder.profile_imageView.setImageResource(model.getProfilephoto());

        holder.team_member_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileViewFragment frag = new ProfileViewFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("Key", 1);
                bundle.putString("id", ptSkill.getParticipant().get_id());
                frag.setArguments(bundle);

                MainActivity activity = (MainActivity) v.getContext();

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, frag)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ptSkills.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView number_textView, nameTextView, designation_textView, email_textView;
        private ImageView profile_imageView;
        CardView team_member_card;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            number_textView = itemView.findViewById(R.id.number_textView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            designation_textView = itemView.findViewById(R.id.designation_textView);
            email_textView = itemView.findViewById(R.id.email_textView);
            team_member_card = itemView.findViewById(R.id.team_member_card);
            profile_imageView = itemView.findViewById(R.id.profile_imageView);
        }
    }


}
