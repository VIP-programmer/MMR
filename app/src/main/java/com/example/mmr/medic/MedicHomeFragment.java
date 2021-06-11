package com.example.mmr.medic;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mmr.R;
import com.example.mmr.shared.LoadImage;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedicHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicHomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NOM = "nom";
    private static final String ARG_PRENOM = "prenom";
    private static final String ARG_IMG = "img";
    private static final String ARG_CIN = "cin";

    // TODO: Rename and change types of parameters
    private String nom;
    private String prenom;
    private String img;
    private String cin;
    private LinearLayout patients;
    private LinearLayout records;
    private LinearLayout notes;
    private TextView name;
    private CircleImageView profile;

    public MedicHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MedicHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedicHomeFragment newInstance(String cin, String nom, String prenom, String img) {
        MedicHomeFragment fragment = new MedicHomeFragment();
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
        View view= inflater.inflate(R.layout.fragment_medic_home, container, false);

        patients=view.findViewById(R.id.voir_patients);
        records=view.findViewById(R.id.voir_record);
        notes=view.findViewById(R.id.voir_notes);
        profile=view.findViewById(R.id.medic_home_profile_image);
        name=view.findViewById(R.id.medic_home_name);

        //loading profile image
        if (!img.equals("local"))
            new LoadImage(profile,getContext()).execute(img);
        name.setText(nom+" "+prenom);
        patients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),MedicPatients.class));
            }
        });
        records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),MedicEnregistrement.class));
            }
        });
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),MedicNoteHome.class));
            }
        });

        return view;
    }
}