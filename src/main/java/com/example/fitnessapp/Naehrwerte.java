package com.example.fitnessapp;

import java.io.Serializable;

public class Naehrwerte implements Serializable {
    private int kcal;
    private int fette;
    private int kohlenhydrate;
    private int protein;

    public Naehrwerte(int kcal, int fette, int kohlenhydrate, int protein) {
        this.kcal = kcal;
        this.fette = fette;
        this.kohlenhydrate = kohlenhydrate;
        this.protein = protein;
    }

    //_________________getter und setter__________________________//


    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public void setFette(int fette) {
        this.fette = fette;
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

    public int getFette() {
        return fette;
    }

    public int getKohlenhydrate() {
        return kohlenhydrate;
    }

    public int getProtein() {
        return protein;
    }

    @Override
    public String toString() {
        return "Kcal: " + kcal +
                ", Kohlenhydrate: " + kohlenhydrate +
                ", Protein: " + protein +
                ", Fette: " + fette;
    }
}
