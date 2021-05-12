package com.example.mmr.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.mmr.R;
import com.example.mmr.VolleySingleton;
import com.example.mmr.shared.SharedModel;

import java.util.Vector;

public class MyMedcins extends AppCompatActivity {

    private PatientSessionManager sessionManager;
    private OnlineMeds onlineMeds;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make it fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_medcins);
        RecyclerView rvMeds = (RecyclerView) findViewById(R.id.med_list);
        sessionManager=new PatientSessionManager(this);
        queue = VolleySingleton.getInstance(this).getRequestQueue();
        new SharedModel(this,queue).getMedcins(sessionManager.getCinPatient(), new SharedModel.LoadHomeInfoCallBack() {
            @Override
            public void onSuccess(Vector<Object> vector) {
                OnlineMeds meds=(OnlineMeds)vector.get(0);
                MedListAdapter medListAdapter = new MedListAdapter(getApplicationContext(),meds.getMedList());
                rvMeds.setAdapter(medListAdapter);
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