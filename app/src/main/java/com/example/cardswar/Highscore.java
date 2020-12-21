package com.example.cardswar;

public class Highscore implements Comparable<Highscore> {
    private String name;
    private int score;
    private double lat, lng;
    private boolean touched;

    public Highscore() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Highscore(String name, int score, double lat, double lng) {
        this.name = name;
        this.score = score;
        this.lat = lat;
        this.lng = lng;
    }


    @Override
    public String toString() {
        return this.name + "|" + this.score + "|" + this.lat + "|" + this.lng;
    }

    @Override
    public int compareTo(Highscore o) {
        return o.score-this.score;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public boolean getTouched() {
        return touched;
    }
}
