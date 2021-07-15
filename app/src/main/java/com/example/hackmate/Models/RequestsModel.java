package com.example.hackmate.Models;

public class RequestsModel {

    public static class Requests_Model extends RequestsModel {
        private int Participantphoto_req;
        private String ParticipantName_req,TeamName_req;

        public Requests_Model(int participantphoto_req, String participantName_req, String teamName_req) {
            Participantphoto_req = participantphoto_req;
            ParticipantName_req = participantName_req;
            TeamName_req = teamName_req;
        }

        public int getParticipantphoto_req() {
            return Participantphoto_req;
        }

        public void setParticipantphoto_req(int participantphoto_req) {
            Participantphoto_req = participantphoto_req;
        }

        public String getParticipantName_req() {
            return ParticipantName_req;
        }

        public void setParticipantName_req(String participantName_req) {
            ParticipantName_req = participantName_req;
        }

        public String getTeamName_req() {
            return TeamName_req;
        }

        public void setTeamName_req(String teamName_req) {
            TeamName_req = teamName_req;
        }
    }
}
