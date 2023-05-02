package com.example.fitnessapp;

import java.io.Serializable;

public class Naehrwerte implements Serializable {
    private int kcal;
    private int fett;
    private int kohlenhydrate;
    private int protein;

    public Naehrwerte(int kcal, int fett, int kohlenhydrate, int protein) {
        this.kcal = kcal;
        this.fett = fett;
        this.kohlenhydrate = kohlenhydrate;
        this.protein = protein;
    }

    //_________________getter und setter__________________________//


    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public void setFett(int fett) {
        this.fett = fett;
    }

    public void setKohlenhydrate(int kohlenhydrate) {
        this.kohlenhydrate = kohlenhydrate;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getKcal() {
        return kcal;
    }

    public int getFett() {
        return fett;
    }

    public int getKohlenhydrate() {
        return kohlenhydrate;
    }

    public int getProtein() {
        return protein;
    }

    @Override
    public String toString() {
        return "Naehrwerte{" +
                "kcal=" + kcal +
                ", kohlenhydrate=" + kohlenhydrate +
                ", protein=" + protein +
                ", fett=" + fett +
                '}';
    }
}
