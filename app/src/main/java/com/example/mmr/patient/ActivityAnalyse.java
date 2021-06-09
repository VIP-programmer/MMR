package com.example.mmr.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.mmr.R;
import com.example.mmr.VolleySingleton;
import com.example.mmr.shared.RequestPermissionHandler;
import com.example.mmr.shared.SharedModel;
import com.mapbox.android.core.permissions.PermissionsListener;

import java.util.Vector;

public class ActivityAnalyse extends AppCompatActivity {
    private RequestPermissionHandler mRequestPermissionHandler;
    RecyclerView recyclerView;
    private RequestQueue queue;
    PatientSessionManager sessionManager;
    Vector<Analyse> analyses=new Vector<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make it fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_analyse);
        mRequestPermissionHandler = new RequestPermissionHandler();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            mRequestPermissionHandler.requestPermission(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 123, new RequestPermissionHandler.RequestPermissionListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(ActivityAnalyse.this, "request permission success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed() {
                    Toast.makeText(ActivityAnalyse.this, "request permission failed", Toast.LENGTH_SHORT).show();
                }
            });
        }

        recyclerView=findViewById(R.id.analyse_list);
        sessionManager=new PatientSessionManager(this);
        queue = VolleySingleton.getInstance(this).getRequestQueue();
        new SharedModel(this,queue).getAnalyses(sessionManager.getCinPatient(), new SharedModel.LoadHomeInfoCallBack() {
            @Override
            public void onSuccess(Vector<Object> vector) {
                for (Object obj: vector) {
                    analyses.add((Analyse)obj);
                }
                AnalyseListAdapter medListAdapter = new AnalyseListAdapter(analyses,getApplicationContext());
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mRequestPermissionHandler.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
    }
}