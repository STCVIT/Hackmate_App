package com.example.hackmate.JSONPlaceholders;


import com.example.hackmate.POJOClasses.GetParticipantPOJO;
import com.example.hackmate.POJOClasses.JoinTeamPOJO;
import com.example.hackmate.POJOClasses.POST.Code;
import com.example.hackmate.POJOClasses.POST.LoginDetails;
import com.example.hackmate.POJOClasses.POST.LoginEmail;
import com.example.hackmate.POJOClasses.POST.PatchDetails;
import com.example.hackmate.POJOClasses.POST.PatchTeamDetails;
import com.example.hackmate.POJOClasses.POST.PostSkills;
import com.example.hackmate.POJOClasses.ProjectPOJO;
import com.example.hackmate.POJOClasses.Skill;
import com.example.hackmate.POJOClasses.SkillPerson;
import com.example.hackmate.POJOClasses.Team;
import com.example.hackmate.POJOClasses.loginPOJO;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface loginAPI {
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("participant/login")
    Call<Response<Void>> getLoginStatus(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("participant/login")
    Call<loginPOJO> getParticipant(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("participant/get/{id}")
    Call<GetParticipantPOJO> getParticipantByID(@Header("Authorization") String token, @Path("id") String id);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("projects/getAll")
    Call<ProjectPOJO> getProject(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("skills/mySkills")
    Call<List<Skill>> getSkills(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("DN_Team/{id}")
    Call<JoinTeamPOJO> getTeam(@Header("Authorization") String token, @Path("id") String id);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("DN_Team/admin/{id}")
    Call<List<JoinTeamPOJO>> getMyTeam(@Header("Authorization") String token, @Path("id") String id);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("projects/getAll/")
    Call<ProjectPOJO> getProjectP(@Header("Authorization") String token, @Query("participant_id") String id);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("participant/signup")
    Call<Response<Map<String, String>>> setClaim(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("participant/createProfile")
    Call<Response<Map<String, String>>> setLoginDetails(@Header("Authorization") String token, @Body LoginDetails loginDetail);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PATCH("participant/updateProfile")
    Call<Response<Map<String, String>>> patchProfile(@Header("Authorization") String token, @Body PatchDetails patchDetails);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("skills/mySkills")
    Call<Void> postSkills(@Header("Authorization") String token, @Body PostSkills postSkills);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("skills/mySkills")
    Call<List<Skill>> getSkillsP(@Header("Authorization") String token, @Query("participant_id") String id);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PATCH("DN_Team/update/{id}")
    Call<Void> patchTeamDetails(@Header("Authorization") String token, @Path("id") String id,
                                @Body PatchTeamDetails patchTeamDetails);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("requests/request/{id}")
    Call<Void> postTeamCode(@Header("Authorization") String token, @Path("id") String id);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @DELETE("projects/delete/{id}")
    Call<Map<String,Object>> delProject(@Header("Authorization") String token, @Path("id") String id);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("{id}")
    Call<Void> checkUsername(@Header("Authorization") String token, @Path("id") String id);
}
