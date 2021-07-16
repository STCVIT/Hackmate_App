package com.example.hackmate.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.Fragments.ProfileViewFragment;
import com.example.hackmate.MainActivity;
import com.example.hackmate.R;
import com.google.android.material.chip.Chip;

public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.ProgramViewHolder>{

    private String[] names,domains;
    public InviteAdapter(String[] names, String[] domains) {
        //this.images=images;
        this.names=names;
        this.domains=domains;
    }

    @NonNull
    @Override
    public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.invite_list,parent,false);
        return new ProgramViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramViewHolder holder, int position) {
        //String showImg = images[position];
        String showName = names[position];
        String showDomain = domains[position];

        holder.name.setText(showName);
        holder.domain.setText(showDomain);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileViewFragment frag = new ProfileViewFragment();

                MainActivity activity = (MainActivity) v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,frag)
                        .addToBackStack(null)
                        .commit();
            }
        });

        holder.invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.invite.getText().toString().matches("INVITE"))
                {
                    holder.invite.setText("INVITED");
                    holder.invite.setTextColor(ContextCompat.getColor(v.getContext(),R.color.pill_color));
                    holder.invite.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.ic_invited));
                }
                else
                {
                    holder.invite.setText("INVITE");
                    holder.invite.setTextColor(ContextCompat.getColor(v.getContext(),R.color.text));
                    holder.invite.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.ic_buttongradient));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class ProgramViewHolder extends RecyclerView.ViewHolder {
        //ImageView imgIcon;
        TextView name;
        Chip domain;
        AppCompatButton invite;
        public ProgramViewHolder(@NonNull View itemView) {
            super(itemView);
            //imgIcon = itemView.findViewById(R.id.inviteImg);
            name = itemView.findViewById(R.id.inviteName);
            domain = itemView.findViewById(R.id.inviteDomain);
            invite = itemView.findViewById(R.id.sendInviteButton);
        }
    }
}
