package com.example.hackmate;

public class myTeamsModel {

    public static class myTeams_Model extends myTeamsModel {

        private String TeamName, HackName,TeamPosition;
        private int nextFragment;
        private String Domain1,Domain2,Domain3,Domain4,Domain5;


        public myTeams_Model(String teamName, String hackName, String teamPosition, int nextFragment, String domain1, String domain2, String domain3, String domain4, String domain5) {
            TeamName = teamName;
            HackName = hackName;
            TeamPosition = teamPosition;
            this.nextFragment = nextFragment;
            Domain1 = domain1;
            Domain2 = domain2;
            Domain3 = domain3;
            Domain4 = domain4;
            Domain5 = domain5;

        }

        public String getTeamName() {
            return TeamName;
        }

        public void setTeamName(String teamName) {
            TeamName = teamName;
        }

        public String getHackName() {
            return HackName;
        }

        public void setHackName(String hackName) {
            HackName = hackName;
        }

        public String getTeamPosition() {
            return TeamPosition;
        }

        public void setTeamPosition(String teamPosition) {
            TeamPosition = teamPosition;
        }

        public int getNextFragment() {
            return nextFragment;
        }

        public void setNextFragment(int nextFragment) {
            this.nextFragment = nextFragment;
        }

        public String getDomain1() {
            return Domain1;
        }

        public void setDomain1(String domain1) {
            Domain1 = domain1;
        }

        public String getDomain2() {
            return Domain2;
        }

        public void setDomain2(String domain2) {
            Domain2 = domain2;
        }

        public String getDomain3() {
            return Domain3;
        }

        public void setDomain3(String domain3) {
            Domain3 = domain3;
        }

        public String getDomain4() {
            return Domain4;
        }

        public void setDomain4(String domain4) {
            Domain4 = domain4;
        }

        public String getDomain5() {
            return Domain5;
        }

        public void setDomain5(String domain5) {
            Domain5 = domain5;
        }



    }

}
