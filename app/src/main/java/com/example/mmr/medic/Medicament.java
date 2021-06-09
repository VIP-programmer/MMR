package com.example.mmr.medic;

public class Medicament {
    private int id;
    private String name;
    private float prix;

    public Medicament(int id, String name, float prix) {
        this.id = id;
        this.name = name;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }
}
