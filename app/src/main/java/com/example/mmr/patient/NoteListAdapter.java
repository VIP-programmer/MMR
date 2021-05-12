package com.example.mmr.patient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mmr.R;

import java.util.Vector;

import de.hdodenhof.circleimageview.CircleImageView;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder>{
    private Vector<Notes.Note> mList;

    // Pass in the contact array into the constructor
    public NoteListAdapter(Vector<Notes.Note> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public NoteListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.home_note_item, parent, false);

        // Return a new holder instance
        NoteListAdapter.ViewHolder viewHolder = new NoteListAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Notes.Note note=mList.get(position);
        // Set item views based on your views and data model
        holder.title.setText(note.getTitle());
        holder.date.setText(note.getDate());
        holder.priority.setText(note.convertPriority());
        holder.body.setText(note.getBody());
        holder.author.setText(note.getAuthor());
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
        public TextView title;
        public TextView date;
        public TextView priority;
        public TextView body;
        public TextView author;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            title =  (TextView) itemView.findViewById(R.id.note_title);
            date =  (TextView) itemView.findViewById(R.id.note_date);
            priority =  (TextView) itemView.findViewById(R.id.note_priority);
            body =  (TextView) itemView.findViewById(R.id.note_body);
            author =  (TextView) itemView.findViewById(R.id.note_author);
        }
    }
}
