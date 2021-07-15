package com.example.hackmate;

public class InvitesModel {

    public static class Invites_Model extends InvitesModel {

        private int Participantphoto;
        private String ParticipantName_invites,TeamName_invites;

        public Invites_Model(int participantphoto, String participantName_invites, String teamName_invites) {
            Participantphoto = participantphoto;
            ParticipantName_invites = participantName_invites;
            TeamName_invites = teamName_invites;
        }

        public int getParticipantphoto() {
            return Participantphoto;
        }

        public void setParticipantphoto(int participantphoto) {
            Participantphoto = participantphoto;
        }

        public String getParticipantName_invites() {
            return ParticipantName_invites;
        }

        public void setParticipantName_invites(String participantName_invites) {
            ParticipantName_invites = participantName_invites;
        }

        public String getTeamName_invites() {
            return TeamName_invites;
        }

        public void setTeamName_invites(String teamName_invites) {
            TeamName_invites = teamName_invites;
        }
    }
}
