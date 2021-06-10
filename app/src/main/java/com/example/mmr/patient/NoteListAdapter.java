package com.example.mmr.patient;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

        String shortBody;
        String fullBody;
        final Boolean[] isShort = {false};
        // Get the data model based on position
        Notes.Note note=mList.get(position);
        // Set item views based on your views and data model
        holder.title.setText(note.getTitle());
        holder.day.setText(note.getDayOfNote()+"");
        holder.month.setText(note.getMonthName());
        holder.year.setText(note.getYear()+"");
        holder.priority.setBackgroundColor(note.convertPriority());
        //holder.body.setText(note.getBody());
        holder.author.setText(note.getAuthor());
        fullBody=note.getBody();
        //adding see more
        if(fullBody.length() > 105){

            shortBody=fullBody.substring(0,106)+"...<b>Voir plus</b>";
            holder.body.setText(Html.fromHtml(shortBody));
            isShort[0] =true;
            holder.body.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isShort[0]){
                        holder.body.setText(fullBody);
                        isShort[0] =false;
                    }else{
                        holder.body.setText(Html.fromHtml(shortBody));
                        isShort[0] =true;
                    }
                }
            });
        }else{
            holder.body.setText(fullBody);
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
        public TextView title;
        public TextView day;
        public TextView month;
        public TextView year;
        public LinearLayout priority;
        public TextView body;
        public TextView author;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            title =  (TextView) itemView.findViewById(R.id.note_title);
            day =  (TextView) itemView.findViewById(R.id.note_day);
            month =  (TextView) itemView.findViewById(R.id.note_month);
            year =  (TextView) itemView.findViewById(R.id.note_year);
            priority =  (LinearLayout) itemView.findViewById(R.id.note_priority);
            body =  (TextView) itemView.findViewById(R.id.note_body);
            author =  (TextView) itemView.findViewById(R.id.note_author);


        }
    }
}
