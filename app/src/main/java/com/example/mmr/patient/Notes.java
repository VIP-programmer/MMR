package com.example.mmr.patient;

import java.util.Date;
import java.util.Vector;

public class Notes {
    private Vector<Note> notes;

    public Notes() {
        this.notes = new Vector<>();
    }
    public Notes(Vector<Note> notes) {
        this.notes = new Vector<>();
        this.notes.addAll(notes);
    }

    public Vector<Note> getNotes() {
        return notes;
    }

    public void addNote(Note note){
        notes.add(note);
    }

    public static class Note{
        private String title;
        private String author;
        private String body;
        private int priority;
        private Date date;

        public Note(String title,String author, String body, int priority, Date date) {
            this.title=title;
            this.author = author;
            this.body = body;
            this.priority = priority;
            this.date = date;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getBody() {
            return body;
        }

        public int getPriority() {
            return priority;
        }

        public Date getDate() {
            return date;
        }

        public String getDateAsString(){
            return "";
        }
    }
}
