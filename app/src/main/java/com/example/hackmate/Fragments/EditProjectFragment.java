package com.example.hackmate.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackmate.JSONPlaceholders.JSONPlaceHolderAPI;
import com.example.hackmate.POJOClassesKavita.editProjectPOJO;
import com.example.hackmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EditProjectFragment extends Fragment {
private TextView ProjectName,ProjectDescription,githubLink,designLink,demoLink,appBarTitle;
    Button saveButton;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String idToken = "Bearer ";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_project, container, false);

//inflate appbar for this particular fragment
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.projectAppBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        saveButton = view.findViewById(R.id.saveChange);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Changes saved !!!", Toast.LENGTH_SHORT).show();

            }
        });

        ProjectName=view.findViewById(R.id.projectNameEdit);
        ProjectDescription=view.findViewById(R.id.projectDescriptionEdit);
        githubLink=view.findViewById(R.id.githubLinkEdit);
        designLink=view.findViewById(R.id.designLinkEdit);
       demoLink=view.findViewById(R.id.designLinkEdit);
       appBarTitle=view.findViewById(R.id.editProject_title2);

       getRetrofit();

    }

    private void getRetrofit() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //.addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(loggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hackportalbackend.herokuapp.com/")
                .client(okHttpClient)
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
                            //editProjectAPI editProjectAPI = retrofit.create(editProjectAPI.class);
                            JSONPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JSONPlaceHolderAPI.class);
                            Call<List<editProjectPOJO>> call5 = jsonPlaceHolderAPI.getEditProject(idToken,"60e865e12d9a640015411630");
                            call5.enqueue(new Callback<List<editProjectPOJO>>() {
                                @Override
                                public void onResponse(Call<List<editProjectPOJO>> call5, Response<List<editProjectPOJO>> response5) {
                                    if (!response5.isSuccessful()) {
                                        Log.i("not sucess5", "code: " + response5.code());
                                        return;
                                    }

                                    List<editProjectPOJO> editProjectPOJOS = response5.body();

                                    for(editProjectPOJO editProjectPOJO : editProjectPOJOS){
                                        String u = editProjectPOJO.getProjectName();
                                        ProjectName.setText(u);
                                        String v = editProjectPOJO.getDescription();
                                        ProjectDescription.setText(v);
                                        String w = editProjectPOJO.getGithubLink();
                                        githubLink.setText(w);
                                        String x = editProjectPOJO.getDesignLink();
                                        designLink.setText(x);
                                        String y = editProjectPOJO.getDemoLink();
                                        demoLink.setText(y);
                                        String z = editProjectPOJO.getProjectName();
                                        appBarTitle.setText(z);
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<editProjectPOJO>> call5, Throwable t) {
                                    Log.i("failed5", t.getMessage());
                                }
                            });
                        }
                    }
                });
    }
}
