package com.example.hackmate.Fragments.FindParticipant;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.Fragments.ProfileViewFragment;
import com.example.hackmate.JSONPlaceholders.API;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.FindParticipant.FinalPt;
import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.gms.common.api.Api;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InviteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private List<FinalPt> InviteArrayList;
    private Context context;
    private String name, teamId;
    private API api;
    private List<String> ptId;

    public InviteAdapter(Context context, List<FinalPt> inviteArrayList, String teamId, List<String> ptId) {
        InviteArrayList = inviteArrayList;
        this.context = context;
        this.teamId = teamId;
        this.ptId = ptId;
    }

    public void addItems(List<FinalPt> list, String name) {
        int position = this.InviteArrayList.size();

        List<FinalPt> list1 = list;
        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < ptId.size(); j++) {
                if (list1.get(i).getParticipant().get_id().equals(ptId.get(j))) {
                    list.remove(i);
                    break;
                }
            }
        }

        this.InviteArrayList.addAll(list);
        this.name = name;
        notifyItemRangeInserted(position, list.size());
    }


    @Override
    public int getItemViewType(int position) {
        if (InviteArrayList.get(position) == null)
            return VIEW_TYPE_LOADING;
        return VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM)
            return new ProgramViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.invite_list, parent, false));
        else
            return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProgramViewHolder)
            populateItem((ProgramViewHolder) holder, position);
    }

    public void populateItem(ProgramViewHolder holder, int position) {

        holder.name.setText(InviteArrayList.get(position).getParticipant().getName());

        List<String> skills = new ArrayList<String>();
        int length_skills = InviteArrayList.get(position).getSkills().size();
        if (length_skills > 0) {
            if (name.isEmpty())
                skills.add(InviteArrayList.get(position).getSkills().get(0).getSkill());
            else
                skills.add(name);
            if (length_skills > 1)
                skills.add("+" + String.valueOf(length_skills - 1) + " More");
        }

        holder.domainGrp.removeAllViews();
        for (String s : skills) {
            Chip chip = new Chip(context);
            chip.setText(s);
            chip.setChipStrokeColorResource(R.color.pill_color);
            chip.setChipBackgroundColor(context.getResources().getColorStateList(R.color.chip_background_color));
            chip.setTextColor(context.getResources().getColorStateList(R.color.chip_text_color));
            chip.setChipStrokeWidth(4);
            chip.setClickable(false);
            holder.domainGrp.addView(chip);
        }

        holder.itemView.setOnClickListener(v -> {
            ProfileViewFragment frag = new ProfileViewFragment();

            MainActivity activity = (MainActivity) v.getContext();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, frag)
                    .addToBackStack(null)
                    .commit();
        });

        /*
        for(int i=0;i<names.size();i++) {
            if (InviteArrayList.get(position).getParticipant().get_id().equals(names.get(i))); {
                Log.i("PARTICIPANT_CHECK",InviteArrayList.get(position).getParticipant().getName());
                holder.invite.setText("INVITED");
                holder.invite.setTextColor(ContextCompat.getColor(context, R.color.pill_color));
                holder.invite.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_invited));
                break;
            }
        }

         */


        holder.invite.setOnClickListener(v -> {
            if (holder.invite.getText().toString().matches("INVITE")) {
                api = RetrofitInstance.getRetrofitInstance().create(API.class);
                Log.i(TAG, "populateItem: ");

                Call<Void> calls = api.sendInvitation(MainActivity.getIdToken(), teamId, InviteArrayList.get(position).getParticipant()._id);
                calls.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> calls, Response<Void> response) {
                        Log.i(TAG, "onResponse: =>" + response.code());
                        if (response.isSuccessful() && response.code() == 201) {
                            holder.invite.setText("INVITED");
                            holder.invite.setTextColor(ContextCompat.getColor(v.getContext(), R.color.pill_color));
                            holder.invite.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_invited));
                            Toast.makeText(context, "Invitation Sent !!", Toast.LENGTH_SHORT).show();
                        } else if (response.isSuccessful() && response.code() == 400) {
                            Toast.makeText(context, "Already in a team!!", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(context, "Already invited", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> calls, Throwable t) {

                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return InviteArrayList.size();
    }


    public void showProgress() {
        Log.i(TAG, "showProgress: ");
        if (!InviteArrayList.contains(null)) {
            InviteArrayList.add(null);
            notifyItemInserted(InviteArrayList.size() - 1);
        }
    }

    private static final String TAG = "InviteAdapter";

    public void removeProgress() {
        int position = InviteArrayList.indexOf(null);
        notifyItemRemoved(position);
        InviteArrayList.remove(null);
    }


    public void clearList() {
        InviteArrayList.clear();
        InviteArrayList.add(null);
    }

    public List<FinalPt> getList() {
        return this.InviteArrayList;
    }

    public static class ProgramViewHolder extends RecyclerView.ViewHolder {
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

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NotNull View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar);
        }
    }
}
