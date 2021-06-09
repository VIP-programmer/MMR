package com.example.mmr.patient;

public class Medicament {
    private String name;
    private String doc;
    private String qnte;
    private String dateStart;
    private String dateEnd;
    private String prix;

    public Medicament(String name, String doc, String qnte, String dateStart, String dateEnd, String prix) {
        this.name = name;
        this.doc = doc;
        this.qnte = qnte;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.prix = prix;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getQnte() {
        return qnte;
    }

    public void setQnte(String qnte) {
        this.qnte = qnte;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}
