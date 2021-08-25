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


import com.example.hackmate.Fragments.HackProfileFragment;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.Kavita.Final;
import com.example.hackmate.POJOClasses.Kavita.hackListPOJO;
import com.example.hackmate.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;



public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Viewholder> {
    private Context context;
    private List<Final> HomeArrayList;
    private String id;


    public HomeAdapter(Context context, List<Final> homeArrayList) {
        this.context = context;
        HomeArrayList = homeArrayList;
    }



    public void setHackList(List<Final> final_objs) {
<<<<<<< Updated upstream
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
=======

        int position = this.HomeArrayList.size();
        this.HomeArrayList.addAll(final_objs);
        notifyItemRangeInserted(position, final_objs.size());
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == VIEW_TYPE_ITEM)
            return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_hack_info_rv_layout, parent, false));
        else
            return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
>>>>>>> Stashed changes
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Final  finals = HomeArrayList.get(position);
<<<<<<< Updated upstream
//Final Final = HomeArrayList.get(position);
//HomeModel.home_Model model = (HomeModel.home_Model) HomeArrayList.get(position);

        //int photo=Integer.parseInt(HomeArrayList.get(position).getHackPhoto());
        //holder.Profilephoto_home.setImageResource(photo);
        id = HomeArrayList.get(position).getId();
=======
>>>>>>> Stashed changes
        holder.HackName.setText(HomeArrayList.get(position).getName());
        holder.teamSizeMax.setText(String.valueOf(HomeArrayList.get(position).getMaxTeamSize()));
        holder.teamSizeMin.setText(String.valueOf(HomeArrayList.get(position).getMinTeamSize()));
        holder.startDate.setText(HomeArrayList.get(position).getStart().substring(0,10));
        holder.endDate.setText(HomeArrayList.get(position).getEnd().substring(0,10));
<<<<<<< Updated upstream
        /*holder.Profilephoto_home.setImageResource(model.getHackphoto());
        holder.HackName.setText(model.getHackName());
        holder.teamSize.setText(model.getTeamSize());
        holder.startDate.setText(model.getStartDate());
        holder.endDate.setText(model.getEndDate());*/
=======

        Glide.with(context)
                .load(HomeArrayList.get(position).getPoster())
                .placeholder(Drawable.createFromPath("https://firebasestorage.googleapis.com/v0/b/hackportal-450d0.appspot.com/o/Organisers%2FHacks%2FBrew%202k21?alt=media&token=4314d03f-0c45-49df-a70b-75ed79bc7ae2"))
                .into(Profilephoto_home);


>>>>>>> Stashed changes
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


        }
    }

<<<<<<< Updated upstream
    /*
    public interface OnHackListerner{
        void OnHackClick(int position); //using this interface to interpret the click and then send  to fragment to get position of that clicked item
    }

     */
=======
    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NotNull View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar);
        }
    }
>>>>>>> Stashed changes


}