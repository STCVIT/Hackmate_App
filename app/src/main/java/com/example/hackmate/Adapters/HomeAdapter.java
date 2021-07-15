package com.example.hackmate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.HomeModel;
import com.example.hackmate.R;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Viewholder> {
    private OnHackListerner mOnHackListener;
    private Context context;
    private ArrayList<HomeModel> HomeArrayList;


    public HomeAdapter(Context context, ArrayList<HomeModel> homeArrayList, OnHackListerner onHackListerner) {
        this.context = context;
        HomeArrayList = homeArrayList;
        this.mOnHackListener=onHackListerner;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_hack_info_rv_layout, parent, false);
        return new Viewholder(view,mOnHackListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        // Get the data model based on position

        HomeModel.home_Model model = (HomeModel.home_Model) HomeArrayList.get(position);
        holder.Profilephoto_home.setImageResource(model.getHackphoto());
        holder.HackName.setText(model.getHackName());
        holder.teamSize.setText(model.getTeamSize());
        holder.startDate.setText(model.getStartDate());
        holder.endDate.setText(model.getEndDate());

    }

    @NonNull
    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return HomeArrayList.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private ImageView Profilephoto_home;
        private TextView HackName, teamSize, startDate,endDate;
        OnHackListerner onHackListerner;

        public Viewholder(@NonNull View itemView,OnHackListerner onHackListerner) {
            super(itemView);

            Profilephoto_home=itemView.findViewById(R.id.HackImage);
            HackName=itemView.findViewById(R.id.HackName_Home);
            teamSize=itemView.findViewById(R.id.teamSize_input);
            startDate=itemView.findViewById(R.id.startDate_input);
            endDate=itemView.findViewById(R.id.endDate_input);
this.onHackListerner=onHackListerner;
itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
onHackListerner.OnHackClick(getAdapterPosition());
        }
    }

    public interface OnHackListerner{
        void OnHackClick(int position); //using this interface to interpret the click and then send  to fragment to get position of that clicked item
    }


}
