package com.example.mmr.patient;

import java.util.Vector;

public class Positions {
    private Vector<Position> positionVector;
    private String type;

    public Positions(String type) {
        this.type = type;
        positionVector=new Vector<>();
    }

    public Positions() {
        positionVector=new Vector<>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Vector<Position> getPositionVector() {
        return positionVector;
    }

    public void setPositionVector(Vector<Position> positionVector) {
        this.positionVector = positionVector;
    }
    public void addPosition(Position position){
        positionVector.add(position);
    }
    public static class Position{
        private float x,y;
        private String name;

        public Position(float x, float y, String name) {
            this.x = x;
            this.y = y;
            this.name = name;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
