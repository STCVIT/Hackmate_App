package com.example.hackmate.POJOClasses.Kavita;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



/*
public class Final{
   @SerializedName("_id")
   public int Hackid;
   @SerializedName("name")
   public String hackName;
   @SerializedName("max_team_size")
   public int teamSizeMax;
   @SerializedName("min_team_size")
   public int teamSizeMin;
   @SerializedName("start")
   public Date startDate;
   @SerializedName("end")
   public Date endDate;


   public int getHackid() {
       return Hackid;
   }

   public void setHackid(int hackid) {
       this.Hackid = hackid;
   }

   public String getHackName() {
       return hackName;
   }

   public void setHackName(String hackName) {
       this.hackName = hackName;
   }

   public int getTeamSizeMax() {
       return teamSizeMax;
   }

   public void setTeamSizeMax(int teamSizeMax) {
       this.teamSizeMax = teamSizeMax;
   }

   public int getTeamSizeMin() {
       return teamSizeMin;
   }

   public void setTeamSizeMin(int teamSizeMin) {
       this.teamSizeMin = teamSizeMin;
   }

   public Date getStartDate() {
       return startDate;
   }

   public void setStartDate(Date startDate) {
       this.startDate = startDate;
   }

   public Date getEndDate() {
       return endDate;
   }

   public void setEndDate(Date endDate) {
       this.endDate = endDate;
   }
}*/


public class Final {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("venue")
    @Expose
    private String venue;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("min_team_size")
    @Expose
    private int minTeamSize;
    @SerializedName("max_team_size")
    @Expose
    private int maxTeamSize;
    @SerializedName("mode_of_conduct")
    @Expose
    private String modeOfConduct;
    @SerializedName("prize_pool")
    @Expose
    private String prizePool;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("organiser_id")
    @Expose
    private String organiserId;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getMinTeamSize() {
        return minTeamSize;
    }

    public void setMinTeamSize(int minTeamSize) {
        this.minTeamSize = minTeamSize;
    }

    public int getMaxTeamSize() {
        return maxTeamSize;
    }

    public void setMaxTeamSize(int maxTeamSize) {
        this.maxTeamSize = maxTeamSize;
    }

    public String getModeOfConduct() {
        return modeOfConduct;
    }

    public void setModeOfConduct(String modeOfConduct) {
        this.modeOfConduct = modeOfConduct;
    }

    public String getPrizePool() {
        return prizePool;
    }

    public void setPrizePool(String prizePool) {
        this.prizePool = prizePool;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getOrganiserId() {
        return organiserId;
    }

    public void setOrganiserId(String organiserId) {
        this.organiserId = organiserId;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}