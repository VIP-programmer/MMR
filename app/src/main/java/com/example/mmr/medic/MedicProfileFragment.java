package com.example.mmr.medic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.mmr.Config;
import com.example.mmr.R;
import com.example.mmr.VolleySingleton;
import com.example.mmr.patient.Patient;
import com.example.mmr.patient.PatientSessionManager;
import com.example.mmr.patient.Positions;
import com.example.mmr.patient.ProfileFragment;
import com.example.mmr.shared.LoadImage;
import com.example.mmr.shared.SharedModel;
import com.google.gson.Gson;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedicProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NOM = "nom";
    private static final String ARG_PRENOM = "prenom";
    private static final String ARG_IMG = "img";
    private static final String ARG_CIN = "cin";
    public static final int CODE_IMG_GALERY=1;
    public static final String SAMPLE_CROP="SampleCropImg";

    // TODO: Rename and change types of parameters
    private String nom;
    private String prenom;
    private String img;
    private String cin;
    Bitmap bitmap;
    private Button submit;
    private Button editCoord;
    private Button changeImg;
    private Button status;
    private Button modify;
    private EditText adresse;
    private EditText email;
    private EditText inputNom;
    private EditText inputPrenom;
    private EditText tele;
    private EditText about;
    private EditText password;
    private EditText confPassword;
    private RequestQueue queue;
    private SharedModel sharedModel;
    private TextView name;
    private CheckBox checkBox;
    private CircleImageView profile;
    private LinearLayout passLayout;
    private LinearLayout confLayout;
    MedicSessionManager sessionManager;
    Map<String,String> infos=new HashMap<>();
    public MedicProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MedicProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedicProfileFragment newInstance(String cin, String nom, String prenom, String img) {
        MedicProfileFragment fragment = new MedicProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NOM, nom);
        args.putString(ARG_PRENOM, prenom);
        args.putString(ARG_IMG, img);
        args.putString(ARG_CIN, cin);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nom = getArguments().getString(ARG_NOM);
            prenom = getArguments().getString(ARG_PRENOM);
            img = getArguments().getString(ARG_IMG);
            cin = getArguments().getString(ARG_CIN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medic_profile, container, false);
        sessionManager=new MedicSessionManager(getContext());
        final boolean[] okeyToGo = {true};
        name=view.findViewById(R.id.doc_profile_name);
        submit=view.findViewById(R.id.doc_profile_edit_save);
        changeImg=view.findViewById(R.id.doc_profile_change_img);
        status=view.findViewById(R.id.statut);
        modify=view.findViewById(R.id.doc_profile_edit_btn);
        adresse=view.findViewById(R.id.doc_profile_input_adrs);
        email=view.findViewById(R.id.doc_profile_input_email);
        inputNom=view.findViewById(R.id.doc_profile_input_nom);
        inputPrenom=view.findViewById(R.id.doc_profile_input_prenom);
        tele=view.findViewById(R.id.doc_profile_input_tele);
        password=view.findViewById(R.id.doc_profile_input_pass);
        confPassword=view.findViewById(R.id.doc_profile_input_passconf);
        profile=view.findViewById(R.id.doc_profile_img);
        checkBox=view.findViewById(R.id.doc_profile_edit_pass);
        passLayout=view.findViewById(R.id.doc_layout_pass);
        confLayout=view.findViewById(R.id.doc_profile_pass_conf);
        editCoord=view.findViewById(R.id.doc_profile_edit_pos);
        about=view.findViewById(R.id.doc_profile_about);
        queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
        name.setText(nom+" "+prenom);
        if (!img.equals("local"))
            new LoadImage(profile,getContext()).execute(img);
        passLayout.setVisibility(View.GONE);
        confLayout.setVisibility(View.GONE);
        setModeModify(false);

        new SharedModel(getContext(),queue).getMyInfosAsMedic(cin, new SharedModel.LoadHomeInfoCallBack() {
            @Override
            public void onSuccess(Vector<Object> vector) {
                Medcin medcin=(Medcin) vector.get(0);
                inputNom.setText(nom);
                inputPrenom.setText(prenom);
                adresse.setText(medcin.getAdresse());
                email.setText(medcin.getEmail());
                tele.setText(medcin.getTele());
                about.setText(medcin.getAbout());
                sessionManager.setOnline(medcin.isOnline());
                if (medcin.isOnline()) {
                    status.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.green));
                }else {
                    status.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.gray_light));
                }
            }

            @Override
            public void onErr(String message) {
                Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
            }
        });
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setModeModify(true);
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isOnline=false;
                if (sessionManager.getIsOnline()) {
                    isOnline = false;
                    status.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.gray_light));
                }else {
                    isOnline = true;
                    status.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.green));
                }
                sessionManager.setOnline(isOnline);
                Map<String,String> infos = new HashMap<>();
                infos.put("cin",sessionManager.getCinMedcin());
                infos.put("isOnline",(isOnline ? "1" : "0"));
                new SharedModel(getActivity(),queue).updateStatut(infos, new SharedModel.SignUpCallBack() {
                    @Override
                    public void onSuccess(String message) {
                        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onErr(String message) {
                        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

        editCoord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MedicInfoMap.class);
                intent.putExtra("cin",cin);
                startActivityForResult(intent, 3);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //test if somme input is empty
                if (adresse.getText().toString().matches("") ||
                        email.getText().toString().matches("") ||
                        inputNom.getText().toString().matches("") ||
                        inputPrenom.getText().toString().matches("") ||
                        tele.getText().toString().matches("") ||
                        about.getText().toString().matches("")
                ){
                    //if one at least is empty
                    Toast.makeText(getContext(),"Remplissez tous les champs !",Toast.LENGTH_LONG).show();
                }else{
                    //everything is okey sending request
                    //preparing infos

                    infos.put("lname",inputNom.getText().toString());
                    infos.put("cin",cin);
                    infos.put("fname",inputPrenom.getText().toString());
                    infos.put("adrr",adresse.getText().toString());
                    infos.put("about",about.getText().toString());
                    infos.put("email",email.getText().toString());
                    infos.put("tele",tele.getText().toString());
                    //test if checkbox is checked
                    if (checkBox.isChecked()){
                        //if one at least is empty
                        if (password.getText().toString().matches("") ||
                                confPassword.getText().toString().matches("")){
                            //if one at least is empty
                            okeyToGo[0] =false;
                            Toast.makeText(getContext(),"Remplissez tous les champs !",Toast.LENGTH_LONG).show();
                        }else {
                            //non is empty
                            //testing password length
                            if (password.getText().length()<8) {
                                okeyToGo[0] =false;
                                Toast.makeText(getContext(), "Attention mot de passe doit contenir plus que 8 caracteres", Toast.LENGTH_LONG).show();
                            }else {
                                //testing matching
                                if (!password.getText().toString().equals(confPassword.getText().toString())) {
                                    okeyToGo[0] =false;
                                    Toast.makeText(getContext(), "Erreur le mots de passes sont différents", Toast.LENGTH_LONG).show();
                                }else {
                                    //everything is okey sending request
                                    //preparing infos
                                    infos.put("pass", password.getText().toString());
                                    okeyToGo[0] =true;
                                }
                            }
                        }
                    }
                    if (okeyToGo[0]){
                        new SharedModel(getContext(),queue).updateMedic(infos, new SharedModel.SignUpCallBack() {
                            @Override
                            public void onSuccess(String message) {

                                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                setModeModify(false);
                            }

                            @Override
                            public void onErr(String message) {
                                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                }
            }
        });
        changeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent()
                        .setAction(Intent.ACTION_GET_CONTENT)
                        .setType("image/*"),CODE_IMG_GALERY);
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    passLayout.setVisibility(View.VISIBLE);
                    confLayout.setVisibility(View.VISIBLE);
                }else {
                    passLayout.setVisibility(View.GONE);
                    confLayout.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_IMG_GALERY && resultCode == RESULT_OK){
            Uri imageUri=data.getData();
            if (imageUri != null) {
                startCrop(imageUri);
            }
        }else if(requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK){
            Uri imageResultCrop = UCrop.getOutput(data);

            if (imageResultCrop != null){
                try {
                    bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imageResultCrop);
                    Log.i("TAG", "onActivityResult: Bitmap:"+ImageToString(bitmap));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                uploadImage(ImageToString(bitmap),cin);
                profile.setImageBitmap(bitmap);
                sessionManager.setImgMedcin(Config.URL+"/Data/images/profile/"+sessionManager.getCinMedcin()+".jpg");
            }
        }
        if (requestCode == 3) {
            if (data != null) {
                if (data.hasExtra("list")) {
                    int taille = 0;
                    Gson gson = new Gson();
                    Positions positions = gson.fromJson(data.getStringExtra("list"), Positions.class);
                    infos.put("hasPos", "");
                    for (Positions.Position position : positions.getPositionVector()) {
                        infos.put(taille + "lat", position.getX() + "");
                        infos.put(taille + "lon", position.getY() + "");
                        infos.put(taille + "titre", position.getName());
                        taille++;
                    }
                    infos.put("size", (taille - 1) + "");
                    /*
                    new SharedModel(getApplicationContext(), queue).updateMedic(infos, new SharedModel.SignUpCallBack() {
                        @Override
                        public void onSuccess(String message) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MedicLogin.class));
                        }

                        @Override
                        public void onErr(String message) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        }
                    });

                     */
                } else {
                    //DONTDO NOTH
                }
            }
        }
    }
    private void startCrop(@Nullable Uri uri){
        String destination=SAMPLE_CROP;
        destination+=".jpg";
        UCrop uCrop=UCrop.of(uri,Uri.fromFile(new File(this.getActivity().getCacheDir(),destination)));
        uCrop.withAspectRatio(1,1);
        uCrop.withMaxResultSize(450,450);
        uCrop.withOptions(getCropOptions());
        uCrop.start(getActivity().getApplicationContext(), MedicProfileFragment.this);
    }

    private UCrop.Options getCropOptions(){
        UCrop.Options options=new UCrop.Options();
        options.setCompressionQuality(70);

        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(true);

        options.setStatusBarColor(getResources().getColor(R.color.design_default_color_primary));
        options.setToolbarColor(getResources().getColor(R.color.design_default_color_primary));

        options.setToolbarTitle("Recorter Image");

        return options;

    }

    private void uploadImage(String image,String cin){
        sessionManager.newImg(true);
        Map<String,String> infos=new HashMap<>();
        infos.put("cin",cin);
        infos.put("image",image);
        new SharedModel(getContext(),queue).UploadProfileImg(infos,new SharedModel.SignUpCallBack() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onErr(String message) {
                Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
            }
        });
    }

    private String ImageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] imgBytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }


    private void setModeModify(boolean isModify){
        int modif=isModify?View.VISIBLE:View.GONE;
        int save=isModify?View.GONE:View.VISIBLE;
        modify.setVisibility(save);
        submit.setVisibility(modif);
        name.setVisibility(save);
        changeImg.setVisibility(save);
        inputNom.setEnabled(isModify);
        inputPrenom.setEnabled(isModify);
        adresse.setEnabled(isModify);
        email.setEnabled(isModify);
        tele.setEnabled(isModify);
        checkBox.setEnabled(isModify);
        checkBox.setVisibility(modif);
        about.setEnabled(isModify);
        editCoord.setVisibility(modif);
    }
}