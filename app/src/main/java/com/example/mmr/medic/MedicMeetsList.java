package com.example.mmr.medic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.bumptech.glide.request.target.ThumbnailImageViewTarget;
import com.example.mmr.R;
import com.example.mmr.VolleySingleton;
import com.example.mmr.patient.MeetingListAdapter;
import com.example.mmr.patient.Meetings;
import com.example.mmr.shared.SharedModel;

import java.util.Vector;

public class MedicMeetsList extends AppCompatActivity {
    private Meetings meetings;
    private String cin;
    TextView emplty;
    private RequestQueue queue;
    private MedicSessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make it fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_medic_meets_list);

        RecyclerView rvMeets = (RecyclerView) findViewById(R.id.doc_meet_list);
        emplty=findViewById(R.id.empty_view_meet_list);
        rvMeets.setVisibility(View.GONE);
        emplty.setVisibility(View.VISIBLE);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        sessionManager=new MedicSessionManager(this);
        meetings = new Meetings();
        new SharedModel(this,queue).getMedicMeetings(cin, new SharedModel.LoadHomeInfoCallBack() {
            @Override
            public void onSuccess(Vector<Object> vector) {
                meetings=(Meetings) vector.get(0);
                // Create adapter passing in the sample user data
                MeetingListAdapter medListAdapter = new MeetingListAdapter(meetings.getMeeetList());
                // Attach the adapter to the recyclerview to populate items
                rvMeets.setAdapter(medListAdapter);
                // Set layout manager to position the items
                rvMeets.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                if (meetings.getMeeetList().isEmpty()) {
                    rvMeets.setVisibility(View.GONE);
                    emplty.setVisibility(View.VISIBLE);
                }
                else {
                    rvMeets.setVisibility(View.VISIBLE);
                    emplty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onErr(String message) {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        });
    }
}