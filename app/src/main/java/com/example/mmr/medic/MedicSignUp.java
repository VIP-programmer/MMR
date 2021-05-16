package com.example.mmr.medic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.mmr.R;
import com.example.mmr.VolleySingleton;
import com.example.mmr.patient.Login;
import com.example.mmr.shared.SharedModel;

import java.util.HashMap;
import java.util.Map;

public class MedicSignUp extends AppCompatActivity {
    Button submit;
    private EditText cin;
    private EditText serie;
    private EditText adresse;
    private EditText email;
    private EditText nom;
    private EditText prenom;
    private EditText tele;
    private EditText ville;
    private RadioGroup type;
    private EditText password;
    private EditText confPassword;
    private EditText about;
    private RequestQueue queue;
    private SharedModel sharedModel;
    private boolean positionMarked=false;
    private boolean isPublic=true;
    private Map<String,String> infos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make it fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_medic_sign_up);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        sharedModel = new SharedModel(this, queue);
        submit=findViewById(R.id.submit);
        cin=findViewById(R.id.cin);
        serie=findViewById(R.id.serie);
        adresse=findViewById(R.id.adrs);
        email=findViewById(R.id.email);
        nom=findViewById(R.id.nom);
        prenom=findViewById(R.id.prenom);
        about=findViewById(R.id.about);
        type=findViewById(R.id.type);
        tele=findViewById(R.id.tele);
        ville=findViewById(R.id.ville);
        password=findViewById(R.id.password);
        confPassword=findViewById(R.id.confirm_password);
        type.check(R.id.public_med);
        type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.public_med) isPublic=true;
                else isPublic=false;
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positionMarked){
                    //if already selected a position

                }else{
                    //needs select a position
                    //test if somme input is empty
                    if (cin.getText().toString().matches("") ||
                            cin.getText().toString().matches("") ||
                            adresse.getText().toString().matches("") ||
                            email.getText().toString().matches("") ||
                            nom.getText().toString().matches("") ||
                            prenom.getText().toString().matches("") ||
                            tele.getText().toString().matches("") ||
                            ville.getText().toString().matches("") ||
                            type.getCheckedRadioButtonId() == -1 ||
                            password.getText().toString().matches("") ||
                            confPassword.getText().toString().matches("") ||
                            about.getText().toString().matches("")
                    ){
                        //if one at least is empty
                        Toast.makeText(getApplicationContext(),"Remplissez tous les champs !",Toast.LENGTH_LONG).show();
                    }else{
                        //non is empty
                        //testing password length
                        if (password.getText().length()<8)
                            Toast.makeText(getApplicationContext(),"Attention mot de passe doit contenir plus que 8 caracteres",Toast.LENGTH_LONG).show();
                        else{
                            //testing matching
                            if (! password.getText().toString().equals(confPassword.getText().toString()))
                                Toast.makeText(getApplicationContext(),"Erreur le mots de passes sont différents",Toast.LENGTH_LONG).show();
                            else{
                                //everything is okey sending request
                                //preparing infos
                                infos.put("lname",nom.getText().toString());
                                infos.put("cin",cin.getText().toString());
                                infos.put("serie",serie.getText().toString());
                                infos.put("about",about.getText().toString());
                                infos.put("fname",prenom.getText().toString());
                                infos.put("adrr",adresse.getText().toString());
                                infos.put("ville",ville.getText().toString());
                                infos.put("type",isPublic?"public":"privé");
                                infos.put("email",email.getText().toString());
                                infos.put("tele",tele.getText().toString());
                                infos.put("pass",password.getText().toString());
                                /*
                                sharedModel.registerMedic(infos, new SharedModel.SignUpCallBack() {
                                    @Override
                                    public void onSuccess(String message) {
                                        startActivity(new Intent(getApplicationContext(), Login.class));
                                    }

                                    @Override
                                    public void onErr(String message) {
                                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                    }
                                });*/
                            }
                        }
                    }
                }
            }
        });
    }
}