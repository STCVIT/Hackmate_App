package com.example.hackmate.JSONPlaceholders;

import com.example.hackmate.POJOClasses.GetMyTeamPOJO;
import com.example.hackmate.POJOClasses.GetParticipantPOJO;
import com.example.hackmate.POJOClasses.JoinTeamPOJO;
import com.example.hackmate.POJOClasses.ProjectPOJO;
import com.example.hackmate.POJOClasses.loginPOJO;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface loginAPI {
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("login")
    Call<Response<Void>> getLoginStatus(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("login")
    Call<Response<Void>> NewAccount(@Header("Authorization") String token);

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
    Call<loginPOJO> getSkills(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("DN_Team/{id}")
    Call<JoinTeamPOJO> getTeam(@Header("Authorization") String token, @Path("id") String id);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("DN_Team/myTeams?page=1")
    Call<GetMyTeamPOJO> getMyTeam(@Header("Authorization") String token);

}
