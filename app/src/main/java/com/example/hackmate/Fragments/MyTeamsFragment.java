package com.example.hackmate.Fragments;

import static android.media.CamcorderProfile.get;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hackmate.Adapters.MyTeamsAdapter;
import com.example.hackmate.JSONPlaceholders.JSONPlaceHolderAPI;
import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.Kavita.Invites.Received;
import com.example.hackmate.POJOClasses.Kavita.Invites.invitePOJO;
import com.example.hackmate.POJOClasses.Kavita.Requests.ReceivedRequest;
import com.example.hackmate.POJOClasses.Kavita.Requests.RequestPOJO;
import com.example.hackmate.POJOClasses.Kavita.myTeams.Final2;
import com.example.hackmate.POJOClasses.Kavita.myTeams.PtSkill2;
import com.example.hackmate.POJOClasses.Kavita.myTeams.myTeamsPOJO;
import com.example.hackmate.POJOClasses.loginPOJO;
import com.example.hackmate.R;
import com.example.hackmate.util.ClickListener;
import com.example.hackmate.util.RecyclerTouchListener;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.ChipGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MyTeamsFragment extends Fragment {
    private static final String TAG = "MyTeamsFragment";
    public String id3;
    MyTeamsAdapter adapter;
    private ChipGroup chipGroup;
    private boolean isLoading = false, isLastPage = false;
    private int earlier_pos = 0, page = 1;
    ImageView imageView, bell;
    BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    public int before1 = 0, before2 = 0, myinvites = 1, myrequests = 1;
    public ImageView dot;
    public boolean status;
    public BadgeDrawable badge;

    public void onStart() {
        super.onStart();

        notifications(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_my_teams, container, false);


        Toolbar toolbar = (Toolbar) v.findViewById(R.id.myTeamsAppBar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView = requireActivity().findViewById(R.id.bottom_nav_bar);
        TextView newTeams = view.findViewById(R.id.newTeam);
        bell = view.findViewById(R.id.imageView10);
        imageView = view.findViewById(R.id.imageView14);
        chipGroup = view.findViewById(R.id.skillGrp);
        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        dot = view.findViewById(R.id.imageView11);
//notifications();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyTeamsAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);
        imageView.setVisibility(View.GONE);

        badge = bottomNavigationView.getOrCreateBadge(R.id.nav_myTeams);

        bell.setOnClickListener(v -> {
            bottomNavigationView.setVisibility(View.GONE);


            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new InviteOrRequestFragment())
                    .addToBackStack(null)
                    .commit();
        });

        newTeams.setOnClickListener(v -> {
            bottomNavigationView.setVisibility(View.GONE);

            TeamCreationFormFragment frag = new TeamCreationFormFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("Key", 1);
            frag.setArguments(bundle);

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, frag)
                    .addToBackStack(null)
                    .commit();
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null) {
                    int curr_position = linearLayoutManager.findLastVisibleItemPosition();
                    if (!isLoading) {
                        Log.i(TAG, "onScrolled: curr_position->" + curr_position + " => " + ((curr_position + 1) % 8 == 0) + " earlier -> " + earlier_pos);
                        //if (curr_position == adapter.getItemCount() - 1) {
                        if (curr_position == adapter.getItemCount() - 1 && !isLastPage && ((curr_position + 1) % 8 == 0)) {
                            earlier_pos = curr_position;
                            Log.i(TAG, "onScrolled: position => " + curr_position);
                            isLoading = true;
                            getMyTeams(++page);
                        }
                    }
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(() -> {
            adapter.clearList();
            isLoading = true;
            recyclerView.scrollToPosition(0);
            earlier_pos = 0;
            if (dot.getVisibility() == VISIBLE)
                notifications(0);
            else if (dot.getVisibility() == GONE)
                notifications(1);
            getMyTeams(page = 1);
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(requireContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.e(TAG, adapter.getList().get(position).getTeam().getId());
                if ((adapter.getList().get(position).getTeam().getAdminId()).equals(id3)) {
                    bottomNavigationView.setVisibility(View.GONE);
                    TeamProfileLeaderViewFragment frag = new TeamProfileLeaderViewFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("teamID", String.valueOf(adapter.getList().get(position).getTeam().getId()));
                    frag.setArguments(bundle);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment, frag)
                            .addToBackStack(null)
                            .commit();
                } else {
                    bottomNavigationView.setVisibility(View.GONE);
                    TeamProfileMemberViewFragment frag1 = new TeamProfileMemberViewFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("teamID", String.valueOf(adapter.getList().get(position).getTeam().getId()));
                    frag1.setArguments(bundle1);

                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment, frag1)
                            .addToBackStack(null)
                            .commit();
                }

            }

            @Override
            public void onLongClick(View view, int position) {
                Log.e(TAG, adapter.getList().get(position).getTeam().getId());
            }
        }));


        loginAPI loginAPI = RetrofitInstance.getRetrofitInstance().create(loginAPI.class);
        Call<loginPOJO> call2 = loginAPI.getParticipant(MainActivity.getIdToken());
        call2.enqueue(new Callback<loginPOJO>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<loginPOJO> call2, Response<loginPOJO> response1) {

                if (!response1.isSuccessful()) {
                    Log.i(TAG, "code: " + response1.code());
                    return;
                }
                loginPOJO loginPOJOS = response1.body();
                Log.i("Response bodykAVITA", loginPOJOS.getId());
                id3 = loginPOJOS.getId();
                getMyTeams(page = 1);
                Log.i("Response bodyid2", id3);
                Log.d("MyTeams", "jsonPlaceHolder");
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<loginPOJO> call2, Throwable t) {
                Log.i("failed1", t.getMessage());
            }
        });
    }

    public void getMyTeams(int page3) {
        //notifications();
        adapter.showProgress();
        JSONPlaceHolderAPI jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);
        Call<myTeamsPOJO> call1 = jsonPlaceHolderAPI.getmyTeams(MainActivity.getIdToken(), page3);
        Log.i("callback problem m", "errorMyTeams");
        call1.enqueue(new Callback<myTeamsPOJO>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<myTeamsPOJO> call1, Response<myTeamsPOJO> response1) {
                isLoading = false;
                adapter.removeProgress();
                swipeRefreshLayout.setRefreshing(false);
                Log.i("callback problem3MT", "error3MT");
                if (!response1.isSuccessful()) {
                    Log.i(TAG, "code: " + response1.code());
                    if (page3 == 1) {
                        imageView.setVisibility(View.VISIBLE);
                    }
                    return;
                }
                myTeamsPOJO myTeamsPOJOS = response1.body();
                Log.i("Response body", myTeamsPOJOS.getLength().toString());
                if (myTeamsPOJOS.getLength() < 8)
                    isLastPage = true;
                List<Final2> final_objs2 = myTeamsPOJOS.getFinal2();

                Log.i("Response body1", final_objs2.get(0).getHackName());
                adapter.setmyTeams(final_objs2, id3);

                /*if (final_objs2.size() == 0) {
                    --page;
                }*/
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<myTeamsPOJO> call1, Throwable t) {
                isLoading = false;
                adapter.removeProgress();
                if (page3 == 1) {
                    imageView.setVisibility(View.VISIBLE);
                }
                Log.i("failed1", t.getMessage());
            }
        });
    }

    public void notifications(int a) {

        switch (a) {
            case 0:
                Toast.makeText(getContext(), "You have unchecked notifications!!!", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                JSONPlaceHolderAPI jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);
                Call<invitePOJO> call5 = jsonPlaceHolderAPI.getMyInvites(MainActivity.getIdToken());
                Log.i("callback problemInvite2", "errorIni2");
                call5.enqueue(new Callback<invitePOJO>() {
                    @Override
                    public void onResponse(Call<invitePOJO> call5, Response<invitePOJO> response5) {
                        if (!response5.isSuccessful()) {
                            Log.i("not sucess6", "code: " + response5.code());
                            myinvites = 0;
                            if (before1 != myinvites || before2 != myrequests) {
                                dot.setVisibility(VISIBLE);
                                before1 = myinvites;
                                before2 = myrequests;
                                badge.setVisible(true);
                            } else {
                                dot.setVisibility(GONE);
                                badge.setVisible(false);
                            }
                            return;
                        }

                        invitePOJO invitePOJOS = (invitePOJO) response5.body();
                        Log.i("Response body", invitePOJOS.toString());
                        List<Received> recieved_objs = invitePOJOS.getReceived();


                        Log.i("Response body3", "list sending to adapter sucessfull");
                        myinvites = recieved_objs.size();

                        if (before1 != myinvites || before2 != myrequests) {
                            dot.setVisibility(VISIBLE);
                            before1 = myinvites;
                            before2 = myrequests;

                            badge.setVisible(true);

                        } else {
                            dot.setVisibility(GONE);
                            badge.setVisible(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<invitePOJO> call5, Throwable t) {
                        Log.i("failed5", t.getMessage());

                    }

                });


                Call<RequestPOJO> call7 = jsonPlaceHolderAPI.getMyRequests(MainActivity.getIdToken());
                Log.i("callbackRequest2", "errorReq2");
                call7.enqueue(new Callback<RequestPOJO>() {
                    @Override
                    public void onResponse(Call<RequestPOJO> call7, Response<RequestPOJO> response7) {
                        if (!response7.isSuccessful()) {
                            Log.i("not sucess6", "code: " + response7.code());
                            myrequests = 0;
                            if (before1 != myinvites || before2 != myrequests) {
                                dot.setVisibility(VISIBLE);
                                before1 = myinvites;
                                before2 = myrequests;
                                badge.setVisible(true);
                            } else {
                                dot.setVisibility(GONE);
                                badge.setVisible(false);
                            }
                            return;
                        }
                        RequestPOJO requestPOJO = response7.body();

                        Log.i("Response body", requestPOJO.toString());
                        List<ReceivedRequest> req_objs = requestPOJO.getReceived();


                        myrequests = req_objs.size();

                        if (before1 != myinvites || before2 != myrequests) {
                            dot.setVisibility(VISIBLE);
                            before1 = myinvites;
                            before2 = myrequests;
                            badge.setVisible(true);
                        } else {
                            dot.setVisibility(GONE);
                            badge.setVisible(false);
                        }
                        Log.i("Response body3", "list sending to adapter sucessfull");

                    }

                    @Override
                    public void onFailure(Call<RequestPOJO> call7, Throwable t) {


                        Log.i("failed5", t.getMessage());
                    }
                });
                Log.i("invitecount", "before:" + before1 + "inviteList:" + myinvites);

                break;

        }

    }

}