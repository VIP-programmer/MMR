package com.example.mmr.shared;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.mmr.R;
import com.example.mmr.VolleySingleton;
import com.example.mmr.patient.Home;
import com.example.mmr.patient.Patient;
import com.example.mmr.patient.SignUp;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    Button login;
    TextView signUp;
    TextView email;
    TextView password;
    private RequestQueue queue;
    private SharedModel sharedModel;
    private PatientSessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager=new PatientSessionManager(this);
        if (sessionManager.isLogged()){
            startActivity(new Intent(this,Home.class));
            finish();
        }
        queue = VolleySingleton.getInstance(this).getRequestQueue();
        sharedModel = new SharedModel(this, queue);
        login=findViewById(R.id.submit);
        signUp=findViewById(R.id.register);
        email =findViewById(R.id.user_name);
        password=findViewById(R.id.password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //test if somme input is empty
                if (email.getText().toString().matches("") ||
                        password.getText().toString().matches("")
                ){//if one at least is empty
                    Toast.makeText(getApplicationContext(),"Remplissez tous les champs !",Toast.LENGTH_LONG).show();
                }else{
                    //non is empty
                    //testing password length
                    if (password.getText().length()<8){
                        Toast.makeText(getApplicationContext(),"Attention mot de passe doit contenir plus que 8 caracteres",Toast.LENGTH_LONG).show();
                    }else {
                        //everything is okey sending request
                        //preparing infos
                        Map<String,String> infos=new HashMap<>();
                        infos.put("mail", email.getText().toString());
                        infos.put("pass",password.getText().toString());
                        sharedModel.connexion(infos, new SharedModel.LoginCallBack() {
                            @Override
                            public void onSuccess(Patient patient) {
                                sessionManager.login();
                                sessionManager.setPatient(patient);
                                startActivity(new Intent(getApplicationContext(), Home.class));
                            }

                            @Override
                            public void onErr(String message) {
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });
    }
}