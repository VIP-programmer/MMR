package com.example.mmr.patient;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mmr.patient.Patient;
import com.google.gson.Gson;

public class PatientSessionManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static final String PREFS_NAMES= "app_prefs_pat";
    private static final String IS_LOGGED= "isLogged_pat";
    private static final int PRIVATE_MODE= 0;

    private static final String CIN_PATIENT="cin_pat";
    private static final String NOM_PATIENT="nom_pat";
    private static final String PRENOM_PATIENT="prenom_pat";
    private static final String EMAIL_PATIENT="email_pat";
    private static final String IMG_PATIENT="img_pat";
    private static final String IS_NEW="img_new";

    private Context context;
    private Gson gson;

    public PatientSessionManager(Context context) {
        this.context = context;
        preferences=context.getSharedPreferences(PREFS_NAMES,PRIVATE_MODE);
        editor=preferences.edit();
    }

    public void setPatient(Patient patient){
        gson= new Gson();
        editor.putString(CIN_PATIENT,patient.getCin());
        editor.putString(NOM_PATIENT,patient.getNom());
        editor.putString(PRENOM_PATIENT,patient.getPrenom());
        editor.putString(EMAIL_PATIENT,patient.getEmail());
        editor.putString(IMG_PATIENT,patient.getPhoto());
        editor.apply();

    }

    public void login(){
        editor.putBoolean(IS_LOGGED,true);
        editor.apply();
    }
    public void newImg(boolean bool){
        editor.putBoolean(IS_NEW,bool);
        editor.apply();
    }

    public Boolean getIsNew() {
        return preferences.getBoolean(IS_NEW,false);
    }

    public boolean isLogged(){
        return preferences.getBoolean(IS_LOGGED,false);
    }

    public void logout(){
        editor.clear().commit();
    }

    public String getCinPatient() {
        gson=new Gson();
        return preferences.getString(CIN_PATIENT, "");
    }

    public String getImgPatient() {
        gson=new Gson();
        return preferences.getString(IMG_PATIENT, "");
    }

    public String getNomPatient() {
        gson=new Gson();
        return preferences.getString(NOM_PATIENT, "");
    }

    public String getPrenomPatient() {
        gson=new Gson();
        return preferences.getString(PRENOM_PATIENT, "");
    }
}
