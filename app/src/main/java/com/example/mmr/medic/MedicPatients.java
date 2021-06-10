package com.example.mmr.medic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.mmr.R;
import com.example.mmr.VolleySingleton;
import com.example.mmr.patient.MedListAdapter;
import com.example.mmr.patient.OnlineMeds;
import com.example.mmr.patient.Patient;
import com.example.mmr.shared.SharedModel;

import java.util.Vector;

public class MedicPatients extends AppCompatActivity {

    private RequestQueue queue;
    private MedicSessionManager sessionManager;
    Vector<Patient> patients=new Vector<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medic_patients);
        queue = VolleySingleton.getInstance(this).getRequestQueue();
        sessionManager = new MedicSessionManager(this);
        RecyclerView rvMeds = (RecyclerView) findViewById(R.id.patients_list);

        new SharedModel(this,queue).getMyPatients(sessionManager.getCinMedcin(), new SharedModel.LoadHomeInfoCallBack() {
            @Override
            public void onSuccess(Vector<Object> vector) {
                for (Object obj: vector) {
                    patients.add((Patient) obj);
                }
                PatientListAdapter listAdapter = new PatientListAdapter(getApplicationContext(),patients);
                rvMeds.setAdapter(listAdapter);
                // Attach the adapter to the recyclerview to populate items
                rvMeds.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
            }

            @Override
            public void onErr(String message) {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        });
    }
}