package com.example.hackmate.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.Adapters.HomeAdapter;
import com.example.hackmate.JSONPlaceholders.JSONPlaceHolderAPI;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.Kavita.Hacks.Final;
import com.example.hackmate.POJOClasses.Kavita.Hacks.hackListPOJO;
import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HackListFragment extends Fragment {
    private RecyclerView HackRV;

    private List<Final> HomeArrayList;

    BottomNavigationView bottomNavigation;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String idToken = "Bearer ";
    private Button hackInfo;
    JSONPlaceHolderAPI jsonPlaceHolderAPI;
    HomeAdapter homeAdapter;
    private ChipGroup filterhacks;
    private String status;
    private HackListViewModel viewModel;
    private boolean isLoading = false, isLastPage = false;
    private int page=1;
    ImageView imageView;
    TextView textView;
    int cacheSize= 10*1024*1024;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_hack_list, container, false);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.HomeAppBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return v;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HackListViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        idToken = MainActivity.getIdToken();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.setStatus(status);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigation = getActivity().findViewById(R.id.bottom_nav_bar);

        filterhacks = view.findViewById(R.id.FilterGroupChipGroup);
        List<Integer> checkedChipId = filterhacks.getCheckedChipIds();
        Log.i("chipID", String.valueOf(checkedChipId));
        HomeArrayList = new ArrayList<>();
        imageView=view.findViewById(R.id.imageView7);
        textView=view.findViewById(R.id.textView2);

        imageView.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);

        HackRV = view.findViewById(R.id.RVHack);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        HackRV.setLayoutManager(layoutManager);
        homeAdapter = new HomeAdapter(getContext(), HomeArrayList);
        HackRV.setAdapter(homeAdapter);

        idToken = MainActivity.getIdToken();
        Log.i("xx", String.valueOf(idToken));
        // jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);

        if(viewModel.getStatus() == null || viewModel.getStatus() == "all") {

            status = "all";
            caching();

            // getHacks(status,page=1);
        }

        HackRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) HackRV.getLayoutManager();
                if(linearLayoutManager!=null) {
                    int position = linearLayoutManager.findLastVisibleItemPosition();
                    if(!isLoading) {
                        if(position == homeAdapter.getItemCount()-1 && !isLastPage && ((position+1)%6==0)) {
                            isLoading = true;
                            getHacks(status,++page);
                        }
                    }
                }
            }
        });

        chipsOnClick();

        Log.i("callback problem", "error");

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void caching() {

        Interceptor onlineInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response response = chain.proceed(chain.request());
                int maxAge = 60; // read from cache for 60 seconds even if there is internet connection
                return response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Pragma")
                        .build();
            }
        };
        Cache cache = new Cache(getContext().getCacheDir(), cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new Interceptor() {
                    @Override public okhttp3.Response intercept(Interceptor.Chain chain)
                            throws IOException {
                        Request request = chain.request();
                        if (!isNetworkAvailable()) {
                            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale \
                            request = request
                                    .newBuilder()
                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                    .build();
                        }
                        return chain.proceed(request);
                    }
                })
                .addNetworkInterceptor(onlineInterceptor)
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://hackportalbackend.herokuapp.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = builder.build();
        jsonPlaceHolderAPI=retrofit.create(JSONPlaceHolderAPI.class);
        getHacks(status,page=1);

    }

    public void chipsOnClick()
    {
        filterhacks.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                //status = "all";
                switch (checkedId) {
                    case R.id.POPULAR_Chip:
                        status = "popularity";
                        break;
                    case R.id.ONGOING_Chip:
                        status = "ongoing";
                        break;
                    case R.id.UPCOMING_Chip:
                        status = "upcoming";
                        break;
                    default:
                        status = "all";
                }
                homeAdapter.clearList();
                HackRV.scrollToPosition(0);
                isLastPage = false;
                getHacks(status,page=1);
            }
        });
    }

    public void getHacks(String status,int page) {
        homeAdapter.showProgress();
        Log.i("allhacks", "this chip was clicked");
        //hackListAPI hackListAPI1 = retrofit1.create(hackListAPI.class);

        Log.i("callback problem", "error");
        Call<hackListPOJO> call5 = jsonPlaceHolderAPI.getHacks(idToken,status, page);
        //Call<hackListPOJO> call5 = hackListAPI1.getHacks(idToken, Integer.parseInt("2"));
        Log.i("callback problem2", "error2");
        call5.enqueue(new Callback<hackListPOJO>() {
            @Override
            public void onResponse(Call<hackListPOJO> call5, Response<hackListPOJO> response5) {
                Log.i("callback problem3", "error3");
                homeAdapter.removeProgress();
                if (!response5.isSuccessful()) {
                    Log.i("not sucess5", "code: " + response5.code());
                    imageView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    return;
                }

                hackListPOJO hackListPOJOS = (hackListPOJO) response5.body();
                Log.i("Response body", hackListPOJOS.getLength().toString());
                List<Final> final_objs = hackListPOJOS.getFinal();

                if(final_objs.size()<6)
                    isLastPage = true;
                Log.i("Response body1", final_objs.get(0).getName());
                homeAdapter.setHackList(final_objs);
                isLoading = false;
                Log.i("Response body3", "list sending to adapter sucessfull");

            }

            @Override
            public void onFailure(Call<hackListPOJO> call5, Throwable t) {
                Log.i("failed5", t.getMessage());
                isLoading = false;
                homeAdapter.removeProgress();
                imageView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
            }
        });
    }
}