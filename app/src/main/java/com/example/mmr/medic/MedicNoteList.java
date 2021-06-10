package com.example.mmr.medic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.mmr.R;
import com.example.mmr.VolleySingleton;
import com.example.mmr.patient.NoteListAdapter;
import com.example.mmr.patient.Notes;
import com.example.mmr.patient.OnlineMedListAdapter;
import com.example.mmr.patient.OnlineMeds;
import com.example.mmr.shared.SharedModel;

import java.util.Vector;

public class MedicNoteList extends AppCompatActivity {
    RecyclerView recyclerView;
    private RequestQueue queue;
    private String cin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medic_note_list);

        if (getIntent().hasExtra("cin"))
            cin=getIntent().getStringExtra("cin");

        recyclerView=findViewById(R.id.doc_note_list);
        queue = VolleySingleton.getInstance(this).getRequestQueue();
        new SharedModel(this,queue).getOnlineMedAndNote(cin, new SharedModel.LoadHomeInfoCallBack() {
            @Override
            public void onSuccess(Vector<Object> vector) {
                Notes notes=(Notes) vector.get(1);

                NoteListAdapter noteListAdapter = new NoteListAdapter(notes.getNotes());
                // Attach the adapter to the recyclerview to populate items
                recyclerView.setAdapter(noteListAdapter);
            }

            @Override
            public void onErr(String message) {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        });
    }
}