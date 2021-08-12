package com.example.hackmate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.Models.ProjectModel;
import com.example.hackmate.POJOClasses.Kavita.Projects.TeamProject;
import com.example.hackmate.R;

import java.util.ArrayList;
import java.util.List;


public class ProjectAdapterTP extends RecyclerView.Adapter<ProjectAdapterTP.Viewholder> {

    private Context context;
    //private ArrayList<ProjectModel> projectParticipantModelArrayList;
    private List<TeamProject> projectParticipantModelArrayList;
    public  String teamName;
    public ProjectAdapterTP(Context context, List<TeamProject> projectParticipantModelArrayList) {
        this.context = context;
        this.projectParticipantModelArrayList = projectParticipantModelArrayList;
    }

    public void setProjectMember(List<TeamProject> projectList, String teamname) {
        this.projectParticipantModelArrayList = projectList;

        this.teamName=teamname;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.projects_row, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

       // ProjectModel model = projectParticipantModelArrayList.get(position);
        TeamProject teamProject = projectParticipantModelArrayList.get(position);
        if(teamProject.getName().equals(teamName)) {

            holder.project_nameTextView.setText(teamProject.getName());
            holder.descriptionTextView.setText(teamProject.getProjectDescription());
            holder.bio_textView.setText(teamProject.getProjectDescription());
            holder.link1_textView.setText(teamProject.getCode());
            holder.link2_textView.setText(teamProject.getDesign());
            holder.link3_textView.setText(teamProject.getDemonstration());
        }
        /*holder.project_nameTextView.setText(model.getProjectName());
        holder.descriptionTextView.setText(model.getDescription());
        holder.bio_textView.setText(model.getBio());
        holder.link1_textView.setText(model.getLink1());
        holder.link2_textView.setText(model.getLink2());
        holder.link3_textView.setText(model.getLink3());*/

    }

    @Override
    public int getItemCount() {
        return projectParticipantModelArrayList.size();
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



