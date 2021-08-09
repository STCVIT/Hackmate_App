package com.example.hackmate.POJOClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamProject{
    public String _idp;
    @SerializedName("name")
    @Expose
    public String names;
    public String admin_id;
    public String team_code;
    public List<Member> members;
    public int __vp;
    public String project_name;
    @SerializedName("code")
    @Expose
    public String codes;
    public String demonstration;
    public String design;
    public String project_description;

    public TeamProject(String _idp, String names, String admin_id, String team_code,
                       List<Member> members, int __vp, String project_name, String codes,
                       String demonstration, String design, String project_description) {
        this._idp = _idp;
        this.names = names;
        this.admin_id = admin_id;
        this.team_code = team_code;
        this.members = members;
        this.__vp = __vp;
        this.project_name = project_name;
        this.codes = codes;
        this.demonstration = demonstration;
        this.design = design;
        this.project_description = project_description;
    }

    public String get_idp() {
        return _idp;
    }

    public void set_idp(String _idp) {
        this._idp = _idp;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getTeam_code() {
        return team_code;
    }

    public void setTeam_code(String team_code) {
        this.team_code = team_code;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public int get__vp() {
        return __vp;
    }

    public void set__vp(int __vp) {
        this.__vp = __vp;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public String getDemonstration() {
        return demonstration;
    }

    public void setDemonstration(String demonstration) {
        this.demonstration = demonstration;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getProject_description() {
        return project_description;
    }

    public void setProject_description(String project_description) {
        this.project_description = project_description;
    }
}
