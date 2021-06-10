package com.example.mmr.medic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.mmr.R;
import com.example.mmr.patient.ActivityAllergie;
import com.example.mmr.patient.ActivityAnalyse;
import com.example.mmr.patient.ActivityMedicament;
import com.example.mmr.patient.ActivityVisite;

public class MedicPatientRecord extends AppCompatActivity {
    private RelativeLayout visites;
    private RelativeLayout allergies;
    private RelativeLayout medicaments;
    private RelativeLayout analyses;
    private String cin="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medic_patient_record);
        visites=findViewById(R.id.doc_rec_visit);
        allergies=findViewById(R.id.doc_rec_alergie);
        analyses=findViewById(R.id.doc_rec_analyses);
        medicaments=findViewById(R.id.doc_rec_medicament);

        if (getIntent().hasExtra("cin"))
            cin=getIntent().getStringExtra("cin");

        visites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicPatientRecord.this,ActivityVisite.class);
                intent.putExtra("cin",cin);
                startActivity(intent);
            }
        });
        allergies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicPatientRecord.this, ActivityAllergie.class);
                intent.putExtra("cin",cin);
                startActivity(intent);
            }
        });
        analyses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicPatientRecord.this, ActivityAnalyse.class);
                intent.putExtra("cin",cin);
                startActivity(intent);
            }
        });
        medicaments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicPatientRecord.this, ActivityMedicament.class);
                intent.putExtra("cin",cin);
                startActivity(intent);
            }
        });
    }
}