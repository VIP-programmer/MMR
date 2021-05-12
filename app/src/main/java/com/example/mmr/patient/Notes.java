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
        private String date;

        public Note(String title,String author, String body, int priority, String date) {
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

        public String getDate() {
            return date;
        }

        public String convertPriority(){
            String s="";
            switch (priority){
                case 1:s="URGENT";break;
                case 2:s="ATTENTION";break;
                case 3:s="INFORAMTION";break;
            }
            return s;
        }


    }
}
