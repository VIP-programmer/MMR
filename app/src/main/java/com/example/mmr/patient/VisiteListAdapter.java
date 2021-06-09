package com.example.mmr.patient;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mmr.R;

import java.util.Calendar;
import java.util.Vector;

public class VisiteListAdapter extends RecyclerView.Adapter<VisiteListAdapter.ViewHolder>{
    // Store a member variable for the meetings
    private Vector<Visite> mList;

    // Pass in the meeting array into the constructor
    public VisiteListAdapter(Vector<Visite> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public VisiteListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.visite_item_list, parent, false);

        // Return a new holder instance
        VisiteListAdapter.ViewHolder viewHolder = new VisiteListAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VisiteListAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Visite med = mList.get(position);

        // Set item views based on your views and data model
        holder.day.setText(med.getDate());
        holder.docName.setText(med.getDoc());
        if (!med.getAnalyse().matches(""))
            holder.analyses.setText(med.getAnalyse());
        if (!med.getAllergie().matches(""))
            holder.allergies.setText(med.getAllergie());
        if (med.getMedicaments().size() > 0)
            holder.medicaments.setText("");
        for (String name: med.getMedicaments()) {
            holder.medicaments.append(name+"\n");
        }
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
        public TextView docName;
        public TextView medicaments;
        public TextView analyses;
        public TextView allergies;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            docName =  (TextView) itemView.findViewById(R.id.visite_avec);
            day =  (TextView) itemView.findViewById(R.id.visite_date);
            medicaments =  (TextView) itemView.findViewById(R.id.visit_medicaments);
            analyses =  (TextView) itemView.findViewById(R.id.visite_analyse);
            allergies =  (TextView) itemView.findViewById(R.id.visite_allergie);
        }
    }
}
