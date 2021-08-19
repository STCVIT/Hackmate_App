package com.example.hackmate.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hackmate.Adapters.InvitesAdapter;
import com.example.hackmate.Adapters.RequestsAdapter;
import com.example.hackmate.JSONPlaceholders.JSONPlaceHolderAPI;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.Kavita.Invites.Received;
import com.example.hackmate.POJOClasses.Kavita.Requests.ReceivedRequest;
import com.example.hackmate.POJOClasses.Kavita.Requests.RequestPOJO;
import com.example.hackmate.POJOClasses.Kavita.Invites.invitePOJO;
import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class InviteOrRequestFragment extends Fragment {

    Button request, accept, reject;
    private RecyclerView RVinvite, RVrequest;
    public List<Received> InvitesArrayList;
    private List<ReceivedRequest> RequestsArrayList;
    private TextView Invites, Requests;
    private ImageView arrowDown_Invite, arrowDown_Req;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String idToken = "Bearer ";
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //getChildFragmentManager().beginTransaction().replace(R.id.cpu_bottomNav_frame,new fragment()).commit();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_invite_or_request, container, false);

//inflate appbar for this particular fragment
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.InviteAppBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Invites = view.findViewById(R.id.Invites_tittleText_reqTally);
        arrowDown_Invite = view.findViewById(R.id.arrowDown_invite);

        RVinvite = view.findViewById(R.id.InviteRV);

        InvitesArrayList = new ArrayList<>();

        progressBar=view.findViewById(R.id.progressBar3);
        //InvitesArrayList.add(new InvitesModel.Invites_Model(R.drawable.imageholder,"Muhammad Muaazz Zuberi","GreenCoders"));
        //InvitesArrayList.add(new InvitesModel.Invites_Model(R.drawable.imageholder,"Muhammad Muaazz Zuberi","GreenCoders"));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        RVinvite.setLayoutManager(linearLayoutManager);
        InvitesAdapter invitesAdapter = new InvitesAdapter(getContext(), InvitesArrayList);
        RVinvite.setAdapter(invitesAdapter);
        RVrequest = view.findViewById(R.id.RequestRV);

        RequestsArrayList = new ArrayList<>();
        RequestsAdapter requestsAdapter = new RequestsAdapter(getContext(), RequestsArrayList);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RVrequest.setLayoutManager(linearLayoutManager1);
        RVrequest.setAdapter(requestsAdapter);

        idToken = MainActivity.getIdToken();
        JSONPlaceHolderAPI jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);
        progressBar.setVisibility(VISIBLE);


        Log.i("callback problemInvite1", "errorIni1");
        Call<invitePOJO> call5 = jsonPlaceHolderAPI.getMyInvites(idToken);
        Log.i("callback problemInvite2", "errorIni2");
        call5.enqueue(new Callback<invitePOJO>() {
            @Override
            public void onResponse(Call<invitePOJO> call5, Response<invitePOJO> response5) {
                if (!response5.isSuccessful()) {
                    Log.i("not sucess6", "code: " + response5.code());
                    return;
                }

                invitePOJO invitePOJOS = (invitePOJO) response5.body();
                Log.i("Response body", invitePOJOS.toString());
                List<Received> recieved_objs = invitePOJOS.getReceived();
                Log.i("Response body1", String.valueOf(recieved_objs.get(0).getTeam()));
                invitesAdapter.setMyInvites(recieved_objs,idToken);
                Log.i("Response body3", "list sending to adapter sucessfull");


                Call<RequestPOJO> call7 = jsonPlaceHolderAPI.getMyRequests(idToken);
                Log.i("callbackRequest2", "errorReq2");
                call7.enqueue(new Callback<RequestPOJO>() {
                    @Override
                    public void onResponse(Call<RequestPOJO> call7, Response<RequestPOJO> response7) {
                        if (!response7.isSuccessful()) {
                            Log.i("not sucess6", "code: " + response7.code());
                            progressBar.setVisibility(GONE);
                            return;
                        }
                        RequestPOJO requestPOJO = response7.body();

                        Log.i("Response body", requestPOJO.toString());
                        List<ReceivedRequest> req_objs = requestPOJO.getReceived();
                        // Log.i("Response body1", String.valueOf(req_objs.get(0).getParticipant().getName()));
                        requestsAdapter.setMyRequests(req_objs,idToken);
                        progressBar.setVisibility(GONE);
                        Log.i("Response body3", "list sending to adapter sucessfull");

                    }

                    @Override
                    public void onFailure(Call<RequestPOJO> call7, Throwable t) {
                        Log.i("failed5", t.getMessage());
                        progressBar.setVisibility(GONE);
                    }
                });



            }

            @Override
            public void onFailure(Call<invitePOJO> call5, Throwable t) {
                Log.i("failed5", t.getMessage());
                progressBar.setVisibility(GONE);
            }
        });


        Invites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RVinvite.getVisibility() == VISIBLE) {
                    RVinvite.setVisibility(View.GONE);
                    arrowDown_Invite.setRotation(-90);
                } else if (RVinvite.getVisibility() == GONE) {
                    RVinvite.setVisibility(VISIBLE);
                    arrowDown_Invite.setRotation(360);
                }
            }
        });

        arrowDown_Invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RVinvite.getVisibility() == VISIBLE) {
                    RVinvite.setVisibility(View.GONE);
                    arrowDown_Invite.setRotation(-90);
                } else if (RVinvite.getVisibility() == GONE) {
                    RVinvite.setVisibility(VISIBLE);
                    arrowDown_Invite.setRotation(360);
                }
            }
        });
        Requests = view.findViewById(R.id.Requests_tittleText_reqTally);
        arrowDown_Req = view.findViewById(R.id.arrowDown_request);
        arrowDown_Req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RVrequest.getVisibility() == VISIBLE) {
                    RVrequest.setVisibility(View.GONE);
                    arrowDown_Req.setRotation(-90);
                } else if (RVrequest.getVisibility() == GONE) {
                    RVrequest.setVisibility(VISIBLE);
                    arrowDown_Req.setRotation(+360);
                }
            }
        });

        //RequestsArrayList.add(new RequestsModel.Requests_Model(R.drawable.imageholder,"Muhammad Muaazz Zuberi","GreenCoders"));
        //RequestsArrayList.add(new RequestsModel.Requests_Model(R.drawable.imageholder,"Muhammad Muaazz Zuberi","GreenCoders"));


        // in below two lines we are setting layoutmanager and adapter to our recycler view.


        Requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RVrequest.getVisibility() == VISIBLE) {
                    RVrequest.setVisibility(View.GONE);
                    arrowDown_Req.setRotation(-90);
                } else if (RVrequest.getVisibility() == GONE) {
                    RVrequest.setVisibility(VISIBLE);
                    arrowDown_Req.setRotation(+360);
                }
            }
        });



        //InvitesArrayList = (ArrayList<invitePOJO>) response5.body();


        // Log.d("problem5",InvitesArrayList.toString());

        //invitesAdapter.setMyInvites(InvitesArrayList);


    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        BottomNavigationView bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigation.setVisibility(VISIBLE);
    }

}
