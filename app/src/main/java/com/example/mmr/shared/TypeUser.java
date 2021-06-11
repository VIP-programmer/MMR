package com.example.mmr.shared;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.mmr.R;
import com.example.mmr.medic.MedicHome;
import com.example.mmr.medic.MedicLogin;
import com.example.mmr.medic.MedicMap;
import com.example.mmr.medic.MedicSessionManager;
import com.example.mmr.patient.Home;
import com.example.mmr.patient.Login;
import com.example.mmr.patient.PatientSessionManager;

public class TypeUser extends AppCompatActivity {
    RelativeLayout patient;
    RelativeLayout medcin;
    PatientSessionManager patientSessionManager;
    MedicSessionManager medicSessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make it fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_type_user);
        patient=findViewById(R.id.patient);
        medcin=findViewById(R.id.medic);

        medicSessionManager=new MedicSessionManager(this);
        patientSessionManager=new PatientSessionManager(this);

        if (patientSessionManager.isLogged()){
            startActivity(new Intent(this, Home.class));
            finish();
        }else if (medicSessionManager.isLogged()){
                startActivity(new Intent(this, MedicHome.class));
                finish();
        }

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
        medcin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MedicLogin.class));
            }
        });
    }
}