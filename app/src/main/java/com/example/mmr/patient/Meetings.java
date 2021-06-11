package com.example.mmr.patient;

import android.graphics.Bitmap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        private String day;
        private String hour;
        private int month;
        private String year;
        private String docName;

        public Meeting(String date, String hour, String docName) {
            day=extractDay(date);
            month=extractMonth(date);
            year = extractYear(date);
            this.hour = hour;
            this.docName = docName;
        }

        private String extractYear(String date) {
            return date.substring(0,4);
        }

        private int extractMonth(String date) {
            return Integer.parseInt(date.substring(5,7));
        }
        private String extractDay(String date){
            return date.substring(8);
        }
        public String getDocName() {
            return docName;
        }

        public int getDayOfMeeting(){
            return Integer.parseInt(day);
        }

        public int getHourOfMeeting(){
            return Integer.parseInt(hour.substring(0,2));
        }
        public int getMinuteOfMeeting(){
            return Integer.parseInt(hour.substring(hour.lastIndexOf(":"+1)));
        }

        public String getMonthName() {
            String s="";
            switch (month){
                case 1:s="Janvier";break;
                case 2:s="Février";break;
                case 3:s="Mars";break;
                case 4:s="Avril";break;
                case 5:s="Mai";break;
                case 6:s="Juin";break;
                case 7:s="Juillet";break;
                case 8:s="Août";break;
                case 9:s="Septembre";break;
                case 10:s="Octobre";break;
                case 11:s="Novembre";break;
                case 12:s="Décembre";break;
            }
            return s;
        }
        public int getMonth() {
            return (month);
        }

        public int getYear() {
            return Integer.parseInt(year);
        }

        public String getDay() {
            return day;
        }

        public String getHour() {
            return hour;
        }
    }
}
