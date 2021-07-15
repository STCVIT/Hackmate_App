package com.example.hackmate.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hackmate.Adapters.JoinAdapter;
import com.example.hackmate.R;
import com.google.android.material.chip.ChipGroup;
public class TeamsFragment extends Fragment {

    private LinearLayout filter;
    private ImageView downArrow, upArrow;
    private ChipGroup chips;
    private TextView joinUsingCode;
    private CardView appBar;

    int GET_NAV_CODE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teams, container, false);
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
        upArrow = view.findViewById(R.id.upArrow);

        chips = view.findViewById(R.id.chips);
        joinUsingCode = view.findViewById(R.id.joinTeamCode);

        if(GET_NAV_CODE==1)
            appBar.setVisibility(View.VISIBLE);
        else
            appBar.setVisibility(View.GONE);

        joinUsingCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogBox();
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(upArrow.getVisibility() == View.GONE) {
                    chips.setVisibility(View.VISIBLE);
                    downArrow.setVisibility(View.GONE);
                    upArrow.setVisibility(View.VISIBLE);
                }
                else
                {
                    chips.setVisibility(View.GONE);
                    upArrow.setVisibility(View.GONE);
                    downArrow.setVisibility(View.VISIBLE);
                }
            }
        });

        RecyclerView joinTeamList = view.findViewById(R.id.joinList);
        joinTeamList.setLayoutManager(new LinearLayoutManager(getContext()));
        String[] team_name = {"Desiderata","The Hustlers"};
        String[][] domains = {{"App Dev","Frontend","Backend"},{"Frontend","Backend"}};
        joinTeamList.setAdapter(new JoinAdapter(team_name,domains,GET_NAV_CODE));
    }

    public void AlertDialogBox()
    {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.custom_join_dialog_box,null);

        AppCompatButton joinTeam = (AppCompatButton) mView.findViewById(R.id.joinTeam);
        EditText teamCode = mView.findViewById(R.id.teamCode);
        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

        joinTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}