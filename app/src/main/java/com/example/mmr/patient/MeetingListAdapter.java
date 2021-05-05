package com.example.mmr.patient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mmr.R;

import java.util.Vector;

import de.hdodenhof.circleimageview.CircleImageView;

public class MeetingListAdapter extends RecyclerView.Adapter<MeetingListAdapter.ViewHolder>{
    // Store a member variable for the meetings
    private Vector<Meetings.Meeting> mList;

    // Pass in the meeting array into the constructor
    public MeetingListAdapter(Vector<Meetings.Meeting> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public MeetingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.rendezvous_item_list, parent, false);

        // Return a new holder instance
        MeetingListAdapter.ViewHolder viewHolder = new MeetingListAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingListAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Meetings.Meeting med = mList.get(position);

        // Set item views based on your views and data model
        CircleImageView profileImg = holder.profileImg;
        profileImg.setImageBitmap(med.getProfile());
        holder.hour.setText(med.getHourOfMeeting());
        holder.day.setText(med.getDayOfMeeting());
        holder.body.setText(med.getBody());
        holder.docName.setText(med.getDocName());
        //activeDot.setBackground();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public CircleImageView profileImg;
        public TextView day;
        public TextView hour;
        public TextView body;
        public TextView docName;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            profileImg =  (CircleImageView) itemView.findViewById(R.id.rend_doc_image);
            body =  (TextView) itemView.findViewById(R.id.rend_body);
            docName =  (TextView) itemView.findViewById(R.id.rend_avec);
            day =  (TextView) itemView.findViewById(R.id.rend_day);
            hour =  (TextView) itemView.findViewById(R.id.rend_hour);
        }
    }
}
