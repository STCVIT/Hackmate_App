package com.example.hackmate.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.JSONPlaceholders.JSONPlaceHolderAPI;
import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.IndividualProject;
import com.example.hackmate.POJOClasses.TeamProject;
import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectAdapterEP extends RecyclerView.Adapter<ProjectAdapterEP.Viewholder> {

    private Context context;
    private List<IndividualProject> individualProjectsList;
    private List<TeamProject> teamProjectList;

    public ProjectAdapterEP(Context context, List<IndividualProject> individualProjectsList, List<TeamProject> teamProjectList) {
        this.context = context;
        this.individualProjectsList = individualProjectsList;
        this.teamProjectList = teamProjectList;
    }

    public void setGetProjectEP(List<IndividualProject> individualProjectsList, List<TeamProject> teamProjectList) {
        this.individualProjectsList = individualProjectsList;
        this.teamProjectList = teamProjectList;

    }

    @NonNull
    @Override
    public ProjectAdapterEP.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.projects_row, parent, false);
        return new ProjectAdapterEP.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectAdapterEP.Viewholder holder, int position) {

        if (position - individualProjectsList.size() < 0) {
            IndividualProject individualProject = individualProjectsList.get(position);
            holder.project_nameTextView.setText(individualProject.getNames());
            holder.descriptionTextView.setText("Personal");
            holder.bio_textView.setText(individualProject.getDescriptions());
            holder.link1_textView.setText(individualProject.getCodes());
            holder.link2_textView.setText(individualProject.getDemonstration());
            holder.link3_textView.setText(individualProject.getDesign());
            holder.deleteProject.setVisibility(View.VISIBLE);
        } else {
            TeamProject teamProject = teamProjectList.get(position - individualProjectsList.size());
            holder.project_nameTextView.setText(teamProject.getProject_name());
            holder.descriptionTextView.setText(teamProject.getNames());
            holder.bio_textView.setText(teamProject.getProject_description());
            holder.link1_textView.setText(teamProject.getCodes());
            holder.link2_textView.setText(teamProject.getDemonstration());
            holder.link3_textView.setText(teamProject.getDesign());
        }
        loginAPI loginAPI = RetrofitInstance.getRetrofitInstance().create(loginAPI.class);

        holder.deleteProject.setOnClickListener(view -> {
            final AlertDialog.Builder alert = new AlertDialog.Builder(context);
            View mView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_box, null);
            TextView dialogText = mView.findViewById(R.id.dialogText);
            MaterialButton delete = (MaterialButton) mView.findViewById(R.id.signOut);
            MaterialButton cancel = (MaterialButton) mView.findViewById(R.id.No);

            dialogText.setText("Are you sure you want to delete ?");
            delete.setText("DELETE");

            alert.setView(mView);

            final AlertDialog alertDialog = alert.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();

            delete.setOnClickListener(v -> {
                int newPos = holder.getBindingAdapterPosition();
                alertDialog.dismiss();
                Call<Map<String, Object>> call1 = loginAPI.delProject("Bearer " + MainActivity.getIdToken(),
                        individualProjectsList.get(newPos).get_ids());
                call1.enqueue(new Callback<Map<String, Object>>() {
                    @Override
                    public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
//                            Log.i(TAG, "Del proj" + String.valueOf(response.body()));
                        individualProjectsList.remove(newPos);
                        notifyItemRemoved(newPos);

                    }

                    @Override
                    public void onFailure(Call<Map<String, Object>> call, Throwable t) {

                    }
                });
            });

            cancel.setOnClickListener(v -> alertDialog.dismiss());
        });

    }

    @Override
    public int getItemCount() {
        return individualProjectsList.size() + teamProjectList.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder {

        TextView project_nameTextView, descriptionTextView, bio_textView,
                link1_textView, link2_textView, link3_textView, deleteProject;
        CardView cardView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            project_nameTextView = itemView.findViewById(R.id.project_nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            bio_textView = itemView.findViewById(R.id.bio_textView);
            link1_textView = itemView.findViewById(R.id.link1_textView);
            link2_textView = itemView.findViewById(R.id.link2_textView);
            link3_textView = itemView.findViewById(R.id.link3_textView);
            cardView = itemView.findViewById(R.id.cardView_Projects);
            deleteProject = itemView.findViewById(R.id.deleteProject);
            deleteProject.setVisibility(View.GONE);
        }
    }
}
