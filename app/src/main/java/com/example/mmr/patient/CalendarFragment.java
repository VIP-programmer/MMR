package com.example.mmr.patient;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.mmr.R;
import com.example.mmr.VolleySingleton;
import com.example.mmr.shared.SharedModel;

import java.util.Date;
import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_CIN = "cin";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private Meetings meetings;
    private String cin;
    TextView emplty;

    private RequestQueue queue;
    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(int param1, String cin) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_CIN, cin);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            cin = getArguments().getString(ARG_CIN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        // Lookup the recyclerview in activity layout
        RecyclerView rvMeets = (RecyclerView) view.findViewById(R.id.rend_list);

        emplty=view.findViewById(R.id.empty_view_my_rends);
        rvMeets.setVisibility(View.GONE);
        emplty.setVisibility(View.VISIBLE);

        queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
        // Initialize data
        meetings = new Meetings();
        new SharedModel(getContext(),queue).getMeetings(cin, new SharedModel.LoadHomeInfoCallBack() {
            @Override
            public void onSuccess(Vector<Object> vector) {
                meetings=(Meetings) vector.get(0);
                // Create adapter passing in the sample user data
                MeetingListAdapter medListAdapter = new MeetingListAdapter(meetings.getMeeetList(), getActivity(),cin,false);
                // Attach the adapter to the recyclerview to populate items
                rvMeets.setAdapter(medListAdapter);
                // Set layout manager to position the items
                rvMeets.setLayoutManager(new LinearLayoutManager(getContext()));
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

            }
        });
        /*
        meetings.addmeeting(new Meetings.Meeting(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.profileholder),new Date(),"This is a meeting for your interest","Dr. Ahmed"));
        meetings.addmeeting(new Meetings.Meeting(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.profileholder),new Date(),"This is a meeting for your interest","Dr. Ahmed"));
        meetings.addmeeting(new Meetings.Meeting(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.profileholder),new Date(),"This is a meeting for your interest","Dr. Ahmed"));
        meetings.addmeeting(new Meetings.Meeting(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.profileholder),new Date(),"This is a meeting for your interest","Dr. Ahmed"));
        meetings.addmeeting(new Meetings.Meeting(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.profileholder),new Date(),"This is a meeting for your interest","Dr. Ahmed"));
        meetings.addmeeting(new Meetings.Meeting(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.profileholder),new Date(),"This is a meeting for your interest","Dr. Ahmed"));
        meetings.addmeeting(new Meetings.Meeting(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.profileholder),new Date(),"This is a meeting for your interest","Dr. Ahmed"));
        meetings.addmeeting(new Meetings.Meeting(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.profileholder),new Date(),"This is a meeting for your interest","Dr. Ahmed"));
        meetings.addmeeting(new Meetings.Meeting(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.profileholder),new Date(),"This is a meeting for your interest","Dr. Ahmed"));
        meetings.addmeeting(new Meetings.Meeting(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.profileholder),new Date(),"This is a meeting for your interest","Dr. Ahmed"));

         */

        return view;
    }
}