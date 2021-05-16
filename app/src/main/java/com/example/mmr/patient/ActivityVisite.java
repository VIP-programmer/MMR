package com.example.mmr.patient;

import androidx.appcompat.app.AppCompatActivity;
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

public class ActivityVisite extends AppCompatActivity {
    RecyclerView recyclerView;
    private RequestQueue queue;
    PatientSessionManager sessionManager;
    Vector<Visite> visites=new Vector<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make it fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_visite);
        recyclerView=findViewById(R.id.visit_list);
        sessionManager=new PatientSessionManager(this);
        queue = VolleySingleton.getInstance(this).getRequestQueue();
        new SharedModel(this,queue).getVisites(sessionManager.getCinPatient(), new SharedModel.LoadHomeInfoCallBack() {
            @Override
            public void onSuccess(Vector<Object> vector) {
                for (Object obj: vector) {
                    visites.add((Visite) obj);
                }
                VisiteListAdapter medListAdapter = new VisiteListAdapter(visites);
                recyclerView.setAdapter(medListAdapter);
                // Attach the adapter to the recyclerview to populate items
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onErr(String message) {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        });
    }
}