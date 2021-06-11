package com.example.mmr.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.mmr.R;
import com.example.mmr.VolleySingleton;
import com.example.mmr.medic.Medcin;
import com.example.mmr.shared.LoadImage;
import com.example.mmr.shared.SharedModel;

import java.util.Vector;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileMed extends AppCompatActivity {
    CircleImageView imageView;
    TextView adresse;
    TextView email;
    TextView name;
    TextView about;
    TextView dateJoin;
    TextView speciality;
    TextView status;
    TextView nbPatients;
    Button toMap;
    ImageButton call;
    ImageButton message;
    ImageButton back;
    Medcin medcin;
    String cin;
    String shortAbout;
    boolean isShot;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make it fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile_med);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        imageView = findViewById(R.id.profile_img_doc_pat);
        adresse = findViewById(R.id.doc_adrs);
        email = findViewById(R.id.doc_email);
        name = findViewById(R.id.doc_name);
        about = findViewById(R.id.doc_about);
        speciality = findViewById(R.id.doc_speciality);
        dateJoin = findViewById(R.id.doc_date_join);
        status = findViewById(R.id.doc_status);
        nbPatients = findViewById(R.id.doc_nb_pat);
        call = findViewById(R.id.doc_btn_call);
        message = findViewById(R.id.doc_btn_chat);
        back = findViewById(R.id.doc_btn_back);
        toMap= findViewById(R.id.to_map);
        cin=getIntent().getStringExtra("cin");
        new SharedModel(this,queue).getMedcinInfos(cin, new SharedModel.LoadHomeInfoCallBack() {
            @Override
            public void onSuccess(Vector<Object> vector) {
                medcin=(Medcin)vector.get(0);
                if (!medcin.getPhoto().equals("local")){
                    new LoadImage(imageView,getApplicationContext()).execute(medcin.getPhoto());
                }
                adresse.setText(medcin.getAdresse());
                email.setText(medcin.getEmail());
                name.setText("Dr."+medcin.getNom()+" "+medcin.getPrenom());

                speciality.setText(medcin.getSpeciality());
                dateJoin.setText(medcin.getDateJoin());
                status.setText((medcin.isOnline())?"En ligne":"Hors ligne");
                nbPatients.setText(medcin.getNbPatients());
                //adding see more
                if(medcin.getAbout().length() > 105){
                    shortAbout=medcin.getAbout().substring(0,106)+"...<b>Voir plus</b>";
                    about.setText(Html.fromHtml(shortAbout));
                    isShot=true;
                    about.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (isShot){
                                about.setText(medcin.getAbout());
                                isShot=false;
                            }else{
                                about.setText(Html.fromHtml(shortAbout));
                                isShot=true;
                            }
                        }
                    });
                }else{
                    about.setText(medcin.getAbout());
                }
                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.parse("tel:" + medcin.getTele()));
                        startActivity(intent);
                    }
                });

                message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.parse("smsto:" + medcin.getTele()));
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onErr(String message) {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        });
        toMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Map.class);
                intent.putExtra("cin",cin);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}