package com.example.hackmate.JSONPlaceholders;

import com.example.hackmate.POJOClasses.JoinTeamPOJO;
import com.example.hackmate.POJOClasses.Kavita.Projects.IndividualProject;
import com.example.hackmate.POJOClasses.Kavita.Projects.getProjectPOJO;
import com.example.hackmate.POJOClasses.Kavita.Requests.RequestPOJO;
import com.example.hackmate.POJOClasses.Kavita.Projects.addOReditProject.addProjectPOJO;
import com.example.hackmate.POJOClasses.Kavita.Projects.addOReditProject.editProjectPOJO;
import com.example.hackmate.POJOClasses.Kavita.Hacks.hackByIdPOJO;
import com.example.hackmate.POJOClasses.Kavita.Hacks.hackListPOJO;
import com.example.hackmate.POJOClasses.Kavita.Invites.invitePOJO;
import com.example.hackmate.POJOClasses.Kavita.myTeams.myTeamsPOJO;
import com.example.hackmate.POJOClasses.Kavita.Participants.participantPOJO;
//import com.example.hackmate.POJOClasses.Kavita.teamIdPOJO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JSONPlaceHolderAPI {
    //hackListFragment


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("getHacks/{name}")
    Call<hackListPOJO> getHacks(@Header("Authorization") String token,
                                @Path("name") String name,
                                @Query(value = "page",encoded = true) int currentPage);


    @GET("getHacks/{id}")
    Call<hackByIdPOJO> getHackById(@Header("Authorization") String token, @Path("id") String id);



    //myTeamsAPI+ TeamLeader+TeamMemberView
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("DN_Team/myTeams")
    Call<myTeamsPOJO> getmyTeams(@Header("Authorization") String token,@Query( value = "page", encoded = true) int page);


    @GET("participant/get/{id}")
    Call<participantPOJO> getParticiapnt(@Header("Authorization") String token, @Path("id") String id);

    @DELETE("DN_Team/deleteTeam/{team_id}")
    Call<Void> deleteTeam(@Header("Authorization") String token, @Path("team_id") String id);

    @GET("projects/get/{id}")
    Call<IndividualProject> getProject(@Header("Authorization") String token, @Path("id") String id);

    @GET("projects/getAll")
    Call<getProjectPOJO> getProject1(@Header("Authorization") String token, @Query( value = "participant_id", encoded = true) String particpantID);

    @PATCH("DN_Team/removeMember/{team_id}/{member_id}")
    Call<JoinTeamPOJO> removeMember(@Header("Authorization") String token, @Path("team_id") String teamId, @Path("member_id") String memID);

    @PATCH("DN_Team/{team_id}")
    Call<JoinTeamPOJO> leaveTeam(@Header("Authorization") String token, @Path("team_id") String teamID);

    //InvitesOrRequestFragemnt
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("invites/myInvites")
    Call<invitePOJO> getMyInvites(@Header("Authorization") String token);

    @GET("requests/myRequests")
    Call<RequestPOJO> getMyRequests(@Header("Authorization") String token);

    @POST("invites/inviteStatus/{status}/{id}")
    Call<Void> postInviteStatus(@Header("Authorization") String token,@Path("status") String status, @Path("id") String id);
    @POST("requests/requestStatus/{status}/{id}")
    Call<Void> postRequestStatus(@Header("Authorization") String token,@Path("status") String status, @Path("id") String id);

    //EditProject FRagment
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    /*@GET("projects/get/{id}")
    Call<List<editProjectPOJO>> getEditProject(@Header("Authorization") String token, @Path("id") String id);*/
    @PATCH("DN_Team/update/{id}")
    Call<editProjectPOJO> updateProject(@Header("Authorization") String token, @Path("id") String id,@Body editProjectPOJO editProjectPOJO);

    //AddProject

   /* @POST("projects/create")
    Call<addProjectPOJO> addProject(@Header("Authorization") String token, @Body addProjectPOJO addProjectPOJO);*/

    @PATCH("DN_Team/update/{id}")
    Call<addProjectPOJO> addProject(@Header("Authorization") String token,@Path("id") String id, @Body addProjectPOJO addProjectPOJO);
}
