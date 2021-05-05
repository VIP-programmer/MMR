package com.example.mmr.patient;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.Vector;

public class Meetings {
    private Vector<Meetings.Meeting> meeetList;
    public Meetings(){
        meeetList =new Vector<>();
    }
    public Meetings(Vector<Meetings.Meeting> meeetList){
        this.meeetList =new Vector<>();
        this.meeetList.addAll(meeetList);
    }

    public void addmeeting(Meetings.Meeting meeting){
        /*if (! medList.contains(onlineMed))*/ meeetList.add(meeting);
    }
    public Vector<Meetings.Meeting> getMeeetList() {
        return meeetList;
    }

    public static class Meeting {
        private Bitmap profile;
        private Date date;
        private String body;
        private String docName;

        public Meeting(Bitmap profile, Date date, String body, String docName) {
            this.profile = profile;
            this.date = date;
            this.body = body;
            this.docName = docName;
        }

        public Bitmap getProfile() {
            return profile;
        }

        public Date getDate() {
            return date;
        }

        public String getBody() {
            return body;
        }

        public String getDocName() {
            return docName;
        }

        public String getDayOfMeeting(){
            return "Wed";
        }

        public String getHourOfMeeting(){
            return "09:00";
        }
    }
}
