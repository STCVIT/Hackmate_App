package com.example.hackmate.POJOClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IndividualProject {
    public String _ids;
    @SerializedName("name")
    @Expose
    public String names;
    @SerializedName("description")
    @Expose
    public String descriptions;
    @SerializedName("code")
    @Expose
    public String codes;
    public String participant_id;
    public int __vs;
    public String demonstration;
    public String design;

    public IndividualProject(String _ids, String names, String descriptions, String codes,
                             String participant_id, int __vs, String demonstration, String design) {
        this._ids = _ids;
        this.names = names;
        this.descriptions = descriptions;
        this.codes = codes;
        this.participant_id = participant_id;
        this.__vs = __vs;
        this.demonstration = demonstration;
        this.design = design;
    }

    public String get_ids() {
        return _ids;
    }

    public void set_ids(String _ids) {
        this._ids = _ids;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public String getParticipant_id() {
        return participant_id;
    }

    public void setParticipant_id(String participant_id) {
        this.participant_id = participant_id;
    }

    public int get__vs() {
        return __vs;
    }

    public void set__vs(int __vs) {
        this.__vs = __vs;
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
}
