package com.example.mmr.patient;

import java.util.Vector;

public class Visite {
    private String doc;
    private String date;
    private String analyse;
    private String allergie;
    private Vector<String> medicaments=new Vector<>();

    public Visite(String doc, String date) {
        this.doc = doc;
        this.date = date;
        analyse="";
        allergie="";
    }

    public String getAnalyse() {
        return analyse;
    }

    public void setAnalyse(String analyse) {
        this.analyse = analyse;
    }

    public String getAllergie() {
        return allergie;
    }

    public void setAllergie(String allergie) {
        this.allergie = allergie;
    }

    public Vector<String> getMedicaments() {
        return medicaments;
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

    public void addMedicament(String medicament) {
        medicaments.add(medicament);
    }
}
