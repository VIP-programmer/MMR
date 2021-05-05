package com.example.mmr.patient;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mmr.R;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;

    // my variables

    private OnlineMeds onlineMeds;
    private Notes notes;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(int param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
            mParam1 = getArguments().getInt(ARG_PARAM1,0);
            mParam2 = getArguments().getString(ARG_PARAM2);
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

        // Initialize data
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
        // Create adapter passing in the sample user data
        OnlineMedListAdapter medListAdapter = new OnlineMedListAdapter(onlineMeds.getMedList());
        NoteListAdapter noteListAdapter = new NoteListAdapter(notes.getNotes());
        // Attach the adapter to the recyclerview to populate items
        rvMeds.setAdapter(medListAdapter);
        rvNotes.setAdapter(noteListAdapter);
        // Set layout manager to position the items
        LinearLayoutManager linearLayout=new LinearLayoutManager(getContext());
        linearLayout.setOrientation(RecyclerView.HORIZONTAL);
        rvMeds.setLayoutManager(linearLayout);
        rvNotes.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}