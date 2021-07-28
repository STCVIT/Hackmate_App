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


public class ProjectAdapterTP extends RecyclerView.Adapter<ProjectAdapterTP.Viewholder> {

    private Context context;
    private ArrayList<ProjectModel> projectParticipantModelArrayList;

    public ProjectAdapterTP(Context context, ArrayList<ProjectModel> projectParticipantModelArrayList) {
        this.context = context;
        this.projectParticipantModelArrayList = projectParticipantModelArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.projects_row, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        ProjectModel model = projectParticipantModelArrayList.get(position);
        holder.project_nameTextView.setText(model.getProjectName());
        holder.descriptionTextView.setText(model.getDescription());
        holder.bio_textView.setText(model.getBio());
        holder.link1_textView.setText(model.getLink1());
        holder.link2_textView.setText(model.getLink2());
        holder.link3_textView.setText(model.getLink3());

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



