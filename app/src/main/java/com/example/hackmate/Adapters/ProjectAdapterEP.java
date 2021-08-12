package com.example.hackmate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.Models.ProjectModel;
import com.example.hackmate.POJOClasses.IndividualProject;
import com.example.hackmate.POJOClasses.TeamProject;
import com.example.hackmate.R;

import java.util.ArrayList;
import java.util.List;

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

        if(position - individualProjectsList.size() < 0) {
            IndividualProject individualProject = individualProjectsList.get(position);
            holder.project_nameTextView.setText(individualProject.getNames());
            holder.descriptionTextView.setText("");
            holder.bio_textView.setText(individualProject.getDescriptions());
            holder.link1_textView.setText(individualProject.getCodes());
            holder.link2_textView.setText(individualProject.getDemonstration());
            holder.link3_textView.setText(individualProject.getDesign());
        }
        else{
            TeamProject teamProject = teamProjectList.get(position - individualProjectsList.size());
            holder.project_nameTextView.setText(teamProject.getProject_name());
            holder.descriptionTextView.setText(teamProject.getNames());
            holder.bio_textView.setText(teamProject.getProject_description());
            holder.link1_textView.setText(teamProject.getCodes());
            holder.link2_textView.setText(teamProject.getDemonstration());
            holder.link3_textView.setText(teamProject.getDesign());
        }

    }

    @Override
    public int getItemCount() {
        return individualProjectsList.size() + teamProjectList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        TextView project_nameTextView, descriptionTextView, bio_textView, link1_textView, link2_textView, link3_textView;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            project_nameTextView = itemView.findViewById(R.id.project_nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            bio_textView = itemView.findViewById(R.id.bio_textView);
            link1_textView = itemView.findViewById(R.id.link1_textView);
            link2_textView = itemView.findViewById(R.id.link2_textView);
            link3_textView = itemView.findViewById(R.id.link3_textView);

        }
    }
}
