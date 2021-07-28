package com.example.hackmate.JSONPlaceholders;

import com.example.hackmate.POJOClasses.FindParticipant.invitePOJO;
import com.example.hackmate.POJOClasses.GetTeams.getTeamsPOJO;
import com.example.hackmate.POJOClasses.JoinHackTeams.JoinHackTeamPOJO;
import com.example.hackmate.POJOClasses.SearchAndJoin.SearchAndJoinPOJO;
import com.example.hackmate.POJOClasses.hackProfilePOJO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface API {
    @GET("participant/get/all/null?page=1")
    Call<invitePOJO> inviteParticipants(@Header("Authorization") String token);

    @GET("DN_Team/all/null?page=1")
    Call<getTeamsPOJO> getAllTeams(@Header("Authorization") String token);

    @GET("getHacks/{id}")
    Call<hackProfilePOJO> hackProfile(@Header("Authorization") String token, @Path("id") String hackID);

    @GET("DN_Team/all/{id}?page=1")
    Call<JoinHackTeamPOJO> getHackTeams(@Header("Authorization") String token, @Path("id") String hackID);

    @GET("DN_Team/teamName/null?name={NAME}")
    Call<SearchAndJoinPOJO> searchTeam(@Header("Authorization") String token, @Path("NAME") String name);
}
