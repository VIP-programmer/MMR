package com.example.mmr.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.mmr.R;
import com.example.mmr.VolleySingleton;
import com.example.mmr.shared.SharedModel;

import java.util.Vector;

public class ActivityVisite extends AppCompatActivity {
    RecyclerView recyclerView;
    private RequestQueue queue;
    TextView emplty;
    PatientSessionManager sessionManager;
    Vector<Visite> visites=new Vector<>();
    String cin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make it fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_visite);

        sessionManager=new PatientSessionManager(this);
        if (getIntent().hasExtra("cin"))
            cin=getIntent().getStringExtra("cin");
        else {
            cin=sessionManager.getCinPatient();
        }
        recyclerView=findViewById(R.id.visit_list);
        emplty=findViewById(R.id.empty_view_my_visits);
        recyclerView.setVisibility(View.GONE);
        emplty.setVisibility(View.VISIBLE);
        queue = VolleySingleton.getInstance(this).getRequestQueue();
        new SharedModel(this,queue).getVisites(cin, new SharedModel.LoadVisitCallBack() {
            @Override
            public void onSuccess(Vector<Visite> vector) {
                visites.addAll(vector);
                VisiteListAdapter medListAdapter = new VisiteListAdapter(visites);
                recyclerView.setAdapter(medListAdapter);
                // Attach the adapter to the recyclerview to populate items
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                if (visites.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    emplty.setVisibility(View.VISIBLE);
                }
                else {
                    recyclerView.setVisibility(View.VISIBLE);
                    emplty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onErr(String message) {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        });
    }
}