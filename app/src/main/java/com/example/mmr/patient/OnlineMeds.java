package com.example.mmr.patient;

import android.graphics.Bitmap;

import java.util.Vector;

public class OnlineMeds {
    private Vector<OnlineMed> medList;
    public OnlineMeds(){
        medList=new Vector<>();
    }
    public OnlineMeds(Vector<OnlineMed> medList){
        this.medList=new Vector<>();
        this.medList.addAll(medList);
    }

    public void addOnlineMed(OnlineMed onlineMed){
        /*if (! medList.contains(onlineMed))*/ medList.add(onlineMed);
    }
    public Vector<OnlineMed> getMedList() {
        return medList;
    }

    public static class OnlineMed {
        private String profile;
        private String cin;
        private String name;
        private boolean isActive;

        public OnlineMed(String profile, String cin, boolean isActive) {
            this.profile = profile;
            this.cin = cin;
            this.isActive = isActive;
        }

        public OnlineMed(String profile, String cin, String name, boolean isActive) {
            this.profile = profile;
            this.cin = cin;
            this.name = name;
            this.isActive = isActive;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProfile() {
            return profile;
        }

        public String getCin() {
            return cin;
        }


        public boolean isActive() {
            return isActive;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public void setCin(String cin) {
            this.cin = cin;
        }


        public void setActive(boolean active) {
            isActive = active;
        }
    }
}
