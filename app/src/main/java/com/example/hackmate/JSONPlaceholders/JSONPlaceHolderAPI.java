package com.example.hackmate.JSONPlaceholders;

import com.example.hackmate.POJOClasses.Kavita.Invites.AcceptPOJO;
import com.example.hackmate.POJOClasses.Kavita.Projects.getProjectPOJO;
import com.example.hackmate.POJOClasses.Kavita.Remove;
import com.example.hackmate.POJOClasses.Kavita.Requests.RequestPOJO;
import com.example.hackmate.POJOClasses.Kavita.addProjectPOJO;
import com.example.hackmate.POJOClasses.Kavita.editProjectPOJO;
import com.example.hackmate.POJOClasses.Kavita.hackByIdPOJO;
import com.example.hackmate.POJOClasses.Kavita.hackListPOJO;
import com.example.hackmate.POJOClasses.Kavita.Invites.invitePOJO;
import com.example.hackmate.POJOClasses.Kavita.myTeamsPOJO;
import com.example.hackmate.POJOClasses.Kavita.participantPOJO;
import com.example.hackmate.POJOClasses.Kavita.teamIdPOJO;
import com.example.hackmate.POJOClasses.ProjectPOJO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
    @GET("DN_Team/myTeams?page=1")
    Call<myTeamsPOJO> getmyTeams(@Header("Authorization") String token);

    @GET("DN_Team/{id}")
    Call<teamIdPOJO> getTeamId(@Header("Authorization") String token, @Path("id") String id);
    @GET("participant/get/{id}")
    Call<participantPOJO> getParticiapnt(@Header("Authorization") String token, @Path("id") String id);

    @DELETE("DN_Team/deleteTeam/{team_id}")
    Call<Void> deleteTeam(@Header("Authorization") String token, @Path("team_id") String id);

    @GET("projects/get/{id}")
    Call<ProjectPOJO> getProject(@Header("Authorization") String token, @Path("id") String id);

    @GET("projects/getAll")
    Call<getProjectPOJO> getProject1(@Header("Authorization") String token, @Query( value = "participant_id", encoded = true) String particpantID);

    @DELETE("DN_Team/removeMember/{team_id}/{member_id}")
    Call<Void> removeMember(@Header("Authorization") String token, @Path("team_id") String teamId, @Path("member_id") String memID);

    @DELETE("DN_Team/{team_id}")
    Call<Void> leaveTeam(@Header("Authorization") String token, @Path("team_id") String teamID);

//InvitesOrRequestFragemnt
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("invites/myInvites")
    Call<invitePOJO> getMyInvites(@Header("Authorization") String token);

   /* @GET("invites/myInvites")
    Call<invitePOJO> getMyRequests(@Header("Authorization") String token);*/

    @GET("requests/myRequests")
    Call<RequestPOJO> getMyRequests(@Header("Authorization") String token);

    @POST("invites/inviteStatus/accepted/{id}")
    Call<Void> postInviteStatus(@Header("Authorization") String token, @Path("id") String id);

//EditProject FRagment
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("projects/get/{id}")
    Call<List<editProjectPOJO>> getEditProject(@Header("Authorization") String token, @Path("id") String id);

   //AddProject

    @POST("projects/create")
    Call<addProjectPOJO> addProject(@Header("Authorization") String token, @Body addProjectPOJO addProjectPOJO);


}
