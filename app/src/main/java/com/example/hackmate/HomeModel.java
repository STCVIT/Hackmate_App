package com.example.hackmate;

public class HomeModel {

    public static class home_Model extends HomeModel {
        private int Hackphoto;
        private String HackName,teamSize,startDate,endDate;

        public home_Model(int hackphoto, String hackName, String teamSize, String startDate, String endDate) {
            Hackphoto = hackphoto;
            HackName = hackName;
            this.teamSize = teamSize;
            this.startDate = startDate;
            this.endDate = endDate;

        }

        public int getHackphoto() {
            return Hackphoto;
        }

        public void setHackphoto(int hackphoto) {
            Hackphoto = hackphoto;
        }

        public String getHackName() {
            return HackName;
        }

        public void setHackName(String hackName) {
            HackName = hackName;
        }

        public String getTeamSize() {
            return teamSize;
        }

        public void setTeamSize(String teamSize) {
            this.teamSize = teamSize;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }


    }


}
