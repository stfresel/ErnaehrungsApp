package com.example.fitnessapp;

import java.io.Serializable;

/**
 * <h2>Klasse: Nährwerte</h2>
 * Zu den Nährwerte gehören: Kalorien, Kohlenhydrate, Proteine und Fette.
 */
public class Naehrwerte implements Serializable {
    /**
     * Gibt die Menge der Kalorien an.
     */
    private int kcal;

    /**
     * Gibt die Menge der Fette an.
     */
    private int fette;

    /**
     * Gibt die Menge der Kohlenhydrate an.
     */
    private int kohlenhydrate;

    /**
     * Gibt die Menge der Proteine an.
     */
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
