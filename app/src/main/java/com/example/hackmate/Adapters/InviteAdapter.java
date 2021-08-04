package com.example.hackmate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.Fragments.ProfileViewFragment;
import com.example.hackmate.MainActivity;
import com.example.hackmate.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.ProgramViewHolder>{

    private String[] names;
    private String[][] domains;
    private Context context;
    public InviteAdapter(String[] names, String[][] domains) {
        //this.images=images;
        this.names=names;
        this.domains=domains;
    }

    @NonNull
    @Override
    public InviteAdapter.ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.invite_list,parent,false);
        context = parent.getContext();
        return new InviteAdapter.ProgramViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InviteAdapter.ProgramViewHolder holder, int position) {
        //String showImg = images[position];
        String showName = names[position];
        String[] pt_domains = domains[position];

        holder.name.setText(showName);

        holder.domainGrp.removeAllViews();
        for(int i=0;i<pt_domains.length;i++)
        {
            Chip chip = new Chip(context);
            chip.setText(pt_domains[i]);
            chip.setChipStrokeColorResource(R.color.pill_color);
            chip.setChipBackgroundColor(context.getResources().getColorStateList(R.color.chip_background_color));
            chip.setTextColor(context.getResources().getColorStateList(R.color.chip_text_color));
            chip.setChipStrokeWidth(4);
            chip.setClickable(false);
            holder.domainGrp.addView(chip);
        };

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
                    Toast.makeText(context, "Invitation Sent !!", Toast.LENGTH_SHORT).show();
                }
                /*else
                {
                    holder.invite.setText("INVITE");
                    holder.invite.setTextColor(ContextCompat.getColor(v.getContext(),R.color.text));
                    holder.invite.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.ic_buttongradient));
                }

                 */
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class ProgramViewHolder extends RecyclerView.ViewHolder {
        //ImageView imgIcon;
        TextView name;
        ChipGroup domainGrp;
        AppCompatButton invite;
        public ProgramViewHolder(@NonNull View itemView) {
            super(itemView);
            //imgIcon = itemView.findViewById(R.id.inviteImg);
            name = itemView.findViewById(R.id.inviteName);
            domainGrp = itemView.findViewById(R.id.chipGrp);
            invite = itemView.findViewById(R.id.sendInviteButton);
        }
    }
}
