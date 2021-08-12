package com.example.hackmate.JSONPlaceholders;

import com.example.hackmate.POJOClasses.AddSkill;
import com.example.hackmate.POJOClasses.CreateTeamResponse;
import com.example.hackmate.POJOClasses.InvitationSent.Invites;
import com.example.hackmate.POJOClasses.JoinHackTeams.Final;
import com.example.hackmate.POJOClasses.POST.Code;
import com.example.hackmate.POJOClasses.FindParticipant.invitePOJO;
import com.example.hackmate.POJOClasses.JoinHackTeams.JoinHackTeamPOJO;
import com.example.hackmate.POJOClasses.POST.CreateTeam;
import com.example.hackmate.POJOClasses.hackProfilePOJO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    @GET("participant/get/all/{hackid}")
    Call<invitePOJO> inviteParticipants(@Header("Authorization") String token,@Path("hackid") String hackId, @Query("page") int page);

    @GET("participant/get/skill/{hackid}")
    Call<invitePOJO> inviteBySkills(@Header("Authorization") String token,
                                    @Path("hackid") String hackId,
                                    @Query("page") int pageNum,
                                    @Query("skill") String skill);

    @GET("participant/get/userName/{hackid}")
    Call<invitePOJO> getPtByName(@Header("Authorization") String token,
                                 @Path("hackid") String hackId,
                                 @Query("name") String name,
                                 @Query("page") int page);

    @GET("getHacks/{id}")
    Call<hackProfilePOJO> hackProfile(@Header("Authorization") String token, @Path("id") String hackID);

    @GET("DN_Team/all/{id}")
    Call<JoinHackTeamPOJO> getHackTeams(@Header("Authorization") String token,
                                        @Path("id") String hackID,
                                        @Query("page") int pageNum);

    @GET("DN_Team/teamName/{hackId}")
    Call<List<Final>> searchTeamHack(@Header("Authorization") String token,
                                     @Path("hackId") String hackId,
                                     @Query("name") String name);

    @GET("DN_Team/teamSkills/{hackID}")
    Call<JoinHackTeamPOJO> domainTeamHack(@Header("Authorization") String token,
                                         @Path("hackID") String hackId,
                                         @Query("page") int pageNum,
                                         @Query("skill") String skill);

    @GET("invites/myInvites")
    Call<Invites> inviteSent(@Header("Authorization") String token);

    @POST("DN_Team/code/{hackID}")
    Call<Response<Void>> getTeam(@Header("Authorization") String token, @Body Code code, @Path("hackID") String hackId);

    @POST("Dn_Team/createTeam/{hackID}")
    Call<CreateTeamResponse> createTeam(@Header("Authorization") String token, @Body CreateTeam createTeam, @Path("hackID") String hackId);

    @POST("DN_Team/addSkills/{teamId}")
    Call<List<CreateTeamResponse>> addSkill(@Header("Authorization") String token, @Body AddSkill addSkill, @Path("teamId") String teamId);

    @POST("invites/invite/{teamId}/{ptId}")
    Call<Void> sendInvitation(@Header("Authorization") String token, @Path("teamId") String teamId, @Path("ptId") String ptId);

    @DELETE("participant/deleteProfile")
    Call<Void> delete(@Header("Authorization") String token);
}
