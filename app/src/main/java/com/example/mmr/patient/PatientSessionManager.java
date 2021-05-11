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
        editor.apply();

    }

    public void login(){
        editor.putBoolean(IS_LOGGED,true);
        editor.apply();
    }

    public boolean isLogged(){
        return preferences.getBoolean(IS_LOGGED,false);
    }

    public void logout(){
        editor.clear().commit();
    }


}
