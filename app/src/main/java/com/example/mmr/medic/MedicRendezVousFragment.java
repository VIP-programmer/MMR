package com.example.mmr.medic;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.mmr.R;
import com.example.mmr.VolleySingleton;
import com.example.mmr.patient.Patient;
import com.example.mmr.shared.SharedModel;
import com.mikhaellopez.lazydatepicker.LazyDatePicker;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedicRendezVousFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicRendezVousFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;
    private RelativeLayout write;
    private RelativeLayout read;
    private RelativeLayout seeAll;
    private SearchableSpinner searchableSpinner;
    private RequestQueue queue;
    private MedicSessionManager sessionManager;
    private Vector<Patient> patients;
    public MedicRendezVousFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MedicRendezVousFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedicRendezVousFragment newInstance(int param1, String param2) {
        MedicRendezVousFragment fragment = new MedicRendezVousFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_medic_rendez_vous, container, false);
        queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
        sessionManager = new MedicSessionManager(getActivity());
        searchableSpinner=view.findViewById(R.id.doc_list_patients_rend);
        write=view.findViewById(R.id.write_rend);
        read=view.findViewById(R.id.see_rend);
        seeAll=view.findViewById(R.id.see_all_rend);
        Vector<String> stringPastients =new Vector<>();
        patients =new Vector<>();
        new SharedModel(getActivity(),queue).getPatients(sessionManager.getCinMedcin(), new SharedModel.LoadHomeInfoCallBack() {
            @Override
            public void onSuccess(Vector<Object> vector) {
                for (Object obj: vector) {
                    Patient patient=(Patient) obj;
                    patients.add(patient);
                    stringPastients.add(patient.getNom()+" "+patient.getPrenom());
                    searchableSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, stringPastients));
                }
            }

            @Override
            public void onErr(String message) {
                Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
            }
        });
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MedicMeetsList.class);
                intent.putExtra("cin",patients.get(searchableSpinner.getSelectedItemPosition()).getCin());
                startActivity(intent);
            }
        });
        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MedicMeetsList.class);
                startActivity(intent);
            }
        });
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> infos=new HashMap<String,String>();
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_add_rendez_vous);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button mActionOk = dialog.findViewById(R.id.add_meet);
                Button mActionCancel = dialog.findViewById(R.id.cancel_meet);
                LazyDatePicker picker = dialog.findViewById(R.id.lazyDatePicker);
                NumberPicker hour = dialog.findViewById(R.id.pickerHours);
                NumberPicker minute = dialog.findViewById(R.id.pickerMinutes);
                final Boolean[] isSelected = {false};
                hour.setMaxValue(23);
                minute.setDisplayedValues(new String[]{"00","30"});
                hour.setMinValue(0);
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
                            Toast.makeText(getActivity(),"quelques champs sont vides",Toast.LENGTH_LONG).show();
                        else {
                            dialog.dismiss();
                            infos.put("cinPat",patients.get(searchableSpinner.getSelectedItemPosition()).getCin());
                            infos.put("cinMed",sessionManager.getCinMedcin());
                            infos.put("date",(picker.getDate().getMonth()+1)+"/"+picker.getDate().getDate()+"/"+picker.getDate().toString().substring(picker.getDate().toString().lastIndexOf(" ")+1));
                            infos.put("hour",hour.getValue()+"");
                            infos.put("minute",minute.getValue()+"");
                            new SharedModel(getActivity(),queue).addMeet(infos, new SharedModel.SignUpCallBack() {
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
                    }
                });
            }
        });
        return view;
    }
}