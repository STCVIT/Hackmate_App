package com.example.hackmate.JSONPlaceholders;

import com.example.hackmate.POJOClasses.DomainTeamsHack.DomainTeamsHackPOJO;
import com.example.hackmate.POJOClasses.FindParticipant.invitePOJO;
import com.example.hackmate.POJOClasses.FindPtByName.FindPtByNamePOJO;
import com.example.hackmate.POJOClasses.JoinHackTeams.JoinHackTeamPOJO;
import com.example.hackmate.POJOClasses.SearchAndJoinHack.SearchAndJoinHackPOJO;
import com.example.hackmate.POJOClasses.hackProfilePOJO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    @GET("participant/get/all/null?page=1")
    Call<invitePOJO> inviteParticipants(@Header("Authorization") String token);

    @GET("participant/get/skill/null")
    Call<invitePOJO> inviteBySkills(@Header("Authorization") String token,
                                    @Query("page") int pageNum,
                                    @Query("skill") String skill);

    @GET("participant/get/userName")
    Call<FindPtByNamePOJO> getPtByName(@Header("Authorization") String token,
                                     @Query("name") String name,
                                     @Query("page") int page);

    @GET("getHacks/{id}")
    Call<hackProfilePOJO> hackProfile(@Header("Authorization") String token, @Path("id") String hackID);

    @GET("DN_Team/all/{id}?page=1")
    Call<JoinHackTeamPOJO> getHackTeams(@Header("Authorization") String token, @Path("id") String hackID);

    @GET("DN_Team/teamName/{hackId}")
    Call<List<SearchAndJoinHackPOJO>> searchTeamHack(@Header("Authorization") String token,
                                           @Path("hackId") String hackId,
                                           @Query("name") String name);

    @GET("DN_Team/teamSkills/{hackID}")
    Call<DomainTeamsHackPOJO> domainTeamHack(@Header("Authorization") String token,
                                         @Path("hackID") String hackId,
                                         @Query("page") int pageNum,
                                         @Query("skill") String skill);
}
