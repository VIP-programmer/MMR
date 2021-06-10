package com.example.mmr.patient;

import android.graphics.Color;

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
        private String day;
        private int month;
        private String year;

        public Note(String title,String author, String body, int priority, String date) {
            this.title=title;
            this.author = author;
            this.body = body;
            this.priority = priority;
            this.date = date;
            day=extractDay(date);
            month=extractMonth(date);
            year = extractYear(date);
        }
        private String extractYear(String date) {
            return date.substring(0,4);
        }

        private int extractMonth(String date) {
            return Integer.parseInt(date.substring(5,7));
        }
        private String extractDay(String date){
            return date.substring(8);
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

        public int convertPriority(){
            int color=Color.parseColor("#388E3C");
            switch (priority){
                case 1:color= Color.parseColor("#D32F2F");break;
                case 2:color=Color.parseColor("#FFC107");break;
                case 3:color=Color.parseColor("#388E3C");break;
            }
            return color;
        }
        public int getDayOfNote(){
            return Integer.parseInt(day);
        }
        public String getMonthName() {
            String s="";
            switch (month){
                case 1:s="Janvier";break;
                case 2:s="Février";break;
                case 3:s="Mars";break;
                case 4:s="Avril";break;
                case 5:s="Mai";break;
                case 6:s="Juin";break;
                case 7:s="Juillet";break;
                case 8:s="Août";break;
                case 9:s="Septembre";break;
                case 10:s="Octobre";break;
                case 11:s="Novembre";break;
                case 12:s="Décembre";break;
            }
            return s;
        }

        public int getYear() {
            return Integer.parseInt(year);
        }
    }
}
