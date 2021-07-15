package com.example.hackmate.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hackmate.Adapters.InvitesAdapter;
import com.example.hackmate.Adapters.RequestsAdapter;
import com.example.hackmate.Models.InvitesModel;
import com.example.hackmate.R;
import com.example.hackmate.Models.RequestsModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class RequestTallyFragment extends Fragment {

    Button request,accept,reject;
    private RecyclerView RVinvite,RVrequest;
    private ArrayList<InvitesModel> InvitesArrayList;
    private ArrayList<RequestsModel> RequestsArrayList;
    private TextView Invites,Requests;
    private ImageView arrowDown_Invite,arrowDown_Req;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_request_tally, container, false);

//inflate appbar for this particular fragment
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.InviteAppBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Invites= view.findViewById(R.id.Invites_tittleText_reqTally);
        arrowDown_Invite=view.findViewById(R.id.arrowDown_invite);
        RVinvite = view.findViewById(R.id.InviteRV);

        InvitesArrayList = new ArrayList<>();


        InvitesArrayList.add(new InvitesModel.Invites_Model(R.drawable.imageholder,"Muhammad Muaazz Zuberi","GreenCoders"));
        InvitesArrayList.add(new InvitesModel.Invites_Model(R.drawable.imageholder,"Muhammad Muaazz Zuberi","GreenCoders"));

        InvitesAdapter invitesAdapter = new InvitesAdapter(getContext(), InvitesArrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        RVinvite.setLayoutManager(linearLayoutManager);
        RVinvite.setAdapter(invitesAdapter);



        Invites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RVinvite.getVisibility()==VISIBLE){
                    RVinvite.setVisibility(View.GONE);
                    arrowDown_Invite.setRotation(-90);}
                else if (RVinvite.getVisibility()==GONE)
                {RVinvite.setVisibility(VISIBLE);
                    arrowDown_Invite.setRotation(360);}
            }
        });


        Requests= view.findViewById(R.id.Requests_tittleText_reqTally);
        arrowDown_Req=view.findViewById(R.id.arrowDown_request);
        RVrequest = view.findViewById(R.id.RequestRV);

        RequestsArrayList = new ArrayList<>();


        RequestsArrayList.add(new RequestsModel.Requests_Model(R.drawable.imageholder,"Muhammad Muaazz Zuberi","GreenCoders"));
        RequestsArrayList.add(new RequestsModel.Requests_Model(R.drawable.imageholder,"Muhammad Muaazz Zuberi","GreenCoders"));


        RequestsAdapter requestsAdapter = new RequestsAdapter(getContext(), RequestsArrayList);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        RVrequest.setLayoutManager(linearLayoutManager1);
        RVrequest.setAdapter(requestsAdapter);



        Requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RVrequest.getVisibility()==VISIBLE){
                    RVrequest.setVisibility(View.GONE);
                    arrowDown_Req.setRotation(-90);}
                else if (RVrequest.getVisibility()==GONE)
                { RVrequest.setVisibility(VISIBLE);
                    arrowDown_Req.setRotation(+360);}
            }
        });



        /*accept=view.findViewById(R.id.acceptButton);
        reject = view.findViewById(R.id.rejectButton);
        accept.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Invite is accepted", Toast.LENGTH_SHORT).show();
            }
        });
        reject.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Invite is rejected", Toast.LENGTH_SHORT).show();
            }
        });*/

        /*request = view.findViewById(R.id.requestButton);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Invites can be accepted and rejected !!!", Toast.LENGTH_SHORT).show();
            }
        });*/
    }





    @Override
    public void onDestroy() {
        super.onDestroy();

        BottomNavigationView bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigation.setVisibility(VISIBLE);
    }

}
