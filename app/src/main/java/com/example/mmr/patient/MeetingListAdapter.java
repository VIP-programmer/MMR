package com.example.mmr.patient;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mmr.R;

import java.util.Calendar;
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
        holder.hour.setText(med.getHour());
        holder.day.setText(med.getDay());
        holder.docName.setText("Visite avec "+med.getDocName());
        holder.month.setText(med.getMonthName());
        holder.year.setText(med.getYear()+"");
        //activeDot.setBackground();
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, med.getDayOfMeeting());
                cal.set(Calendar.MONTH, med.getMonth()-1);
                cal.set(Calendar.YEAR, med.getYear());
                Log.i("TAG", "onClick minutes: "+med.getMinuteOfMeeting());
                cal.set(Calendar.HOUR_OF_DAY, med.getHourOfMeeting());
                cal.set(Calendar.MINUTE, med.getMinuteOfMeeting());
                Intent intent= new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra(CalendarContract.Events.TITLE, "Visite");
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,cal.getTimeInMillis());
                intent.putExtra(CalendarContract.Events.ALL_DAY, false);// periodicity
                intent.putExtra(CalendarContract.Events.DESCRIPTION,"Visite avec Dr."+med.getDocName());
                v.getContext().startActivity(intent);
            }
        });
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
        public TextView day;
        public TextView hour;
        public TextView docName;
        public TextView month;
        public TextView year;
        public ImageButton add;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            docName =  (TextView) itemView.findViewById(R.id.rend_doc_name);
            month =  (TextView) itemView.findViewById(R.id.rend_month);
            day =  (TextView) itemView.findViewById(R.id.rend_day);
            hour =  (TextView) itemView.findViewById(R.id.rend_hour);
            year =  (TextView) itemView.findViewById(R.id.rend_year);
            add =  itemView.findViewById(R.id.rend_add_to_cal);
        }
    }
}
