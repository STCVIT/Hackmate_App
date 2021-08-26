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
    private TextView Invites, Requests, textView, textView1;
    private ImageView arrowDown_Invite, arrowDown_Req, imageView, imageView1;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String idToken = "Bearer ";
<<<<<<< Updated upstream
=======
    ProgressBar progressBar;
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
    public int myinvites, myrequests;
>>>>>>> Stashed changes

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
<<<<<<< Updated upstream
<<<<<<< Updated upstream

        // Inflate the layout for this fragment
=======

>>>>>>> Stashed changes
        View v = inflater.inflate(R.layout.fragment_invite_or_request, container, false);
=======
>>>>>>> Stashed changes

<<<<<<< Updated upstream
        View v = inflater.inflate(R.layout.fragment_invite_or_request, container, false);
=======

>>>>>>> Stashed changes
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.InviteAppBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.InvitesIV);
        imageView1 = view.findViewById(R.id.imageView12);
        textView = view.findViewById(R.id.textView5);
        textView1 = view.findViewById(R.id.textView6);
        Invites = view.findViewById(R.id.Invites_tittleText_reqTally);
        arrowDown_Invite = view.findViewById(R.id.arrowDown_invite);
        RVinvite = view.findViewById(R.id.InviteRV);
        InvitesArrayList = new ArrayList<>();
<<<<<<< Updated upstream


<<<<<<< Updated upstream
        //InvitesArrayList.add(new InvitesModel.Invites_Model(R.drawable.imageholder,"Muhammad Muaazz Zuberi","GreenCoders"));
        //InvitesArrayList.add(new InvitesModel.Invites_Model(R.drawable.imageholder,"Muhammad Muaazz Zuberi","GreenCoders"));
=======
        progressBar = view.findViewById(R.id.progressBar3);
>>>>>>> Stashed changes


=======
        progressBar = view.findViewById(R.id.progressBar3);
        imageView.setVisibility(GONE);
        imageView1.setVisibility(GONE);
        textView.setVisibility(GONE);
        textView1.setVisibility(GONE);
>>>>>>> Stashed changes
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RVinvite.setLayoutManager(linearLayoutManager);
        InvitesAdapter invitesAdapter = new InvitesAdapter(getContext(), InvitesArrayList);
        RVinvite.setAdapter(invitesAdapter);
        RVrequest = view.findViewById(R.id.RequestRV);

        RequestsArrayList = new ArrayList<>();
        RequestsAdapter requestsAdapter = new RequestsAdapter(getContext(), RequestsArrayList);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RVrequest.setLayoutManager(linearLayoutManager1);
        RVrequest.setAdapter(requestsAdapter);

        idToken = MainActivity.getidToken();
        JSONPlaceHolderAPI jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);
<<<<<<< Updated upstream

        // Send token to your backend via HTTPS
        // ...
        //inviteAPI inviteAPI = retrofit.create(inviteAPI.class);
        //JSONPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JSONPlaceHolderAPI.class);
=======
        progressBar.setVisibility(VISIBLE);
        if (InvitesArrayList.size() == 0) {
            imageView.setVisibility(VISIBLE);
            textView.setVisibility(VISIBLE);
        }
        if (RequestsArrayList.size() == 0) {
            imageView1.setVisibility(VISIBLE);
            textView1.setVisibility(VISIBLE);
        }
>>>>>>> Stashed changes

        Call<invitePOJO> call5 = jsonPlaceHolderAPI.getMyInvites(idToken);

        call5.enqueue(new Callback<invitePOJO>() {
            @Override
            public void onResponse(Call<invitePOJO> call5, Response<invitePOJO> response5) {
                if (!response5.isSuccessful()) {
                    Log.i("not sucess6", "code: " + response5.code());
                    return;
                }

                invitePOJO invitePOJOS = (invitePOJO) response5.body();

                List<Received> recieved_objs = invitePOJOS.getReceived();
<<<<<<< Updated upstream
<<<<<<< Updated upstream
                Log.i("Response body1", String.valueOf(recieved_objs.get(0).getTeam().getId()));
                invitesAdapter.setMyInvites(recieved_objs,idToken);
                Log.i("Response body3", "list sending to adapter sucessfull");
=======

                invitesAdapter.setMyInvites(recieved_objs, idToken);
>>>>>>> Stashed changes
=======
                //    Log.i("Response body1", String.valueOf(recieved_objs.get(0).getTeam()));
                invitesAdapter.setMyInvites(recieved_objs, idToken);
                Log.i("Response body3", "list sending to adapter sucessfull");
                myinvites = recieved_objs.size();
                if (recieved_objs.size() == 0) {
                    imageView.setVisibility(VISIBLE);
                    textView.setVisibility(VISIBLE);
                } else {
                    imageView.setVisibility(GONE);
                    textView.setVisibility(GONE);
                }
>>>>>>> Stashed changes

            }

<<<<<<< Updated upstream
                Call<RequestPOJO> call7 = jsonPlaceHolderAPI.getMyRequests(idToken);

                call7.enqueue(new Callback<RequestPOJO>() {
                    @Override
                    public void onResponse(Call<RequestPOJO> call7, Response<RequestPOJO> response7) {
                        if (!response7.isSuccessful()) {
<<<<<<< Updated upstream
                            Log.i("not sucess6", "code: " + response7.code());
=======
                            Log.i("InviteOrRequestFragment", "code: " + response7.code());
                            progressBar.setVisibility(GONE);
>>>>>>> Stashed changes
                            return;
                        }
                        RequestPOJO requestPOJO = response7.body();

                        Log.i("Response body", requestPOJO.toString());
                        List<ReceivedRequest> req_objs = requestPOJO.getReceived();
<<<<<<< Updated upstream
                        Log.i("Response body1", String.valueOf(req_objs.get(0).getParticipant().getName()));
                        requestsAdapter.setMyRequests(req_objs);
                        Log.i("Response body3", "list sending to adapter sucessfull");
=======
                        requestsAdapter.setMyRequests(req_objs, idToken);
                        progressBar.setVisibility(GONE);
                        Log.i("InviteOrRequests", "list sending to adapter sucessfull");
>>>>>>> Stashed changes

                    }

                    @Override
                    public void onFailure(Call<RequestPOJO> call7, Throwable t) {
<<<<<<< Updated upstream
                        Log.i("failed5", t.getMessage());
=======
                        Log.i("Requests", t.getMessage());
                        progressBar.setVisibility(GONE);
>>>>>>> Stashed changes
                    }
                });
=======
            @Override
            public void onFailure(Call<invitePOJO> call5, Throwable t) {
                Log.i("failed5", t.getMessage());
                progressBar.setVisibility(GONE);
                imageView.setVisibility(VISIBLE);
                textView.setVisibility(VISIBLE);
            }
        });

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
>>>>>>> Stashed changes

                Log.i("Response body", requestPOJO.toString());
                List<ReceivedRequest> req_objs = requestPOJO.getReceived();
                // Log.i("Response body1", String.valueOf(req_objs.get(0).getParticipant().getName()));
                requestsAdapter.setMyRequests(req_objs, idToken);
                progressBar.setVisibility(GONE);
                myrequests = req_objs.size();
                if (req_objs.size() == 0) {
                    imageView1.setVisibility(VISIBLE);
                    textView1.setVisibility(VISIBLE);
                } else {
                    imageView1.setVisibility(GONE);
                    textView1.setVisibility(GONE);
                }

