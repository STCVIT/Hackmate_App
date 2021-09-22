package com.example.hackmate.Fragments.FindTeams;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackmate.Fragments.FindParticipant.FindParticipantViewModel;
import com.example.hackmate.JSONPlaceholders.API;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.JoinHackTeams.Final;
import com.example.hackmate.POJOClasses.POST.Code;
import com.example.hackmate.POJOClasses.JoinHackTeams.JoinHackTeamPOJO;
import com.example.hackmate.R;
import com.example.hackmate.util.Functions;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class FindTeamsFragment extends Fragment {
    private static final String TAG = "FindTeamsFragment";

    private LinearLayout filter;
    private ImageView downArrow, noTeamsToShow;
    private ChipGroup chips;
    private TextView joinUsingCode, appBarName;
    private CardView appBar;
    private int page = 1, page3 = 1, earlier_pos = 0;
    private JoinAdapter joinAdapter;
    int GET_NAV_CODE = 0;
    private API api;
    private RecyclerView recyclerView;
    public String hackId, hackName, team_name="", domain="";
    private EditText searchTeam;
    private ImageButton close_btn;
    private boolean isLoading = false, isSearch = false;
    private FindTeamsViewModel viewModel;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(FindTeamsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_teams, container, false);
    }

    @Override
    public void onPause() {
        viewModel.setList(joinAdapter.getList());
        viewModel.setName(team_name);
        viewModel.setSkill(domain);
        super.onPause();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            GET_NAV_CODE = bundle.getInt("Key", 0);
        }

        appBar = view.findViewById(R.id.appBarJoin);
        filter = view.findViewById(R.id.domainFilterTeammate);
        downArrow = view.findViewById(R.id.downArrow);
        appBarName = view.findViewById(R.id.appBarName);
        noTeamsToShow = view.findViewById(R.id.searchImage);

        searchTeam = view.findViewById(R.id.searchTeamJoin);
        close_btn = view.findViewById(R.id.close_btn);
        chips = view.findViewById(R.id.chips);
        joinUsingCode = view.findViewById(R.id.joinTeamCode);
        recyclerView = view.findViewById(R.id.joinList);

        if (GET_NAV_CODE == 1) {
            appBar.setVisibility(View.VISIBLE);
            hackId = bundle.getString("HackId", "");
            hackName = bundle.getString("HackName", "");
            appBarName.setText(hackName + " Teams");
        } else {
            appBar.setVisibility(View.GONE);
            hackId = "null";
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        joinAdapter = new JoinAdapter(getContext(), new ArrayList<>(), GET_NAV_CODE, hackName);
        recyclerView.setAdapter(joinAdapter);
        api = RetrofitInstance.getRetrofitInstance().create(API.class);


        if (viewModel.getList() == null || viewModel.getList().isEmpty()) {
            Log.e(TAG, "onViewCreated: fetching new data");
            isLoading = true;
            if(viewModel.getName() == null && viewModel.getSkill() == null)
                getHackTeamsToJoin(page = 1);
            else {
                noTeamsToShow.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        } else {
            Log.e(TAG, "onViewCreated: getting stored data");
            joinAdapter.addItems(viewModel.getList());
            if (domain.length()==0) {
                searchTeam.setText(viewModel.getName());
                if (searchTeam.getText().length() == 0) {
                    isSearch = false;
                    page = ((viewModel.getList().size() - 1) / 12) + 1;
                    Log.i(TAG, "onViewCreated: =>" + searchTeam.getText().toString());
                } else {
                    isSearch = true;
                }
            }
        }

        //set on text change listener for edittext
        searchTeam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!searchTeam.getText().toString().equals("")) {
                    close_btn.setVisibility(View.VISIBLE);
                }
                else
                {
                    close_btn.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        //set event for clear button
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTeam.setText("");
                joinAdapter.clearList();
                recyclerView.scrollToPosition(0);
                earlier_pos = 0;
                team_name = "";
                isSearch = false;
                if(chips.getCheckedChipId() != -1)
                    getDomainHackTeams(skillSelected(chips.getCheckedChipId()), page3=1);
                else
                    getHackTeamsToJoin(page=1);
            }
        });

        chipOnClick();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null) {
                    int curr_position = linearLayoutManager.findLastVisibleItemPosition();
                    if (!isLoading)
                        if ((curr_position + 1) % 8 == 0 && curr_position != 0 && curr_position > earlier_pos) {
                            earlier_pos = curr_position;
                            Log.i(TAG, "onScrolled: position => " + curr_position);
                            isLoading = true;
                            Log.i(TAG, "onScrolledscroll listener: " + chips.getCheckedChipId());
                            if (chips.getCheckedChipId() != -1) {
                                Log.i("Checked", String.valueOf(chips.isSelected()) + "hh");
                                getDomainHackTeams(skillSelected(chips.getCheckedChipId()), ++page3);
                            } else {
                                Log.i("Not Checked", String.valueOf(chips.isSelected()) + "ggg");
                                getHackTeamsToJoin(++page);
                            }
                        }
                }
            }
        });

        searchTeam.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == event.KEYCODE_ENTER)) {
                    try {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    joinAdapter.clearList();
                    recyclerView.scrollToPosition(0);
                    earlier_pos = 0;
                    team_name = searchTeam.getText().toString();
                    if (searchTeam.getText().length() == 0) {
                        isSearch = false;
                        if(chips.getCheckedChipId() != -1)
                            getDomainHackTeams(skillSelected(chips.getCheckedChipId()), page3=1);
                        else
                            getHackTeamsToJoin(page=1);
                    }
                    else {
                        isSearch = true;
                        searchHackTeamToJoin(searchTeam.getText().toString());
                    }
                    return true;
                }
                else
                    return false;
            }
        });

        joinUsingCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogBox();
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chips.getVisibility() == View.GONE) {
                    chips.setVisibility(View.VISIBLE);
                    downArrow.animate().rotationBy(180F);
                } else {
                    chips.setVisibility(View.GONE);
                    downArrow.animate().rotationBy(180F);
                }
            }
        });
    }

    public void chipOnClick() {
        chips.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                joinAdapter.clearList();
                recyclerView.scrollToPosition(0);
                earlier_pos = 0;
                domain =  "";

                if(checkedId!=-1) {
                    domain = skillSelected(checkedId);
                    getDomainHackTeams(domain, page3 = 1);
                }
                else if (isSearch)
                    searchHackTeamToJoin(searchTeam.getText().toString());
                else
                    getHackTeamsToJoin(page=1);
            }
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

    public void getDomainHackTeams(String domain,int page3) {
        noTeamsToShow.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        joinAdapter.showProgress();
        Call<JoinHackTeamPOJO> call = api.domainTeamHack(MainActivity.getIdToken(), hackId, page3, domain);
        call.enqueue(new Callback<JoinHackTeamPOJO>() {
            @Override
            public void onResponse(Call<JoinHackTeamPOJO> call, Response<JoinHackTeamPOJO> response) {
                joinAdapter.removeProgress();
                if(response.code()==401)
                    Functions.fetchToken(requireActivity(), () -> getDomainHackTeams(domain,page3));
                if (response.isSuccessful()) {
                    joinAdapter.addItems(response.body().getFinal());
                    isLoading = false;
                } else if (response.code() == 404) {
                    if(page3==1) {
                        noTeamsToShow.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<JoinHackTeamPOJO> call, Throwable t) {
                joinAdapter.removeProgress();
                isLoading = false;
            }
        });
    }

    public void searchHackTeamToJoin(String name) {
        noTeamsToShow.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        joinAdapter.showProgress();
        Call<List<Final>> call = api.searchTeamHack(MainActivity.getIdToken(), hackId, name);
        call.enqueue(new Callback<List<Final>>() {
            @Override
            public void onResponse(Call<List<Final>> call, Response<List<Final>> response) {
                joinAdapter.removeProgress();
                if(response.code()==401)
                    Functions.fetchToken(requireActivity(), () -> searchHackTeamToJoin(name));
                if (response.isSuccessful()) {
                    joinAdapter.addItems(response.body());
                    isLoading = false;
                } else if (response.code() == 404) {
                    noTeamsToShow.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<List<Final>> call, Throwable t) {
                joinAdapter.removeProgress();
                isLoading = false;
            }
        });
    }

    public void getHackTeamsToJoin(int page) {
        noTeamsToShow.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        joinAdapter.showProgress();
        Call<JoinHackTeamPOJO> call = api.getHackTeams(MainActivity.getIdToken(), hackId, page);
        call.enqueue(new Callback<JoinHackTeamPOJO>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<JoinHackTeamPOJO> call, Response<JoinHackTeamPOJO> response) {
                joinAdapter.removeProgress();
                if(response.code()==401)
                    Functions.fetchToken(requireActivity(), () -> getHackTeamsToJoin(page));
                if (response.isSuccessful()) {
                    joinAdapter.addItems(response.body().getFinal());
                    isLoading = false;
                } else if (response.code() == 404) {
                    if(page == 1)
                    {
                        noTeamsToShow.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<JoinHackTeamPOJO> call, Throwable t) {
                joinAdapter.removeProgress();
                isLoading = false;
            }
        });
    }

    public void AlertDialogBox() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.custom_join_dialog_box, null);

        AppCompatButton joinTeam = (AppCompatButton) mView.findViewById(R.id.joinTeam);
        EditText teamCode = mView.findViewById(R.id.teamCode);
        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

        joinTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Code code = new Code();
                code.setCode(teamCode.getText().toString().trim());

                Call<Response<Void>> call = api.getTeam(MainActivity.getIdToken(), code, hackId);
                call.enqueue(new Callback<Response<Void>>() {
                    @Override
                    public void onResponse(Call<Response<Void>> call, Response<Response<Void>> response) {
                        String message;
                        if (response.isSuccessful()) {
                            Log.i(TAG, "onResponse: "+ response.code());
                            if (response.code() == 201)
                                message = "You have been successfully added to the team";
                            else if (response.code() == 200)
                                message = "Duplicate entry in team is not allowed";
                            else
                                message = "Invalid code...Please check and try again";
                        } else
                            message = "Invalid code...Please check and try again";

                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Response<Void>> call, Throwable t) {
                        Toast.makeText(getContext(), "Try again later !!", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.dismiss();
            }
        });
    }
}