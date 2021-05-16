package com.example.mmr.patient;

public class Visite {
    private String doc;
    private String date;

    public Visite(String doc, String date) {
        this.doc = doc;
        this.date = date;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
