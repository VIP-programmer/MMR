package com.example.mmr.patient;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mmr.R;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;
    private Meetings meetings;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(int param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        // Lookup the recyclerview in activity layout
        RecyclerView rvMeets = (RecyclerView) view.findViewById(R.id.rend_list);
        // Initialize data
        meetings = new Meetings();
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
        // Create adapter passing in the sample user data
        MeetingListAdapter medListAdapter = new MeetingListAdapter(meetings.getMeeetList());
        // Attach the adapter to the recyclerview to populate items
        rvMeets.setAdapter(medListAdapter);
        // Set layout manager to position the items
        rvMeets.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}