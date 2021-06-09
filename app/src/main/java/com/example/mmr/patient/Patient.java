package com.example.mmr.patient;

public class Patient {
    private String cin;
    private String adresse;
    private String email;
    private String nom;
    private String prenom;
    private String photo;
    private String tele;
    private String ville;
    private int sang;
    private int assurance;
    private char gender;
    private int age;

    public Patient(String cin, String nom, String prenom) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Patient() {
    }

    public String getCin() {
        return cin;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getEmail() {
        return email;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getPhoto() {
        return photo;
    }

    public String getTele() {
        return tele;
    }

    public String getVille() {
        return ville;
    }

    public char getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public int getSang() {
        return sang;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setSang(int sang) {
        this.sang = sang;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAssurance() {
        return assurance;
    }

    public void setAssurance(int assurance) {
        this.assurance = assurance;
    }
}
