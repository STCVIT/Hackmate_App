package com.example.hackmate.Fragments.FindParticipant;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hackmate.JSONPlaceholders.API;

import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.FindParticipant.FinalPt;
import com.example.hackmate.POJOClasses.FindParticipant.invitePOJO;

import com.example.hackmate.POJOClasses.InvitationSent.Invites;
import com.example.hackmate.R;
import com.example.hackmate.util.Functions;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.material.chip.ChipGroup;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class FindParticipantFragment extends Fragment {
    private static final String TAG = "FindParticipantFragment";

    private boolean isSearch = false;

    private ImageView downArrow;
    private ChipGroup chips;
    private API api;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private EditText findPtByName;
    private InviteAdapter inviteAdapter;
    private boolean isLoading = false, isLastPage = false;
    private int page = 1, page2 = 1, page3 = 1, earlier_pos = 0;
    private List<String> ptId;
    private String hackID, name = "", skill = "", teamId;
    private FindParticipantViewModel viewModel;
    private List<String> invited_names;

    public FindParticipantFragment(String hackID, String teamId, List<String> ptId) {
        this.hackID = hackID;
        this.teamId = teamId;
        this.ptId = ptId;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(FindParticipantViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_participant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.wtf(TAG, "onViewCreated: created");
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        downArrow = view.findViewById(R.id.downArrow);
        findPtByName = view.findViewById(R.id.findPtByName);

        chips = view.findViewById(R.id.chips);
        recyclerView = view.findViewById(R.id.inviteList);

        api = RetrofitInstance.getRetrofitInstance().create(API.class);
        Trial();

        /*hackID = "null";

        teamId = "60fe92283dc96e001575ee3a";
        ptId = new ArrayList<>();
        ptId.add("61124f06089dc90015a20844");
        ptId.add("60f13063ce79400015732285");


        teamId = "60f647fd7aa44d77a0dc2805";
        ptId = new ArrayList<>();
        ptId.add("60f13062ce79400015732284");*/
        Log.i("IDs", hackID + " " + teamId + " " + ptId);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        inviteAdapter = new InviteAdapter(getContext(), new ArrayList<>(), teamId, ptId, invited_names);
        recyclerView.setAdapter(inviteAdapter);


        /*if (frontend.isChecked() || backend.isChecked() || ml.isChecked() || ui_ux.isChecked() ||
                management.isChecked() || appdev.isChecked() || cybersecurity.isChecked()) {
            skill = skillSelected();
            getPtBySkills(skill,page3);
        } else {
            isLoading = true;
            inviteAdapter.clearList();
            recyclerView.scrollToPosition(0);
            sendInvite(page = 1);
        }*/
        if (viewModel.getList() == null || viewModel.getList().isEmpty()) {
            Log.e(TAG, "onViewCreated: fetching new data");
            isLoading = true;
            sendInvite(page = 1);
        } else {
            Log.e(TAG, "onViewCreated: getting stored data");
            inviteAdapter.addItems(viewModel.getList(), "");
            if (skill.length() == 0) {
                findPtByName.setText(viewModel.getName());
                if (findPtByName.getText().length() == 0) {
                    isSearch = false;
                    page = ((viewModel.getList().size() - 1) / 12) + 1;
                    Log.i(TAG, "onViewCreated: =>" + findPtByName.getText().toString());
                } else {
                    isSearch = true;
                    page2 = ((viewModel.getList().size() - 1) / 12) + 1;
                    Log.i(TAG, "onViewCreated: name=>" + page2);
                }
            }
        }

        chipsOnClick();


        swipeRefreshLayout.setOnRefreshListener(() -> {
            isLoading = true;
            inviteAdapter.clearList();
            recyclerView.scrollToPosition(0);
            earlier_pos = 0;
            isLastPage = false;
            swipeRefreshLayout.setRefreshing(true);
            if (isSearch)
                searchPtByName(findPtByName.getText().toString(), page2 = 1);
            else if (chips.getCheckedChipId() != -1)
                getPtBySkills(skillSelected(chips.getCheckedChipId()), page3 = 1);
            else
                sendInvite(page = 1);
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null) {
                    int curr_position = linearLayoutManager.findLastVisibleItemPosition();
                    if (!isLoading)
                        if (!isLastPage && curr_position == inviteAdapter.getItemCount() - 1 && curr_position != 0 && curr_position > earlier_pos) {
                            earlier_pos = curr_position;
                            Log.i(TAG, "onScrolled: position => " + curr_position);
                            isLoading = true;
                            Log.i(TAG, "onScrolledscroll listener: " + chips.getCheckedChipId());
                            if (isSearch)
                                searchPtByName(findPtByName.getText().toString(), ++page2);
                            else if (chips.getCheckedChipId() != -1) {
                                Log.i("Checked", String.valueOf(chips.isSelected()) + "hh");
                                getPtBySkills(skillSelected(chips.getCheckedChipId()), ++page3);
                            } else {
                                Log.i("Not Checked", String.valueOf(chips.isSelected()) + "ggg");
                                sendInvite(++page);
                            }
                        }
                }
            }
        });

        view.findViewById(R.id.domainFilterTeammate).setOnClickListener(v -> {
            if (chips.getVisibility() == View.GONE) {
                chips.setVisibility(View.VISIBLE);
                downArrow.animate().rotationBy(180F);
            } else {
                chips.setVisibility(View.GONE);
                downArrow.animate().rotationBy(180F);
            }
        });

        findPtByName.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == event.KEYCODE_ENTER)) {
                try {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    Log.wtf(TAG, "onViewCreated: ", e);
                }
                inviteAdapter.clearList();
                recyclerView.scrollToPosition(0);
                earlier_pos = 0;
                isLastPage = false;
                name = findPtByName.getText().toString();
                if (findPtByName.getText().length() == 0) {
                    isSearch = false;
                    if (chips.getCheckedChipId() != -1)
                        getPtBySkills(skillSelected(chips.getCheckedChipId()), page3 = 1);
                    else
                        sendInvite(page = 1);
                } else {
                    isSearch = true;
                    searchPtByName(findPtByName.getText().toString(), page2 = 1);
                }
                return true;
            } else
                return false;
        });
    }

    public void chipsOnClick() {
        chips.setOnCheckedChangeListener((group, checkedId) -> {
            inviteAdapter.clearList();
            recyclerView.scrollToPosition(0);
            earlier_pos = 0;
            isLastPage = false;
            skill = "";
            if (checkedId != -1) {
                skill = skillSelected(checkedId);
                getPtBySkills(skill, page3 = 1);
            } else if (isSearch)
                searchPtByName(findPtByName.getText().toString(), page2 = 1);
            else
                sendInvite(page = 1);

        });
    }

    public String skillSelected(int checkedId) {
        switch (checkedId) {
            case R.id.chipDomain1:
                return "frontend";
            case R.id.chipDomain2:
                return "backend";
            case R.id.chipDomain3:
                return "ml";
            case R.id.chipDomain4:
                return "ui/ux";
            case R.id.chipDomain5:
                return "management";
            case R.id.chipDomain6:
                return "appdev";
            case R.id.chipDomain7:
                return "cybersecurity";
            default:
                return "blockchain";
        }
    }

    public void getPtBySkills(String skill, int page3) {
        inviteAdapter.showProgress();
        Log.i(TAG, "getPtBySkills: " + skill + "=>" + page3);
        Call<invitePOJO> call = api.inviteBySkills(MainActivity.getIdToken(), hackID, page3, skill);
        call.enqueue(new Callback<invitePOJO>() {
            @Override
            public void onResponse(Call<invitePOJO> call, Response<invitePOJO> response) {
                inviteAdapter.removeProgress();
                if (response.code() == 401)
                    Functions.fetchToken(requireActivity(), () -> getPtBySkills(skill, page3));
                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body().getFinal().size() < 12)
                        isLastPage = true;
                    inviteAdapter.addItems(response.body().getFinal(), skill);
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<invitePOJO> call, Throwable t) {
                inviteAdapter.removeProgress();
                isLoading = false;
            }
        });
    }

    @Override
    public void onPause() {
        viewModel.setList(inviteAdapter.getList());
        viewModel.setName(name);
        viewModel.setSkill(skill);
        super.onPause();
    }

    public void sendInvite(int page) {
        inviteAdapter.showProgress();
        Call<invitePOJO> call = api.inviteParticipants(MainActivity.getIdToken(), hackID, page);
        call.enqueue(new Callback<invitePOJO>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<invitePOJO> call, Response<invitePOJO> response) {
                inviteAdapter.removeProgress();
                Log.d(TAG, "onResponse() returned: " + response.code());
                if (response.code() == 401)
                    Functions.fetchToken(requireActivity(), () -> sendInvite(page));
                if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body().getFinal().size() < 12)
                        isLastPage = true;
                    inviteAdapter.addItems(response.body().getFinal(), "");
                    isLoading = false;
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<invitePOJO> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
                inviteAdapter.removeProgress();
                isLoading = false;
            }
        });
    }


    public void Trial() {
        invited_names = new ArrayList<>();
        Call<Invites> call = api.inviteSent(MainActivity.getIdToken());
        call.enqueue(new Callback<Invites>() {
            @Override
            public void onResponse(Call<Invites> call, Response<Invites> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    for (int i = 0; i < response.body().getSent().size(); i++) {

                        if (teamId.equals(response.body().getSent().get(i).getTeam().getId()))
                            invited_names.add(response.body().getSent().get(i).getParticipant().getId());


                    }
                    Log.i("PARTICIPANT_CHEK", String.valueOf(invited_names));
                } else
                    Log.i("PARTICIPANT_CHECH", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<Invites> call, Throwable t) {
                Log.i("PARTICIPANT_CHECH", t.getMessage());
            }
        });
    }


    public void searchPtByName(String name, int page2) {
        inviteAdapter.showProgress();
        Log.d(TAG, "searchPtByName() returned: " + name + " ,page =>" + page2);
        Call<invitePOJO> call = api.getPtByName(MainActivity.getIdToken(), hackID, name, page2);
        call.enqueue(new Callback<invitePOJO>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<invitePOJO> call, Response<invitePOJO> response) {
                Log.d(TAG, "onResponse() returned: " + response.message());
                inviteAdapter.removeProgress();
                if (response.code() == 401)
                    Functions.fetchToken(requireActivity(), () -> searchPtByName(name, page2));
                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body().getFinal().size() < 12)
                        isLastPage = true;
                    inviteAdapter.addItems(response.body().getFinal(), "");
                    isLoading = false;
                }

            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<invitePOJO> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
                inviteAdapter.removeProgress();
                isLoading = false;
            }
        });
    }
}