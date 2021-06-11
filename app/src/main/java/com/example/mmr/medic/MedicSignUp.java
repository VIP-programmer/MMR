package com.example.mmr.medic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.mmr.R;
import com.example.mmr.VolleySingleton;
import com.example.mmr.patient.Home;
import com.example.mmr.patient.Login;
import com.example.mmr.patient.Positions;
import com.example.mmr.patient.SignUp;
import com.example.mmr.shared.LoadingDialogBuilder;
import com.example.mmr.shared.MailSender;
import com.example.mmr.shared.SharedModel;
import com.google.gson.Gson;

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
    private Spinner speciality;
    private RadioGroup type;
    private EditText password;
    private EditText confPassword;
    private EditText about;
    private RequestQueue queue;
    private SharedModel sharedModel;
    private boolean positionMarked=false;
    private boolean isPublic=true;
    private Map<String,String> infos=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make it fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_medic_sign_up);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        sharedModel = new SharedModel(this, queue);
        submit=findViewById(R.id.doc_to_map);
        cin=findViewById(R.id.doc_cin);
        serie=findViewById(R.id.doc_serie);
        adresse=findViewById(R.id.doc_adrs);
        email=findViewById(R.id.doc_email);
        speciality=findViewById(R.id.spinner_pecialite);
        nom=findViewById(R.id.doc_nom);
        prenom=findViewById(R.id.doc_prenom);
        about=findViewById(R.id.doc_about);
        type=findViewById(R.id.type);
        tele=findViewById(R.id.doc_tele);
        ville=findViewById(R.id.doc_ville);
        password=findViewById(R.id.doc_password);
        confPassword=findViewById(R.id.doc_confirm_password);
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
                            adresse.getText().toString().matches("") ||
                            email.getText().toString().matches("") ||
                            nom.getText().toString().matches("") ||
                            prenom.getText().toString().matches("") ||
                            tele.getText().toString().matches("") ||
                            ville.getText().toString().matches("") ||
                            type.getCheckedRadioButtonId() == -1 ||
                            speciality.getSelectedItemPosition() == 0 ||
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
                                if (!MailSender.isValid(email.getText().toString()))
                                    Toast.makeText(getApplicationContext(),"Email non valide",Toast.LENGTH_LONG).show();
                                else{
                                    Dialog dialog=new Dialog(MedicSignUp.this);
                                    dialog.setContentView(R.layout.dialog_confirm_email);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    EditText code=(EditText) dialog.findViewById(R.id.code);
                                    Button validate=(Button) dialog.findViewById(R.id.validate);
                                    TextView errorText=(TextView) dialog.findViewById(R.id.erreur);
                                    validate.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String currentCode= code.getText().toString().trim();
                                            if (MailSender.codeValid(currentCode)){
                                                errorText.setVisibility(View.GONE);
                                                dialog.dismiss();
                                            }else{
                                                errorText.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    });
                                    MailSender.sendVerificationEmail(MedicSignUp.this,nom.getText().toString()+" "+prenom.getText().toString(),email.getText().toString());
                                    dialog.show();
                                    DisplayMetrics metrics = getResources().getDisplayMetrics();
                                    int width = metrics.widthPixels;
                                    dialog.getWindow().setLayout((6*width)/7, WindowManager.LayoutParams.WRAP_CONTENT);
                                    dialog.setCancelable(false);
                                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialog) {
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
                                            infos.put("specialite",speciality.getSelectedItemPosition()+"");
                                            infos.put("tele",tele.getText().toString());
                                            infos.put("pass",password.getText().toString());
                                            startActivityForResult(new Intent(getApplicationContext(),MedicMap.class), 2);
                                        }
                                    });

                                }

                            }
                        }
                    }
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2){
            if (data !=null) {
                if (data.hasExtra("list")) {
                    LoadingDialogBuilder.startDialog(MedicSignUp.this);
                    int taille=0;
                    Gson gson=new Gson();
                    Positions positions=gson.fromJson(data.getStringExtra("list"),Positions.class);
                    for (Positions.Position position: positions.getPositionVector()) {
                        infos.put(taille+"lat",position.getX()+"");
                        infos.put(taille+"lon",position.getY()+"");
                        infos.put(taille+"titre",position.getName());
                        taille++;
                    }
                    infos.put("size",(taille-1)+"");
                    new SharedModel(getApplicationContext(),queue).registerMedic(infos, new SharedModel.SignUpCallBack() {
                        @Override
                        public void onSuccess(String message) {
                            LoadingDialogBuilder.closeDialog();
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onErr(String message) {
                            LoadingDialogBuilder.closeDialog();
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    //DONTDO NOTH
                }
            }
        }
        /*
        Place place = PlacePicker.getPlace(data,this);
        latitude=String.valueOf(place.getLatLng().latitude);
        longitude=String.valueOf(place.getLatLng().longitude);
        Log.i("TAG", "onActivityResult: "+latitude+", "+longitude);

         */
    }
}