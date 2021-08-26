package com.example.hackmate.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.hackmate.Fragments.HackProfile.HackProfileFragment;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.Kavita.Hacks.Final;
import com.example.hackmate.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Final> HomeArrayList;
    private String id;
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    public  ImageView Profilephoto_home;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public HomeAdapter(Context context, List<Final> homeArrayList) {
        this.context = context;
        HomeArrayList = homeArrayList;
    }

    @Override
    public int getItemViewType(int position) {
        if(HomeArrayList.get(position) == null)
            return VIEW_TYPE_LOADING;
        return VIEW_TYPE_ITEM;

    }

    public void setHackList(List<Final> final_objs) {
        //ArrayList HomeArrayList1 = (ArrayList) HomeAL.finals;
        int position = this.HomeArrayList.size();
        this.HomeArrayList.addAll(final_objs);
        notifyItemRangeInserted(position, final_objs.size());
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        if(viewType == VIEW_TYPE_ITEM)
            return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_hack_info_rv_layout, parent, false));
        else
            return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Viewholder)
            populateItem((Viewholder) holder,position);

    }

    private void populateItem(Viewholder holder, int position) {
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

        Glide.with(context)
                .load(HomeArrayList.get(position).getPoster())
                .placeholder(Drawable.createFromPath("https://firebasestorage.googleapis.com/v0/b/hackportal-450d0.appspot.com/o/Organisers%2FHacks%2FBrew%202k21?alt=media&token=4314d03f-0c45-49df-a70b-75ed79bc7ae2"))
                .into(Profilephoto_home);
        //holder.Profilephoto_home.setImageResource(model.getHackphoto());

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

    public void showProgress() {
        if(!HomeArrayList.contains(null)) {
            HomeArrayList.add(null);
            notifyItemInserted(HomeArrayList.size() - 1);
        }
    }

    public void removeProgress() {
        int position = HomeArrayList.indexOf(null);
        notifyItemRemoved(position);
        HomeArrayList.remove(null);
    }

    public void clearList() {
        HomeArrayList.clear();
        HomeArrayList.add(null);
    }


    public class Viewholder extends RecyclerView.ViewHolder  {



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

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NotNull View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar);
        }
    }

    /*
    public interface OnHackListerner{
        void OnHackClick(int position); //using this interface to interpret the click and then send  to fragment to get position of that clicked item
    }

     */


}