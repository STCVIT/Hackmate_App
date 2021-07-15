package com.example.hackmate;

public class teamMember_Model {

    public static class TeamMemberModel extends teamMember_Model {

        private String SerialNo,MemName,MemEmail,MemPosition;
        private int Profilephoto;
        private boolean mMember;

        // Constructor
        public TeamMemberModel(String serialNo, String memName, String memEmail, String memPosition, int profilephoto,boolean m_Member) {
            SerialNo = serialNo;
            MemName = memName;
            MemEmail = memEmail;
            MemPosition = memPosition;
            Profilephoto = profilephoto;
            mMember=m_Member;
        }

        //getter and setter methods


        public String getSerialNo() {
            return SerialNo;
        }

        public void setSerialNo(String serialNo) {
            SerialNo = serialNo;
        }

        public String getMemName() {
            return MemName;
        }

        public void setMemName(String memName) {
            MemName = memName;
        }

        public String getMemEmail() {
            return MemEmail;
        }

        public void setMemEmail(String memEmail) {
            MemEmail = memEmail;
        }

        public String getMemPosition() {
            return MemPosition;
        }

        public void setMemPosition(String memPosition) {
            MemPosition = memPosition;
        }

        public int getProfilephoto() {
            return Profilephoto;
        }

        public void setProfilephoto(int profilephoto) {
            Profilephoto = profilephoto;
        }
        public boolean isMember() {
            return mMember;
        }

    }
}
