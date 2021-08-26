
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackmate.Adapters.HomeAdapter;
import com.example.hackmate.JSONPlaceholders.JSONPlaceHolderAPI;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.Kavita.Final;
import com.example.hackmate.POJOClasses.Kavita.hackListPOJO;
import com.example.hackmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
<<<<<<< Updated upstream
import com.google.firebase.auth.GetTokenResult;
=======
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
>>>>>>> Stashed changes

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

<<<<<<< Updated upstream
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
=======
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
>>>>>>> Stashed changes
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HackListFragment extends Fragment {
    private RecyclerView HackRV;
<<<<<<< Updated upstream
    //
=======

>>>>>>> Stashed changes
    private List<Final> HomeArrayList;

    BottomNavigationView bottomNavigation;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String idToken = "Bearer ";
    private Button hackInfo;

    private Chip allHacks, onGoingHacks, upcomingHacks, popularHacks;
    private ChipGroup filterhacks;
<<<<<<< Updated upstream

=======
    private String status;
    private HackListViewModel viewModel;
    private boolean isLoading = false, isLastPage = false;
    private int page=1;
    ImageView imageView;
    TextView textView;
    int cacheSize= 10*1024*1024;
>>>>>>> Stashed changes
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_hack_list, container, false);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.HomeAppBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return v;

    }

    @Override
    public void onStart() {
        super.onStart();
        idToken = MainActivity.getidToken();
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
        List<Integer> checkedChipId = filterhacks.getCheckedChipIds();
        Log.i("chipID", String.valueOf(checkedChipId));
        HomeArrayList = new ArrayList<>();
<<<<<<< Updated upstream
        //MainActivity activity = new MainActivity();
        //String id = activity.getidToken();
        //Log.i("id",String.valueOf( id));
=======
imageView=view.findViewById(R.id.imageView7);
textView=view.findViewById(R.id.textView2);

        imageView.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);

>>>>>>> Stashed changes
        HackRV = view.findViewById(R.id.RVHack);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        HackRV.setLayoutManager(layoutManager);
        HomeAdapter homeAdapter = new HomeAdapter(getContext(), HomeArrayList);
        HackRV.setAdapter(homeAdapter);

<<<<<<< Updated upstream
        HttpLoggingInterceptor loggingInterceptor1 = new HttpLoggingInterceptor();
        loggingInterceptor1.setLevel(HttpLoggingInterceptor.Level.BODY);
=======
        idToken = MainActivity.getIdToken();
        Log.i("xx", String.valueOf(idToken));
<<<<<<< Updated upstream
        jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);
Log.i("HacKlist",String.valueOf(viewModel.getStatus()));
=======
       // jsonPlaceHolderAPI = RetrofitInstance.getRetrofitInstance().create(JSONPlaceHolderAPI.class);

>>>>>>> Stashed changes
        if(viewModel.getStatus() == null || viewModel.getStatus() == "all") {

            status = "all";
<<<<<<< Updated upstream
            
            getHacks(status,page=1);

=======
            caching();

           // getHacks(status,page=1);
>>>>>>> Stashed changes
        }
>>>>>>> Stashed changes

        OkHttpClient okHttpClient1 = new OkHttpClient.Builder()
                //.addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(loggingInterceptor1)
                .build();
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl("https://hackportalbackend.herokuapp.com/getHacks/")
                .client(okHttpClient1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        /*mAuth.getCurrentUser().getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            idToken += task.getResult().getToken();

<<<<<<< Updated upstream
         */
        idToken = MainActivity.getidToken();
        Log.i("xx", String.valueOf(idToken));
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
=======
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

        Call<hackListPOJO> call5 = jsonPlaceHolderAPI.getHacks(idToken,status, page);
        call5.enqueue(new Callback<hackListPOJO>() {
            @Override
            public void onResponse(Call<hackListPOJO> call5, Response<hackListPOJO> response5) {

                homeAdapter.removeProgress();
>>>>>>> Stashed changes
                if (!response5.isSuccessful()) {
<<<<<<< Updated upstream
                    Log.i("HackList", "code: " + response5.code());
=======
                    Log.i("not sucess5", "code: " + response5.code());
                    imageView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
>>>>>>> Stashed changes
                    return;
                }
                // hackListPOJO hackListPOJOS= (hackListPOJO) response5.body();
                //Log.i("RESPONSE",response5.body().toString());


                hackListPOJO hackListPOJOS = (hackListPOJO) response5.body();
                Log.i("Response body", hackListPOJOS.getLength().toString());
                List<Final> final_objs = hackListPOJOS.getFinal();
<<<<<<< Updated upstream
                Log.i("Response body1", final_objs.get(0).getName());
                homeAdapter.setHackList(final_objs);
                Log.i("Response body3", "list sending to adapter sucessfull");
                //ArrayList<Final> HomeArrayList1 = (ArrayList<Final>) hackListPOJOS.getFinals();
                //Log.i("RESPONSE", String.valueOf(HomeArrayList1));
=======

                if(final_objs.size()<6)
                    isLastPage = true;
                Log.i("HackList", final_objs.get(0).getName());
                homeAdapter.setHackList(final_objs);
                isLoading = false;
                Log.i("HackList", "list sending to adapter sucessfull");
>>>>>>> Stashed changes

            }

            @Override
            public void onFailure(Call<hackListPOJO> call5, Throwable t) {
                Log.i("failed5", t.getMessage());
<<<<<<< Updated upstream
            }
        });


//});


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
                Call<hackListPOJO> call5 = jsonPlaceHolderAPI.getonGoingHacks(idToken, Integer.parseInt("1"));
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
                Call<hackListPOJO> call5 = jsonPlaceHolderAPI.getupComingHacks(idToken, Integer.parseInt("1"));
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
                Call<hackListPOJO> call5 = jsonPlaceHolderAPI.getpopularHacks(idToken, Integer.parseInt("1"));
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
=======
                isLoading = false;
                homeAdapter.removeProgress();
                imageView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
>>>>>>> Stashed changes
            }
        });
    }
}