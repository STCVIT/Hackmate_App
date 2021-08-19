package com.example.hackmate.POJOClasses.Kavita.Participants;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class participantPOJO {



        @SerializedName("participant")
        @Expose
        private Participant1 participant1;
        @SerializedName("skills")
        @Expose
        private List<Skill> skills = null;

        public Participant1 getParticipant1() {
            return participant1;
        }

        public void setParticipant1(Participant1 participant1) {
            this.participant1 = participant1;
        }

        public List<Skill> getSkills() {
            return skills;
        }

        public void setSkills(List<Skill> skills) {
            this.skills = skills;
        }

    }
