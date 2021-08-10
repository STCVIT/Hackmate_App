package com.example.hackmate.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hackmate.Fragments.HackProfile.HackProfileFragment;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.Kavita.Final;
import com.example.hackmate.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Viewholder> {
    private Context context;
    private List<Final> HomeArrayList;
    private String id;


    public HomeAdapter(Context context, List<Final> homeArrayList) {
        this.context = context;
        HomeArrayList = homeArrayList;
    }



    public void setHackList(List<Final> final_objs) {
        //ArrayList HomeArrayList1 = (ArrayList) HomeAL.finals;
        this.HomeArrayList = final_objs;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_hack_info_rv_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Final  finals = HomeArrayList.get(position);
//FinalPt FinalPt = HomeArrayList.get(position);
//HomeModel.home_Model model = (HomeModel.home_Model) HomeArrayList.get(position);

        //int photo=Integer.parseInt(HomeArrayList.get(position).getHackPhoto());
        //holder.Profilephoto_home.setImageResource(photo);

        holder.HackName.setText(HomeArrayList.get(position).getName());
        holder.teamSizeMax.setText(String.valueOf(HomeArrayList.get(position).getMaxTeamSize()));
        holder.teamSizeMin.setText(String.valueOf(HomeArrayList.get(position).getMinTeamSize()));
        holder.startDate.setText(HomeArrayList.get(position).getStart().substring(0,10));
        holder.endDate.setText(HomeArrayList.get(position).getEnd().substring(0,10));
        /*holder.Profilephoto_home.setImageResource(model.getHackphoto());
        holder.HackName.setText(model.getHackName());
        holder.teamSize.setText(model.getTeamSize());
        holder.startDate.setText(model.getStartDate());
        holder.endDate.setText(model.getEndDate());*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = HomeArrayList.get(position).getId();
                HackProfileFragment frag = new HackProfileFragment();
                Log.i("hackId", id);
                Bundle bundle = new Bundle();
                bundle.putString("ID", id);
                frag.setArguments(bundle);

                MainActivity activity = (MainActivity) v.getContext();

                BottomNavigationView bottomNavigation = activity.findViewById(R.id.bottom_nav_bar);;
                bottomNavigation.setVisibility(View.GONE);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, frag)
                        .addToBackStack(null)
                        .commit();

            }
        });

    }

    @NonNull
    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return HomeArrayList.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder  {


        private ImageView Profilephoto_home;
        private TextView HackName, teamSizeMin,teamSizeMax, startDate,endDate;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            Profilephoto_home=itemView.findViewById(R.id.HackImage);
            HackName=itemView.findViewById(R.id.HackName_Home);
            teamSizeMin=itemView.findViewById(R.id.teamSize__min_input);
            teamSizeMax=itemView.findViewById(R.id.teamSize_max_input);
            startDate=itemView.findViewById(R.id.startDate_input);
            endDate=itemView.findViewById(R.id.endDate_input);

            //Profilephoto_home.setImageResource(R.drawable.hackimage);
        }
    }

    /*
    public interface OnHackListerner{
        void OnHackClick(int position); //using this interface to interpret the click and then send  to fragment to get position of that clicked item
    }

     */


}