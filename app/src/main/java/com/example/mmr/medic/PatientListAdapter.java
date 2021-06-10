package com.example.mmr.medic;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mmr.R;
import com.example.mmr.patient.MedListAdapter;
import com.example.mmr.patient.OnlineMeds;
import com.example.mmr.patient.Patient;
import com.example.mmr.patient.ProfileMed;
import com.example.mmr.shared.LoadImage;

import java.util.Vector;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.ViewHolder> {
    Context context;
    Vector<Patient> patients;
    public PatientListAdapter(Context applicationContext, Vector<Patient> patients) {
        this.context=applicationContext;
        this.patients=patients;
    }

    @NonNull
    @Override
    public PatientListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.med_item, parent, false);

        // Return a new holder instance
        PatientListAdapter.ViewHolder viewHolder = new PatientListAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PatientListAdapter.ViewHolder holder, int position) {
// Get the data model based on position
        Patient med = patients.get(position);

        // Set item views based on your views and data model
        holder.cin=med.getCin();
        CircleImageView profileImg = holder.profileImg;
        if (med.getPhoto().equals("local")) {
            profileImg.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.profileholder));
        } else {
            new LoadImage(profileImg, context).execute(med.getPhoto());
        }
        holder.name.setText(med.getNom()+" "+med.getPrenom());
        //activeDot.setBackground();
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public CircleImageView profileImg;
        public TextView name;

        public String cin;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            profileImg = (CircleImageView) itemView.findViewById(R.id.med_item_img);
            name = (TextView) itemView.findViewById(R.id.med_item_txt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(v.getContext(), ProfilePatient.class);
                    intent.putExtra("cin",cin);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
