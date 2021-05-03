package com.example.mmr.patient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mmr.R;

import java.util.Vector;

import de.hdodenhof.circleimageview.CircleImageView;

public class OnlineMedListAdapter extends RecyclerView.Adapter<OnlineMedListAdapter.ViewHolder> {
    // Store a member variable for the contacts
    private Vector<OnlineMeds.OnlineMed> mList;

    // Pass in the contact array into the constructor
    public OnlineMedListAdapter(Vector<OnlineMeds.OnlineMed> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.online_med_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        OnlineMeds.OnlineMed med = mList.get(position);

        // Set item views based on your views and data model
        CircleImageView profileImg = holder.profileImg;
        profileImg.setImageBitmap(med.getProfile());
        View activeDot= holder.activeDot;
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
        public View activeDot;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            profileImg =  (CircleImageView) itemView.findViewById(R.id.profile_image);
            activeDot = (View) itemView.findViewById(R.id.active_dot);
        }
    }
}