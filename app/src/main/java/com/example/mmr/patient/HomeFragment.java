package com.example.mmr.patient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.mmr.Config;
import com.example.mmr.R;
import com.example.mmr.VolleySingleton;
import com.example.mmr.medic.Medcin;
import com.example.mmr.shared.LoadImage;
import com.example.mmr.shared.SharedModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

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
    private PatientSessionManager sessionManager;

    // my variables

    private OnlineMeds onlineMeds;
    private Notes notes;
    private TextView name;
    private CircleImageView profile;
    private RequestQueue queue;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String cin, String nom, String prenom, String img) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Lookup the recyclerview in activity layout
        RecyclerView rvMeds = (RecyclerView) view.findViewById(R.id.online_med_list);
        RecyclerView rvNotes = (RecyclerView) view.findViewById(R.id.note_list);
        name=view.findViewById(R.id.name);
        profile=view.findViewById(R.id.profile_image);
        //loading profile image
        new LoadImage(profile,getActivity()).execute(img);
        queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
        name.setText(nom+" "+prenom);
        /*
        onlineMeds = new OnlineMeds();
        notes =new Notes();
        notes.addNote(new Notes.Note("Title","Dr Ahmed","this is note this is notethis is note this is note",1,new Date()));
        notes.addNote(new Notes.Note("Title","Dr Ahmed","this is note this is notethis is note this is note",1,new Date()));
        notes.addNote(new Notes.Note("Title","Dr Ahmed","this is note this is notethis is note this is note",1,new Date()));
        notes.addNote(new Notes.Note("Title","Dr Ahmed","this is note this is notethis is note this is note",1,new Date()));
        notes.addNote(new Notes.Note("Title","Dr Ahmed","this is note this is notethis is note this is note",1,new Date()));
        notes.addNote(new Notes.Note("Title","Dr Ahmed","this is note this is notethis is note this is note",1,new Date()));
        notes.addNote(new Notes.Note("Title","Dr Ahmed","this is note this is notethis is note this is note",1,new Date()));
        notes.addNote(new Notes.Note("Title","Dr Ahmed","this is note this is notethis is note this is note",1,new Date()));
        onlineMeds.addOnlineMed(new OnlineMeds.OnlineMed(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.profileholder),true));
        onlineMeds.addOnlineMed(new OnlineMeds.OnlineMed(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.profileholder),true));
        onlineMeds.addOnlineMed(new OnlineMeds.OnlineMed(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.profileholder),true));
        onlineMeds.addOnlineMed(new OnlineMeds.OnlineMed(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.profileholder),true));
        onlineMeds.addOnlineMed(new OnlineMeds.OnlineMed(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.profileholder),true));
        onlineMeds.addOnlineMed(new OnlineMeds.OnlineMed(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.profileholder),true));
        onlineMeds.addOnlineMed(new OnlineMeds.OnlineMed(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.profileholder),true));
        onlineMeds.addOnlineMed(new OnlineMeds.OnlineMed(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.profileholder),true));
        onlineMeds.addOnlineMed(new OnlineMeds.OnlineMed(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.profileholder),true));
        */

        // Initialize data
        //new LoadOnlineMedics(queue,rvMeds,rvNotes,cin).execute();
        new SharedModel(getActivity(),queue).getOnlineMedAndNote(cin, new SharedModel.LoadHomeInfoCallBack() {
            @Override
            public void onSuccess(Vector<Object> vector) {
                OnlineMeds meds=(OnlineMeds)vector.get(0);
                Notes notes=(Notes) vector.get(1);
                OnlineMedListAdapter medListAdapter = new OnlineMedListAdapter(getActivity(),meds.getMedList());
                NoteListAdapter noteListAdapter = new NoteListAdapter(notes.getNotes());
                // Attach the adapter to the recyclerview to populate items
                rvMeds.setAdapter(medListAdapter);
                rvNotes.setAdapter(noteListAdapter);
            }

            @Override
            public void onErr(String message) {
                Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
            }
        });
        // Set layout manager to position the items
        LinearLayoutManager linearLayout=new LinearLayoutManager(getContext());
        linearLayout.setOrientation(RecyclerView.HORIZONTAL);
        rvMeds.setLayoutManager(linearLayout);
        rvNotes.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
    static class LoadOnlineMedics extends AsyncTask<Void,Void, Vector<Object>> {
        String cin;
        RequestQueue queue;
        RecyclerView medList,noteList;
        Context context;
        public LoadOnlineMedics(RequestQueue queue,RecyclerView medList, RecyclerView noteList, String cin) {
            this.cin=cin;
            this.queue=queue;
            this.noteList=noteList;
            this.medList=medList;
            this.context=context;
        }

        @Override
        protected Vector<Object> doInBackground(Void... voids) {
            Vector<Object> map =new Vector<>();
            new SharedModel(context,queue).getOnlineMedAndNote(cin, new SharedModel.LoadHomeInfoCallBack() {
                @Override
                public void onSuccess(Vector<Object> vector) {
                    map.addAll(vector);
                    Log.i("TAG", "onSuccess: "+vector.size());
                }

                @Override
                public void onErr(String message) {
                    Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                }
            });
            return map;
        }

        @Override
        protected void onPostExecute(Vector<Object> map) {
            // Create adapter passing in the sample user data
            OnlineMeds meds=(OnlineMeds)map.get(0);
            Notes notes=(Notes) map.get(1);
            OnlineMedListAdapter medListAdapter = new OnlineMedListAdapter(context,meds.getMedList());
            NoteListAdapter noteListAdapter = new NoteListAdapter(notes.getNotes());
            // Attach the adapter to the recyclerview to populate items
            medList.setAdapter(medListAdapter);
            noteList.setAdapter(noteListAdapter);
        }
    }
}
