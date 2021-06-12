package com.example.mmr.medic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.mmr.R;
import com.example.mmr.VolleySingleton;
import com.example.mmr.patient.Patient;
import com.example.mmr.patient.ProfileFragment;
import com.example.mmr.shared.LoadImage;
import com.example.mmr.shared.SharedModel;
import com.mikhaellopez.lazydatepicker.LazyDatePicker;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePatient extends AppCompatActivity {

    private RequestQueue queue;
    private CircleImageView imageView;
    private TextView email;
    private TextView name;
    private LinearLayout call;
    private LinearLayout message;
    private LinearLayout writeNote;
    private LinearLayout seeRecord;
    private LinearLayout setMeet;
    private String cin;
    private Patient patient;
    ImageButton back;
    MedicSessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_patient);
        queue = VolleySingleton.getInstance(this).getRequestQueue();
        sessionManager=new MedicSessionManager(this);
        imageView = findViewById(R.id.doc_profile_img_pat);
        email = findViewById(R.id.patient_email);
        name = findViewById(R.id.patient_name);
        call = findViewById(R.id.patient_btn_call);
        message = findViewById(R.id.patient_btn_chat);
        writeNote = findViewById(R.id.to_notes);
        seeRecord = findViewById(R.id.to_record);
        setMeet = findViewById(R.id.to_calandar);
        back = findViewById(R.id.patient_btn_back);
        cin=getIntent().getStringExtra("cin");
        new SharedModel(this,queue).getPatientInfos(cin, new SharedModel.LoadHomeInfoCallBack() {
            @Override
            public void onSuccess(Vector<Object> vector) {
                patient=(Patient) vector.get(0);
                if (!patient.getPhoto().equals("local")){
                    new LoadImage(imageView,getApplicationContext()).execute(patient.getPhoto());
                }

                email.setText(patient.getEmail());
                name.setText(patient.getNom()+" "+patient.getPrenom());

                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.parse("tel:" + patient.getTele()));
                        startActivity(intent);
                    }
                });

                message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.parse("smsto:" + patient.getTele()));
                        startActivity(intent);
                    }
                });
                seeRecord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ProfilePatient.this,MedicPatientRecord.class);
                        intent.putExtra("cin",patient.getCin());
                        startActivity(intent);
                    }
                });
                writeNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,String> infos=new HashMap<String,String>();
                        Dialog dialog = new Dialog(ProfilePatient.this);
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
                                    infos.put("cinPat",cin);
                                    infos.put("cinMed",sessionManager.getCinMedcin());
                                    infos.put("title",title.getText().toString());
                                    infos.put("priority",spinner.getSelectedItemPosition()+"");
                                    infos.put("body",body.getText().toString());
                                    new SharedModel(ProfilePatient.this,queue).addNote(infos, new SharedModel.SignUpCallBack() {
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
                setMeet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,String> infos=new HashMap<String,String>();
                        Dialog dialog = new Dialog(ProfilePatient.this);
                        dialog.setContentView(R.layout.dialog_add_rendez_vous);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        Button mActionOk = dialog.findViewById(R.id.add_meet);
                        Button mActionCancel = dialog.findViewById(R.id.cancel_meet);
                        LazyDatePicker picker = dialog.findViewById(R.id.lazyDatePicker);
                        NumberPicker hour = dialog.findViewById(R.id.pickerHours);
                        NumberPicker minute = dialog.findViewById(R.id.pickerMinutes);
                        final Boolean[] isSelected = {false};
                        hour.setMaxValue(23);
                        hour.setMinValue(0);
                        minute.setMaxValue(23);
                        minute.setMinValue(0);
                        picker.setOnDateSelectedListener(new LazyDatePicker.OnDateSelectedListener() {
                            @Override
                            public void onDateSelected(Boolean dateSelected) {
                                isSelected[0] =dateSelected;
                            }
                        });
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
                                if (! isSelected[0])
                                    Toast.makeText(getApplicationContext(),"quelques champs sont vides",Toast.LENGTH_LONG).show();
                                else {
                                    dialog.dismiss();
                                    infos.put("cinPat",cin);
                                    infos.put("cinMed",sessionManager.getCinMedcin());
                                    infos.put("date",(picker.getDate().getMonth()+1)+"/"+picker.getDate().getDate()+"/"+picker.getDate().toString().substring(picker.getDate().toString().lastIndexOf(" ")+1));
                                    infos.put("hour",hour.getValue()+"");
                                    infos.put("minute",minute.getValue()+"");
                                    new SharedModel(getApplicationContext(),queue).addMeet(infos, new SharedModel.SignUpCallBack() {
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
            @Override
            public void onErr(String message) {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
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