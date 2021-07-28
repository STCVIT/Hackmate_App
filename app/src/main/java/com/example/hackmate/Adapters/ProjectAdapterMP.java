package com.example.hackmate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.Models.ProjectModel;
import com.example.hackmate.R;

import java.util.ArrayList;

public class ProjectAdapterMP extends RecyclerView.Adapter<ProjectAdapterMP.Viewholder> {

    private Context context;
    private ArrayList<ProjectModel> projectModelArrayList;

    public ProjectAdapterMP(Context context, ArrayList<ProjectModel> projectModelArrayList) {
        this.context = context;
        this.projectModelArrayList = projectModelArrayList;
    }

    @NonNull
    @Override
    public ProjectAdapterMP.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.projects_row, parent, false);
        return new ProjectAdapterMP.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectAdapterMP.Viewholder holder, int position) {

        ProjectModel model = projectModelArrayList.get(position);
        holder.project_nameTextView.setText(model.getProjectName());
        holder.descriptionTextView.setText(model.getDescription());
        holder.bio_textView.setText(model.getBio());
        holder.link1_textView.setText(model.getLink1());
        holder.link2_textView.setText(model.getLink2());
        holder.link3_textView.setText(model.getLink3());

    }

    @Override
    public int getItemCount() {
        return projectModelArrayList.size();
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
