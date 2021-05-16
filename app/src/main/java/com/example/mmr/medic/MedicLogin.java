package com.example.mmr.medic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.mmr.R;
import com.example.mmr.VolleySingleton;
import com.example.mmr.patient.Home;
import com.example.mmr.patient.Patient;
import com.example.mmr.patient.PatientSessionManager;
import com.example.mmr.patient.SignUp;
import com.example.mmr.shared.SharedModel;

import java.util.HashMap;
import java.util.Map;

public class MedicLogin extends AppCompatActivity {

    Button login;
    TextView signUp;
    TextView email;
    TextView password;
    private RequestQueue queue;
    private SharedModel sharedModel;
    private MedicSessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make it fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_medic_login);
        sessionManager=new MedicSessionManager(this);
        if (sessionManager.isLogged()){
            startActivity(new Intent(this, MedicHome.class));
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
                        sharedModel.MedicConnexion(infos, new SharedModel.LoginCallBackMedic() {
                            @Override
                            public void onSuccess(Medcin medcin) {
                                sessionManager.login();
                                sessionManager.setMedic(medcin);
                                startActivity(new Intent(getApplicationContext(), MedicHome.class));
                                finish();
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
                startActivity(new Intent(getApplicationContext(), MedicSignUp.class));
            }
        });
    }
}