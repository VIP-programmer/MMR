package com.example.mmr.medic;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mmr.patient.Patient;
import com.google.gson.Gson;

public class MedicSessionManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static final String PREFS_NAMES= "app_prefs_med";
    private static final String IS_LOGGED= "isLogged_med";
    private static final int PRIVATE_MODE= 0;

    private static final String CIN_PATIENT="cin_med";
    private static final String NOM_PATIENT="nom_med";
    private static final String PRENOM_PATIENT="prenom_med";
    private static final String EMAIL_PATIENT="email_med";
    private static final String IMG_PATIENT="img_med";
    private static final String IS_NEW="new_med";
    private static final String IS_ONLINE="online_med";

    private Context context;
    private Gson gson;

    public MedicSessionManager(Context context) {
        this.context = context;
        preferences=context.getSharedPreferences(PREFS_NAMES,PRIVATE_MODE);
        editor=preferences.edit();
    }

    public void setMedic(Medcin medcin){
        gson= new Gson();
        editor.putString(CIN_PATIENT,medcin.getCin());
        editor.putString(NOM_PATIENT,medcin.getNom());
        editor.putString(PRENOM_PATIENT,medcin.getPrenom());
        editor.putString(EMAIL_PATIENT,medcin.getEmail());
        editor.putString(IMG_PATIENT,medcin.getPhoto());
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
    public Boolean getIsOnline() {
        return preferences.getBoolean(IS_ONLINE,false);
    }
    public void setOnline(Boolean bool) {
        editor.putBoolean(IS_ONLINE,bool);
        editor.apply();
    }

    public boolean isLogged(){
        return preferences.getBoolean(IS_LOGGED,false);
    }

    public void logout(){
        editor.clear().commit();
    }

    public String getCinMedcin() {
        gson=new Gson();
        return preferences.getString(CIN_PATIENT, "");
    }

    public String getImgMedcin() {
        gson=new Gson();
        return preferences.getString(IMG_PATIENT, "");
    }
    public void setImgMedcin(String img) {
        editor.putString(IMG_PATIENT,img);
        editor.apply();
    }

    public String getNomMedcin() {
        gson=new Gson();
        return preferences.getString(NOM_PATIENT, "");
    }

    public String getPrenomMedcin() {
        gson=new Gson();
        return preferences.getString(PRENOM_PATIENT, "");
    }
}
