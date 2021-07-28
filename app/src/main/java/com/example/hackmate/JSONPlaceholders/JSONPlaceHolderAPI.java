package com.example.hackmate.JSONPlaceholders;

import com.example.hackmate.POJOClasses.addProjectPOJO;
import com.example.hackmate.POJOClasses.editProjectPOJO;
import com.example.hackmate.POJOClasses.hackByIdPOJO;
import com.example.hackmate.POJOClasses.hackListPOJO;
import com.example.hackmate.POJOClasses.invitePOJO;
import com.example.hackmate.POJOClasses.loginPOJO;
import com.example.hackmate.POJOClasses.myTeamsPOJO;
import com.example.hackmate.POJOClasses.participantPOJO;
import com.example.hackmate.POJOClasses.teamIdPOJO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JSONPlaceHolderAPI {
//hackListFragment
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("all")
    Call<hackListPOJO> getHacks(@Header("Authorization") String token,
                                @Query(value = "page",encoded = true) int currentPage);

    @GET("ongoing")
    Call<hackListPOJO> getonGoingHacks(@Header("Authorization") String token,
                                       @Query(value = "page",encoded = true) int currentPage);

    @GET("upcoming")
    Call<hackListPOJO> getupComingHacks(@Header("Authorization") String token,
                                        @Query(value = "page",encoded = true) int currentPage);

    @GET("popularity")
    Call<hackListPOJO> getpopularHacks(@Header("Authorization") String token,
                                       @Query(value = "page",encoded = true) int currentPage);
    @GET("getHacks/{id}")
    Call<hackByIdPOJO> getHackById(@Header("Authorization") String token, @Path("id") String id);

    //myTeamsAPI+ TeamLeader+TeamMemberView
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("myTeams?page=1")
    Call<myTeamsPOJO> getmyTeams(@Header("Authorization") String token);

    @GET("DN_Team/{id}")
    Call<teamIdPOJO> getTeamId(@Header("Authorization") String token, @Path("id") String id);
    @GET("participant/get/{id}")
    Call<participantPOJO> getParticiapnt(@Header("Authorization") String token, @Path("id") String id);

    @GET("DN_Team/deleteTeam/:{team_id}")
    Call<teamIdPOJO> deleteTeam(@Header("Authorization") String token, @Path("team_id") String id);

    @GET("projects/get/{team_id}")
    Call<teamIdPOJO> getProject(@Header("Authorization") String token, @Path("project_id") String id);

//InvitesOrRequestFragemnt
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("invites/myInvites")
    Call<invitePOJO> getMyInvites(@Header("Authorization") String token);

    @GET("invites/myInvites")
    Call<invitePOJO> getMyRequests(@Header("Authorization") String token);

//EditProject FRagment
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("projects/get/{id}")
    Call<List<editProjectPOJO>> getEditProject(@Header("Authorization") String token, @Path("id") String id);

   //AddProject

    @POST("/projects/create")
    Call<addProjectPOJO> addProject(@Header("Authorization") String token);

}
