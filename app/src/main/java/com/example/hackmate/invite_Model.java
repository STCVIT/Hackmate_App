package com.example.hackmate;

import android.widget.Button;

public class invite_Model {




    public static class InviteModel extends invite_Model {

        private String MemName, TeamName, InviteTitle;
        private int Profilephoto;
        //private Button accept, reject;


        private boolean expandable;

        // Constructor
        public InviteModel(String memName, String teamName, int profilephoto, String InviteTitle) {

            MemName = memName;
            TeamName = teamName;
            Profilephoto = profilephoto;
            //this.accept = accept;
            //this.reject = reject;
            this.InviteTitle = InviteTitle;
            this.expandable = false;
        }


//getter and setter methods


        public String getMemName() {
            return MemName;
        }

        public void setMemName(String memName) {
            MemName = memName;
        }

        public String getTeamName() {
            return TeamName;
        }

        public void setTeamName(String teamName) {
            TeamName = teamName;
        }

        public int getProfilephoto() {
            return Profilephoto;
        }

        public void setProfilephoto(int profilephoto) {
            Profilephoto = profilephoto;
        }

       /* public Button getAccept() {
            return accept;
        }

        public void setAccept(Button accept) {
            this.accept = accept;
        }

        public Button getReject() {
            return reject;
        }

        public void setReject(Button reject) {
            this.reject = reject;
        }
*/
        public String getInviteTitle() {
            return InviteTitle;
        }

        public void setInviteTitle(String inviteTitle) {
            InviteTitle = inviteTitle;
        }

        public boolean isExpandable() {
            return expandable;
        }



        public void setExpandable(boolean expandable) {
            this.expandable = expandable;
        }
    }

}
