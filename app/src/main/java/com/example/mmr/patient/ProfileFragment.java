package com.example.mmr.patient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.mmr.Config;
import com.example.mmr.R;
import com.example.mmr.VolleySingleton;
import com.example.mmr.shared.LoadImage;
import com.example.mmr.shared.LoadingDialogBuilder;
import com.example.mmr.shared.SharedModel;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NOM = "nom";
    private static final String ARG_PRENOM = "prenom";
    private static final String ARG_IMG = "img";
    private static final String ARG_CIN = "cin";
    public static final int CODE_IMG_GALERY=1;
    public static final String SAMPLE_CROP="SampleCropImg";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;
    private String nom;
    private String prenom;
    private String img;
    private String cin;
    Bitmap bitmap;
    private Button submit;
    private Button changeImg;
    private Button modify;
    private EditText adresse;
    private EditText email;
    private EditText inputNom;
    private EditText inputPrenom;
    private EditText tele;
    private EditText ville;
    private Spinner sang;
    private Spinner assurance;
    private EditText age;
    private EditText password;
    private EditText confPassword;
    private RequestQueue queue;
    private SharedModel sharedModel;
    private TextView name;
    private CheckBox checkBox;
    private CircleImageView profile;
    private LinearLayout passLayout;
    private LinearLayout confLayout;
    PatientSessionManager sessionManager;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String cin, String nom, String prenom, String img) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        sessionManager=new PatientSessionManager(getContext());
        final boolean[] okeyToGo = {true};
        name=view.findViewById(R.id.profile_name);
        submit=view.findViewById(R.id.profile_edit_save);
        changeImg=view.findViewById(R.id.change_img);
        modify=view.findViewById(R.id.profile_edit_btn);
        adresse=view.findViewById(R.id.profile_input_adrs);
        email=view.findViewById(R.id.profile_input_email);
        inputNom=view.findViewById(R.id.profile_input_nom);
        inputPrenom=view.findViewById(R.id.profile_input_prenom);
        tele=view.findViewById(R.id.profile_input_tele);
        ville=view.findViewById(R.id.profile_input_ville);
        sang=view.findViewById(R.id.profile_spinner_sang);
        assurance=view.findViewById(R.id.profile_spinner_assur);
        age=view.findViewById(R.id.profile_input_age);
        password=view.findViewById(R.id.profile_input_pass);
        confPassword=view.findViewById(R.id.profile_input_passconf);
        profile=view.findViewById(R.id.profile_img);
        checkBox=view.findViewById(R.id.profile_edit_pass);
        passLayout=view.findViewById(R.id.layout_pass);
        confLayout=view.findViewById(R.id.layout_pass_conf);
        queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        name.setText(nom+" "+prenom);
        Log.i("TAG", "this is image: "+img);
        if (!img.equals("local"))
            new LoadImage(profile,getContext()).execute(img);
        passLayout.setVisibility(View.GONE);
        confLayout.setVisibility(View.GONE);
        setModeModify(false);
        new SharedModel(getContext(),queue).getMyInfos(cin, new SharedModel.LoadHomeInfoCallBack() {
            @Override
            public void onSuccess(Vector<Object> vector) {
                Patient patient=(Patient) vector.get(0);
                inputNom.setText(nom);
                inputPrenom.setText(prenom);
                adresse.setText(patient.getAdresse());
                email.setText(patient.getEmail());
                ville.setText(patient.getVille());
                tele.setText(patient.getTele());
                age.setText(patient.getAge()+"");
                sang.setSelection(patient.getSang());
                assurance.setSelection(patient.getAssurance()-1);
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
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //test if somme input is empty
                if (adresse.getText().toString().matches("") ||
                        email.getText().toString().matches("") ||
                        inputNom.getText().toString().matches("") ||
                        inputPrenom.getText().toString().matches("") ||
                        tele.getText().toString().matches("") ||
                        ville.getText().toString().matches("") ||
                        sang.getSelectedItemPosition() == 0 ||
                        age.getText().toString().matches("")
                ){
                    //if one at least is empty
                    Toast.makeText(getContext(),"Remplissez tous les champs !",Toast.LENGTH_LONG).show();
                }else{
                    //everything is okey sending request
                    //preparing infos
                    Map<String,String> infos=new HashMap<>();
                    infos.put("lname",inputNom.getText().toString());
                    infos.put("cin",cin);
                    infos.put("fname",inputPrenom.getText().toString());
                    infos.put("adrr",adresse.getText().toString());
                    infos.put("ville",ville.getText().toString());
                    infos.put("age",age.getText().toString());
                    infos.put("sang",convertSang(sang.getSelectedItemPosition()));
                    infos.put("assur",convertAssurance(assurance.getSelectedItemPosition()));
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
                        new SharedModel(getContext(),queue).update(infos, new SharedModel.SignUpCallBack() {
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
        uCrop.start(getActivity().getApplicationContext(), ProfileFragment.this);
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
        LoadingDialogBuilder.startDialog(getActivity());
        new SharedModel(getContext(),queue).UploadProfileImg(infos,new SharedModel.SignUpCallBack() {
            @Override
            public void onSuccess(String message) {
                LoadingDialogBuilder.closeDialog();
                sessionManager.setImgPatient(Config.URL+"/Data/images/profile/"+sessionManager.getCinPatient()+".jpg");
                Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onErr(String message) {
                LoadingDialogBuilder.closeDialog();
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
        age.setEnabled(isModify);
        tele.setEnabled(isModify);
        checkBox.setEnabled(isModify);
        ville.setEnabled(isModify);
        sang.setEnabled(isModify);
        assurance.setEnabled(isModify);
        checkBox.setVisibility(modif);
    }
    //converting sang from position in spinner to a name
    public String convertSang(int pos){
        String s="";
        switch (pos){
            case 1:s= "A-";break;
            case 2:s= "A+";break;
            case 3:s= "B-";break;
            case 4:s= "B+";break;
            case 5:s= "O-";break;
            case 6:s= "O+";break;
            case 7:s= "AB-";break;
            case 8:s= "AB+";break;
        }
        return s;
    }
    //converting sang from position in spinner to a name
    public String convertAssurance(int pos){
        String s="";
        switch (pos){
            case 0:s= "RIEN";break;
            case 1:s= "RAMED";break;
            case 2:s= "CNSS";break;
            case 3:s= "CNOPS";break;
        }
        return s;
    }
}