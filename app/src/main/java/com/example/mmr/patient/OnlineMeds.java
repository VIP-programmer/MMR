package com.example.mmr.patient;

import android.graphics.Bitmap;

import java.util.Vector;

public class OnlineMeds {
    private Vector<OnlineMed> medList;
    public OnlineMeds(){
        medList=new Vector<>();
    }
    public OnlineMeds(Vector<OnlineMed> medList){
        medList=new Vector<>();
        medList.addAll(medList);
    }

    public void addOnlineMed(OnlineMed onlineMed){
        /*if (! medList.contains(onlineMed))*/ medList.add(onlineMed);
    }
    public Vector<OnlineMed> getMedList() {
        return medList;
    }

    public static class OnlineMed {
        private Bitmap profile;
        private boolean isActive;

        public OnlineMed(Bitmap profile, boolean isActive) {
            this.profile = profile;
            this.isActive = isActive;
        }

        public Bitmap getProfile() {
            return profile;
        }
    }
}
