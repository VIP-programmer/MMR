package com.example.mmr.patient;

public class Analyse {
    private String titre;
    private String date;
    private String lien;

    public Analyse(String titre, String date, String lien) {
        this.titre = titre;
        this.date = date;
        this.lien = lien;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

}
