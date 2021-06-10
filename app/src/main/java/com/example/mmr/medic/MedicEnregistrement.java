package com.example.mmr.medic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.mmr.R;
import com.example.mmr.VolleySingleton;
import com.example.mmr.patient.Patient;
import com.example.mmr.shared.SharedModel;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.Vector;

public class MedicEnregistrement extends AppCompatActivity {

    private Button submit;
    private SearchableSpinner searchableSpinner;
    private RequestQueue queue;
    private MedicSessionManager sessionManager;
    private Vector<Patient> patients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medic_enregistrement);
        submit=findViewById(R.id.doc_patients_submit);
        searchableSpinner=findViewById(R.id.doc_list_patients);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        sessionManager = new MedicSessionManager(this);
        Vector<String> stringPastients =new Vector<>();
        patients =new Vector<>();
        new SharedModel(this,queue).getPatients(sessionManager.getCinMedcin(), new SharedModel.LoadHomeInfoCallBack() {
            @Override
            public void onSuccess(Vector<Object> vector) {
                for (Object obj: vector) {
                    Patient patient=(Patient) obj;
                    patients.add(patient);
                    stringPastients.add(patient.getNom()+" "+patient.getPrenom());
                    searchableSpinner.setAdapter(new ArrayAdapter<>(MedicEnregistrement.this, android.R.layout.simple_spinner_dropdown_item, stringPastients));
                }
            }

            @Override
            public void onErr(String message) {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicEnregistrement.this,MedicPatientRecord.class);
                intent.putExtra("cin",patients.get(searchableSpinner.getSelectedItemPosition()).getCin());
                startActivity(intent);
            }
        });

    }
}