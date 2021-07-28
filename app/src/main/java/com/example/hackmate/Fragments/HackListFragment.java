
package com.example.hackmate.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.Adapters.HomeAdapter;
import com.example.hackmate.JSONPlaceholders.JSONPlaceHolderAPI;
import com.example.hackmate.POJOClasses.Final;
import com.example.hackmate.POJOClasses.hackListPOJO;
import com.example.hackmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HackListFragment extends Fragment implements HomeAdapter.OnHackListerner {
    private RecyclerView HackRV;
    // Arraylist for storing data
    private List<Final> HomeArrayList;
    //Button hackInfo;
    BottomNavigationView bottomNavigation;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String idToken = "Bearer ";
    private Button hackInfo;

    private Chip allHacks, onGoingHacks, upcomingHacks, popularHacks;
    private ChipGroup filterhacks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hack_list, container, false);

//inflate appbar for this particular fragment
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.HomeAppBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);

        //Bundle bundle = getArguments();
        // String token = bundle.getString("idToken");
        allHacks = view.findViewById(R.id.ALL_Chip);
        onGoingHacks = view.findViewById(R.id.ONGOING_Chip);
        upcomingHacks = view.findViewById(R.id.UPCOMING_Chip);
        popularHacks = view.findViewById(R.id.POPULAR_Chip);
        filterhacks = view.findViewById(R.id.FilterGroupChipGroup);
        List<Integer> checkedChipId = filterhacks.getCheckedChipIds ();
        Log.i("chipID",String.valueOf(checkedChipId));
        HomeArrayList = new ArrayList<>();
        //MainActivity activity = new MainActivity();
        //String id = activity.getidToken();
        //Log.i("id",String.valueOf( id));
        HackRV = view.findViewById(R.id.RVHack);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        HackRV.setLayoutManager(layoutManager);
        HomeAdapter homeAdapter = new HomeAdapter(getContext(), HomeArrayList, this);
        HackRV.setAdapter(homeAdapter);

        HttpLoggingInterceptor loggingInterceptor1 = new HttpLoggingInterceptor();
        loggingInterceptor1.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient1 = new OkHttpClient.Builder()
                //.addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(loggingInterceptor1)
                .build();
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl("https://hackportalbackend.herokuapp.com/getHacks/")
                .client(okHttpClient1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mAuth.getCurrentUser().getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            idToken += task.getResult().getToken();
                            Log.i("xx", idToken);
                            // Send token to your backend via HTTPS
                            // ...
                            JSONPlaceHolderAPI jsonPlaceHolderAPI = retrofit1.create(JSONPlaceHolderAPI.class);
                            //hackListAPI hackListAPI1 = retrofit1.create(hackListAPI.class);

                            Log.i("callback problem", "error");
                            Call<hackListPOJO> call5 = jsonPlaceHolderAPI.getHacks(idToken, Integer.parseInt("2"));
                            //Call<hackListPOJO> call5 = hackListAPI1.getHacks(idToken, Integer.parseInt("2"));
                            Log.i("callback problem2", "error2");
                            call5.enqueue(new Callback<hackListPOJO>() {
                                @Override
                                public void onResponse(Call<hackListPOJO> call5, Response<hackListPOJO> response5) {
                                    Log.i("callback problem3", "error3");
                                    if (!response5.isSuccessful()) {
                                        Log.i("not sucess5", "code: " + response5.code());
                                        return;
                                    }
                                    // hackListPOJO hackListPOJOS= (hackListPOJO) response5.body();
                                    //Log.i("RESPONSE",response5.body().toString());


                                    hackListPOJO hackListPOJOS = (hackListPOJO) response5.body();
                                    Log.i("Response body", hackListPOJOS.getLength().toString());
                                    List<Final> final_objs = hackListPOJOS.getFinal();
                                    Log.i("Response body1", final_objs.get(0).getName());
                                    homeAdapter.setHackList(final_objs);
                                    Log.i("Response body3", "list sending to adapter sucessfull");
                                    //ArrayList<Final> HomeArrayList1 = (ArrayList<Final>) hackListPOJOS.getFinals();
                                    //Log.i("RESPONSE", String.valueOf(HomeArrayList1));

                                }

                                @Override
                                public void onFailure(Call<hackListPOJO> call5, Throwable t) {
                                    Log.i("failed5", t.getMessage());
                                }
                            });

                        }
                    }
                });


        allHacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("allhacks", "this chip was clicked");
                //hackListAPI hackListAPI1 = retrofit1.create(hackListAPI.class);
                JSONPlaceHolderAPI jsonPlaceHolderAPI = retrofit1.create(JSONPlaceHolderAPI.class);

                Log.i("callback problem", "error");
                Call<hackListPOJO> call5 = jsonPlaceHolderAPI.getHacks(idToken, Integer.parseInt("2"));
                //Call<hackListPOJO> call5 = hackListAPI1.getHacks(idToken, Integer.parseInt("2"));
                Log.i("callback problem2", "error2");
                call5.enqueue(new Callback<hackListPOJO>() {
                    @Override
                    public void onResponse(Call<hackListPOJO> call5, Response<hackListPOJO> response5) {
                        Log.i("callback problem3", "error3");
                        if (!response5.isSuccessful()) {
                            Log.i("not sucess5", "code: " + response5.code());
                            return;
                        }
                        // hackListPOJO hackListPOJOS= (hackListPOJO) response5.body();
                        //Log.i("RESPONSE",response5.body().toString());


                        hackListPOJO hackListPOJOS = (hackListPOJO) response5.body();
                        Log.i("Response body", hackListPOJOS.getLength().toString());
                        List<Final> final_objs = hackListPOJOS.getFinal();
                        Log.i("Response body1", final_objs.get(0).getName());
                        homeAdapter.setHackList(final_objs);
                        Log.i("Response body3", "list sending to adapter sucessfull");
                        //ArrayList<Final> HomeArrayList1 = (ArrayList<Final>) hackListPOJOS.getFinals();
                        //Log.i("RESPONSE", String.valueOf(HomeArrayList1));

                    }

                    @Override
                    public void onFailure(Call<hackListPOJO> call5, Throwable t) {
                        Log.i("failed5", t.getMessage());
                    }
                });
            }
        });
        onGoingHacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onGoingHacks", "this chip was clicked");
                //hackListAPI hackListAPI1 = retrofit1.create(hackListAPI.class);
                JSONPlaceHolderAPI jsonPlaceHolderAPI = retrofit1.create(JSONPlaceHolderAPI.class);
                Log.i("onGoingHacks", "this chip was clicked");
                Call<hackListPOJO> call5 = jsonPlaceHolderAPI.getonGoingHacks(idToken, Integer.parseInt("2"));
                //Call<hackListPOJO> call5 = hackListAPI1.getonGoingHacks(idToken, Integer.parseInt("2"));
                Log.i("callback problem2", "error2");
                call5.enqueue(new Callback<hackListPOJO>() {
                    @Override
                    public void onResponse(Call<hackListPOJO> call5, Response<hackListPOJO> response5) {
                        Log.i("callback problem3", "error3");
                        if (!response5.isSuccessful()) {
                            Log.i("not sucess5", "code: " + response5.code());
                            return;
                        }
                        // hackListPOJO hackListPOJOS= (hackListPOJO) response5.body();
                        //Log.i("RESPONSE",response5.body().toString());


                        hackListPOJO hackListPOJOS = (hackListPOJO) response5.body();
                        Log.i("Response body", hackListPOJOS.getLength().toString());
                        List<Final> final_objs = hackListPOJOS.getFinal();
                        Log.i("Response body1", final_objs.get(0).getName());
                        homeAdapter.setHackList(final_objs);
                        Log.i("Response body3", "list sending to adapter sucessfull");
                        //ArrayList<Final> HomeArrayList1 = (ArrayList<Final>) hackListPOJOS.getFinals();
                        //Log.i("RESPONSE", String.valueOf(HomeArrayList1));

                    }

                    @Override
                    public void onFailure(Call<hackListPOJO> call5, Throwable t) {
                        Log.i("failed5", t.getMessage());
                    }
                });

            }
        });
        upcomingHacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("upComing", "this chip was clicked");
                //hackListAPI hackListAPI1 = retrofit1.create(hackListAPI.class);
                JSONPlaceHolderAPI jsonPlaceHolderAPI = retrofit1.create(JSONPlaceHolderAPI.class);
                Log.i("callback problem", "error");
                Call<hackListPOJO> call5 = jsonPlaceHolderAPI.getupComingHacks(idToken, Integer.parseInt("2"));
                //Call<hackListPOJO> call5 = hackListAPI1.getupComingHacks(idToken, Integer.parseInt("1"));
                Log.i("callback problem2", "error2");
                call5.enqueue(new Callback<hackListPOJO>() {
                    @Override
                    public void onResponse(Call<hackListPOJO> call5, Response<hackListPOJO> response5) {
                        Log.i("callback problem3", "error3");
                        if (!response5.isSuccessful()) {
                            Log.i("not sucess5", "code: " + response5.code());
                            return;
                        }
                        // hackListPOJO hackListPOJOS= (hackListPOJO) response5.body();
                        //Log.i("RESPONSE",response5.body().toString());


                        hackListPOJO hackListPOJOS = (hackListPOJO) response5.body();
                        Log.i("Response body", hackListPOJOS.getLength().toString());
                        List<Final> final_objs = hackListPOJOS.getFinal();
                        Log.i("Response body1", final_objs.get(0).getName());
                        homeAdapter.setHackList(final_objs);
                        Log.i("Response body3", "list sending to adapter sucessfull");
                        //ArrayList<Final> HomeArrayList1 = (ArrayList<Final>) hackListPOJOS.getFinals();
                        //Log.i("RESPONSE", String.valueOf(HomeArrayList1));

                    }

                    @Override
                    public void onFailure(Call<hackListPOJO> call5, Throwable t) {
                        Log.i("failed5", t.getMessage());
                    }
                });
            }
        });
        popularHacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("popular", "this chip was clicked");
                //hackListAPI hackListAPI1 = retrofit1.create(hackListAPI.class);
                JSONPlaceHolderAPI jsonPlaceHolderAPI = retrofit1.create(JSONPlaceHolderAPI.class);
                Log.i("onGoingHacks", "this chip was clicked");
                Call<hackListPOJO> call5 = jsonPlaceHolderAPI.getpopularHacks(idToken, Integer.parseInt("2"));
                //Call<hackListPOJO> call5 = hackListAPI1.getpopularHacks(idToken, Integer.parseInt("1"));
                Log.i("callback problem2", "error2");
                call5.enqueue(new Callback<hackListPOJO>() {
                    @Override
                    public void onResponse(Call<hackListPOJO> call5, Response<hackListPOJO> response5) {
                        Log.i("callback problem3", "error3");
                        if (!response5.isSuccessful()) {
                            Log.i("not sucess5", "code: " + response5.code());
                            return;
                        }
                        // hackListPOJO hackListPOJOS= (hackListPOJO) response5.body();
                        //Log.i("RESPONSE",response5.body().toString());


                        hackListPOJO hackListPOJOS = (hackListPOJO) response5.body();
                        Log.i("Response body", hackListPOJOS.getLength().toString());
                        List<Final> final_objs = hackListPOJOS.getFinal();
                        Log.i("Response body1", final_objs.get(0).getName());
                        homeAdapter.setHackList(final_objs);
                        Log.i("Response body3", "list sending to adapter sucessfull");
                        //ArrayList<Final> HomeArrayList1 = (ArrayList<Final>) hackListPOJOS.getFinals();
                        //Log.i("RESPONSE", String.valueOf(HomeArrayList1));

                    }

                    @Override
                    public void onFailure(Call<hackListPOJO> call5, Throwable t) {
                        Log.i("failed5", t.getMessage());
                    }
                });
            }
        });
    }

       





    /*@Override
    public void OnHackClick(int position) {
        bottomNavigation.setVisibility(View.GONE);
        HomeArrayList.get(position);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment,new HackProfileFragment())
                .addToBackStack(null)
                .commit();

    }*/

    @Override
    public void OnHackClick(int position) {
//      HomeArrayList.get(position);
        HackProfileFragment frag = new HackProfileFragment();
        Log.i("hackId", String.valueOf(getId()));
        Bundle bundle = new Bundle();
        bundle.putString("ID", String.valueOf(getId()));
        frag.setArguments(bundle);

        bottomNavigation.setVisibility(View.GONE);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, frag)
                .addToBackStack(null)
                .commit();
    }
}
