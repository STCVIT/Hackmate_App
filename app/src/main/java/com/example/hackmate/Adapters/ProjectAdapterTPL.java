package com.example.hackmate.Adapters;

import android.content.Context;
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
import com.example.hackmate.R;

import java.util.ArrayList;


public class ProjectAdapterTPL extends RecyclerView.Adapter<ProjectAdapterTPL.Viewholder> {

    private Context context;
    private ArrayList<ProjectModel> projectModelArrayList;

    public ProjectAdapterTPL(Context context, ArrayList<ProjectModel> projectModelArrayList) {
        this.context = context;
        this.projectModelArrayList = projectModelArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.projects_row_leader, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

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

