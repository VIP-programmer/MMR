package com.example.mmr.medic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.mmr.R;
import com.example.mmr.VolleySingleton;
import com.example.mmr.patient.Patient;
import com.example.mmr.patient.Positions;
import com.example.mmr.shared.SharedModel;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MedicNoteHome extends AppCompatActivity {
    private RelativeLayout write;
    private RelativeLayout read;
    private SearchableSpinner searchableSpinner;
    private RequestQueue queue;
    private MedicSessionManager sessionManager;
    private Vector<Patient> patients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make it fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_medic_note_home);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        sessionManager = new MedicSessionManager(this);
        searchableSpinner=findViewById(R.id.doc_list_patients);
        write=findViewById(R.id.write_note);
        read=findViewById(R.id.see_notes);
        Vector<String> stringPastients =new Vector<>();
        patients =new Vector<>();
        new SharedModel(this,queue).getPatients(sessionManager.getCinMedcin(), new SharedModel.LoadHomeInfoCallBack() {
            @Override
            public void onSuccess(Vector<Object> vector) {
                for (Object obj: vector) {
                    Patient patient=(Patient) obj;
                    patients.add(patient);
                    stringPastients.add(patient.getNom()+" "+patient.getPrenom());
                    searchableSpinner.setAdapter(new ArrayAdapter<>(MedicNoteHome.this, android.R.layout.simple_spinner_dropdown_item, stringPastients));
                }
            }

            @Override
            public void onErr(String message) {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        });
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicNoteHome.this,MedicNoteList.class);
                intent.putExtra("cin",patients.get(searchableSpinner.getSelectedItemPosition()).getCin());
                startActivity(intent);
            }
        });
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> infos=new HashMap<String,String>();
                Dialog dialog = new Dialog(MedicNoteHome.this);
                dialog.setContentView(R.layout.dialog_add_note);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button mActionOk = dialog.findViewById(R.id.add_note);
                Button mActionCancel = dialog.findViewById(R.id.cancel_note);
                EditText title = dialog.findViewById(R.id.titre_note);
                EditText body = dialog.findViewById(R.id.desc_note);
                Spinner spinner = dialog.findViewById(R.id.note_priority_add);
                dialog.show();
                mActionCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                mActionOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (title.getText().toString().matches("") ||
                                body.getText().toString().matches("") ||
                                spinner.getSelectedItemPosition()==0
                        )
                            Toast.makeText(getApplicationContext(),"quelques champs sont vides",Toast.LENGTH_LONG).show();
                        else {
                            dialog.dismiss();
                            infos.put("cinPat",patients.get(searchableSpinner.getSelectedItemPosition()).getCin());
                            infos.put("cinMed",sessionManager.getCinMedcin());
                            infos.put("title",title.getText().toString());
                            infos.put("priority",spinner.getSelectedItemPosition()+"");
                            infos.put("body",body.getText().toString());
                            new SharedModel(MedicNoteHome.this,queue).addNote(infos, new SharedModel.SignUpCallBack() {
                                @Override
                                public void onSuccess(String message) {
                                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onErr(String message) {
                                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    }
                });
            }
        });
    }
}