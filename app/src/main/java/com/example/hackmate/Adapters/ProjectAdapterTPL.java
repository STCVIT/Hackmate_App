package com.example.hackmate.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.Fragments.EditProjectFragment;
import com.example.hackmate.MainActivity;
import com.example.hackmate.Models.ProjectModel;
import com.example.hackmate.POJOClasses.Kavita.Projects.TeamProject;
import com.example.hackmate.POJOClasses.PtSkill;
import com.example.hackmate.R;

import java.util.ArrayList;
import java.util.List;


public class ProjectAdapterTPL extends RecyclerView.Adapter<ProjectAdapterTPL.Viewholder> {

    private Context context;
    //private ArrayList<ProjectModel> projectModelArrayList;
    private List<TeamProject> projectModelArrayList;
    public  String teamName;
    public ProjectAdapterTPL(Context context, List<TeamProject> projectModelArrayList) {
        this.context = context;
        this.projectModelArrayList = projectModelArrayList;
    }
    public void setProjects(List<TeamProject> projectList,  String teamname) {
        this.projectModelArrayList = projectList;

        this.teamName=teamname;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.projects_row_leader, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

       // ProjectModel model = projectModelArrayList.get(position);
        TeamProject teamProject = projectModelArrayList.get(position);
        Log.i("ProjectSAdapterTPL", teamName);
        Log.i("ProjectSAdapterTPL", teamProject.getId());
        if(teamProject.getName().equals(teamName)) {
            holder.project_nameTextView.setText(teamProject.getName());
            holder.descriptionTextView.setText(teamProject.getProjectDescription());
            holder.bio_textView.setText(teamProject.getProjectDescription());
            holder.link1_textView.setText(teamProject.getCode());
            holder.link2_textView.setText(teamProject.getDesign());
            holder.link3_textView.setText(teamProject.getDemonstration());
        }
       /* holder.project_nameTextView.setText(model.getProjectName());
        holder.descriptionTextView.setText(model.getDescription());
        holder.bio_textView.setText(model.getBio());
        holder.link1_textView.setText(model.getLink1());
        holder.link2_textView.setText(model.getLink2());
        holder.link3_textView.setText(model.getLink3());*/
    }

    @Override
    public int getItemCount() {
        return projectModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        TextView project_nameTextView, descriptionTextView, bio_textView, link1_textView, link2_textView, link3_textView;
        ImageView  editProject;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            project_nameTextView = itemView.findViewById(R.id.project_nameTextView_Leader);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView_Leader);
            bio_textView = itemView.findViewById(R.id.bio_textView_Leader);
            link1_textView = itemView.findViewById(R.id.link1_textView_Leader);
            link2_textView = itemView.findViewById(R.id.link2_textView_Leader);
            link3_textView = itemView.findViewById(R.id.link3_textView_Leader);
            editProject=itemView.findViewById(R.id.editProject_Leader);
            editProject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditProjectFragment frag = new EditProjectFragment();
                    MainActivity activity = (MainActivity) v.getContext();
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment,frag)
                            .addToBackStack(null)
                            .commit();
                }
            });

        }
    }
}

