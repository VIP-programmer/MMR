package com.example.mmr.patient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mmr.R;

import java.util.Vector;

public class MedicamentListAdapter extends RecyclerView.Adapter<MedicamentListAdapter.ViewHolder>{
    // Store a member variable for the meetings
    private Vector<Medicament> mList;

    // Pass in the meeting array into the constructor
    public MedicamentListAdapter(Vector<Medicament> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public MedicamentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.medicament_item_list, parent, false);

        // Return a new holder instance
        MedicamentListAdapter.ViewHolder viewHolder = new MedicamentListAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentListAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Medicament med = mList.get(position);

        // Set item views based on your views and data model
        holder.docName.setText(med.getDoc());
        holder.titre.setText(med.getName());
        holder.qnte.setText(med.getQnte()+" fois");
        holder.prix.setText(med.getPrix());
        holder.date.setText("De "+med.getDateStart()+" à "+med.getDateEnd());

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
        public TextView docName;
        public TextView titre;
        public TextView date;
        public TextView qnte;
        public TextView prix;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            docName =  (TextView) itemView.findViewById(R.id.medicament_name);
            titre =  (TextView) itemView.findViewById(R.id.med_medicament);
            qnte =  (TextView) itemView.findViewById(R.id.qnt);
            date =  (TextView) itemView.findViewById(R.id.date_medicament);
            prix =  (TextView) itemView.findViewById(R.id.medicament_prix);
        }
    }
}