<<<<<<< Updated upstream
            }

            @Override
            public void onFailure(Call<invitePOJO> call5, Throwable t) {
<<<<<<< Updated upstream
=======
                Log.i("Response body3", "list sending to adapter sucessfull");

            }

            @Override
            public void onFailure(Call<RequestPOJO> call7, Throwable t) {
>>>>>>> Stashed changes
                Log.i("failed5", t.getMessage());
=======
                Log.i("Invites", t.getMessage());
                progressBar.setVisibility(GONE);
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
                imageView1.setVisibility(VISIBLE);
                textView1.setVisibility(VISIBLE);
>>>>>>> Stashed changes
            }
        });
        Invites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RVinvite.getVisibility() == VISIBLE) {
                    RVinvite.setVisibility(View.GONE);
                    arrowDown_Invite.setRotation(-90);
                    if (myinvites == 0) {
                        imageView.setVisibility(GONE);
                        textView.setVisibility(GONE);
                    }
                } else if (RVinvite.getVisibility() == GONE) {
                    RVinvite.setVisibility(VISIBLE);
                    arrowDown_Invite.setRotation(360);
                    if (myinvites == 0) {
                        imageView.setVisibility(VISIBLE);
                        textView.setVisibility(VISIBLE);
                    }
                }
            }
        });

<<<<<<< Updated upstream

        Requests = view.findViewById(R.id.Requests_tittleText_reqTally);
        arrowDown_Req = view.findViewById(R.id.arrowDown_request);


=======
        arrowDown_Invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RVinvite.getVisibility() == VISIBLE) {
                    RVinvite.setVisibility(View.GONE);
                    arrowDown_Invite.setRotation(-90);
                    if (myinvites == 0) {
                        imageView.setVisibility(GONE);
                        textView.setVisibility(GONE);
                    }
                } else if (RVinvite.getVisibility() == GONE) {
                    RVinvite.setVisibility(VISIBLE);
                    arrowDown_Invite.setRotation(360);
                    if (myinvites == 0) {
                        imageView.setVisibility(VISIBLE);
                        textView.setVisibility(VISIBLE);
                    }

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
                    if (myrequests == 0) {
                        imageView1.setVisibility(GONE);
                        textView1.setVisibility(GONE);
                    }
                } else if (RVrequest.getVisibility() == GONE) {
                    RVrequest.setVisibility(VISIBLE);
                    arrowDown_Req.setRotation(+360);
                    if (myrequests == 0) {
                        imageView1.setVisibility(VISIBLE);
                        textView1.setVisibility(VISIBLE);
                    }
                }
            }
        });
>>>>>>> Stashed changes

        Requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RVrequest.getVisibility() == VISIBLE) {
                    RVrequest.setVisibility(View.GONE);
                    arrowDown_Req.setRotation(-90);
                    if (myrequests == 0) {
                        imageView1.setVisibility(GONE);
                        textView1.setVisibility(GONE);
                    }
                } else if (RVrequest.getVisibility() == GONE) {
                    RVrequest.setVisibility(VISIBLE);
                    arrowDown_Req.setRotation(+360);
                    if (myrequests == 0) {
                        imageView1.setVisibility(VISIBLE);
                        textView1.setVisibility(VISIBLE);
                    }
                }
            }
        });


<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
>>>>>>> Stashed changes
        //InvitesArrayList = (ArrayList<invitePOJO>) response5.body();


        // Log.d("problem5",InvitesArrayList.toString());

        //invitesAdapter.setMyInvites(InvitesArrayList);


=======
>>>>>>> Stashed changes
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        BottomNavigationView bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigation.setVisibility(VISIBLE);
    }

}
