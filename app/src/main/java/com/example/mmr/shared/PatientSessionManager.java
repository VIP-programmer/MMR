package com.example.mmr.shared;

import android.content.Context;
import android.content.SharedPreferences;

public class PatientSessionManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static final String PREFS_NAMES= "app_prefs";
    private static final String IS_LOGGED= "isLogged";
    private static final int PRIVATE_MODE= 0;

    private static final String CLIENT="client";
    private static final String PRODS="prods";
    private static final String ITEMS="items";

    private Context context;

    public PatientSessionManager(Context context) {
        this.context = context;
        preferences=context.getSharedPreferences(PREFS_NAMES,PRIVATE_MODE);
        editor=preferences.edit();
    }


    public boolean isLogged(){
        return preferences.getBoolean(IS_LOGGED,false);
    }



    public void logout(){
        editor.clear().commit();
    }


}
