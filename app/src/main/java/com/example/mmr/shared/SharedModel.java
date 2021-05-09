package com.example.mmr.shared;

import android.content.Context;
import android.widget.Toast;

import com.example.mmr.Config;

import java.sql.Connection;
import java.sql.DriverManager;

public class SharedModel {
    public static Connection getConnection(Context context){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection connection= DriverManager.getConnection("jdbc:mysql:"+Config.URL+"/mmr_db",Config.ROOT,Config.PASSWORD);
            Toast.makeText(context,"Connecté",Toast.LENGTH_LONG).show();
            return connection;
        }catch (Exception e){
            Toast.makeText(context,"Connexion echoué "+e.getMessage(),Toast.LENGTH_LONG).show();
            return null;
        }
    }
}
