package com.example.hackmate.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hackmate.JSONPlaceholders.loginAPI;
import com.example.hackmate.LoginActivity;
import com.example.hackmate.MainActivity;
import com.example.hackmate.POJOClasses.POST.LoginDetails;
import com.example.hackmate.POJOClasses.POST.PostSkills;
import com.example.hackmate.R;
import com.example.hackmate.util.RetrofitInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class CreateAccountFragment extends Fragment {

    private static int IMAGE_TASK = 1;
    View view;
    Button create_profile;
    LoginActivity loginActivity;
    LoginDetails loginDetails;
    String idToken, downloadUrl;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ChipGroup chipGroup;
    PostSkills postSkills;
    private EditText first_name, last_name, username, university, linkedIn_link, github_link, website, bio;
    private loginAPI loginAPI;
    private StorageReference storageReference;
    private Uri uri;
    private ImageView profile_pic;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == IMAGE_TASK && resultCode == RESULT_OK) {
            uri = data.getData();
            profile_pic.setImageURI(uri);
        } else if(data!=null){
            Toast.makeText(getContext(), "Error...Try Again !!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_account, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialise();
//        String email = getArguments().getString("email");
        storageReference = FirebaseStorage.getInstance().getReference("Participants/Profile");

        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_TASK);
            }
        });


        AutoCompleteTextView YOG_CompleteTextView = view.findViewById(R.id.year_of_graduation);
        String[] years = {"Year of Graduation", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
        ArrayAdapter<String> YOG_arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.option_item, years);
        YOG_CompleteTextView.setText(YOG_arrayAdapter.getItem(0), false);
        YOG_CompleteTextView.setDropDownBackgroundResource(R.color.field_fill);
        YOG_CompleteTextView.setAdapter(YOG_arrayAdapter);

        AutoCompleteTextView gender_CompleteTextView = view.findViewById(R.id.gender);
        String[] gender = {"Gender", "Male", "Female", "Other"};
        ArrayAdapter<String> gender_arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.option_item, gender);
        gender_CompleteTextView.setDropDownBackgroundResource(R.color.field_fill);
        gender_CompleteTextView.setText(gender_arrayAdapter.getItem(0).toString(), false);
        gender_CompleteTextView.setAdapter(gender_arrayAdapter);

        create_profile.setOnClickListener(v -> {

            String year = YOG_CompleteTextView.getText().toString();
            String gen = gender_CompleteTextView.getText().toString();
            Log.i("YOG", year);
            Log.i("gender", gen);

            if (first_name.getText().toString().isEmpty()) {
                first_name.setError("First Name is Required");
                first_name.requestFocus();
                return;
            }
            if (last_name.getText().toString().isEmpty()) {
                last_name.setError("Last Name is Required");
                last_name.requestFocus();
                return;
            }
            if (username.getText().toString().isEmpty()) {
                username.setError("Username is Required");
                username.requestFocus();
                return;
            }
            if (university.getText().toString().isEmpty()) {
                university.setError("College or University is Required");
                university.requestFocus();
                return;
            }
            if (linkedIn_link.getText().toString().isEmpty()) {
                linkedIn_link.setText("--");
            } else if(linkedIn_link.getText().toString().equals("--")){
                linkedIn_link.setText("--");
            } else if (!Patterns.WEB_URL.matcher(linkedIn_link.getText().toString()).matches()) {
                linkedIn_link.setError("Please Enter Valid linkedIn link!!");
                linkedIn_link.requestFocus();
                return;
            }
            if (github_link.getText().toString().isEmpty()) {
                github_link.setError("Github Link is Required");
                github_link.requestFocus();
                return;
            } else if(github_link.getText().toString().equals("--")){
                github_link.setText("--");
            }else if (!Patterns.WEB_URL.matcher(github_link.getText().toString()).matches()) {
                github_link.setError("Please Enter Valid Github Link link!!");
                github_link.requestFocus();
                return;
            }
            if (website.getText().toString().isEmpty()) {
                website.setText("--");
            }else if(website.getText().toString().equals("--")){
                website.setText("--");
            }else if(!Patterns.WEB_URL.matcher(website.getText().toString()).matches()){
                website.setError("Please Enter Valid website link!!");
                website.requestFocus();
                return;
            }
            if (bio.getText().toString().isEmpty()) {
                bio.setError("Bio is Required");
                bio.requestFocus();
                return;
            }
            if (year.equals("Year of Graduation")) {
                YOG_CompleteTextView.setError("Year of graduation is Required");
                YOG_CompleteTextView.requestFocus();
                return;
            }

            loginDetails = new LoginDetails(first_name.getText().toString() + " " + last_name.getText().toString(),
                    university.getText().toString(), github_link.getText().toString(), linkedIn_link.getText().toString(),
                    website.getText().toString(), "---", bio.getText().toString(), 2024,
                    username.getText().toString());

            loginDetails.setName(first_name.getText().toString() + " " + last_name.getText().toString());
            loginDetails.setCollege(university.getText().toString());
            loginDetails.setGithub(github_link.getText().toString());
            loginDetails.setLinkedIn(linkedIn_link.getText().toString());
            loginDetails.setWebsite(website.getText().toString());
            loginDetails.setBio(bio.getText().toString());
            loginDetails.setGraduation_year(Integer.parseInt(year));
            loginDetails.setUsername(username.getText().toString());
            if(downloadUrl!=null)
                loginDetails.setPhoto(downloadUrl);
            else
                loginDetails.setPhoto("---");

            List<String> skill =  new ArrayList<>();

            for (int i=0; i<chipGroup.getChildCount();i++){
                Chip chip = (Chip)chipGroup.getChildAt(i);
                String chipText = chip.getText().toString();
                if(chip.isChecked()){
                    if(chipText.equals("Machine Learning")){
                        skill.add("ml");
                    }
                    else if(chipText.equals("Frontend")){
                        skill.add("frontend");
                    }
                    else if(chipText.equals("Backend")){
                        skill.add("backend");
                    }
                    else if(chipText.equals("UI/UX Design")){
                        skill.add("ui/ux");
                    }
                    else if(chipText.equals("Management")){
                        skill.add("management");
                    }
                    else if(chipText.equals("App Development")){
                        skill.add("appdev");
                    }
                }
                // Do something
            }

            postSkills = new PostSkills(skill);
            postSkills.setSkills(skill);

            checkIfEmailVerified();

        });

    }

    public void initialise() {
        create_profile = view.findViewById(R.id.createProfile_button);
        loginActivity = (LoginActivity) getActivity();
        first_name = view.findViewById(R.id.first_name);
        last_name = view.findViewById(R.id.last_name);
        username = view.findViewById(R.id.username);
        university = view.findViewById(R.id.university);
        linkedIn_link = view.findViewById(R.id.linkedIn_link);
        github_link = view.findViewById(R.id.github_link);
        website = view.findViewById(R.id.website);
        bio = view.findViewById(R.id.about);
        chipGroup = view.findViewById(R.id.chipGroup);
        loginAPI = RetrofitInstance.getRetrofitInstance().create(loginAPI.class);
        profile_pic = view.findViewById(R.id.imageView6);
    }

    public void checkIfEmailVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        if (user.isEmailVerified()) {
            mAuth.getCurrentUser().getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                idToken = task.getResult().getToken();
                                Log.i("xx", idToken);


                                Call<Response<Map<String, String>>> call = loginAPI.setLoginDetails("Bearer " + idToken, loginDetails);
                                call.enqueue(new Callback<Response<Map<String, String>>>() {
                                    @Override
                                    public void onResponse(Call<Response<Map<String, String>>> call, Response<Response<Map<String, String>>> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<Response<Map<String, String>>> call, Throwable t) {

                                    }
                                });

                                Call<Void> call2 = loginAPI.postSkills("Bearer " +
                                        idToken, postSkills);
                                call2.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call2, Response<Void> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<Void> call2, Throwable t) {
                                        Toast.makeText(getActivity(), "Error! Please try again later", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                loginActivity.preferences.edit().putInt("response", 200).apply();
                                Log.i("responsexx", String.valueOf(loginActivity.preferences.getInt("response", 0)));
                                Intent intent = new Intent(getActivity(), MainActivity.class);
//                                intent.putExtra("Email", email);
                                intent.putExtra("idToken", idToken);
                                startActivity(intent);
                                loginActivity.finish();
                                Toast.makeText(getContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
//            Toast.makeText(getContext(), "Please Verify your Email to continue", Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = loginActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.bodyFragment, loginActivity.fragmentLogin)
                    .addToBackStack(null)
                    .commit();
            FirebaseAuth.getInstance().signOut();

            //restart this activity

        }
    }
}