package com.example.mmr.patient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mmr.R;

import java.util.Vector;

public class AnalyseListAdapter extends RecyclerView.Adapter<AnalyseListAdapter.ViewHolder>{
    // Store a member variable for the meetings
    private Vector<Analyse> mList;

    // Pass in the meeting array into the constructor
    public AnalyseListAdapter(Vector<Analyse> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public AnalyseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.analyse_item_list, parent, false);

        // Return a new holder instance
        AnalyseListAdapter.ViewHolder viewHolder = new AnalyseListAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnalyseListAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Analyse med = mList.get(position);

        // Set item views based on your views and data model
        holder.name.setText(med.getTitre());
        holder.date.setText(med.getDate());
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        public TextView name;
        public TextView date;
        public ImageButton download;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            name =  (TextView) itemView.findViewById(R.id.analyse_doc_name);
            date =  (TextView) itemView.findViewById(R.id.analyse_date);
            download =  (ImageButton) itemView.findViewById(R.id.download);
        }
    }
}

