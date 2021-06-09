package com.example.mmr.medic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.mmr.Config;
import com.example.mmr.R;
import com.example.mmr.VolleySingleton;
import com.example.mmr.patient.Patient;
import com.example.mmr.patient.Positions;
import com.example.mmr.shared.SharedModel;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import abhishekti7.unicorn.filepicker.UnicornFilePicker;
import abhishekti7.unicorn.filepicker.utils.Constants;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicAddNewVisit extends AppCompatActivity {
    //Pdf request code
    private  int REQ_PDF = 21;
    private  String encodedPDF;
    private int PICK_PDF_REQUEST = 1;
    public static final String UPLOAD_URL = Config.URL+"/Model/medcin/upload_file.php";

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    private LinearLayout ordContainer;
    private LinearLayout allergieContainer;
    private LinearLayout analyseContainer;

    private CheckBox ordCheck;
    private CheckBox allergieCheck;
    private CheckBox analyseCheck;

    private Button ordBtn;
    private Button analyseBtn;
    private Button submit;

    private TextView ordShow;
    private TextView analyseShow;

    private EditText allergieInput;

    private SearchableSpinner searchableSpinner;

    private Vector<String> stringPastients;
    private Vector<String> stringMedicaments;
    private Vector<Patient> patients;
    private Vector<Medicament> medicaments;
    private RequestQueue queue;
    private MedicSessionManager sessionManager;
    private Map<String,String> infos=new HashMap<>();
    private int medicamentCount=0;
    private String filePath;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medic_add_new_visit);

        ordContainer=findViewById(R.id.ord_container);
        ordBtn=findViewById(R.id.doc_btn_add_medicament);
        ordCheck=findViewById(R.id.check_ajout_medicament);
        ordShow=findViewById(R.id.doc_shaw_medicaments);


        analyseContainer=findViewById(R.id.analyse_container);
        analyseBtn=findViewById(R.id.doc_btn_add_file);
        analyseCheck=findViewById(R.id.check_ajout_analyse);
        analyseShow=findViewById(R.id.doc_shaw_file);

        allergieCheck=findViewById(R.id.check_ajout_allergie);
        allergieContainer=findViewById(R.id.allergie_container);
        allergieInput=findViewById(R.id.input_allergie_name);


        submit=findViewById(R.id.doc_visite_submit);
        searchableSpinner=findViewById(R.id.doc_visit_pat_list);


        queue = VolleySingleton.getInstance(this).getRequestQueue();
        sessionManager=new MedicSessionManager(this);

        allergieContainer.setVisibility(View.GONE);
        analyseContainer.setVisibility(View.GONE);
        ordContainer.setVisibility(View.GONE);
        infos.put("cinMed",sessionManager.getCinMedcin());
        analyseCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    analyseContainer.setVisibility(View.VISIBLE);
                    infos.put("hasAnalyse","");
                }else {
                    analyseContainer.setVisibility(View.GONE);
                }
            }
        });
        ordCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ordContainer.setVisibility(View.VISIBLE);
                    infos.put("hasOrd","");
                }else ordContainer.setVisibility(View.GONE);
            }
        });
        allergieCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    allergieContainer.setVisibility(View.VISIBLE);
                    infos.put("hasAllergie","");
                }
                else allergieContainer.setVisibility(View.GONE);
            }
        });

        stringPastients =new Vector<>();
        patients =new Vector<>();
        new SharedModel(this,queue).getPatients(sessionManager.getCinMedcin(), new SharedModel.LoadHomeInfoCallBack() {
            @Override
            public void onSuccess(Vector<Object> vector) {
                for (Object obj: vector) {
                    Patient patient=(Patient) obj;
                    patients.add(patient);
                    stringPastients.add(patient.getNom()+" "+patient.getPrenom());
                    searchableSpinner.setAdapter(new ArrayAdapter<>(MedicAddNewVisit.this, android.R.layout.simple_spinner_dropdown_item, stringPastients));
                }
            }

            @Override
            public void onErr(String message) {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        });
        //TODO
        searchableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                infos.put("cinPat",patients.get(position).getCin()+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        analyseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
                /*
                UnicornFilePicker.from(MedicAddNewVisit.this)
                     .addConfigBuilder()
                     .selectMultipleFiles(false)
                     .setRootDirectory(Environment.getExternalStorageDirectory().getAbsolutePath())
                     .showHiddenFiles(false)
                     .setFilters(new String[]{"pdf"})
                     .addItemDivider(true)
                     .theme(R.style.UnicornFilePicker_Default)
                     .build()
                     .forResult(Constants.REQ_UNICORN_FILE);

                 */
            }
        });

        ordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MedicAddNewVisit.this);
                dialog.setContentView(R.layout.medic_dialog_add_ord);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button mActionOk = dialog.findViewById(R.id.ord_validate);
                Button mActionCancel = dialog.findViewById(R.id.ord_cancel);
                EditText qntInput = dialog.findViewById(R.id.medicament_qte);
                EditText duree = dialog.findViewById(R.id.medicament_duree);
                SearchableSpinner medicamentSpinner = dialog.findViewById(R.id.medicament_nom_spinner);
                medicaments=new Vector<>();
                stringMedicaments =new Vector<>();
                new SharedModel(getApplicationContext(),queue).getAllMedicaments(sessionManager.getCinMedcin(), new SharedModel.LoadHomeInfoCallBack() {
                    @Override
                    public void onSuccess(Vector<Object> vector) {
                        for (Object obj: vector) {
                            Medicament medicament=(Medicament) obj;
                            medicaments.add(medicament);
                            stringMedicaments.add(medicament.getName());
                            medicamentSpinner.setAdapter(new ArrayAdapter<>(MedicAddNewVisit.this, android.R.layout.simple_spinner_dropdown_item, stringMedicaments));
                        }
                    }

                    @Override
                    public void onErr(String message) {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
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
                        if (qntInput.getText().toString().matches("") ||
                                duree.getText().toString().matches("")
                        )
                            Toast.makeText(getApplicationContext(),"Remplissez tous les champs !",Toast.LENGTH_LONG).show();
                        else {
                            infos.put("medicament"+medicamentCount,medicaments.get(medicamentSpinner.getSelectedItemPosition()).getId()+"");
                            infos.put("medicamentQte"+medicamentCount,qntInput.getText().toString());
                            infos.put("medicamentDuree"+medicamentCount,duree.getText().toString());
                            ordShow.append(medicaments.get(medicamentSpinner.getSelectedItemPosition()).getName()+"\n");
                            medicamentCount++;
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        (ordCheck.isChecked() && ordShow.getText().toString().matches(""))||
                        (allergieCheck.isChecked() && allergieInput.getText().toString().matches(""))||
                        (analyseCheck.isChecked() && analyseShow.getText().toString().matches(""))
                )
                    Toast.makeText(getApplicationContext(),"Remplissez tous les champs !",Toast.LENGTH_LONG).show();
                else {
                    mProgressDialog = new ProgressDialog(MedicAddNewVisit.this);
                    mProgressDialog.setMessage("Veuillez patienter ...");
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.show();

                    infos.put("medicamentSize",(medicamentCount)+"");
                    infos.put("patient",patients.get(searchableSpinner.getSelectedItemPosition()).getCin());
                    if (allergieCheck.isChecked())
                        infos.put("allergie",allergieInput.getText().toString());
                    if (analyseCheck.isChecked()) {

                        infos.put("nameAnalyse",analyseShow.getText().toString());
                        infos.put("analyse", "");
                        infos.put("PDF", encodedPDF);
                    }
                    new SharedModel(getApplicationContext(),queue).addVisit(infos, new SharedModel.SignUpCallBack() {
                        @Override
                        public void onSuccess(String message) {
                            //Upload.uploadDocument(encodedPDF,MedicAddNewVisit.this);
                            /*
                            UploadReceiptService service = RetrofitClientInstance.getRetrofitInstance().create(UploadReceiptService.class);
                            String cookie = "cookie";
                            File file = new File(filePath);
                            RequestBody requestFile =
                                    RequestBody.create(
                                            MediaType.parse("application/pdf"),
                                            file
                                    );
                            MultipartBody.Part body =
                                    MultipartBody.Part.createFormData("files[0]", file.getName(), requestFile);
                            RequestBody items = RequestBody.create(MediaType.parse("application/json"), "[1,2,4]");
                            RequestBody stringValue = RequestBody.create(MediaType.parse("text/plain"), "stringValue");
                            Call<ResponseBody> call = service.uploadReceipt(cookie, body, items, stringValue);
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                }
                            });

                             */
                            //uploadDocument();
                            mProgressDialog.dismiss();
                        }

                        @Override
                        public void onErr(String message) {

                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Erreur"+message,Toast.LENGTH_LONG).show();
                        }
                    });
                    //
                    //sent queue
                }
            }
        });
    }
    private void uploadDocument() {

        Call<ResponsePOJO> call = RetrofitClient.getInstance().getAPI().uploadDocument(encodedPDF,infos.get("patient"));
        call.enqueue(new Callback<ResponsePOJO>() {
            @Override
            public void onResponse(Call<ResponsePOJO> call, Response<ResponsePOJO> response) {
                mProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponsePOJO> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Erreur",Toast.LENGTH_LONG).show();

            }
        });
    }
    /*
    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
                analyseCheck.setChecked(false);
            }
        }
    }

     */
    //method to show file chooser
    private void showFileChooser() {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("application/pdf");
        chooseFile = Intent.createChooser(chooseFile, "Choisir un fichier PDF");
        startActivityForResult(chooseFile, REQ_PDF);
    }

    @Override
      protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQ_PDF && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri path = data.getData();
                filePath= FilePath.getPath(MedicAddNewVisit.this,path);
                try {
                    InputStream inputStream = MedicAddNewVisit.this.getContentResolver().openInputStream(path);
                    byte[] pdfInBytes = new byte[inputStream.available()];
                    inputStream.read(pdfInBytes);
                    encodedPDF = Base64.encodeToString(pdfInBytes, Base64.DEFAULT);
                    analyseShow.setText(new File(filePath).getName());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
      }
    /**
     * UnicornFilePicker.from(MainActivity.this)
     *                     .addConfigBuilder()
     *                     .selectMultipleFiles(false)
     *                     .showOnlyDirectory(true)
     *                     .setRootDirectory(Environment.getExternalStorageDirectory().getAbsolutePath())
     *                     .showHiddenFiles(false)
     *                     .setFilters(new String[]{"pdf", "png", "jpg", "jpeg"})
     *                     .addItemDivider(true)
     *                     .theme(R.style.UnicornFilePicker_Dracula)
     *                     .build()
     *                     .forResult(Constants.REQ_UNICORN_FILE);
     *
     *
     * List<String> mSelected_files;
     *
     * @Override
     * protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     *     super.onActivityResult(requestCode, resultCode, data);
     *     if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
     *         ArrayList<String> files = data.getStringArrayListExtra("filePaths");
     *         for(String file : files){
     *             Log.e(TAG, file);
     *         }
     *     }
     * }
     *
     * R.style.UnicornFilePicker_Default (light mode)
     * R.style.UnicornFilePicker_Dracula (dark mode)
     */
}